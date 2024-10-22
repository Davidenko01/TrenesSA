/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas;

/**
 *
 * @author Usuario
 */
public class NodoAVL {
    private Comparable elem;
    private NodoAVL izquierdo;
    private NodoAVL derecho;
    private int altura;
    
    public NodoAVL(Comparable elem){
        this.elem = elem;
        this.izquierdo = null;
        this.derecho = null;
        this.altura = 0;
    }
    public NodoAVL(Comparable elem, NodoAVL izquierdo, NodoAVL derecho){
        this.elem = elem;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.altura = 0;
    }        
    public Comparable getElem(){
        return this.elem;
    }
    public NodoAVL getIzquierdo(){
        return this.izquierdo;
    }
    public NodoAVL getDerecho(){
        return this.derecho;
    }
    public int getAltura(){
        return this.altura;
    }
    public void setElem(Comparable elem){
        this.elem = elem;
    }
    public void setIzquierdo(NodoAVL izquierdo){
        this.izquierdo = izquierdo;
    }
    public void setDerecho(NodoAVL derecho){
        this.derecho = derecho;
    }
     public void recalcularAltura() {
        int izq = -1, der = -1;
        if (this.izquierdo != null) {
            izq = this.izquierdo.altura;
        }
        if (this.derecho != null) {
            der = this.derecho.altura;
        }
        this.altura = (Math.max(izq, der)) + 1;
    }  
}
