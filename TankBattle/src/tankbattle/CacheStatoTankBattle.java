package tankbattle;

import java.io.*;
/**
 *
 * @author Gerti
 */
public class CacheStatoTankBattle implements Serializable{
    public TankDiGioco tankGiocatore = null;
    public TankDiGioco[] tankNemico = null;
    public String username = "";
    public int punteggio = 0;
    public boolean giocoIniziato = false;
    
    public CacheStatoTankBattle(VistaGiocoTankBattle gameview, AreaRegistrazione registrazione){
        tankGiocatore = gameview.getTankGiocatore();
        tankNemico = gameview.getTankNemico();
        username = registrazione.campoUsername.getText();
        punteggio = gameview.getPunteggio();
        giocoIniziato = gameview.isGiocoIniziato();
    }
    
    public void caricaStatoGioco(VistaGiocoTankBattle gameview, AreaRegistrazione registrazione){ 
        CacheStatoTankBattle cache;
        
        // (01)
        try (ObjectInputStream objectInStream = new ObjectInputStream(new FileInputStream("cacheStatoGioco.bin"))) {
            cache = (CacheStatoTankBattle)objectInStream.readObject();
        } catch (FileNotFoundException ex) {
                cache = null;
        } catch (ClassNotFoundException | IOException ex) {
                System.err.println(ex.getMessage());
                cache = null;
        }
        
        if(cache == null){
            //System.out.println("[DEBUG] No cache da caricare");
            return;
        }
        
        // (02) 
        gameview.setTankGiocatore(cache.tankGiocatore);
        gameview.setTankNemico(cache.tankNemico);
        registrazione.campoUsername.setText(cache.username);
        gameview.setPunteggio(cache.punteggio);
        gameview.setGiocoIniziato(cache.giocoIniziato);
    }
    
    
    public void salvaStatoGioco(){
        
        try (ObjectOutputStream objectOutStream = new ObjectOutputStream(new FileOutputStream("cacheStatoGioco.bin"))) { // (03)
            objectOutStream.writeObject(this);
            //System.out.println("Scrittura stato effettuata");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}

/* Note:

    (01) Deserializza da file binario

    (02) Aggiorna vari elementi

    (03) Serializza su file binario
*/