package io.itamarc.mymovieslistapi.services;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import io.itamarc.mymovieslistapi.exception.BadRequestException;
import org.springframework.stereotype.Service;

import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.repositories.UserRepository;
import io.itamarc.mymovieslistapi.transfer.UserPayload;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Set<UserPayload> findAll() {
        Set<UserPayload> users = new LinkedHashSet<>();
        userRepository.findAll().forEach(user -> users.add(UserConverter.userToUserPayload(user)));
        return users;
    }

    @Override
    public UserPayload findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return UserConverter.userToUserPayload(user);
        } else {
            return null;
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserPayload save(UserPayload user) {
        Optional<User> userCheck = userRepository.findByEmail(user.getEmail());
        if (userCheck.isPresent()) {
            throw new BadRequestException("User already registered with e-mail '" + user.getEmail() + "'.");
        }
        User userSaved = userRepository.save(UserConverter.userPayloadToUser(user));
        return UserConverter.userToUserPayload(userSaved);
    }
}
