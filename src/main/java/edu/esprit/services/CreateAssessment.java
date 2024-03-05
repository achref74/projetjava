package edu.esprit.services;

import com.google.cloud.recaptchaenterprise.v1.RecaptchaEnterpriseServiceClient;
import com.google.recaptchaenterprise.v1.Assessment;
import com.google.recaptchaenterprise.v1.CreateAssessmentRequest;
import com.google.recaptchaenterprise.v1.Event;
import com.google.recaptchaenterprise.v1.ProjectName;
import com.google.recaptchaenterprise.v1.RiskAnalysis.ClassificationReason;
import java.io.IOException;

public class CreateAssessment {

    public static void main(String[] args) throws IOException {
        // À FAIRE : remplacer le jeton et les variables d'action reCAPTCHA avant d'exécuter l'exemple.
        String projectID = "projectpidev";
        String recaptchaKey = "6Lfp04cpAAAAAAVTXdytMfTBojOUSxm1mPSGeYes";
        String token = "action-token";
        String recaptchaAction = "action-name";

        createAssessment(projectID, recaptchaKey, token, recaptchaAction);
    }

    /**
     * Créez une évaluation pour analyser le risque d'une action dans l'interface utilisateur.
     *
     * @param projectID : L'ID de votre projet Google Cloud.
     * @param recaptchaKey : La clé reCAPTCHA associée au site ou à l'application
     * @param token : Jeton généré auprès du client.
     * @param recaptchaAction : Nom d'action correspondant au jeton.
     */
    public static void createAssessment(
            String projectID, String recaptchaKey, String token, String recaptchaAction)
            throws IOException {
        // Créez le client reCAPTCHA.
        // À FAIRE : mettre en cache le code de génération du client (recommandé) ou appeler client.close() avant de quitter la méthode.
        try (RecaptchaEnterpriseServiceClient client = RecaptchaEnterpriseServiceClient.create()) {

            // Définissez les propriétés de l'événement à suivre.
            Event event = Event.newBuilder().setSiteKey(recaptchaKey).setToken(token).build();

            // Créez la demande d'évaluation.
            CreateAssessmentRequest createAssessmentRequest =
                    CreateAssessmentRequest.newBuilder()
                            .setParent(ProjectName.of(projectID).toString())
                            .setAssessment(Assessment.newBuilder().setEvent(event).build())
                            .build();

            Assessment response = client.createAssessment(createAssessmentRequest);

            // Vérifiez si le jeton est valide.
            if (!response.getTokenProperties().getValid()) {
                System.out.println(
                        "The CreateAssessment call failed because the token was: "
                                + response.getTokenProperties().getInvalidReason().name());
                return;
            }

            // Vérifiez si l'action attendue a été exécutée.
            if (!response.getTokenProperties().getAction().equals(recaptchaAction)) {
                System.out.println(
                        "The action attribute in reCAPTCHA tag is: "
                                + response.getTokenProperties().getAction());
                System.out.println(
                        "The action attribute in the reCAPTCHA tag "
                                + "does not match the action ("
                                + recaptchaAction
                                + ") you are expecting to score");
                return;
            }

            // Obtenez le score de risques et le ou les motifs.
            // Pour savoir comment interpréter l'évaluation, consultez les pages suivantes :
            // https://cloud.google.com/recaptcha-enterprise/docs/interpret-assessment
            for (ClassificationReason reason : response.getRiskAnalysis().getReasonsList()) {
                System.out.println(reason);
            }

            float recaptchaScore = response.getRiskAnalysis().getScore();
            System.out.println("The reCAPTCHA score is: " + recaptchaScore);

            // Obtenez le nom de l'évaluation (id). Utilisez cette méthode pour annoter l'évaluation.
            String assessmentName = response.getName();
            System.out.println(
                    "Assessment name: " + assessmentName.substring(assessmentName.lastIndexOf("/") + 1));
        }
    }
}