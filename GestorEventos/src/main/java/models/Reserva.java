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

    private int id_reserva;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String email;
    private Timestamp fecha_inscripcion;
    private int n_acompanantes;
    private String observaciones;

    private Evento evento;

    public Reserva() {
    }

    public Reserva(String nombre, String apellido1, String apellido2, String email, Evento evento, int n_acompanantes, String observaciones) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
        this.evento = evento;
        this.n_acompanantes = n_acompanantes;
        this.observaciones = observaciones;
    }

    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getFecha_inscripcion() {
        return fecha_inscripcion;
    }

    public void setFecha_inscripcion(Timestamp fecha_inscripcion) {
        this.fecha_inscripcion = fecha_inscripcion;
    }

    public int getN_acompanantes() {
        return n_acompanantes;
    }

    public void setN_acompanantes(int n_acompanantes) {
        this.n_acompanantes = n_acompanantes;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public int getAsistentesReserva() {
        int asistentesReserva = this.n_acompanantes + 1;
        return asistentesReserva;
    }

    public String getApellidos() {
        String apellidos = this.apellido1 + " " + this.apellido2;
        return apellidos;
    }

    @Override
    public String toString() {
        return "Reserva{" + "id_reserva=" + id_reserva + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", email=" + email + ", evento=" + evento + ", n_acompanantes=" + n_acompanantes + ", observaciones=" + observaciones + '}';
    }

}