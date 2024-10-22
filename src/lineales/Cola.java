/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineales;

/**
 *
 * @author Davor Kissner
 */
public class Cola {
    //atributos
    private Nodo frente;
    private Nodo fin;
    
    //constructor
    public Cola(){
        this.fin = null;
        this.frente = null;    
    }
    
    public boolean poner(Object elem){
        //inserta un elemento en la cola retorna siempre true
        boolean exito;
        if (this.esVacia()){ //condicion cola vacia, enlaza fin y frente al nodo ingresado
            Nodo nuevo = new Nodo(elem, null);
            this.fin = nuevo;
            this.frente = nuevo;  
            exito = true;                 
        }
        else{
            //si tiene al menos un elemento
            Nodo nuevo = new Nodo(elem, null); //enlaza el fin con el nuevo nodo
            this.fin.setEnlace(nuevo);
            this.fin = nuevo; //actualiza el fin
            exito = true;
        }
        return exito;      
    }
    public boolean sacar(){
        boolean exito = true;
        if (this.frente == null){ //cola vacia, reporta error
            exito = false;
        }
        else{
            //al menos hay un elemento
            //quita el primer elemento y actualiza frente (y fin si queda vacia)
            this.frente = this.frente.getEnlace();
            if (this.frente == null){
                this.fin = null;
            }
        }
        return exito;
    }
    public Object obtenerFrente(){
        Object elem;
        if (this.esVacia()){ //cola vacia, retorna null
            elem = null;
        }
        else{
            elem = this.frente.getElem(); //obtiene el elemento del frente
        }
        return elem;
    }
    public boolean esVacia(){
        //retorna true si el frente no tiene elemento(cola vacia), sino retorna false
        return this.frente == null;
    }
    public void vaciar(){
        //vacia la cola
        if (!this.esVacia()){ //si no es vacia, hay al menos 1 elemento
            //tanto fin y frente cortan sus enlaces
            this.fin = null;
            this.frente = null;
        }
    }
    @Override
    public Cola clone(){
        //crea un clon de la cola
        Cola clon = new Cola();
        Nodo aux1 = this.frente;
        //setea el frente del clon
        clon.frente = new Nodo(aux1.getElem(),null);
        aux1 = aux1.getEnlace();
        Nodo aux2 = clon.frente;
        //repetitiva que recorre la cola
        while (aux1 != null){
            //enlaza un nuevo nodo en la cola clone con el elemento que posee
            //el nodo en la pila original
            aux2.setEnlace(new Nodo(aux1.getElem(), null));
            aux2 = aux2.getEnlace();
            aux1 = aux1.getEnlace();         
        }
        clon.fin = aux2;
        return clon;
    }
    @Override
    public String toString(){
        /* Retorna en forma de String los elementos de la pila */
        String s = "";
        if (this.frente == null){
            /* si la pila esta vacia devuelve este mensaje */
            s = "[]";           
        }
        else{
            /* llamado al modulo llamadaString */
            s +=  llamadaString(this.frente);                                  
        }
        return s;
    }
    
    private String llamadaString(Nodo top){
        /* forma  la cadena con los elementos de la pila */
        String str = "";
        if (top.getEnlace() != null){
            /* llamado recursivo que junta en un String los elementos de la pila */
            str += top.getElem().toString()+","+llamadaString(top.getEnlace());
        }else{
            /* Condicion para que no ponga la coma al final */
            str += top.getElem().toString();
        }
        return str;
    }
    
}
