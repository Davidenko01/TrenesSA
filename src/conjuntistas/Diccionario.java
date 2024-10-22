/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conjuntistas;

/**
 *
 * @author Davor Kissner
 */
import lineales.Lista;

public class Diccionario {
    private NodoAVLDicc raiz;
    
    public Diccionario(){
        this.raiz = null;       
    }
    public boolean existeClave(Comparable clave) {
        return existeClaveAux(this.raiz, clave);
    }

    private boolean existeClaveAux(NodoAVLDicc nodo, Comparable clave) {
        boolean exito = false;
        if (nodo != null) {
            if (clave.compareTo(nodo.getClave()) == 0) {
                //Elemento encontrado
                exito = true;
            } else if (clave.compareTo(nodo.getClave()) < 0) {
                //Elem es menor al nodo, busco a su izquierda
                exito = existeClaveAux(nodo.getIzquierdo(), clave);
            } else {
                //El elem es mayor a nodo.getElem, busco a su derecha
                exito = existeClaveAux(nodo.getDerecho(), clave);
            }
        }
        return exito;
    }
    public Object obtenerDato(Comparable clave) {
        Object retorno = null;
        if (this.raiz != null) {
            retorno = obtenerDatoAux(this.raiz, clave);
        }
        return retorno;
    }
    private Object obtenerDatoAux(NodoAVLDicc n, Comparable clave){
        Object retorno = null;
        if (n != null) {
            //Clave encontrada
            if (n.getClave().equals(clave)){
                retorno = n.getDato(); //Guardo el dato
            } else {
                if (n.getClave().compareTo(clave) > 0) {
                    //Clave es menor al nodo, busco a la izquierda
                    retorno = obtenerDatoAux(n.getIzquierdo(), clave);
                } else {
                    //Clave es mayor al nodo, busco a la derecha
                    retorno = obtenerDatoAux(n.getDerecho(), clave);
                }
            }
        }
        return retorno;
    }

    public void vaciar() {
        //Seteo raiz en null
        this.raiz = null;
    }

    public boolean esVacio() {
        //Si la raiz es nula retorno true sino false
        return (this.raiz == null);

    }  
    public Lista listarClaves() {
        Lista l1 = new Lista();
        listarClavesAux(this.raiz, l1);
        return l1;
    }

    private void listarClavesAux(NodoAVLDicc n, Lista l1) {
        if (n != null) {
            //Inserto las claves a una lista en reocorrido inorden
            listarClavesAux(n.getIzquierdo(), l1);
            l1.insertar(n.getClave(), l1.longitud() + 1);
            listarClavesAux(n.getDerecho(), l1);

        }
    }

    public Lista listarDatos() {
        Lista l1 = new Lista();
        listarDatosAux(this.raiz, l1);
        return l1;
    }

    private void listarDatosAux(NodoAVLDicc n, Lista l1) {
        if (n != null) {
            //Inserto los datos a una lista en recorrido inorden
            listarDatosAux(n.getIzquierdo(), l1);
            l1.insertar(n.getDato(), l1.longitud() + 1);
            listarDatosAux(n.getDerecho(), l1);

        }
    }

    public boolean insertar(Comparable clave, Object elem) {
        boolean exito;
        //CASO ESPECIAL INSERTO LA RAIZ
        if (this.raiz == null) {
            this.raiz = new NodoAVLDicc(clave, elem, null, null);
            exito = true;
        } else {
            exito = insertarAux(this.raiz, clave, elem);
            this.raiz.recalcularAltura();
            balancear(balance(this.raiz),this.raiz, null);
        }
        return exito;
    }

    private boolean insertarAux(NodoAVLDicc nodo, Comparable clave, Object elem) {
        boolean exito = true;
        int comparacion = clave.compareTo(nodo.getClave());
        //SI EXISTE LA CLAVE EN EL DICCIONARIO RETORNO FALSE
        if (comparacion == 0) {
            exito = false;
        //SI LA CLAVE ES MENOR BAJO POR LA IZQUIERDA    
        } else if (comparacion < 0) {
            if (nodo.getIzquierdo() == null) {
                nodo.setIzquierdo(new NodoAVLDicc(clave, elem, null, null));
                exito = true;

            } else {
                exito = insertarAux(nodo.getIzquierdo(), clave, elem);
                balancear(balance(nodo.getIzquierdo()),nodo.getIzquierdo(), nodo);               
            }
            nodo.recalcularAltura();
        //SI ES MAYOR BAJO POR LA DERECHA
        } else if (comparacion > 0) {
            if (nodo.getDerecho() == null) {
                nodo.setDerecho(new NodoAVLDicc(clave, elem, null, null));
                exito = true;
            } else {
                exito = insertarAux(nodo.getDerecho(), clave, elem);
                balancear((balance(nodo.getDerecho())),nodo.getDerecho(), nodo);
            }
            nodo.recalcularAltura();

        }

        return exito;
    }

    private int balance(NodoAVLDicc nodo) {
        int balance = 0, izq = -1, der = -1;
        
        if (nodo != null) {
            if (nodo.getIzquierdo() != null) {
                izq = nodo.getIzquierdo().getAltura();
            }
            if (nodo.getDerecho() != null) {
                der = nodo.getDerecho().getAltura();
            }

            balance = izq - der;
        }
        return balance;

    }

    private void balancear(int balanceNodo, NodoAVLDicc nodo, NodoAVLDicc padre) {
        int balanceHijo;
        switch (balanceNodo) {
            case 2:
                balanceHijo = balance(nodo.getIzquierdo());
                if (balanceHijo == 0 || balanceHijo == 1) {
                    if (padre == null) {
                        this.raiz = rotarDerecha(nodo);
                    } else {
                        if (nodo.getClave().compareTo(padre.getClave()) < 0) {
                            padre.setIzquierdo(rotarDerecha(nodo));
                        } else {
                            padre.setDerecho(rotarDerecha(nodo));
                        }
                    }
                } else if (padre == null) {
                    this.raiz = rotacionIzquierdaDerecha(nodo);
                } else {
                    if (nodo.getClave().compareTo(padre.getClave()) < 0) {
                        padre.setIzquierdo(rotacionIzquierdaDerecha(nodo));
                    } else {
                        padre.setDerecho(rotacionIzquierdaDerecha(nodo));
                    }
                }
                break;
            case -2:
                balanceHijo = balance(nodo.getDerecho());
                if (balanceHijo == 0 || balanceHijo == -1) {
                    if (padre == null) {
                        this.raiz = rotarIzquierda(nodo);
                    } else {
                        if (padre.getClave().compareTo(nodo.getClave()) < 0) {
                            padre.setDerecho(rotarIzquierda(nodo));
                        } else {
                            padre.setIzquierdo(rotarIzquierda(nodo));
                        }
                    }
                } else {
                    if (padre == null) {
                        this.raiz = rotacionDerechaIzquierda(nodo);
                    } else {
                        if (padre.getClave().compareTo(nodo.getClave()) < 0) {
                            padre.setDerecho(rotacionDerechaIzquierda(nodo));
                        } else {
                            padre.setIzquierdo(rotacionDerechaIzquierda(nodo));
                        }
                    }
                }
                break;
        }
    }

    private NodoAVLDicc rotarIzquierda(NodoAVLDicc nodo) {
        NodoAVLDicc hijo = nodo.getDerecho();
        NodoAVLDicc temp = hijo.getIzquierdo();
        hijo.setIzquierdo(nodo);
        nodo.setDerecho(temp);
        nodo.recalcularAltura();
        hijo.recalcularAltura();
        return hijo;
    }

    private NodoAVLDicc rotarDerecha(NodoAVLDicc nodo) {
        NodoAVLDicc hijo = nodo.getIzquierdo();
        NodoAVLDicc temp = hijo.getDerecho();
        hijo.setDerecho(nodo);
        nodo.setIzquierdo(temp);
        nodo.recalcularAltura();
        hijo.recalcularAltura();
        return hijo;
    }

    private NodoAVLDicc rotacionIzquierdaDerecha(NodoAVLDicc r) {
        //PADRE ESTA CAIDO A LA IZQUIERDA Y EL HIJO CAIDO A LA DERECHA
        r.setIzquierdo(rotarIzquierda(r.getIzquierdo()));
        return rotarDerecha(r);
    }

    private NodoAVLDicc rotacionDerechaIzquierda(NodoAVLDicc r) {
        //PADRE ESTA CAIDO A LA DERECHA Y EL HIJO CAIDO A LA IZQUIERDA
        r.setDerecho(rotarDerecha(r.getDerecho()));
        return rotarIzquierda(r);
    }
    public boolean eliminar(Comparable elem) {
        boolean borrado = false;
        if (this.raiz != null) {
            borrado = auxEliminar(elem, this.raiz, null);

        }
        return borrado;
    }

    private boolean auxEliminar(Comparable clave, NodoAVLDicc nodo, NodoAVLDicc padre) {
        boolean eliminado = false;
        if (nodo != null) {
            int comparacion = clave.compareTo(nodo.getClave());
            if (comparacion < 0) {
                eliminado = auxEliminar(clave, nodo.getIzquierdo(), nodo);
            } else if (comparacion > 0) {
                eliminado = auxEliminar(clave, nodo.getDerecho(), nodo);      
            //SE ENCONTRÓ EL NODO
            } else {
                //CASO NODO A ELIMINAR ES HOJA
                if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                    caso1(clave, padre);
                //CASO TIENE AL MENOS 1 HIJO
                } else if (nodo.getIzquierdo() == null || nodo.getDerecho() == null) {
                    caso2(clave, nodo, padre);
                } else {
                //TIENE 2 HIJOS
                    caso3(nodo, nodo.getDerecho());
                }                           
                eliminado = true;
            }
            if(eliminado){                 
                nodo.recalcularAltura();
                balancear(balance(nodo), nodo, padre);
            }
            
        }
        return eliminado;
    }

    // Caso 1 de eliminar.
    private void caso1(Comparable clave, NodoAVLDicc padre) {
        if (padre == null) {
            this.raiz = null;
        } else {
            if (clave.compareTo(padre.getClave()) < 0) {
                padre.setIzquierdo(null);
            } else {
                padre.setDerecho(null);
            }
        }
    }

    // Caso 2 de eliminar.
    private void caso2(Comparable clave, NodoAVLDicc nodo, NodoAVLDicc padre) {
        // BUSCO AL CANDIDATO
        // 1 HIJO SERÁ NULL
        NodoAVLDicc derecho = nodo.getDerecho();
        NodoAVLDicc izquierdo = nodo.getIzquierdo();
        if (padre == null) {
            // CASO CON LA RAIZ
            if (derecho == null) {
                this.raiz = izquierdo;
            } else {
                this.raiz = derecho;
            }
        } else {
            // RECORRO AMBAS RAMAS 
            if (clave.compareTo(padre.getClave()) < 0) {
                if (izquierdo == null) {
                    padre.setIzquierdo(derecho);
                } else {
                    padre.setIzquierdo(izquierdo);
                }
            } else {
                if (izquierdo == null) {
                    padre.setDerecho(derecho);
                } else {
                    padre.setDerecho(izquierdo);
                }
            }
        }
    }
    //CASO 3 EL NODO A ELIMINAR TIENE 2 HIJOS
    private void caso3(NodoAVLDicc nodo, NodoAVLDicc HD) {
        if (HD.getIzquierdo() != null){
            NodoAVLDicc candidato = nodoCandidato(HD.getIzquierdo(), HD);
            intercambiar(nodo, candidato);    
        } else {
            intercambiar(nodo, HD);  
            nodo.setDerecho(HD.getDerecho());
        }    
    }
    //CANDIDATO ES EL MENOR DEL SUBARBOL IZQUIERDO DEL NODO DERECHO DEL NODO A ELIMINAR
    private NodoAVLDicc nodoCandidato(NodoAVLDicc nodo, NodoAVLDicc padre){
        NodoAVLDicc candidato;
        if (nodo.getIzquierdo() != null){
            candidato = nodoCandidato(nodo.getIzquierdo(), nodo);
        } else {
            candidato = nodo;
            padre.setIzquierdo(nodo.getDerecho());          
        }
        nodo.recalcularAltura();
        balancear(balance(nodo), nodo, padre);
        return candidato;
    }
    private void intercambiar(NodoAVLDicc nodo, NodoAVLDicc nodoCambio){
        nodo.setClave(nodoCambio.getClave());
        nodo.setDato(nodoCambio.getDato());
    }
    @Override
    public String toString(){
        return llamadoString(this.raiz);
    }
    private String llamadoString(NodoAVLDicc n) {
        String str = "";
        if (n != null) {
            str += "NODO: " + n.getClave().toString() + "."+" Altura: "+n.getAltura()+".";
            if (n.getIzquierdo() != null) {
                str += " H.I: " + n.getIzquierdo().getClave().toString() + ".";
            }
            else{
                str += " H.I: -.";
            }
            if (n.getDerecho() != null) {
                str += " H.D: " + n.getDerecho().getClave().toString() + ".\n";
            } else {
                str += " H.D: - \n";
            }
            if (n.getIzquierdo() != null) {
                str += llamadoString(n.getIzquierdo());
            }
            if (n.getDerecho() != null) {
                str += llamadoString(n.getDerecho());
            }
        }       
        return str;
    }

    
}
