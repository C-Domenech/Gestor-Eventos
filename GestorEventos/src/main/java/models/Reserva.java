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
package models;

import java.sql.Timestamp;

/**
 *
 * @author Cristina Domenech <linkedin.com/in/c-domenech github.com/C-Domenech>
 */
public class Reserva {

    // Attributes
    private int id_reserva;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String email;
    private Timestamp fecha_inscripcion;
    private int asistentes;
    private String observaciones;

    private Evento evento;

    /**
     * Empty constructor
     */
    public Reserva() {
    }

    /**
     * Constructor
     *
     * @param nombre
     * @param apellido1
     * @param apellido2
     * @param email
     * @param evento
     * @param n_acompanantes
     * @param observaciones
     */
    public Reserva(String nombre, String apellido1, String apellido2, String email, Evento evento, int n_acompanantes, String observaciones) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
        this.evento = evento;
        this.asistentes = n_acompanantes;
        this.observaciones = observaciones;
    }

    // Getters & Setters
    /**
     *
     * @return
     */
    public int getId_reserva() {
        return id_reserva;
    }

    /**
     *
     * @param id_reserva
     */
    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     *
     * @param apellido1
     */
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    /**
     *
     * @return
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     *
     * @param apellido2
     */
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public Timestamp getFecha_inscripcion() {
        return fecha_inscripcion;
    }

    /**
     *
     * @param fecha_inscripcion
     */
    public void setFecha_inscripcion(Timestamp fecha_inscripcion) {
        this.fecha_inscripcion = fecha_inscripcion;
    }

    /**
     *
     * @return
     */
    public int getAsistentes() {
        return asistentes;
    }

    /**
     *
     * @param asistentes
     */
    public void setAsistentes(int asistentes) {
        this.asistentes = asistentes;
    }

    /**
     *
     * @return
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     *
     * @param observaciones
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     *
     * @return
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     *
     * @param evento
     */
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    /**
     *
     * @return
     */
    public String getApellidos() {
        String apellidos = this.apellido1 + " " + this.apellido2;
        return apellidos;
    }

    @Override
    public String toString() {
        return "Reserva{" + "id_reserva=" + id_reserva + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", email=" + email + ", evento=" + evento + ", n_acompanantes=" + asistentes + ", observaciones=" + observaciones + '}';
    }

}
