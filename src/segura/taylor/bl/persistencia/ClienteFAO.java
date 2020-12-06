package segura.taylor.bl.persistencia;

import segura.taylor.bl.entidades.Cliente;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * La clase FAO se encarga de toda la lógica de acceso a los archivos ya sea para lectura o escritura
 *
 * @author Taylor Segura Vindas
 * @version 1.0
 * @since 2020-11-22
 */
public class ClienteFAO {
    private final String directorioClientes = "c:\\dev\\Clientes.csv";

    /**
     * Metodo para guardar un nuevo Cliente en el archivo
     * @param nuevoCliente instancia de la clase Cliente que será almacenado
     * @return true si se realiza correctamente, false si ocurre un error
     * @see Cliente
     */
    public boolean guardarNuevoCliente(Cliente nuevoCliente) {
        boolean idRepetido = buscarPorId(nuevoCliente.getId()).isPresent();

        if(!idRepetido) {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(nuevoCliente.toCSV());

            try {
                Files.write(Paths.get(directorioClientes), lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    /**
     * Metodo usado para leer todos los clientes almacenados
     * @return lista con instancias de la clase Cliente
     * @see Cliente
     */
    public List<Cliente> listarTodos() {
        ArrayList<Cliente> result = new ArrayList<>();
        BufferedReader reader;

        File archivoUsuarios = new File(directorioClientes);
        if(archivoUsuarios.exists()) {
            try {
                reader = new BufferedReader(new FileReader(directorioClientes));
                String currentLine = reader.readLine();
                while (currentLine != null) {
                    String[] datos = currentLine.split(",");
                    result.add(leerClienteCSV(datos));

                    currentLine = reader.readLine();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * Metodo usado para buscar un cliente usando como filtro el id especificado
     * @param id identificador del cliente que se busca
     * @return instancia de la clase Cliente que coincide con el identificador enviado
     * @see Cliente
     */
    public Optional<Cliente> buscarPorId(String id) {
        BufferedReader reader;

        File archivoUsuarios = new File(directorioClientes);
        if(archivoUsuarios.exists()) {
            try {
                reader = new BufferedReader(new FileReader(directorioClientes));
                String currentLine = reader.readLine();
                while (currentLine != null) {
                    String[] datos = currentLine.split(",");
                    //Filtrar por signatura o id.
                    if (datos[0].equals(id)) {
                        return Optional.of(leerClienteCSV(datos));
                    }

                    currentLine = reader.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    /**
     * Metodo para crear instancias de la clase Cliente usando datos leidos de un archivo
     * @param datosLinea arreglo de String con los datos necesarios para inicializar la clase
     * @return una instancia de la clase Cliente
     * @see Cliente
     */
    private Cliente leerClienteCSV(String[] datosLinea){
        return new Cliente(datosLinea);
    }
}
