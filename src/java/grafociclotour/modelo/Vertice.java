/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafociclotour.modelo;

import java.io.Serializable;

/**
 *
 * @author Santiago Betancur
 */
public class Vertice implements Serializable {

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
