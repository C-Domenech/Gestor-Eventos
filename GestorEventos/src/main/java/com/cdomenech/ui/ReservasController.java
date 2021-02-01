package com.cdomenech.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import database.DBManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Evento;
import models.Reserva;

/**
 *
 * @author Cristina Domenech <linkedin.com/in/c-domenech github.com/C-Domenech>
 */
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
    @FXML
    private MenuItem mEditar;
    @FXML
    private MenuItem mEliminar;

    DBManager DB = new DBManager();

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
        // Get evento from the ComboBox
        Evento evento = cbReservasEvento.getValue();
        // Clear table
        tbReservas.getItems().clear();
        // Set objects Reserva from the database
        tbReservas.setItems(DB.listarReservasEvento(evento));
//        Evento e = DB.obtenerEvento(evento);
        // Update the situation of the event
        lbAforo.setText(Integer.toString(evento.getAforo()));
        lbCompleto.setText(Integer.toString(evento.getAforo() - evento.getDisponible()));
        lbDisponible.setText(Integer.toString(evento.getDisponible()));
//        System.out.println("EVENTO: " + evento.getReservas());
//        System.out.println("E: " + e.getReservas());
    }

    /**
     * Initializer of the window
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set the size of the columns in proportion to the window
        cNombre.prefWidthProperty().bind(tbReservas.widthProperty().divide(6)); // w * 1/6
        cApellidos.prefWidthProperty().bind(tbReservas.widthProperty().divide(4)); // w * 1/4
        cEmail.prefWidthProperty().bind(tbReservas.widthProperty().divide(4)); // w * 1/4
        cAsistentes.prefWidthProperty().bind(tbReservas.widthProperty().divide(6)); // w * 1/6
        cObservaciones.prefWidthProperty().bind(tbReservas.widthProperty().divide(6)); // w * 1/6
        // Set the eventos from the database into the ComboBox
        cbReservasEvento.setItems(DB.listarEventos());
    }

    /**
     * Set new root
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void cambiarVistaHome(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    /**
     * Set new root
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void cambiarVistaEventos(ActionEvent event) throws IOException {
        App.setRoot("eventos");
    }

    /**
     * Set new root
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void cambiarVistaSobreNosotros(ActionEvent event) throws IOException {
        App.setRoot("sobre_nosotros");
    }

    /**
     * Open another window to create a new reservation
     *
     * @param event
     * @throws IOException
     */
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
        Image image = new Image("images/eventhor_icon.png");
        stage.getIcons().add(image);
        // User can not do anything until the window is closed
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        // Repopulate the table
        if (cbReservasEvento.getValue() != null) {
            actualizarTabla();
        }
    }

    /**
     * From the context menu -> Open another window to edit a reservation
     *
     * @param event
     */
    @FXML
    private void editarReserva(ActionEvent event) {
        Reserva reservaSeleccionada = tbReservas.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("nueva_reserva.fxml"));

            Parent root = fxmlLoader.load();

            NuevaReservaController controller = fxmlLoader.getController();
            // Send through the controller the selected object
            controller.inicializaDatosParaEditar(reservaSeleccionada);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false); // perfect size -> 495,750
            stage.setTitle("Editar Reserva");
            Image image = new Image("images/eventhor_icon.png");
            stage.getIcons().add(image);
            // User can not do anything until the window is closed
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            // Repopulate the table
            actualizarTabla();

        } catch (IOException ex) {
            Logger.getLogger(ReservasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * From the context menu -> Remove a reservation
     *
     * @param event
     */
    @FXML
    private void eliminarReserva(ActionEvent event) {
        // Alert dialog to warn the user
        if (App.generadorAlertaConfirmacion("Eliminar reserva", "¿Deseas eliminar esta reserva?", "Sí", "Cancelar", Alert.AlertType.CONFIRMATION)) {
            DB.eliminarReserva(tbReservas.getSelectionModel().getSelectedItem());
            // Repopulate the table
            actualizarTabla();
        }
    }
}
