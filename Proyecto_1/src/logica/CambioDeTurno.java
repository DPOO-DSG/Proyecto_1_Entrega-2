package logica;

public class CambioDeTurno {
	private int id;
	private Empleado empleado;
	private Turno turnoOriginal;
	private Turno turnoCambio;
	private String estado;
	
	public CambioDeTurno(int id, Empleado empleado, Turno turnoOriginal, Turno turnoCambio) {
		super();
		this.id = id;
		this.empleado = empleado;
		this.turnoOriginal = turnoOriginal;
		this.turnoCambio = turnoCambio;
		this.estado = "PENDIENTE";
	}
    //Getters and setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	public Turno getTurnoOriginal() {
		return turnoOriginal;
	}
	public void setTurnoOriginal(Turno turnoOriginal) {
		this.turnoOriginal = turnoOriginal;
	}
	public Turno getTurnoCambio() {
		return turnoCambio;
	}
	public void setTurnoCambio(Turno turnoCambio) {
		this.turnoCambio = turnoCambio;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getEstado() {
		return estado;
	}
	public void aprobar() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
