package segura.taylor.bl.gestor;

import segura.taylor.PropertiesHandler;
import segura.taylor.bl.entidades.Cuenta;
import segura.taylor.bl.persistencia.CuentaDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * La clase Gestor se encarga de realizar la comunicación entre DAO y el Controlador
 *
 * @author Taylor Segura Vindas
 * @version 1.0
 * @since 2020-11-22
 */
public class GestorCuentas {
    //CuentaFAO cuentaFAO = new CuentaFAO();
    CuentaDAO cuentaDAO;
    PropertiesHandler propertiesHandler = new PropertiesHandler();
    Connection connection;

    public GestorCuentas() {
        try {
            propertiesHandler.loadProperties();
            //ABRIR DB
            String driver = propertiesHandler.getDriver();
            try {
                Class.forName(driver).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("LOADED DRIVER ---> " + driver);
            String url = propertiesHandler.getCnxStr();
            this.connection = DriverManager.getConnection(url, propertiesHandler.getUser(), propertiesHandler.getPwd());
            System.out.println("CONNECTED TO ---> "+ url);

            cuentaDAO = new CuentaDAO(this.connection);
        } catch (Exception e) {
            System.out.println("CANT CONNECT TO DATABASE");
            e.printStackTrace();
        }
    }
    /**
     * Metodo usado para guardar cuentas
     * @param nuevocuenta instancia de la clase Cuenta que se desea guardar
     * @return true si se hace correctamente, false si ocurre un error
     * @see Cuenta
     */
    public int guardarcuenta(Cuenta nuevocuenta) {
        return cuentaDAO.save(nuevocuenta);
    }

    /**
     * Metodo usado para modificar cuentas
     * @param pCuenta instancia de la clase Cuenta que se desea modificar
     * @return true si se hace correctamente, false si ocurre un error
     * @see Cuenta
     */
    public boolean modificarCuenta(Cuenta pCuenta) {
        return cuentaDAO.updateSaldo(pCuenta);
    }

    /**
     * Metodo usado para listar cuentas
     * @return lista con todos los cuentas almacenados
     * @see Cuenta
     */
    public List<Cuenta> listarCuentas() {
        try {
            return cuentaDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Metodo usado para buscar una cuenta basado en su id
     * @param id id de la cuenta que se desea encontrar
     * @return instancia de la clase Cuenta
     * @see Cuenta
     */
    public Optional<Cuenta> buscarPorId(String id) {
        try {
            return cuentaDAO.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
