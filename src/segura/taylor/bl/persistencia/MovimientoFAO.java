package segura.taylor.bl.persistencia;

import segura.taylor.bl.entidades.Movimiento;

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
public class MovimientoFAO {
    private final String directorioMovimientos = "c:\\dev\\Movimientos.csv";

    private CuentaFAO cuentaFAO = new CuentaFAO();

    /**
     * Metodo para guardar un nuevo Movimiento en el archivo
     * @param nuevoMovimiento instancia de la clase Movimiento que será almacenado
     * @return true si se realiza correctamente, false si ocurre un error
     * @see Movimiento
     */
    public boolean guardarNuevoMovimiento(Movimiento nuevoMovimiento) {
        boolean idRepetido = buscarPorId(nuevoMovimiento.getId()).isPresent();

        if(!idRepetido) {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(nuevoMovimiento.toCSV());

            try {
                Files.write(Paths.get(directorioMovimientos), lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    /**
     * Metodo usado para leer todos los Movimientos almacenados
     * @return lista con instancias de la clase Movimiento
     * @see Movimiento
     */
    public List<Movimiento> listarTodos() {
        ArrayList<Movimiento> result = new ArrayList<>();
        BufferedReader reader;

        File archivoUsuarios = new File(directorioMovimientos);
        if(archivoUsuarios.exists()) {
            try {
                reader = new BufferedReader(new FileReader(directorioMovimientos));
                String currentLine = reader.readLine();
                while (currentLine != null) {
                    String[] datos = currentLine.split(",");
                    result.add(leerMovimientoCSV(datos));

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
     * Metodo usado para buscar un Movimiento usando como filtro el id especificado
     * @param id identificador del Movimiento que se busca
     * @return instancia de la clase Movimiento que coincide con el identificador enviado
     * @see Movimiento
     */
    public Optional<Movimiento> buscarPorId(String id) {
        BufferedReader reader;

        File archivoUsuarios = new File(directorioMovimientos);
        if(archivoUsuarios.exists()) {
            try {
                reader = new BufferedReader(new FileReader(directorioMovimientos));
                String currentLine = reader.readLine();
                while (currentLine != null) {
                    String[] datos = currentLine.split(",");
                    //Filtrar por id.
                    if (datos[0].equals(id)) {
                        return Optional.of(leerMovimientoCSV(datos));
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
     * Metodo para crear instancias de la clase Movimiento usando datos leidos de un archivo
     * @param datosLinea arreglo de String con los datos necesarios para inicializar la clase
     * @return una instancia de la clase Movimiento
     * @see Movimiento
     */
    private Movimiento leerMovimientoCSV(String[] datosLinea){
        Movimiento movimiento = new Movimiento(datosLinea);
        movimiento.setCuentaModificada(cuentaFAO.buscarPorId(datosLinea[5]).get());
        return movimiento;
    }

}
