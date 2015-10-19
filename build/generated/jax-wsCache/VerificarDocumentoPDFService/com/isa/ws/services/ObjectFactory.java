
package com.isa.ws.services;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.isa.ws.services package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ValidarDocumentoByDocResponse_QNAME = new QName("http://services.ws.isa.com/", "validarDocumentoByDocResponse");
    private final static QName _WServiceTXException_QNAME = new QName("http://services.ws.isa.com/", "WService_TXException");
    private final static QName _ValidarDocumentoByParams_QNAME = new QName("http://services.ws.isa.com/", "validarDocumentoByParams");
    private final static QName _ValidarDocumentoByDoc_QNAME = new QName("http://services.ws.isa.com/", "validarDocumentoByDoc");
    private final static QName _ValidarDocumentoByParamsResponse_QNAME = new QName("http://services.ws.isa.com/", "validarDocumentoByParamsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.isa.ws.services
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ValidarDocumentoByParams }
     * 
     */
    public ValidarDocumentoByParams createValidarDocumentoByParams() {
        return new ValidarDocumentoByParams();
    }

    /**
     * Create an instance of {@link ValidarDocumentoByDocResponse }
     * 
     */
    public ValidarDocumentoByDocResponse createValidarDocumentoByDocResponse() {
        return new ValidarDocumentoByDocResponse();
    }

    /**
     * Create an instance of {@link WServiceTXException }
     * 
     */
    public WServiceTXException createWServiceTXException() {
        return new WServiceTXException();
    }

    /**
     * Create an instance of {@link ValidarDocumentoByDoc }
     * 
     */
    public ValidarDocumentoByDoc createValidarDocumentoByDoc() {
        return new ValidarDocumentoByDoc();
    }

    /**
     * Create an instance of {@link ValidarDocumentoByParamsResponse }
     * 
     */
    public ValidarDocumentoByParamsResponse createValidarDocumentoByParamsResponse() {
        return new ValidarDocumentoByParamsResponse();
    }

    /**
     * Create an instance of {@link CampoTipoValorTx }
     * 
     */
    public CampoTipoValorTx createCampoTipoValorTx() {
        return new CampoTipoValorTx();
    }

    /**
     * Create an instance of {@link Signature }
     * 
     */
    public Signature createSignature() {
        return new Signature();
    }

    /**
     * Create an instance of {@link VerifyResponse }
     * 
     */
    public VerifyResponse createVerifyResponse() {
        return new VerifyResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidarDocumentoByDocResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.ws.isa.com/", name = "validarDocumentoByDocResponse")
    public JAXBElement<ValidarDocumentoByDocResponse> createValidarDocumentoByDocResponse(ValidarDocumentoByDocResponse value) {
        return new JAXBElement<ValidarDocumentoByDocResponse>(_ValidarDocumentoByDocResponse_QNAME, ValidarDocumentoByDocResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WServiceTXException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.ws.isa.com/", name = "WService_TXException")
    public JAXBElement<WServiceTXException> createWServiceTXException(WServiceTXException value) {
        return new JAXBElement<WServiceTXException>(_WServiceTXException_QNAME, WServiceTXException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidarDocumentoByParams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.ws.isa.com/", name = "validarDocumentoByParams")
    public JAXBElement<ValidarDocumentoByParams> createValidarDocumentoByParams(ValidarDocumentoByParams value) {
        return new JAXBElement<ValidarDocumentoByParams>(_ValidarDocumentoByParams_QNAME, ValidarDocumentoByParams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidarDocumentoByDoc }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.ws.isa.com/", name = "validarDocumentoByDoc")
    public JAXBElement<ValidarDocumentoByDoc> createValidarDocumentoByDoc(ValidarDocumentoByDoc value) {
        return new JAXBElement<ValidarDocumentoByDoc>(_ValidarDocumentoByDoc_QNAME, ValidarDocumentoByDoc.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidarDocumentoByParamsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.ws.isa.com/", name = "validarDocumentoByParamsResponse")
    public JAXBElement<ValidarDocumentoByParamsResponse> createValidarDocumentoByParamsResponse(ValidarDocumentoByParamsResponse value) {
        return new JAXBElement<ValidarDocumentoByParamsResponse>(_ValidarDocumentoByParamsResponse_QNAME, ValidarDocumentoByParamsResponse.class, null, value);
    }

}
