package tankbattle;

import com.thoughtworks.xstream.*;
import java.io.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;
/**
 *
 * @author Gerti
 */
public class ParametriDiConfigurazione implements Serializable{
    public String ipClient;
    public String ipServer;
    public int portaServer;
    public String usernameDatabase;
    public String passwordDatabase;
    public int numeroCampiTabella;
    public int etaMassimaInGiorni;
    
    private static ParametriDiConfigurazione istanzaParametriDiConfigurazione;
    
    private ParametriDiConfigurazione(){}
    
    // (01)
    public static ParametriDiConfigurazione ottieniIstanza(){
        if(istanzaParametriDiConfigurazione == null){
            try {
                istanzaParametriDiConfigurazione = caricaParametriDiConfigurazione();
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
        return istanzaParametriDiConfigurazione;
    }
    
    private static ParametriDiConfigurazione caricaParametriDiConfigurazione() throws FileNotFoundException{
        valida();
        XStream xstream = new XStream();
        
        // (02)
        FileInputStream fileInput = new FileInputStream("configurazione.xml");
        xstream.alias("ParametriDiConfigurazione", ParametriDiConfigurazione.class);
        return (ParametriDiConfigurazione) xstream.fromXML(fileInput);
    }
    
    private static void valida(){
        try{
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Document doc = docBuilder.parse(new File("configurazione.xml"));
            Schema schema = schemaFactory.newSchema(new StreamSource(new File("configurazione.xsd")));
            schema.newValidator().validate(new DOMSource(doc));
        } catch(ParserConfigurationException | SAXException | IOException e) {
            if(e instanceof SAXException)
                System.err.println("Errore di validazione: " + e.getMessage());
            else 
                System.err.println(e.getMessage());
        }
    }
}

/* Note:
    
    // (01) Ho utilizzato il pattern Singleton
    
    // (02) Per lettura contenuto file di configurazione
*/