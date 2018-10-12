package grafociclotour.dto;

import grafociclotour.controlador.ControladorGrafo;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Clase que me permite manejar he instanciar mis clases principales mas
 * utilizadas, teniedo acceso a los mas utilizado.
 *
 * @author Santiago Betancur
 */
@Stateless
public class GrafosMunicipioDto implements Serializable {

    //Atributos
    @EJB
    ControladorGrafo contGrafo = new ControladorGrafo();

    //Constructor
    public GrafosMunicipioDto() {
    }
    //Getter and Setters

    //Metodos
    public ControladorGrafo getContGrafo() {
        return contGrafo;
    }

    public void setContGrafo(ControladorGrafo contGrafo) {
        this.contGrafo = contGrafo;
    }
}
