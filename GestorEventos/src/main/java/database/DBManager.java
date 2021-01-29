/*
 * Copyright (C) 2021 Cristina Domenech <linkedin.com/in/c-domenech/>
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

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Evento;
import models.Reserva;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Cristina Domenech <linkedin.com/in/c-domenech/>
 */
public class DBManager {

    private static Session s;
    private static Transaction t;

    public DBManager() {
        s = HibernateUtil.getSessionFactory().openSession();
        t = s.beginTransaction();
    }

    public void crearReserva(Reserva r) {
        s.save(r);
        t.commit();
        System.out.println("> Reserva realizada con éxito");
    }

    public void crearEvento(Evento e) {
        s.save(e);
        t.commit();
        System.out.println("> Evento creado con éxito");
    }

    public ObservableList<Evento> listarEventos() {
        ObservableList<Evento> eventos = FXCollections.observableArrayList();
//        List<Evento> result = s.createQuery("FROM Evento").list();
        eventos.addAll(s.createQuery("FROM Evento").list());
        return eventos;
    }

    public ObservableList<Reserva> listarReservasEvento(Evento evento) {
        ObservableList<Reserva> reservas = FXCollections.observableArrayList();
        reservas.addAll(evento.getReservas());
        return reservas;
    }

    public void editarReserva() {

    }

    public void editarEvento() {

    }

    public boolean compruebaEmailReservaExiste(String email) {
        boolean existe;
        List<Integer> result;
        Query query = this.s.createQuery("SELECT r.id_reserva FROM Reserva r WHERE r.email = :email");
        query.setParameter("email", email);
        result = query.list();
        if (result.isEmpty()) {
            existe = false;
        } else {
            existe = true;
        }

        return existe;
    }
}
