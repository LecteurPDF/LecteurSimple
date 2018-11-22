package info2.lecteurpdf.tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class OutilLecture {

	public static ImageView getPagePdfToImg( String nomFichier, int page ) {

		PDDocument document;
		ImageView imageCentrale = new ImageView(); // Image convertie finale

		try {
			document = PDDocument.load(new File(nomFichier));
			/* Rendu de l'image */
			PDFRenderer pdfRenderer = new PDFRenderer(document);

	        BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 100, ImageType.RGB);

	        /* Convertion d'un objet JavaAWT en JavaFX */
	        WritableImage fxImage = SwingFXUtils.toFXImage(bim, null);
	        imageCentrale = new ImageView(fxImage);

	        /* Fermeture fichier */
	        document.close();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return imageCentrale;


	}

}
