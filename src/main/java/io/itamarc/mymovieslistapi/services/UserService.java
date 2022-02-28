package io.itamarc.mymovieslistapi.services;

import java.util.Set;

import io.itamarc.mymovieslistapi.transfer.UserPayload;

public interface UserService {
    public Set<UserPayload> findAll();

    public UserPayload findById(Long id);
}
