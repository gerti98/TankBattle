/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankbattle;

/**
 *
 * @author Gerti
 */
public class BloccoDiGioco {
    private final double posizioneX;
    private final double posizioneY;
     
    private final int tipoBlocco; //(01)
    
    private final double larghezzaBlocco;
    private final double altezzaBlocco;
    
    public BloccoDiGioco(double posX, double posY, int tipo, double larg, double alte){
        posizioneX = posX;
        posizioneY = posY;
        tipoBlocco = tipo;
        larghezzaBlocco = larg;
        altezzaBlocco = alte;
    }

    public double getPosizioneX() {
        return posizioneX;
    }

    public double getPosizioneY() {
        return posizioneY;
    }

    public int getTipoBlocco() {
        return tipoBlocco;
    }

    public double getLarghezzaBlocco() {
        return larghezzaBlocco;
    }

    public double getAltezzaBlocco() {
        return altezzaBlocco;
    }
    
    public boolean controllaCollisione(double x, double y, double lato, double velX, double velY){
        
        // (02)
        double right = x + lato + velX;
        double left = x + velX;
        double up = y + velY;
        double down = y + lato + velY;
        
        double rightBlocco = posizioneX + larghezzaBlocco;
        double leftBlocco = posizioneX;
        double upBlocco = posizioneY;
        double downBlocco = posizioneY + altezzaBlocco;
        
        // (03)
        if(((right >= leftBlocco && left < leftBlocco) || (right >= rightBlocco && left < rightBlocco)) &&
                ((down > upBlocco && down < downBlocco) || (up > upBlocco && up < downBlocco)))
            return true;
        if(((up < upBlocco && down >= upBlocco) || (up < downBlocco && down >= downBlocco)) && 
                ((left < rightBlocco && left > leftBlocco) || (right > leftBlocco && right < rightBlocco)))
            return true;
        return (left > leftBlocco && right < rightBlocco && up > upBlocco && down < downBlocco);
    }
}

/* Note:

    (01) 0: blocco normale, 1: base

    (02) Variabili di utilità, per maggior chiarezza

    (03) Controllo vari bordi per check collisione
*/
