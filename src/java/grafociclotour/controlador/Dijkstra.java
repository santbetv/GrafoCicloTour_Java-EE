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
 * @author Carlos loaiza
 * @author Santiago Betancur
 * @version V.8
 */
public class Dijkstra implements Serializable {

    /**
     * Atributo grafo donde se realiza extends a la clase GrafoAbstract para
     * tener los verices y las arista Atributo nodoInicio y nodoFinal para tener
     * la direccion de la ruta
     */
    private GrafoNoDirigido grafo;
    private Vertice nodoInicio;
    private Vertice nodoFinal;

    public Dijkstra() {
    }

    /**
     * Constructos principal de clase de se solicita un grafo y la dirrecion de
     * este.
     *
     * @param grafo
     * @param nodoInicio
     * @param nodoFinal
     */
    public Dijkstra(GrafoNoDirigido grafo, Vertice nodoInicio, Vertice nodoFinal) {
        this.grafo = grafo;
        this.nodoInicio = nodoInicio;
        this.nodoFinal = nodoFinal;
    }

    /**
     * Atributo que ayuda a validar si la ruta esta en el grafo
     */
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
        /**
         * Ciclo que permite recorrer segun sus vertices, y determinar la ruta
         * mas corta del primer grafo
         *
         */
        while (contVertusados < grafo.getVertices().size()) {
            //se llenan las adyacencias del antecesor que no estén marcadas
            antecesor.llenarAdyacenciasVertice(grafo, listadoVerticesUsados);
            VerticeDijkstra menor = antecesor.obtenerAdyacenciaMenorPeso(listadoVerticesUsados);
            /**
             * Condicion que indica si la ruta ya fue encontrada, restar los
             * nodos faltantes para poder continuar con el proceso.
             */
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
        VerticeDijkstra verificarFinal = obtenerVerticeAntecesorxCodigo(codigoFianalAnt, listadoVerticesUsados);
        /**
         * Condición que verifica si la ruta existe, para poder devolver la ruta
         * más corta
         */
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

    /**
     * Metodo que devuelve un VeriticeDijstra con su verticeAntecesor, su
     * vértice, su estado de uso
     *
     * @param codigo
     * @param lista
     * @return VerticeDijkstra
     */
    public VerticeDijkstra obtenerVerticeAntecesorxCodigo(int codigo, List<VerticeDijkstra> lista) {
        for (VerticeDijkstra vert : lista) {
            if (vert.getVertice().getCodigo() == codigo) {
                return vert;
            }
        }
        return null;
    }

    /**
     * Metodo que cuenta cuantos vértices estan en uso
     *
     * @param lista
     * @return int la suma se los verices
     */
    public int contarVerticesUsados(List<VerticeDijkstra> lista) {
        int cont = 0;
        for (VerticeDijkstra vert : lista) {
            if (vert.isUsado()) {
                cont++;
            }
        }
        return cont;
    }

    /**
     * Metodo que busca adyacencia de los verices
     *
     * @param listadoAdyacencias
     * @param listadoVerticesUsados
     * @return VerticeDijkstra
     */
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
     * Metodo que busca todas la rutas posibles
     *
     * @author Sebastian Gomez Quintero
     * @author Santiago Betancur V
     * @param verticeInicio
     * @param verticeFin
     */
    public void calcularRutasPosibles(Vertice verticeInicio, Vertice verticeFin) {
        validadorRut = false;
        List<Vertice> ruta = new ArrayList<>();
        /**
         * Ciclo que busca del grafo el grafo siguiente a sus adyacencias
         */
        for (Vertice verticeSiguiente : buscarVerices(verticeInicio)) {//Se realiza la busqueda de los vertices siguientes de cada nodo
            ruta.add(verticeInicio);
            calcularRutasPosibles(verticeSiguiente, verticeFin, verticeInicio, ruta);//Envia el vertice siguiente al inicial, llamando el metodo recursivo
            //  adicionarEnRutas(ruta, verticeFin, verticeInicio);
            ruta.clear();
        }
        if (rutas != null) {
            validadorRut = true;
        }
    }

    public List<Vertice> calcularRutica() {
        List<Vertice> ruta = new ArrayList<>();
        int pesoMin = Integer.MAX_VALUE;
        if (rutas.size() > 0) {
            for (List<Vertice> rutaN : rutas) {
                if (obtenerPesoLista(rutaN) < pesoMin) {
                    ruta = rutaN;
                    pesoMin = obtenerPesoLista(rutaN);
                }
            }
        }
        return ruta;
    }

    /**
     * Atributo de lista que almacena una lista de vertices
     */
    private List<List<Vertice>> rutas = new ArrayList<>();

    public List<List<Vertice>> getRutas() {
        return rutas;
    }

    public void setRutas(List<List<Vertice>> rutas) {
        this.rutas = rutas;
    }

    /**
     * Metodo Recursivo que calcula todas las rutas posibles, realizado una
     * busqueda vertice por vetices verificando sus siguientes determinando
     * todas las rutas posibles
     *
     * @param nodo
     * @param verticeFin
     * @param verticeInicio
     * @param ruta
     */
    public void calcularRutasPosibles(Vertice nodo, Vertice verticeFin, Vertice verticeInicio, List<Vertice> ruta) {
        if (nodo != verticeFin) {
            if (!validarExistenciaRuta(ruta, nodo)) {//Verifica que el nodo no se encuentre en la ruta
                ruta.add(nodo);
                for (Vertice verticeSiguiente : buscarVerices(nodo)) {//busca entre los vertices adyacentes para buscar sus recoridos 
                    calcularRutasPosibles(verticeSiguiente, verticeFin, verticeInicio, ruta);
                    if (buscarVerices(nodo).size() > 1) {
//                        if (ruta.get(ruta.size() - 1) == verticeFin) {
//                            adicionarEnRutas(ruta, verticeFin, verticeInicio);
//                        }
                        for (int i = 0; i < ruta.size(); i++) { //Recorra toda la ruta
                            if (ruta.get(i) == nodo) {//borrar todos los sigientes anteriores al nodo en la ruta 
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

    /**
     * Metodo boolean que de termina la existencia de la ruta
     *
     * @param ruta
     * @param nodo
     * @return true o flase
     */
    public boolean validarExistenciaRuta(List<Vertice> ruta, Vertice nodo) {
        for (Vertice verticeDeRuta : ruta) {
            if (nodo == verticeDeRuta) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo adiciona todas las rutas posibles en una lista de rutas
     *
     * @param ruta
     * @param verticeFin
     * @param verticeInicio
     */
    public void adicionarEnRutas(List<Vertice> ruta, Vertice verticeFin, Vertice verticeInicio) {//valida existencia en rutas y adiciona 
        boolean añadir = true;
        if (ruta != null) {
            if (rutas != null) {
                /**
                 * Ciclo que recorre las rutas verificando si la ruta que me
                 * mandaron ya esta en la rutas
                 */
                for (List<Vertice> rutasGuardadas : rutas) {
                    int iguales = 0;// cuantos iguales hay por ruta guardada y la ruta que me mandaron
                    int sum = 0;
                    /**
                     * Ciclo que recorre las rutasGuardadas he indica si la ruta
                     * ya si no esta dara acceso para guradar la nueva ruta
                     *
                     */
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
                    /**
                     * Ciclo que recorre la ruta y añade un nuevo objeto de tipo
                     * vertices a la a lista de rutaGuardar para depues darla
                     * toda esa ruta a la lista principal de rutas.
                     */
                    for (Vertice ver : ruta) {
                        rutaGuardar.add(new Vertice(ver.getCodigo(), ver.getDato()));
                    }
                    rutas.add(rutaGuardar);
                }
            }
        }

    }

    /**
     * Metodo que busca arista segun el nodo que tenga como parametro,
     * devolviendo una lista de aristas del nodo buscado.
     *
     * @param nodo
     * @return
     */
    public List<Arista> buscarAristas(Vertice nodo) {
        List<Arista> aristasDeNodo = new ArrayList<>();
        for (Arista arista : grafo.getAristas()) {
            if (arista.getOrigen() == nodo.getCodigo() || arista.getDestino() == nodo.getCodigo()) {
                aristasDeNodo.add(arista);
            }
        }
        return aristasDeNodo;
    }

    /**
     * Metodo que busca verices segun el nodo buscado devolviendo sus vertices
     *
     * @param nodo
     * @return List vérice
     */
    public List<Vertice> buscarVerices(Vertice nodo) {//Busca todos los vertices de cada nodo
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

    /**
     * Metodo que busca un vertice solo por su codigo en la lista de vertices
     *
     * @param codigo
     * @return
     */
    public Vertice obtenerVerticexCodigo(int codigo) {//Busca el vertice por codigo indicado
        for (Vertice vert : grafo.getVertices()) {
            if (vert.getCodigo() == codigo) {
                return vert;
            }
        }
        return null;
    }

    /**
     * Metodo que busca el peso de cada ruta que se le pasa por parametro
     *
     * @param ruta
     * @return
     */
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

    public List<Vertice> calcularRutasPosiblesWS(Vertice verticeInicio, Vertice verticeFin) {
        validadorRut = false;
        List<Vertice> ruta = new ArrayList<>();//se crea una ruta para guardar las posibles rutas temporalmente y agregarlas a la lista de rutas
        for (Vertice verticeSiguiente : buscarVerices(verticeInicio)) {//recorrel las adyacencias del inicio
            ruta.add(verticeInicio);//lo añade a la primera 
            calcularRutasPosibles(verticeSiguiente, verticeFin, verticeInicio, ruta);//calcula las rutas posibles
            adicionarEnRutas(ruta, verticeFin, verticeInicio);// se adiciona la ruta en la lista 
            ruta.clear();//se limpia la ruta temporal
        }
        if (rutas != null) {//se valida que las rutas sea diferente de null
            validadorRut = true;//se usa para saber si hay una ruta 
        }
        List<Vertice> rutaCorta = new ArrayList<>();
        int pesoMin = Integer.MAX_VALUE;
        for (List<Vertice> rutaN : rutas) {//se recore ra lista de rutas
            if (obtenerPesoLista(rutaN) < pesoMin) {//se valida si la ruta es mas optima que la anterior y se guarda en la ruta a mostrar
                rutaCorta = rutaN;
                pesoMin = obtenerPesoLista(rutaN);//se calcula el peso de la ruta 
            }
        }
        return rutaCorta;
    }

    public List<Vertice> calcularRutaMasLarga(Vertice verticeInicio, Vertice verticeFin) {
        validadorRut = false;
        List<Vertice> ruta = new ArrayList<>();//se crea una ruta para guardar las posibles rutas temporalmente y agregarlas a la lista de rutas
        for (Vertice verticeSiguiente : buscarVerices(verticeInicio)) {//recorrel las adyacencias del inicio
            ruta.add(verticeInicio);//lo añade a la primera 
            calcularRutasPosibles(verticeSiguiente, verticeFin, verticeInicio, ruta);//calcula las rutas posibles
            adicionarEnRutas(ruta, verticeFin, verticeInicio);// se adiciona la ruta en la lista 
            ruta.clear();//se limpia la ruta temporal
        }
        if (rutas != null) {//se valida que las rutas sea diferente de null
            validadorRut = true;//se usa para saber si hay una ruta 
        }
        List<Vertice> rutaMasLarga = new ArrayList<>();
        int pesoMin = Integer.MIN_VALUE;
        for (List<Vertice> rutaN : rutas) {//se recore ra lista de rutas
            if (obtenerPesoLista(rutaN) > pesoMin) {//se valida si la ruta es mas optima que la anterior y se guarda en la ruta a mostrar
                rutaMasLarga = rutaN;
                pesoMin = obtenerPesoLista(rutaN);//se calcula el peso de la ruta 
            }
        }
        return rutaMasLarga;
    }
}
