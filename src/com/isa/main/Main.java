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
import java.awt.CardLayout;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
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
            setFrontPanelSize();
            setPanels();
            KeyStoreValidator.setInitStoreValidator();
            
//            UtilesResources.setRutaProperties("http://localhost:8080/ISCert/resources/hacienda/applet.properties");
//            String cedula = "17706166";
//            ActualCertInfo.getInstance().setCertIndex(2);
            
            UtilesResources.setRutaProperties(getParameter("ruta"));
            String cedula = getParameter("cedula");
            
            sincronizarTokens();
            ActualCertInfo.getInstance().setCedula(cedula);
            ManejadorPaneles.showPanel(LoginJPanel.class.getName());
        }
        catch( AppletException e ){
            //llamar a mensaje.
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
        
        if (actualCertInfo.getCertIndex() == -1){
            ManejadorPaneles.showMessageCertList( UtilesMsg.ERROR_CERT_NO_SELECCIONADO );
            return;            
        }
        RequestPdfWS requestPdfWs = GsonHelper.getInstance().fromJsonToRequestPdfWS( json, ActualCertInfo.getInstance().getCedula() );
        
        System.out.println("Post fromJson.");
        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    FirmaPDFController firmapdfcontroller = FirmaPDFController.getInstance();
                    RequestPdfWS requestPdfWs = GsonHelper.getInstance().fromJsonToRequestPdfWS( jsonClass, ActualCertInfo.getInstance().getCedula() );
                    DocumentoElectronico dElectronico = firmapdfcontroller.obtenerPDFFromWS( requestPdfWs );
                    PDFFirma infoFirma = firmapdfcontroller.generarApariencia();
                    
                    ByteArrayOutputStream pdfOS = firmapdfcontroller.firmar(infoFirma, dElectronico.getObjetoPdf().getInputStream());
                    InputStream pdfIS = new ByteArrayInputStream( pdfOS.toByteArray() );
                    
                    
                    if (isValidar()){
                        ManejadorPaneles.showPanelMessageInfo( UtilesMsg.VALIDANDO_DOCUMENTO );
                        firmapdfcontroller.validarFirma( pdfOS.toByteArray() );                        
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
                            case -1: ManejadorPaneles.showPanelMessageError( UtilesMsg.ERROR_GUARDANDO_DOCUMENTO );
                                     firmaError( resultOp.getMsg() );
                                     break;

                            case 1: ManejadorPaneles.showPanelMessageInfo( UtilesMsg.DOCUMENTO_GUARDADO_OK );
                                    firmaExitosa( resultOp.getMsg() );
                                    break;

                            default:ManejadorPaneles.showPanelMessageError( UtilesMsg.ERROR_CODIGO_WS );
                                    firmaError( UtilesMsg.ERROR_CODIGO_WS );
                                    break;
                        }
                    }
                    else{
                        ManejadorPaneles.showPanelMessageError( UtilesMsg.ERROR_CODIGO_WS );
                        firmaError( UtilesMsg.ERROR_CODIGO_WS );                        
                    }
                }
                catch (AppletException ex) {
                    Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
                    ManejadorPaneles.showPanelMessageError( ex.getMsj() );
                    firmaError( ex.getMsj() );
                }
                catch (IOException ex) {
                    Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
                    ManejadorPaneles.showPanelMessageError( UtilesMsg.ERROR_ACCEDIENDO_ARCHIVO );
                    firmaError( UtilesMsg.ERROR_ACCEDIENDO_ARCHIVO );
                }
                catch (KeyStoreException ex) {
                    Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
                    ManejadorPaneles.showPanelMessageError( UtilesMsg.ERROR_FIRMANDO_DOCUMENTO );
                    firmaError( UtilesMsg.ERROR_FIRMANDO_DOCUMENTO );
                }
                catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
                    ManejadorPaneles.showPanelMessageError( UtilesMsg.ERROR_FIRMANDO_DOCUMENTO );
                    firmaError( UtilesMsg.ERROR_FIRMANDO_DOCUMENTO );
                }
                catch (UnrecoverableKeyException ex) {
                    Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
                    ManejadorPaneles.showPanelMessageError( UtilesMsg.ERROR_FIRMANDO_DOCUMENTO );
                    firmaError( UtilesMsg.ERROR_FIRMANDO_DOCUMENTO );
                }
            }
        };
        thread.start();
        System.out.println("Comienzo de hilo.");
        String tipoDocumento = requestPdfWs.getParametroValue(RequestPdfWS.TIPO_DOCUMENTO);
        String anio = requestPdfWs.getParametroExtraValue(RequestPdfWS.ANIO);
        String solnumero = requestPdfWs.getParametroExtraValue(RequestPdfWS.SOLNUMERO);
        ManejadorPaneles.showPanelProcesando(tipoDocumento , solnumero, anio);
        
    }
    
    
    private void firmaError(String msg){
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
