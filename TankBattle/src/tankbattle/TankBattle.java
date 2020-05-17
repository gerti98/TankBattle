package tankbattle;

import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 *
 * @author Gerti
 */
public class TankBattle extends Application {
    private VistaGiocoTankBattle vistaGioco;
    private AreaRegistrazione vistaRegistrazione;
    private AreaTabellaVisualeRanking vistaTabellaRanking;
            
    public void start(Stage primaryStage) {
        EventoDiNavigazioneGUI.invia("AVVIO");
        
        vistaRegistrazione = new AreaRegistrazione();
        vistaGioco = new VistaGiocoTankBattle();
        vistaTabellaRanking = new AreaTabellaVisualeRanking(); 
        vistaTabellaRanking.AggiornaListaRanking(DatabaseRanking.caricaRanking());
        
        // (01) 
        CacheStatoTankBattle cache = new CacheStatoTankBattle(vistaGioco, vistaRegistrazione);
        cache.caricaStatoGioco(vistaGioco, vistaRegistrazione);
        
        vistaGioco.caricaAggiornamentoStato();
        VBox vboxDestra = new VBox();
        vboxDestra.getChildren().addAll(vistaTabellaRanking, vistaRegistrazione);
        vboxDestra.setPadding(new Insets(0, 20, 0, 0));
        BorderPane root = new BorderPane();
        root.setRight(vboxDestra);
        root.setCenter(vistaGioco);
        
        Scene scene = new Scene(root, 1000, 800);
        
        vistaRegistrazione.campoUsername.setOnAction((ActionEvent e) -> { root.requestFocus(); });
        vistaGioco.setOnMousePressed((MouseEvent ev)->{vistaGioco.requestFocus();});
        vistaRegistrazione.getButton().setOnAction((ActionEvent ev)->{gestisciEventoButtonPlayPremuto();});
        vistaGioco.gameOverProperty().addListener((obs, gameOverLast, gameOverNow) -> {gestisciEventoGameOver();});
        scene.setOnKeyPressed((KeyEvent e) -> {gestisciEventoTastieraPremuta(e);});
        scene.setOnKeyReleased((KeyEvent e) -> {gestisciEventoTastieraRilasciata(e);});
        primaryStage.setOnCloseRequest((WindowEvent we) -> {gestisciEventoChiusuraFinestra();});
        root.requestFocus();
        primaryStage.setTitle("Tank Battle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    public void gestisciEventoChiusuraFinestra(){ // (02)
        
        CacheStatoTankBattle cache = new CacheStatoTankBattle(vistaGioco, vistaRegistrazione); // (03)
        cache.salvaStatoGioco();
        
        
        EventoDiNavigazioneGUI.invia("TERMINE"); // (04)
    }
    
    public void gestisciEventoButtonPlayPremuto(){
        //System.out.println("[INFO] Play Premuto");
        
        EventoDiNavigazioneGUI.invia("PLAY");
         
        boolean valido = vistaRegistrazione.usernameValido(); // (05)
        if(!valido){
            System.out.println("[ERR] Username non valido");
            return;
        }
        
        vistaGioco.gestisciInputButtonPlayPremuto();
        vistaGioco.requestFocus();
    }
    
    public void gestisciEventoTastieraPremuta(KeyEvent e){
        vistaGioco.gestisciInputTastieraPremuta(e);
    }

    public void gestisciEventoTastieraRilasciata(KeyEvent e){
        vistaGioco.gestisciInputTastieraRilasciata(e);
    }
    
    public void gestisciEventoGameOver(){
        if(vistaGioco.isGameOver()){
            //System.out.println("[DEBUG] Game Over handling");
            vistaGioco.setGameOver(false);
            String username = vistaRegistrazione.getCampoUsername(); // (06) 
            
            int score = vistaGioco.getPunteggio();  // (07)   
            DatabaseRanking.AggiungiRanking(new Partita(0, username, score));
            vistaTabellaRanking.AggiornaListaRanking(DatabaseRanking.caricaRanking());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/* Note:
    
    (01) Caricamento dati da cache eventuale

    (02) Demultiplexing eventi

    (03) Salvataggio in cache eventuale

    (04) Invia evento di navigazione

    (05) Controllo che campo textfield sia valido

    (06) Ottieni username

    (07) Ottieni punteggio
*/