package demo.grandao.Service;

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

    //Constructor de la clase Servicio, añade los repositorios de Autores y Libros
    @Autowired
    public Servicio(AutorRepository repositorioAutores, LibroRepository repositorioLibros) {
        this.repositorioAutores = repositorioAutores;
        this.repositorioLibros = repositorioLibros;
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
            // Si el archivo no existe, retorna una lista vacía
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return new ArrayList<>();
            }

            // Si el archivo existe, lee y deserializa la lista de usuarios desde el archivo XML
            JAXBContext context = JAXBContext.newInstance(UsuariosList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            UsuariosList usuariosList = (UsuariosList) unmarshaller.unmarshal(file);
            return usuariosList.getUsuarios(); // Devuelve la lista de usuarios deserializada
        } catch (JAXBException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Si hay un error, retorna una lista vacía
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

        // Serializa la lista de usuarios y la escribe en el archivo XML
        JAXBContext context = JAXBContext.newInstance(UsuariosList.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(wrapper, new File(FILE_PATH)); // Guarda los cambios en XML
    }


    //Actualiza los datos de un usuario en el archivo XML
    public void actualizarUsuario(String nombre, Usuarios usuarioNuevo) throws JAXBException {
        List<Usuarios> usuarios = getUsuarios();  //Obtiene la lista de usuarios

        //Busca el usuario con el nombre especificado
        for (Usuarios usuario : usuarios) {
            if (usuario.getNombre().equals(nombre)) {
                //Actualiza los datos del usuario
                usuario.setNombre(usuarioNuevo.getNombre());
                usuario.setPassword(usuarioNuevo.getPassword());
                break;
            }
        }

        //Crea el wrapper con la lista actualizada de usuarios
        UsuariosList wrapper = new UsuariosList(usuarios);

        //Serializa y guarda la lista actualizada en el archivo XML
        JAXBContext context = JAXBContext.newInstance(UsuariosList.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(wrapper, new File(FILE_PATH)); //Guarda los cambios en XML
    }

    //Elimina un usuario del archivo XML
    public void deleteUsuario(String nombre) throws JAXBException {
        List<Usuarios> usuarios = getUsuarios();  //Obtiene la lista de usuarios desde el archivo XML

        //Busca el usuario con el nombre especificado
        for (Usuarios usuario : usuarios) {
            if (usuario.getNombre().equals(nombre)) {
                usuarios.remove(usuario);  //Elimina el usuario encontrado
                break;
            }
        }

        //Escribe la lista actualizada en el archivo XML
        UsuariosList wrapper = new UsuariosList(usuarios);
        JAXBContext context = JAXBContext.newInstance(UsuariosList.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(wrapper, new File(FILE_PATH));  //Guarda los cambios en XML
    }
}
