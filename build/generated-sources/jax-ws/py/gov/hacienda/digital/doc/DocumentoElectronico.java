
package py.gov.hacienda.digital.doc;

import java.util.ArrayList;
import java.util.List;
import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para documentoElectronico complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="documentoElectronico">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="objetoPdf" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="parametros" type="{http://doc.digital.hacienda.gov.py/}campoTipoValor" maxOccurs="unbounded"/>
 *         &lt;element name="tipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "documentoElectronico", propOrder = {
    "objetoPdf",
    "parametros",
    "tipoDocumento"
})
public class DocumentoElectronico {

    @XmlElement(required = true)
    @XmlMimeType("application/pdf")
    protected DataHandler objetoPdf;
    @XmlElement(required = true)
    protected List<CampoTipoValor> parametros;
    @XmlElement(required = true)
    protected String tipoDocumento;

    /**
     * Obtiene el valor de la propiedad objetoPdf.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    public DataHandler getObjetoPdf() {
        return objetoPdf;
    }

    /**
     * Define el valor de la propiedad objetoPdf.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    public void setObjetoPdf(DataHandler value) {
        this.objetoPdf = value;
    }

    /**
     * Gets the value of the parametros property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parametros property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParametros().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CampoTipoValor }
     * 
     * 
     */
    public List<CampoTipoValor> getParametros() {
        if (parametros == null) {
            parametros = new ArrayList<CampoTipoValor>();
        }
        return this.parametros;
    }

    /**
     * Obtiene el valor de la propiedad tipoDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Define el valor de la propiedad tipoDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

}
