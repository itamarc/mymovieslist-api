package io.itamarc.mymovieslistapi.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import io.itamarc.mymovieslistapi.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
