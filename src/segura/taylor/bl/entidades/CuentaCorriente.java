package segura.taylor.bl.entidades;

import segura.taylor.bl.enums.EnumTipoCuenta;
import java.time.LocalDate;

public class CuentaCorriente extends Cuenta {
    //Constantes
    public static final double constTasaIntereses = 0.14;
    public static final double constMinDepositoInicial = 50000.0;

    //Variables
    //Propiedades

    //Constructores
    public CuentaCorriente() {
        this.tipoCuenta = EnumTipoCuenta.CORRIENTE;
        this.tasaInteres = CuentaCorriente.constTasaIntereses;
    }
    /**
     * Metodo constructor usado para crear una instancia basada en texto
     * @param datos array de String con los datos necesarios para crear la instancia
     */
    public CuentaCorriente(String[] datos) {
        this.tipoCuenta = EnumTipoCuenta.CORRIENTE;
        this.numeroCuenta = datos[1];
        this.fechaApertura = LocalDate.parse(datos[2]);
        this.saldo = Double.parseDouble(datos[3]);

        this.tasaInteres = CuentaCorriente.constTasaIntereses;
    }

    /**
     * Metodo constructor
     * @param numeroCuenta String que define el numero de cuenta
     * @param fechaApertura LocalDate que define la fecha de apertura
     * @param saldo double que define el saldo
     * @param duenno instancia de la clase Cliente que define al dueño
     * @see Cliente
     */
    public CuentaCorriente(String numeroCuenta, LocalDate fechaApertura, double saldo, Cliente duenno) {
        super(numeroCuenta, fechaApertura, saldo, CuentaCorriente.constTasaIntereses, duenno);
        this.tipoCuenta = EnumTipoCuenta.CORRIENTE;
    }

    /**
     * Metodo usado para determinar si un movimiento se puede realizar o no
     * @param pMovimiento instancia de la clase Movimiento que define los cambios que se harán a la cuenta
     * @return true si se puede hacer el movimiento, false si no se puede
     * @see Movimiento
     */
    @Override
    public boolean puedeRealizarMovimiento(Movimiento pMovimiento) {
        return true;
    }

    @Override
    public String toString() {
        return "CuentaCorriente{" +
                "numeroCuenta='" + numeroCuenta + '\'' +
                ", fechaApertura=" + fechaApertura +
                ", saldo=" + saldo +
                ", tasaInteres=" + tasaInteres +
                ", duenno=" + duenno +
                '}';
    }

    @Override
    public String toCSV() {
        String datos = this.tipoCuenta + "," +
                this.numeroCuenta + "," +
                this.fechaApertura.toString() + "," +
                this.saldo + "," +
                this.duenno.getId();
        return datos;
    }
}
