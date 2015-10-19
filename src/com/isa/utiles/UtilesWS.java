/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.utiles;

import com.isa.exception.AppletException;
import com.isa.ws.services.VerificarDocumentoPDF;
import com.isa.ws.services.VerificarDocumentoPDFService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.ws.BindingProvider;
import py.gov.hacienda.digital.doc.DocumentoFirmaDigitalEndPoint;
import py.gov.hacienda.digital.doc.DocumentoFirmaDigitalService;

/**
 *
 * @author JMiraballes
 */
public class UtilesWS {
    
    private static DocumentoFirmaDigitalEndPoint port = null;
    private static VerificarDocumentoPDF portV = null;
    public static int CODIGO_RESPUESTA_ERROR = -1; 
    public static int CODIGO_RESPUESTA_OK = 1;
    
    public static DocumentoFirmaDigitalEndPoint getInstancePortWS() throws AppletException{
        try{
            if (port == null){
                System.out.println("Operacion docs: " + UtilesResources.getProperty(UtilesResources.PROP_WS_ENDPOINT));
                URL wsdllocation = new URL(UtilesResources.getProperty(UtilesResources.PROP_WS_ENDPOINT));
                DocumentoFirmaDigitalService serviceRes = new DocumentoFirmaDigitalService( wsdllocation );
                port = serviceRes.getDocumentoFirmaDigitalEndPointPort();
                BindingProvider bindingProvider = (BindingProvider) port; 
    //          bindingProvider.getRequestContext().put( BindingProvider.ENDPOINT_ADDRESS_PROPERTY, UtilesResources.getProperty(UtilesResources.PROP_WS_ENDPOINT) );
                if (UtilesResources.TRUE_VALUE.equals(UtilesResources.getProperty(UtilesResources.PROP_WS_AUTH))){
                    bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, UtilesResources.getProperty(UtilesResources.PROP_WS_USER));
                    bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, UtilesResources.getProperty(UtilesResources.PROP_WS_PASSWD));
                }
            }
            return port;
        }
        catch (IOException e ){
            e.printStackTrace();
            throw new AppletException(UtilesMsg.ERROR_ACCEDIENDO_ARCHIVO, null, e.getCause());
        }
        catch (Exception e){
            e.printStackTrace();
           throw new AppletException(UtilesMsg.ERROR_CONEXION_WEB_SERVICES, null, e.getCause());
        }
    }
    
    public static VerificarDocumentoPDF getInstancePortWSVerify() throws AppletException{
        String url = "";
        try{
            url = UtilesResources.getProperty(UtilesResources.PROP_WS_ENDPOINT_VALIDACION);
            System.out.println("ENPOINT VALIDACION: " + url);
            if (portV == null){
                URL wsdllocation = new URL(url);
                VerificarDocumentoPDFService verifyservice = new VerificarDocumentoPDFService( wsdllocation );
                portV = verifyservice.getVerificarDocumentoPDFPort();
            }
            return portV;
        }
        catch(MalformedURLException e){
            throw new AppletException(UtilesMsg.ERROR_URL_WS + url, null, e.getCause());
        }
        catch (IOException e ){
            throw new AppletException(UtilesMsg.ERROR_ACCEDIENDO_ARCHIVO, null, e.getCause());
        }
        catch (Exception e){
           throw new AppletException(UtilesMsg.ERROR_CONEXION_WEB_SERVICES, null, e.getCause());
        }
    }
    
}
