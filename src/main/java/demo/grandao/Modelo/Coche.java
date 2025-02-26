package demo.grandao.Modelo;

import java.io.Serializable;

public class Coche implements Serializable {
    private String matricula;
    private String marca;
    private String modelo;

    // Constructor vacío
    public Coche() {}

    // Constructor con parámetros
    public Coche(String matricula, String marca, String modelo) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
    }

    // Getters y setters
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return matricula + "," + marca + "," + modelo;
    }

    // Metodo para crear un objeto Coche desde una línea de texto
    public static Coche fromString(String linea) {
        String[] datos = linea.split(",");
        if (datos.length == 3) {
            return new Coche(datos[0], datos[1], datos[2]);
        }
        return null;
    }
}
