package segura.taylor.bl.entidades;

import segura.taylor.bl.enums.EnumTipoCuenta;
import segura.taylor.bl.enums.EnumTipoMovimiento;
import segura.taylor.bl.interfaces.SerializableCSV;

import java.time.LocalDate;

public abstract class Cuenta implements SerializableCSV {
    //Variables
    protected EnumTipoCuenta tipoCuenta;
    protected String numeroCuenta;
    protected LocalDate fechaApertura;
    protected double saldo;
    protected double tasaInteres;
    protected Cliente duenno;

    //Propiedades
    public EnumTipoCuenta getTipoCuenta() { return this.tipoCuenta; }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public LocalDate getFechaApertura() {
        return fechaApertura;
    }
    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }
    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public Cliente getDuenno() {
        return duenno;
    }
    public void setDuenno(Cliente duenno) {
        this.duenno = duenno;
    }

    //Constructores
    public Cuenta(){}
    /**
     * Metodo constructor usado para crear una instancia basada en texto
     * @param datos array de String con los datos necesarios para crear la instancia
     */
    public Cuenta(String[] datos){}

    /**
     * Metodo constructor
     * @param numeroCuenta String que define el numero de cuenta
     * @param fechaApertura LocalDate que define la fecha de apertura
     * @param saldo double que define el saldo
     * @param tasaInteres double que define la tasa de interes
     * @param duenno instancia de la clase Cliente que define el dueño
     * @see Cliente
     */
    public Cuenta(String numeroCuenta, LocalDate fechaApertura, double saldo, double tasaInteres, Cliente duenno) {
        this.numeroCuenta = numeroCuenta;
        this.fechaApertura = fechaApertura;
        this.saldo = saldo;
        this.tasaInteres = tasaInteres;
        this.duenno = duenno;
    }

    //Metodos
    @Override
    public String toString() {
        return "Cuenta{" +
                "numeroCuenta=" + numeroCuenta +
                ", fechaApertura=" + fechaApertura +
                ", saldo=" + saldo +
                ", tasaInteres=" + tasaInteres +
                ", duenno=" + duenno +
                '}';
    }

    /**
     * Metodo usado para determinar si un movimiento se puede realizar o no
     * @param pMovimiento instancia de la clase Movimiento que define los cambios que se harán a la cuenta
     * @return true si se puede hacer el movimiento, false si no se puede
     * @see Movimiento
     */
    public abstract boolean puedeRealizarMovimiento(Movimiento pMovimiento);

    /**
     * Metodo usado para modificar el saldo de la cuenta basado en un movimiento
     * @param pMovimiento instancia de la clase Movimiento que define los cambios que se harán a la cuenta
     * @throws Exception se ejecuta si no hay suficiente saldo en la cuenta
     * @see Movimiento
     */
    public void registrarMovimiento(Movimiento pMovimiento) throws Exception {
        if (pMovimiento.getTipo().equals(EnumTipoMovimiento.DEPOSITO)) {
            this.saldo += pMovimiento.getMonto();
        }

        if (pMovimiento.getTipo().equals(EnumTipoMovimiento.RETIRO)) {
            if((this.saldo-pMovimiento.getMonto()) >= 0.0) {
                this.saldo -= pMovimiento.getMonto();
            } else {
                throw new Exception("No hay suficiente saldo en la cuenta para realizar el retiro");
            }
        }
    }
}
