package info2.lecteurpdf.tests;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;


/**
 * TODO commenter les responsabilités de cette classe
 * @author Ayzoh
 *
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            /* PdfRenderer -> Plus rapide -> java 8 ou +*/
            System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");

            /* Import FXML */
            VBox root = (VBox) FXMLLoader.load(getClass().getResource("maquette.fxml"));
            Scene scene = new Scene(root,900,600);

            //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setTitle("Test lecture pdf");

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO commenter le rôle de cette méthode
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
