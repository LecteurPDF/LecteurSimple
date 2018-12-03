/*
 * OutilLecture.java                            22/11/2018
 */

package info2.lecteurpdf;

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

/**
 * Permet de g�rer une page pdf, � partir d'un fichier pdf ( String du chemin + nom ) on cr�e
 * un objet qui contient :
 *   - Le fichier lui-m�me (pdf)
 *   - La page que l'on est en train de regarder
 * A aucun moment on ne modifie le fichier, on ne fait que le lire
 * Le fichi� est ouvert lorsque l'objet est cr�e, il sera ferm� gr�ce � la m�thode .close()
 * Le but de la classe est de transformer une page en ImageView et de gerer l'affichage de cette image
 * @author sannac, vivier, pouzelgues, renoleau
 * @version 1
 */
public class OutilLecture {

    /** Le document pdf sur lequel on va agir */
    private PDDocument document;

    /** La page que l'on est en train de lire, on commence � 0 */
    private int pageCour = 0;

    /** Nombre de page totale */
    private int nbPages;

	/**
     * Constructeur par d�faut, sans argument
     */
    public OutilLecture() {
    }

    /**
     * Permet d'initialiser un objet OutilLecture, il contient
     *   - Le fichier lui-m�me (pdf)
     *   - La page que l'on est en train de regarder
     * @param nomFichier Le fichier pdf que l'on veut ouvrir et afficher
     */
    public OutilLecture(String nomFichier) {

        try {
            document = PDDocument.load(new File(nomFichier));
            nbPages = document.getNumberOfPages();
        } catch (InvalidPasswordException e) {
            // Prot�g�
            e.printStackTrace();
        } catch (IOException e) {
            // Fichier non trouv�
            e.printStackTrace();
        }

    };

    /**
     * Retourne le nombre de pages r�el totales du PDF courant
     * @return nombre de pages du pdf
     */
    public int getNbPages() {
		return nbPages;
	}

    /**
     * Permet de transformer une page pdf en image ImageView
     * @param page La page que l'on souhaite transformer
     * @return La page transform�e en ImageView
     */
    public ImageView getPagePdfToImg( int page ) {

        ImageView imageCentrale = new ImageView(); // L'image de la page pdf convertie

        if(!pageCorrecte()) {
            return getPagePdfToImg(pageCour);
        }

        /* Rendu de l'image */
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        try {
            /* Cr�ation d'une image en couleur avec 100 DPI */
            BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 122, ImageType.RGB);
            setPagesCour(page); // La page courante est chang�e

            /* Convertion d'un objet JavaAWT en JavaFX */
            WritableImage fxImage = SwingFXUtils.toFXImage(bim, null);
            imageCentrale = new ImageView(fxImage);

        } catch (IOException e) {
            /* Probl�me lors de la lecture du fichier */
            e.printStackTrace();
        }

        return imageCentrale;
    }

    /**
     * @return valeur de document
     */
    public PDDocument getDocument() {
        return document;
    }

    /**
     * Pages courantes r�el
     * @return valeur de pageCour
     */
    public int getPagesCourReel() {
        return pageCour;
    }

    /**
     * Page courante
     * @return valeur de pageCour
     */
    public int getPagesCour() {
        return pageCour + 1;
    }

    /**
     * Permet de changer la valeur de pageCour
     * @param page La nouvelle page que l'on affiche ( en cours )
     */
    public void setPagesCour(int page) {
        pageCour = page;
    }

    /**
     * Permet D'obtenir la page ( sous forme d'ImageView ) suivante � afficher
     * Si on est page 2 -> On renvoie la page 3
     * Page courante est incr�ment�
     * @return La page suivante en ImageView
     */
    public ImageView getNextPage() {
        if(pageCour < document.getNumberOfPages()-1) {
            pageCour++;
        }
        return getPagePdfToImg(pageCour);

    }

    /**
     * Permet D'obtenir la page ( sous forme d'ImageView ) pr�c�dente � afficher
     * Si on est page 3 -> On renvoie la page 3
     * Page courante est d�cr�ment�
     * @return La page pr�c�dente en ImageView
     */
    public ImageView getPrecPage() {
        if(pageCour > 0) {
            pageCour--;
        }
        return getPagePdfToImg(pageCour);
    }

    /**
     * Permet de d�finir si la pagecourante existe
     * @return true si elle existe, sinon false
     */
    public boolean pageCorrecte() {
        if( pageCour >= 0 && pageCour < document.getNumberOfPages() ) {
            return true;
        }
        return false;

    }

    /**
     * Permet de fermer le document (this)
     */
    public void close() {
        try {
            document.close();
        } catch (IOException e) {
            // Probl�me fermeture
            e.printStackTrace();
        }
    }

}
