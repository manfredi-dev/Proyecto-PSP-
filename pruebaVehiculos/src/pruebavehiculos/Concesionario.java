package pruebavehiculos;

import java.util.concurrent.Semaphore;

/**
 * La clase Concesionario representa un concesionario con un número de vehículos
 * disponibles limitado. La clase maneja la asignación y liberación de los
 * vehículos de forma concurrente, mediante el uso de un semáforo para controlar
 * el acceso y la sincronización de los hilos clientes.
 *
 * @author A.Manfredi
 */
public class Concesionario {

    // Array de vehículos disponibles en el concesionario
    private final Vehiculo[] vehiculos;

    // Semáforo que controla el número de vehículos disponibles.
    private final Semaphore semaforo;

    // Array de booleanos que indica la disponibilidad del vehículo.
    private final boolean[] enUso;

    /**
     * Constructor de la clase Concesionario. Inicializa el concesionario con
     * una cantidad de vehículos especificada, tambien establece que cada
     * vehículo este disponible y crea el semáforo con el mismo número de
     * permisos de vehículos disponibles.
     *
     * @param totalVehiculo El número total de vehiculos disponibles en el
     * concesionario.
     */
    public Concesionario(int totalVehiculo) {

        vehiculos = new Vehiculo[totalVehiculo];
        // El estado "true" indica que se aplica un FIFO
        semaforo = new Semaphore(totalVehiculo, true);
        enUso = new boolean[totalVehiculo];

        // Se inicializa los vehículos y se marcan disponibles.
        for (int i = 0; i < totalVehiculo; i++) {
            vehiculos[i] = new Vehiculo(i + 1); // Se crea los vehículos.
            enUso[i] = false; // Marca el vehículo como disponible.
        }
    }

    /**
     * Metodo que asigna un vehículo disponible a un cliente. Este método
     * encuentra el primer vehículo disponible y lo asigna al cliente,
     *
     *
     * @return un objeto Vehiculo que representa al vehículo asignado al
     * cliente.
     * @throws InterruptedException
     */
    public Vehiculo asignarVehiculo() throws InterruptedException {
        // El cliente espera hasta que haya un permiso disponible en el semáforo.
        semaforo.acquire();
        /*
        Este bloque sincronizado bloquea el objeto Concesionario para que un solo
        hilo cliente pueda ejecutar el bloque de código a la vez.
         */
        synchronized (this) {
            for (int i = 0; i < vehiculos.length; i++) {
                if (!enUso[i]) { // Se busca el primer vehículo que este disponible
                    enUso[i] = true; // Marca el vehiculo como en uso.
                    return vehiculos[i]; // Cede el vehículo al cliente.
                }
            }
        }
        return null; //
    }

    /**
     * Método para liberar un vehiculo previamente asignado, dejando libre el
     * vehículo para usarlo de nuevo. Este método asegura que un solo hilo
     * cliente pueda liberar un vehículo a la vez mediante el bloque
     * sincronizado. Ademas, libera un permiso en el semáforo,permitiendo que a
     * otro cliente se le pueda asignar un vehículo.
     *
     * @param vehiculo objeto vehículo que se libera, previamente asignado a un
     * cliente.
     *
     */
    public void liberarVehiculo(Vehiculo vehiculo) {
        /*
        Este bloque sincronizado bloquea el objeto Concesionario para que un solo
        hilo cliente pueda ejecutar el bloque de código a la vez.
         */
        synchronized (this) {
            for (int i = 0; i < vehiculos.length; i++) {
                if (vehiculos[i].getIdVehiculo() == vehiculo.getIdVehiculo()) {
                    enUso[i] = false; // Marca el vehículo como disponible.
                    break; 
                }
            }
        }
        // El semáforo libera el permiso, para que a otro cliente se le pueda asignar el vehículo.
        semaforo.release();
    }

}
