
package pruebavehiculos;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase principal que simula la prueba de vehiculos en un concesionario.
 * @author A.Manfredi
 */
public class PruebaVehiculos {

    /**
     * Método principal que ejecuta la simulación.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Concesionario con 4 vehículos
        Concesionario concesionario = new Concesionario(4); 

        // Se crea los 9 clientes que utilizan los vehículos.
        Cliente[] clientes = new Cliente[9];
        for (int i = 0; i < clientes.length; i++) {
            clientes[i] = new Cliente("Cliente " + (i + 1), concesionario);
            clientes[i].start();// Se inicia el hilo.
        }

        // Este bucle se espera hasta que cada hilo cliente actual se ha finalizado de ejecutar.
        for (Cliente cliente : clientes) {
            try {
                cliente.join();
            } catch (InterruptedException e) {
                Logger.getLogger(PruebaVehiculos.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        System.out.println("Todos los clientes han terminado de probar los vehículos.");
    }
}
