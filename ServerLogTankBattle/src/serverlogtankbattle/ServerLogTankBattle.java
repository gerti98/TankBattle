package serverlogtankbattle;

import java.io.*;
import java.net.*;
import java.nio.file.*;

/**
 *
 * @author Gerti
 */
public class ServerLogTankBattle {
    public static void main(String[] args) {
        try (
            ServerSocket socket = new ServerSocket(8080)
        ){
            while(true){
                LogDiNavigazioneGUI logEvento = LogDiNavigazioneGUI.ricevi(socket);
                if(logEvento == null)
                    continue;
                
                logEvento.stampa();
                Files.write(Paths.get("eventoXML.xml"),(logEvento.serializzaConUTF() + "\n<!--###########-->\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } 
    }
}
