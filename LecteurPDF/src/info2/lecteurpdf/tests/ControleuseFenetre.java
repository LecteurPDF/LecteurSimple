package info2.lecteurpdf.tests;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ControleuseFenetre {


    @FXML
    private ImageView affichageImg;

    @FXML
    void changerFichier(ActionEvent event) {

    	final FileChooser choixFichier = new FileChooser(); // Choisisseur de fichier

    	/* Extension obligatoire : .PDF*/
    	FileChooser.ExtensionFilter filtreFichierPdf = new FileChooser.ExtensionFilter("Fichier PDF (*.pdf)", "*.pdf");
    	choixFichier.getExtensionFilters().add(filtreFichierPdf);

    	/* Ouverture de la fenetre pour choix du fichier */
    	File file = choixFichier.showOpenDialog(new Stage());
    	System.out.println(file);

    	try {
    		affichageImg.setImage(OutilLecture.getPagePdfToImg(file.getAbsolutePath(), 0).getImage());
    		System.out.println("Page fini de chargé");
    	} catch(NullPointerException e) {
    		System.out.println("Aucun fichier selectionné");
    	}
    }

}
