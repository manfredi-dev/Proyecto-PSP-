package pruebavehiculos;

import java.util.concurrent.Semaphore;

/**
 * La clase Concesionario representa un concesionario con un n�mero de veh�culos
 * disponibles limitado. La clase maneja la asignaci�n y liberaci�n de los
 * veh�culos de forma concurrente, mediante el uso de un sem�foro para controlar
 * el acceso y la sincronizaci�n de los hilos clientes.
 *
 * @author A.Manfredi
 */
public class Concesionario {

    // Array de veh�culos disponibles en el concesionario
    private final Vehiculo[] vehiculos;

    // Sem�foro que controla el n�mero de veh�culos disponibles.
    private final Semaphore semaforo;

    // Array de booleanos que indica la disponibilidad del veh�culo.
    private final boolean[] enUso;

    /**
     * Constructor de la clase Concesionario. Inicializa el concesionario con
     * una cantidad de veh�culos especificada, tambien establece que cada
     * veh�culo este disponible y crea el sem�foro con el mismo n�mero de
     * permisos de veh�culos disponibles.
     *
     * @param totalVehiculo El n�mero total de vehiculos disponibles en el
     * concesionario.
     */
    public Concesionario(int totalVehiculo) {

        vehiculos = new Vehiculo[totalVehiculo];
        // El estado "true" indica que se aplica un FIFO
        semaforo = new Semaphore(totalVehiculo, true);
        enUso = new boolean[totalVehiculo];

        // Se inicializa los veh�culos y se marcan disponibles.
        for (int i = 0; i < totalVehiculo; i++) {
            vehiculos[i] = new Vehiculo(i + 1); // Se crea los veh�culos.
            enUso[i] = false; // Marca el veh�culo como disponible.
        }
    }

    /**
     * Metodo que asigna un veh�culo disponible a un cliente. Este m�todo
     * encuentra el primer veh�culo disponible y lo asigna al cliente,
     *
     *
     * @return un objeto Vehiculo que representa al veh�culo asignado al
     * cliente.
     * @throws InterruptedException
     */
    public Vehiculo asignarVehiculo() throws InterruptedException {
        // El cliente espera hasta que haya un permiso disponible en el sem�foro.
        semaforo.acquire();
        /*
        Este bloque sincronizado bloquea el objeto Concesionario para que un solo
        hilo cliente pueda ejecutar el bloque de c�digo a la vez.
         */
        synchronized (this) {
            for (int i = 0; i < vehiculos.length; i++) {
                if (!enUso[i]) { // Se busca el primer veh�culo que este disponible
                    enUso[i] = true; // Marca el vehiculo como en uso.
                    return vehiculos[i]; // Cede el veh�culo al cliente.
                }
            }
        }
        return null; //
    }

    /**
     * M�todo para liberar un vehiculo previamente asignado, dejando libre el
     * veh�culo para usarlo de nuevo. Este m�todo asegura que un solo hilo
     * cliente pueda liberar un veh�culo a la vez mediante el bloque
     * sincronizado. Ademas, libera un permiso en el sem�foro,permitiendo que a
     * otro cliente se le pueda asignar un veh�culo.
     *
     * @param vehiculo objeto veh�culo que se libera, previamente asignado a un
     * cliente.
     *
     */
    public void liberarVehiculo(Vehiculo vehiculo) {
        /*
        Este bloque sincronizado bloquea el objeto Concesionario para que un solo
        hilo cliente pueda ejecutar el bloque de c�digo a la vez.
         */
        synchronized (this) {
            for (int i = 0; i < vehiculos.length; i++) {
                if (vehiculos[i].getIdVehiculo() == vehiculo.getIdVehiculo()) {
                    enUso[i] = false; // Marca el veh�culo como disponible.
                    break; 
                }
            }
        }
        // El sem�foro libera el permiso, para que a otro cliente se le pueda asignar el veh�culo.
        semaforo.release();
    }

}
