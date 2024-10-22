/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conjuntistas;

/**
 *
 * @author Davor Kissner
 */
public class NodoAVLDicc {
    private Comparable clave;
    private NodoAVLDicc izquierdo;
    private NodoAVLDicc derecho;
    private Object dato;
    private int altura;
    
    public NodoAVLDicc(Comparable clave, Object dato, NodoAVLDicc izquierdo, NodoAVLDicc derecho){
        this.clave = clave;
        this.dato = dato;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.altura = 0;
    }        
    public Comparable getClave(){
        return this.clave;
    }
    public Object getDato(){
        return this.dato;
    }
    public NodoAVLDicc getIzquierdo(){
        return this.izquierdo;
    }
    public NodoAVLDicc getDerecho(){
        return this.derecho;
    }
    public int getAltura(){
        return this.altura;
    }
    public void setDato(Object dato){
        this.dato = dato;
    }
    public void setIzquierdo(NodoAVLDicc izquierdo){
        this.izquierdo = izquierdo;
    }
    public void setDerecho(NodoAVLDicc derecho){
        this.derecho = derecho;
    }
    public void setClave(Comparable clave){
        this.clave = clave;
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
