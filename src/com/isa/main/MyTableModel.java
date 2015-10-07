package com.isa.main;

import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JMiraballes
 */
    //Extension del modelo de la tabla para hacer que las celdas no sean editables.
    public class MyTableModel extends DefaultTableModel{

        @Override
        public boolean isCellEditable(int a, int b) {
                return false;
        }
    }
