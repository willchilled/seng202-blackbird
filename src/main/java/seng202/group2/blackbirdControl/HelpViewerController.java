package seng202.group2.blackbirdControl;


import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class HelpViewerController{


    @FXML private WebView webView;
    private WebEngine webEngine;
    private Stage stage;
    private Parent root;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setRoot(Parent root){
        this.root = root;
    }

    /**
     * Displays the popup
     */
    public void start() {
        stage.setScene(new Scene(root));
        stage.setTitle("Help");
        initDoc();

    }

    /**
     * Holds the doc in the pop up
     */
    public void initDoc() {
        webEngine = webView.getEngine();
        URL urlHello = getClass().getResource("/Help/HelpDocManual.html");
        webEngine.load(urlHello.toExternalForm());
    }

}