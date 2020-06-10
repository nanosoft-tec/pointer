package br.com.nanosofttecnologia.repository;

import br.com.nanosofttecnologia.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String login);
}
