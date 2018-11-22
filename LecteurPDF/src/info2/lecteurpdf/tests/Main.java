package info2.lecteurpdf.tests;

import java.awt.image.BufferedImage;
import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,827,1170);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);

			File monFichier = new File("src\\Exemple de PDF\\identifications_de_paliers_dans_une_discussion.pdf"); // Sélection du fichier

			PDDocument document = PDDocument.load(monFichier);
	        PDFRenderer pdfRenderer = new PDFRenderer(document);
	        //for (int page = 0; page < document.getNumberOfPages(); ++page)
	        //{
	        /** Image qui repr�sente l'image centrale*/

	        int page = 0;
	        BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 100, ImageType.RGB);

	            // suffix in filename will be used as the file format

	        WritableImage fxImage = SwingFXUtils.toFXImage(bim, null);
	        ImageView imageCentrale = new ImageView(fxImage);

root.getChildren().add(imageCentrale);
	        //}
	        document.close();

			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
