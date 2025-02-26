package demo.grandao.Modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ciudades")
public class Ciudad {
    @Id
    private String id;
    private String nombre;
    private int numeroHabitantes;
    private String pais;

    // Constructores, getters y setters
    public Ciudad() {}

    public Ciudad(String nombre, int numeroHabitantes, String pais) {
        this.nombre = nombre;
        this.numeroHabitantes = numeroHabitantes;
        this.pais = pais;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroHabitantes() {
        return numeroHabitantes;
    }

    public void setNumeroHabitantes(int numeroHabitantes) {
        this.numeroHabitantes = numeroHabitantes;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}