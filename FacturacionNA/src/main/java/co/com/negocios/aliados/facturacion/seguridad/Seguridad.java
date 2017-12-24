/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.seguridad;

import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 *
 * @author jorge.lopez
 */
public final class Seguridad {
    
    private Seguridad(){
        
    }

//    public static String getPassword() {
//        return "pass1";
//    }
//
//    public static String getPasswordPrueba() {
//        return "pass12";
//    }
//    public static void main(String[] args) {        
//       
//        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
//        
//        String encryptPassword = encriptar(passwordEncryptor, getPasswordPrueba());
//        System.out.println("Pass encriptado: " + encryptPassword);
//        if(validarPassword(passwordEncryptor, encryptPassword)){
//            System.out.println("Pass correcto");
//        }else{
//             System.out.println("Pass erroneo");
//        }
//    }

//    public static String encriptar(StrongPasswordEncryptor passwordEncryptor, String password) {
//        return passwordEncryptor.encryptPassword(password);   
//    }
//
//    public static boolean validarPassword(StrongPasswordEncryptor passwordEncryptor, String encryptPassword){
//        return  passwordEncryptor.checkPassword(getPassword(), encryptPassword);
//    }
//    public static void main(String[] args) {
//
//        Autenticacion autenticacion = new Autenticacion();
//
//        String ingresado = "pass1";
//        String guardado = "QrIL9UuOdxHkYBE1TuCXyAhhpYiTVno3OkfdAyr/tJFjCWNXrvJA5KY8XiA2k+2c";
//
//        if (autenticacion.validarPassword(ingresado, guardado)) {
//            System.out.println("Pass correcto");
//        } else {
//            System.out.println("Pass erroneo");
//        }
//    }

    public static boolean validarPassword(String ingresado, String guardado) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        return passwordEncryptor.checkPassword(ingresado, guardado);
    }

    public static String encriptar(String password) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        return passwordEncryptor.encryptPassword(password);
    }
}
