package io.itamarc.mymovieslistapi.services;

import java.util.Set;

import io.itamarc.mymovieslistapi.model.User;

public interface UserService {
    public Set<User> findAll();

    public User findById(Long id);
}
