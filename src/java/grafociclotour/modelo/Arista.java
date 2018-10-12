/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafociclotour.modelo;

import java.io.Serializable;

/**
 * @author Carlos loaiza
 * @author Santiago Betancur
 * @version V.8
 */
public class Arista implements Serializable {

    /**
     * Atributes que tomas origen destino y peso
     */
    private int origen;
    private int destino;
    private int peso;

    public Arista() {
    }

    public Arista(int origen, int destino, int peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    public int getOrigen() {
        return origen;
    }

    public void setOrigen(int origen) {
        this.origen = origen;
    }

    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "Origen: " + origen + " Destino: " + destino + " Peso: " + peso;
    }

}
