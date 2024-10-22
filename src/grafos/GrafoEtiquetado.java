/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafos;

import lineales.Lista;
import java.util.HashMap;


/**
 *
 * @author Davor Kissner
 */
public class GrafoEtiquetado {

    private NodoVert inicio = null;

    public GrafoEtiquetado() {
        this.inicio = null;
    }

    public boolean insertarVertice(Object nuevoVertice) {
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(nuevoVertice);
        if (aux == null) {
            this.inicio = new NodoVert(nuevoVertice, this.inicio);
            exito = true;
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean eliminarVertice(Object elem) {
        boolean exito = false;
        NodoVert verticeAux = this.inicio;
        NodoVert verticePrevio = null;
        NodoAdy sigVertice;
        NodoVert buscado = null;
        while (verticeAux != null && buscado == null) {
            if (verticeAux.getElem().equals(elem)) {
                buscado = verticeAux;
            } else {
                verticePrevio = verticeAux;
            }
            verticeAux = verticeAux.getSigVertice();
        }
        if (buscado != null) {
            exito = true;
            sigVertice = buscado.getPrimerAdy();
            while (sigVertice != null) {
                eliminarVerticeAux(sigVertice.getVertice(), elem);
                sigVertice = sigVertice.getSigAdyacente();
            }
            if (verticePrevio != null) {
                verticePrevio.setSigVertice(verticeAux);
            } else {
                this.inicio = verticeAux;
            }
        }

        return exito;
    }
    private NodoVert eliminarVerticeAux(NodoVert vert, Object fin) {
        NodoAdy aux = vert.getPrimerAdy();
        NodoVert destino = null;
        boolean exito = false;
        NodoAdy previo = null;
        if (aux != null) {
            while (aux != null && !exito) {
                if (aux.getVertice().getElem().equals(fin)){
                    exito = true;
                    destino = aux.getVertice();
                } else {
                    previo = aux;
                    aux = aux.getSigAdyacente();
                }
            }
            if (exito) {
                if (previo != null){
                    previo.setSigAdyacente(aux.getSigAdyacente());
                } else {
                    vert.setPrimerAdy(aux.getSigAdyacente());
                }
            }
        }
        return destino;
    }

    public boolean insertarArco(Object origen, Object destino, int etiqueta) {
        boolean res = false;
        boolean existe;
        NodoAdy ady;
        NodoVert verticeOrigen = ubicarVertice(origen);
        NodoVert verticeDestino = ubicarVertice(destino);
        if (verticeOrigen != null && verticeDestino != null) {
            ady = verticeOrigen.getPrimerAdy();
            existe = false;
            while (ady != null && !existe) {
                if (ady.getVertice().getElem().equals(destino)) {
                    existe = true;
                } else {
                    ady = ady.getSigAdyacente();
                }
            }
            if (!existe) {
                verticeOrigen.setPrimerAdy(new NodoAdy(verticeDestino, verticeOrigen.getPrimerAdy(), etiqueta));
                verticeDestino.setPrimerAdy(new NodoAdy(verticeOrigen, verticeDestino.getPrimerAdy(), etiqueta));
                res = true;
            }
        }

        return res;
    }

    public boolean eliminarArco(Object origen, Object fin) {
        boolean exito = false;
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(fin);       
        if(nodoOrigen!=null && nodoDestino!=null){
            exito = eliminarArcoAux(nodoOrigen, fin) && eliminarArcoAux(nodoDestino, origen);
        }
        return exito;
    }   
    private boolean eliminarArcoAux(NodoVert vert, Object fin) {  
        boolean exito = false;
        NodoAdy aux = vert.getPrimerAdy();
        if(aux.getVertice().getElem().equals(fin)){
            vert.setPrimerAdy(aux.getSigAdyacente());
            exito = true;
        }
        while(!exito && aux.getSigAdyacente() != null){
            if(aux.getSigAdyacente().getVertice().getElem().equals(fin)){
                aux.setSigAdyacente(aux.getSigAdyacente().getSigAdyacente());
                exito = true;
            }
            aux = aux.getSigAdyacente();
        }
        return exito;
    }

    public boolean existeArco(Object vertice1, Object vertice2) {
        NodoVert rec = ubicarVertice(vertice1);
        boolean exito = false;
        if (rec != null) {
            NodoAdy ady = rec.getPrimerAdy();
            while (ady != null && !exito) {
                if (ady.getVertice().equals(vertice2)) {
                    exito = true;
                } else {
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public Lista listarEnProfundidad() {
        Lista visitados = new Lista();
        // define un vetice donde comenzar a recorrer
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                // si el vertice no fue visitado aun , avanza en profundidad
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }

        return visitados;
    }

    private void listarEnProfundidadAux(NodoVert n, Lista vis) {
        if (n != null) {
            // marca al vertice n como visitado
            vis.insertar(n.getElem(), vis.longitud() + 1);
            NodoAdy ady = n.getPrimerAdy();
            while (ady != null) {
                // visita en profundidad los adyacetntes de n aun no visitados;
                if (vis.localizar(ady.getVertice().getElem()) < 0) {
                    listarEnProfundidadAux(ady.getVertice(), vis);
                }
                ady = ady.getSigAdyacente();
            }

        }
    }

    public boolean existeCamino(Object origen, Object destino) {
        boolean exito = false;
        // verifica si ambos vertices existen 
        NodoVert auxO = ubicarVertice(origen);
        NodoVert auxD = ubicarVertice(destino);
        if (auxO != null && auxD != null) {
            // si ambos vertices existen busca si existe camino entre ambos
            Lista visitados = new Lista();
            exito = existeCaminoAux(auxO, destino, visitados);

        }
        return exito;
    }

    private boolean existeCaminoAux(NodoVert n, Object dest, Lista vis) {
        boolean exito = false;
        if (n != null) {
            // si vertice n es el destino: hay camino
            if (n.getElem().equals(dest)) {
                exito = true;
            } else {
                // si no es el destino verifica si hay camino entre n y el destino
                vis.insertar(n.getElem(), vis.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (!exito && ady != null) {
                    if (vis.localizar(ady.getVertice().getElem()) < 0) {
                        exito = existeCaminoAux(ady.getVertice(), dest, vis);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public Lista caminoMasCorto(Object origen, Object destino) {
        Lista l1 = new Lista();
        Lista minima = new Lista();
        NodoVert aux = ubicarVertice(origen);
        if (aux != null) {
            l1.insertar(origen, 1);
            minima = caminoMasCortoAux(aux, destino, minima, l1);

        }
        return minima;
    }

    private Lista caminoMasCortoAux(NodoVert origen, Object destino, Lista minima, Lista l1) {
        NodoAdy ady;
        if (origen != null) {
            if (origen.getElem().equals(destino)) {
                minima = l1.clone();
            } else {
                if (origen.getPrimerAdy() != null) {
                    ady = origen.getPrimerAdy();
                    while (ady != null) {
                        if (l1.localizar(ady.getVertice().getElem()) < 0) {
                            if (!minima.esVacia()) {
                                if (l1.longitud() < minima.longitud()) {
                                    l1.insertar(ady.getVertice().getElem(), l1.longitud() + 1);
                                    minima = caminoMasCortoAux(ady.getVertice(), destino, minima, l1);
                                    l1.eliminar(l1.longitud());
                                }
                            } else {
                                l1.insertar(ady.getVertice().getElem(), l1.longitud() + 1);
                                minima = caminoMasCortoAux(ady.getVertice(), destino, minima, l1);
                                l1.eliminar(l1.longitud());
                            }
                        }
                        ady = ady.getSigAdyacente();
                    }
                }
            }
        }
        return minima;
    }
    public HashMap listaCaminoMasRapido(Object inicio,Object destino){
        HashMap aux = new HashMap(); 
        int kms = 0;
        Lista visitados = new Lista();
        Lista minima = new Lista();
        aux.put("peso", Integer.MAX_VALUE);
        NodoVert ini = ubicarVertice(inicio);
        NodoVert fin = ubicarVertice(destino);
        if(ini != null && fin != null){          
           minima = CaminoMasRapidoAux(ini,destino,visitados,minima,aux,kms);        
           aux.put("camino", minima);
        }
        return aux;
    }
    private Lista CaminoMasRapidoAux(NodoVert origen, Object destino, Lista visitados, Lista minima, HashMap aux, int kms) {
        if (origen != null) {          
            visitados.insertar(origen.getElem(), visitados.longitud() + 1);
            if (origen.getElem().equals(destino)) {
                if (kms < (int) aux.get("peso")) {
                    minima = visitados.clone();
                    aux.remove("peso");
                    aux.put("peso", kms);
                }
            } else {
                NodoAdy ady = origen.getPrimerAdy();
                while (ady != null) {
                    kms += ady.getEtiqueta();
                    if (visitados.localizar(ady.getVertice().getElem()) < 0 && kms < (int)aux.get("peso")) {
                        minima = CaminoMasRapidoAux(ady.getVertice(), destino, visitados, minima, aux, kms);
                    }
                    kms -= ady.getEtiqueta();
                    ady = ady.getSigAdyacente();
                }
            }
            visitados.eliminar(visitados.longitud());
        }

        return minima;
    }

    @Override
    public String toString() {
        String cadena = "";
        NodoVert vertice = this.inicio;
        NodoAdy arco;
        while (vertice != null) {
            cadena += "|" + vertice.getElem().toString() + "|" + "-se conecta con:";
            arco = vertice.getPrimerAdy();
            while (arco != null) {
                cadena += "(" + arco.getVertice().getElem().toString() + ")";
                arco = arco.getSigAdyacente();
            }
            cadena += "\n";
            vertice = vertice.getSigVertice();
        }
        return cadena;
    }
}
    
    
