package demo.grandao.Repositories;

import demo.grandao.Modelo.Ciudad;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CiudadRepository extends MongoRepository<Ciudad, String> {
}
