package caixa.pedro.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class SimulacaoResourceTest {

    // Teste para o cálculo do valor final da simulação
    @Test
    void testValor() {
        assertEquals(11268.25f, SimulacaoResource.calcularValorFinal(10000f, 0.12f, 12), 0.01f);
        assertEquals(0, SimulacaoResource.calcularValorFinal(0f, 0.12f, 12), 0.01f);
        assertEquals(10000f, SimulacaoResource.calcularValorFinal(10000f, 0f, 12), 0.01f);
        assertEquals(10000f, SimulacaoResource.calcularValorFinal(10000f, 0.5f, 0), 0.01f);
    }

    // Teste para o endpoint GET /simulacoes/all
    @Test
    void testGetTodosProdutos() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/simulacoes/all")
                .then()
                .statusCode(200)
                .body(containsString("CDB teste"));
    }

    // Teste para o endpoint POST /simulacoes
    @Test
    void testSimularProduto1() {
        String requestJson = """
        {
            "clienteId": "12345678900",
            "valor": 10000.0,
            "prazoMeses": 12,
            "tipoProduto": "CDB"
        }
        """;

        given()
            .contentType(ContentType.JSON)  // This sets Content-Type header
            .accept(ContentType.JSON)        // This sets Accept header
            .body(requestJson)
            .when()
            .post("/simulacoes")
            .then()
            .statusCode(200);


    String requestJsonInvalido = """
        {
            "clienteId": "",
            "valor": -1.0,
            "prazoMeses": -1,
            "tipoProduto": ""
        }
        """;

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body(requestJsonInvalido)
        .when()
        .post("/simulacoes")
        .then()
        .statusCode(422);
    }

    // Teste para o endpoint GET /simulacoes?clienteId=1
     @Test
    void testGetSimulacoesByClienteId() {

         given()
                 .accept(ContentType.JSON)
                 .when()
                 .get("/simulacoes?clienteId=1")
                 .then()
                 .statusCode(200);
     }

}