package demo.grandao.Controller;

import demo.grandao.Modelo.*;
import demo.grandao.Service.Servicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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


    // ==========================================================================
    //                             MÉTODOS PARA AUTORES
    // ==========================================================================

    //GET ALL AUTORES --> SELECT *
    @GetMapping("/getAutores")
    public ResponseEntity<List<Autores>> getAutores() {
        //Llama al Service para obtener todos los autores desde la base de datos
        return ResponseEntity.ok(service.getAutores()); //Recibe una respuesta con todos los autores
    }


    //GET AUTOR BY ID
    @GetMapping("/getAutorById/{id}")
    @Cacheable
    public ResponseEntity<Autores> getAutorById(@PathVariable int id) {
        //Llama al Service para obtener el autor por ID
        return ResponseEntity.ok(service.getAutorById(id)); // Si el autor existe, devuelve el Objeto de tipo Autor
    }

    //POST de un Objeto autor
    @PostMapping("/postAutor")
    public ResponseEntity<Autores> addAutor(@Valid @RequestBody Autores autor) {
        //Llama al Service para agregar un nuevo autor a la BDD
        return ResponseEntity.ok().body(service.addAutor(autor));  //Devuelve el Objeto del autor creado
    }


    //POST con Form Normal
    @PostMapping(value = "/autorForm", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Autores> addAutorForm(
            @RequestParam @Valid String nombre,  //Recibe el nombre del autor como parámetro del formulario
            @RequestParam @Valid String nacionalidad //Recibe la nacionalidad del autor como parámetro del formulario
    ) {
        //Llama al Service para agregar un nuevo autor con los parámetros recibidos del formulario
        return ResponseEntity.created(null).body(service.addAutorParameters(nombre, nacionalidad)); //Devuelve el Objeto del autor creado
    }


    //PUT AUTOR --> UPDATE OBJETO AUTOR
    @PutMapping("/updateAutor/{id}")
    public ResponseEntity<Autores> updateAutor(@PathVariable @Valid int id, @RequestBody @Valid Autores autor) {
        Autores autorExistente = service.getAutorById(id);         //Busca al autor por su ID
        if (autorExistente == null) {
            return ResponseEntity.notFound().build(); // Si el autor no existe, devuelve un error NOT FOUND
        }
        //Actualiza los campos del autor existente con los datos proporcionados
        autorExistente.setNacionalidad(autor.getNacionalidad());
        autorExistente.setNombre(autor.getNombre());

        Autores autorActualizado = service.updateAutor(autorExistente);         //Llama al Service para actualizar el autor
        return ResponseEntity.ok().body(autorActualizado);         //Devuelve el Objeto Autor con los parámetros actualizados

    }

    //DELETE --> DELETE DE UN OBJETO AUTOR
    @DeleteMapping("/deleteAutor/{id}")
    public ResponseEntity<String> deleteAutor(@PathVariable int id) {
        //Llama al Service para eliminar el autor de la BDD
        service.deleteAutor(id);
        String mensaje = "Autor con id: " + id + " eliminado";  //Muestra un mensaje de que el autor ha sido eliminado con exito
        return ResponseEntity.ok().body(mensaje);
    }



    // ==========================================================================
    //                             MÉTODOS PARA LIBROS
    // ==========================================================================

    //GET ALL LIBROS --> SELECT *
    @GetMapping("/getLibros")
    public ResponseEntity<List<Libros>> getLibros() {
        //Llama al Service para obtener todos los libros desde la base de datos
        return ResponseEntity.ok(service.getLibros()); //Recibe una respuesta con todos los libros
    }

    //GET LIBRO BY ID
    @GetMapping("/getLibroById/{id}")
    @Cacheable
    public ResponseEntity<Libros> getLibroById(@PathVariable int id) {
        //Llama al Service para obtener el libro por ID
        return ResponseEntity.ok(service.getLibroById(id)); // Si el libro existe, devuelve el Objeto de tipo Libro
    }

    //POST DE UN OBJETO LIBRO
    @PostMapping("/postLibro")
    public ResponseEntity<Libros> addLibro(@Valid @RequestBody Libros libro) {
        //Llama al Service para agregar un nuevo libro a la BDD
        return ResponseEntity.ok().body(service.addLibro(libro)); //Devuelve el Objeto del libro creado
    }


    //POST DE UN OBJETO LIBRO POR PARÁMETROS
    @PostMapping(value = "/libroForm", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Libros> addLibroForm(
            @RequestParam @Valid String titulo,   //Recibe el título del libro como parámetro del formulario
            @RequestParam @Valid Integer anio,    //Recibe el año de publicacion del libro como parámetro del formulario
            @RequestParam @Valid Integer autor_id  //Recibe el ID del autor del libro como parámetro del formulario
    ) {
        //Llama al Service para agregar un nuevo libro con los parámetros recibidos del formulario
        return ResponseEntity.created(null).body(service.addLibroParameters(titulo, anio, autor_id));   //Devuelve el Objeto del libro creado
    }

    //PUT LIBRO --> UPDATE OBJETO LIBRO
    @PutMapping("/updateLibro/{id}")
    public ResponseEntity<Libros> updateLibro(@PathVariable @Valid int id, @RequestBody @Valid Libros libro) {
        Libros libroExistente = service.getLibroById(id);         //Busca si el libro existe por su ID
        if (libroExistente == null) {
            return ResponseEntity.notFound().build(); // Si el libro no existe, devuelve un error NOT FOUND
        }

        //Actualiza los campos del libro existente con los datos introducidos
        libroExistente.setTitulo(libro.getTitulo());
        libroExistente.setAnioPublicacion(libro.getAnioPublicacion());
        libroExistente.setAutor(libro.getAutor());

        Libros libroActualizado = service.updateLibro(libroExistente);  //Llama al Service para actualizar el libro
        return ResponseEntity.ok().body(libroActualizado); //Devuelve el Objeto del libro actualizado
    }


    //DELETE --> DELETE DE UN OBJETO LIBRO
    @DeleteMapping("/deleteLibro/{id}")
    public ResponseEntity<String> deleteLibro(@PathVariable int id) {
        //Llama al Service para eliminar el libro de la BDD
        service.deleteLibro(id);
        String mensaje = "Libro con id: " + id + " eliminado";  //Muestra un mensaje de que el libro se ha eliminado con éxito
        return ResponseEntity.ok().body(mensaje);
    }


    // ==========================================================================
    //                             MÉTODOS PARA USUARIOS
    // ==========================================================================


    //GET ALL USUARIOS --> SELECT *
    @GetMapping("/getUsuarios")
    public ResponseEntity<List<Usuarios>> getUsuarios() throws JAXBException {
        //Llama al Service para obtener todos los usuarios desde el fichero XML
        return ResponseEntity.ok(service.getUsuarios()); //Recibe una respuesta con todos los usuarios
    }

    //GET USUARIO BY NOMBRE
    @GetMapping("/getUsuarioByName/{nombre}")
    @Cacheable
    public ResponseEntity<Usuarios> getUsuarioByName(@PathVariable String nombre) throws JAXBException {
        //Llama al Service para obtener el usuario por su Nombre
        return ResponseEntity.ok(service.getUsuarioByName(nombre));
    }


    //POST DE UN OBJETO USUARIO
    @PostMapping("/postUsuario")
    public ResponseEntity<Usuarios> postUsuario(@RequestBody @Valid Usuarios usuario) throws JAXBException {
        //Llama al Service para agregar un nuevo usuario al fichero XML
        service.guardarUsuario(usuario);
        return ResponseEntity.ok(usuario);  //Devuelve el Objeto del usuario creado
    }


    //PUT USUARIO --> UPDATE OBJETO USUARIO
    @PutMapping("/updateUsuario/{nombre}")
    public ResponseEntity<Usuarios> actualizarUsuario(@PathVariable @Valid String nombre, @RequestBody @Valid Usuarios usuario) throws JAXBException {
        Usuarios usuarioExistente = service.getUsuarioByName(nombre);   //Busca si el usuario existe por su nombre
        if (usuarioExistente == null) {
            return ResponseEntity.notFound().build();           // Si el usuario no existe, devuelve un error NOT FOUND
        }

        //Actualiza los campos del usuario existente con los datos introducidos
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setPassword(usuario.getPassword());

        service.actualizarUsuario(nombre, usuario); //Llama al Service para actualizar el usuario
        return ResponseEntity.ok(usuario); //Devuelve el Objeto del usuario actualizado

    }


    //DELETE --> DELETE DE UN OBJETO USUARIO
    @DeleteMapping("/deleteUsuario/{nombre}")
    public ResponseEntity<String> deleteUsuario(@PathVariable String nombre) throws JAXBException {
        //Llama al Service para eliminar el usuario del fichero XMl
        service.deleteUsuario(nombre);
        String mensaje = "Usuario " + nombre + " eliminado"; //Muestra un mensaje de que el usuario se ha eliminado con exito
        return ResponseEntity.ok().body(mensaje);
    }




    // ==========================================================================
    //                             MÉTODOS PARA COCHES
    // ==========================================================================


    //Endpoint para guardar un nuevo coche
    @PostMapping("/postCoche")
    public ResponseEntity<String> guardarCoche(@RequestBody Coche coche) throws IOException, ClassNotFoundException {
        service.insertarCoche(coche);
        return ResponseEntity.ok("Coche añadido correctamente");
    }

    //Endpoint para leer un coche por matrícula
    @GetMapping("/getCocheByMatricula/{matricula}")
    public ResponseEntity<Coche> obtenerCochePorMatricula(@PathVariable String matricula) throws IOException, ClassNotFoundException {
        return ResponseEntity.ok(service.buscarCochePorMatricula(matricula));
    }

    //Endpoint para leer todos los coches
    @GetMapping("/getCoches")
    public ResponseEntity<List<Coche>> obtenerCoches() throws IOException, ClassNotFoundException {
        List<Coche> coches = service.obtenerCoches();
        return ResponseEntity.ok(coches);
    }


    // ==========================================================================
    //                             MÉTODOS PARA CIUDADES
    // ==========================================================================

    //Obtener todas las ciudades
    @GetMapping("/getCiudades")
    public ResponseEntity<List<Ciudad>> obtenerTodasLasCiudades() {
        return ResponseEntity.ok(service.obtenerTodasLasCiudades());
    }

    //Obtener una ciudad por su ID
    @GetMapping("/getCiudadPorId/{id}")
    public ResponseEntity<Ciudad> obtenerCiudadPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.obtenerCiudadPorId(id));
    }

    //Crear una nueva ciudad
    @PostMapping("/postCiudad")
    public ResponseEntity<Ciudad> crearCiudad(@RequestBody Ciudad ciudad) {
        return ResponseEntity.ok(service.guardarCiudad(ciudad));
    }

    //Actualizar una ciudad existente
    @PutMapping("/updateCiudad/{id}")
    public ResponseEntity<Ciudad> actualizarCiudad(@PathVariable String id, @RequestBody Ciudad ciudad) {
        ciudad.setId(id); // Asegurar que el ID coincida
        return ResponseEntity.ok().body(service.guardarCiudad(ciudad));
    }

    //Eliminar una ciudad por su ID
    @DeleteMapping("/deleteCiudad/{id}")
    public ResponseEntity<String> eliminarCiudad(@PathVariable String id) {
        service.eliminarCiudadPorId(id);
        return ResponseEntity.ok("Ciudad con ID: " + id + " eliminada");
    }

}
