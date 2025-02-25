package demo.grandao.Controller;

import demo.grandao.Modelo.Autores;
import demo.grandao.Modelo.Libros;
import demo.grandao.Service.Servicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/granDAO")
public class Controller {

Servicio service = new Servicio();

    @Autowired
    public Controller(Servicio service) {
        this.service = service;
    }

    public Controller() {
    }



    /////////////////////AUTORES///////////////////////////


    //GET ALL AUTORES --> SELECT *
    @GetMapping("/getAutores")
    public ResponseEntity<List<Autores>> getAutores() {

        return ResponseEntity.ok(service.getAutores());
    }


    //GET AUTOR BY ID
    @GetMapping("/getAutorby/{id}")
    @Cacheable
    public ResponseEntity<Autores> getAutorById(@PathVariable int id) {
        return ResponseEntity.ok(service.getAutorById(id));
    }

    //POST de un Objeto autor
    @PostMapping("/postAutor")
    public ResponseEntity<Autores> addAutor(@Valid @RequestBody Autores autor) {
        return ResponseEntity.ok().body(service.addAutor(autor));
    }


    //POST con Form Normal
    @PostMapping(value = "/autorForm", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Autores> addAutorForm(
            @RequestParam String nombre,
            @RequestParam String nacionalidad
    ) {
        return ResponseEntity.created(null).body(service.addAutorParameters(nombre,nacionalidad));
    }


    //PUT AUTOR --> UPDATE OBJETO AUTOR
    @PutMapping("/updateAutor/{id}")
    public ResponseEntity<Autores> updateAutor(@PathVariable int id, @RequestBody Autores autor) {
        // Buscar el autor existente por su ID
        Autores autorExistente = service.getAutorById(id);
        if (autorExistente == null) {
            return ResponseEntity.notFound().build(); // Si el libro no existe, retornar 404
        }

        // Actualizar los campos del libro existente con los datos proporcionados
        autorExistente.setNacionalidad(autor.getNacionalidad());
        autorExistente.setNombre(autor.getNombre());

        // Llamar al servicio para actualizar el libro
        Autores autorActualizado = service.updateAutor(autorExistente);

        // Retornar el libro actualizado
        return ResponseEntity.ok().body(autorActualizado);
    }




    /////////////////////LIBROS//////////////////////////


    //GET ALL LIBROS --> SELECT *
    @GetMapping("/getLibros")
    public ResponseEntity<List<Libros>> getLibros() {

        return ResponseEntity.ok(service.getLibros());
    }


    //GET LIBRO BY ID
    @GetMapping("/getLibroById/{id}")
    @Cacheable
    public ResponseEntity<Libros> getLibroById(@PathVariable int id) {
        return ResponseEntity.ok(service.getLibroById(id));
    }



    //POST DE UN OBJETO LIBRO
    @PostMapping("/postLibro")
    public ResponseEntity<Libros> addLibro(@Valid @RequestBody Libros libro) {
        return ResponseEntity.ok().body(service.addLibro(libro));
    }


    //POST DE UN OBJETO LIBRO POR PARÁMETROS
    @PostMapping(value = "/libroForm", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Libros> addLibroForm(
            @RequestParam String titulo,
            @RequestParam Integer anio,
            @RequestParam Integer autor_id
    ) {
        return ResponseEntity.created(null).body(service.addLibroParameters(titulo,anio,autor_id));
    }

    //PUT LIBRO --> UPDATE OBJETO LIBRO
    @PutMapping("/updateLibro/{id}")
    public ResponseEntity<Libros> updateLibro(@PathVariable int id, @RequestBody Libros libro) {
        // Buscar el libro existente por su ID
        Libros libroExistente = service.getLibroById(id);
        if (libroExistente == null) {
            return ResponseEntity.notFound().build(); // Si el libro no existe, retornar 404
        }

        // Actualizar los campos del libro existente con los datos proporcionados
        libroExistente.setTitulo(libro.getTitulo());
        libroExistente.setAnioPublicacion(libro.getAnioPublicacion());
        libroExistente.setAutor(libro.getAutor());

        // Llamar al servicio para actualizar el libro
        Libros libroActualizado = service.updateLibro(libroExistente);

        // Retornar el libro actualizado
        return ResponseEntity.ok().body(libroActualizado);
    }


    //DELETE
    @DeleteMapping("/deleteLibro/{id}")
    public ResponseEntity<String> deleteLibro(@PathVariable int id) {
        service.deleteLibro(id);
        String mensaje = "Libro con id: " + id + " eliminado";
        return ResponseEntity.ok().body(mensaje);
    }
}
