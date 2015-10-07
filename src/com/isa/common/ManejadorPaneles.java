/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.common;

import com.isa.front.ListaCertsJPanel;
import com.isa.front.MensajeJPanel;
import com.isa.front.ProcesandoJPanel;
import com.itextpdf.text.Font;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author JMiraballes
 */
public class ManejadorPaneles {
    
    private static JPanel cards;
    private static ManejadorPaneles manejadorPaneles;
    
    private ManejadorPaneles(){
        cards = new JPanel(new CardLayout());
    }
    
    public static ManejadorPaneles getInstance(){
        if (manejadorPaneles == null){
            manejadorPaneles = new ManejadorPaneles();
        }
        return manejadorPaneles;
    }
    
    public static void init(){
        cards = new JPanel(new CardLayout());
    }
    
    public static void addPanel( JPanel jpanel, String nombre ){
        getInstance().getCards().add( jpanel, nombre);
    }
    
    public static void showPanel( String nombre ){
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show( cards, nombre );
    }
    
    public static void showMessageCertList( String msg ){
        JPanel panel = getPanel(ListaCertsJPanel.class.getName());
        ListaCertsJPanel msgJpanel = (ListaCertsJPanel) panel; 
        msgJpanel.getMensaje().setText( msg );
        msgJpanel.getMensaje().setVisible( true );
        msgJpanel.getMensaje().setForeground( new Color(255, 0, 51) );       
    }
    
    public static void showPanelMessageError( String msg ){
        JPanel panel = getPanel(MensajeJPanel.class.getName());
        MensajeJPanel msgJpanel = (MensajeJPanel) panel; 
        msgJpanel.getMensaje().setText( msg );
        msgJpanel.getMensaje().setForeground( new Color(255, 0, 51) );
        msgJpanel.getMensaje().setHorizontalAlignment(SwingConstants.CENTER);
        
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show( cards, MensajeJPanel.class.getName() );
    }
    
    public static void showPanelMessageInfo( String msg ){
        JPanel panel = getPanel(MensajeJPanel.class.getName());
        MensajeJPanel msgJpanel = (MensajeJPanel) panel; 
        msgJpanel.getMensaje().setText( msg );
        msgJpanel.getMensaje().setForeground( new Color(0, 118, 196)  );
        msgJpanel.getMensaje().setHorizontalAlignment(SwingConstants.CENTER);
        
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show( cards, MensajeJPanel.class.getName() );
    }   
    
    public static void showPanelProcesando(String titulo, String tipodoc, String nro, String anio){
        JPanel panel = getPanel(ProcesandoJPanel.class.getName());
        ProcesandoJPanel procJpanel = (ProcesandoJPanel) panel;
        procJpanel.getAnioText().setText(anio);
        procJpanel.getNroText().setText(nro);
        procJpanel.getTipoText().setText(tipodoc);
        procJpanel.getTitulo().setText(titulo);
        
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show( cards, ProcesandoJPanel.class.getName() );
    }
    
    public static void showPanelProcesando(String titulo){
        JPanel panel = getPanel(ProcesandoJPanel.class.getName());
        ProcesandoJPanel procJpanel = (ProcesandoJPanel) panel;
        procJpanel.getTitulo().setText(titulo);
        
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show( cards, ProcesandoJPanel.class.getName() );
    }    
    
    public static JPanel getPanel(String nombre){
        
        Component visibleComp = null;
        for (Component comp : cards.getComponents()) {

            if (nombre.equals(comp.getClass().getName())) {
                visibleComp = comp;
                break;
            }
        }
        return (JPanel) visibleComp;
    }
    
    
    public JPanel getCards() {
        return cards;
    }
    
}
