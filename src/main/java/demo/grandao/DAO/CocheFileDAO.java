package demo.grandao.DAO;

import demo.grandao.Modelo.Usuarios;
import org.springframework.stereotype.Repository;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import demo.grandao.Modelo.Coche;

@Repository
public class CocheFileDAO {

    private static final String FILE_PATH = "coches.dat"; //Cambiado a .dat para evitar confusiones

    //Guardar una lista de coches en el fichero
    public void guardarCoches(List<Coche> coches) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(coches); // Escribe la lista en el archivo
        }
    }

    //Añadir un coche a la lista existente
    public void insertarCoche(Coche coche) throws IOException, ClassNotFoundException {
        List<Coche> coches = leerCoches(); //Lee la lista existente
        coches.add(coche); //Añade el nuevo coche
        guardarCoches(coches); //Guarda la lista actualizada
    }

    //Leer la lista de coches desde el fichero
    public List<Coche> leerCoches() throws IOException, ClassNotFoundException {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>(); //Si el archivo no existe o está vacío, devuelve una lista vacía
        }
        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            return (List<Coche>) objectIn.readObject();
        }
    }


    //Buscar un coche por matrícula
    public Coche buscarCochePorMatricula(String matricula) throws IOException, ClassNotFoundException {
        List<Coche> coches = leerCoches(); //Lee la lista de coches
        Coche cocheEncontrado = new Coche();
        for (Coche coche : coches) {
            if (coche.getMatricula().equals(matricula)) {
                cocheEncontrado = coche;
            }
        }
        return cocheEncontrado;
    }
}

