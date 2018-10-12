/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafociclotour.modelo;

import java.io.Serializable;

/**
 *
 * @author Carlos Loaiza
 * @author Santiago Betancur
 * @version V.8
 */
public class Vertice implements Serializable {

    /**
     * Atributo de tipo codigo que va dando de manera ascendente segun su oreden
     * de creacion un entero Atributo dato que tiene una realación de tipo
     * composicion con municipio
     */
    private int codigo;
    private Municipio dato;

    public Vertice() {
    }

    public Vertice(int codigo, Municipio dato) {
        this.codigo = codigo;
        this.dato = dato;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Municipio getDato() {
        return dato;
    }

    public void setDato(Municipio dato) {
        this.dato = dato;
    }

    @Override
    public String toString() {
        return codigo + " " + dato;
    }

}
