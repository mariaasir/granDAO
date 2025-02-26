package demo.grandao.Modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "nombre"))
public class Usuarios {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(name = "nombre", unique = true, nullable = false)
    private String nombre;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!*]).{8,20}$",
            message = "La contraseña debe contener al menos una mayúscula, una minúscula, un número y un carácter especial (@#$%^&+=!*)"
    )
    @Column(name = "password", nullable = false)
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
