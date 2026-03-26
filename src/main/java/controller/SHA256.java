/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

//Librerias para importar el criptosistema
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Funcion para encriptar la contraseña
 * @author Luis Morales
 */
public class SHA256 {
    /**
     * Este metodo permite encriptar una contraseña para almacenarla en la base de datos usando el metodo SHA-256
     * @param pass Contraseña sin hashear
     * @return String: Contraseña hasheada
     **/
    public String contraseñaNueva(String pass){
        try{
            //Importar el algoritmo SHA-256
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            //Obtener la contraseña cifrada bit por bit
            byte [] digest = sha.digest(pass.getBytes());
            
            //Convierte la secuencia de Bytes en codigo Hexadecimal
            StringBuilder hexString = new StringBuilder();
            for(byte b: digest){
                hexString.append(String.format("%02x",b));
            } 
            //Regresa el nuevo valor en formato de cadena
            return String.valueOf(hexString);
        }catch(NoSuchAlgorithmException ex){
            throw new RuntimeException(ex);
        }
    }
}
