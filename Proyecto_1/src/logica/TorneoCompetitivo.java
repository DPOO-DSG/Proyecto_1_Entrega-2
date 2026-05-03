package logica;

import java.io.Serializable;

public class TorneoCompetitivo extends Torneo implements Serializable {
    
    private int costoEntrada;
    private int premio;

    public TorneoCompetitivo(String nombre, Juego juego, int cuposMaximos, String dia,
            int costoEntrada, int premio) {
        super(nombre, juego, cuposMaximos, dia);
        this.costoEntrada = costoEntrada;
        this.premio = premio;
    }

    @Override
    public void inscribirUsuario(Cafe cafe, Usuario u, int cantidad) throws Exception {
        super.inscribirUsuario(cafe, u, cantidad);

        if (u instanceof Cliente) {
            Cliente c = (Cliente) u;
            int subtotalInscripcion = this.costoEntrada * cantidad;
            
            int numeroFactura = cafe.getRegistroVentas().size() + 1;
            
            CompraVenta factura = new CompraVenta(numeroFactura, c, 0.0, null);
            
            factura.setSubtotal(subtotalInscripcion);
            factura.calcularValores(); 
            
            cafe.getRegistroVentas().put(numeroFactura, factura);
            
            double nuevosPuntos = factura.getTotal() * 0.01;
            c.setPuntosFidelidad(c.getPuntosFidelidad() + nuevosPuntos);
            
            System.out.println("Inscripción exitosa. Se ha generado la Factura #" + numeroFactura 
                             + " por un total de $" + factura.getTotal() + " (IVA incluido).");
                             
        } else if (u instanceof Empleado) {
            System.out.println("Inscripción de empleado confirmada. Entrada gratuita aplicada, no se generó factura.");
        }
    }

    @Override
    public void otorgarPremio(Usuario ganador) {
        if (ganador instanceof Cliente) {
            System.out.println("¡El cliente '" + ganador.getLogin() + "' ha ganado!");
            System.out.println("Entregar premio en metálico por valor de: $" + this.premio);
        } else if (ganador instanceof Empleado) {
            System.out.println("El empleado '" + ganador.getLogin() + "' ha ganado.");
            System.out.println("Por políticas internas, los empleados no reciben premios en metálico.");
        }
    }
}