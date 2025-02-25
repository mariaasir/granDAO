package demo.grandao.Service;

import demo.grandao.Modelo.Autores;
import demo.grandao.Modelo.Libros;
import demo.grandao.Repositories.AutorRepository;
import demo.grandao.Repositories.LibroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Servicio {
    AutorRepository repositorioAutores;
    LibroRepository repositorioLibros;

    @Autowired
    // Constructor Injection
    public Servicio(AutorRepository repositorioAutores, LibroRepository repositorioLibros) {
        this.repositorioAutores = repositorioAutores;
        this.repositorioLibros = repositorioLibros;
    }

    public Servicio() {
    }


    //MÉTODOS PARA AUTOR
    public List<Autores> getAutores() {
        return repositorioAutores.findAll();
    }

    public Autores getAutorById(int id) {
        return repositorioAutores.findById(id).get();
    }

    public Autores addAutor(@Valid Autores autor) {
        return repositorioAutores.save(autor);
    }

    public Autores addAutorParameters(String nombre, String nacionalidad) {
        Autores autor = new Autores();
        autor.setNombre(nombre);
        autor.setNacionalidad(nacionalidad);
        return repositorioAutores.save(autor);
    }

    public Autores updateAutor(Autores autor) {
        return repositorioAutores.save(autor);
    }

    public void deleteAutor(int id) {
        repositorioAutores.deleteById(id);
    }



    //MÉTODOS PARA LIBROS
    public List<Libros> getLibros() {
        return repositorioLibros.findAll();
    }

    public Libros getLibroById(int id) {
        return repositorioLibros.findById(id).get();
    }

    public Libros addLibro(@Valid Libros libro) {
        return repositorioLibros.save(libro);
    }

    public Libros addLibroParameters(String titulo, Integer anio, Integer autor_id) {
        Libros libro = new Libros();
        libro.setTitulo(titulo);
        libro.setAnioPublicacion(anio);
        libro.setAutor(getAutorById(autor_id));
        return repositorioLibros.save(libro);
    }

    //UPDATE
    public Libros updateLibro(Libros libro) {
        // Verificar que los campos requeridos no sean nulos
        if (libro.getTitulo() == null || libro.getAnioPublicacion() == null) {
            System.out.println("Error: Título o año de publicación son nulos.");
            throw new IllegalArgumentException("Título y año de publicación son obligatorios.");
        }

        // Verificar que el libro tiene un autor válido antes de acceder a su ID
        if (libro.getAutor() == null || libro.getAutor().getId() == null) {
            System.out.println("Error: El libro no tiene un autor válido.");
            throw new IllegalArgumentException("El libro debe tener un autor válido.");
        }

        // Guardar cambios en la base de datos
        System.out.println("✅ Guardando actualización del libro con ID: " + libro.getId());
        return repositorioLibros.save(libro);
    }


    public void deleteLibro(int id) {
        repositorioLibros.deleteById(id);
    }
}
