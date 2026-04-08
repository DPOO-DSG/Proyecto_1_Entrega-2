package logica;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Mesa {
    private int idMesa;
    private int capacidad;
    private ArrayList<Reserva> agenda; // Lista de todas sus reservas futuras

    public Mesa(int idMesa, int capacidad) {
        this.idMesa = idMesa;
        this.capacidad = capacidad;
        this.agenda = new ArrayList<>();
    }
    
    public int getIdMesa() {
		return idMesa;
	}

	public void setIdMesa(int idMesa) {
		this.idMesa = idMesa;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public ArrayList<Reserva> getAgenda() {
		return agenda;
	}

	public void setAgenda(ArrayList<Reserva> agenda) {
		this.agenda = agenda;
	}

    public boolean estaDisponibleEnFecha(LocalDateTime fechaSolicitada) {
        for (Reserva r : agenda) {
            
            if (r.getFechaReserva().equals(fechaSolicitada)) {
                return false; 
            }
        }
        return true; 
    }

    public void agregarReserva(Reserva nuevaReserva) {
        this.agenda.add(nuevaReserva);
    }

	public boolean tieneMenores() {
		// TODO Auto-generated method stub
		return false;
	}
}