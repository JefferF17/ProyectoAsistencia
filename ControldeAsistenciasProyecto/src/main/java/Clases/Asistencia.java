/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JLabel;
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
public class Asistencia {
    FileInputStream fis;
    int longitudBytes;
    //Agregar una asistencia a la base de datos
    public void AgregarAsistencia(JTextField id, JTextField asiste, JTextField clase, JTextField estudiante) {
        //Realizamos la conexion con nuestra Base de datos
        Conexion.CConexion conexion = new Conexion.CConexion();

        //Esta sera la consulta que enviaremos a Postgresql 
        String consulta = ("select insertarasistencia(?,?,?,?);");

        try {

            //Establecemos la conexion y preparamos la consulta
            CallableStatement cs = conexion.establecerConexion().prepareCall(consulta);
            //Aqui preparamos la insercion de los datos y cuantos parametros vamos a añadir
            //Añadimos Id de la asistencia
            cs.setInt(1, Integer.parseInt(id.getText()));
            //Añadimos Asiste de la asistencia
            cs.setBoolean(2, Boolean.parseBoolean(asiste.getText()));
            //Añadimos Id de la clase a la asistencia
            cs.setInt(3, Integer.parseInt(clase.getText()));
            //Añadimos Codigo del estudiante a la asistencia
            cs.setInt(4, Integer.parseInt(estudiante.getText()));
            //Y pedimos que se ejecute nuestra consulta
            cs.execute();

            //Con este panel comprobaremos si la insercion se realizo correctamente o hubo un fallo
            JOptionPane.showMessageDialog(null, "Insertó correctamente la asistencia correctamente, VERIFIQUE");

        } catch (SQLException ex) {
            //En caso de errores, se nos mostrara este panel y el error 
            JOptionPane.showMessageDialog(null, "No se guardó correctamente los datos" + ex.toString());

        }

    }

    public void MostrarAsistencia(JTable tablaasistencia) {
        //Realizamos la conexion 
        Conexion.CConexion conexion = new Conexion.CConexion();
        //Asignamos un modelo por defecto a nuestra tabla de asistencia
        DefaultTableModel modelo = new DefaultTableModel();
        //Ordenamos la tabla 
        TableRowSorter<TableModel> OrdenaTabla = new TableRowSorter<TableModel>(modelo);
        tablaasistencia.setRowSorter(OrdenaTabla);
        //Creamos una variable en donde posteriormente guardaremos la consulta
        String sql = "";
        //Describimos cuantas columnas tendra nuestro modelo y sus nombres
        modelo.addColumn("Asiste");
        modelo.addColumn("Codigo Est.");
        modelo.addColumn("Fecha");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Programa");
        //Asignamos el modelo a nuestra tabla de asistencia
        tablaasistencia.setModel(modelo);
        //Escribimos nuestra consulta que en este caso estamos haciendo consulta a una vista
        sql = "select * from totalclasesasistencia;";
        //Hacemos un arreglo con el numero de columnas de nuestro modelo
        String[] datos = new String[6];
        Statement st;

        try {
            st = conexion.establecerConexion().createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                //Añadimos los datos al modelo
                modelo.addRow(datos);

            }
            //Y cambiamos el modelo de la tablacla
            tablaasistencia.setModel(modelo);

        } catch (SQLException ex) {
            System.out.print("Error: " + ex.toString());
        }

    }
}
