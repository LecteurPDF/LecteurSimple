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

public class ControleurPrincipal {

	private OutilLecture pdf = new OutilLecture();

    @FXML
    private Button btnPrecPage;

    @FXML
    private Button btnNextPage;

    @FXML
    private TextField txbNbPage;

    @FXML
    private AnchorPane emplacementImage;

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

    	if(file != null) {

    		pdf = new OutilLecture(file.getAbsolutePath());

    		affichageImg.setImage(pdf.getPagePdfToImg(0).getImage());

    		//affichageImg.fitWidthProperty().bind(emplacementImage.widthProperty());

    	    //pane.setCenter(affichageImg);

    		System.out.println("Page fini de chargé");
    	}
    }

    @FXML
    void precedentePage(ActionEvent event) {
    	affichageImg.setImage(pdf.getPrecPage().getImage());
    	txbNbPage.setText(Integer.toString(pdf.getPagesCour()));
    }

    @FXML
    void prochainePage(ActionEvent event) {
    	affichageImg.setImage(pdf.getNextPage().getImage());
    	txbNbPage.setText(Integer.toString(pdf.getPagesCour()));
    }

    @FXML
    void nbPage(ActionEvent event) {
    	affichageImg.setImage(pdf.getPagePdfToImg(Integer.parseInt(txbNbPage.getText())).getImage());
    	txbNbPage.setText(Integer.toString(pdf.getPagesCour()));
    }

    @FXML
    void fermetureFenetre(ActionEvent event) {
    	//TODO: Gerer la fermeture général
    	pdf.close();
    	Platform.exit();
    }

}
