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
package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Evento;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Cristina Domenech <linkedin.com/in/c-domenech github.com/C-Domenech>
 */
public class JRManager {

    private Connection conn;

    /**
     * Constructor
     *
     * @throws IOException
     */
    public JRManager() throws IOException {
        this.conn = conectar();
    }

    /**
     * Method that connect to database
     *
     * @throws IOException
     * @return conn Connection to the database
     */
    public Connection conectar() throws IOException {
        try {
            Properties params = new Properties();
            params.load(new FileReader("db_config.cfg"));

            String protocol = params.getProperty("protocol");
            String server = params.getProperty("server");
            String port = params.getProperty("port");
            String database = params.getProperty("database");
            String extra = params.getProperty("extra");
            String user = params.getProperty("user");
            String password = params.getProperty("password");

            String url = protocol + server + ":" + port + "/" + database + "?" + extra;
            this.conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión con la base de datos establecida.");
        } catch (FileNotFoundException | SQLException ex) {
            Logger.getLogger(JRManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return conn;
    }

    public void generadorInformeGeneral(File file) throws JRException {
        String templateGeneral = "JRTemplateInformeGeneral.jasper";
//        String reportFilename = new SimpleDateFormat("ddMMyyyy_HHmm'_general_report.pdf'").format(new Date());
        // Parameters needed by the JasperSoft report
        HashMap parameters = new HashMap();
        parameters.put("REPORT_TITLE", "Reservas realizadas");
        // Generate the report
        JasperPrint jasperPrint = JasperFillManager.fillReport(templateGeneral, parameters, conn);

        String exportPath = file.toString();
        // Export report -> It needs a JasperPrint object and the destination of the file with filename
        JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
    }

    public void generadorInformeEvento(Evento e, File file) throws JRException {
        String templateEvento = "JRTemplateInformeEvento.jasper";
//        String reportFilename = new SimpleDateFormat("ddMMyyyy_HHmm'_" + e.getNombre() + "_report.pdf'").format(new Date());
        // Parameters needed by the JasperSoft report
        HashMap parameters = new HashMap();
        parameters.put("REPORT_TITLE", "Reservas \"" + e.getNombre() + "\" - " + e.getFechaFormatted());
        // Parameter that the SQL sentence uses in the template
        parameters.put("ID_EVENTO", e.getId_evento());
        // Generate the report
        JasperPrint jasperPrint = JasperFillManager.fillReport(templateEvento, parameters, conn);

        String exportPath = file.toString();
        // Export report -> It needs a JasperPrint object and the destination of the file with filename
        JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
    }

}
