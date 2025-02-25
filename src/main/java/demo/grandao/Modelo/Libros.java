package demo.grandao.Modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @NotNull
    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;


    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "autor_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Autores autor;

    public Libros( String titulo, Integer anioPublicacion, Autores autor) {
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