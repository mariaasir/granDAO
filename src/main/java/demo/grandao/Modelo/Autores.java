package demo.grandao.Modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Size(max = 50)
    @Column(name = "nacionalidad", length = 50)
    private String nacionalidad;

    public Autores() {
    }

    public Autores(String nombre, String nacionalidad) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @Size(max = 100) @NotNull String getNombre() {
        return nombre;
    }

    public void setNombre(@Size(max = 100) @NotNull String nombre) {
        this.nombre = nombre;
    }

    public @Size(max = 50) String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(@Size(max = 50) String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    @Override
    public String toString() {
        return "Autores{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                '}';
    }
}