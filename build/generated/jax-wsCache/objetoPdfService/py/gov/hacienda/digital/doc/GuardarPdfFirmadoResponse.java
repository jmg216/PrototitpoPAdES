
package py.gov.hacienda.digital.doc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para guardarPdfFirmadoResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="guardarPdfFirmadoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resultPdfFirmado" type="{http://doc.digital.hacienda.gov.py/}resultOperacion" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "guardarPdfFirmadoResponse", propOrder = {
    "resultPdfFirmado"
})
public class GuardarPdfFirmadoResponse {

    protected ResultOperacion resultPdfFirmado;

    /**
     * Obtiene el valor de la propiedad resultPdfFirmado.
     * 
     * @return
     *     possible object is
     *     {@link ResultOperacion }
     *     
     */
    public ResultOperacion getResultPdfFirmado() {
        return resultPdfFirmado;
    }

    /**
     * Define el valor de la propiedad resultPdfFirmado.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultOperacion }
     *     
     */
    public void setResultPdfFirmado(ResultOperacion value) {
        this.resultPdfFirmado = value;
    }

}
