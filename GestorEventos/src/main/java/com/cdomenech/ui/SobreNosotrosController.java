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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * FXML Controller class
 *
 * @author Cristina Domenech <linkedin.com/in/c-domenech github.com/C-Domenech>
 */
public class SobreNosotrosController implements Initializable {

    @FXML
    private Button btnVistaReservas;
    @FXML
    private Button btnVistaEventos;
    @FXML
    private Button btnVistaHome;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Do nothing
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
    private void cambiarVistaEventos(ActionEvent event) throws IOException {
        App.setRoot("eventos");
    }

    /**
     * Open the browser and go to a specific website
     *
     * @param event
     * @throws URISyntaxException
     * @throws IOException
     */
    @FXML
    private void abrirWeb(ActionEvent event) throws URISyntaxException, IOException {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI("https://github.com/C-Domenech/Gestor-Eventos"));
        }
    }

}
