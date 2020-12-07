package segura.taylor.bl.persistencia;

import segura.taylor.bl.entidades.Movimiento;
import segura.taylor.bl.enums.EnumTipoMovimiento;

import java.sql.*;
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
public class MovimientoDAO {
    private CuentaDAO cuentaDAO;
    Connection connection;

    /**
     * Método constructor
     * @param connection instancia de Connection que define la conexion con la base de datos
     */
    public MovimientoDAO(Connection connection) {
        this.connection = connection;
        this.cuentaDAO = new CuentaDAO(connection);
    }

    /**
     * Método usado para guardar un movimiento en la base de datos
     * @param nuevoMovimiento instancia que se desea guardar
     * @return true si el proceso es exitoso, false si ocurre un error
     * @see Movimiento
     */
    public boolean save(Movimiento nuevoMovimiento) {
        try {
            Statement query = connection.createStatement();
            String insert = "INSERT INTO tmovimiento (tipoMovimiento, fecha, descripcion, monto, idCuentaModificada) VALUES ";
            insert += "('" + nuevoMovimiento.getTipo().toString() + "','";
            insert += Date.valueOf(nuevoMovimiento.getFecha()) + "','";
            insert += nuevoMovimiento.getDescripcion() + "',";
            insert += nuevoMovimiento.getMonto() + ",";
            insert += nuevoMovimiento.getCuentaModificada().getId() + ")";

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Método usado para obtener todas las listas guardadas
     * @return Lista con todos los movimientos guardados
     * @throws SQLException si hay un problema con el query
     */
    public List<Movimiento> findAll() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM tmovimiento");

        ArrayList<Movimiento> listaMovimientos = new ArrayList<>();

        while (result.next()) {
            Movimiento movimientoLeido = new Movimiento();
            movimientoLeido.setId(result.getInt("id"));
            movimientoLeido.setTipo(EnumTipoMovimiento.valueOf(result.getString("tipoMovimiento")));
            movimientoLeido.setFecha(result.getDate("fecha").toLocalDate());
            movimientoLeido.setDescripcion(result.getString("descripcion"));
            movimientoLeido.setMonto(result.getDouble("monto"));
            movimientoLeido.setCuentaModificada(cuentaDAO.findById(result.getString("idCuentaModificada")).get());
            listaMovimientos.add(movimientoLeido);
        }

        return Collections.unmodifiableList(listaMovimientos);
    }

    /**
     * Método usado para buscar un movimiento usando como filtro su id
     * @param id int que define el id del movimiento que se desea encontrar
     * @return objeto de tipo Optional que contiene una instancia de Movimiento si se encuentra una coincidencia
     * @throws SQLException si hay un problema con el query
     * @see Optional
     * @see Movimiento
     */
    public Optional<Movimiento> findById(int id) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM tmovimiento WHERE id = " + id);

        while (result.next()) {
            Movimiento movimientoLeido = new Movimiento();
            movimientoLeido.setId(result.getInt("id"));
            movimientoLeido.setTipo(EnumTipoMovimiento.valueOf(result.getString("tipoMovimiento")));
            movimientoLeido.setFecha(result.getDate("fecha").toLocalDate());
            movimientoLeido.setDescripcion(result.getString("descripcion"));
            movimientoLeido.setMonto(result.getDouble("monto"));
            movimientoLeido.setCuentaModificada(cuentaDAO.findById(result.getString("idCuentaModificada")).get());

            return Optional.of(movimientoLeido);
        }

        return Optional.empty();
    }
}
