package com.productos.app.dto;

public class ProductoDTO {

    private String nombre;

    private Float precio;

    public ProductoDTO() {
    }

    public ProductoDTO( String nombre,  Float precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }
}
