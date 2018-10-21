/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafociclotour.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Loaiza
 * @author Santiago Betancur
 * @version V.8
 */
public class VerticeDijkstra implements Serializable {

    /**
     * Atributo de tipo vertice donde esta cada dato del vertice con su codigo
     * Atributo verticeAntecesor donde esta los antecesores de cada vertice
     * Atributo pesoAcumulado de tipo entero Atributo de listadoAdyacencias de
     * cada vertice Atributo boolean indicando el estado del vertice
     */
    private Vertice vertice;
    private Vertice verticeAntecesor;
    private int pesoAcumulado;
    private List<VerticeDijkstra> listadoAdyacencias;
    private boolean usado = false;

    public VerticeDijkstra() {
    }

    public Vertice getVertice() {
        return vertice;
    }

    public void setVertice(Vertice vertice) {
        this.vertice = vertice;
    }

    public Vertice getVerticeAntecesor() {
        return verticeAntecesor;
    }

    public void setVerticeAntecesor(Vertice verticeAntecesor) {
        this.verticeAntecesor = verticeAntecesor;
    }

    public int getPesoAcumulado() {
        return pesoAcumulado;
    }

    public void setPesoAcumulado(int pesoAcumulado) {
        this.pesoAcumulado = pesoAcumulado;
    }

    public List<VerticeDijkstra> getListadoAdyacencias() {
        return listadoAdyacencias;
    }

    public void setListadoAdyacencias(List<VerticeDijkstra> listadoAdyacencias) {
        this.listadoAdyacencias = listadoAdyacencias;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    /**
     * Metodo de que permite llenar las adyacencias de los nodos ingresados
     *
     * @author Carlos Loaiza
     * @author Santiago Betancur
     * @param grafo
     * @param listadoVertices
     */
    public void llenarAdyacenciasVertice(GrafoNoDirigido grafo, List<VerticeDijkstra> listadoVertices) {
        listadoAdyacencias = new ArrayList<>();
        /**
         * Ciclo que busca la arista de dependiendo del codigo solicitado
         */
        for (Arista arista : grafo.getAristas()) {
            int codigoDestino = 0;
            if (arista.getOrigen() == vertice.getCodigo()) {
                codigoDestino = arista.getDestino();
            }
            if (arista.getDestino() == vertice.getCodigo()) {
                codigoDestino = arista.getOrigen();
            }
            if (codigoDestino != 0) {
                /**
                 * varible de tipo VerticeDijkstra que envia el vertices del
                 * destino
                 */
                VerticeDijkstra vert = obtenerVerticeDijkstraxCodigo(codigoDestino, listadoVertices);//verificar vacio

                if (vert != null) {

                    /**
                     * Condición que identifica si el vertice ya esta usado, de
                     * lo contrario le adiciona vertice antecesor y su peso
                     * acumulado
                     */
                    if (!vert.isUsado()) { //Pregunta si ya ha sido usado
                        if (vert.getVerticeAntecesor() != null) {
                            //Ya se había gestionado una ruta anterior
                            int nuevoPeso = this.getPesoAcumulado() + arista.getPeso();
                            if (nuevoPeso < vert.getPesoAcumulado()) {
                                vert.setVerticeAntecesor(this.vertice);
                                vert.setPesoAcumulado(nuevoPeso);
                            }
                        } else {
                            vert.setVerticeAntecesor(this.vertice);
                            vert.setPesoAcumulado(this.getPesoAcumulado() + arista.getPeso());
                        }
                        listadoAdyacencias.add(vert);
                    }

                } else {
                    /**
                     * Condición que indica que si el verice no esta se adiciona
                     * como nuevo en el VerticeDijkstra()
                     */
                    vert = new VerticeDijkstra();
                    vert.setVertice(grafo.obtenerVerticexCodigo(codigoDestino));
                    vert.setPesoAcumulado(this.getPesoAcumulado() + arista.getPeso());
                    vert.setVerticeAntecesor(this.vertice);
                    listadoAdyacencias.add(vert);
                }
            }
        }
    }

    /**
     * Metodo que busca un vertice por su codigo
     *
     * @author Carlos Loaiza
     * @author Santiago Betancur
     * @param codigo
     * @param listadoVertices
     * @return
     */
    public VerticeDijkstra obtenerVerticeDijkstraxCodigo(int codigo, List<VerticeDijkstra> listadoVertices) {
        for (VerticeDijkstra vert : listadoVertices) {
            if (vert.getVertice().getCodigo() == codigo) {
                return vert;
            }
        }
        return null;
    }

    /**
     * Variable de prueba que indica el salto indentificando el primer error que
     * tenia.
     *
     * @author Santiago Betancur
     */
    private VerticeDijkstra prueba = null;

    /**
     * Metodo que busca un codigo heindica si el codigo ya ha sido usado
     *
     * @author Santiago Betancur
     * @param listadoVertices
     * @return
     */
    public VerticeDijkstra obtenerVerticeDijkstraxEstado(List<VerticeDijkstra> listadoVertices) {
        for (VerticeDijkstra vert : listadoVertices) {
            if (vert.usado == false) {
                return vert;
            }
        }
        return null;
    }

    /**
     * Metodo que obtiene la adyacencia de menor peso y verifica con una mejora
     * de con la varible prueba si no tiene adyacencia, encontro la ruta
     * verificar todos los demas nodos que faltan por visitar para dar
     * finalización a la busqueda
     *
     * @author Carlos Loaiza
     * @author Santiago Betancur
     * @param listadoVertices
     * @return VerticeDijkstra
     */
    public VerticeDijkstra obtenerAdyacenciaMenorPeso(List<VerticeDijkstra> listadoVertices) {
        int menor = Integer.MAX_VALUE;
        VerticeDijkstra vertMenor = null;
        if (!listadoAdyacencias.isEmpty()) {
            for (VerticeDijkstra vert : listadoAdyacencias) { //Verificar en recorrido el peso acumulado
                if (!vert.isUsado() && vert.getPesoAcumulado() <= menor) {//Se realiza cambio obligació de pasar por (B)
                    vertMenor = vert;
                    menor = vert.getPesoAcumulado();
                }
            }
        } else {
            vertMenor = prueba = obtenerVerticeDijkstraxEstado(listadoVertices);//verificar vacio
        }
        return vertMenor;
    }
}
