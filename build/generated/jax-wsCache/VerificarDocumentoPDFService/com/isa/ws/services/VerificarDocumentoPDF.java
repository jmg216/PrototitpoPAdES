
package com.isa.ws.services;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "VerificarDocumentoPDF", targetNamespace = "http://services.ws.isa.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface VerificarDocumentoPDF {


    /**
     * 
     * @param arg0
     * @return
     *     returns com.isa.ws.services.VerifyResponse
     * @throws WServiceTXException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "validarDocumentoByDoc", targetNamespace = "http://services.ws.isa.com/", className = "com.isa.ws.services.ValidarDocumentoByDoc")
    @ResponseWrapper(localName = "validarDocumentoByDocResponse", targetNamespace = "http://services.ws.isa.com/", className = "com.isa.ws.services.ValidarDocumentoByDocResponse")
    public VerifyResponse validarDocumentoByDoc(
        @WebParam(name = "arg0", targetNamespace = "")
        byte[] arg0)
        throws WServiceTXException_Exception
    ;

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns com.isa.ws.services.VerifyResponse
     * @throws WServiceTXException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "validarDocumentoByParams", targetNamespace = "http://services.ws.isa.com/", className = "com.isa.ws.services.ValidarDocumentoByParams")
    @ResponseWrapper(localName = "validarDocumentoByParamsResponse", targetNamespace = "http://services.ws.isa.com/", className = "com.isa.ws.services.ValidarDocumentoByParamsResponse")
    public VerifyResponse validarDocumentoByParams(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        List<CampoTipoValorTx> arg1)
        throws WServiceTXException_Exception
    ;

}
