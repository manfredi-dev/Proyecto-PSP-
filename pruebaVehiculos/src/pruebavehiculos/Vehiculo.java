package pruebavehiculos;

/**
 * La clase Vehiculo representa un vehiculo en el concesionario.
 *
 * @author A.Manfredi
 */
public class Vehiculo {

    private final int idVehiculo;

    /**
     * Constructor que inicializa el vehículo con un ID.
     *
     * @param idVehiculo el identificador único del vehículo.
     */
    public Vehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    /**
     * Obtiene el identificador del vehículo.
     *
     * @return el ID del vehículo.
     */
    public int getIdVehiculo() {
        return idVehiculo;
    }
}
