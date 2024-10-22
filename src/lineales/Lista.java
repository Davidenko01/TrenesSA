/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineales;

/**
 *
 * @author Usuario
 */
public class Lista {
    //atributos
    private Nodo cabecera;
    private int longitud;
    
    //constructor
    public Lista(){
        this.cabecera = null;
        this.longitud = 0;
    }
    
    public boolean insertar(Object nuevoElem, int pos) {
        // Inserta el elemento nuevo en la posicion pos
        // Detecta y reporta error posicion invalida
        boolean exito = true;

        if (pos < 1 || pos > this.longitud() + 1) {
            exito = false;
        } else {
            if (pos == 1) { // Crea un nuevo nodo y se enlaza a la cabecera
                this.cabecera = new Nodo(nuevoElem, this.cabecera);
            } else { // Avanza hasta el elemento en posicion pos-1
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                // Crea el nodo y lo enlaza
                Nodo nuevo = new Nodo(nuevoElem, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
            this.longitud++;
        }
        // Nueca hay error de lista llena, entonces devuelve true
        return exito;
    }
    public boolean eliminar(int pos) {
        // Elimina un elemento en la posicion deseada por el usuario
        // Esta posicion debe ser valida, de lo contrario el metodo retornara false
        boolean exito = true;

        //Condicion invalida
        if (pos < 1 || pos > longitud()) {
            exito = false;
        } else {
            //Caso especial 
            if (pos == 1) {
                this.cabecera = this.cabecera.getEnlace(); 

            } else { // Avanza hasta la posicion pos-1
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                //Enlaza el nodo aux con la posicion siguiente a pos
                //El elemento de pos pierde en enlace por lo que queda liberado
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
            this.longitud--;

        }
        return exito;
    }
    public Object recuperar(int pos){
        //devuelve el elemento en una posicion determinada
        Object elem;
        int i = 1;
        //si la posicion no es valida retorna null
        if (pos < 1 || pos > this.longitud()){
            elem = null;
        }
        else{
            //sino, recorre la lista hasta la posicion deseada
            Nodo aux = this.cabecera;          
            while (i < pos){
                aux = aux.getEnlace();
                i++;
            }
            elem = aux.getElem();
        }
        //retorna el elemento en dicha posicion
        return elem;
    }
    public int localizar (Object buscado){
        //metodo que devuelve la posicion en la que se encuentra un elemento de la lista
        int pos = -1;
        Nodo aux = this.cabecera;
        boolean control = false;
        int i = 1;
        while( i <= this.longitud() && !control ){
            //cuando lo encontremos cortamos y retornamos al pocicion donde se encontro
            if(aux.getElem().equals(buscado)){
                control = true;
                pos = i;
            }else{
                aux = aux.getEnlace();
                i++;   
            }
        }
        //retornamos -1 si no lo encuentra y n si lo encuentra
        return pos;
    }
    public void vaciar(){
        this.cabecera = null;
        this.longitud = 0;
    }
    @Override
    public Lista clone(){
        //cremos la lista clon
        Lista clon = new Lista();
        if(this.cabecera != null){
            //generamos la caecera
            clon.cabecera = new Nodo(this.cabecera.getElem(), null);
            //llamamos al paso recursivo
            clonePasoRecursivo(this.cabecera, clon.cabecera);
            clon.longitud = this.longitud;
        }

        return clon;
    }
    private void clonePasoRecursivo(Nodo aux, Nodo puntero){
        //mientras el nodo de la lista actual no apunte a null
        if(aux.getEnlace() != null){
            //obtenemos el enlace de este nodo
            aux = aux.getEnlace();
            //cremos un nuevo nodo clon el cual vamos a enlazar con puntero
            Nodo nuevo = new Nodo(aux.getElem(), null);
            puntero.setEnlace(nuevo);
            //hacemos que puntero cambie a su enlace
            puntero = nuevo;
            clonePasoRecursivo(aux, puntero);
        }
    }
    
    public boolean esVacia(){
        //retorna si la cabecera es null o no
        return this.cabecera == null;
    }
    @Override
    public String toString(){
        String retorno;
        //evaluamos que la lista no este vacia
        if(this.cabecera != null){
            retorno = "[";
            Nodo aux = this.cabecera;
            
            while(aux != null){
                //agrega al string el elemento del nodo
                retorno += aux.getElem().toString();
                if(aux.getEnlace() != null){
                    retorno += " - ";
                }
                //obtiene el enlace del nodo
                aux = aux.getEnlace();
            }
            
            retorno += "]";
        }else{
            retorno = "[]";
        }
        return retorno;
    }
    public int longitud(){
        //retorna la longitud de la lista
        return this.longitud;
    }
    public boolean eliminarElem(Object elem){
        boolean eliminado = false;
        int i = this.localizar(elem);
        if (i != -1){
            this.eliminar(i);
            eliminado = true;
        }  
        return eliminado;    
    }
}
   
    

