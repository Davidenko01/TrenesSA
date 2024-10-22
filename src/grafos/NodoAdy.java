/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafos;

/**
 *
 * @author Davor Kissner
 */
public class NodoAdy {
    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private int etiqueta;
    
    public NodoAdy(NodoVert v , NodoAdy n ,int e){
        this.vertice = v;
        this.sigAdyacente = n;
        this.etiqueta = e;
    }
    public NodoVert getVertice(){
        return this.vertice;
    }
    public int getEtiqueta(){
        return this.etiqueta;
    }
    public void setEtiqueta(int etiqueta){
        this.etiqueta= etiqueta;
    }
    public void setVertice(NodoVert v){
        this.vertice = v;
    }
    public NodoAdy getSigAdyacente(){
        return this.sigAdyacente;
    }
    public void setSigAdyacente(NodoAdy n){
        this.sigAdyacente = n;
    }
}

