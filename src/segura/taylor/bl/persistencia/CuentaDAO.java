package segura.taylor.bl.persistencia;

import segura.taylor.bl.entidades.Cuenta;
import segura.taylor.bl.entidades.CuentaAhorro;
import segura.taylor.bl.entidades.CuentaAhorroProgramado;
import segura.taylor.bl.entidades.CuentaCorriente;
import segura.taylor.bl.enums.EnumTipoCuenta;

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
public class CuentaDAO {
    Connection connection;

    private ClienteDAO clienteDAO;

    /**
     * Método constructor
     * @param connection instancia de Connection que define la conexion con la base de datos
     */
    public CuentaDAO(Connection connection) {
        this.connection = connection;
        this.clienteDAO = new ClienteDAO(connection);
    }

    /**
     * Método usado para guardar una cuenta
     * @param nuevaCuenta instancia que se desea guardar
     * @return id de la cuenta guardada, -1 si ocurre un error
     * @see Cuenta
     */
    public int save(Cuenta nuevaCuenta) {
        String insert;

        if(nuevaCuenta.getTipoCuenta().equals(EnumTipoCuenta.AHORRO_PROGRAMADO)) {
            insert = "INSERT INTO tcuenta (numeroCuenta, tipoCuenta, fechaApertura, saldo, tasaIntereses, idDuenno, montoDebito, cuentaRelacionada) VALUES ";
        } else {
            insert = "INSERT INTO tcuenta (numeroCuenta, tipoCuenta, fechaApertura, saldo, tasaIntereses, idDuenno) VALUES ";
        }

        insert += "('" + nuevaCuenta.getNumeroCuenta() + "','";
        insert += nuevaCuenta.getTipoCuenta().toString() + "','";
        insert += Date.valueOf(nuevaCuenta.getFechaApertura()) + "',";
        insert += nuevaCuenta.getSaldo() + ",";
        insert += nuevaCuenta.getTasaInteres() + ",";
        insert += nuevaCuenta.getDuenno().getId();

        if(nuevaCuenta.getTipoCuenta().equals(EnumTipoCuenta.AHORRO_PROGRAMADO)) {
            CuentaAhorroProgramado cuenta = (CuentaAhorroProgramado) nuevaCuenta;
            insert += "," + cuenta.getMontoDebito() + ",";
            insert += cuenta.getCuentaCorriente().getId() + ")";
        } else {
            insert += ")";
        }

        int key = -1;
        try {
            Statement query = connection.createStatement();
            query.execute(insert, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = query.getGeneratedKeys();

            while (generatedKeys.next()) {
                key = generatedKeys.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return key;
    }

    /**
     * Método usado para actualizar el saldo de una cuenta guardada
     * @param cuentaModificada instancia con los datos actualizados que se desea guardar
     * @return true si el proceso es exitoso, false si ocurre un error
     * @see Cuenta
     */
    public boolean updateSaldo(Cuenta cuentaModificada) {
        try {
            Statement query = connection.createStatement();
            String update = "UPDATE tcuenta SET ";
            update += "saldo = " + cuentaModificada.getSaldo();
            update += " WHERE id = '" + cuentaModificada.getId() + "'";
            query.execute(update);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Método usado para obtener todas las cuentas guardadas
     * @return lista con todas las cuentas guardadas
     * @throws SQLException Si hay un problema con el query
     * @see List
     * @see Cuenta
     */
    public List<Cuenta> findAll() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM tcuenta");

        ArrayList<Cuenta> listaCuentas = new ArrayList<>();

        while (result.next()) {
            EnumTipoCuenta tipoCuenta = EnumTipoCuenta.valueOf(result.getString("tipoCuenta"));

            if(EnumTipoCuenta.CORRIENTE.equals(tipoCuenta)) {
                CuentaCorriente cuentaLeida = new CuentaCorriente();

                cuentaLeida.setId(result.getInt("id"));
                cuentaLeida.setNumeroCuenta(result.getString("numeroCuenta"));
                cuentaLeida.setFechaApertura(result.getDate("fechaApertura").toLocalDate());
                cuentaLeida.setSaldo(result.getDouble("saldo"));
                cuentaLeida.setTasaInteres(CuentaCorriente.constTasaIntereses);
                cuentaLeida.setDuenno(clienteDAO.findById(result.getInt("idDuenno")).get());

                listaCuentas.add(cuentaLeida);

            } else if (EnumTipoCuenta.AHORRO.equals(tipoCuenta)) {
                CuentaAhorro cuentaLeida = new CuentaAhorro();

                cuentaLeida.setId(result.getInt("id"));
                cuentaLeida.setNumeroCuenta(result.getString("numeroCuenta"));
                cuentaLeida.setFechaApertura(result.getDate("fechaApertura").toLocalDate());
                cuentaLeida.setSaldo(result.getDouble("saldo"));
                cuentaLeida.setTasaInteres(CuentaCorriente.constTasaIntereses);
                cuentaLeida.setDuenno(clienteDAO.findById(result.getInt("idDuenno")).get());

                listaCuentas.add(cuentaLeida);

            } else if (EnumTipoCuenta.AHORRO_PROGRAMADO.equals(tipoCuenta)) {
                CuentaAhorroProgramado cuentaLeida = new CuentaAhorroProgramado();

                cuentaLeida.setId(result.getInt("id"));
                cuentaLeida.setNumeroCuenta(result.getString("numeroCuenta"));
                cuentaLeida.setFechaApertura(result.getDate("fechaApertura").toLocalDate());
                cuentaLeida.setSaldo(result.getDouble("saldo"));
                cuentaLeida.setTasaInteres(CuentaCorriente.constTasaIntereses);
                cuentaLeida.setDuenno(clienteDAO.findById(result.getInt("idDuenno")).get());

                listaCuentas.add(cuentaLeida);
            }
        }

        return Collections.unmodifiableList(listaCuentas);
    }

    /**
     * Método usado para buscar una cuenta usando como filtro su id
     * @param id String que define el id o el numero de cuenta de la cuenta que se desea encontrar
     * @return objeto de tipo Optional que contiene una instancia de Cuenta si se encuentra una coincidencia
     * @throws SQLException Si hay un problema con el query
     */
    public Optional<Cuenta> findById(String id) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM tcuenta WHERE id = " + id + " OR numeroCuenta = '" + id + "'");

        while (result.next()) {
            EnumTipoCuenta tipoCuenta = EnumTipoCuenta.valueOf(result.getString("tipoCuenta"));

            if(EnumTipoCuenta.CORRIENTE.equals(tipoCuenta)) {
                CuentaCorriente cuentaLeida = new CuentaCorriente();

                cuentaLeida.setId(result.getInt("id"));
                cuentaLeida.setNumeroCuenta(result.getString("numeroCuenta"));
                cuentaLeida.setFechaApertura(result.getDate("fechaApertura").toLocalDate());
                cuentaLeida.setSaldo(result.getDouble("saldo"));
                cuentaLeida.setTasaInteres(CuentaCorriente.constTasaIntereses);
                cuentaLeida.setDuenno(clienteDAO.findById(result.getInt("idDuenno")).get());

                return Optional.of(cuentaLeida);

            } else if (EnumTipoCuenta.AHORRO.equals(tipoCuenta)) {
                CuentaAhorro cuentaLeida = new CuentaAhorro();

                cuentaLeida.setId(result.getInt("id"));
                cuentaLeida.setNumeroCuenta(result.getString("numeroCuenta"));
                cuentaLeida.setFechaApertura(result.getDate("fechaApertura").toLocalDate());
                cuentaLeida.setSaldo(result.getDouble("saldo"));
                cuentaLeida.setTasaInteres(CuentaCorriente.constTasaIntereses);
                cuentaLeida.setDuenno(clienteDAO.findById(result.getInt("idDuenno")).get());

                return Optional.of(cuentaLeida);

            } else if (EnumTipoCuenta.AHORRO_PROGRAMADO.equals(tipoCuenta)) {
                CuentaAhorroProgramado cuentaLeida = new CuentaAhorroProgramado();

                cuentaLeida.setId(result.getInt("id"));
                cuentaLeida.setNumeroCuenta(result.getString("numeroCuenta"));
                cuentaLeida.setFechaApertura(result.getDate("fechaApertura").toLocalDate());
                cuentaLeida.setSaldo(result.getDouble("saldo"));
                cuentaLeida.setTasaInteres(CuentaCorriente.constTasaIntereses);
                cuentaLeida.setDuenno(clienteDAO.findById(result.getInt("idDuenno")).get());

                return Optional.of(cuentaLeida);
            }
        }

        return Optional.empty();
    }
}
