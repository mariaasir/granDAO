package demo.grandao.Modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Year;

@Getter
@Setter
@Entity
@Table(name = "libros")

public class Libros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 150)
    @NotBlank(message = "El título no puede estar vacío")
    @Pattern(regexp = "^[a-zA-Z0-9À-ÿ\\s.,'-]+$", message = "El título contiene caracteres no permitidos")
    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Min(value = 1000, message = "El año de publicación debe ser válido")
    @Max(value = Year.MAX_VALUE, message = "El año de publicación no puede ser superior al actual")
    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;


    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "autor_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Autores autor;

    public Libros(String titulo, Integer anioPublicacion, Autores autor) {
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
        this.autor = autor;
    }

    public Libros() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @Size(max = 150) @NotNull String getTitulo() {
        return titulo;
    }

    public void setTitulo(@Size(max = 150) @NotNull String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public Autores getAutor() {
        return autor;
    }

    public void setAutor(Autores autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Libros{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", anioPublicacion=" + anioPublicacion +
                ", autor=" + autor +
                '}';
    }
}