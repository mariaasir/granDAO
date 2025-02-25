package demo.grandao.Modelo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Usuarios {

    private String nombre;
    private String password;

    // Constructor vacío para JAXB
    public Usuarios() {}

    // Constructor con parámetros
    public Usuarios(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }

    // Getters y setters
    @XmlElement
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlElement
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
