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
public class Semestre {

    //Metodo para mostrar los semestres
    public void MostrarSemestres(JTable tablasemestres) {
        //Creamos un objeto conexion 
        Conexion.CConexion conexion = new Conexion.CConexion();
        //Creamos un modelo
        DefaultTableModel modelo = new DefaultTableModel();
        //Ordenamos la tabla
        TableRowSorter<TableModel> OrdenaTabla = new TableRowSorter<TableModel>(modelo);
        tablasemestres.setRowSorter(OrdenaTabla);
        //Creamos la consulta
        String sql = "";
        //Añadimos las columnas al modelo
        modelo.addColumn("Id");
        modelo.addColumn("Numero");
        //Asignamos el modelo
        tablasemestres.setModel(modelo);
        //Escribimos la consulta
        sql = "select * from totalsemestres;";

        String[] datos = new String[2];
        Statement st;

        try {
            //Establece conexion 
            st = conexion.establecerConexion().createStatement();
            //Ejecucion de la consulta
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);

                modelo.addRow(datos);

            }

            tablasemestres.setModel(modelo);

        } catch (SQLException ex) {
            System.out.print("Error: " + ex.toString());
        }

    }

    //Metodo para Agregar Semestres
    public void AgregarSemestre(JTextField id, JTextField numero) {
        //Establecemos la conexion con la base de datos
        Conexion.CConexion conexion = new Conexion.CConexion();
        //Escribimos y guardamos nuestra consulta 
        String consulta = ("select insertarsemestre(?,?);");

        try {
            //Establecemos y preparamos la consulta
            CallableStatement cs = conexion.establecerConexion().prepareCall(consulta);
            //Escribimos el numero,el tipo de dato y el dato para la insercion  
            cs.setInt(1, Integer.parseInt(id.getText()));
            cs.setInt(2, Integer.parseInt(numero.getText()));
            
            //Ejecutamos la consulta
            cs.execute();
            //Si se ejecuta correcta nos mostrara este panel
            JOptionPane.showMessageDialog(null, "Insertó correctamente el Semestre, VERIFIQUE");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se guardó correctamente los datos" + ex.toString());

        }

    }
}
