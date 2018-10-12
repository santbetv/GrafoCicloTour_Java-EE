/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafociclotour.servicios;

/**
 *
 * @author Santiago Betancur Villegas
 */
import grafociclotour.controlador.ControladorGrafo;
import grafociclotour.controlador.Dijkstra;
import grafociclotour.modelo.Arista;
import grafociclotour.modelo.Municipio;
import grafociclotour.modelo.Vertice;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * @author Santiago Betancur
 */
@WebService(serviceName = "WsGrafoCicloTour")
@Stateless
public class WsGrafoCicloTour {

    @EJB
    private ControladorGrafo controlGrafo;

//    @WebMethod(operationName = "iniciarGrafo")
//    public String iniciarGrafo() {
//
//        controlGrafo = new ControladorGrafo();
//        controlGrafo.inicializar();
//
//        return "Grafo Iniciado";
//    }
    //Inicio de Metodos de vertice
    /**
     *
     * Metodo que devuelve una lista en el web Service
     *
     * @author Carlos loaiza
     * @author Santiago Betancur
     * @Version V.8
     * @return List de vertices
     */
    @WebMethod(operationName = "listarVertices")
    public List<Vertice> listarVertices() {
        return controlGrafo.getGrafoND().getVertices();
    }

    /**
     * Metodeo creea un vertice en el Web Service
     *
     * @author Santiago Betancur
     * @Version V.8
     * @param municipio
     * @return un mensaje de indicando la creacion del vertice
     */
    @WebMethod(operationName = "crearVertice")
    public String crearVertice(@WebParam(name = "municipio") Municipio municipio) {
        controlGrafo.getGrafoND().adicionarVertice(
                new Vertice(controlGrafo.getGrafoND().getVertices().size() + 1,
                        municipio));
        controlGrafo.pintarGrafo(controlGrafo.getGrafoND());
        return "Vertice creado";
    }

    /**
     *
     * @author Santiago Betancur
     * @Version V.8
     * @param codigo
     * @return retorna un mesaje de vertice borrado
     */
    @WebMethod(operationName = "borrarVertice")
    public String borrarVertice(@WebParam(name = "borarVerice") int codigo) {
        for (int i = 0; i < controlGrafo.getGrafoND().getVertices().size(); i++) {
            if (controlGrafo.getGrafoND().getVertices().get(i).getCodigo() == codigo) {
                controlGrafo.getGrafoND().getVertices().remove(i);
            }
        }
        controlGrafo.pintarGrafo(controlGrafo.getGrafoND());
        return "Vertice Borrado";
    }

    /**
     * Metodo que edita el vertice en el web service
     *
     * @author Santiago Betancur
     * @Version V.8
     * @param codigo
     * @param nombre
     * @param posx
     * @param posy
     * @return un mesaje indicando si el verice es editado o no fue editado
     */
    @WebMethod(operationName = "editarVertice")
    public String editarVertice(@WebParam(name = "codigo") int codigo, @WebParam(name = "nombre") String nombre, @WebParam(name = "posx") int posx,
            @WebParam(name = "posy") int posy) {
        String validacion = "";
        for (int i = 0; i < controlGrafo.getGrafoND().getVertices().size(); i++) {
            if (controlGrafo.getGrafoND().getVertices().get(i).getCodigo() == codigo) {
                controlGrafo.getGrafoND().getVertices().get(i).getDato().setNombre(nombre);
                controlGrafo.getGrafoND().getVertices().get(i).getDato().setPosx(posx);
                controlGrafo.getGrafoND().getVertices().get(i).getDato().setPosy(posy);
                validacion = "Vertice editado";
                break;
            } else {
                validacion = "Vertice NO se encuentra";
            }
        }
        return validacion;
    }

    //Final de Metodos de vertice
    /**
     * Metodo que me permite crear una arista
     *
     * @author Santiago Betancur
     * @Version V.8
     * @param origen
     * @param destino
     * @param peso
     * @return retorna un mesaje indicando si ya existe o no existe comunicacion
     * retorna mensaje de si el destino existe o no existe y si su ariste es
     * creada
     */
    //Inicio de metodos de aristas
    @WebMethod(operationName = "crearArista")
    public String crearArista(@WebParam(name = "origen") int origen, @WebParam(name = "destino") int destino, @WebParam(name = "peso") int peso) {
        if (controlGrafo.getGrafoND().verificarOrigenVerticeWs(origen)) {
            if (controlGrafo.getGrafoND().verificarDestinoVerticeWs(destino)) {
                if (controlGrafo.getGrafoND().verificarAristaWs(origen, destino)) {
                    return "Ya existe comunicación entre los dos vertices";
                } else {
                    controlGrafo.getGrafoND().adicionarArista(new Arista(origen, destino, peso));
                }
            } else {
                return "Destino NO existe";
            }
        } else {
            return "Origen NO existe";
        }
        controlGrafo.pintarGrafo(controlGrafo.getGrafoND());
        return "Arista Creada";
    }

    /**
     * Metodo que elimina la arista indicando su origen y su destino
     *
     * @author Santiago Betancur
     * @Version V.8
     * @param origen
     * @param destino
     * @return retorna un mesaje de indicando si la arista fue eliminada si el
     * destino no existe si el origen no existe o si no existe la arista
     */
    @WebMethod(operationName = "eliminarArista")
    public String eliminarArista(@WebParam(name = "origen") int origen, @WebParam(name = "destino") int destino) {
        if (controlGrafo.getGrafoND().verificarOrigenVerticeWs(origen)) {
            if (controlGrafo.getGrafoND().verificarDestinoVerticeWs(destino)) {
                for (Arista ar : controlGrafo.getGrafoND().getAristas()) {
                    if ((ar.getOrigen() == origen && ar.getDestino() == destino) || (ar.getDestino() == origen && ar.getOrigen() == destino)) {
                        controlGrafo.getGrafoND().getAristas().remove(ar);
                        controlGrafo.pintarGrafo(controlGrafo.getGrafoND());
                        return "Arista eliminada";
                    }
                }
            } else {
                return "Destino NO existe";
            }
        } else {
            return "Origen NO existe";
        }
        return "No existe Arista";
    }

    /**
     * Metodo que edita el peso de una arista indicando su origen y su destino,
     * validando su existencia edita su peso
     *
     * @author Santiago Betancur
     * @Version V.8
     * @param origen
     * @param destino
     * @param pesoNuevo
     * @return metodo que devuelve un mensaje de indicando si el peso fue
     * editado si no encontro el destino , si no encontro el origen, si la
     * arista no existe.
     */
    @WebMethod(operationName = "editarPesoArista")
    public String editarPesoArista(@WebParam(name = "origen") int origen, @WebParam(name = "destino") int destino, @WebParam(name = "pesoNuevo") int pesoNuevo) {
        if (controlGrafo.getGrafoND().verificarOrigenVerticeWs(origen)) {
            if (controlGrafo.getGrafoND().verificarDestinoVerticeWs(destino)) {
                for (int i = 0; i < controlGrafo.getGrafoND().getAristas().size(); i++) {
                    if ((controlGrafo.getGrafoND().getAristas().get(i).getOrigen() == origen && controlGrafo.getGrafoND().getAristas().get(i).getDestino() == destino)
                            || (controlGrafo.getGrafoND().getAristas().get(i).getDestino() == origen && controlGrafo.getGrafoND().getAristas().get(i).getOrigen() == destino)) {
                        controlGrafo.getGrafoND().getAristas().get(i).setPeso(pesoNuevo);
                        controlGrafo.pintarGrafo(controlGrafo.getGrafoND());
                        return "Peso editado";
                    }
                }
            } else {
                return "Destino NO existe";
            }
        } else {
            return "Origen NO existe";
        }
        return "No existe Arista";
    }

    @WebMethod(operationName = "listarAristas")
    public List<Arista> listarAristas() {
        return controlGrafo.getGrafoND().getAristas();
    }

    //Final de metodos de aristas
    //Buscar por codigo
    /**
     * Metodo que encuentra la ruta mas corta en el web service
     *
     * @author Santiago Betancur
     * @Version V.8
     * @param inicio
     * @param destino
     * @return metodo que retorna una lista de vertices la cual es el la ruta
     * mas corta encontrada, este lo devuelve en el web service
     */
    @WebMethod(operationName = "obtenerRutaMasCortaXCodigo")
    public List<Vertice> obtenerRutaMasCortaXCodigo(@WebParam(name = "inicio") int inicio, @WebParam(name = "destino") int destino) {
        if (destino != inicio) {
            if (!controlGrafo.getGrafoND().getAristas().isEmpty()) {
                if (controlGrafo.verificarNoConexo()) {
                    Dijkstra dijstra = new Dijkstra(controlGrafo.getGrafoND(), controlGrafo.getGrafoND().obtenerVerticexCodigo(inicio), controlGrafo.getGrafoND().obtenerVerticexCodigo(destino));
                    controlGrafo.setRutaCorta(dijstra.calcularRutaMasCorta());
                    controlGrafo.pintarGrafo(controlGrafo.getGrafoND());
                    controlGrafo.pintarRutaCorta();
                    controlGrafo.setActivarPanel(false);
                }
            }
        }
        return controlGrafo.getRutaCorta();
    }
}
