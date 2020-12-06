package segura.taylor.ui;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * La clase UI se encarga de mostrar informacion al usuario
 *
 * @author Taylor Segura Vindas
 * @version 1.0
 * @since 2020-11-22
 */
public class UI {
    private PrintStream output = new PrintStream(System.out);
    private Scanner input = new Scanner(System.in).useDelimiter("\\n");

    /**
     * Imprime un mensaje en consola
     * @param mensaje el mensaje que se desea imprimir
     */
    public void imprimir(String mensaje) {
        System.out.print(mensaje);
    }

    /**
     * Imprime un mensaje en consola con salto de linea
     * @param mensaje el mensaje que se desea imprimir
     */
    public void imprimirLinea(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Metodo usado para leer informacion digitada por el usuario
     * @return String de la informacion ingresada
     */
    public String leerLinea() {
        return input.next();
    }
    /**
     * Metodo usado para leer informacion digitada por el usuario
     * @return int de la informacion ingresada
     */
    public int leerEntero() {
        return input.nextInt();
    }
    /**
     * Metodo usado para leer informacion digitada por el usuario
     * @return double de la informacion ingresada
     */
    public double leerDouble() {
        return input.nextDouble();
    }
}
