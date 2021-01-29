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

    DBManager DB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DB = new DBManager();
        cbEvento.setItems(DB.listarEventos());
        ObservableList<String> numeros = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        //        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
        cbNumAcom.setItems(numeros);
    }

    @FXML
    private void realizarReserva(ActionEvent event) {
        DB = new DBManager();
        if (checkData()) {
            String nombre = tfNombre.getText();
            String apellido1 = tfApellido1.getText();
            String apellido2 = tfApellido2.getText();
            String email = tfEmail.getText();
            Evento evento = cbEvento.getValue();
            int numAc = Integer.parseInt(cbNumAcom.getValue());
            if (!DB.compruebaEmailReservaExiste(email)) {
                if (numAc + 1 > evento.getDisponible()) {
                    lbInfo.setText("Aforo completo");
                } else {
                    String observaciones = "";
                    if (!taObservaciones.getText().isBlank()) {
                        observaciones = taObservaciones.getText();
                    }
                    Reserva r = new Reserva(nombre, apellido1, apellido2, email, evento, numAc, observaciones);
                    DB.crearReserva(r);
                    btnBorrar.fire();
                }
            } else {
                lbInfo.setText("Ya existe una reserva con ese email");
            }

        }

    }

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

    public boolean isValidEmail(String email) {
//        String email = tfEmail.getText();
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        boolean validEmail = email.matches(regex);
        if (!validEmail) {
            lbInfo.setText("Introduzca un email válido");
        } else {
            lbInfo.setText("");
        }
        return validEmail;
    }

    @FXML
    private void borrarDatosIntroducidos(ActionEvent event) {
        tfNombre.setText("");
        tfApellido1.setText("");
        tfApellido2.setText("");
        tfEmail.setText("");
        cbEvento.getSelectionModel().clearSelection();
        cbNumAcom.getSelectionModel().clearSelection();
        taObservaciones.setText("");
        lbInfo.setText("");

    }

}
