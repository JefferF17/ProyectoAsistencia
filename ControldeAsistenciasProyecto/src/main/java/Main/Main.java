/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Conexion.CConexion;
import Interfaces.Escritorio;

/**
 *
 * @author Jefferson
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CConexion objetoconexion = new CConexion();
        objetoconexion.establecerConexion();
        new Escritorio().setVisible(true);
    }
    
}
