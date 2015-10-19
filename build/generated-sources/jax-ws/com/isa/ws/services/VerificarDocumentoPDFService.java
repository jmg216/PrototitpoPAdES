
package com.isa.ws.services;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "VerificarDocumentoPDFService", targetNamespace = "http://services.ws.isa.com/", wsdlLocation = "file:/C:/Users/JMiraballes/Desktop/VerificarDocumentoPDFService.wsdl")
public class VerificarDocumentoPDFService
    extends Service
{

    private final static URL VERIFICARDOCUMENTOPDFSERVICE_WSDL_LOCATION;
    private final static WebServiceException VERIFICARDOCUMENTOPDFSERVICE_EXCEPTION;
    private final static QName VERIFICARDOCUMENTOPDFSERVICE_QNAME = new QName("http://services.ws.isa.com/", "VerificarDocumentoPDFService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Users/JMiraballes/Desktop/VerificarDocumentoPDFService.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        VERIFICARDOCUMENTOPDFSERVICE_WSDL_LOCATION = url;
        VERIFICARDOCUMENTOPDFSERVICE_EXCEPTION = e;
    }

    public VerificarDocumentoPDFService() {
        super(__getWsdlLocation(), VERIFICARDOCUMENTOPDFSERVICE_QNAME);
    }

    public VerificarDocumentoPDFService(WebServiceFeature... features) {
        super(__getWsdlLocation(), VERIFICARDOCUMENTOPDFSERVICE_QNAME, features);
    }

    public VerificarDocumentoPDFService(URL wsdlLocation) {
        super(wsdlLocation, VERIFICARDOCUMENTOPDFSERVICE_QNAME);
    }

    public VerificarDocumentoPDFService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, VERIFICARDOCUMENTOPDFSERVICE_QNAME, features);
    }

    public VerificarDocumentoPDFService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public VerificarDocumentoPDFService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns VerificarDocumentoPDF
     */
    @WebEndpoint(name = "VerificarDocumentoPDFPort")
    public VerificarDocumentoPDF getVerificarDocumentoPDFPort() {
        return super.getPort(new QName("http://services.ws.isa.com/", "VerificarDocumentoPDFPort"), VerificarDocumentoPDF.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns VerificarDocumentoPDF
     */
    @WebEndpoint(name = "VerificarDocumentoPDFPort")
    public VerificarDocumentoPDF getVerificarDocumentoPDFPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://services.ws.isa.com/", "VerificarDocumentoPDFPort"), VerificarDocumentoPDF.class, features);
    }

    private static URL __getWsdlLocation() {
        if (VERIFICARDOCUMENTOPDFSERVICE_EXCEPTION!= null) {
            throw VERIFICARDOCUMENTOPDFSERVICE_EXCEPTION;
        }
        return VERIFICARDOCUMENTOPDFSERVICE_WSDL_LOCATION;
    }

}
