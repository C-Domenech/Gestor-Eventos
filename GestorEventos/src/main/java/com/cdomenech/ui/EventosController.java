package com.cdomenech.ui;

import com.jfoenix.controls.JFXButton;
import database.DBManager;
import database.JRManager;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Evento;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Cristina Domenech <linkedin.com/in/c-domenech github.com/C-Domenech>
 */
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
    @FXML
    private MenuItem mEditar;
    @FXML
    private MenuItem mEliminar;
    @FXML
    private JFXButton btnGenerar;
    @FXML
    private MenuItem mInforme;

    DBManager DB = new DBManager();

    JRManager jr;
    @FXML
    private JFXButton btnAyuda;

    public EventosController() throws IOException {
        this.jr = new JRManager();
    }

    /**
     * Method that refresh the data of the table
     */
    public void actualizarTabla() {
        // Set the data that is going to be shown in the TableView
        cNombreEvento.setCellValueFactory(new PropertyValueFactory<Evento, String>("nombre"));
        cFecha.setCellValueFactory(new PropertyValueFactory<Evento, String>("fechaFormatted"));
        cAforo.setCellValueFactory(new PropertyValueFactory<Evento, Integer>("aforo"));
        cDisponible.setCellValueFactory(new PropertyValueFactory<Evento, Integer>("disponible"));
        // Clear table
        tbEventos.getItems().clear();
        // Set objects Evento from the database
        tbEventos.setItems(DB.listarEventos());
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
        cNombreEvento.prefWidthProperty().bind(tbEventos.widthProperty().divide(3));
        cFecha.prefWidthProperty().bind(tbEventos.widthProperty().divide(3));
        cAforo.prefWidthProperty().bind(tbEventos.widthProperty().divide(6));
        cDisponible.prefWidthProperty().bind(tbEventos.widthProperty().divide(6));

        actualizarTabla();
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
    private void cambiarVistaReservas(ActionEvent event) throws IOException {
        App.setRoot("reservas");
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
     * Open another window to create a new event
     *
     * @param event
     * @throws IOException
     */
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
        Image image = new Image("images/eventhor_icon.png");
        stage.getIcons().add(image);
        // User can not do anything until the window is closed
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        // Repopulate the table
        actualizarTabla();
    }

    /**
     * From the context menu -> Open another window to edit an event
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void editarEvento(ActionEvent event) throws IOException {
        Evento eventoSeleccionado = tbEventos.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("nuevo_evento.fxml"));

        Parent root = fxmlLoader.load();

        NuevoEventoController controller = fxmlLoader.getController();
        // Send through the controller the selected object
        controller.inicializaDatosParaEditar(eventoSeleccionado);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Editar Evento");
        Image image = new Image("images/eventhor_icon.png");
        stage.getIcons().add(image);
        // User can not do anything until the window is closed
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        // Repopulate the table
        actualizarTabla();
    }

    /**
     * From the context menu -> Remove an event
     *
     * @param event
     */
    @FXML
    private void eliminarEvento(ActionEvent event) {
        // Alert dialog to warn the user
        if (App.generadorAlertaConfirmacion("Eliminar evento", "¿Deseas eliminar este evento?", "Sí", "Cancelar", Alert.AlertType.CONFIRMATION)) {
            DB.eliminarEvento(tbEventos.getSelectionModel().getSelectedItem());
            // Repopulate the table
            actualizarTabla();
        }
    }

    /**
     * Open default file chooser where the user can select a dicrectory and the
     * filename
     *
     * @param event
     * @throws IOException
     * @throws JRException
     */
    @FXML
    private void generarInformeGeneral(ActionEvent event) throws IOException, JRException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar como...");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("documento PDF", "*.pdf"));
        File file = fileChooser.showSaveDialog(new Stage());

        jr.generadorInformeGeneral(file);

        App.generadorAlertaInformacion("Información", "Informe generado correctamente");
    }

    /**
     * Open default file chooser where the user can select a dicrectory and the
     * filename
     *
     * @param event
     * @throws JRException
     */
    @FXML
    private void generarInformeEvento(ActionEvent event) throws JRException {
        Evento eventoSeleccionado = tbEventos.getSelectionModel().getSelectedItem();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar como...");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("documento PDF", "*.pdf"));
        File file = fileChooser.showSaveDialog(new Stage());

        jr.generadorInformeEvento(eventoSeleccionado, file);

        App.generadorAlertaInformacion("Información", "Informe generado correctamente");
    }

    @FXML
    private void mostrarAyuda(ActionEvent event) throws URISyntaxException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("web_view.fxml"));

        Parent root = fxmlLoader.load();

        WebViewController controller = fxmlLoader.getController();

        // Send through the controller the url
        controller.inicializarWebView("https://eventhor-help.readthedocs.io/es/latest/guide/#eventos");

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setTitle("Ayuda");
        Image image = new Image("images/eventhor_icon.png");
        stage.getIcons().add(image);
        // Users are free to do what they need
        stage.initModality(Modality.NONE);
        stage.show();
//        App.mostrarAyuda("https://eventhor-help.readthedocs.io/es/latest/guide/#eventos");
    }
}
