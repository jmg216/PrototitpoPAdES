
package py.gov.hacienda.digital.doc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtenerPdfParaValidarResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="obtenerPdfParaValidarResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resultPdfParaValidar" type="{http://doc.digital.hacienda.gov.py/}documentoElectronico" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerPdfParaValidarResponse", propOrder = {
    "resultPdfParaValidar"
})
public class ObtenerPdfParaValidarResponse {

    protected DocumentoElectronico resultPdfParaValidar;

    /**
     * Obtiene el valor de la propiedad resultPdfParaValidar.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoElectronico }
     *     
     */
    public DocumentoElectronico getResultPdfParaValidar() {
        return resultPdfParaValidar;
    }

    /**
     * Define el valor de la propiedad resultPdfParaValidar.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoElectronico }
     *     
     */
    public void setResultPdfParaValidar(DocumentoElectronico value) {
        this.resultPdfParaValidar = value;
    }

}
