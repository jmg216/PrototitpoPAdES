/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.main;

import com.google.gson.Gson;
import com.isa.common.ActualCertInfo;
import com.isa.common.FrontCommon;
import com.isa.common.GsonHelper;
import com.isa.common.ICommon;
import com.isa.common.ManejadorPaneles;
import com.isa.entityWS.RequestPdfWS;
import com.isa.exception.AppletException;
import com.isa.firma.FirmaPDFController;
import com.isa.firma.PDFFirma;
import com.isa.front.ListaCertsJPanel;
import com.isa.front.LoginJPanel;
import com.isa.front.MensajeJPanel;
import com.isa.front.ProcesandoJPanel;
import com.isa.front.ValidandoPDFJPanel;
import com.isa.plataform.KeyStoreValidator;
import com.isa.security.ISCertSecurityManager;
import com.isa.token.HandlerToken;
import com.isa.utiles.StreamDataSource;
import com.isa.utiles.Utiles;
import com.isa.utiles.UtilesMsg;
import com.isa.utiles.UtilesResources;
import com.isa.utiles.UtilesWS;
import java.awt.CardLayout;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.swing.JPanel;
import netscape.javascript.JSObject;
import py.gov.hacienda.digital.doc.DocumentoElectronico;
import py.gov.hacienda.digital.doc.ResultOperacion;

/**
 *
 * @author JMiraballes
 */
public class Main extends javax.swing.JApplet implements ICommon{

    private JPanel cards;
    private String jsonClass;
    /**
     * Initializes the applet Main
     */
    @Override
    public void init() {
        try{
            System.out.println("Main::init");
            SecurityManager sm = new ISCertSecurityManager();
            System.setSecurityManager( sm );            
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
            initComponents();
            ActualCertInfo.getInstance().inicializar();
            setFrontPanelSize();
            setPanels();
            KeyStoreValidator.setInitStoreValidator();            
            UtilesResources.setRutaProperties(getParameter("ruta"));
            String cedula = getParameter("cedula");
            sincronizarTokens();
            ActualCertInfo.getInstance().setCedula(cedula);
            ManejadorPaneles.showPanel(LoginJPanel.class.getName());
        }
        catch( AppletException e ){
            //llamar a mensaje.
            System.out.println("Init::Exception");
            ManejadorPaneles.showPanelMessageError( e.getMsj() );
        }
        
    }
    
    public boolean isValidar(){
        if ( getParameter("validar") == null ){
            return true;
        }
        else return Utiles.TRUE_VALUE.equals(getParameter("validar") );
    }
    
    private void sincronizarTokens() throws AppletException {

        HandlerToken handler = new HandlerToken();
        ActualCertInfo.getInstance().setHandler(handler);

        if (handler.isTokenActivo()){
            KeyStoreValidator.setKeystore(KeyStoreValidator.KEYSTORE_TOKEN);
        }
    }   
    
    
    public void firmarPDF( String json ){ 
        jsonClass = json;
        System.out.println("Metodo firmarPDF.");
        ActualCertInfo actualCertInfo = ActualCertInfo.getInstance();
        
        if (Utiles.isNullOrEmpty(actualCertInfo.getPassword())){
            firmaError(null, UtilesMsg.ERROR_PIN_VACIO);
            return;
        }
        
        if ((actualCertInfo.getCertIndex() == null) || (actualCertInfo.getCertIndex() < 0)){
            firmaError(null, UtilesMsg.ERROR_CERT_NO_SELECCIONADO);
            return;            
        }
        RequestPdfWS requestPdfWs = GsonHelper.getInstance().fromJsonToRequestPdfWS( json, ActualCertInfo.getInstance().getCedula());
        
        System.out.println("Post fromJson.");
        Thread thread = new Thread(){
            @Override
            public void run(){
                DocumentoElectronico dElectronico = null;
                try{
                    FirmaPDFController firmapdfcontroller = FirmaPDFController.getInstance();
                    RequestPdfWS requestPdfWs = GsonHelper.getInstance().fromJsonToRequestPdfWS( jsonClass, ActualCertInfo.getInstance().getCedula() );
                    System.out.println("JSON: " + jsonClass);
                    printParametros( requestPdfWs );
                    dElectronico = firmapdfcontroller.obtenerPDFFromWS( requestPdfWs );
                    PDFFirma infoFirma = firmapdfcontroller.generarApariencia();
                    
                    ByteArrayOutputStream pdfOS = firmapdfcontroller.firmar(infoFirma, dElectronico.getObjetoPdf().getInputStream());
                    InputStream pdfIS = new ByteArrayInputStream( pdfOS.toByteArray() );
                    
                    if (isValidar()){
                        ManejadorPaneles.showPanelProcesando(UtilesMsg.PROCESANDO_VALIDACION );
                        firmapdfcontroller.validarFirma( pdfOS.toByteArray() );             
                        ManejadorPaneles.showPanelMessageInfo( UtilesMsg.FIRMA_VERIFICADA_OK );
                    }
                    else{
                        ManejadorPaneles.showPanelMessageInfo( UtilesMsg.DOC_FIRMADO_OK );
                    }

                    // Return the InputStream to the client.
                    DataSource ds = new StreamDataSource( pdfIS, pdfOS );
                    DataHandler dh = new DataHandler(ds);
                    dElectronico.setObjetoPdf( dh );
                    ResultOperacion resultOp = firmapdfcontroller.guardarPDFWS( dElectronico , ActualCertInfo.getInstance().getCedula());
                    
                    if (resultOp != null){
                        switch( resultOp.getCodigo() ){
                            case -2: ManejadorPaneles.showPanelMessageError( resultOp.getMsg() );
                                     firmaError(dElectronico, resultOp.getMsg() );
                                     break;
                                
                            case -1: ManejadorPaneles.showPanelMessageError( UtilesMsg.ERROR_GUARDANDO_DOCUMENTO );
                                     firmaError(dElectronico, resultOp.getMsg() );
                                     break;

                            case 1: ManejadorPaneles.showPanelMessageInfo( UtilesMsg.DOCUMENTO_GUARDADO_OK );
                                    firmaExitosa( resultOp.getMsg() );
                                    break;
                                
                            default:ManejadorPaneles.showPanelMessageError( UtilesMsg.ERROR_CODIGO_WS );
                                    firmaError(dElectronico, UtilesMsg.ERROR_CODIGO_WS );
                                    break;
                        }
                    }
                    else{
                        ManejadorPaneles.showPanelMessageError( UtilesMsg.ERROR_CODIGO_WS );
                        firmaError(dElectronico, UtilesMsg.ERROR_CODIGO_WS );                        
                    }
                }
                catch (AppletException ex) {
                    Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
                    ManejadorPaneles.showPanelMessageError( ex.getMsj() );
                    firmaError(dElectronico, ex.getMsj() );
                }
                catch (IOException ex) {
                    Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
                    ManejadorPaneles.showPanelMessageError( UtilesMsg.ERROR_ACCEDIENDO_ARCHIVO );
                    firmaError(dElectronico, UtilesMsg.ERROR_ACCEDIENDO_ARCHIVO );
                }
                catch (KeyStoreException ex) {
                    Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
                    ManejadorPaneles.showPanelMessageError( UtilesMsg.ERROR_FIRMANDO_DOCUMENTO );
                    firmaError(dElectronico, UtilesMsg.ERROR_FIRMANDO_DOCUMENTO );
                }
                catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
                    ManejadorPaneles.showPanelMessageError( UtilesMsg.ERROR_FIRMANDO_DOCUMENTO );
                    firmaError(dElectronico, UtilesMsg.ERROR_FIRMANDO_DOCUMENTO );
                }
                catch (UnrecoverableKeyException ex) {
                    Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
                    ManejadorPaneles.showPanelMessageError( UtilesMsg.ERROR_FIRMANDO_DOCUMENTO );
                    firmaError(dElectronico, UtilesMsg.ERROR_FIRMANDO_DOCUMENTO );
                }
            }
        };
        thread.start();
        System.out.println("Comienzo de hilo.");
        String tipoDocumento = requestPdfWs.getParametroValue(RequestPdfWS.TIPO_DOCUMENTO);
        String anio = requestPdfWs.getParametroExtraValue(RequestPdfWS.ANIO);
        String solnumero = requestPdfWs.getParametroExtraValue(RequestPdfWS.SOLNUMERO);
        ManejadorPaneles.showPanelProcesando(UtilesMsg.PROCESANDO_FIRMA, tipoDocumento , solnumero, anio);
    }
    
    private void printParametros(RequestPdfWS requestPdfWs){
        if (requestPdfWs != null){
            
            System.out.println("Cedula: " + requestPdfWs.getCedula());
            System.out.println("Tipo Documento: " + requestPdfWs.getParametroValue("tipoDocumento"));
            System.out.println("Solicitud: " + requestPdfWs.getParametroExtraValue("solNumero"));
            System.out.println("Anio: " + requestPdfWs.getParametroExtraValue("anio"));
        }
    }
    
    private void firmaError(DocumentoElectronico delectronico, String msg){
        System.out.println("firmaError::init");
        if (delectronico != null){
            delectronico.setObjetoPdf(null);
            try {
                System.out.println("QUITANDO RESERVA: " + delectronico.getTipoDocumento());
                UtilesWS.getInstancePortWS().quitarReserva(delectronico);
            } catch (AppletException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                msg = ex.getMsj();
            }
        }
        JSObject win = (JSObject) JSObject.getWindow(this);
        win.call("firmaError", new String[]{  msg });        
    }
    
    
    private void firmaExitosa( String msj ){  
        JSObject win = (JSObject) JSObject.getWindow(this);
        win.call("firmaExitosa", new String[]{  msj } );   
    }     

    /**
     * This method is called from within the init() method to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void setFrontPanelSize() {
        setSize(FrontCommon.SIZE_WIDTH, FrontCommon.SIZE_HEIGHT);
    }
    
    public RequestPdfWS testJSON(String json){
        
        Gson gs = new Gson();
        RequestPdfWS pdfrequest = gs.fromJson( json , RequestPdfWS.class);
        return pdfrequest;
    }
    
    public void setPanels(){
        JPanel card1 = new LoginJPanel();
        JPanel card2 = new ListaCertsJPanel();
        JPanel card3 = new ProcesandoJPanel();
        JPanel card4 = new MensajeJPanel();
        JPanel card5 = new ValidandoPDFJPanel();
        
        ManejadorPaneles.init();
        
        ManejadorPaneles.addPanel(card1, LoginJPanel.class.getName());
        ManejadorPaneles.addPanel(card2, ListaCertsJPanel.class.getName());
        ManejadorPaneles.addPanel(card3, ProcesandoJPanel.class.getName());
        ManejadorPaneles.addPanel(card4, MensajeJPanel.class.getName());
        ManejadorPaneles.addPanel(card5, ValidandoPDFJPanel.class.getName());
        cards = ManejadorPaneles.getInstance().getCards();
        
        this.getContentPane().add( cards );
    }
}
