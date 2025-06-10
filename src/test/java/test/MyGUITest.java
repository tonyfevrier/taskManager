import org.testfx.framework.junit5.ApplicationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import static org.junit.jupiter.api.Assertions.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import static org.testfx.util.WaitForAsyncUtils.sleep;
import org.testfx.matcher.control.LabeledMatchers;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;

import org.openjfx.*;


@ExtendWith(ApplicationExtension.class)
public class MyGUITest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm()); // add prend une URL absolue sous forme de str (ce que permet toExternalForm)  
        stage.setTitle("TaskManager");
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void test(FxRobot robot) {
        // L'utilisateur arrivant sur la page d'enregistrement clique sur register et voit le message d'erreur s'afficher
        sleep(1000);
        robot.clickOn("#registerButton");
        /*DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Label headerLabel = (Label) dialogPane.lookup(".header-paner .label");
        assertEquals("Invalid task", headerLabel.getText());
        sleep(1000);*/
        
        // Il remplit alors une tâche non datée, un message de validation apparaît.
        // Il remplit ensuite une tâche datée du jour (avec today pour éviter de devoir modifier le test le lendemain)
        // Il clique sur le menu afin d'aller voir toutes les tâches et voit les deux tâches créées
        // Il clique sur les tâches du jour et voit la tâche qu'il a créée
        // Il supprime sa tâche et voit sa tâche disparaître
        // Il clique sur toutes les tâches et voit qu'il ne reste qu'une tâche
        // Il clique sur cette tâche et la modifie en lui ajoutant une date.

        /*clickOn("#myButton");
        verifyThat("#myLabel", hasText("Bonjour !"));*/
    }
}

