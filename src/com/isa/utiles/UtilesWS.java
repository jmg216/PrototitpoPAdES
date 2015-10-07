/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.utiles;

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
    
    public static DocumentoFirmaDigitalEndPoint getInstancePortWS() throws IOException{
        if (port == null){
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
    
    public static VerificarDocumentoPDF getInstancePortWSVerify() throws MalformedURLException, IOException{
        if (portV == null){
            URL wsdllocation = new URL(UtilesResources.getProperty(UtilesResources.PROP_WS_ENDPOINT_VALIDACION));
            VerificarDocumentoPDFService verifyservice = new VerificarDocumentoPDFService( wsdllocation );
            portV = verifyservice.getVerificarDocumentoPDFPort();
        }
        return portV;
    }
    
}
