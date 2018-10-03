/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafociclotour.controlador;

import grafociclotour.modelo.GrafoNoDirigido;
import grafociclotour.modelo.Vertice;
import grafociclotour.modelo.VerticeDijkstra;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Santiago Betancur
 */
public class Dijkstra implements Serializable {

    private GrafoNoDirigido grafo;
    private Vertice nodoInicio;
    private Vertice nodoFinal;

    public Dijkstra() {
    }

    public Dijkstra(GrafoNoDirigido grafo, Vertice nodoInicio, Vertice nodoFinal) {
        this.grafo = grafo;
        this.nodoInicio = nodoInicio;
        this.nodoFinal = nodoFinal;
    }

    private boolean validadorRut;

    public boolean isValidadorRut() {
        return validadorRut;
    }

    public void setValidadorRut(boolean validadorRut) {
        this.validadorRut = validadorRut;
    }

    public List<Vertice> calcularRutaMasCorta() {
        List<Vertice> ruta = new ArrayList<>();
        VerticeDijkstra inicio = new VerticeDijkstra();
        inicio.setPesoAcumulado(0);
        inicio.setVerticeAntecesor(null);
        inicio.setUsado(true);
        inicio.setVertice(nodoInicio);
        List<VerticeDijkstra> listadoVerticesUsados = new ArrayList<>();
        listadoVerticesUsados.add(inicio);
        /// se hace dijkstra hasta que todos los vertices estén marcados
        VerticeDijkstra antecesor = inicio;
        int contVertusados = 1;
        while (contVertusados < grafo.getVertices().size()) {
            //se llenan las adyacencias del antecesor que no estén marcadas
            antecesor.llenarAdyacenciasVertice(grafo, listadoVerticesUsados);
            VerticeDijkstra menor = antecesor.obtenerAdyacenciaMenorPeso(listadoVerticesUsados);
            if (menor != null) {
                menor.setUsado(true);//Inicio de error
                listadoVerticesUsados.addAll(this.obtenerAdyacenciasNuevas(antecesor.getListadoAdyacencias(), listadoVerticesUsados));
                antecesor = menor;
                //se busca la menor adyacencia y se inicia de nuevo el proceso
                contVertusados = contarVerticesUsados(listadoVerticesUsados);
            } else {
                contVertusados += (-1 * (contVertusados - grafo.getVertices().size()));
            }

        }
        //Ya deben estar los antecesores listos y enlazados, se deben recorrer de atras hacia adelante 
        //hasta llenar la ruta empezando en el nodo final
        VerticeDijkstra destino = null;
        int codigoFianalAnt = nodoFinal.getCodigo();
        VerticeDijkstra verificarFinal = obtenerVerticeAntecesorxCodigo(codigoFianalAnt, listadoVerticesUsados);;
        if (verificarFinal != null) {
            ruta.add(nodoFinal);
            int codigoAnt = nodoFinal.getCodigo();
            do {
                destino = obtenerVerticeAntecesorxCodigo(codigoAnt, listadoVerticesUsados);
                codigoAnt = destino.getVerticeAntecesor().getCodigo();
                ruta.add(destino.getVerticeAntecesor());
            } while (codigoAnt != nodoInicio.getCodigo());
            validadorRut = true;
        } else {
            validadorRut = false;
        }
        return ruta;
    }

    public VerticeDijkstra obtenerVerticeAntecesorxCodigo(int codigo, List<VerticeDijkstra> lista) {
        for (VerticeDijkstra vert : lista) {
            if (vert.getVertice().getCodigo() == codigo) {
                return vert;
            }
        }
        return null;
    }

    public int contarVerticesUsados(List<VerticeDijkstra> lista) {
        int cont = 0;
        for (VerticeDijkstra vert : lista) {
            if (vert.isUsado()) {
                cont++;
            }
        }
        return cont;
    }

    public List<VerticeDijkstra> obtenerAdyacenciasNuevas(List<VerticeDijkstra> listadoAdyacencias, List<VerticeDijkstra> listadoVerticesUsados) {
        List<VerticeDijkstra> nuevas = new ArrayList<>();
        for (VerticeDijkstra vert : listadoAdyacencias) {
            if (vert.obtenerVerticeDijkstraxCodigo(vert.getVertice().getCodigo(), listadoVerticesUsados) == null) {
                nuevas.add(vert);
            }
        }
        return nuevas;
    }

}
