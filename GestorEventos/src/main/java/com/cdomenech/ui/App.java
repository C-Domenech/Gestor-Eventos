package com.cdomenech.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    /**
     *
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("home"), 1000, 600);
        stage.setTitle("Eventhor - Tu gestor de eventos");
        Image image = new Image("images/eventhor_icon.png");
        stage.getIcons().add(image);
        stage.setMinWidth(1000);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Static method that generates info alerts
     *
     * @param titulo title given to the alert
     * @param contenido message of the alert
     */
    public static void generadorAlertaInformacion(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    /**
     * Static method that generates confirmation alerts
     *
     * @param titulo title given to the alert
     * @param contenido message of the alert
     * @param aceptar text of the first button
     * @param cancelar text of the second button
     * @param alerta type of alert
     * @return the option selected by the user operation
     */
    public static boolean generadorAlertaConfirmacion(String titulo, String contenido, String aceptar, String cancelar, Alert.AlertType alerta) {
        Alert alert = new Alert(alerta);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.setHeaderText(null);
        ButtonType btnAceptar = new ButtonType(aceptar, ButtonBar.ButtonData.YES);
        ButtonType btnCancelar = new ButtonType(cancelar, ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(btnAceptar, btnCancelar);
        alert.showAndWait();
        if (alert.getResult().getButtonData().equals(btnAceptar.getButtonData())) {
            return true;
        } else {
            return false;
        }

    }

}
