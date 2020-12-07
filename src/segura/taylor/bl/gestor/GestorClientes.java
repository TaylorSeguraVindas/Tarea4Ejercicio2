package segura.taylor.bl.gestor;

import segura.taylor.PropertiesHandler;
import segura.taylor.bl.entidades.Cliente;
import segura.taylor.bl.persistencia.ClienteDAO;

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

public class GestorClientes {
    //ClienteFAO clienteFAO = new ClienteFAO();
    ClienteDAO clienteDAO;
    PropertiesHandler propertiesHandler = new PropertiesHandler();
    Connection connection;

    public GestorClientes() {
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

            clienteDAO = new ClienteDAO(this.connection);
        } catch (Exception e) {
            System.out.println("CANT CONNECT TO DATABASE");
            e.printStackTrace();
        }
    }
    /**
     * Metodo usado para guardar clientes
     * @param nuevoCliente instancia de la clase Cliente que se desea guardar
     * @return true si se hace correctamente, false si ocurre un error
     * @see Cliente
     */
    public boolean guardarCliente(Cliente nuevoCliente) {
        return clienteDAO.save(nuevoCliente);
    }

    /**
     * Metodo usado para listar clientes
     * @return lista con todos los clientes almacenados
     * @see Cliente
     */
    public List<Cliente> listarClientes() {
        try {
            return clienteDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Metodo usado para buscar un cliente basado en su id
     * @param id id del cliente que se desea encontrar
     * @return instancia de la clase Cliente
     * @see Cliente
     */
    public Optional<Cliente> buscarPorId(int id) {
        try {
            return clienteDAO.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
