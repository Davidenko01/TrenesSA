/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas;
import lineales.Cola;
import lineales.Lista;

/**
 *
 * @author Usuario
 */
public class ArbolAVL {
    private NodoAVL raiz;
    
    public ArbolAVL(){
        this.raiz = null;       
    }
    public boolean pertenece(Comparable elem) {
        return perteneceAux(this.raiz, elem);
    }

    private boolean perteneceAux(NodoAVL nodo, Comparable elem) {
        boolean exito = false;
        if (nodo != null) {
            if (elem.compareTo(nodo.getElem()) == 0) {
                //Elemento encontrado
                exito = true;
            } else if (elem.compareTo(nodo.getElem()) < 0) {
                //Elem es menor al nodo, busco a su izquierda
                exito = perteneceAux(nodo.getIzquierdo(), elem);
            } else {
                //El elem es mayor a nodo.getElem, busco a su derecha
                exito = perteneceAux(nodo.getDerecho(), elem);
            }
        }
        return exito;
    }

    public void vaciar() {
        this.raiz = null;
    }

    public boolean esVacio() {
        return (this.raiz == null);

    }

    public Lista listar() {
        Lista laLista = new Lista();
        if (this.raiz != null) {
            listarAux(this.raiz, laLista);
        }
        return laLista;
    }

    private void listarAux(NodoAVL nodo, Lista lista) {
        if (nodo != null) {
            listarAux(nodo.getDerecho(), lista); //llamo recursivamente por derecha
            lista.insertar(nodo.getElem(), 1); //inserto
            listarAux(nodo.getIzquierdo(), lista); //lamo recursivamente por izquierdo

        }
    }

    public Lista listarRango(int min, int max) {
        Lista laLista = new Lista();
        if (this.raiz != null) {
            listarRangoAux(this.raiz, min, max, laLista);
        }

        return laLista;
    }

    private void listarRangoAux(NodoAVL nodo, int minimo, int maximo, Lista lista) {
        if (nodo != null) {
            Comparable elemento = nodo.getElem();
            if (elemento.compareTo(maximo) < 0) {
                listarRangoAux(nodo.getDerecho(), minimo, maximo, lista);
            }
            if (elemento.compareTo(minimo) >= 0 && elemento.compareTo(maximo) <= 0) {
                lista.insertar(elemento, 1);
            }
            if (elemento.compareTo(minimo) > 0) {
                listarRangoAux(nodo.getIzquierdo(), minimo, maximo, lista);
            }
        }
    }

    public Comparable maximo() {
        Comparable elemento = null;
        NodoAVL nodo = this.raiz;
        // bajada por la derecha
        while (nodo != null) {
            elemento = nodo.getElem();
            nodo = nodo.getDerecho();
        }
        return elemento;
    }

    public Comparable minimo() {
        Comparable elemento = null;
        NodoAVL nodo = this.raiz;
        // bajada por la izquierda
        while (nodo != null) {
            elemento = nodo.getElem();
            nodo = nodo.getIzquierdo();
        }
        return elemento;
    }

    @Override
    public ArbolAVL clone() {
        ArbolAVL nuevo = new ArbolAVL();
        nuevo.raiz = clonarAux(this.raiz);
        return nuevo;
    }

    private NodoAVL clonarAux(NodoAVL aux) {
        NodoAVL hijo = null;
        if (aux != null) {
            hijo = new NodoAVL(aux.getElem(), clonarAux(aux.getDerecho()), clonarAux(aux.getIzquierdo()));
        }
        return hijo;
    }

    public boolean insertar(Comparable elem) {
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoAVL(elem);
        } else {
            exito = insertarAux(this.raiz, elem);
            balancear(balance(this.raiz),this.raiz, null);
            this.raiz.recalcularAltura();
        }
        return exito;
    }

    private boolean insertarAux(NodoAVL nodo, Comparable elem) {
        boolean exito = true;
        int comparacion = elem.compareTo(nodo.getElem());
        if (comparacion == 0) {
            exito = false;
        } else if (comparacion < 0) {
            if (nodo.getIzquierdo() == null) {
                nodo.setIzquierdo(new NodoAVL(elem));
                exito = true;
                nodo.recalcularAltura();

            } else {
                exito = insertarAux(nodo.getIzquierdo(), elem);
                balancear(balance(nodo.getIzquierdo()),nodo.getIzquierdo(), nodo);
                nodo.recalcularAltura();
            }

        } else if (comparacion > 0) {
            if (nodo.getDerecho() == null) {
                nodo.setDerecho(new NodoAVL(elem, null, null));
                exito = true;
                nodo.recalcularAltura();
            } else {
                exito = insertarAux(nodo.getDerecho(), elem);
                balancear(balance(nodo.getDerecho()),nodo.getDerecho(), nodo);
                nodo.recalcularAltura();
            }

        }

        return exito;
    }

    private int balance(NodoAVL nodo) {
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

    private void balancear(int balancePadre, NodoAVL nodo, NodoAVL padre) {
        int balanceHijo;
        switch (balancePadre) {
            case 2:
                balanceHijo = balance(nodo.getIzquierdo());
                if (balanceHijo == 0 || balanceHijo == 1) {
                    if (padre == null) {
                        this.raiz = rotarDerecha(nodo);
                    } else {
                        if (nodo.getElem().compareTo(padre.getElem()) < 0) {
                            padre.setIzquierdo(rotarDerecha(nodo));
                        } else {
                            padre.setDerecho(rotarDerecha(nodo));
                        }
                    }
                } else if (padre == null) {
                    this.raiz = rotacionIzquierdaDerecha(nodo);
                } else {
                    if (nodo.getElem().compareTo(padre.getElem()) < 0) {
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
                        if (padre.getElem().compareTo(nodo.getElem()) < 0) {
                            padre.setDerecho(rotarIzquierda(nodo));
                        } else {
                            padre.setIzquierdo(rotarIzquierda(nodo));
                        }
                    }
                } else {
                    if (padre == null) {
                        this.raiz = rotacionDerechaIzquierda(nodo);
                    } else {
                        if (padre.getElem().compareTo(nodo.getElem()) < 0) {
                            padre.setDerecho(rotacionDerechaIzquierda(nodo));
                        } else {
                            padre.setIzquierdo(rotacionDerechaIzquierda(nodo));
                        }
                    }
                }
                break;
        }
    }

    private NodoAVL rotarIzquierda(NodoAVL nodo) {
        NodoAVL hijo = nodo.getDerecho();
        NodoAVL temp = hijo.getIzquierdo();
        hijo.setIzquierdo(nodo);
        nodo.setDerecho(temp);
        nodo.recalcularAltura();
        hijo.recalcularAltura();
        return hijo;
    }

    private NodoAVL rotarDerecha(NodoAVL nodo) {
        NodoAVL hijo = nodo.getIzquierdo();
        NodoAVL temp = hijo.getDerecho();
        hijo.setDerecho(nodo);
        nodo.setIzquierdo(temp);
        nodo.recalcularAltura();
        hijo.recalcularAltura();
        return hijo;
    }

    private NodoAVL rotacionIzquierdaDerecha(NodoAVL r) {
        //PADRE ESTA CAIDO A LA IZQ Y EL HIJO CAIDO A LA DER
        r.setIzquierdo(rotarIzquierda(r.getIzquierdo()));
        return rotarDerecha(r);
    }

    private NodoAVL rotacionDerechaIzquierda(NodoAVL r) {
        //PADRE ESTA CAIDO A LA DER Y EL HIJO CAIDO A LA IZQ
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

    private boolean auxEliminar(Comparable elemento, NodoAVL nodo, NodoAVL padre) {
        boolean eliminado = false;
        if (nodo != null) {
            int comparacion = elemento.compareTo(nodo.getElem());
            if (comparacion < 0) {
                eliminado = auxEliminar(elemento, nodo.getIzquierdo(), nodo);
                if(eliminado){
                    balancear(balance(nodo.getIzquierdo()), nodo.getIzquierdo(), nodo);
                }
            } else if (comparacion > 0) {
                eliminado = auxEliminar(elemento, nodo.getDerecho(), nodo);
                if(eliminado){
                    balancear(balance(nodo.getDerecho()), nodo.getDerecho(), nodo);
                }
            } else {
                // Encontro al nodo-----------------------------------------------------.
                if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                    caso1(elemento, padre);
                } else if (nodo.getIzquierdo() == null || nodo.getDerecho() == null) {
                    caso2(elemento, nodo, padre);
                } else {
                    caso3(nodo, nodo.getDerecho(), nodo.getDerecho());
                    balancear((balance(nodo.getDerecho())), nodo.getDerecho(), nodo);  
                }                           
                eliminado = true;
            }
            if(eliminado && padre !=null){
                nodo.recalcularAltura();
                padre.recalcularAltura();
            }
            
        }
        return eliminado;
    }

    // Caso 1 de eliminar.
    private void caso1(Comparable elemento, NodoAVL padre) {
        if (padre == null) {
            this.raiz = null;
        } else {
            if (elemento.compareTo(padre.getElem()) < 0) {
                padre.setIzquierdo(null);
            } else {
                padre.setDerecho(null);
            }
        }
    }

    // Caso 2 de eliminar.
    private void caso2(Comparable elem, NodoAVL nodo, NodoAVL padre) {
        // Busco al candidato para reemplazar al nodo
        // almenos 1 sera null.
        NodoAVL derecho = nodo.getDerecho();
        NodoAVL izquierdo = nodo.getIzquierdo();
        if (padre == null) {
            // Caso especial.
            if (derecho == null) {
                this.raiz = izquierdo;
            } else {
                this.raiz = derecho;
            }
        } else {
            // Verifico la rama derecha o izquierda.
            if (elem.compareTo(padre.getElem()) < 0) {
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

    /**
     * Usar el candidato B (El Menor del subarbol Derecho de N, siendo N el
     * nodo a eliminar).
     *
     * @param actual envia el nodo a eliminar.
     */

    private void caso3(NodoAVL raiz, NodoAVL padre, NodoAVL nodo) {
        NodoAVL candidato;
        if (nodo.getIzquierdo() == null) {
            candidato = nodo;
        } else {
            candidato = nodo.getIzquierdo();
        }
        if (candidato.getIzquierdo() != null) {
            caso3(raiz, nodo, candidato);
        } else {
            raiz.setElem(candidato.getElem());
            NodoAVL derecho = candidato.getDerecho();
            if (raiz.getDerecho() == candidato) {
                raiz.setDerecho(derecho);
            } else {
                padre.setIzquierdo(derecho);
            }
        }
        raiz.recalcularAltura();
        padre.recalcularAltura();
    }
    public Lista listarPosorden() {
        //METODO PUBLICO QUE CREA LISTA Y LLAMA A METODO PRIVADO CON LA RAIZ
        Lista lis = new Lista();
        listarPosordenAux(this.raiz, lis);
        return lis;
    }

    private void listarPosordenAux(NodoAVL nodo, Lista lis) {
        //METODO PRIVADO QUE INSERTA EN lis NODO, 1° SE VISITA HIJO IZQ, SE INSERTA Y LUEGO HIJO DER
        if (nodo != null) {
            listarPosordenAux(nodo.getIzquierdo(), lis);
            listarPosordenAux(nodo.getDerecho(), lis);
            lis.insertar(nodo.getElem(), lis.longitud() + 1);
        }
    }

    //POR NIVEL
    public Lista listarPorNiveles() {
        //METODO ITERATIVO QUE VA RECORRIENDO POR NIVELES EL ARBOL Y SE LISTA
        Lista lis = new Lista();
        if (!esVacio()) {
            Cola q = new Cola();
            NodoAVL nodoActual;

            q.poner(this.raiz);
            while (!q.esVacia()) {
                nodoActual = (NodoAVL) q.obtenerFrente();
                q.sacar();
                lis.insertar(nodoActual.getElem(), lis.longitud() + 1);

                if (nodoActual.getIzquierdo() != null) {
                    q.poner(nodoActual.getIzquierdo());
                }
                if (nodoActual.getDerecho() != null) {
                    q.poner(nodoActual.getDerecho());
                }
            }
        }
        return lis;
    }


    public String toString(){
        return llamadoString(this.raiz);
    }
    private String llamadoString(NodoAVL n) {
        String str = "";
        if (n != null) {
            str += "NODO: " + n.getElem().toString() + ".";
            if (n.getIzquierdo() != null) {
                str += " H.I: " + n.getIzquierdo().getElem().toString() + ".";
            }
            else{
                str += " H.I: -.";
            }
            if (n.getDerecho() != null) {
                str += " H.D: " + n.getDerecho().getElem().toString() + ".\n";
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
    /*
    public boolean insertar(Comparable elem){
        boolean res = true;
        if (this.raiz == null) {
            this.raiz = new NodoAVL(elem);
        } else {
            res = insertarAux(this.raiz, null, elem);
        }
        return res;
    }
 
    private boolean insertarAux(NodoAVL n, NodoAVL padre, Comparable elem) {
        boolean res = true;
        if (n.getElem().compareTo(elem) == 0) {
            res = false;
        } else {
            if (n.getElem().compareTo(elem) < 0) {
                if (n.getDerecho() == null) {
                    n.setDerecho(new NodoAVL(elem));
                } else {
                    res = insertarAux(n.getDerecho(), n, elem);
                }
                //Root is greater than node to add, so added to left
            } else if (n.getElem().compareTo(elem) > 0) {
                if (n.getIzquierdo() == null) {
                    n.setIzquierdo(new NodoAVL(elem));
                } else {
                    res = insertarAux(n.getIzquierdo(), n, elem);
                }
            }
        }
        balancear(n, padre, balance(n));
        n.recalcularAltura();
        //Update balance and height
        //Restructure
        return res;
    }
    public int balance(NodoAVL n) {
        NodoAVL derecho = raiz.getDerecho();
        NodoAVL izquierdo = raiz.getIzquierdo();
        int alturaDer, alturaIzq;

        alturaIzq = (izquierdo != null) ? izquierdo.getAltura() : -1;
        alturaDer = (derecho != null) ? derecho.getAltura() : -1;

        return (alturaIzq - alturaDer);
    }
    private NodoAVL balancear(NodoAVL raiz, NodoAVL padre, int balance) {
        if (raiz != null) {
            raiz.recalcularAltura();
            if (balance == -2) {
                if (balance(raiz.getDerecho()) == 1) {
                    // Rotacion doble a derecha-izquierda
                    NodoAVL aux = rotarDerecha(raiz.getDerecho());
                    raiz.setDerecho(aux);
                    raiz = rotarIzquierda(raiz);
                } else {
                    raiz = rotarIzquierda(raiz);
                }
            }
            if (balance == 2) {
                if (balance(raiz.getIzquierdo()) == -1) {
                    // Rotacion doble izquierda - derecha
                    NodoAVL aux = rotarIzquierda(raiz.getIzquierdo());
                    raiz.setIzquierdo(aux);
                    raiz = rotarDerecha(raiz);
                } else {
                    raiz = rotarDerecha(raiz);
                }
            }
            raiz.recalcularAltura();
        }
        return raiz;
    }
    private NodoAVL rotarIzquierda(NodoAVL n) {
        NodoAVL h;
        h = n.getDerecho();
        NodoAVL temp = h.getIzquierdo();
        h.setIzquierdo(n);
        n.setDerecho(temp);
        h.recalcularAltura();
        n.recalcularAltura();

        return h;
    }
    private NodoAVL rotarDerecha(NodoAVL n) {
        NodoAVL h = n.getIzquierdo();
        NodoAVL temp = h.getDerecho();
        h.setDerecho(n);
        n.setIzquierdo(temp);
        n.recalcularAltura();
        h.recalcularAltura();
        return h;
    }
    @Override
    public String toString(){
        return llamadoString(this.raiz);
    }
    private String llamadoString(NodoAVL n) {
        String str = "";
        if (n != null) {
            str += "NODO: " + n.getElem().toString() + ".";
            if (n.getIzquierdo() != null) {
                str += " H.I: " + n.getIzquierdo().getElem().toString() + ".";
            }
            else{
                str += " H.I: -.";
            }
            if (n.getDerecho() != null) {
                str += " H.D: " + n.getDerecho().getElem().toString() + ".\n";
            } else {
                str += " H.D: - \n";
            }
            if (n.getIzquierdo() != null) {
                str += llamadoString(n.getIzquierdo());
            }
            if (n.getDerecho() != null) {
                str += llamadoString(n.getDerecho());
            }
        } else {
            str = "Arbol Vacio";
        }
        return str;
    }
    */
}
