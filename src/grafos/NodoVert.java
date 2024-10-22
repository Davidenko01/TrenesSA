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
public class NodoVert {
    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;
    
    public NodoVert(Object elem1,NodoVert sigVertice1,NodoAdy  primerAdy1){
        this.elem = elem1;
        this.sigVertice = sigVertice1;
        this.primerAdy = primerAdy1;
    }
    public NodoVert(Object elem1,NodoVert sigVertice1){
        this.elem = elem1;
        this.sigVertice = sigVertice1;
    }
    public Object getElem(){
        return this.elem;
    }
    public void setElem(Object elem1){
        this.elem = elem1;
    }
    public NodoVert getSigVertice(){
        return this.sigVertice;
    }
    public  void setSigVertice(NodoVert s){
        this.sigVertice = s;
    }
    public NodoAdy getPrimerAdy(){
        return this.primerAdy;
    }
    public void setPrimerAdy(NodoAdy n){
        this.primerAdy = n;
    }
}
