/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineales;

/**
 *
 * @author Kissner Davor FAI-3185
 */
public class Pila {
    private Nodo tope;
    
    /* Metodo constructor de pila */
    public Pila(){
        this.tope = null;
    }
    
    public boolean apilar(Object nuevoElem){
        /* Crea un nuevo nodo y lo enlaza con el tope */
        Nodo nuevo = new Nodo(nuevoElem, this.tope);
        this.tope = nuevo;
        return true;
        /* retorna siempre true */
    }
    public boolean desapilar(){
        /* Quita el tope de la pila */
        boolean exito;
        if (this.tope != null){
            this.tope = this.tope.getEnlace();
            /* el nodo enlazado al tope pasa a ser el nuevo tope */
            exito = true;
        }
        else{
            /* ERROR: PILA VACIA */
            exito = false;
        }
        return exito;
    }
    public Object obtenerTope(){
        /* Obtiene el elemento del tope de la pila y lo retorna */
        Object elem;
        if (this.tope == null){
            elem = null; 
            /* si la pila esta vacia retorna null */
        }
        else{
            elem = this.tope.getElem();
        }
        return elem;
    }
    public boolean esVacia(){
        /* retorna true si la pila esta vacia y false si no lo esta */    
        return this.tope == null;
    }
    public void vaciar(){
        this.tope = null;
    }
    @Override
    public Pila clone(){
        /* crea una copia de la pila y la retorna */
        Pila pilaClon = new Pila();
        /* llamado al modulo cloneAux */
        cloneAux(pilaClon, this.tope);
        return pilaClon;
    }
    private void cloneAux(Pila clon, Nodo aux){
        /* Modulo recursivo para obtener el nodo raiz */
        if (aux != null){
            cloneAux(clon, aux.getEnlace());
            clon.tope = new Nodo(aux.getElem(), clon.tope);
            /* crea un nodo y lo enlaza con el tope */
        }
    }
    @Override
    public String toString(){
        /* Retorna en forma de String los elementos de la pila */
        String s = "";
        if (this.tope == null){
            /* si la pila esta vacia devuelve este mensaje */
            s = "Pila Vacia";           
        }
        else{
            /* llamado al modulo llamadaString */
            s += "[" + llamadaString(this.tope)+ "]";                                  
        }
        return s;
    }
    
    private String llamadaString(Nodo top){
        /* forma  la cadena con los elementos de la pila */
        String str = "";
        if (top.getEnlace() != null){
            /* llamado recursivo que junta en un String los elementos de la pila */
            str += llamadaString(top.getEnlace())+ ","+top.getElem().toString();
        }else{
            /* Condicion para que no ponga la coma al final */
            str += top.getElem().toString();
        }
        return str;
    }
    public Lista pilaALista() {
        Lista lista = new Lista();
        Nodo index = this.tope;
        if (this.tope != null) {
            while (index != null) {
                lista.insertar(index.getElem(), 1);
                index = index.getEnlace();
            }
        }
        return lista;
    }
   
}
    

