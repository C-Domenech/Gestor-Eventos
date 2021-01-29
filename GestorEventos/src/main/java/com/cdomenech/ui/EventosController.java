package com.cdomenech.ui;

import com.jfoenix.controls.JFXButton;
import database.DBManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Evento;

public class EventosController implements Initializable {

    @FXML
    private Button btnVistaHome;
    @FXML
    private Button btnVistaReservas;
    @FXML
    private Button btnVistaSobreNosotros;
    @FXML
    private JFXButton btnNuevoEvento;
    @FXML
    private TableView<Evento> tbEventos;
    @FXML
    private TableColumn<Evento, String> cNombreEvento;
    @FXML
    private TableColumn<Evento, String> cFecha;
    @FXML
    private TableColumn<Evento, Integer> cAforo;
    @FXML
    private TableColumn<Evento, Integer> cDisponible;

    DBManager DB;

    /**
     * Method that refresh the data in the table
     */
    public void actualizarTabla() {
        // Set the data that is going to be shown in the TableView
        cNombreEvento.setCellValueFactory(new PropertyValueFactory<Evento, String>("nombre"));
        cFecha.setCellValueFactory(new PropertyValueFactory<Evento, String>("fechaFormatted"));
        cAforo.setCellValueFactory(new PropertyValueFactory<Evento, Integer>("aforo"));
        cDisponible.setCellValueFactory(new PropertyValueFactory<Evento, Integer>("disponible"));
        DB = new DBManager();
        // Get select of all order from the database
        ObservableList<Evento> eventos = DB.listarEventos();
        // Final set
        tbEventos.setItems(eventos);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cNombreEvento.prefWidthProperty().bind(tbEventos.widthProperty().divide(3)); // w * 1/9
        cFecha.prefWidthProperty().bind(tbEventos.widthProperty().divide(3)); // w * 1/2
        cAforo.prefWidthProperty().bind(tbEventos.widthProperty().divide(6)); // w * 1/5
        cDisponible.prefWidthProperty().bind(tbEventos.widthProperty().divide(6)); // w * 1/5
        actualizarTabla();
    }

    @FXML
    private void cambiarVistaHome(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void cambiarVistaReservas(ActionEvent event) throws IOException {
        App.setRoot("reservas");
    }

    @FXML
    private void cambiarVistaSobreNosotros(ActionEvent event) throws IOException {
        App.setRoot("sobre_nosotros");
    }

    @FXML
    private void abrirVentanaNuevoEvento(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("nuevo_evento.fxml"));

        Parent root = fxmlLoader.load();

        NuevoEventoController controller = fxmlLoader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Crear evento");
//        Image image = new Image("images/gestor-eventos.png");
//        stage.getIcons().add(image);
        // User can not do anything until the window is closed
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        actualizarTabla();
    }

}
