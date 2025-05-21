package cepAPI.service.impl;

import cepAPI.config.JwtService;
import cepAPI.dto.request.AddressRequest;
import cepAPI.dto.response.AddressResponse;
import cepAPI.exception.ResourceNotFoundException;
import cepAPI.model.Address;
import cepAPI.model.User;
import cepAPI.repository.AddressRepository;
import cepAPI.repository.UserRepository;
import cepAPI.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    JwtService jwtService = null;

    @Override
    public AddressResponse save(AddressRequest addressRequest) {
        User user = userRepository.findById(addressRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Address address = modelMapper.map(addressRequest, Address.class);
        address.setUser(user);
        address = addressRepository.save(address);

        return modelMapper.map(address, AddressResponse.class);
    }

    @Override
    public AddressResponse getById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
        return modelMapper.map(address, AddressResponse.class);
    }

    @Override
    public List<AddressResponse> getByUser(Long userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<AddressResponse> getAll(int page, int size, String sortBy) {
        Page<Address> addresses = addressRepository.findAll(
                PageRequest.of(page, size, Sort.by(sortBy)));
        return addresses.map(address -> modelMapper.map(address, AddressResponse.class));
    }

    @Override
    public AddressResponse update(Long id, AddressRequest addressRequest) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));

        if (!address.getUser().getId().equals(addressRequest.getUserId())) {
            User user = userRepository.findById(addressRequest.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
            address.setUser(user);
        }

        modelMapper.map(addressRequest, address);
        address = addressRepository.save(address);

        return modelMapper.map(address, AddressResponse.class);
    }

    @Override
    public void delete(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Endereço não encontrado");
        }
        addressRepository.deleteById(id);
    }

}
