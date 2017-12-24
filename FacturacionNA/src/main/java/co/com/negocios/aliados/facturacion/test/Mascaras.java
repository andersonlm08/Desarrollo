/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.test;

/**
 *
 * @author jorge.lopez
 */
import java.text.ParseException;
 
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
 
@SuppressWarnings("serial")
public class Mascaras extends JFrame {
 
    public Mascaras() {
        add(ingresarContrasenia());
        add(ingresarUsuario());
        add(jtaUsuario());
        // Le enviamos la mascara a nuestro JFormattedTextField
        add(jtaContrasenia(mascara()));
        inicializador();
    }
 
    // Inicializador de la Ventana
    private void inicializador() {
        setSize(400, 200);
        setLayout(null);
        setTitle("Login");
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
 
    // Texto que indica donde va el nombre de Usuario
    private JLabel ingresarUsuario() {
        JLabel usuario = new JLabel("Nombre de Usuario: ");
        usuario.setBounds(30, 30, 200, 20);
        return usuario;
    }
 
    // Texto que indica donde va la contraña
    private JLabel ingresarContrasenia() {
        JLabel contrasenia = new JLabel("Contrasenia: ");
        contrasenia.setBounds(30, 80, 200, 20);
        return contrasenia;
    }
 
    // Espacio para ingresar el nombre de usuario
    private JTextField jtaUsuario() {
        JTextField usuario = new JTextField();
        usuario.setBounds(200, 30, 150, 20);
        usuario.setToolTipText("Ingrese Nombre de Usuario");
        return usuario;
    }
 
    // Espacio para ingresar el nombre de usuario
    private JFormattedTextField jtaContrasenia(MaskFormatter mascara) {
        // Se inicializa el objeto
        JFormattedTextField contrasenia = new JFormattedTextField(mascara);
        // Asignamos posición y tamaño
        contrasenia.setBounds(200, 80, 150, 20);
        // Le colocamos un texto emergente
        contrasenia.setToolTipText("Ingrese la contrasenia");
        return contrasenia;
    }
 
    // Mascara para nuestra contraseña
    private MaskFormatter mascara() {
        // Inicializamos el objeto
        MaskFormatter mascara = new MaskFormatter();
        // Entramos en un try/catch por alguna eventualidad
        try {
            // Creamos el formato de nuestra contraseña
            // # -> un número U -> letra mayúscula L -> letra minúscula
            // * -> cualquier caracter ' -> caracteres de escape
            // A -> cualquier letra o número ? -> cualquier caracter
            // H -> cualquier caracter hexagonal (0-9, a-f or A-F).
            mascara = new MaskFormatter("A#####");
       } catch (ParseException e) {
           // Algún error que pueda ocurrir
           e.printStackTrace();
       }
       return mascara;
    }
 
//    public static void main(String[] args) {
//        new Mascaras();
//   }
 
}
