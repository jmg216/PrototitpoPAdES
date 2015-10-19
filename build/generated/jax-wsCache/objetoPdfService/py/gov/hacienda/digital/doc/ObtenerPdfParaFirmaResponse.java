
package py.gov.hacienda.digital.doc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtenerPdfParaFirmaResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="obtenerPdfParaFirmaResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resultPdfParaFirma" type="{http://doc.digital.hacienda.gov.py/}documentoElectronico" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerPdfParaFirmaResponse", propOrder = {
    "resultPdfParaFirma"
})
public class ObtenerPdfParaFirmaResponse {

    protected DocumentoElectronico resultPdfParaFirma;

    /**
     * Obtiene el valor de la propiedad resultPdfParaFirma.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoElectronico }
     *     
     */
    public DocumentoElectronico getResultPdfParaFirma() {
        return resultPdfParaFirma;
    }

    /**
     * Define el valor de la propiedad resultPdfParaFirma.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoElectronico }
     *     
     */
    public void setResultPdfParaFirma(DocumentoElectronico value) {
        this.resultPdfParaFirma = value;
    }

}
