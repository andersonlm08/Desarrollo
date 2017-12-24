/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.utilidades;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * Clase que encapsula la logica
 * <code>ControlJTextField</code>
 *
 * @author
 * @author Jorge López andersonchiqui1990@hotmail.com
 * @since 10/12/2012
 * @version 2.1
 */
public class ControlJTextField implements KeyListener {

    public static final int ENTERO = 1;
//    public static final int REAL = 2;
    public static final int CADENA = 2;
    private int tipoDato;
    private int longitud;
    private JComponent controlsiguiente = null;

    public ControlJTextField(int tipoDato, int longitud) {
        this.tipoDato = tipoDato;
        this.longitud = longitud;
    }

    public void asignarControlSiguiente(JComponent c) {
        controlsiguiente = c;
    }

    /**
     * Metodo que valida el tipo de datos en la interfaz grafica
     */
    public void keyTyped(KeyEvent e) {

        String letras = "abcdefghijklmnopqrstuvwxyz ";
        String numeros = "1234567890";
        char caracter = e.getKeyChar(); // para leer el caracter correspondiente a la tecla
        caracter = Character.toLowerCase(caracter);// para convertir el caracter a minuscula
        int numero = (int) (caracter);   // guarda el numero ascii correspondiente a la tecla
        if (numero == 10) {
            if (controlsiguiente != null) {
                controlsiguiente.requestFocus();
            }
        }
        if (tipoDato == ENTERO) {
            if (longitud == 0) {
                if (numero > 25 && numeros.indexOf((int) caracter) == -1) {
                    e.consume();
                }
            } else {
                if (numero > 25 && numeros.indexOf((int) caracter) == -1 || ((JTextField) e.getSource()).getText().length() >= longitud) {
                    e.consume();
                }
            }
        }
        if (tipoDato == CADENA) {
            if (longitud == 0) {
                if (numero > 25 && letras.indexOf((int) caracter) == -1) {
                    e.consume();
                }
            } else {
                if (numero > 25 && letras.indexOf((int) caracter) == -1 || ((JTextField) e.getSource()).getText().length() >= longitud) {
                    e.consume();
                }
            }
        }
    }

    /**
     * Metodo que se implementan de la interfaz KeyListener
     */
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Metodo que se implementan de la interfaz KeyListener
     */
    public void keyReleased(KeyEvent e) {
    }
}
