package objetos;

import java.io.Serializable;

public class productos implements Serializable {
    private String nombre;
    private String fechacaducidad;
    private int cantidad;
    private Double precio;
    private String codigo;

    public productos() {
    }

    public productos(String nombre, String fechacaducidad, int cantidad, Double precio, String codigo) {
        this.nombre = nombre;
        this.fechacaducidad = fechacaducidad;
        this.cantidad = cantidad;
        this.precio = precio;
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }


    public String getFechacaducidad() {
        return fechacaducidad;
    }

    public void setFechacaducidad(String fechacaducidad) {
        this.fechacaducidad = fechacaducidad;
    }
}
