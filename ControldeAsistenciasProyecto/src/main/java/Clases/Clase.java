/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Jefferson
 */
public class Clase {

    //Metodo para agregar clase
    public void AgregarClase(JTextField id, JTextField fecha, JTextField identificacion, JTextField horarioid, JTextField codigocurso,JTextField foto) throws ParseException {
        //Establecemos la conexion con nuestra base de datos
        Conexion.CConexion conexion = new Conexion.CConexion();
        //Guardamos nuestra consulta 
        String consulta = ("select insertarclase(?,?,?,?,?);");

        try {
            //Estos campos son para hacer la transformacion de un Date a un Sql Date
            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
            Date fechaN = formato.parse(fecha.getText());
            java.sql.Date fechasql = new java.sql.Date(fechaN.getTime());
            //Establecemos la conexion y preparamos la consulta
            CallableStatement cs = conexion.establecerConexion().prepareCall(consulta);
            //Escribimos el numero,el tipo de dato y el dato para la insercion  
            cs.setInt(1, Integer.parseInt(id.getText()));
            cs.setDate(2, fechasql);
            cs.setInt(3, Integer.parseInt(identificacion.getText()));
            cs.setInt(4, Integer.parseInt(horarioid.getText()));
            cs.setInt(5, Integer.parseInt(codigocurso.getText()));
            //Ejecutamos la Consulta
            cs.execute();
            //Si la consulta se añade nos mostrara este panel
            JOptionPane.showMessageDialog(null, "Insertó correctamente la clase, VERIFIQUE");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se guardó correctamente los datos" + ex.toString());

        }

    }
}
