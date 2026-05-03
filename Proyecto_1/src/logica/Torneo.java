package logica;
import java.util.HashMap;

public abstract class Torneo {

	private String nombre; 
	private Juego juego; 
	
	private String Dia;
	private int cuposMaximos;
	private int cuposReservados;
	private int cuposReservadosOcupados;
	private HashMap<Usuario, Integer> inscripciones;

	public Torneo(String nombre, Juego juego, int cuposMaximos, String dia) {
	    this.nombre = nombre;
	    this.juego = juego;
	    this.cuposMaximos = cuposMaximos;
	    this.inscripciones = new HashMap<>();

	    this.cuposReservados = (int) Math.ceil(cuposMaximos * 0.2);
	    this.cuposReservadosOcupados = 0;
	}
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Juego getJuego() {
		return juego;
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	public String getDia() {
		return Dia;
	}

	public void setDia(String dia) {
		Dia = dia;
	}

	public int getCuposMaximos() {
		return cuposMaximos;
	}

	public void setCuposMaximos(int cuposMaximos) {
		this.cuposMaximos = cuposMaximos;
	}

	public int getCuposReservados() {
		return cuposReservados;
	}

	public void setCuposReservados(int cuposReservados) {
		this.cuposReservados = cuposReservados;
	}

	public int getCuposReservadosOcupados() {
		return cuposReservadosOcupados;
	}

	public void setCuposReservadosOcupados(int cuposReservadosOcupados) {
		this.cuposReservadosOcupados = cuposReservadosOcupados;
	}
	public HashMap<Usuario, Integer> getInscripciones() {
		return inscripciones;
	}
	public void setInscripciones(HashMap<Usuario, Integer> inscripciones) {
		this.inscripciones = inscripciones;
	}
	
	public void inscribirUsuario(Usuario u, int cantidad) throws Exception {

		if (u instanceof Empleado) {
	        if (((Empleado) u).tieneTurno(Dia)) {
	            throw new Exception("El empleado tiene turno ese día.");
	        }
	    }

	    if (u instanceof Cliente) {
	        int actuales = inscripciones.getOrDefault(u, 0);
	        if (actuales + cantidad > 3) {
	            throw new Exception("Máximo 3 cupos por cliente.");
	        }
	    }

	    int totalActual = getTotalInscritos();
	    if (totalActual + cantidad > cuposMaximos) {
	        throw new Exception("No hay cupos suficientes.");
	    }


	    boolean esFan = u.esFanaticoDe(juego);
	    if (esFan && cuposReservadosOcupados < cuposReservados) {
	        int disponibles = cuposReservados - cuposReservadosOcupados;
	        int usar = Math.min(cantidad, disponibles);
	        cuposReservadosOcupados += usar;
	    } 

	    inscripciones.put(u, inscripciones.getOrDefault(u, 0) + cantidad);
	}
	
	public int getTotalInscritos() {
	    int total = 0;
	    for (int cupos : inscripciones.values()) {
	        total += cupos;
	    }
	    return total;
	}
	
	public abstract void otorgarPremio(Usuario ganador);
	
	
	
	
	public void eliminarInscripcion(Usuario u) throws Exception { 

	    if (!inscripciones.containsKey(u)) {
	        throw new Exception("El usuario no está inscrito en el torneo.");
	    }

	    int cupos = inscripciones.get(u);

	    if (u.esFanaticoDe(juego)) {
	        int aLiberar = Math.min(cupos, cuposReservadosOcupados);
	        cuposReservadosOcupados -= aLiberar;
	    }

	    inscripciones.remove(u);
	}


	
	
	
	
	
}
