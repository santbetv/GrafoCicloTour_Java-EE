/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafociclotour.modelo;

import grafociclotour.excepciones.GrafoExcepcion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que ayuda en la herencia de datos obteniendo de esta todos los verices
 * y aristas
 *
 * @author Carlos loaiza
 * @author Santiago Betancur
 * @Version V.8
 */
public abstract class GrafoAbstract implements Serializable {

    /**
     * Atributos principales que crear una lista de verices y aristas
     */
    private List<Vertice> vertices;
    private List<Arista> aristas;

    public GrafoAbstract() {
        vertices = new ArrayList<>();
        aristas = new ArrayList<>();
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertice> vertices) {
        this.vertices = vertices;
    }

    public List<Arista> getAristas() {
        return aristas;
    }

    public void setAristas(List<Arista> aristas) {
        this.aristas = aristas;
    }

    public void adicionarVertice(Vertice vertice) {
        vertices.add(vertice);
    }

    public void adicionarArista(Arista arista) {
        aristas.add(arista);
    }

    public void removerArista(int origen, int destino) {
        List<Arista> tem = new ArrayList<>();
        for (Arista ar : aristas) {
            if (!(ar.getOrigen() == origen && ar.getDestino() == destino)) {
                tem.add(ar);
            }
        }
        aristas = tem;
    }

    public abstract void verificarArista(int origen, int destino) throws GrafoExcepcion;

    public Vertice obtenerVerticexCodigo(int codigo) {
        for (Vertice vert : vertices) {
            if (vert.getCodigo() == codigo) {
                return vert;
            }
        }

        return null;
    }

    public void eliminarAristas() {
        aristas.clear();
    }

    public Vertice obtenerVerticexNombre(String nombre) {
        for (Vertice vert : vertices) {
            if (vert.getDato().getNombre().compareTo(nombre) == 0) {
                return vert;
            }
        }
        return null;
    }
}
