package demo.grandao.Modelo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement

public class UsuariosList {

    private List<Usuarios> usuarios;

    // Constructor vacío
    public UsuariosList() {}

    // Constructor con lista de usuarios
    public UsuariosList(List<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }

    @XmlElement(name = "usuario")
    public List<Usuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }

}
