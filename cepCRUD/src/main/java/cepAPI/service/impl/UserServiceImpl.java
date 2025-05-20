package cepAPI.service.impl;

import cepAPI.dto.request.UserRequest;
import cepAPI.dto.response.UserResponse;
import cepAPI.exception.BusinessException;
import cepAPI.exception.ResourceNotFoundException;
import cepAPI.model.User;
import cepAPI.repository.UserRepository;
import cepAPI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse save(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new BusinessException("Email já está em uso");
        }

        User user = modelMapper.map(userRequest, User.class);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user = userRepository.save(user);

        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public Page<UserResponse> getAll(int page, int size, String sortBy) {
        Page<User> users = userRepository.findAll(
                PageRequest.of(page, size, Sort.by(sortBy)));
        return users.map(user -> modelMapper.map(user, UserResponse.class));
    }

    @Override
    public UserResponse update(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!user.getEmail().equals(userRequest.getEmail()) &&
                userRepository.existsByEmail(userRequest.getEmail())) {
            throw new BusinessException("Email já está em uso");
        }

        modelMapper.map(userRequest, user);
        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
        user = userRepository.save(user);

        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
        userRepository.deleteById(id);
    }
}