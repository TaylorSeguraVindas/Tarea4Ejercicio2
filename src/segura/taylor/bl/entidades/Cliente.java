package segura.taylor.bl.entidades;

import segura.taylor.bl.interfaces.SerializableCSV;

public class Cliente implements SerializableCSV {
    //Variables
    private int id;
    private String nombre;
    private String direccion;

    //Propiedades
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    //Constructores
    public Cliente(){}
    /**
     * Metodo constructor usado para crear una instancia basada en texto
     * @param datos array de String con los datos necesarios para crear la instancia
     */
    public Cliente(String[] datos){
        this.id = Integer.parseInt(datos[0]);
        this.nombre = datos[1];
        this.direccion = datos[2];
    }

    /**
     * Metodo constructor
     * @param id String que define el id
     * @param nombre String que define el nombre
     * @param direccion String que define la direccion
     */
    public Cliente(int id, String nombre, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
    }
    /**
     * Metodo constructor para inicializar sin necesidad de un id
     * @param nombre String que define el nombre
     * @param direccion String que define la direccion
     */
    public Cliente(String nombre, String direccion) {
        this.id = 0;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    //Metodos
    @Override
    public String toString() {
        return "Cliente{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

    @Override
    public String toCSV() {
        String datos = this.id + "," +
                this.nombre + "," +
                this.direccion;
        return datos;
    }
}
