package segura.taylor.bl.gestor;

import segura.taylor.PropertiesHandler;
import segura.taylor.bl.entidades.Movimiento;
import segura.taylor.bl.persistencia.MovimientoDAO;

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
public class GestorMovimientos {
    //MovimientoFAO movimientoFAO = new MovimientoFAO();
    MovimientoDAO movimientoDAO;
    PropertiesHandler propertiesHandler = new PropertiesHandler();
    Connection connection;

    public GestorMovimientos() {
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

            movimientoDAO = new MovimientoDAO(this.connection);
        } catch (Exception e) {
            System.out.println("CANT CONNECT TO DATABASE");
            e.printStackTrace();
        }
    }

    /**
     * Metodo usado para guardar movimientos
     * @param nuevoMovimiento instancia de la clase Movimiento que se desea guardar
     * @return true si se hace correctamente, false si ocurre un error
     * @see Movimiento
     */
    public boolean guardarMovimiento(Movimiento nuevoMovimiento) {
        return movimientoDAO.save(nuevoMovimiento);
    }

    /**
     * Metodo usado para listar movimientos
     * @return lista con todos los movimientos almacenados
     * @see Movimiento
     */
    public List<Movimiento> listarMovimientos() {
        try {
            return movimientoDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Metodo usado para buscar un movimiento basado en su id
     * @param id id del movimiento que se desea encontrar
     * @return instancia de la clase Movimiento
     * @see Movimiento
     */
    public Optional<Movimiento> buscarPorId(int id) {
        try {
            return movimientoDAO.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
