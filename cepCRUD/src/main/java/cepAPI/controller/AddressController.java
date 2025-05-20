package cepAPI.controller;

import cepAPI.dto.request.AddressRequest;
import cepAPI.dto.response.AddressResponse;
import cepAPI.service.AddressService;
import cepAPI.service.ViaCepService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") //Enable CORS in dev mode
public class AddressController {
    private final AddressService addressService;
    private final ViaCepService viaCepService;

    @GetMapping
    public ResponseEntity<Page<AddressResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "logradouro") String sortBy) {
        return ResponseEntity.ok(addressService.getAll(page, size, sortBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponse>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.getByUser(userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AddressResponse> create(
            @Valid @RequestBody AddressRequest request,
            @RequestParam(required = false) boolean fetchCepData) {

        if (fetchCepData) {
            AddressResponse cepData = viaCepService.consultarCep(request.getCep());
            request = mergeAddressData(request, cepData);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AddressRequest request) {
        return ResponseEntity.ok(addressService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private AddressRequest mergeAddressData(AddressRequest request, AddressResponse cepData) {
        return AddressRequest.builder()
                .logradouro(cepData.getLogradouro())
                .numero(request.getNumero())
                .complemento(request.getComplemento())
                .bairro(cepData.getBairro())
                .localidade(cepData.getLocalidade())
                .uf(cepData.getUf())
                .cep(cepData.getCep())
                .userId(request.getUserId())
                .build();
    }
}