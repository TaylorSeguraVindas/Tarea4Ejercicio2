package segura.taylor.bl.gestor;

import segura.taylor.bl.entidades.Cliente;
import segura.taylor.bl.persistencia.ClienteFAO;

import java.util.List;
import java.util.Optional;

/**
 * La clase Gestor se encarga de realizar la comunicación entre FAO y el Controlador
 *
 * @author Taylor Segura Vindas
 * @version 1.0
 * @since 2020-11-22
 */

public class GestorClientes {
    ClienteFAO clienteFAO = new ClienteFAO();

    /**
     * Metodo usado para guardar clientes
     * @param nuevoCliente instancia de la clase Cliente que se desea guardar
     * @return true si se hace correctamente, false si ocurre un error
     * @see Cliente
     */
    public boolean guardarCliente(Cliente nuevoCliente) {
        return clienteFAO.guardarNuevoCliente(nuevoCliente);
    }

    /**
     * Metodo usado para listar clientes
     * @return lista con todos los clientes almacenados
     * @see Cliente
     */
    public List<Cliente> listarClientes() {
        return clienteFAO.listarTodos();
    }

    /**
     * Metodo usado para buscar un cliente basado en su id
     * @param id id del cliente que se desea encontrar
     * @return instancia de la clase Cliente
     * @see Cliente
     */
    public Optional<Cliente> buscarPorId(String id) {
        return clienteFAO.buscarPorId(id);
    }
}
