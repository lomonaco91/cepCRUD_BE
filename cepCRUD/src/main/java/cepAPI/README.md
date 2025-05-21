# Sistema de Controle de Endereços

<img src="https://img.shields.io/badge/Java-17-blue" alt="Java 17"> <img src="https://img.shields.io/badge/Spring_Boot-3.3.4-brightgreen" alt="Spring Boot 3.3.4"> <img src="https://img.shields.io/badge/PostgreSQL-16.2-blue" alt="PostgreSQL Database">

## 📋 Visão Geral
API back-end para gerenciamento de endereços.

## ✨ Funcionalidades
- Gerenciamento de endereços
- Documentação interativa via Swagger UI
- Banco de dados PostreSQL

## 🚀 Tecnologias Principais
| Camada         | Tecnologias                 |
|----------------|-----------------------------|
| **Backend**    | Java 17, Spring Boot 3.3.4  |
| **Persistência**| Spring Data JPA, PostgreSQL |
| **Documentação**| SpringDoc OpenAPI 2.5.0     |
| **Validação**  | Bean Validation 3.0         |
| **Ferramentas**| Maven, Lombok, Postman      |


### `src/main/resources/`

- **`application.properties`**: Arquivo de configuração da aplicação onde são definidos parâmetros de configuração do Spring Boot, como conexão com o banco de dados, portas, etc.

## Considerações Finais

Esta estrutura busca manter o código bem organizado e dividido em camadas, facilitando a manutenção e a escalabilidade. Seguindo essa arquitetura, a aplicação pode ser facilmente expandida com novos recursos sem comprometer a clareza do código.

### Princípios Arquiteturais
- **Clean Architecture**: Separação das camadas
- **SOLID**: Princípios aplicados nos serviços
- **RESTful**: Design de API seguindo boas práticas

## 🔧 Pré-requisitos
- JDK 17+
- Maven 3.9+
- Postman (opcional para testes)

## 🛠 Instalação e Execução

1. **Construção do projeto:**
   ```bash
   mvn clean install

2. **Execução da aplicação:**
   ```bash
   mvn spring-boot:run

# Licença
```text
MIT License

Copyright (c) 2025 - Fabiano Júnior

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
IN THE SOFTWARE.
```
