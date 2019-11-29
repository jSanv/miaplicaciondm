package objetos;

import java.io.Serializable;

public class usuarios implements Serializable {
    private String usuario;
    private String credencial;

    public usuarios() {
    }

    public usuarios(String usuario, String credencial) {
        this.usuario = usuario;
        this.credencial = credencial;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCredencial() {
        return credencial;
    }

    public void setCredencial(String credencial) {
        this.credencial = credencial;
    }
}
