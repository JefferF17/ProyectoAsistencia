/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Jefferson
 */
public class CConexion {
    Connection conectar = null;
    String usuario = "postgres";
    String contraseña = "0317";
    String bd = "Asistencia";
    String ip = "localhost";
    String puerto = "5432";
    String cadena ="jdbc:postgresql://"+ip+":"+puerto+"/"+bd;
    
    public Connection establecerConexion(){
        try{
            Class.forName("org.postgresql.Driver");
            conectar = DriverManager.getConnection(cadena,usuario,contraseña);
            JOptionPane.showMessageDialog(null,"Se conecto la base de datos");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error al conectar a la base de datos. Error:"+ e.toString());
        }
        return conectar;
    }
            
}
