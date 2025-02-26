-- Crear la base de datos
CREATE DATABASE librosAutores;
USE librosAutores;

-- Crear la tabla de Autores
CREATE TABLE autores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    nacionalidad VARCHAR(50)
);

-- Insertar datos de prueba en Autores
INSERT INTO autores (nombre, nacionalidad) VALUES
('Gabriel García Márquez', 'Colombiana'),
('Miguel de Cervantes', 'Española'),
('J.K. Rowling', 'Británica');

-- Crear la tabla de Libros
CREATE TABLE libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    anio_publicacion INT,
    autor_id INT,
    FOREIGN KEY (autor_id) REFERENCES autores(id) ON DELETE CASCADE
);

-- Insertar datos de prueba en Libros
INSERT INTO libros (titulo, anio_publicacion, autor_id) VALUES
('Cien años de soledad', 1967, 1),
('El amor en los tiempos del cólera', 1985, 1),
('Don Quijote de la Mancha', 1605, 2),
('Harry Potter y la piedra filosofal', 1997, 3);
