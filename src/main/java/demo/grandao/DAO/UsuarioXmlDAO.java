package demo.grandao.DAO;

import demo.grandao.Modelo.Usuarios;
import demo.grandao.Modelo.UsuariosList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

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
}

