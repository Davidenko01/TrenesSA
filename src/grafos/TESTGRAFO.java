/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package grafos;

/**
 *
 * @author Usuario
 */
public class TESTGRAFO {

    public static void main(String[] args) {
        GrafoEtiquetado grafo = new GrafoEtiquetado();
        grafo.insertarVertice('E');
        grafo.insertarVertice('D');
        grafo.insertarVertice('C');
        grafo.insertarVertice('B');
        grafo.insertarVertice('A');
        grafo.insertarArco('A', 'B', 6);
        grafo.insertarArco('A', 'D', 1);
        grafo.insertarArco('B', 'E', 2);
        grafo.insertarArco('D', 'B', 2);
        grafo.insertarArco('D', 'E', 1);
        grafo.insertarArco('B', 'C', 5);
        grafo.insertarArco('E', 'C', 5);
        System.out.println(grafo.toString());
        System.out.println(grafo.listaCaminoMasRapido('A', 'C').toString());
    }

}
