package objetos;

import java.io.Serializable;

public class productossalida implements Serializable {
    private String nombre;
    private String fechacaducidad;
    private int cantidad;
    private Double precio;
    private String codigo;


    private String fechsalida;
    private int correlativo;

    public productossalida() {
    }

    public productossalida(String nombre, String fechacaducidad, int cantidad, Double precio, String codigo, String fechsalida, int correlativo) {
        this.nombre = nombre;
        this.fechacaducidad = fechacaducidad;
        this.cantidad = cantidad;
        this.precio = precio;
        this.codigo = codigo;
        this.fechsalida = fechsalida;
        this.correlativo = correlativo;
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

    public String getFechsalida() {
        return fechsalida;
    }

    public void setFechsalida(String fechsalida) {
        this.fechsalida = fechsalida;
    }

    public int getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(int correlativo) {
        this.correlativo = correlativo;
    }
}
