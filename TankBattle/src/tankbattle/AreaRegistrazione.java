package tankbattle;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 *
 * @author Gerti
 */
public class AreaRegistrazione extends VBox{
    final private Button playButton; 
    final private Label titleLabel;
    final private Label usernameLabel;
    public TextField campoUsername;
    
    public AreaRegistrazione(){
        playButton = new Button("PLAY"); // (01)
        playButton.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: #4277eb; -fx-text-fill: white;");
        setMargin(playButton, new Insets(20, 0, 0, 80));
        
        campoUsername = new TextField();
        titleLabel = new Label("REGISTRAZIONE");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        titleLabel.setPadding(new Insets(10, 0, 0, 40));
        
        usernameLabel = new Label("Username");
        usernameLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        usernameLabel.setPadding(new Insets(10, 0, 10, 80));
        
        getChildren().addAll(titleLabel, usernameLabel, campoUsername, playButton);
        setPadding(new Insets(0, 20, 20, 20)); // (02) 
    }
    
    
    public boolean usernameValido(){ // (03)
        //System.out.println("[DEBUG] " + campoUsername.getText());
        
        if(campoUsername.getText().equals("")){ // (04)
            //System.out.println("[DEBUG] Username non valido");
            return false;
        }
        return true;
    }
    
    public String getCampoUsername(){
        return campoUsername.getText();
    }
    
    public void setCampoUsername(String s){
        campoUsername.setText(s);
    }
    
    public Button getButton(){ // (05) 
        return playButton;
    }
}

/* Note:

    (01) Inizializza elementi area registrazione

    (02) Per gestione padding utilizzata classse Insets 

    (03) Controlla se è stato inserito username nel textfield

    (04) Check nome non inserito

    (05) Getter per implementare event handler bottone
*/
