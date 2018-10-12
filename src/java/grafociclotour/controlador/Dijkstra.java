/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafociclotour.controlador;

import grafociclotour.modelo.Arista;
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

    /**
     * @author Sebastian Gomez Quintero
     * @author Santiago Betancur V
     * @param verticeInicio
     * @param verticeFin
     */
    public void calcularRutasPosibles(Vertice verticeInicio, Vertice verticeFin) {
        validadorRut = false;
        List<Vertice> ruta = new ArrayList<>();
        for (Vertice verticeSiguiente : buscarVerices(verticeInicio)) {
            ruta.add(verticeInicio);
            calcularRutasPosibles(verticeSiguiente, verticeFin, verticeInicio, ruta);
            adicionarEnRutas(ruta, verticeFin, verticeInicio);
            ruta.clear();
        }
        if (rutas != null) {
            validadorRut = true;
        }
    }

    private List<List<Vertice>> rutas = new ArrayList<>();

    public List<List<Vertice>> getRutas() {
        return rutas;
    }

    public void setRutas(List<List<Vertice>> rutas) {
        this.rutas = rutas;
    }

    public void calcularRutasPosibles(Vertice nodo, Vertice verticeFin, Vertice verticeInicio, List<Vertice> ruta) {
//        buscarVerices(nodo);        
        if (nodo != verticeFin) {
            if (!validarExistenciaRuta(ruta, nodo)) {
                ruta.add(nodo);
                for (Vertice verticeSiguiente : buscarVerices(nodo)) {//busca entre los vertices adyacentes para buscar sus recoridos 
                    calcularRutasPosibles(verticeSiguiente, verticeFin, verticeInicio, ruta);
                    if (buscarVerices(nodo).size() > 1) {
                        if (ruta.get(ruta.size() - 1) == verticeFin) {//en duda
                            adicionarEnRutas(ruta, verticeFin, verticeInicio);
                        }
                        for (int i = 0; i < ruta.size(); i++) {
                            if (ruta.get(i) == nodo) {//borrar todos los sigientes al nodo en la ruta 
                                for (int o = ruta.size() - 1; o > i; o--) {
                                    ruta.remove(o);
                                }
                            }
                        }
                    }
                }
            }

        } else {
            ruta.add(verticeFin);
            adicionarEnRutas(ruta, verticeFin, verticeInicio);
        }
    }

    public boolean validarExistenciaRuta(List<Vertice> ruta, Vertice nodo) {
        for (Vertice verticeDeRuta : ruta) {
            if (nodo == verticeDeRuta) {
                return true;
            }
        }
        return false;
    }

    public void adicionarEnRutas(List<Vertice> ruta, Vertice verticeFin, Vertice verticeInicio) {//valida existencia en rutas y adiciona 
        boolean añadir = true;
        if (ruta != null) {
            if (rutas != null) {
                for (List<Vertice> rutasGuardadas : rutas) {
                    int iguales = 0;// cuantos iguales hay por ruta guardada y la ruta que me mandaron
                    int sum = 0;
                    for (Vertice rutaG : rutasGuardadas) {
                        if (rutaG.getCodigo() == ruta.get(sum).getCodigo()) {
                            iguales++;
                        }
                        if (sum + 1 < ruta.size()) {
                            sum++;
                        }
                        if (rutasGuardadas.size() == iguales) {
                            añadir = false;
                        }
                    }
                }
            }

            if (añadir) {
                if (ruta.get(0).getCodigo() == verticeInicio.getCodigo() && ruta.get(ruta.size() - 1).getCodigo() == verticeFin.getCodigo()) {
                    List<Vertice> rutaGuardar = new ArrayList<>();
                    for (Vertice ver : ruta) {
                        rutaGuardar.add(new Vertice(ver.getCodigo(), ver.getDato()));
                    }
                    rutas.add(rutaGuardar);
                }
            }
        }

    }

    public List<Arista> buscarAristas(Vertice nodo) {
        List<Arista> aristasDeNodo = new ArrayList<>();
        for (Arista arista : grafo.getAristas()) {
            if (arista.getOrigen() == nodo.getCodigo() || arista.getDestino() == nodo.getCodigo()) {
                aristasDeNodo.add(arista);
            }
        }
        return aristasDeNodo;
    }

    public List<Vertice> buscarVerices(Vertice nodo) {
        List<Vertice> verticesDeNodo = new ArrayList<>();
        for (Arista arista : grafo.getAristas()) {
            if (arista.getOrigen() == nodo.getCodigo()) {
                verticesDeNodo.add(obtenerVerticexCodigo(arista.getDestino()));
            } else if (arista.getDestino() == nodo.getCodigo()) {
                verticesDeNodo.add(obtenerVerticexCodigo(arista.getOrigen()));
            }
        }
        return verticesDeNodo;
    }

    public Vertice obtenerVerticexCodigo(int codigo) {
        for (Vertice vert : grafo.getVertices()) {
            if (vert.getCodigo() == codigo) {
                return vert;
            }
        }
        return null;
    }

    public int obtenerPesoLista(List<Vertice> ruta) {
        int peso = 0;
        if (ruta != null) {
            for (int a = 0; a < ruta.size(); a++) {
                for (int i = 0; i < grafo.getAristas().size(); i++) {
                    if (a + 1 < ruta.size()) {
                        if (ruta.get(a).getCodigo() == grafo.getAristas().get(i).getOrigen() && ruta.get(a + 1).getCodigo() == grafo.getAristas().get(i).getDestino()
                                || ruta.get(a).getCodigo() == grafo.getAristas().get(i).getDestino() && ruta.get(a + 1).getCodigo() == grafo.getAristas().get(i).getOrigen()) {
                            peso += grafo.getAristas().get(i).getPeso();
                        }
                    }

                }

                grafo.getAristas();
            }
        }
        return peso;
    }
}
