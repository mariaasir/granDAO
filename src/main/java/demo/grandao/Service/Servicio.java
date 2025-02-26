package demo.grandao.Service;

import demo.grandao.DAO.UsuarioXmlDAO;
import demo.grandao.Modelo.Autores;
import demo.grandao.Modelo.Libros;
import demo.grandao.Modelo.Usuarios;
import demo.grandao.Modelo.UsuariosList;
import demo.grandao.Repositories.AutorRepository;
import demo.grandao.Repositories.LibroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class Servicio {
    AutorRepository repositorioAutores;
    LibroRepository repositorioLibros;
    UsuarioXmlDAO repositorioUsuarios;

    //Constructor de la clase Servicio, añade los repositorios de Autores y Libros

    @Autowired
    public Servicio(AutorRepository repositorioAutores, LibroRepository repositorioLibros, UsuarioXmlDAO repositorioUsuarios) {
        this.repositorioAutores = repositorioAutores;
        this.repositorioLibros = repositorioLibros;
        this.repositorioUsuarios = repositorioUsuarios;
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
    public Autores addAutorParameters(String nombre, String nacionalidad) {
        Autores autor = new Autores();
        autor.setNombre(nombre);
        autor.setNacionalidad(nacionalidad);
        return repositorioAutores.save(autor);
    }

    //Actualiza los datos de un autor en la base de datos
    public Autores updateAutor(Autores autor) {
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
    public Libros addLibroParameters(String titulo, Integer anio, Integer autor_id) {
        Libros libro = new Libros();
        libro.setTitulo(titulo);
        libro.setAnioPublicacion(anio);
        libro.setAutor(getAutorById(autor_id)); // Obtener el autor por ID
        return repositorioLibros.save(libro);
    }

    //Actualiza un libro en la base de datos
    public Libros updateLibro(Libros libro) {
        return repositorioLibros.save(libro);
    }

    //Elimina un libro de la base de datos por su ID
    public void deleteLibro(int id) {
        repositorioLibros.deleteById(id);
    }


    // ==========================================================================
    //                    MÉTODOS PARA USUARIOS EN FICHEROS XML
    // ==========================================================================


    private static final String FILE_PATH = "usuarios.xml"; //Ruta al archivo XML donde se almacenan los usuarios

    public List<Usuarios> getUsuarios() {
        try {
            return repositorioUsuarios.leerUsuarios(); // Llamada al método que lee los usuarios desde el XML
        } catch (JAXBException e) {
            e.printStackTrace(); // Registrar el error para depuración
            return new ArrayList<>(); // Devolver lista vacía si hay un error
        }
    }


    //Obtiene un usuario por su nombre desde el archivo XML
    public Usuarios getUsuarioByName(String nombre) throws JAXBException {
        //Obtiene la lista de usuarios
        List<Usuarios> usuarios = getUsuarios();

        //Busca el usuario con el nombre especificado
        for (Usuarios usuario : usuarios) {
            if (usuario.getNombre().equals(nombre)) {
                return usuario;  //Devuelve el usuario encontrado
            }
        }

        return null;  //Devuelve null si el usuario no existe
    }


    public void guardarUsuario(Usuarios usuario) throws JAXBException {
        List<Usuarios> usuarios = getUsuarios();  // Obtiene los usuarios existentes

        // Si la lista de usuarios es null, inicializa una lista vacía
        if (usuarios == null) {
            usuarios = new ArrayList<>();
        }

        usuarios.add(usuario);  // Agrega el nuevo usuario a la lista

        // Crea el wrapper para la lista de usuarios
        UsuariosList wrapper = new UsuariosList(usuarios);
        repositorioUsuarios.guardarUsuarios(usuarios);

    }


    //Actualiza los datos de un usuario en el archivo XML
    // Actualizar un usuario existente
    public void actualizarUsuario(String nombre, Usuarios usuarioNuevo) throws JAXBException {
        repositorioUsuarios.updateUsuario(nombre, usuarioNuevo);
    }

    // Eliminar un usuario
    public void deleteUsuario(String nombre) throws JAXBException {
        repositorioUsuarios.deleteUsuario(nombre);
    }
}
