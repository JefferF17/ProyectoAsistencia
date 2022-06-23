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
//Clase Curso
public class Curso {

    //Metodo para Agregar Cursos
    public void AgregarCurso(JTextField codigo, JTextField nombre, JTextField grupo) {
        //Establecemos la conexion con la base de datos
        Conexion.CConexion conexion = new Conexion.CConexion();
        //Escribimos y guardamos nuestra consulta 
        String consulta = ("select insertarcurso(?,?,?);");

        try {
            //Establecemos y preparamos la consulta
            CallableStatement cs = conexion.establecerConexion().prepareCall(consulta);
            //Escribimos el numero,el tipo de dato y el dato para la insercion  
            cs.setInt(1, Integer.parseInt(codigo.getText()));
            cs.setString(2, nombre.getText());
            cs.setInt(3, Integer.parseInt(grupo.getText()));
            //Ejecutamos la consulta
            cs.execute();
            //Si se ejecuta correcta nos mostrara este panel
            JOptionPane.showMessageDialog(null, "Insertó correctamente el curso, VERIFIQUE");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se guardó correctamente los datos" + ex.toString());

        }

    }

    //Metodo para mostrar Cursos con la busqueda
    public void MostrarCurso(int codigo, JTable tablacursos) {
        //Establecemos conexion con nuestra base de datos
        Conexion.CConexion conexion = new Conexion.CConexion();
        //Creamos un modelo default para nuestra tabla
        DefaultTableModel modelo = new DefaultTableModel();
        //Ordenamos nuestra tabla
        TableRowSorter<TableModel> OrdenaTabla = new TableRowSorter<TableModel>(modelo);
        tablacursos.setRowSorter(OrdenaTabla);
        //Añadimos las columnas de nuestra tabla y su respectivo nombre
        modelo.addColumn("Codigo Curso");
        modelo.addColumn("Nombre");
        modelo.addColumn("Grupo");
        modelo.addColumn("Codigo Est");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        //Creamos nuestra consulta que enviaremos a Postgresql
        String codigosql;
        //Asignamos el modelo a la tabla
        tablacursos.setModel(modelo);
        //Escribimos la consulta
        codigosql = "SELECT curso.codigo,curso.nombre,curso.grupo,estudiante.codigo,estudiante.nombre,estudiante.apellidos FROM Curso"
                + " inner join estudiante on curso.codigo = estudiante.codigo_curso WHERE curso.codigo = '" + codigo + "'";
        //Creamos un Arreglo de datos 
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
                //Añadimos los datos
                modelo.addRow(datos);

            }

            tablacursos.setModel(modelo);

        } catch (SQLException ex) {
            System.out.print("Error: " + ex.toString());
        }

    }
}
