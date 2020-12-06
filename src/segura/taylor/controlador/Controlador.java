package segura.taylor.controlador;

import segura.taylor.bl.entidades.*;
import segura.taylor.bl.enums.EnumTipoCuenta;
import segura.taylor.bl.enums.EnumTipoMovimiento;
import segura.taylor.bl.gestor.GestorClientes;
import segura.taylor.bl.gestor.GestorCuentas;
import segura.taylor.bl.gestor.GestorMovimientos;
import segura.taylor.ui.UI;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * La clase Controlador se usa para realizar la comunicación entre
 * el UI y los Gestores
 *
 * @author Taylor Segura Vindas
 * @version 1.0
 * @since 2020-11-22
 */
public class Controlador {
    GestorClientes gestorClientes = new GestorClientes();
    GestorCuentas gestorCuentas = new GestorCuentas();
    GestorMovimientos gestorMovimientos = new GestorMovimientos();

    UI ui = new UI();

    /**
     * Metodo usado para iniciar la ejecucion del programa
     */
    public void iniciarPrograma() {
        //Pruebas
        //pruebaGuardarMateriales();
        //pruebaGuardarUsuarios();

        int opcion;

        do {
            opcion = mostrarMenu();
            procesarOpcion(opcion);
        } while (opcion != 8);
    }

    /**
     * Metodo usado para obtener la accion que desea realizar el usuario
     * @return la opcion seleccionada por el usuario
     */
    private int mostrarMenu() {
        ui.imprimirLinea("Bienvenido, seleccione una opción");
        ui.imprimirLinea("1. Registrar cliente");
        ui.imprimirLinea("2. Listar clientes");
        ui.imprimirLinea("3. Registrar cuenta");
        ui.imprimirLinea("4. Listar cuentas");
        ui.imprimirLinea("5. Buscar cuenta");
        ui.imprimirLinea("6. Realizar movimiento");
        ui.imprimirLinea("7. Listar movimientos");
        ui.imprimirLinea("8. Salir");
        ui.imprimir("Su opcion: ");
        int opcion = ui.leerEntero();
        return opcion;
    }

    /**
     * Metodo usado para determinar la siguiente accion que debe realizar el programa
     * @param opcion entero que define la siguiente accion del programa
     */
    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarCliente();
                break;
            case 2:
                listarClientes();
                break;
            case 3:
                registrarCuenta();
                break;
            case 4:
                listarCuentas();
                break;
            case 5:
                buscarCuenta();
                break;
            case 6:
                registrarMovimiento();
                break;
            case 7:
                listarMovimientos();
                break;
            case 8:
                ui.imprimirLinea("Adios");
                break;
            default:
                ui.imprimirLinea("Opcion invalida");
                break;
        }
    }

    /**
     * Metodo usado para registar clientes
     */
    private void registrarCliente() {
        ui.imprimirLinea("Id: ");
        String id = ui.leerLinea();
        ui.imprimirLinea("Nombre: ");
        String nombre = ui.leerLinea();
        ui.imprimirLinea("Direccion: ");
        String direccion = ui.leerLinea();

        boolean resultado = gestorClientes.guardarCliente(new Cliente(id, nombre, direccion));

        if (resultado) {
            ui.imprimirLinea("Cliente registrado correctamente");
        } else {
            ui.imprimirLinea("Ya existe un cliente con el id especificado");
        }
    }

    /**
     * Metodo usado para listar clientes
     */
    private void listarClientes() {
        List<Cliente> clientes = gestorClientes.listarClientes();

        for (Cliente cliente : clientes) {
            ui.imprimirLinea(cliente.toString());
        }
    }

    /**
     * Metodo usado para registrar cuenta
     */
    private void registrarCuenta() {
        boolean resultado = false;

        //Dueño
        Optional<Cliente> clienteEncontrado;
        Cliente duenno;

        do {
            listarClientes();
            ui.imprimir("Id del cliente dueño: ");
            String idCliente = ui.leerLinea();

            clienteEncontrado = gestorClientes.buscarPorId(idCliente);

            if(!clienteEncontrado.isPresent()) {
                ui.imprimirLinea("No se pudo encontrar ningun cliente con ese id");
            }
        } while (!clienteEncontrado.isPresent());

        duenno = clienteEncontrado.get();

        //Tipo cuenta
        EnumTipoCuenta tipoCuenta = EnumTipoCuenta.CORRIENTE;
        int opcionTipoCuenta = 0;

        do {
            ui.imprimirLinea("Seleccione un tipo de cuenta");
            ui.imprimirLinea("1. Corriente");
            ui.imprimirLinea("2. Ahorro");
            ui.imprimirLinea("3. Ahorro programado");
            ui.imprimir("Su opcion: ");
            opcionTipoCuenta = ui.leerEntero();
        } while (opcionTipoCuenta < 0 || opcionTipoCuenta > 3);

        //Info cuenta
        ui.imprimir("Numero de cuenta: ");
        String numeroCuenta = ui.leerLinea();
        LocalDate fechaApertura = LocalDate.now();

        switch (opcionTipoCuenta) {
            case 1:
                CuentaCorriente cuentaCorriente = new CuentaCorriente(numeroCuenta, fechaApertura, 0.0, duenno);
                resultado = gestorCuentas.guardarcuenta(cuentaCorriente);

                if(resultado) {
                    registrarMovimiento(cuentaCorriente);
                }
                break;
            case 2:
                CuentaAhorro cuentaAhorro = new CuentaAhorro(numeroCuenta, fechaApertura, 0.0, duenno);
                resultado = gestorCuentas.guardarcuenta(cuentaAhorro);
                break;
            case 3:
                Optional<Cuenta> cuentaCorrienteEncontrada;

                do {
                    listarCuentas();
                    ui.imprimir("Numero de la cuenta corriente: ");
                    String idCuentaCorriente = ui.leerLinea();
                    cuentaCorrienteEncontrada = gestorCuentas.buscarPorId(idCuentaCorriente);
                } while (!cuentaCorrienteEncontrada.isPresent());

                CuentaCorriente cuentaCorrienteObjetivo = (CuentaCorriente) cuentaCorrienteEncontrada.get();

                ui.imprimir("Monto debito: ");
                double montoDebito = ui.leerDouble();

                CuentaAhorroProgramado cuentaAhorroProgramado = new CuentaAhorroProgramado(numeroCuenta, fechaApertura, 0.0, montoDebito, duenno, cuentaCorrienteObjetivo);
                resultado = gestorCuentas.guardarcuenta(cuentaAhorroProgramado);
                break;
        }

        if(resultado) {
            ui.imprimirLinea("Cuenta registrada correctamente");
        } else {
            ui.imprimirLinea("Ocurrió un error al registrar la cuenta");
        }
    }

    /**
     * Metodo usado para listar cuentas
     */
    private void listarCuentas() {
        List<Cuenta> cuentas = gestorCuentas.listarCuentas();

        for (Cuenta cuenta : cuentas) {
            ui.imprimirLinea(cuenta.toString());
        }
    }

    /**
     * Metodo usado para buscar una cuenta
     */
    private void buscarCuenta() {
        ui.imprimirLinea("Ingrese el id de la cuenta: ");
        String idCuenta = ui.leerLinea();

        Optional<Cuenta> cuentaEncontrada = gestorCuentas.buscarPorId(idCuenta);

        if(cuentaEncontrada.isPresent()) {
            ui.imprimirLinea(cuentaEncontrada.get().toString());
        }
    }
    /**
     * Metodo usado para registrar movimientos
     */
    //Normal
    private void registrarMovimiento() {
        //Cuenta
        Optional<Cuenta> cuentaCorrienteEncontrada;
        do {
            listarCuentas();
            ui.imprimir("Numero de la cuenta: ");
            String idCuentaCorriente = ui.leerLinea();
            cuentaCorrienteEncontrada = gestorCuentas.buscarPorId(idCuentaCorriente);
        } while (!cuentaCorrienteEncontrada.isPresent());
        Cuenta cuentaModificar = cuentaCorrienteEncontrada.get();

        ui.imprimir("ID movimiento: ");
        String id = ui.leerLinea();

        //Tipo
        int opcionTipo;
        do {
            ui.imprimirLinea("Seleccione el tipo de movimiento");
            ui.imprimirLinea("1. Deposito");
            ui.imprimirLinea("2. Retiro");
            ui.imprimir("Su opcion: ");
            opcionTipo = ui.leerEntero();

            if (opcionTipo < 0 || opcionTipo > 2) {
                ui.imprimirLinea("\n\nOpcion invalida");
            }
        } while (opcionTipo < 0 || opcionTipo > 2);

        EnumTipoMovimiento tipo = EnumTipoMovimiento.DEPOSITO;
        switch (opcionTipo) {
            case 1:
                tipo = EnumTipoMovimiento.DEPOSITO;
                break;
            case 2:
                tipo = EnumTipoMovimiento.RETIRO;
                break;
        }

        //Info
        ui.imprimir("Monto: ");
        double monto = ui.leerDouble();
        ui.imprimir("Descripcion: ");
        String descripcion = ui.leerLinea();

        LocalDate fecha = LocalDate.now();

        //Realizar movimiento
        Movimiento nuevoMovimiento = new Movimiento(id, tipo, fecha, descripcion, monto, cuentaModificar);

        if(cuentaModificar.puedeRealizarMovimiento(nuevoMovimiento)) {
            //Modificar datos en la instancia
            try {
                cuentaModificar.registrarMovimiento(nuevoMovimiento);

                //Actualizar datos de la cuenta en el archivo.
                boolean actualizado = gestorCuentas.modificarCuenta(cuentaModificar);

                if (actualizado) {
                    //Guardar el movimiento en archivo
                    gestorMovimientos.guardarMovimiento(nuevoMovimiento);
                    ui.imprimirLinea("Movimiento exitoso");
                } else {
                    ui.imprimirLinea("Ocurrió un problema al registrar el movimiento");
                }
            } catch (Exception e) {
                ui.imprimirLinea(e.getMessage());
            }
        } else {
            ui.imprimirLinea("No se puede realizar el movimiento");
        }
    }

    /**
     * Metodo usado para registrar el primer movimiento de la cuenta
     * @param cuentaModificar
     */
    //Primer movimiento cuenta corriente
    private void registrarMovimiento(Cuenta cuentaModificar) {
        ui.imprimirLinea("Deposito inicial. Debe ser 50mil colones.");
        //Info
        ui.imprimir("ID movimiento: ");
        String id = ui.leerLinea();
        ui.imprimir("Monto: ");
        double monto = ui.leerDouble();

        //Tipo
        EnumTipoMovimiento tipo = EnumTipoMovimiento.DEPOSITO;
        String descripcion = "Deposito inicial";

        LocalDate fecha = LocalDate.now();

        //Realizar movimiento
        Movimiento nuevoMovimiento = new Movimiento(id, tipo, fecha, descripcion, monto, cuentaModificar);

        if (cuentaModificar.puedeRealizarMovimiento(nuevoMovimiento)) {
            //Modificar datos en la instancia
            try {
                cuentaModificar.registrarMovimiento(nuevoMovimiento);

                //Actualizar datos de la cuenta en el archivo.
                boolean actualizado = gestorCuentas.modificarCuenta(cuentaModificar);

                if (actualizado) {
                    //Guardar el movimiento en archivo
                    gestorMovimientos.guardarMovimiento(nuevoMovimiento);
                    ui.imprimirLinea("Movimiento exitoso");
                } else {
                    ui.imprimirLinea("Ocurrió un problema al registrar el movimiento");
                }
            } catch (Exception e) {
                ui.imprimirLinea(e.getMessage());
            }
        } else {
            ui.imprimirLinea("No se puede realizar el movimiento");
        }
    }

    /**
     * Metodo usado para listar movimientos
     */
    private void listarMovimientos() {
        List<Movimiento> movimientos = gestorMovimientos.listarMovimientos();
        for (Movimiento movimiento : movimientos) {
            ui.imprimirLinea(movimiento.toString());
        }
    }
}
