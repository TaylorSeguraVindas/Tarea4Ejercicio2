package segura.taylor.bl.persistencia;

import segura.taylor.bl.entidades.Cliente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * La clase DAO se encarga de toda la lógica de acceso a la base de datos ya sea para lectura o escritura
 *
 * @author Taylor Segura Vindas
 * @version 1.0
 * @since 2020-12-06
 */
public class ClienteDAO {
    Connection connection;

    /**
     * Método constructor
     * @param connection instancia de Connection que define la conexion con la base de datos
     */
    public ClienteDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Método usado para guardar un cliente en la base de datos
     * @param nuevoCliente instancia de la clase Cliente que se desea guardar
     * @return true si el registro es exitoso, false si ocurre un error
     * @see Cliente
     */
    public boolean save(Cliente nuevoCliente) {
        try {
            Statement query = connection.createStatement();
            String insert = "INSERT INTO tcliente (nombre, direccion) VALUES ";
            insert += "('" + nuevoCliente.getNombre() + "','";
            insert += nuevoCliente.getDireccion() + "')";

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Método usado para obtener todos los clientes guardados
     * @return lista con todos los clientes guardados
     * @throws SQLException Si hay un problema con el query
     * @see List
     * @see Cliente
     */
    public List<Cliente> findAll() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM tcliente");

        ArrayList<Cliente> listaClientes = new ArrayList<>();

        while (result.next()) {
            Cliente ClienteLeido = new Cliente();
            ClienteLeido.setId(result.getInt("id"));
            ClienteLeido.setNombre(result.getString("nombre"));
            ClienteLeido.setDireccion(result.getString("direccion"));

            listaClientes.add(ClienteLeido);
        }

        return Collections.unmodifiableList(listaClientes);
    }

    /**
     * Método usado para buscar un Cliente en la base de datos usando como filtro su id
     * @param id int que define el id del cliente que se desea encontrar
     * @return objeto de tipo Optional que contiene una instancia de Cliente si se encuentra una coincidencia
     * @throws SQLException Si hay un problema con el query
     * @see Optional
     * @see Cliente
     */
    public Optional<Cliente> findById(int id) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery(("SELECT * FROM tcliente WHERE id = " + id));

        while (result.next()) {
            Cliente ClienteLeido = new Cliente();
            ClienteLeido.setId(result.getInt("id"));
            ClienteLeido.setNombre(result.getString("nombre"));
            ClienteLeido.setDireccion(result.getString("direccion"));

            return Optional.of(ClienteLeido);
        }

        return Optional.empty();
    }
}
