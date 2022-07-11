/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.PlayerDelta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnTopPlayer"
    private Button btnTopPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="btnDreamTeam"
    private Button btnDreamTeam; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="txtGoals"
    private TextField txtGoals; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	String m = txtGoals.getText();
    	try {
    		double media = Double.parseDouble(m);
        	txtResult.appendText(model.creaGrafo(media));

    	}catch(NumberFormatException e) {
    		txtResult.appendText("Inserire un numero decimale valido minore di uno");
    		return;
    	}
    }

    @FXML
    void doDreamTeam(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	if(!model.isCreato()) {
    		txtResult.appendText("Creare prima il grafo");
    		return;
    	}
    	
    	String k = txtK.getText();
    	try {
    		int kScelto = Integer.parseInt(k);
    		List<Player> best = model.cercaDreamTeam(kScelto);
    		for(Player p : best) {
    			txtResult.appendText(p.toString()+"\n");
    		}
    		txtResult.appendText("GRADO: " +model.getMaxGrado()+"\n");
    		
    	}catch(NumberFormatException e) {
    		txtResult.appendText("Inserire un numero intero minore di cinque");
    		return;
    	}

    }

    @FXML
    void doTopPlayer(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	if(!model.isCreato()) {
    		txtResult.appendText("Creare prima il grafo");
    		return;
    	}
    	
    	List<PlayerDelta> top = model.getGiocatoreMigliore();
    	
    	txtResult.appendText("TOP PLAYER: " + top.get(0).getP1().toString()+"\n"+"\n");
    	txtResult.appendText("AVVERSARI BATTUTI:" +"\n");
    	List<PlayerDelta> avversari = model.getAvversariBattuti(top.get(0).getP1());
    	for(PlayerDelta p : avversari) {
    		txtResult.appendText(p.getP1() + " | " + p.getDelta() + "\n");
    	}
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTopPlayer != null : "fx:id=\"btnTopPlayer\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGoals != null : "fx:id=\"txtGoals\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
