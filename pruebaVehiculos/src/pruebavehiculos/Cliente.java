package pruebavehiculos;

/**
 * La clase Cliente representa un cliente que interacciona con el
 * concesionario.El cliente solicita y prueba un vehiculo concurrentemente.
 *
 * @author A.Manfredi
 */
public class Cliente extends Thread {

    private final String nombreCliente;
    private final Concesionario concesionario;

    /**
     * Constructor de la clase Cliente.
     *
     * @param nombreCliente El nombre del cliente.
     * @param concesionario El concesionario en el que el cliente intentar�
     * probar un veh�culo.
     */
    public Cliente(String nombreCliente, Concesionario concesionario) {
        this.nombreCliente = nombreCliente;
        this.concesionario = concesionario;
    }

    /**
     * M�todo ejecutado cuando se inicia el hilo del cliente. El cliente
     * solicita un veh�culo, simula el tiempo de prueba y devuelve el veh�culo
     * al concesionario. Utiliza el mecanismo de hilos para realizar las
     * operaciones de manera concurrente.
     */
    @Override
    public void run() {
        try {
            // El cliente solicita un veh�culo al concesionario
            Vehiculo vehiculo = concesionario.asignarVehiculo();

            System.out.println(nombreCliente + " ... probando veh�culo ... " + vehiculo.getIdVehiculo());
            
            // Se simula el tiempo en el cliente prueba el veh�culo.
            Thread.sleep(1000);

            System.out.println(nombreCliente + " ... termino de probar el veh�culo ... " + vehiculo.getIdVehiculo());
            
            // Una vez finalizado el tiempo simulado de prueba se libera el vehiculo.
            concesionario.liberarVehiculo(vehiculo);

        } catch (InterruptedException e) {
            e.getMessage();
        }
    }

}
