package tankbattle;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author Gerti
 */                 
public class DatabaseRanking {
    private static Connection connessioneDB;
    private static String username = ParametriDiConfigurazione.ottieniIstanza().usernameDatabase;
    private static String password = ParametriDiConfigurazione.ottieniIstanza().passwordDatabase;
    private static final String database = "jdbc:mysql://localhost:3306/tankbattle";
    
    // (01) 
    private static int campiTabella = ParametriDiConfigurazione.ottieniIstanza().numeroCampiTabella;
    private static int etaMassimaInGiorni = ParametriDiConfigurazione.ottieniIstanza().etaMassimaInGiorni;
    
    private static PreparedStatement statementCaricamentoPartite;
    private static PreparedStatement statementInserimentoPartite;
    
    static {
        try{
            connessioneDB = DriverManager.getConnection(database, username, password);
            statementCaricamentoPartite = connessioneDB.prepareStatement("SELECT * FROM tankbattle.partita WHERE DATEDIFF(CURRENT_DATE, dataPartita) <= ? ORDER BY score DESC LIMIT ?;"); 
            statementInserimentoPartite = connessioneDB.prepareStatement("INSERT INTO partita(username, score, dataPartita) VALUES (?, ?, ?);");
        } catch (SQLException e) {System.err.println(e.getMessage());} 
    }
    
    public static List<Partita> caricaRanking(){ // (02)
        List<Partita> returnList = new ArrayList();
        int pos = 1;
        try{ 
            statementCaricamentoPartite.setInt(1, etaMassimaInGiorni);
            statementCaricamentoPartite.setInt(2, campiTabella);
            ResultSet result = statementCaricamentoPartite.executeQuery();  
            while (result.next()){ 
                returnList.add(new Partita(pos++, result.getString("username"), result.getInt("score")));
                //System.out.println("[DEBUG] caricamento");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }    
        return returnList;
    }
    
    public static void AggiungiRanking(Partita partita){
        try{
            SimpleDateFormat DateFormatOne = new SimpleDateFormat("yyyy-MM-dd"); // (03) 
            java.util.Date date = new java.util.Date();
            statementInserimentoPartite.setString(1, partita.getUsername());
            statementInserimentoPartite.setInt(2, partita.getScore());
            statementInserimentoPartite.setString(3, DateFormatOne.format(date));
            System.out.println("rows affected: " + statementInserimentoPartite.executeUpdate());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } 
    }   
}   

/* Note:

    (01) Parametri da file di cofigurazione

    (02) Permette ottenimento lista ranking in base a limiti parametri di configurazione

    (03) Gestione data per essere compatibile con formato MySQL
*/