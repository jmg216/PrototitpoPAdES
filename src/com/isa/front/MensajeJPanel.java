/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.front;

import com.isa.common.FrontCommon;
import com.isa.common.ICommon;
import javax.swing.JLabel;

/**
 *
 * @author JMiraballes
 */
public class MensajeJPanel extends javax.swing.JPanel implements ICommon {
    
    /**
     * Creates new form MensajeJPanel
     */
    public MensajeJPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mensaje = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Mensaje"));

        mensaje.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        mensaje.setText("mensaje");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(mensaje)
                .addContainerGap(216, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel mensaje;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setFrontPanelSize() {
        setSize(FrontCommon.SIZE_WIDTH, FrontCommon.SIZE_HEIGHT);
    }

    public JLabel getMensaje() {
        return mensaje;
    }

    public void setMensaje(JLabel mensaje) {
        this.mensaje = mensaje;
    }
}
