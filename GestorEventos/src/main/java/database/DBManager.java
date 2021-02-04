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

import java.sql.Timestamp;
import java.util.Comparator;
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
 * @author Cristina Domenech <linkedin.com/in/c-domenech github.com/C-Domenech>
 */
public class DBManager {

    // Attributes
    private static final Session s = HibernateUtil.openSession();
    private static Transaction t = s.beginTransaction();

    /**
     * Get from the database all reservation in an specific event
     *
     * @param evento selected event
     * @return ObservableList to populate the TableView
     */
    public ObservableList<Reserva> listarReservasEvento(Evento evento) {
        ObservableList<Reserva> reservas = FXCollections.observableArrayList();
        Query query = s.createQuery("FROM Reserva WHERE id_evento = :id_evento");
        query.setParameter("id_evento", evento.getId_evento());
        reservas.addAll(query.list());
//        reservas.addAll(evento.getReservas());
//        reservas.sort(Comparator.comparing(Reserva::getFecha_inscripcion));
        return reservas;
    }

    /**
     * Save a new reservation
     *
     * @param r reservation created by an user
     */
    public void crearReserva(Reserva r) {
        checkTransaction();
        s.save(r);
        t.commit();
        s.refresh(r);
        System.out.println("> Reserva realizada con éxito");
    }

    /**
     * Edit a specific reservation Set all the parameters and update it in the
     * database
     *
     * @param r
     * @param nombre
     * @param apellido1
     * @param apellido2
     * @param email
     * @param evento
     * @param numAc
     * @param observaciones
     */
    public void editarReserva(Reserva r, String nombre, String apellido1, String apellido2, String email, Evento evento, int numAc, String observaciones) {
        checkTransaction();
        r.setNombre(nombre);
        r.setApellido1(apellido1);
        r.setApellido2(apellido2);
        r.setEmail(email);
        r.setEvento(evento);
        r.setAsistentes(numAc);
        r.setObservaciones(observaciones);
        s.update(r);
        t.commit();
        s.refresh(r);
        System.out.println("> Reserva editada con éxito");
    }

    /**
     * Remove a specific reservation
     *
     * @param r
     */
    public void eliminarReserva(Reserva r) {
        checkTransaction();
        s.delete(r);
        t.commit();
        System.out.println("> Reserva eliminada con éxito");
    }

    /**
     * Check if the email is in a reservation in a specific event
     *
     * @param email
     * @param evento
     * @return if it is there or not
     */
    public boolean compruebaEmailReservaExiste(String email, Evento evento) {
        boolean existe;
        List<Integer> result;
        Query query = s.createQuery("SELECT id_reserva FROM Reserva WHERE email = :email AND id_evento = :id_evento");
        query.setParameter("email", email);
        query.setParameter("id_evento", evento.getId_evento());
        result = query.list();
        if (result.isEmpty()) {
            existe = false;
        } else {
            existe = true;
        }
        return existe;
    }

    /**
     * Get from the database all the future events
     *
     * @return
     */
    public ObservableList<Evento> listarEventosFuturos() {
        ObservableList<Evento> eventos = FXCollections.observableArrayList();
//        List<Evento> listaEventos = s.createQuery("FROM Evento").list();
        eventos.addAll(s.createQuery("FROM Evento WHERE fecha > current_date()").list());
        eventos.sort(Comparator.comparing(Evento::getFecha));
        return eventos;
    }

    /**
     * Get from the database all the events
     *
     * @return
     */
    public ObservableList<Evento> listarEventos() {
        ObservableList<Evento> eventos = FXCollections.observableArrayList();
//        List<Evento> listaEventos = s.createQuery("FROM Evento").list();
        eventos.addAll(s.createQuery("FROM Evento").list());
        eventos.sort(Comparator.comparing(Evento::getFecha));
        return eventos;
    }

    /**
     * Save a new event
     *
     * @param e
     */
    public void crearEvento(Evento e) {
        checkTransaction();
        s.save(e);
        t.commit();
        System.out.println("> Evento creado con éxito");
    }

    /**
     * Edit a specific event Set all the parameters and update it in the
     * database
     *
     * @param e
     * @param nombre
     * @param fecha
     * @param aforo
     */
    public void editarEvento(Evento e, String nombre, Timestamp fecha, int aforo) {
        checkTransaction();
        e.setNombre(nombre);
        e.setFecha(fecha);
        e.setAforo(aforo);
        s.update(e);
        t.commit();
        System.out.println("> Evento editado con éxito");
    }

    /**
     * Remove an specific event
     *
     * @param e
     */
    public void eliminarEvento(Evento e) {
        checkTransaction();
        s.delete(e);
        t.commit();
        System.out.println("> Evento eliminado con éxito");
    }

    public Evento obtenerEvento(Evento evento) {
        Evento e = s.get(Evento.class, evento.getId_evento());
        s.refresh(e);
        return e;
    }

    /**
     * Get the closest event
     *
     * @return
     */
    public Evento datosEventoCercano() {
        Query query = s.createQuery("FROM Evento WHERE fecha > current_date() ORDER BY fecha ASC");
        query.setFirstResult(0);
        query.setMaxResults(1);
        List<Evento> listaEventoCercano = query.list();
        Evento e = listaEventoCercano.get(0);
        return e;
    }

    /**
     * Check if the transaction is active
     */
    public void checkTransaction() {
        if (!t.isActive()) {
            this.t = s.beginTransaction();
        }
    }

}
