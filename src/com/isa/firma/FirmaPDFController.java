/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.firma;

import com.isa.common.ActualCertInfo;
import com.isa.entityWS.RequestPdfWS;
import com.isa.exception.AppletException;
import com.isa.token.HandlerToken;
import com.isa.token.Token;
import com.isa.utiles.IdGenerator;
import com.isa.utiles.Utiles;
import com.isa.utiles.UtilesMsg;
import com.isa.utiles.UtilesResources;
import com.isa.utiles.UtilesWS;
import com.isa.ws.services.VerificarDocumentoPDF;
import com.isa.ws.services.VerifyResponse;
import com.isa.ws.services.WServiceTXException_Exception;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import com.itextpdf.xmp.impl.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import py.gov.hacienda.digital.doc.DocumentoElectronico;
import py.gov.hacienda.digital.doc.ResultOperacion;
import py.gov.hacienda.digital.doc.WsException_Exception;

/**
 *
 * @author JMiraballes
 */
public class FirmaPDFController {
    
    private static FirmaPDFController firmaPDFController;
    
    public FirmaPDFController(){}
    
    public static FirmaPDFController getInstance(){
        if (firmaPDFController == null){
            firmaPDFController = new FirmaPDFController();
        }
        return firmaPDFController;
    }
    
    public DocumentoElectronico obtenerPDFFromWS( RequestPdfWS requestPdfWs ) throws AppletException{

        try {
            DocumentoElectronico dElectronico = null;
            String tipoDocumento = requestPdfWs.getParametroValue(RequestPdfWS.TIPO_DOCUMENTO);
            dElectronico = UtilesWS.getInstancePortWS().obtenerPdfParaFirma(tipoDocumento, requestPdfWs.getParametrosExtra(), requestPdfWs.getCedula());
            
            return dElectronico;
        } 
        catch (WsException_Exception ex) {
            Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
            throw new AppletException( UtilesMsg.ERROR_WS_EXCEPTION, null, ex.getCause() );
        } 
    }
    
    public ResultOperacion guardarPDFWS( DocumentoElectronico dElectronico, String cedula ) throws AppletException{
        ResultOperacion resultOpeation = null;
        try {
            GregorianCalendar gcal =  (GregorianCalendar) GregorianCalendar.getInstance();
            XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar( gcal );
            resultOpeation = UtilesWS.getInstancePortWS().guardarPdfFirmado(dElectronico, cedula, xgcal);
            
            return resultOpeation;
        } 
        catch (WsException_Exception ex) {
            Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
            throw new AppletException( UtilesMsg.ERROR_WS_EXCEPTION, null, ex.getCause() );
        } catch (DatatypeConfigurationException ex) {
            System.out.println("Se generó un error instanciando XMLGregorianCalendar");
            Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
            throw new AppletException( UtilesMsg.ERROR_PARSING_CALENDAR, null, ex.getCause() );
        }       
    }  
    
    
    public PDFFirma generarApariencia(  ) throws IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException{
        System.out.println("Firma Controller::generarApariencia");
        
        HandlerToken handlerToken = ActualCertInfo.getInstance().getHandler();         
        Token token = handlerToken.getTokenActivo();  
        HashMap aliasHash = ActualCertInfo.getInstance().getAliasHash();
        int certIndex = ActualCertInfo.getInstance().getCertIndex();
        String alias = (String) aliasHash.get(certIndex);
        
        PDFFirma infofirma = new PDFFirma();
        infofirma.setPk( (PrivateKey) token.getKeystore().getKey(alias, null) );
        infofirma.setChainCert( token.getKeystore().getCertificateChain(alias) );
        infofirma.setProvidername( token.getKeystore().getProvider().getName() );

        //Definiendo apariencia de firma.
        infofirma.setFirmante(alias);
        infofirma.setTextoFirma(alias);
        infofirma.setApariencia(UtilesResources.getProperty(UtilesResources.PROP_APARIENCIA).equals(UtilesResources.TRUE_VALUE));
        
        System.out.println("Generando apariencia");
        System.out.println("AliasHash: " + aliasHash );
        System.out.println("CertIndex: " + certIndex );
        System.out.println("Alias: " + alias);
        System.out.println("Apariencia Properties: " + UtilesResources.getProperty(UtilesResources.PROP_APARIENCIA));
        System.out.println("Apariencia: " + infofirma.isApariencia());
        
        if (infofirma.isApariencia()){
            System.out.println("Definiendo propiedades de apariencia");
            
            infofirma.setHoja( Integer.valueOf( UtilesResources.getProperty(UtilesResources.PROP_PAG_FIRMA)) );
            infofirma.setAncho( Integer.valueOf(UtilesResources.getProperty(UtilesResources.PROP_ANCHO_FIRMA)) );
            infofirma.setLargo( Integer.valueOf(UtilesResources.getProperty(UtilesResources.PROP_LARGO_FIRMA)) );
            infofirma.setRutaImagen( UtilesResources.getProperty(UtilesResources.PROP_URL_IMAGEN) );
            
            System.out.println("Hoja: "  + infofirma.getHoja());
            System.out.println("Ruta Imagen: " + infofirma.getRutaImagen() );
            System.out.println("Largo: " + infofirma.getLargo());
            System.out.println("Ancho: " + infofirma.getAncho());
            System.out.println("Firmante: " + infofirma.getFirmante());
            System.out.println("Texto Firma: " + infofirma.getTextoFirma());
        }
        return infofirma;
    } 
    
    
    
    public ByteArrayOutputStream firmar(PDFFirma infoFirma, InputStream pdfbase64 ) throws AppletException{
        
        try {
            System.out.println("Firma Controller::firmar");
            
            PdfReader reader = new PdfReader( pdfbase64 );
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0', null, true);
            PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
            System.out.println("Pre definir apariencia...");
            if (infoFirma.isApariencia()){
                System.out.println("Insertando apriencia en documento...");
                appearance.setSignatureGraphic( Image.getInstance(new URL( infoFirma.getRutaImagen() )) );
                appearance.setRenderingMode(Utiles.getModoApariencia());           
                
                int numeroPagFirma = infoFirma.getHoja() == -1 ? reader.getNumberOfPages() : infoFirma.getHoja();
                int cantidadFirmaActuales = reader.getAcroFields().getSignatureNames().size();
                int[] coords = infoFirma.calcularCorrdenadasFirma( cantidadFirmaActuales,  infoFirma.getAncho(), infoFirma.getLargo() );
                
                //llx, lly, urx, ury
                String v = Utiles.encodingString(infoFirma.getFirmante());
                System.out.println("Post encoding: " + v);
                appearance.setLayer2Text( v );
                //appearance.setLayer2Text(  infoFirma.getFirmante() );
                appearance.setVisibleSignature(new Rectangle(coords[0], coords[1], coords[2], coords[3]), numeroPagFirma, "Id: " + IdGenerator.generate());
            }
            
            ExternalSignature es = new PrivateKeySignature(infoFirma.getPk(), "SHA-256", infoFirma.getProvidername());
            ExternalDigest digest = new BouncyCastleDigest();
            MakeSignature.signDetached(appearance, digest, es, infoFirma.getChainCert(), null, null, null, 0, CryptoStandard.CMS);
           
            System.out.println("PDF Firmado correctamente.");
            
            return os;
            
        } 
        catch (IOException ex) {
            Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
            throw new AppletException(UtilesMsg.ERROR_FIRMANDO_DOCUMENTO, null, ex.getCause());
        } 
        catch (DocumentException ex) {
            Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
            throw new AppletException(UtilesMsg.ERROR_FIRMANDO_DOCUMENTO, null, ex.getCause());
        } 
        catch (KeyStoreException ex) {
            Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
            throw new AppletException(UtilesMsg.ERROR_FIRMANDO_DOCUMENTO, null, ex.getCause());
        }
        catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
            throw new AppletException(UtilesMsg.ERROR_FIRMANDO_DOCUMENTO, null, ex.getCause());
        }
        catch (UnrecoverableKeyException ex) {
            Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
            throw new AppletException(UtilesMsg.ERROR_FIRMANDO_DOCUMENTO, null, ex.getCause());
        }
        catch (GeneralSecurityException ex) {
            Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
            throw new AppletException(UtilesMsg.ERROR_FIRMANDO_DOCUMENTO, null, ex.getCause());
        }
    }    
    
    public void validarFirma( byte[] pdffirmado ) throws AppletException{
        
        try{
            VerificarDocumentoPDF verificarpdf = UtilesWS.getInstancePortWSVerify();
            byte[] pdfbase64firmando = Base64.encode(pdffirmado);
            VerifyResponse response = verificarpdf.validarDocumentoByDoc( pdfbase64firmando );
            if (!response.isValida()){
                //String msj, String stacktrace, Throwable cause
                throw new AppletException(UtilesMsg.ERROR_UNA_FIRMA_NO_VALIDA, null, null);
            }   
        } 
        catch (WServiceTXException_Exception ex) {
            Logger.getLogger(FirmaPDFController.class.getName()).log(Level.SEVERE, null, ex);
            throw new AppletException(UtilesMsg.ERROR_WS_EXCEPTION_VALIDACION, null, ex.getCause());
        }
    }
    
}
