/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafociclotour.controlador;

import grafociclotour.excepciones.GrafoExcepcion;
import grafociclotour.modelo.Arista;
import grafociclotour.modelo.Municipio;
import grafociclotour.modelo.GrafoAbstract;
import grafociclotour.modelo.GrafoNoDirigido;
import grafociclotour.modelo.Vertice;
import grafociclotour.utilidad.JsfUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.diagram.ConnectEvent;
import org.primefaces.event.diagram.ConnectionChangeEvent;
import org.primefaces.event.diagram.DisconnectEvent;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.endpoint.RectangleEndPoint;
import org.primefaces.model.diagram.overlay.LabelOverlay;

/**
 *
 * @author Santiago Betancur
 */
@Named(value = "controladorGrafo")
@SessionScoped
public class ControladorGrafo implements Serializable {

    //Atributos
    private GrafoNoDirigido grafoND;
    private DefaultDiagramModel model;
    private DefaultDiagramModel mode2;
    private Municipio municipio = new Municipio();
    private boolean suspendEvent;
    private List<Vertice> rutaCorta;
    private int codigoInicio = 0;
    private int codigoFinal = 0;

    //Controlador
    public ControladorGrafo() {
    }

    //Getter and Setter
    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public DefaultDiagramModel getMode2() {
        return mode2;
    }

    public GrafoNoDirigido getGrafoND() {
        return grafoND;
    }

    public void setGrafoND(GrafoNoDirigido grafoND) {
        this.grafoND = grafoND;
    }

    public DefaultDiagramModel getModel() {
        return model;
    }

    public void setModel(DefaultDiagramModel model) {
        this.model = model;
    }

    public List<Vertice> getRutaCorta() {
        return rutaCorta;
    }

    public void setRutaCorta(List<Vertice> rutaCorta) {
        this.rutaCorta = rutaCorta;
    }

    public int getCodigoInicio() {
        return codigoInicio;
    }

    public void setCodigoInicio(int codigoInicio) {
        this.codigoInicio = codigoInicio;
    }

    public int getCodigoFinal() {
        return codigoFinal;
    }

    public void setCodigoFinal(int codigoFinal) {
        this.codigoFinal = codigoFinal;
    }

    //PosConstuct
    @PostConstruct
    public void inicializar() {
        grafoND = new GrafoNoDirigido();

        grafoND.adicionarVertice(new Vertice(grafoND.getVertices().size() + 1,
                new Municipio("Manizales", 4, 4)));
        grafoND.adicionarVertice(new Vertice(grafoND.getVertices().size() + 1,
                new Municipio("Neira", 10, 10)));
        grafoND.adicionarVertice(new Vertice(grafoND.getVertices().size() + 1,
                new Municipio("Chinchiná", 20, 10)));
        grafoND.adicionarVertice(new Vertice(grafoND.getVertices().size() + 1,
                new Municipio("Palestina", 30, 4)));

//        grafoND.adicionarVertice(new Vertice(grafoND.getVertices().size() + 1,
//                new Municipio("O", 500000, 140000, 4, 6, 120)));
//        grafoND.adicionarVertice(new Vertice(grafoND.getVertices().size() + 1,
//                new Municipio("A", 90000, 70000, 20, 2, 150)));
//        grafoND.adicionarVertice(new Vertice(grafoND.getVertices().size() + 1,
//                new Municipio("B", 110000, 85000, 30, 2, 160)));
//        grafoND.adicionarVertice(new Vertice(grafoND.getVertices().size() + 1,
//                new Municipio("C", 190000, 170000, 22, 8, 120)));
//        grafoND.adicionarVertice(new Vertice(grafoND.getVertices().size() + 1,
//                new Municipio("D", 190000, 170000, 50, 5, 140)));
//        grafoND.adicionarVertice(new Vertice(grafoND.getVertices().size() + 1,
//                new Municipio("F", 190000, 170000, 42, 13, 180)));
//        grafoND.adicionarVertice(new Vertice(grafoND.getVertices().size() + 1,
//                new Municipio("E", 190000, 170000, 16, 16, 190)));
//        grafoND.adicionarVertice(new Vertice(grafoND.getVertices().size() + 1,
//                new Municipio("T", 190000, 170000, 38, 20, 200)));
//
//        //llenado de aristas
//        grafoND.getAristas().add(new Arista(1, 2, 3));
//        grafoND.getAristas().add(new Arista(1, 3, 2));
//        grafoND.getAristas().add(new Arista(2, 4, 1));
//        grafoND.getAristas().add(new Arista(2, 5, 4));
//        grafoND.getAristas().add(new Arista(3, 4, 3));
//        grafoND.getAristas().add(new Arista(3, 7, 4));
//        grafoND.getAristas().add(new Arista(4, 6, 2));
//        grafoND.getAristas().add(new Arista(4, 7, 2));
//        grafoND.getAristas().add(new Arista(5, 8, 6));
//        grafoND.getAristas().add(new Arista(6, 7, 3));
//        grafoND.getAristas().add(new Arista(6, 8, 4));
//        grafoND.getAristas().add(new Arista(7, 8, 5));
//
        pintarGrafo(grafoND);
//        listados();
        //Dijkstra dijstra = new Dijkstra(grafoND, grafoND.getVertices().get(0), grafoND.getVertices().get(6));
        //dijstra.calcularRutaMasCorta();
    }

    private void pintarGrafo(GrafoAbstract grafo) {
        //int x = 4;
        //int y = 4;
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);
        StraightConnector connector = new StraightConnector();
        // model.getDefaultConnectionOverlays().add(new ArrowOverlay(20, 20, 1, 1));
        for (Vertice vertice : grafo.getVertices()) {

            //////////////////////////
            connector.setPaintStyle("{strokeStyle:'yellow', lineWidth:3}"); //Color de lineas
//            connector.setHoverPaintStyle("{strokeStyle:'blue'}"); //Color detras de linea
            /////////////////////////
//            if (rutaCorta != null) {
//                for (Vertice rutaCorta1 : rutaCorta) {
//                    if (vertice.getCodigo() != rutaCorta1.getCodigo()) {
//                        connector.setPaintStyle("{strokeStyle:'blue', lineWidth:3}"); //Color de lineas
//                    } else {
//                        connector.setPaintStyle("{strokeStyle:'red', lineWidth:3}"); //Color de lineas
//                        break;
//                    }
//                }
//            } else {
//                connector.setPaintStyle("{strokeStyle:'yellow', lineWidth:3}"); //Color de lineas
//            }

            model.setDefaultConnector(connector);
            Element element = new Element(vertice);
            element.setX(String.valueOf(vertice.getDato().getPosx()) + "em");
            element.setY(String.valueOf(vertice.getDato().getPosy()) + "em");
            element.setId(String.valueOf(vertice.getCodigo()));
            //x = x + 10;
            EndPoint endPointSource = createRectangleEndPoint(EndPointAnchor.BOTTOM);
            endPointSource.setSource(true);
            //endPointSource.setTarget(true);
            element.addEndPoint(endPointSource);

            EndPoint endPoint = createDotEndPoint(EndPointAnchor.TOP);
            endPoint.setTarget(true);
            element.addEndPoint(endPoint);
            element.setDraggable(false);
            model.addElement(element);
        }
        //Pintar aristas
        for (Arista ar : grafoND.getAristas()) {
            //Encuentro origen
            for (Element el : model.getElements()) {
                if (el.getId().compareTo(String.valueOf(ar.getOrigen())) == 0) {
                    for (Element elDes : model.getElements()) {
                        if (elDes.getId().compareTo(String.valueOf(ar.getDestino())) == 0) {
                            ///
                            if (rutaCorta != null) {
                                for (Vertice rutaCorta1 : rutaCorta) {
                                    if (el.getId().compareTo(String.valueOf(rutaCorta1.getCodigo())) == 0) {
                                        for (Vertice rutaCorta2 : rutaCorta) {
                                            if (elDes.getId().compareTo(String.valueOf(rutaCorta2.getCodigo())) == 0) {
                                                Connection conn = new Connection(el.getEndPoints().get(0), elDes.getEndPoints().get(1));
                                                conn.getOverlays().add(new LabelOverlay(String.valueOf(ar.getPeso()), "flow-label", 0.5));
                                                connector.setPaintStyle("{strokeStyle:'red', lineWidth:3}");
                                                conn.setConnector(connector);
                                                model.connect(conn);
                                                break;
                                            }
                                        }
                                    }
                                }
                            } else {
                                Connection conn = new Connection(el.getEndPoints().get(0), elDes.getEndPoints().get(1));
                                conn.getOverlays().add(new LabelOverlay(String.valueOf(ar.getPeso()), "flow-label", 0.5));
                                connector.setPaintStyle("{strokeStyle:'yellow', lineWidth:3}");
                                conn.setConnector(connector);
                                model.connect(conn);
                                break;
                            }
                            ///
                            ///
                        }
                    }
                }
            }
        }
        listados();
    }

    private void pintarRutaCorta() {
        //int x = 4;
        //int y = 4;
        mode2 = new DefaultDiagramModel();
        mode2.setMaxConnections(-1);

        // model.getDefaultConnectionOverlays().add(new ArrowOverlay(20, 20, 1, 1));
        StraightConnector connector = new StraightConnector();
        connector.setPaintStyle("{strokeStyle:'red', lineWidth:3}"); //Color de lineas
        connector.setHoverPaintStyle("{strokeStyle:'red'}"); //Color detras de linea

        mode2.setDefaultConnector(connector);

        for (Vertice vert : rutaCorta) {
            Element element = new Element(vert);

            element.setX(String.valueOf(vert.getDato().getPosx()) + "em");
            element.setY(String.valueOf(vert.getDato().getPosy()) + "em");
            element.setId(String.valueOf(vert.getCodigo()));
            //x = x + 10;

            EndPoint endPointSource = createRectangleEndPointDeRutaCorta(EndPointAnchor.BOTTOM);
            endPointSource.setSource(true);
            //endPointSource.setTarget(true);
            element.addEndPoint(endPointSource);

            EndPoint endPoint = createDotEndPointDeRutaCorta(EndPointAnchor.TOP);
            endPoint.setTarget(true);
            element.addEndPoint(endPoint);
            element.setDraggable(false);
            mode2.addElement(element);
        }

        //Pintar aristas
        for (Arista ar : grafoND.getAristas()) {
            //Encuentro origen
            for (Element el : mode2.getElements()) {
                if (el.getId().compareTo(String.valueOf(ar.getOrigen())) == 0) {
                    for (Element elDes : mode2.getElements()) {
                        if (elDes.getId().compareTo(String.valueOf(ar.getDestino())) == 0) {
                            Connection conn = new Connection(el.getEndPoints().get(0), elDes.getEndPoints().get(1));
                            conn.getOverlays().add(new LabelOverlay(String.valueOf(ar.getPeso()), "flow-label", 0.5));
                            mode2.connect(conn);
                            break;
                        }
                    }
                }
            }
        }
        listados();
    }

    public void adicionarMunicipio() {
        grafoND.adicionarVertice(new Vertice(grafoND.getVertices().size() + 1, municipio));
        listados();
        JsfUtil.addSuccessMessage("Municipio Adicionada");
        municipio = new Municipio();
        pintarGrafo(grafoND);
    }

    public void limpiarMunicipio() {
        listados();
        municipio = new Municipio();

    }

    private EndPoint createRectangleEndPoint(EndPointAnchor anchor) {
        RectangleEndPoint endPoint = new RectangleEndPoint(anchor);
        endPoint.setScope("municipio");
        endPoint.setSource(true);
        endPoint.setStyle("{fillStyle:'#404a4e'}");
        endPoint.setHoverStyle("{fillStyle:'#404a4e'}");
        listados();
        return endPoint;
    }

    private EndPoint createRectangleEndPointDeRutaCorta(EndPointAnchor anchor) {
        RectangleEndPoint endPoint = new RectangleEndPoint(anchor);
        endPoint.setScope("municipio");
        endPoint.setSource(true);
        endPoint.setStyle("{fillStyle:'red'}");
        endPoint.setHoverStyle("{fillStyle:'red'}");
        listados();
        return endPoint;
    }

    public void onConnect(ConnectEvent event) {
        if (!suspendEvent) {
            int origen = Integer.parseInt(event.getSourceElement().getId());
            int destino = Integer.parseInt(event.getTargetElement().getId());
            FacesMessage msg = null;
            try {
                grafoND.verificarArista(origen, destino);
                grafoND.adicionarArista(new Arista(origen, destino, 1));
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Conectado",
                        "Desde " + event.getSourceElement().getData() + " hacia " + event.getTargetElement().getData());

            } catch (GrafoExcepcion ex) {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "");

            }
            pintarGrafo(grafoND);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            PrimeFaces.current().ajax().update("frmGrafo");
            PrimeFaces.current().ajax().update("frmMunicipio");
        } else {
            suspendEvent = false;
        }
        listados();
    }

    public void onDisconnect(DisconnectEvent event) {

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Desconectado",
                "Desde " + event.getSourceElement().getData() + " hacia " + event.getTargetElement().getData());

        int origen = Integer.parseInt(event.getSourceElement().getId());
        int destino = Integer.parseInt(event.getTargetElement().getId());
        grafoND.removerArista(origen, destino);
        FacesContext.getCurrentInstance().addMessage(null, msg);

        PrimeFaces.current().ajax().update("frmGrafo");
        PrimeFaces.current().ajax().update("frmMunicipio");
        listados();
    }

    public void onConnectionChange(ConnectionChangeEvent event) {
        int origenAnt = Integer.parseInt(event.getOriginalSourceElement().getId());
        int destinoAnt = Integer.parseInt(event.getOriginalTargetElement().getId());

        int origen = Integer.parseInt(event.getNewSourceElement().getId());
        int destino = Integer.parseInt(event.getNewTargetElement().getId());
        FacesMessage msg = null;
        try {
            grafoND.removerArista(origenAnt, destinoAnt);
            grafoND.verificarArista(origen, destino);
            grafoND.adicionarArista(new Arista(origen, destino, 1));
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Connección Modificada",
                    "Origen inicial: " + event.getOriginalSourceElement().getData()
                    + ", Nuevo Origen: " + event.getNewSourceElement().getData()
                    + ",Destino inicial: " + event.getOriginalTargetElement().getData()
                    + ", Nuevo Destino: " + event.getNewTargetElement().getData());

        } catch (GrafoExcepcion ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "");
            pintarGrafo(grafoND);
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        PrimeFaces.current().ajax().update("frmGrafo");
        PrimeFaces.current().ajax().update("frmMunicipio");
        suspendEvent = true;
        listados();
    }

    private EndPoint createDotEndPoint(EndPointAnchor anchor) {
        DotEndPoint endPoint = new DotEndPoint(anchor);
        endPoint.setScope("municipio");
        endPoint.setTarget(true);
        endPoint.setStyle("{fillStyle:'#98AFC7'}");
        endPoint.setHoverStyle("{fillStyle:'#5C738B'}");
        listados();
        return endPoint;
    }

    private EndPoint createDotEndPointDeRutaCorta(EndPointAnchor anchor) {
        DotEndPoint endPoint = new DotEndPoint(anchor);
        endPoint.setScope("municipio");
        endPoint.setTarget(true);
        endPoint.setStyle("{fillStyle:'red'}");
        endPoint.setHoverStyle("{fillStyle:'red'}");
        listados();
        return endPoint;
    }

    public void onRowEdit(RowEditEvent event) {
        Arista ar = ((Arista) event.getObject());
        FacesMessage msg = new FacesMessage("Arista Modificada", ar.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        pintarGrafo(grafoND);
        PrimeFaces.current().ajax().update("frmGrafo");
        listados();
    }

    public void onRowCancel(RowEditEvent event) {
        Arista ar = ((Arista) event.getObject());
        FacesMessage msg = new FacesMessage("Edición  Cancelada", ar.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        PrimeFaces.current().ajax().update("frmGrafo");
        listados();
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        PrimeFaces.current().ajax().update("frmGrafo");
        listados();
    }

    boolean activarPanel = true;

    public boolean isActivarPanel() {
        return activarPanel;
    }

    public void setActivarPanel(boolean activarPanel) {
        this.activarPanel = activarPanel;
    }

    public void calcularRutaCorta() {
        activarPanel = false;
        if (codigoFinal != codigoInicio) {
            Dijkstra dijstra = new Dijkstra(grafoND, grafoND.obtenerVerticexCodigo(codigoInicio), grafoND.obtenerVerticexCodigo(codigoFinal));
            rutaCorta = dijstra.calcularRutaMasCorta();
            pintarRutaCorta();
            pintarGrafo(grafoND);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Origen y Destino no pueden ser iguales", "Origen y Destino no pueden ser iguales");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            PrimeFaces.current().ajax().update("grwErrores");
        }
        listados();
    }

    //nuevo
    private List verticesConSuNivel;

    public List getVerticesConSuNivel() {
        return verticesConSuNivel;
    }

    public void setVerticesConSuNivel(List verticesConSuNivel) {
        this.verticesConSuNivel = verticesConSuNivel;
    }

    public void listados() {
        verticesConSuNivel = listadosX();
    }

    private List listadosX() {
        List<String> nuevas = new ArrayList<>();
        for (int i = 1; i <= grafoND.getVertices().size(); i++) {
            nuevas.add("Vertice: " + i + "   <------------>  " + "Nivel: " + contarAdya(i));
        }
        return nuevas;
    }

    private int contarAdya(int indicador) {
        int con = 0;
        for (Arista arista : grafoND.getAristas()) {
            if (arista.getOrigen() == indicador) {
                con++;
            }
            if (arista.getDestino() == indicador) {
                con++;
            }
        }
        return con;
    }
}
