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

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Set;

/**
 *
 * @author Cristina Domenech <linkedin.com/in/c-domenech github.com/C-Domenech>
 */
public class Evento implements Serializable {

    private int id_evento;
    private String nombre;
    private Timestamp fecha;
    private int aforo;

    private Set<Reserva> reservas;

    public Evento() {
    }

    public Evento(String nombre, Timestamp fecha, int aforo) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.aforo = aforo;
    }

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }

    public int getDisponible() {
        int disponible = this.aforo;
        for (Reserva reserva : reservas) {
            disponible -= reserva.getAsistentesReserva();
        }
        return disponible;
    }

    public String getFechaFormatted() {
        String fechaFormatted = new SimpleDateFormat("dd/MM/yy HH:mm").format(this.fecha);
        return fechaFormatted;
    }

    @Override
    public String toString() {
        return nombre + " - " + getFechaFormatted();
    }

}
