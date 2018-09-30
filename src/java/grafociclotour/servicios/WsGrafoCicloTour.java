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
import java.util.ArrayList;
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
    @WebMethod(operationName = "listarVertices")
    public List<Vertice> listarVertices() {
        return controlGrafo.getGrafoND().getVertices();
    }

    @WebMethod(operationName = "crearVertice")
    public String crearVertice(@WebParam(name = "municipio") Municipio municipio) {
        controlGrafo.getGrafoND().adicionarVertice(
                new Vertice(controlGrafo.getGrafoND().getVertices().size() + 1,
                        municipio));
        controlGrafo.pintarGrafo(controlGrafo.getGrafoND());
        return "Vertice creado";
    }

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
