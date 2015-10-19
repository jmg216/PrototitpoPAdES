
package py.gov.hacienda.digital.doc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para guardarPdfFirmado complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="guardarPdfFirmado">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="postFirmado" type="{http://doc.digital.hacienda.gov.py/}documentoElectronico" minOccurs="0"/>
 *         &lt;element name="cedulaFirmante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaFirmaCliente" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "guardarPdfFirmado", propOrder = {
    "postFirmado",
    "cedulaFirmante",
    "fechaFirmaCliente"
})
public class GuardarPdfFirmado {

    protected DocumentoElectronico postFirmado;
    protected String cedulaFirmante;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaFirmaCliente;

    /**
     * Obtiene el valor de la propiedad postFirmado.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoElectronico }
     *     
     */
    public DocumentoElectronico getPostFirmado() {
        return postFirmado;
    }

    /**
     * Define el valor de la propiedad postFirmado.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoElectronico }
     *     
     */
    public void setPostFirmado(DocumentoElectronico value) {
        this.postFirmado = value;
    }

    /**
     * Obtiene el valor de la propiedad cedulaFirmante.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCedulaFirmante() {
        return cedulaFirmante;
    }

    /**
     * Define el valor de la propiedad cedulaFirmante.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCedulaFirmante(String value) {
        this.cedulaFirmante = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaFirmaCliente.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaFirmaCliente() {
        return fechaFirmaCliente;
    }

    /**
     * Define el valor de la propiedad fechaFirmaCliente.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaFirmaCliente(XMLGregorianCalendar value) {
        this.fechaFirmaCliente = value;
    }

}
