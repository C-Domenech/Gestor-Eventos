package com.cdomenech.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Evento;
import models.Reserva;

public class ReservasController implements Initializable {

    @FXML
    private Button btnVistaHome;
    @FXML
    private Button btnVistaEventos;
    @FXML
    private Button btnSobreNosotros;
    @FXML
    private JFXButton btnNuevaReserva;
    @FXML
    private TableView<Reserva> tbReservas;
    @FXML
    private TableColumn<Reserva, String> cNombre;
    @FXML
    private TableColumn<Reserva, String> cApellidos;
    @FXML
    private TableColumn<Reserva, String> cEmail;
    @FXML
    private TableColumn<Reserva, String> cObservaciones;
    @FXML
    private TableColumn<Reserva, Integer> cAsistentes;
    @FXML
    private JFXComboBox<Evento> cbReservasEvento;
    @FXML
    private Label lbAforo;
    @FXML
    private Label lbCompleto;
    @FXML
    private Label lbDisponible;

    DBManager DB;

    /**
     * Method that refresh the data in the table
     */
    @FXML
    public void actualizarTabla() {
        // Set the data that is going to be shown in the TableView
        cNombre.setCellValueFactory(new PropertyValueFactory<Reserva, String>("nombre"));
        cApellidos.setCellValueFactory(new PropertyValueFactory<Reserva, String>("apellidos"));
        cEmail.setCellValueFactory(new PropertyValueFactory<Reserva, String>("email"));
        cAsistentes.setCellValueFactory(new PropertyValueFactory<Reserva, Integer>("asistentesReserva"));
        cObservaciones.setCellValueFactory(new PropertyValueFactory<Reserva, String>("observaciones"));
        DB = new DBManager();
        Evento evento = cbReservasEvento.getValue();
        // Get select of all order from the database
        ObservableList<Reserva> reservasEvento = DB.listarReservasEvento(evento);
        // Final set
        tbReservas.setItems(reservasEvento);
        lbAforo.setText(Integer.toString(evento.getAforo()));
        lbCompleto.setText(Integer.toString(evento.getAforo() - evento.getDisponible()));
        lbDisponible.setText(Integer.toString(evento.getDisponible()));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cNombre.prefWidthProperty().bind(tbReservas.widthProperty().divide(6)); // w * 1/9
        cApellidos.prefWidthProperty().bind(tbReservas.widthProperty().divide(4)); // w * 1/2
        cEmail.prefWidthProperty().bind(tbReservas.widthProperty().divide(4)); // w * 1/5
        cAsistentes.prefWidthProperty().bind(tbReservas.widthProperty().divide(6)); // w * 1/5
        cObservaciones.prefWidthProperty().bind(tbReservas.widthProperty().divide(6)); // w * 1/5

        DB = new DBManager();
        cbReservasEvento.setItems(DB.listarEventos());
    }

    @FXML
    private void cambiarVistaHome(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void cambiarVistaEventos(ActionEvent event) throws IOException {
        App.setRoot("eventos");
    }

    @FXML
    private void cambiarVistaSobreNosotros(ActionEvent event) throws IOException {
        App.setRoot("sobre_nosotros");
    }

    @FXML
    private void abrirVentanaNuevaReserva(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("nueva_reserva.fxml"));

        Parent root = fxmlLoader.load();

        NuevaReservaController controller = fxmlLoader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Realizar reserva");
//        Image image = new Image("images/gestor-eventos.png");
//        stage.getIcons().add(image);
//        User can not do anything until the window is closed
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        actualizarTabla();
    }
}
