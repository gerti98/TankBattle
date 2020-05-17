package tankbattle;

import javafx.animation.*;
import javafx.beans.property.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.shape.*;
import javafx.util.*;
/**
 *
 * @author Gerti
 */
public class VistaGiocoTankBattle extends Group{
    
    // (01)
    final private static int defaultVel = 3;
    final private static int defaultVelProiettile = 8;
    private static final Image sfondo = new Image("file:../../img/sfondo.png");
    private static final Image tankImage = new Image("file:../../img/icon.png");
    private static final Image tankEnemyOneImage = new Image("file:../../img/enemy.png");
    private static final Image tankEnemyTwoImage = new Image("file:../../img/enemy3.png");
    private static final Image bloccoImage = new Image("file:../../img/blocco.png");

    // (02)
    private TankDiGioco tankGiocatore;
    private TankDiGioco[] tankNemico;
    private BloccoDiGioco[] ostacoloMuro;
    private BloccoDiGioco baseGiocatore;
    private ProiettileDiGioco proiettileGiocatore;
    private ProiettileDiGioco[] proiettileNemico;
    
    // (03) 
    private ImageView spriteTankGiocatore;
    private ImageView[] spriteTankNemico;
    private Circle spriteProiettileGiocatore;    
    private Circle[] spriteProiettileNemico;
    private final AnimationTimer animazioneTimer;
    private boolean animazionePartita;
    final private Label titleGame;
    final private Label labelPunteggio;
    public ImageView sfondoGioco;    
    private int punteggio;
    
    
    private final BooleanProperty gameOver = new SimpleBooleanProperty(); // (04)
    private boolean giocoIniziato = false;
    private boolean left, right, up, down;
    
    public VistaGiocoTankBattle(){
        labelPunteggio = new Label("Player Score: " + punteggio);
        titleGame = new Label("TANK BATTLE");
        titleGame.setLayoutX(240);
        titleGame.setLayoutY(-80);
        titleGame.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        labelPunteggio.setLayoutY(-40);
        labelPunteggio.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        sfondoGioco = new ImageView();
        sfondoGioco.setImage(sfondo);
    
        animazionePartita = false;    
        tankNemico = new TankDiGioco[4];
        spriteTankNemico = new ImageView[4];
        proiettileNemico = new ProiettileDiGioco[4];
        spriteProiettileNemico = new Circle[4];
        punteggio = 0;
        
        animazioneTimer = new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                gestisciAnimationTimer();
            }
        };
        getChildren().addAll(titleGame, labelPunteggio, sfondoGioco);
    }
    
    public void gestisciAnimationTimer(){
        boolean collisione = controllaCollisioneConBlocchi(tankGiocatore.getPosizioneX(), tankGiocatore.getPosizioneY(), tankImage.getWidth(), tankGiocatore.getVelocitaX(), tankGiocatore.getVelocitaY());
        int collisioneConTank = controllaCollisioneConTank(tankGiocatore.getPosizioneX(), tankGiocatore.getPosizioneY(), tankImage.getWidth(), tankGiocatore.getVelocitaX(), tankGiocatore.getVelocitaY());
        boolean collisioneGameOver = false; 
        double largBlocco = bloccoImage.getWidth();

        if(collisioneConTank != -1)
            gestisciGameOver();
        aggiornaPosizioneTankGiocatore(collisione);

        for(int i=0; i<4; i++){
            collisioneGameOver = gestisciAggiornamentoPosizioneElementiNemici(i, collisioneGameOver);

            if(proiettileNemico[i].isAttivo()){ // (05) 
                collisione = controllaCollisioneConBlocchi(proiettileNemico[i].getPosizioneX(), proiettileNemico[i].getPosizioneY(), spriteProiettileNemico[i].getRadius(), proiettileNemico[i].getVelocitaX(), proiettileNemico[i].getVelocitaY());
                boolean collisioneConTankGiocatore = controllaCollisioneConTankGiocatore(proiettileNemico[i].getPosizioneX(), proiettileNemico[i].getPosizioneY(), spriteProiettileNemico[i].getRadius(), proiettileNemico[i].getVelocitaX(), proiettileNemico[i].getVelocitaY());
                collisioneGameOver = controllaCollisioneConBase(proiettileNemico[i].getPosizioneX(), proiettileNemico[i].getPosizioneY(), spriteProiettileNemico[i].getRadius(), proiettileNemico[i].getVelocitaX(), proiettileNemico[i].getVelocitaY()); 
                aggiornaPosizioneProiettileNemico(i, collisioneGameOver, collisione, collisioneConTankGiocatore, collisioneConTank);
            }
        }

        gestisciCollisioneProiettileGiocatore(collisioneGameOver, collisione, collisioneConTank);
    }
    
    public void aggiornaPosizioneProiettileNemico(int i, boolean collisioneGameOver, boolean collisione, boolean collisioneConTankGiocatore, int collisioneConTank){
        if(collisioneGameOver || collisioneConTankGiocatore){
                    gestisciGameOver();
        }

        else if(!collisione && (collisioneConTank == -1) && (proiettileNemico[i].getPosizioneX() - spriteProiettileNemico[i].getRadius() + proiettileNemico[i].getVelocitaX()) > 4
            && (proiettileNemico[i].getPosizioneX() + proiettileNemico[i].getVelocitaX()) < sfondo.getWidth() - spriteProiettileNemico[i].getRadius()*2 - 4
                && (proiettileNemico[i].getPosizioneY() + proiettileNemico[i].getVelocitaY()) > 6
            && (proiettileNemico[i].getPosizioneY() + proiettileNemico[i].getVelocitaY()) < sfondo.getHeight() - spriteProiettileNemico[i].getRadius()*2 - 4){

            proiettileNemico[i].setPosizioneX(proiettileNemico[i].getPosizioneX() + proiettileNemico[i].getVelocitaX());
            spriteProiettileNemico[i].setCenterX(proiettileNemico[i].getPosizioneX() + proiettileNemico[i].getVelocitaX());
            proiettileNemico[i].setPosizioneY(proiettileNemico[i].getPosizioneY() + proiettileNemico[i].getVelocitaY());
            spriteProiettileNemico[i].setCenterY(proiettileNemico[i].getPosizioneY() + proiettileNemico[i].getVelocitaY());
        } else {
            proiettileNemico[i].setAttivo(false);
            getChildren().remove(spriteProiettileNemico[i]);
        }
    }
    public boolean gestisciAggiornamentoPosizioneElementiNemici(int i, boolean collisioneGameOver){
        double largBlocco = bloccoImage.getWidth();
        if(tankNemico[i].isInGioco()){
            collisioneGameOver = controllaCollisioneConBase(tankNemico[i].getPosizioneX(), tankNemico[i].getPosizioneY(), tankImage.getWidth(), tankNemico[i].getVelocitaX(), tankNemico[i].getVelocitaY());
            if(collisioneGameOver)
                gestisciGameOver();

            int flagSparo = aggiornaPosizioneTankNemico(i);
            aggiornaPosizioneProiettileNemico(flagSparo, i);
        } else {
            int timer = tankNemico[i].getTimerRinascita();
            if(timer == 0){
                tankNemico[i].setTimerRinascita();
                tankNemico[i].setInGioco(true);
                tankNemico[i].setTipo();
                tankNemico[i].setDirezione(6);
                tankNemico[i].setPosizioneX(7 + largBlocco*4*i);
                tankNemico[i].setPosizioneY(7);
                spriteTankNemico[i] = new ImageView();
                spriteTankNemico[i].setImage(getProperImage(tankNemico[i].getTipoTank()));
                spriteTankNemico[i].setX(7 + largBlocco*4*i);
                spriteTankNemico[i].setY(7);
                getChildren().add(spriteTankNemico[i]);
            }
        }
        return collisioneGameOver;
    }
    
    public void aggiornaPosizioneTankGiocatore(boolean collisione){                    
        if(!collisione && (tankGiocatore.getPosizioneX() + tankGiocatore.getVelocitaX()) >= 0 
                && (tankGiocatore.getPosizioneX() + tankGiocatore.getVelocitaX()) <= sfondo.getWidth() - tankImage.getWidth()){
            double x = tankGiocatore.getPosizioneX();
            double velX = tankGiocatore.getVelocitaX();
            tankGiocatore.setPosizioneX(x + velX);
            spriteTankGiocatore.setX(x + velX);                        
        }

        if(!collisione && (tankGiocatore.getPosizioneY() + tankGiocatore.getVelocitaY()) >= 0
                && (tankGiocatore.getPosizioneY() + tankGiocatore.getVelocitaY()) <= sfondo.getHeight() - tankImage.getHeight()){
            double y = tankGiocatore.getPosizioneY();
            double velY = tankGiocatore.getVelocitaY();
            tankGiocatore.setPosizioneY(y + velY);
            spriteTankGiocatore.setY(y + velY); 
        }
    }
    
    public int aggiornaPosizioneTankNemico(int i){
        int flagSparo = -1;
        double largBlocco = bloccoImage.getWidth();
        double x = tankNemico[i].getPosizioneX();
        double y = tankNemico[i].getPosizioneY();
        flagSparo = controllaPossibilitaSparo(tankNemico[i].getDirezione(), x, y);
        
        if(y + tankNemico[i].getVelocitaY() <=  largBlocco*12 + 10){ // (06)
            tankNemico[i].setVelocitaY(defaultVel);
            double velY = tankNemico[i].getVelocitaY();
            tankNemico[i].setPosizioneY(y + velY);
            spriteTankNemico[i].setY(y + velY);   
        } else if(x + tankNemico[i].getVelocitaX() <=  largBlocco*6 + 10){ // (07) 
            RotateTransition rt = new RotateTransition(Duration.millis(1), spriteTankNemico[i]);
            rt.setFromAngle(270);
            rt.play();
            tankNemico[i].setDirezione(3);
            tankNemico[i].setVelocitaY(0);
            tankNemico[i].setVelocitaX(defaultVel);
            double velX = tankNemico[i].getVelocitaX();
            tankNemico[i].setPosizioneX(x + velX);
            spriteTankNemico[i].setX(x + velX);
        } else if(x + tankNemico[0].getVelocitaX() >=  largBlocco*6 + 10){ // (08)
            RotateTransition rt = new RotateTransition(Duration.millis(1), spriteTankNemico[i]);
            rt.setFromAngle(90);
            rt.play();
            tankNemico[i].setDirezione(9);
            tankNemico[i].setVelocitaY(0);
            tankNemico[i].setVelocitaX(-defaultVel);
            double velX = tankNemico[i].getVelocitaX();
            tankNemico[i].setPosizioneX(x + velX);
            spriteTankNemico[i].setX(x + velX);
        }
        
        return flagSparo;
    }
    
    public void aggiornaPosizioneProiettileNemico(int flagSparo, int i){
        if(flagSparo == 1 && !proiettileNemico[i].isAttivo()){
            // (09)
            int dir = tankNemico[i].getDirezione();
            double x = tankNemico[i].getPosizioneX() + tankImage.getWidth()/2;
            double y = tankNemico[i].getPosizioneY() + tankImage.getHeight()/2;

            proiettileNemico[i].setPosizioneX(x);
            proiettileNemico[i].setPosizioneY(y);
            proiettileNemico[i].setAttivo(true);
            spriteProiettileNemico[i] = new Circle(x, y, 2);
            spriteProiettileNemico[i].setFill(javafx.scene.paint.Color.WHITE);
            getChildren().add(spriteProiettileNemico[i]);
            if(dir == 0){
                proiettileNemico[i].setVelocitaY(-defaultVelProiettile);
                proiettileNemico[i].setVelocitaX(0);
            }
            if(dir == 3){ 
                proiettileNemico[i].setVelocitaX(defaultVelProiettile);
                proiettileNemico[i].setVelocitaY(0);
            }
            if(dir == 6){
                proiettileNemico[i].setVelocitaY(defaultVelProiettile);
                proiettileNemico[i].setVelocitaX(0);
            }
            if(dir == 9){
                proiettileNemico[i].setVelocitaX(-defaultVelProiettile);
                proiettileNemico[i].setVelocitaY(0);
            }
        }
    }
    
    public void gestisciCollisioneProiettileGiocatore(boolean collisioneGameOver, boolean collisione, int collisioneConTank){
        if(proiettileGiocatore.isAttivo()){
            collisione = controllaCollisioneConBlocchi(proiettileGiocatore.getPosizioneX(), proiettileGiocatore.getPosizioneY(), spriteProiettileGiocatore.getRadius(), proiettileGiocatore.getVelocitaX(), proiettileGiocatore.getVelocitaY());
            collisioneConTank = controllaCollisioneConTank(proiettileGiocatore.getPosizioneX(), proiettileGiocatore.getPosizioneY(), spriteProiettileGiocatore.getRadius(), proiettileGiocatore.getVelocitaX(), proiettileGiocatore.getVelocitaY());
            collisioneGameOver = controllaCollisioneConBase(proiettileGiocatore.getPosizioneX(), proiettileGiocatore.getPosizioneY(), spriteProiettileGiocatore.getRadius(), proiettileGiocatore.getVelocitaX(), proiettileGiocatore.getVelocitaY()); 

            if(collisioneGameOver)
                gestisciGameOver();
           
            else if(!collisione && (collisioneConTank == -1) && (proiettileGiocatore.getPosizioneX() - spriteProiettileGiocatore.getRadius() + proiettileGiocatore.getVelocitaX()) > 4
                && (proiettileGiocatore.getPosizioneX() + proiettileGiocatore.getVelocitaX()) < sfondo.getWidth() - spriteProiettileGiocatore.getRadius()*2 - 4
                    && (proiettileGiocatore.getPosizioneY() + proiettileGiocatore.getVelocitaY()) > 6
                && (proiettileGiocatore.getPosizioneY() + proiettileGiocatore.getVelocitaY()) < sfondo.getHeight() - spriteProiettileGiocatore.getRadius()*2 - 4){

                proiettileGiocatore.setPosizioneX(proiettileGiocatore.getPosizioneX() + proiettileGiocatore.getVelocitaX());
                spriteProiettileGiocatore.setCenterX(proiettileGiocatore.getPosizioneX() + proiettileGiocatore.getVelocitaX());
                proiettileGiocatore.setPosizioneY(proiettileGiocatore.getPosizioneY() + proiettileGiocatore.getVelocitaY());
                spriteProiettileGiocatore.setCenterY(proiettileGiocatore.getPosizioneY() + proiettileGiocatore.getVelocitaY());
            } else {
                proiettileGiocatore.setAttivo(false);
                getChildren().remove(spriteProiettileGiocatore);
                if(collisioneConTank != -1){
                    punteggio++;
                    labelPunteggio.setText("Player Score: " + punteggio);
                    tankNemico[collisioneConTank].setInGioco(false);
                    tankNemico[collisioneConTank].setPosizioneX(-10);
                    tankNemico[collisioneConTank].setPosizioneY(-10);
                    getChildren().remove(spriteTankNemico[collisioneConTank]); 
                }
            }   
        }
    }
    
    public void caricaAggiornamentoStato(){
        double largBlocco = bloccoImage.getWidth();
        if(giocoIniziato == true){          
            labelPunteggio.setText("Player Score: " + punteggio);
            
            // (10)
            baseGiocatore = new BloccoDiGioco(largBlocco * 6, largBlocco * 12, 0,  largBlocco, largBlocco);
            proiettileGiocatore = new ProiettileDiGioco(-1, -1, 0, 0, 0, false);
            spriteTankGiocatore = new ImageView("file:../../img/icon.png");
            spriteTankGiocatore.setX(tankGiocatore.getPosizioneX());
            spriteTankGiocatore.setY(tankGiocatore.getPosizioneY());
            caricaDirezione(tankGiocatore.getDirezione());
            getChildren().add(spriteTankGiocatore);
            
            for(int i=0; i<4; i++){
                proiettileNemico[i] = new ProiettileDiGioco(-1, -1, 0, 0, i, false);
                spriteTankNemico[i] = new ImageView();
                if(tankNemico[i].isInGioco()){
                    spriteTankNemico[i].setImage(getProperImage(tankNemico[i].getTipoTank()));
                    getChildren().add(spriteTankNemico[i]);
                }
                spriteTankNemico[i].setX(tankNemico[i].getPosizioneX());
                spriteTankNemico[i].setY(tankNemico[i].getPosizioneY());
            }
            ostacoloMuro = new BloccoDiGioco[9];

            // (11)
            for(int i=0; i<3; i++){
                ostacoloMuro[i] = new BloccoDiGioco(largBlocco * 4 * i + largBlocco, largBlocco * 2, 1, largBlocco * 3, largBlocco * 3);
                ostacoloMuro[i+3] = new BloccoDiGioco(largBlocco * 4 * i + largBlocco, largBlocco * 6, 1, largBlocco * 3, largBlocco); 
                ostacoloMuro[i+6] = new BloccoDiGioco(largBlocco * 4 * i + largBlocco, largBlocco * 8, 1, largBlocco * 3, largBlocco * 3);
            }            
            animazionePartita = true;
            animazioneTimer.start();
        }
    }
    
    private int controllaPossibilitaSparo(int dir, double nemicoX, double nemicoY){ // (12)
        double posX = tankGiocatore.getPosizioneX();
        double posY = tankGiocatore.getPosizioneY();
        double scarto = 25;
        int probabilitaSparo = (int)(50*Math.random());
        
        if(probabilitaSparo != 1)
            return 0;
        probabilitaSparo = (int)(3*Math.random());
        if(dir == 6){
            if(posX <= nemicoX + scarto && posX >= nemicoX - scarto && posY > nemicoY)
                return 1;
        } else if(dir == 3){
            if(posY <= nemicoY + scarto && posY >= nemicoY - scarto && posX > nemicoX)
                return 1;
            else {  // (13)
                if(probabilitaSparo == 1)
                    return 1;
            }
        } else if(dir == 9){
            if(posY <= nemicoY + scarto && posY >= nemicoY - scarto && posX < nemicoX)
                return 1;
            else{ // (14) 
                if(probabilitaSparo == 1)
                    return 1;
            }
        }
        return 0;
    }
    
    public void gestisciInputButtonPlayPremuto(){
        double largBlocco = bloccoImage.getWidth();
        giocoIniziato = true;
        
        // (16) 
        if(getChildren().contains(spriteTankGiocatore))
            getChildren().remove(spriteTankGiocatore);
        
        if(getChildren().contains(spriteProiettileGiocatore))
            getChildren().remove(spriteProiettileGiocatore);
        
        for(int i=0; i<4; i++){
            if(getChildren().contains(spriteTankNemico[i]))
                getChildren().remove(spriteTankNemico[i]);
            if(getChildren().contains(spriteProiettileNemico[i]))
                getChildren().remove(spriteProiettileNemico[i]);
        }
        
        if(animazionePartita == true)  
            animazioneTimer.stop();
        
        inizializzaGioco();
     
        animazionePartita = true;
        animazioneTimer.start();
    }
    
    
    private void inizializzaGioco(){
        double largBlocco = bloccoImage.getWidth();
        punteggio = 0;
        labelPunteggio.setText("Player Score: " + punteggio);
        baseGiocatore = new BloccoDiGioco(largBlocco * 6, largBlocco * 12, 0,  largBlocco, largBlocco);
        
        left = right = up = down = false;
        
        // (17)
        tankGiocatore = new TankDiGioco(largBlocco * 7 + 10, largBlocco * 12 + 10, 10, 0);
        tankGiocatore.setDirezione(3);
        proiettileGiocatore = new ProiettileDiGioco(-1, -1, 0, 0, 0, false);
        spriteTankGiocatore = new ImageView("file:../../img/icon.png");
        getChildren().add(spriteTankGiocatore);
        
        for(int i=0; i<4; i++){
            proiettileNemico[i] = new ProiettileDiGioco(-1, -1, 0, 0, i, false);
            tankNemico[i] = new TankDiGioco(-10, -10, 10, 1);
            tankNemico[i].setDirezione(6);
            spriteTankNemico[i] = new ImageView();
            spriteTankNemico[i].setX(tankNemico[i].getPosizioneX());
            spriteTankNemico[i].setY(tankNemico[i].getPosizioneY());
        }
        
        ostacoloMuro = new BloccoDiGioco[9];
        
        // (19) 
        for(int i=0; i<3; i++){
            ostacoloMuro[i] = new BloccoDiGioco(largBlocco * 4 * i + largBlocco, largBlocco * 2, 1, largBlocco * 3, largBlocco * 3);
            ostacoloMuro[i+3] = new BloccoDiGioco(largBlocco * 4 * i + largBlocco, largBlocco * 6, 1, largBlocco * 3, largBlocco); 
            ostacoloMuro[i+6] = new BloccoDiGioco(largBlocco * 4 * i + largBlocco, largBlocco * 8, 1, largBlocco * 3, largBlocco * 3);
        }
    }
    
    private boolean controllaCollisioneConBlocchi(double x, double y, double width, double velX, double velY){
        boolean work;
        
        // (20) 
        for(int i=0; i<9; i++){
            work = ostacoloMuro[i].controllaCollisione(x, y, width, velX, velY);
            if(work)
                return true;
        }
        
        work = baseGiocatore.controllaCollisione(x, y, width, velX, velY); // (21)
        
        return work;
    }
    
    
     
    private int controllaCollisioneConTank(double x, double y, double width, double velX, double velY){ // (22)
        boolean work;
        
        for(int i=0; i<4; i++){
            work = tankNemico[i].controllaCollisione(x, y, width, velX, velY, tankImage.getWidth(), tankImage.getHeight());
            if(work)
                return i;
        }
        
        return -1;
    }
    
    private boolean controllaCollisioneConTankGiocatore(double x, double y, double width, double velX, double velY){
        return tankGiocatore.controllaCollisione(x, y, width, velX, velY, tankImage.getWidth(), tankImage.getHeight());
    }
    
    private boolean controllaCollisioneConBase(double x, double y, double width, double velX, double velY){
        return baseGiocatore.controllaCollisione(x, y, width, velX, velY);
    }
    
    public Image getProperImage(int tipoTank){
        // (23) 
        if(tipoTank == 1)
            return tankEnemyOneImage;
        else
            return tankEnemyTwoImage;
    }
    
    public void gestisciInputTastieraPremuta(KeyEvent e){
        if(giocoIniziato == false)
            return;        
        
        RotateTransition rt = new RotateTransition(Duration.millis(1), spriteTankGiocatore);
        
        if (e.getCode() == KeyCode.A) {
            tankGiocatore.setVelocitaX(-1*defaultVel);
            tankGiocatore.setDirezione(9);
            left = true;
            rt.setFromAngle(180);            
        } else if(e.getCode() == KeyCode.D){
            tankGiocatore.setVelocitaX(defaultVel);
            tankGiocatore.setDirezione(3);
            right = true;
            rt.setFromAngle(0);
        } else if(e.getCode() == KeyCode.S){
            tankGiocatore.setVelocitaY(defaultVel);
            tankGiocatore.setDirezione(6);
            down = true;
            rt.setFromAngle(90);
        } else if(e.getCode() == KeyCode.W){
            tankGiocatore.setVelocitaY(-1*defaultVel);
            tankGiocatore.setDirezione(0);
            up = true;
            rt.setFromAngle(270);
        } else if(e.getCode() == KeyCode.J){
            creaProiettileGiocatore();
        }
        rt.play();
    }
    
    public void gestisciInputTastieraRilasciata(KeyEvent e){
        if(giocoIniziato == false)
            return;
        if (e.getCode() == KeyCode.A) {
            tankGiocatore.setVelocitaX(0);
            left = false;
            gestisciCambioDirezione(9);
        } else if(e.getCode() == KeyCode.D){
            tankGiocatore.setVelocitaX(0);
            right = false;
            gestisciCambioDirezione(3);
        } else if(e.getCode() == KeyCode.S){
            tankGiocatore.setVelocitaY(0);
            down = false;
            gestisciCambioDirezione(6);
        } else if(e.getCode() == KeyCode.W){
            tankGiocatore.setVelocitaY(0);        
            up = false;
            gestisciCambioDirezione(0);
        } 
    }
    
    public void gestisciCambioDirezione(int direzioneTastoLasciato){
        RotateTransition rt = new RotateTransition(Duration.millis(1), spriteTankGiocatore);
        if(direzioneTastoLasciato == tankGiocatore.getDirezione()){
            if(up){
                tankGiocatore.setDirezione(0);
                rt.setFromAngle(270);
            } else if(right){
                tankGiocatore.setDirezione(3);
                rt.setFromAngle(0);
            } else if(down){
                tankGiocatore.setDirezione(6);
                rt.setFromAngle(90);
            } else if(left){
                tankGiocatore.setDirezione(9);
                rt.setFromAngle(180);
            }  
            rt.play();
        }
    }
    
    public void caricaDirezione(int direzione){
        RotateTransition rt = new RotateTransition(Duration.millis(1), spriteTankGiocatore);
        if(direzione == 0){
            tankGiocatore.setDirezione(0);
            rt.setFromAngle(270);
        } else if(direzione == 3){
            tankGiocatore.setDirezione(3);
            rt.setFromAngle(0);
        } else if(direzione == 6){
            tankGiocatore.setDirezione(6);
            rt.setFromAngle(90);
        } else if(direzione == 9){
            tankGiocatore.setDirezione(9);
            rt.setFromAngle(180);
        }  
        rt.play();
    }
    
    public void setXTransition(float x, ImageView rect){
        TranslateTransition tt = new TranslateTransition(Duration.millis(1), rect);        
    }
    
    
    private void creaProiettileGiocatore(){
        if(proiettileGiocatore.isAttivo() == false){ // (24)
            int dir = tankGiocatore.getDirezione();
            double x = tankGiocatore.getPosizioneX() + tankImage.getWidth()/2;
            double y = tankGiocatore.getPosizioneY() + tankImage.getHeight()/2;
            
            proiettileGiocatore.setPosizioneX(x);
            proiettileGiocatore.setPosizioneY(y);
            proiettileGiocatore.setAttivo(true);
            spriteProiettileGiocatore = new Circle(x, y, 2);
            spriteProiettileGiocatore.setFill(javafx.scene.paint.Color.WHITE);
            getChildren().add(spriteProiettileGiocatore);
            if(dir == 0){
                proiettileGiocatore.setVelocitaY(-defaultVelProiettile);
                proiettileGiocatore.setVelocitaX(0);
            }
            if(dir == 3){ 
                proiettileGiocatore.setVelocitaX(defaultVelProiettile);
                proiettileGiocatore.setVelocitaY(0);
            }
            if(dir == 6){
                proiettileGiocatore.setVelocitaY(defaultVelProiettile);
                proiettileGiocatore.setVelocitaX(0);
            }
            if(dir == 9){
                proiettileGiocatore.setVelocitaX(-defaultVelProiettile);
                proiettileGiocatore.setVelocitaY(0);
            }
        }
    }
    
    private void gestisciGameOver(){
        //System.out.println("[DEBUG] Game Over");
        animazioneTimer.stop();
        animazionePartita = false;
        giocoIniziato = false;
        setGameOver(true);
    }
    
    public BooleanProperty gameOverProperty() {
        return gameOver;
    }
    
    public boolean isGameOver() {
        return gameOver.get();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver.set(gameOver);
    }
    
    public int getPunteggio(){
        return punteggio;
    }    

    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }
    
    public TankDiGioco getTankGiocatore() {
        return tankGiocatore;
    }

    public void setTankGiocatore(TankDiGioco tankGiocatore) {
        this.tankGiocatore = tankGiocatore;
    }

    public TankDiGioco[] getTankNemico() {
        return tankNemico;
    }

    public void setTankNemico(TankDiGioco[] tankNemico) {
        this.tankNemico = tankNemico;
    }

    public boolean isGiocoIniziato() {
        return giocoIniziato;
    }

    public void setGiocoIniziato(boolean giocoIniziato) {
        this.giocoIniziato = giocoIniziato;
    }
    
}

/* Note:
        
    (01) Dati da caricare in memoria statica 
    
    (02) Configurazione Model

    (03) View

    (04) Logica per gestione gameOver ed ultimo input rilasciato

    (05) Gestione collisioni proiettile

    (06) Discesa

    (07) Movimento verso base

    (08) Movimento verso base

    (09) Generazione proiettile

    (10) Gioco era in corso

    (11) Creazione strutture dati per blocchi

    (12) 1: sparo (se proiettile non è attivo, 0: non sparo

    (13) Gestione vista base

    (14) Gestione vista base

    (16) Pulizia vecchi elementi rimasti visibili 

    (17) Inizializzo strutture giocatore

    (19) Creazione strutture dati per blocchi

    (20) Check collisione con blocchi

    (21) Check collisione con base

    (22) Ritorna: -1 se non vi è collisione tra oggetto passato per argomento e nessun tank

    (23) Controlla tipo tank e restituisci immagine relativa

    (24) Proiettile 'inattivo'
*/