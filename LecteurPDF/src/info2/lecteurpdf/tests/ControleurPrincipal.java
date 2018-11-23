/*
 * ControleurPrincipal.java                            22/11/2018
 */

package info2.lecteurpdf.tests;

import java.io.File;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Permet de controler les différents objets de SceneBuilder
 * @author kevin.s
 * @version 1.0
 */
public class ControleurPrincipal {

    /** Elements du fichier pdf ouvert en cours ( fichier et page affichée en ce moment ) */
    private OutilLecture pdf = new OutilLecture();

    /** Permet d'accéder à la page précédente */
    @FXML
    private Button btnPrecPage;

    /** Permet d'accéder à la page suivante */
    @FXML
    private Button btnNextPage;

    /** Permet d'accéder à la page saisie par l'utilisateur */
    @FXML
    private TextField txbNbPage;

    /** Là où la page est affichée */
    @FXML
    private AnchorPane emplacementImage;

    /** La page que l'on affiche sous forme d'ImageView */
    @FXML
    private ImageView affichageImg;

    /**
     * Permet de définir le fichier que l'on va afficher
     * @param event
     */
    @FXML
    void changerFichier(ActionEvent event) {

        final FileChooser choixFichier = new FileChooser(); // Choisisseur de fichier

        /* Extension obligatoire : .PDF*/
        FileChooser.ExtensionFilter filtreFichierPdf = new FileChooser.ExtensionFilter("Fichier PDF (*.pdf)", "*.pdf");
        choixFichier.getExtensionFilters().add(filtreFichierPdf);

        /* Ouverture de la fenetre pour choix du fichier */
        File file = choixFichier.showOpenDialog(new Stage());

        /* Si le fichier existe, on l'affiche */
        if(file != null) {

            pdf = new OutilLecture(file.getAbsolutePath()); // On crée l'objet avec le lien du fichier pdf

            // affichageImg.imageProperty().set(null); TODO : lag sur gros fichiers
            affichageImg.setImage(pdf.getPagePdfToImg(0).getImage()); // On met l'image sur l'écran

            /* On met l'ImageView à la bonne échelle */
            resize(affichageImg);

            /* On met au centre */
            // TODO Centrer image

            //emplacementImage.getTopAnchor(affichageImg);

            //System.out.println("Page fini de chargé");
        }
    }



    /**
     * Permet d'afficher en taille réelle *1.2 le document
     * @param toResize L'image que l'on souhaite redimensionner
     * @return L'ImageView à la bonne taille
     */
    private ImageView resize(ImageView toResize) {
        toResize.setFitHeight(pdf.getDocument().getPage(pdf.getPagesCour()).getMediaBox().getHeight());
        toResize.setFitWidth(pdf.getDocument().getPage(pdf.getPagesCour()).getMediaBox().getWidth());
        return toResize;
    }


    /**
     * Permet d'afficher la précédente page
     * @param event btnPrecPage
     */
    @FXML
    void precedentePage(ActionEvent event) {
        affichageImg.setImage(pdf.getPrecPage().getImage());
        /* On met l'ImageView à la bonne échelle */
        resize(affichageImg);
        txbNbPage.setText(Integer.toString(pdf.getPagesCour()));
    }

    /**
     * Permet d'afficher la prochaine page
     * @param event btnNextPage
     */
    @FXML
    void prochainePage(ActionEvent event) {
        affichageImg.setImage(pdf.getNextPage().getImage());
        /* On met l'ImageView à la bonne échelle */
        resize(affichageImg);
        txbNbPage.setText(Integer.toString(pdf.getPagesCour()));
    }

    /**
     * Permet d'afficher la page souhaitée par l'utilisateur
     * @param event txbNbPage
     */
    @FXML
    void nbPage(ActionEvent event) {
        affichageImg.setImage(pdf.getPagePdfToImg(Integer.parseInt(txbNbPage.getText())).getImage());
        /* On met l'ImageView à la bonne échelle */
        resize(affichageImg);
        txbNbPage.setText(Integer.toString(pdf.getPagesCour()));
    }

    /**
     * Permet de fermer proprement le fichier et la fenêtre
     * @param event
     */
    @FXML
    void fermetureFenetre(ActionEvent event) {
        //TODO Gerer la fermeture général
        pdf.close();
        Platform.exit();
    }

}
