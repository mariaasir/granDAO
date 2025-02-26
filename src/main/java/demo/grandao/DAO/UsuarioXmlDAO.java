package demo.grandao.DAO;

import demo.grandao.Modelo.Usuarios;
import demo.grandao.Modelo.UsuariosList;
import org.springframework.stereotype.Repository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

 @Repository
public class UsuarioXmlDAO {

    private static final String ARCHIVO_XML = "usuarios.xml";  // Nombre del archivo XML

    public void guardarUsuarios(List<Usuarios> usuarios) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(UsuariosList.class); // "UsuariosWrapper" es una clase auxiliar
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(new UsuariosList(usuarios), new File(ARCHIVO_XML));
    }

    public List<Usuarios> leerUsuarios() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(UsuariosList.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        UsuariosList wrapper = (UsuariosList) unmarshaller.unmarshal(new File(ARCHIVO_XML));
        return wrapper.getUsuarios();
    }

    public List<Usuarios> leerUsuariosPorNombre(String nombre) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(UsuariosList.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        UsuariosList wrapper = (UsuariosList) unmarshaller.unmarshal(new File(ARCHIVO_XML));

        List<Usuarios> usuariosFiltrados = new ArrayList<Usuarios>();
        for (Usuarios usuario : wrapper.getUsuarios()) {
            if (usuario.getNombre().equals(nombre)) {
                usuariosFiltrados.add(usuario);
            }
        }
        return usuariosFiltrados;
    }

    // Actualizar un usuario en el XML
    public void updateUsuario(String nombre, Usuarios usuarioNuevo) throws JAXBException {
        List<Usuarios> usuarios = leerUsuarios();

        for (Usuarios usuario : usuarios) {
            if (usuario.getNombre().equals(nombre)) {
                usuario.setNombre(usuarioNuevo.getNombre());
                usuario.setPassword(usuarioNuevo.getPassword());
                guardarUsuarios(usuarios); //Guarda la lista actualizada
                return;
            }
        }
    }

    public void deleteUsuario(String nombre) throws JAXBException {
        List<Usuarios> usuarios = leerUsuarios();
        usuarios.removeIf(usuario -> usuario.getNombre().equals(nombre));
        guardarUsuarios(usuarios); //Guarda la lista actualizada
    }


}

