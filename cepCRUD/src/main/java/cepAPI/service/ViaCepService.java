package cepAPI.service;

import cepAPI.dto.response.AddressResponse;
import cepAPI.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ViaCepService {
    private final RestTemplate restTemplate;

    public AddressResponse consultarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        ResponseEntity<AddressResponse> response = restTemplate.getForEntity(url, AddressResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null
                && response.getBody().getCep() != null) {
            return response.getBody();
        }
        throw new BusinessException("CEP não encontrado ou serviço indisponível");
    }
}
