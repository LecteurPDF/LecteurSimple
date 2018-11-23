package info2.lecteurpdf.tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class OutilLecture {

	PDDocument document;

	private int pageCour = 0;

	public OutilLecture() {
	}

	public OutilLecture(String nomFichier) {

		try {
			document = PDDocument.load(new File(nomFichier));
		} catch (InvalidPasswordException e) {
			// Protégé
			e.printStackTrace();
		} catch (IOException e) {
			// Fichier non trouvé
			e.printStackTrace();
		}

	};

	public ImageView getPagePdfToImg( int page ) {

		ImageView imageCentrale = new ImageView(); // Image convertie finale

		if(!pageCorrecte()) {
			return getPagePdfToImg(pageCour);
		}

		/* Rendu de l'image */
		PDFRenderer pdfRenderer = new PDFRenderer(document);

		try {
			BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 100, ImageType.RGB);
			setPagesCour(page);
			/* Convertion d'un objet JavaAWT en JavaFX */
			WritableImage fxImage = SwingFXUtils.toFXImage(bim, null);
			imageCentrale = new ImageView(fxImage);

		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

		return imageCentrale;


	}

	public int getPagesCour() {
		return pageCour;
	}

	public void setPagesCour(int page) {
		pageCour = page;
	}

	public ImageView getNextPage() {
		if(pageCour < document.getNumberOfPages()-1) {
			pageCour++;
		}
		return getPagePdfToImg(pageCour);

	}

	public ImageView getPrecPage() {
		if(pageCour > 0) {
			pageCour--;
		}
		return getPagePdfToImg(pageCour);
	}

	public boolean pageCorrecte() {
		if( pageCour >= 0 && pageCour < document.getNumberOfPages() ) {
			return true;
		}
		return false;

	}

	/**
	 *  Fermeture fichier
	 *
	 */
	public void close() {
		try {
			document.close();
		} catch (IOException e) {
			// Probléme fermetire
			e.printStackTrace();
		}
	}

}
