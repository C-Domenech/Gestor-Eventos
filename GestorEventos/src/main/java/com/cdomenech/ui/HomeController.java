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
import database.DBManager;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Evento;

/**
 * FXML Controller class
 *
 * @author Cristina Domenech <linkedin.com/in/c-domenech github.com/C-Domenech>
 */
public class HomeController implements Initializable {

    @FXML
    private Button btnVistaReservas;
    @FXML
    private Button btnVistaEventos;
    @FXML
    private Button btnSobreNosotros;
    @FXML
    private Label lbTituloEventoCercano;
    @FXML
    private Label lbFechaEventoCercano;
    @FXML
    private PieChart pieChartAforo;

    DBManager DB = new DBManager();
    @FXML
    private JFXButton btnAyuda;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Generates the element that can be set into the Pie Chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        // Get the data from the database
        Evento e = DB.datosEventoCercano();
        // Add every element that need to be shown in the chart
        pieChartData.add(new PieChart.Data("Reservado", e.getAforo() - e.getDisponible()));
        pieChartData.add(new PieChart.Data("Disponible", e.getDisponible()));
        lbTituloEventoCercano.setText(e.getNombre());
        lbFechaEventoCercano.setText(e.getFechaFormatted());
        // Set data into the Pie Chart
        pieChartAforo.setData(pieChartData);
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
     * Set new root
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void cambiarVistaSobreNosotros(ActionEvent event) throws IOException {
        App.setRoot("sobre_nosotros");
    }

    @FXML
    private void mostrarAyuda(ActionEvent event) throws URISyntaxException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("web_view.fxml"));

        Parent root = fxmlLoader.load();

        WebViewController controller = fxmlLoader.getController();

        // Send through the controller the url
        controller.inicializarWebView("https://eventhor-help.readthedocs.io/es/latest/guide/#home");

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
//        App.mostrarAyuda("https://eventhor-help.readthedocs.io/es/latest/guide/#home");

    }

}
