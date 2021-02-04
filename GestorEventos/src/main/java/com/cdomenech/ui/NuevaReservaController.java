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
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import database.DBManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Evento;
import models.Reserva;

/**
 * FXML Controller class
 *
 * @author Cristina Domenech <linkedin.com/in/c-domenech github.com/C-Domenech>
 */
public class NuevaReservaController implements Initializable {

    @FXML
    private JFXTextField tfNombre;
    @FXML
    private JFXTextField tfApellido1;
    @FXML
    private JFXTextField tfApellido2;
    @FXML
    private JFXTextField tfEmail;
    @FXML
    private JFXComboBox<Evento> cbEvento;
    @FXML
    private JFXComboBox<String> cbNumAcom;
    @FXML
    private JFXTextArea taObservaciones;
    @FXML
    private JFXButton btnReservar;
    @FXML
    private JFXButton btnBorrar;
    @FXML
    private Label lbInfo;

    DBManager DB = new DBManager();
    Reserva reservaParaEditar;
    @FXML
    private Label lbSuperior;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set the eventos into the ComboBox
        cbEvento.setItems(DB.listarEventosFuturos());
        // Set numbers into the ComboBox
        ObservableList<String> numeros = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        cbNumAcom.setItems(numeros);
    }

    /**
     * Check the data given by the user and, if they are correct, update it into
     * the database
     *
     * @param event
     */
    @FXML
    private void realizarReserva(ActionEvent event) {
        if (checkData()) {
            // Get the data from the fields
            String nombre = tfNombre.getText();
            String apellido1 = tfApellido1.getText();
            String apellido2 = tfApellido2.getText();
            String email = tfEmail.getText();
            Evento evento = cbEvento.getValue();
            int asistentes = Integer.parseInt(cbNumAcom.getValue()) + 1;
            String observaciones = "";
            if (!taObservaciones.getText().isBlank()) {
                observaciones = taObservaciones.getText();
            }
            // Check if the aforo of the event won't be exceed by the changes or the new reservation
            if (asistentes > evento.getDisponible() && reservaParaEditar == null || asistentes > evento.getDisponible() && asistentes > reservaParaEditar.getAsistentes()) {
                lbInfo.setText("Aforo completo");
            } else {
                // Check if it is a new reservation or the user is editing an existing one
                if (reservaParaEditar == null) {
                    // New element
                    // Check if there is a reservation from the same person in the same event
                    if (!DB.compruebaEmailReservaExiste(email, evento)) {
                        // Create a new Reserva object and send it to the DBManager class
                        Reserva r = new Reserva(nombre, apellido1, apellido2, email, evento, asistentes, observaciones);
                        DB.crearReserva(r);
                        btnBorrar.fire();
                        // Alert dialog that inform about the success of the operation
                        App.generadorAlertaInformacion("Información", "Reserva realizada correctamente");
                        Stage stage = (Stage) btnReservar.getScene().getWindow();
                        stage.close();

                    } else {
                        lbInfo.setText("Ya existe una reserva con ese email");
                    }
                } else {
                    // Editing existing one
                    // Send object that is going to be updated and the data
                    DB.editarReserva(reservaParaEditar, nombre, apellido1, apellido2, email, evento, asistentes, observaciones);
                    // Alert dialog that inform about the success of the operation
                    App.generadorAlertaInformacion("Información", "Reserva actualizada correctamente");
                    Stage stage = (Stage) btnReservar.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }

    /**
     * Check if the fields are filled and if the email is correct
     *
     * @return boolean if the fields have been completed and the email is
     * correct
     */
    public boolean checkData() {
        boolean validData;

        if (tfNombre.getText().isBlank() || tfApellido1.getText().isBlank() || tfApellido2.getText().isBlank() || tfEmail.getText().isBlank() || cbEvento.getValue() == null || cbNumAcom.getValue() == null) {
            validData = false;
            lbInfo.setText("Rellene los campos obligatorios *");
        } else {
            validData = isValidEmail(tfEmail.getText());
        }
        return validData;
    }

    /**
     * Check if the structure of the email is correct
     *
     * @param email
     * @return boolean
     */
    public boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        boolean validEmail = email.matches(regex);
        if (!validEmail) {
            lbInfo.setText("Introduzca un email válido");
        } else {
            lbInfo.setText("");
        }
        return validEmail;
    }

    /**
     * Reset the fields
     *
     * @param event
     */
    @FXML
    private void borrarDatosIntroducidos(ActionEvent event) {
        tfNombre.clear();
        tfApellido1.clear();
        tfApellido2.clear();
        tfEmail.clear();
        cbEvento.getSelectionModel().clearSelection();
        cbNumAcom.getSelectionModel().clearSelection();
        taObservaciones.clear();
        lbInfo.setText(null);
    }

    /**
     * If the user is going to edit a reservation, the fields are filled with
     * the original data
     *
     * @param reserva
     */
    public void inicializaDatosParaEditar(Reserva reserva) {
        reservaParaEditar = reserva;
        tfNombre.setText(reserva.getNombre());
        tfApellido1.setText(reserva.getApellido1());
        tfApellido2.setText(reserva.getApellido2());
        tfEmail.setText(reserva.getEmail());
        cbEvento.getSelectionModel().select(reserva.getEvento());
        cbNumAcom.getSelectionModel().select(reserva.getAsistentes() - 1);
        taObservaciones.setText(reserva.getObservaciones());

        lbSuperior.setText("Editar reserva");
        btnReservar.setText("ACTUALIZAR");
        btnBorrar.setDisable(true);
    }

    /**
     * Set the details of the reservation
     *
     * @param reserva
     */
    public void inicializaDatosDetalle(Reserva reserva) {
        tfNombre.setText(reserva.getNombre());
        tfApellido1.setText(reserva.getApellido1());
        tfApellido2.setText(reserva.getApellido2());
        tfEmail.setText(reserva.getEmail());
        cbEvento.getSelectionModel().select(reserva.getEvento());
        cbNumAcom.getSelectionModel().select(reserva.getAsistentes() - 1);
        taObservaciones.setText(reserva.getObservaciones());
        // User can not change any field
        tfNombre.setDisable(true);
        tfApellido1.setDisable(true);
        tfApellido2.setDisable(true);
        tfEmail.setDisable(true);
        cbEvento.setDisable(true);
        cbNumAcom.setDisable(true);
        taObservaciones.setDisable(true);

        tfNombre.setStyle("-fx-opacity: 1.0;");
        tfApellido1.setStyle("-fx-opacity: 1.0;");
        tfApellido2.setStyle("-fx-opacity: 1.0;");
        tfEmail.setStyle("-fx-opacity: 1.0;");
        cbEvento.setStyle("-fx-opacity: 1.0;");
        cbNumAcom.setStyle("-fx-opacity: 1.0;");
        taObservaciones.setStyle("-fx-opacity: 1.0;");

        lbSuperior.setText("Reserva en detalle");
        btnReservar.setVisible(false);
        btnBorrar.setVisible(false);
    }

}
