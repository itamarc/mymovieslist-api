package io.itamarc.mymovieslistapi.repositories;

import org.springframework.data.repository.CrudRepository;

import io.itamarc.mymovieslistapi.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
