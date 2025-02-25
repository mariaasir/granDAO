package demo.grandao.Repositories;

import demo.grandao.Modelo.Libros;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libros, Integer> {
}
