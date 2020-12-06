package segura.taylor.bl.entidades;

import segura.taylor.bl.enums.EnumTipoMovimiento;
import segura.taylor.bl.interfaces.SerializableCSV;

import java.time.LocalDate;

public class Movimiento implements SerializableCSV {
    //Variables
    private String id;
    private EnumTipoMovimiento tipo;
    private LocalDate fecha;
    private String descripcion;
    private double monto;
    private Cuenta cuentaModificada;

    //Propiedades
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public EnumTipoMovimiento getTipo() {
        return tipo;
    }
    public void setTipo(EnumTipoMovimiento tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Cuenta getCuentaModificada() {
        return cuentaModificada;
    }
    public void setCuentaModificada(Cuenta cuentaModificada) {
        this.cuentaModificada = cuentaModificada;
    }

    //Constructores
    public Movimiento(){}
    /**
     * Metodo constructor usado para crear una instancia basada en texto
     * @param datos array de String con los datos necesarios para crear la instancia
     */
    public Movimiento(String[] datos){
        this.id = datos[0];
        this.tipo = EnumTipoMovimiento.valueOf(datos[1]);
        this.fecha = LocalDate.parse(datos[2]);
        this.descripcion = datos[3];
        this.monto = Double.parseDouble(datos[4]);
    }

    /**
     * Metodo constructor
     * @param id String que define el id
     * @param tipo valor de EnumTipoMovimiento que define el tipo de movimiento
     * @param fecha LocalDate que define la fecha de realización
     * @param descripcion String que define la descripcion
     * @param monto double que define el monto
     * @param cuentaModificada instancia de la clase Cuenta que define la cuenta que se va a modificar
     * @see EnumTipoMovimiento
     * @see Cuenta
     */
    public Movimiento(String id, EnumTipoMovimiento tipo, LocalDate fecha, String descripcion, double monto, Cuenta cuentaModificada) {
        this.id = id;
        this.tipo = tipo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.monto = monto;
        this.cuentaModificada = cuentaModificada;
    }

    //Metodos
    @Override
    public String toString() {
        return "Movimiento{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", fecha=" + fecha +
                ", descripcion='" + descripcion + '\'' +
                ", monto=" + monto +
                ", cuenta=" + cuentaModificada.toString() +
                '}';
    }

    @Override
    public String toCSV() {
        String datos = this.id + "," +
                this.tipo.toString() + "," +
                this.fecha.toString().toString() + "," +
                this.descripcion + "," +
                this.monto + "," +
                this.cuentaModificada.getNumeroCuenta();
        return datos;
    }
}
