package demo.grandao.Repositories;

import demo.grandao.Modelo.Autores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autores, Integer> {
}
