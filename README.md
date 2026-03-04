# Desafio Caixaverso
Aplicação para gerenciamento de simulações de produtos financeiros.

## Tecnologias
- Java 21
- Quarkus
- Maven

## Como rodar
- Clone o repositório
- Entre na pasta, compile e entre no modo desenvolvimento do maven
```bash
git clone https://github.com/pedro-gca/caixaverso.git
cd desafio-caixaverso
mvnw quarkus:dev
```  

## Como rodar os testes
- Após clonar, usar o comando
```
mvnw test
```

- A aplicação estará disponível em http://localhost:8080

## Endpoints
### Produtos
- GET /produtos - Listar todos os produtos
- GET /produtos/{id} - Obter produto por ID
- GET /produtos/tipo/{tipoProduto} - Listar por tipo
- POST /produtos - Criar novo produto
- PUT /produtos/{id} - Atualizar produto
- DELETE /produtos/{id} - Deletar produto

### Simulações
- GET /simulacoes - Listar todas as simulações
- GET /simulacoes?clienteId={clienteId} - Obter simulações por cliente
- POST /simulacoes - Criar nova simulação

As chamadas para os endpoints poderão ser feitas ou via cURL ou via Postman
