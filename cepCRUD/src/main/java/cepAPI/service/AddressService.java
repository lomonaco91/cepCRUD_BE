package cepAPI.service;

import cepAPI.dto.request.AddressRequest;
import cepAPI.dto.response.AddressResponse;
import org.springframework.data.domain.Page;
import java.util.List;

public interface AddressService {
    AddressResponse save(AddressRequest addressRequest);
    AddressResponse getById(Long id);
    List<AddressResponse> getByUser(Long userId);
    Page<AddressResponse> getAll(int page, int size, String sortBy);
    AddressResponse update(Long id, AddressRequest addressRequest);
    void delete(Long id);
}