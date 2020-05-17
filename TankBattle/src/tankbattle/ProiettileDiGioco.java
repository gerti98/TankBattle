package tankbattle;

/**
 *
 * @author Gerti
 */
public class ProiettileDiGioco {
    private double posizioneX;
    private double posizioneY;
    private int tipoProiettile; // (01)
    private double velocitaX;
    private double velocitaY;
    private boolean attivo; // (02)
    
    public ProiettileDiGioco(double posX, double posY, double velX, double velY, int tipo, boolean att){
        posizioneX = posX;
        posizioneY = posY;
        velocitaX = velX;
        velocitaY = velY;
        tipoProiettile = tipo;
        attivo = att;
    }

    public double getPosizioneX() {
        return posizioneX;
    }

    public double getPosizioneY() {
        return posizioneY;
    }

    public double getVelocitaX() {
        return velocitaX;
    }

    public double getVelocitaY() {
        return velocitaY;
    }

    public int getTipoProiettile() {
        return tipoProiettile;
    }

    public void setPosizioneX(double posizioneX) {
        this.posizioneX = posizioneX;
    }

    public void setPosizioneY(double posizioneY) {
        this.posizioneY = posizioneY;
    }

    public void setVelocitaX(double velocitaX) {
        this.velocitaX = velocitaX;
    }

    public void setVelocitaY(double velocitaY) {
        this.velocitaY = velocitaY;
    }

    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }
      
}

/* Note:
    
    (01) 0: proiettile giocatore, 1: proiettile nemico

    (02) true se proiettile è in gioco
*/