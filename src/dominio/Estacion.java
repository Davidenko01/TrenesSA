/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author Davor Kissner
 */
public class Estacion {
    private final String nombre;
    private String calle;
    private int numero;
    private String ciudad;
    private String codPostal;
    private int cantVias;
    private int cantPlataformas;
    
    public Estacion(String nombre){
        this.nombre = nombre;
        this.calle = null;
        this.numero = 0;
        this.ciudad = null;
        this.codPostal = null;  
        this.cantVias = 0;
        this.cantPlataformas = 0;     
    }
    public Estacion(String nombre, String calle, int numero, String ciudad, String codPostal, int cantVias, int cantPlataformas){
        this.nombre = nombre;
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.codPostal = codPostal;  
        this.cantVias = cantVias;
        this.cantPlataformas = cantPlataformas;   
    }
    public String getNombre(){
        return this.nombre;
    }
    public String getCalle(){
        return this.calle;
    }
    public int getNumero(){
        return this.numero;       
    }
    public String getCiudad(){
        return this.ciudad;
    }
    public String getCodPostal(){
        return this.codPostal;
    }
    public int getCantVias(){
        return this.cantVias;
    }
    public int getCantPlataformas(){
        return this.cantPlataformas;
    }
    public void setCalle(String calle){
        this.calle = calle;
    }
    public void setNumero(int numero){
        this.numero = numero;
    }
    public void setCiudad(String ciudad){
        this.ciudad = ciudad;
    }
    public void setCodPostal(String codPostal){
        this.codPostal = codPostal;
    }
    public void setCantVias(int cantVias){
        this.cantVias = cantVias;
    }
    public void setCantPlataformas(int cantPlataformas){
        this.cantPlataformas = cantPlataformas;
    }
    public String toStringCompleto(){
        return "Estacion: "+this.nombre+", Calle: "+this.calle+", Numero: "+this.numero+", Ciudad: "+this.ciudad+", Codigo Postal: "+this.codPostal+", Cant. de Vias: "+this.cantVias+", Cant. de Plataformas: "+this.cantPlataformas;
    }
    @Override
    public String toString(){
        return "Estación: "+this.nombre;
    }
}
