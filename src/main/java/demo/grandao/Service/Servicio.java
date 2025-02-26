package demo.grandao.Service;

import demo.grandao.DAO.CocheFileDAO;
import demo.grandao.DAO.UsuarioXmlDAO;
import demo.grandao.Modelo.*;
import demo.grandao.Repositories.AutorRepository;
import demo.grandao.Repositories.LibroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Servicio {
    AutorRepository repositorioAutores;
    LibroRepository repositorioLibros;
    UsuarioXmlDAO repositorioUsuarios;
    CocheFileDAO repositorioCoches;

    //Constructor de la clase Servicio, añade los repositorios de Autores y Libros

    @Autowired
    public Servicio(AutorRepository repositorioAutores, LibroRepository repositorioLibros, UsuarioXmlDAO repositorioUsuarios, CocheFileDAO repositorioCoches) {
        this.repositorioAutores = repositorioAutores;
        this.repositorioLibros = repositorioLibros;
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioCoches = repositorioCoches;
    }

    public Servicio() {
    }


    // ==========================================================================
    //                             MÉTODOS PARA AUTORES
    // ==========================================================================


    //Obtiene todos los autores desde la base de datos
    public List<Autores> getAutores() {
        return repositorioAutores.findAll();
    }

    //Obtiene un autor por su ID desde la base de datos
    public Autores getAutorById(int id) {
        return repositorioAutores.findById(id).get();
    }

    //Agrega un nuevo autor a la base de datos
    public Autores addAutor(@Valid Autores autor) {
        return repositorioAutores.save(autor);
    }

    //Agrega un autor usando parámetros
    public Autores addAutorParameters(@Valid  String nombre, @Valid String nacionalidad) {
        Autores autor = new Autores();
        autor.setNombre(nombre);
        autor.setNacionalidad(nacionalidad);
        return repositorioAutores.save(autor);
    }

    //Actualiza los datos de un autor en la base de datos
    public Autores updateAutor(@Valid Autores autor) {
        return repositorioAutores.save(autor);
    }

    //Elimina un autor de la base de datos por su ID
    public void deleteAutor(int id) {
        repositorioAutores.deleteById(id);
    }


    // ==========================================================================
    //                             MÉTODOS PARA LIBROS
    // ==========================================================================


    //Obtiene todos los libros desde la base de datos
    public List<Libros> getLibros() {
        return repositorioLibros.findAll();
    }

    //Obtiene un libro por su ID desde la base de datos
    public Libros getLibroById(int id) {
        return repositorioLibros.findById(id).get();
    }

    //Agrega un nuevo libro a la base de datos
    public Libros addLibro(@Valid Libros libro) {
        return repositorioLibros.save(libro);
    }

    //Agrega un libro utilizando parámetros
    public Libros addLibroParameters(@Valid String titulo, @Valid Integer anio,@Valid Integer autor_id) {
        Libros libro = new Libros();
        libro.setTitulo(titulo);
        libro.setAnioPublicacion(anio);
        libro.setAutor(getAutorById(autor_id)); // Obtener el libro por ID
        return repositorioLibros.save(libro);
    }

    //Actualiza un libro en la base de datos
    public Libros updateLibro(@Valid Libros libro) {
        return repositorioLibros.save(libro);
    }

    //Elimina un libro de la base de datos por su ID
    public void deleteLibro(int id) {
        repositorioLibros.deleteById(id);
    }


    // ==========================================================================
    //                    MÉTODOS PARA USUARIOS EN FICHEROS XML
    // ==========================================================================



    public List<Usuarios> getUsuarios() {
        try {
            return repositorioUsuarios.leerUsuarios(); //Llama al método que lee los usuarios desde el XML
        } catch (JAXBException e) {
            e.printStackTrace();
            return new ArrayList<>(); //Devuelve una lista vacía si hay un error
        }
    }


    //Obtiene un usuario por su nombre desde el archivo XML
    public Usuarios getUsuarioByName(String nombre) throws JAXBException {
        List<Usuarios> usuarios = repositorioUsuarios.leerUsuariosPorNombre(nombre);
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }


    public void guardarUsuario(@Valid Usuarios usuario) throws JAXBException {
        List<Usuarios> usuarios = getUsuarios();  // Obtiene los usuarios existentes

        //Si la lista de usuarios es null, inicializa una lista vacía
        if (usuarios == null) {
            usuarios = new ArrayList<>();
        }
        usuarios.add(usuario);  //Agrega el nuevo usuario a la lista
        repositorioUsuarios.guardarUsuarios(usuarios);

    }


    //Actualiza los datos de un usuario en el archivo XML
    public void actualizarUsuario(@Valid String nombre, @Valid Usuarios usuarioNuevo) throws JAXBException {
        repositorioUsuarios.updateUsuario(nombre, usuarioNuevo);
    }

    // Eliminar un usuario
    public void deleteUsuario(String nombre) throws JAXBException {
        repositorioUsuarios.deleteUsuario(nombre);
    }


    // ==========================================================================
    //                             MÉTODOS PARA COCHES
    // ==========================================================================



    // Leer la lista de coches desde el fichero
    public List<Coche> obtenerCoches() throws IOException, ClassNotFoundException {
        return repositorioCoches.leerCoches();
    }

    // Añadir un coche a la lista existente
    public void insertarCoche(Coche coche) throws IOException, ClassNotFoundException {
        repositorioCoches.insertarCoche(coche);
    }

    // Buscar un coche por matrícula
    public Optional<Coche> buscarCochePorMatricula(String matricula) throws IOException, ClassNotFoundException {
        return repositorioCoches.buscarCochePorMatricula(matricula);
    }
}


