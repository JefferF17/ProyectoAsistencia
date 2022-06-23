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
public class Programa {

    //Metodo para mostrar programas
    public void MostrarProgramas(JTable tablaprogramas) {
        //Creamos un objeto de tipo conexion 
        Conexion.CConexion conexion = new Conexion.CConexion();
        //Creamos un modelo 
        DefaultTableModel modelo = new DefaultTableModel();
        //Ordenamos la tabla 
        TableRowSorter<TableModel> OrdenaTabla = new TableRowSorter<TableModel>(modelo);
        tablaprogramas.setRowSorter(OrdenaTabla);
        //Creamos la consulta
        String sql = "";
        //Añadimos las columnas 
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombre");
        modelo.addColumn("Jornada");
        //Asignamos el modelo
        tablaprogramas.setModel(modelo);
        //Escribimos la consulta
        sql = "select * from totalprogramas;";
        //Creamos un arreglo
        String[] datos = new String[3];
        Statement st;

        try {
            //Establecemos la conexion 
            st = conexion.establecerConexion().createStatement();
            //Ejecutamos la consulta
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);

                modelo.addRow(datos);

            }

            tablaprogramas.setModel(modelo);

        } catch (SQLException ex) {
            System.out.print("Error: " + ex.toString());
        }

    }
    //Metodo para Agregar Programa
    public void AgregarPrograma(JTextField codigo, JTextField nombre, JTextField Jornada) {
        //Establecemos la conexion con la base de datos
        Conexion.CConexion conexion = new Conexion.CConexion();
        //Escribimos y guardamos nuestra consulta 
        String consulta = ("select insertarprograma(?,?,?);");

        try {
            //Establecemos y preparamos la consulta
            CallableStatement cs = conexion.establecerConexion().prepareCall(consulta);
            //Escribimos el numero,el tipo de dato y el dato para la insercion  
            cs.setInt(1, Integer.parseInt(codigo.getText()));
            cs.setString(2, nombre.getText());
            cs.setString(3, Jornada.getText());
            //Ejecutamos la consulta
            cs.execute();
            //Si se ejecuta correcta nos mostrara este panel
            JOptionPane.showMessageDialog(null, "Insertó correctamente el programa, VERIFIQUE");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se guardó correctamente los datos" + ex.toString());

        }

    }
}
