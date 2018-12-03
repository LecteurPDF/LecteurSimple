/*
 * ControleurPrincipal.java                            22/11/2018
 */

package info2.lecteurpdf;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Permet de controler les différents objets de SceneBuilder
 * @author sannac, vivier, pouzelgues, renoleau
 * @version 1.0
 */
public class ControleurPrincipal {

	/** Elements du fichier pdf ouvert en cours ( fichier et page affichée en ce moment ) */
	private OutilLecture pdf = new OutilLecture();

	private Preferences prefs = Main.prefs;

	@FXML
	private VBox parentVBox;

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

	/** Taille de la fenetre en vertical */
	private double initialX;

	/** Taille de la fenetre en horizontal */
	private double initialY;


	public void initialize() {

	}

	/**
	 * Prise de la touche clavier utilisé
	 *
	 * @param event
	 */
	@FXML
	void entreeClavier(KeyEvent event) {

		KeyCode entreeClavier = event.getCode();

		if (entreeClavier == KeyCode.getKeyCode(prefs.get("TOUCHE_PAGE_SUIVANTE", KeyCode.CHANNEL_DOWN.toString() ))) {

			affichageImg.setImage(pdf.getNextPage().getImage());
			/* On met l'ImageView à la bonne échelle */
			resize(affichageImg);
			txbNbPage.setText(Integer.toString(pdf.getPagesCour()));

		} else if(entreeClavier == KeyCode.getKeyCode(prefs.get("TOUCHE_PAGE_PRECEDENTE", KeyCode.CHANNEL_UP.toString() ))){
			//TODO
		} else {
			System.out.println("Pas de preferences");

		}

	}

	private void chargementFichier(File fich) {
		/* Si le fichier existe, on l'affiche */
		if(fich != null) {

			pdf = new OutilLecture(fich.getAbsolutePath()); // On crée l'objet avec le lien du fichier pdf

			// affichageImg.imageProperty().set(null); TODO : lag sur gros fichiers
			affichageImg.setImage(pdf.getPagePdfToImg(0).getImage()); // On met l'image sur l'écran

			/* On met l'ImageView à la bonne échelle */
			resize(affichageImg);

			/* On met au centre */
			// TODO Centrer image

			//emplacementImage.getTopAnchor(affichageImg);

			//System.out.println("Page fini de chargé");

			txbNbPage.setText(Integer.toString(pdf.getPagesCour()));

		}
	}

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
		prefs.put("DERNIER_FICHIER", file.getAbsolutePath());

		chargementFichier(file);

	}



	@FXML
	void chargerDernierFichier(ActionEvent event) {

		try {
			//TODO: Afficher liste des fichiers ouvert
			chargementFichier(new File(prefs.get("DERNIER_FICHIER", null)));
		} catch( NullPointerException e ) {

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
		affichageImg.setImage(pdf.getPagePdfToImg(Integer.parseInt(txbNbPage.getText()) - 1).getImage());
		/* On met l'ImageView à la bonne échelle */
		resize(affichageImg);
		txbNbPage.setText(Integer.toString(pdf.getPagesCour()));
	}

	/**
	 * Permet l'ouverture de la fenétre préférence
	 * @param event non utilisé
	 */
	@FXML
	void ouvrirPref(ActionEvent event) {
		//TODO: Fenetre preference
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("preference.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 300, 500);
			Stage stage = new Stage();
			stage.setTitle("Préférence - Lecteur PDF");
			stage.setScene(scene);

			stage.setResizable(false);

			/* Fenetre modale */
			stage.initOwner( parentVBox.getScene().getWindow() );
			stage.initModality( Modality.APPLICATION_MODAL );
			stage.showAndWait();
		} catch (IOException e) {
			//TODO: Voir classe Logger
			e.printStackTrace();
		}
	}

	/**
	 * Permet de prendre la valeur initial de la fenetre
	 * avant deplacement de celle ci
	 * @param event entree souris
	 */
	@FXML
	void clickPourDeplacement(MouseEvent event) {
		if (event.getButton() != MouseButton.MIDDLE) {
			initialX = event.getSceneX();
			initialY = event.getSceneY();
		}
	}

	/**
	 * Permet de suivre la souris pendant le deplacement de la fenetre
	 * @param me entree souris
	 */
	@FXML
	public void dragPourDeplacement(MouseEvent me) {
		if (me.getButton() != MouseButton.MIDDLE) {
			parentVBox.getScene().getWindow().setX(me.getScreenX() - initialX);
			parentVBox.getScene().getWindow().setY(me.getScreenY() - initialY);
		}
	}

	/**
	 * Permet d'agrandir la fenêtre
	 * @param event
	 */
	@FXML
	void agrandissementFenetre(ActionEvent event) {

		Stage stage = (Stage) parentVBox.getScene().getWindow();

		if( stage.isMaximized() ) {
			stage.setMaximized(false);
		} else {
			stage.setMaximized(true);
		}
	}

	/**
	 * Permet de fermer proprement le fichier et la fenêtre
	 * @param event
	 */
	@FXML
	void fermetureFenetre(ActionEvent event) {

		try {
			pdf.close();
		} catch( NullPointerException e) {
			System.out.println("Impossible de fermer le fichier");
		}
		Platform.exit();
	}

}
