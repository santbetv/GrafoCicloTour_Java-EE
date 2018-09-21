package grafociclotour.dto;

import grafociclotour.controlador.ControladorGrafo;
import java.io.Serializable;

/**
 * @author Santiago Betancur
 */
public class GrafosMunicipioDto implements Serializable {

    //Atributos
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
