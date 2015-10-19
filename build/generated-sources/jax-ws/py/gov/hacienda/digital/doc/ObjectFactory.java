
package py.gov.hacienda.digital.doc;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the py.gov.hacienda.digital.doc package. 
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

    private final static QName _GuardarPdfFirmadoResponse_QNAME = new QName("http://doc.digital.hacienda.gov.py/", "guardarPdfFirmadoResponse");
    private final static QName _ObtenerPdfParaValidar_QNAME = new QName("http://doc.digital.hacienda.gov.py/", "obtenerPdfParaValidar");
    private final static QName _ObtenerPdfParaFirmaResponse_QNAME = new QName("http://doc.digital.hacienda.gov.py/", "obtenerPdfParaFirmaResponse");
    private final static QName _ObtenerPdfParaFirma_QNAME = new QName("http://doc.digital.hacienda.gov.py/", "obtenerPdfParaFirma");
    private final static QName _ObtenerPdfParaValidarResponse_QNAME = new QName("http://doc.digital.hacienda.gov.py/", "obtenerPdfParaValidarResponse");
    private final static QName _QuitarReserva_QNAME = new QName("http://doc.digital.hacienda.gov.py/", "quitarReserva");
    private final static QName _GuardarPdfFirmado_QNAME = new QName("http://doc.digital.hacienda.gov.py/", "guardarPdfFirmado");
    private final static QName _QuitarReservaResponse_QNAME = new QName("http://doc.digital.hacienda.gov.py/", "quitarReservaResponse");
    private final static QName _WsException_QNAME = new QName("http://doc.digital.hacienda.gov.py/", "WsException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: py.gov.hacienda.digital.doc
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ObtenerPdfParaFirma }
     * 
     */
    public ObtenerPdfParaFirma createObtenerPdfParaFirma() {
        return new ObtenerPdfParaFirma();
    }

    /**
     * Create an instance of {@link ObtenerPdfParaValidarResponse }
     * 
     */
    public ObtenerPdfParaValidarResponse createObtenerPdfParaValidarResponse() {
        return new ObtenerPdfParaValidarResponse();
    }

    /**
     * Create an instance of {@link GuardarPdfFirmado }
     * 
     */
    public GuardarPdfFirmado createGuardarPdfFirmado() {
        return new GuardarPdfFirmado();
    }

    /**
     * Create an instance of {@link QuitarReserva }
     * 
     */
    public QuitarReserva createQuitarReserva() {
        return new QuitarReserva();
    }

    /**
     * Create an instance of {@link GuardarPdfFirmadoResponse }
     * 
     */
    public GuardarPdfFirmadoResponse createGuardarPdfFirmadoResponse() {
        return new GuardarPdfFirmadoResponse();
    }

    /**
     * Create an instance of {@link ObtenerPdfParaValidar }
     * 
     */
    public ObtenerPdfParaValidar createObtenerPdfParaValidar() {
        return new ObtenerPdfParaValidar();
    }

    /**
     * Create an instance of {@link ObtenerPdfParaFirmaResponse }
     * 
     */
    public ObtenerPdfParaFirmaResponse createObtenerPdfParaFirmaResponse() {
        return new ObtenerPdfParaFirmaResponse();
    }

    /**
     * Create an instance of {@link WsException }
     * 
     */
    public WsException createWsException() {
        return new WsException();
    }

    /**
     * Create an instance of {@link QuitarReservaResponse }
     * 
     */
    public QuitarReservaResponse createQuitarReservaResponse() {
        return new QuitarReservaResponse();
    }

    /**
     * Create an instance of {@link ResultOperacion }
     * 
     */
    public ResultOperacion createResultOperacion() {
        return new ResultOperacion();
    }

    /**
     * Create an instance of {@link CampoTipoValor }
     * 
     */
    public CampoTipoValor createCampoTipoValor() {
        return new CampoTipoValor();
    }

    /**
     * Create an instance of {@link DocumentoElectronico }
     * 
     */
    public DocumentoElectronico createDocumentoElectronico() {
        return new DocumentoElectronico();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GuardarPdfFirmadoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://doc.digital.hacienda.gov.py/", name = "guardarPdfFirmadoResponse")
    public JAXBElement<GuardarPdfFirmadoResponse> createGuardarPdfFirmadoResponse(GuardarPdfFirmadoResponse value) {
        return new JAXBElement<GuardarPdfFirmadoResponse>(_GuardarPdfFirmadoResponse_QNAME, GuardarPdfFirmadoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerPdfParaValidar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://doc.digital.hacienda.gov.py/", name = "obtenerPdfParaValidar")
    public JAXBElement<ObtenerPdfParaValidar> createObtenerPdfParaValidar(ObtenerPdfParaValidar value) {
        return new JAXBElement<ObtenerPdfParaValidar>(_ObtenerPdfParaValidar_QNAME, ObtenerPdfParaValidar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerPdfParaFirmaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://doc.digital.hacienda.gov.py/", name = "obtenerPdfParaFirmaResponse")
    public JAXBElement<ObtenerPdfParaFirmaResponse> createObtenerPdfParaFirmaResponse(ObtenerPdfParaFirmaResponse value) {
        return new JAXBElement<ObtenerPdfParaFirmaResponse>(_ObtenerPdfParaFirmaResponse_QNAME, ObtenerPdfParaFirmaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerPdfParaFirma }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://doc.digital.hacienda.gov.py/", name = "obtenerPdfParaFirma")
    public JAXBElement<ObtenerPdfParaFirma> createObtenerPdfParaFirma(ObtenerPdfParaFirma value) {
        return new JAXBElement<ObtenerPdfParaFirma>(_ObtenerPdfParaFirma_QNAME, ObtenerPdfParaFirma.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerPdfParaValidarResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://doc.digital.hacienda.gov.py/", name = "obtenerPdfParaValidarResponse")
    public JAXBElement<ObtenerPdfParaValidarResponse> createObtenerPdfParaValidarResponse(ObtenerPdfParaValidarResponse value) {
        return new JAXBElement<ObtenerPdfParaValidarResponse>(_ObtenerPdfParaValidarResponse_QNAME, ObtenerPdfParaValidarResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QuitarReserva }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://doc.digital.hacienda.gov.py/", name = "quitarReserva")
    public JAXBElement<QuitarReserva> createQuitarReserva(QuitarReserva value) {
        return new JAXBElement<QuitarReserva>(_QuitarReserva_QNAME, QuitarReserva.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GuardarPdfFirmado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://doc.digital.hacienda.gov.py/", name = "guardarPdfFirmado")
    public JAXBElement<GuardarPdfFirmado> createGuardarPdfFirmado(GuardarPdfFirmado value) {
        return new JAXBElement<GuardarPdfFirmado>(_GuardarPdfFirmado_QNAME, GuardarPdfFirmado.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QuitarReservaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://doc.digital.hacienda.gov.py/", name = "quitarReservaResponse")
    public JAXBElement<QuitarReservaResponse> createQuitarReservaResponse(QuitarReservaResponse value) {
        return new JAXBElement<QuitarReservaResponse>(_QuitarReservaResponse_QNAME, QuitarReservaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://doc.digital.hacienda.gov.py/", name = "WsException")
    public JAXBElement<WsException> createWsException(WsException value) {
        return new JAXBElement<WsException>(_WsException_QNAME, WsException.class, null, value);
    }

}
