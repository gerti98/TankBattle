package tankbattle;

import java.io.*;

/**
 *
 * @author Gerti
 */
public class TankDiGioco implements Serializable{
    // (01) 
    private final static int defaultVelEnemyOne = 2;
    private final static int defaultVelEnemyTwo = 4;
    
    public double posizioneX;
    public double posizioneY; 
    public double velocitaX;
    public double velocitaY;
    
    public int direzione; // (02)  
    public int tipoTank;  // (03) 
    
    // (04) 
    public int timerRinascita;
    public int limiteTimer;
    public int contatoreRinascite;
    public boolean inGioco;
    public int probabilitaSecondoTank = 7;
    public TankDiGioco(double posX, double posY, int dir, int tipoTank){
        posizioneX = posX;
        posizioneY = posY;
        direzione = dir;
        this.tipoTank = tipoTank;
        velocitaX = velocitaY = 0;
        
        // (05)
        if(tipoTank == 1 || tipoTank == 2){
            limiteTimer = (int)(800 * Math.random()) + 200;
            timerRinascita = limiteTimer;
            contatoreRinascite = 1;
            //System.out.println("[DEBUG] timer: " + timerRinascita + " contatore: " + contatoreRinascite + " limite: " + limiteTimer);
            inGioco = false;
        }
    }
    

    public void setTipo(){     // (06)
        int rand = (int)(Math.random()*probabilitaSecondoTank);
        if(rand == 0)
            tipoTank = 2;
        else 
            tipoTank = 1;
        
        if(probabilitaSecondoTank != 0)
            probabilitaSecondoTank--;
        
        //System.out.println("Probabilita: " + probabilitaSecondoTank + " rand: " + rand);
    }
    
    public double getPosizioneX() {
        return posizioneX;
    }

    public void setPosizioneX(double posizioneX) {
        this.posizioneX = posizioneX;
    }

    public double getPosizioneY() {
        return posizioneY;
    }

    public void setPosizioneY(double posizioneY) {
        this.posizioneY = posizioneY;
    }
    
    public int getDirezione() {
        return direzione;
    }

    public void setDirezione(int direzione) {
        this.direzione = direzione;
    }

    public double getVelocitaX() {
        return velocitaX;
    }
    
    
    public double getVelocitaY() {
        return velocitaY;
    }
    
    public int getTipoTank(){
        return tipoTank;
    }

    public void setVelocitaX(double velocitaX) {
        if(velocitaX != 0 && tipoTank == 1)
            this.velocitaX = defaultVelEnemyOne * (velocitaX/3);
        else if(velocitaX != 0 && tipoTank == 2)
            this.velocitaX = defaultVelEnemyTwo * (velocitaX/3);
        else
            this.velocitaX = velocitaX;
    }

    public void setVelocitaY(double velocitaY) {
        if(velocitaY != 0 && tipoTank == 1)
            this.velocitaY = defaultVelEnemyOne * (velocitaY/3);
        else if(velocitaY != 0 && tipoTank == 2)
            this.velocitaY = defaultVelEnemyTwo * (velocitaY/3);
        else
            this.velocitaY = velocitaY;
    }

    public boolean isInGioco() {
        return inGioco;
    }

    public void setInGioco(boolean inGioco) {
        this.inGioco = inGioco;
    }
    
    public boolean controllaCollisione(double x, double y, double lato, double velX, double velY, double larg, double alt){
        // (07) 
        double right = x + lato + velX;
        double left = x + velX;
        double up = y + velY;
        double down = y + lato + velY;
        
        double rightBlocco = posizioneX + larg;
        double leftBlocco = posizioneX;
        double upBlocco = posizioneY;
        double downBlocco = posizioneY + alt;
        
        if(((right >= leftBlocco && left < leftBlocco) || (right >= rightBlocco && left < rightBlocco)) 
                && ((down > upBlocco && down < downBlocco) || (up > upBlocco && up < downBlocco)))
            return true;
        if(((up < upBlocco && down >= upBlocco) || (up < downBlocco && down >= downBlocco)) 
                && ((left < rightBlocco && left > leftBlocco) || (right > leftBlocco && right < rightBlocco)))
            return true;
        return (left > leftBlocco && right < rightBlocco && up > upBlocco && down < downBlocco);
    }

    public int getTimerRinascita() {
        return timerRinascita--;
    }
    
    public void setTimerRinascita(){ // (08)
        contatoreRinascite++;
        timerRinascita = (int)((limiteTimer/contatoreRinascite)*1.5);
        //System.out.println("[DEBUG] timer: " + timerRinascita + " contatore: " + contatoreRinascite + " limite: " + limiteTimer);
    }
        
}

/* Note:
    (01) Variabili di configurazione per velocità tank

    (02) 0: su, 3: destra, 6: giu, 9: sinistra
    
    (03) tipo : 0 (tank giocatore), 1 (tank nemico)

    (04) Timer per respawn

    (05) configurazione TankNemico

    (06) Gestisce tipologia tankNemico alla rinascita

    (07) Definizione variabili di utilità per facilitare lettura condizioni

    (08) Gestisce definizione timer rinascita, dopo ogni abbattimento di Tank
*/