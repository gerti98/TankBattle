package tankbattle;

import java.util.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;

/**
 *
 * @author Gerti
 */
public class AreaTabellaVisualeRanking extends VBox{
    private final TableView<Partita> tabellaVisualeRanking = new TableView<>();
    private final Label labelRanking;
    private final TableColumn colonnaPosizione;
    private final TableColumn colonnaUsername;
    private final TableColumn colonnaScore;
    private ObservableList<Partita> observablePartita;
    
    
    AreaTabellaVisualeRanking(){
        labelRanking = new Label("RANKING");
        labelRanking.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        labelRanking.setPadding(new Insets(0, 0, 10, 70));
        colonnaPosizione = new TableColumn("#");
        colonnaPosizione.setCellValueFactory(new PropertyValueFactory<>("posizione"));
        colonnaPosizione.setStyle( "-fx-alignment: CENTER;");
        
        colonnaUsername = new TableColumn("Username");
        colonnaUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colonnaUsername.setStyle( "-fx-alignment: CENTER;");
        
        colonnaScore = new TableColumn("Score");
        colonnaScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        colonnaScore.setStyle( "-fx-alignment: CENTER;");
        
        observablePartita = FXCollections.observableArrayList();
        tabellaVisualeRanking.setItems(observablePartita);
        tabellaVisualeRanking.getColumns().addAll(colonnaPosizione, colonnaUsername, colonnaScore);
        tabellaVisualeRanking.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        getChildren().addAll(labelRanking, tabellaVisualeRanking);
        setPadding(new Insets(40, 20, 20, 20));
    }
    
    
    public void AggiornaListaRanking(List<Partita> partitaList){ // (01) 
        observablePartita.clear();
        observablePartita.addAll(partitaList);
    }
}

/* Note:

    (01) Permette di aggiornare elementi partita in tabella visuale

*/