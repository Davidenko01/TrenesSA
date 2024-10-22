/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author Usuario
 */
public class Tren {
    private final int identificador;
    private String propulsion;
    private int vagonesPasajeros;
    private int vagonesCarga;
    private String linea;
    
    public Tren (int identificador){
        this.identificador = identificador;
        this.propulsion = "carbón";
        this.vagonesPasajeros = 10;
        this.vagonesCarga = 0;
        this.linea = "no-asignado";
    }
    public Tren(int identificador, String propuslion, int vagonesPasajeros, int vagonesCarga, String linea){
        this.identificador = identificador;
        this.propulsion = propuslion;
        this.vagonesPasajeros = vagonesPasajeros;
        this.vagonesCarga = vagonesCarga;
        this.linea = linea;
    } 
    public int getIdentificador(){
        return this.identificador;
    }
    public String getPropulsion(){
        return this.propulsion;
    }
    public int getVagonesPersonas(){
        return this.vagonesPasajeros;
    }
    public int getVagonesCarga(){
        return this.vagonesCarga;
    }
    public String getLinea(){
        return this.linea;
    }
    public void setPropulsion(String propulsion){
        this.propulsion = propulsion;
    }
    public void setVagonesPasajaeros(int x){
        this.vagonesPasajeros = x;
    }
    public void setVagonesCarga(int x){
        this.vagonesCarga = x;
    }
    public void setLinea(String linea){
        this.linea = linea;
    }
    @Override
    public String toString(){
        return "ID-Tren: "+this.identificador;
    }
    public String toStringCompleto(){
        return "ID-Tren: "+this.identificador+", Tipo Propulsion: "+this.propulsion+", Cant. Vagones Pasajeros: "+this.vagonesPasajeros+", Cant. Vagones Carga: "+this.vagonesCarga+", Linea: "+this.linea;
    }
}
