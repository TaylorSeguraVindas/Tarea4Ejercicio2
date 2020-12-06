package segura.taylor.bl.gestor;

import segura.taylor.bl.entidades.Movimiento;
import segura.taylor.bl.persistencia.MovimientoFAO;

import java.util.List;
import java.util.Optional;

/**
 * La clase Gestor se encarga de realizar la comunicación entre FAO y el Controlador
 *
 * @author Taylor Segura Vindas
 * @version 1.0
 * @since 2020-11-22
 */
public class GestorMovimientos {
    MovimientoFAO movimientoFAO = new MovimientoFAO();

    /**
     * Metodo usado para guardar movimientos
     * @param nuevoMovimiento instancia de la clase Movimiento que se desea guardar
     * @return true si se hace correctamente, false si ocurre un error
     * @see Movimiento
     */
    public boolean guardarMovimiento(Movimiento nuevoMovimiento) {
        return movimientoFAO.guardarNuevoMovimiento(nuevoMovimiento);
    }

    /**
     * Metodo usado para listar movimientos
     * @return lista con todos los movimientos almacenados
     * @see Movimiento
     */
    public List<Movimiento> listarMovimientos() {
        return movimientoFAO.listarTodos();
    }

    /**
     * Metodo usado para buscar un movimiento basado en su id
     * @param id id del movimiento que se desea encontrar
     * @return instancia de la clase Movimiento
     * @see Movimiento
     */
    public Optional<Movimiento> buscarPorId(String id) {
        return movimientoFAO.buscarPorId(id);
    }
}
