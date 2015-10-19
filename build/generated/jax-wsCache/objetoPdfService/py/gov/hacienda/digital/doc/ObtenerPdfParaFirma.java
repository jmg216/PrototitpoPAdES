
package py.gov.hacienda.digital.doc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtenerPdfParaFirma complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="obtenerPdfParaFirma">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listaParametros" type="{http://doc.digital.hacienda.gov.py/}campoTipoValor" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="cedulaFirmante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerPdfParaFirma", propOrder = {
    "tipoDocumento",
    "listaParametros",
    "cedulaFirmante"
})
public class ObtenerPdfParaFirma {

    protected String tipoDocumento;
    protected List<CampoTipoValor> listaParametros;
    protected String cedulaFirmante;

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

    /**
     * Gets the value of the listaParametros property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaParametros property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaParametros().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CampoTipoValor }
     * 
     * 
     */
    public List<CampoTipoValor> getListaParametros() {
        if (listaParametros == null) {
            listaParametros = new ArrayList<CampoTipoValor>();
        }
        return this.listaParametros;
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

}
