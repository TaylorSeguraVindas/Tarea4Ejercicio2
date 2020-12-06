package segura.taylor.bl.gestor;

import segura.taylor.bl.entidades.Cuenta;
import segura.taylor.bl.persistencia.CuentaFAO;

import java.util.List;
import java.util.Optional;

/**
 * La clase Gestor se encarga de realizar la comunicación entre FAO y el Controlador
 *
 * @author Taylor Segura Vindas
 * @version 1.0
 * @since 2020-11-22
 */
public class GestorCuentas {
    CuentaFAO cuentaFAO = new CuentaFAO();

    /**
     * Metodo usado para guardar cuentas
     * @param nuevocuenta instancia de la clase Cuenta que se desea guardar
     * @return true si se hace correctamente, false si ocurre un error
     * @see Cuenta
     */
    public boolean guardarcuenta(Cuenta nuevocuenta) {
        return cuentaFAO.guardarNuevaCuenta(nuevocuenta);
    }

    /**
     * Metodo usado para modificar cuentas
     * @param pCuenta instancia de la clase Cuenta que se desea modificar
     * @return true si se hace correctamente, false si ocurre un error
     * @see Cuenta
     */
    public boolean modificarCuenta(Cuenta pCuenta) {
        return cuentaFAO.modificarCuenta(pCuenta);
    }

    /**
     * Metodo usado para listar cuentas
     * @return lista con todos los cuentas almacenados
     * @see Cuenta
     */
    public List<Cuenta> listarCuentas() {
        return cuentaFAO.listarTodas();
    }

    /**
     * Metodo usado para buscar una cuenta basado en su id
     * @param id id de la cuenta que se desea encontrar
     * @return instancia de la clase Cuenta
     * @see Cuenta
     */
    public Optional<Cuenta> buscarPorId(String id) {
        return cuentaFAO.buscarPorId(id);
    }
}
