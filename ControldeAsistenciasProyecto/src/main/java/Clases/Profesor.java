/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Jefferson
 */
public class Profesor {

    //Metodo para agregar Profesor a nuestra base de datos
    public void AgregarProfesor(JTextField identificacion, JTextField nombre, JTextField apellidos) {
        //Hacemos un objeto de tipo conexion 
        Conexion.CConexion conexion = new Conexion.CConexion();
        //Escribimos y guardamos la consulta
        String consulta = ("select insertarprofesor(?,?,?);");

        try {
            //Establecemos conexion y preparamos la consulta
            CallableStatement cs = conexion.establecerConexion().prepareCall(consulta);
            //Escribimos el numero,el tipo de dato y el dato para la insercion  
            cs.setInt(1, Integer.parseInt(identificacion.getText()));
            cs.setString(2, nombre.getText());
            cs.setString(3, apellidos.getText());
            //Ejecutamos la consulta 
            cs.execute();
            //Ejecucion correctamente
            JOptionPane.showMessageDialog(null, "Insertó correctamente el profesor, VERIFIQUE SU BASE DE DATOS");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se guardó correctamente los datos" + ex.toString());

        }

    }

    public void MostrarProfesor(int identificacion, JTable tablaprofesor) {
        //Creamos un objeto de tipo conexion 
        Conexion.CConexion conexion = new Conexion.CConexion();
        //Creamos un objeto modelo default
        DefaultTableModel modelo = new DefaultTableModel();
        //Ordenamos la tabla
        TableRowSorter<TableModel> OrdenaTabla = new TableRowSorter<TableModel>(modelo);
        tablaprofesor.setRowSorter(OrdenaTabla);
        //Añadimos las columnas de la tabla
        modelo.addColumn("Identificacion");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellidos");
        //Creamos una consulta
        String codigosql;
        //Asignamos el modelo a la tabla
        tablaprofesor.setModel(modelo);
        //Escribimos la consulta 
        codigosql = "SELECT * FROM Profesor WHERE identificacion = '" + identificacion + "'";
        //Creamos un arreglo de datos 
        String[] datos = new String[3];
        Statement st;

        try {
            //Establecemos la conexion 
            st = conexion.establecerConexion().createStatement();
            //Ejecutamos la consulta
            ResultSet rs = st.executeQuery(codigosql);

            while (rs.next()) {

                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);

                modelo.addRow(datos);

            }

            tablaprofesor.setModel(modelo);

        } catch (SQLException ex) {
            System.out.print("Error: " + ex.toString());
        }

    }
}
