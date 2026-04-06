package logica;

public class Mesa {
	
	private int idMesa;
	private int capacidad;
	private boolean ninos;
	private boolean jovenes;
	private Reserva reservaActual;
	//Constructores
	public Mesa(int idMesa, int capacidad, boolean ninos, boolean jovenes, Reserva reservaActual) {
		this.idMesa = idMesa;
		this.capacidad = capacidad;
		this.ninos = ninos;
		this.jovenes = jovenes;
		this.reservaActual = reservaActual;
	}
	//Gettters y setters
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
	public boolean isNinos() {
		return ninos;
	}
	public void setNinos(boolean ninos) {
		this.ninos = ninos;
	}
	public boolean isJovenes() {
		return jovenes;
	}
	public void setJovenes(boolean jovenes) {
		this.jovenes = jovenes;
	}
	public Reserva getReservaActual() {
		return reservaActual;
	}
	public void setReservaActual(Reserva reservaActual) {
		this.reservaActual = reservaActual;
	}
	
	//Metodos
	
	// El estado de disponibilidad se deduce
    public boolean estaDisponible() {
        return this.reservaActual == null;
    }
    //Ocupa la mesa
    public void asignarReserva(Reserva reserva) {
        this.reservaActual = reserva;
    }
    //liberar la mesa
    public void liberarMesa() {
        this.reservaActual = null;
    }
    //saber si la mesa tiene niños
    public boolean tieneNinos() {
        if (reservaActual != null) {
            return reservaActual.isTieneNinos();
        }
        return false;
    }
	
	
	
}
