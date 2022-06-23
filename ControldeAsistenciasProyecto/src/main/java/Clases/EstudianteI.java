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
public class EstudianteI {

    //Metodo para agregar estudiante a nuestra base de datos
    public void AgregarEstudiante(JTextField codigo, JTextField nombre, JTextField apellido, JTextField codigocurso, JTextField codigoprograma, JTextField semestreid) {
        //Establecemos conexion con nuestra base de datos
        Conexion.CConexion conexion = new Conexion.CConexion();
        //Escribimos la consulta y se guarda en un String
        String consulta = ("select insertarestudiante(?,?,?,?,?,?);");

        try {
            //Establece la conexion y prepara la consulta
            CallableStatement cs = conexion.establecerConexion().prepareCall(consulta);
            //Escribimos el numero,el tipo de dato y el dato para la insercion  
            cs.setInt(1, Integer.parseInt(codigo.getText()));
            cs.setString(2, nombre.getText());
            cs.setString(3, apellido.getText());
            cs.setInt(4, Integer.parseInt(codigocurso.getText()));
            cs.setInt(5, Integer.parseInt(codigoprograma.getText()));
            cs.setInt(6, Integer.parseInt(semestreid.getText()));
            //Ejecucion de la consulta
            cs.execute();
            //Ejecucion correctamente
            JOptionPane.showMessageDialog(null, "Insertó correctamente el estudiante, VERIFIQUE");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se guardó correctamente los datos" + ex.toString());

        }

    }

    //Metodo para Mostrar Estudiantes
    public void MostrarEstudiante(int codigo, JTable tablaEstudiante) {
        //Creamos un objeto de tipo conexion 
        Conexion.CConexion conexion = new Conexion.CConexion();
        //Creamos un modelo para la tabla estudiantes
        DefaultTableModel modelo = new DefaultTableModel();
        //Ordenamos la tabla
        TableRowSorter<TableModel> OrdenaTabla = new TableRowSorter<TableModel>(modelo);
        tablaEstudiante.setRowSorter(OrdenaTabla);
        //Añadimos las columnas de nuestra tabla
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Curso");
        modelo.addColumn("Programa");
        modelo.addColumn("Semestre");
        //Creamos la consulta 
        String codigosql;
        //Asignamos el modelo a nuestra tabla
        tablaEstudiante.setModel(modelo);
        //Escribimos la consulta
        codigosql = "SELECT * FROM Estudiante WHERE codigo = '" + codigo + "'";
        //Creamos un arreglo para guardar los datos
        String[] datos = new String[6];
        Statement st;

        try {
            st = conexion.establecerConexion().createStatement();

            ResultSet rs = st.executeQuery(codigosql);

            while (rs.next()) {

                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);

                modelo.addRow(datos);

            }

            tablaEstudiante.setModel(modelo);

        } catch (SQLException ex) {
            System.out.print("Error: " + ex.toString());
        }

    }

}
