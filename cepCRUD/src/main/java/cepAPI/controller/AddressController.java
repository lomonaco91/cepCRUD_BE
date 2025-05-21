package cepAPI.controller;

import cepAPI.dto.request.AddressRequest;
import cepAPI.dto.response.AddressResponse;
import cepAPI.service.AddressService;
import cepAPI.service.ViaCepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@CrossOrigin(origins = "http://localhost:4200") //Enable dev mode
@Tag(name = "Address Management", description = "Endpoints para gerenciamento de endereços.")
public class AddressController {
    private final AddressService addressService;
    private final ViaCepService viaCepService;

    @Operation(summary = "Listar endereços paginados", description = "Retorna uma lista paginada de endereços")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<Page<AddressResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "logradouro") String sortBy) {
        return ResponseEntity.ok(addressService.getAll(page, size, sortBy));
    }

    @Operation(summary = "Buscar endereço por ID", description = "Retorna um endereço específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getById(id));
    }

    @Operation(summary = "Listar endereços por usuário", description = "Retorna todos endereços de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponse>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.getByUser(userId));
    }

    @Operation(summary = "Criar novo endereço", description = "Cria um novo endereço, com opção de buscar dados do CEP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
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

    @Operation(summary = "Atualizar endereço", description = "Atualiza um endereço existente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AddressRequest request) {
        return ResponseEntity.ok(addressService.update(id, request));
    }

    @Operation(summary = "Excluir endereço", description = "Remove um endereço pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {
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