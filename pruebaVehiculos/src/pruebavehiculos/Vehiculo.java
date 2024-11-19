package pruebavehiculos;

/**
 * La clase Vehiculo representa un vehiculo en el concesionario.
 *
 * @author A.Manfredi
 */
public class Vehiculo {

    private final int idVehiculo;

    /**
     * Constructor que inicializa el veh�culo con un ID.
     *
     * @param idVehiculo el identificador �nico del veh�culo.
     */
    public Vehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    /**
     * Obtiene el identificador del veh�culo.
     *
     * @return el ID del veh�culo.
     */
    public int getIdVehiculo() {
        return idVehiculo;
    }
}
