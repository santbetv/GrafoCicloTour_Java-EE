/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafociclotour.modelo;

import grafociclotour.excepciones.GrafoExcepcion;
import java.io.Serializable;

/**
 * Clase de excepciones principales que grita cuando se requiera
 *
 * @author Carlos Loaiza
 * @author Santiago Betancur
 * @version V.8
 *
 *
 */
public class GrafoDirigido extends GrafoAbstract implements Serializable {

    /**
     * Exepción con nombre verificarArita que lanza un mesaje de error cuando ya
     * hay una comunicacion entre dos vertices
     *
     * @param origen
     * @param destino
     * @throws GrafoExcepcion
     */
    @Override
    public void verificarArista(int origen, int destino) throws GrafoExcepcion {
        for (Arista ar : this.getAristas()) {
            if (ar.getOrigen() == origen && ar.getDestino() == destino) {
                throw new GrafoExcepcion("Ya existe comunicación entre los dos vertices");
            }
        }
    }

}
