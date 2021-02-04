package com.cdomenech.ui;

/*
 * Copyright (C) 2021 Cristina Domenech <linkedin.com/in/c-domenech github.com/C-Domenech>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DBManager;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import models.Evento;

/**
 * FXML Controller class
 *
 * @author Cristina Domenech <linkedin.com/in/c-domenech github.com/C-Domenech>
 */
public class NuevoEventoController implements Initializable {

    @FXML
    private JFXTextField tfNombreEvento;
    @FXML
    private JFXTextField tfAforo;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private Spinner<String> spHora;
    @FXML
    private Spinner<String> spMin;
    @FXML
    private JFXButton btnCrear;
    @FXML
    private JFXButton btnBorrar;
    @FXML
    private Label lbInfo;
    @FXML
    private Label lbSuperior;

    DBManager DB = new DBManager();
    Evento eventoParaEditar;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set data in the spinners
        ObservableList<String> num1 = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        SpinnerValueFactory<String> horas = new SpinnerValueFactory.ListSpinnerValueFactory<String>(num1);
        spHora.setValueFactory(horas);
        ObservableList<String> num2 = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59");
        SpinnerValueFactory<String> minutos = new SpinnerValueFactory.ListSpinnerValueFactory<String>(num2);
        spMin.setValueFactory(minutos);
    }

    /**
     * Check the data given by the user and, if they are correct, update it into
     * the database
     *
     * @param event
     */
    @FXML
    private void crearEvento(ActionEvent event) {
        if (checkData()) {
            // Get the data from the fields
            String nombre = tfNombreEvento.getText();
            int aforo = Integer.parseInt(tfAforo.getText());
            LocalDate dia = dpFecha.getValue();
            Timestamp fecha = Timestamp.valueOf(dia + " " + spHora.getValue() + ":" + spMin.getValue() + ":" + "00");
            // Check if it is a new event or the user is editing an existing one

            if (eventoParaEditar == null) {
                // New element
                // Create a new Evento object and send it to the DBManager class
                Evento e = new Evento(nombre, fecha, aforo);
                DB.crearEvento(e);
                btnBorrar.fire();
                // Alert dialog that inform about the success of the operation
                App.generadorAlertaInformacion("Información", "Evento creado correctamente");
                Stage stage = (Stage) btnCrear.getScene().getWindow();
                stage.close();
            } else {
                // Editing existing one
                // Send object that is going to be updated and the data
                DB.editarEvento(eventoParaEditar, nombre, fecha, aforo);
                // Alert dialog that inform about the success of the operation
                App.generadorAlertaInformacion("Información", "Evento actualizado correctamente");
                Stage stage = (Stage) btnCrear.getScene().getWindow();
                stage.close();
            }
        }
    }

    /**
     * Check if the fields are filled and if the email is correct
     *
     * @return boolean if the fields have been completed
     */
    public boolean checkData() {
        boolean validData;
        if (tfNombreEvento.getText().isBlank() || tfAforo.getText().isBlank() || dpFecha.getValue() == null) {
            validData = false;
            lbInfo.setText("Rellene los campos obligatorios *");
        } else {
            validData = isValidAforo(tfAforo.getText());
        }
        return validData;
    }

    /**
     * Check if aforo is a number
     *
     * @param aforo
     * @return
     */
    public boolean isValidAforo(String aforo) {
        boolean validAforo;
        try {
            int n = Integer.parseInt(aforo);
            validAforo = true;
            lbInfo.setText("");
        } catch (NumberFormatException e) {
            validAforo = false;
            lbInfo.setText("El aforo debe ser un número entero");
        }
        return validAforo;
    }

    /**
     * Reset the fields
     *
     * @param event
     */
    @FXML
    private void borrarDatosIntroducidos(ActionEvent event) {
        tfNombreEvento.clear();
        dpFecha.getEditor().clear();
        spHora.getValueFactory().setValue("00");
        spMin.getValueFactory().setValue("00");
        tfAforo.clear();
        lbInfo.setText(null);
    }

    /**
     * If the user is going to edit an event, the fields are filled with the
     * original data
     *
     * @param evento
     */
    public void inicializaDatosParaEditar(Evento evento) {
        eventoParaEditar = evento;
        tfNombreEvento.setText(evento.getNombre());
        tfAforo.setText(Integer.toString(evento.getAforo()));

        LocalDate localDate = evento.getFecha().toLocalDateTime().toLocalDate();
        dpFecha.setValue(localDate);

        LocalDateTime localDT = evento.getFecha().toLocalDateTime();
        spHora.getValueFactory().setValue(Integer.toString(localDT.getHour()));
        spMin.getValueFactory().setValue(Integer.toString(localDT.getMinute()));

        lbSuperior.setText("Editar evento");
        btnCrear.setText("ACTUALIZAR");
        btnBorrar.setDisable(true);
    }

}
