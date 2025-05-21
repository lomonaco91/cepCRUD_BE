package cepAPI.service;

import cepAPI.dto.request.UserRequest;
import cepAPI.dto.response.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    UserResponse save(UserRequest userRequest);
    UserResponse getById(Long id);
    Page<UserResponse> getAll(int page, int size, String sortBy);
    UserResponse update(Long id, UserRequest userRequest);
    void delete(Long id);
    UserResponse getByEmail(String email);
}