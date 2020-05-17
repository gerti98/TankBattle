package tankbattle;

import javafx.beans.property.*;

/**
 *
 * @author Gerti
 */

// (01)
public class Partita {
    private final SimpleIntegerProperty posizione;
    private final SimpleStringProperty username;
    private final SimpleIntegerProperty score;
    
    Partita(int posizione, String username, int score){
        this.posizione = new SimpleIntegerProperty(posizione);
        this.username = new SimpleStringProperty(username);
        this.score = new SimpleIntegerProperty(score);
    }
    
    public String getUsername(){
        return username.get();
    }
    
    public int getScore(){
        return score.get();
    }
    
    public int getPosizione(){
        return posizione.get();
    }
}

/* Note:

    (01) Classe bean per memorizzazioe informazioni partita
*/