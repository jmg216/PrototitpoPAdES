
package py.gov.hacienda.digital.doc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para tipoDato.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoDato">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INTEGER"/>
 *     &lt;enumeration value="LONG"/>
 *     &lt;enumeration value="STRING"/>
 *     &lt;enumeration value="DATE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipoDato")
@XmlEnum
public enum TipoDato {

    INTEGER,
    LONG,
    STRING,
    DATE;

    public String value() {
        return name();
    }

    public static TipoDato fromValue(String v) {
        return valueOf(v);
    }

}
