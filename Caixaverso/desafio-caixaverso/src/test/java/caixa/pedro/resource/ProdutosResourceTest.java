package caixa.pedro.resource;

import caixa.pedro.entity.Produto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ProdutosResourceTest {

    @PersistenceContext
    EntityManager em;

    // Teste para o endpoint GET /produtos
    @Test
    void testGetProdutos() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/produtos")
                .then()
                .statusCode(200)
                .body(containsString("CDB Caixa"));
    }

    // Teste para o endpoint GET /produtos/{id}
    @Test
    void testGetProdutoById() {
        var produto = em.find(caixa.pedro.entity.Produto.class, 1L);
        assertNotNull(produto, "Produto com ID 1 deve existir");

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/produtos/1")
                .then()
                .statusCode(200)
                .body(containsString("CDB Caixa"));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/produtos/-1")
                .then()
                .statusCode(404);


    }

    // Teste para o endpoint GET /produtos/tipo/{tipoProduto}
    @Test
    void testGetProdutoByTipo() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/produtos/tipo/CDB")
                .then()
                .statusCode(200)
                .body(containsString("CDB Caixa"));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/produtos/tipo/TipoInexistente")
                .then()
                .statusCode(200)
                .body(containsString("[]"));
    }

    // Teste para o endpoint POST /produtos
    @Test
    void testCreateProduto() {
        Produto produto = new Produto();
        produto.setNome("Teste Produto");
        produto.setTipoProduto("Teste");
        produto.setRentabilidadeAnual(0.1f);
        produto.setRisco("Médio");
        produto.setPrazoMinMeses(6);
        produto.setPrazoMaxMeses(24);
        produto.setValorMin(1000f);
        produto.setValorMax(10000f);

        given()
                .contentType("application/json")
                .body(produto)
                .when()
                .post("/produtos")
                .then()
                .statusCode(200);

        given()
                .contentType("application/json")
                .body(new Produto()) // Enviando um produto vazio para testar validação
                .when()
                .post("/produtos")
                .then()
                .statusCode(422);

    }

    // Teste para o endpoint PUT /produtos/{id}
    @Test
    void testUpdateProduto() {
        Produto produto = em.find(Produto.class, 2L);
        assertNotNull(produto, "Produto com ID 2 deve existir");

        produto.setNome("LCI Caixa Atualizado");
        given()
                .contentType("application/json")
                .body(produto)
                .when()
                .put("/produtos/2")
                .then()
                .statusCode(200);

        Produto produtoAtualizado = em.find(Produto.class, 2L);
        assertEquals("LCI Caixa Atualizado", produtoAtualizado.getNome(), "O nome do produto deve ter sido atualizado");

        // Testar atualização de um produto inexistente
        given()
                .contentType("application/json")
                .body(produto)
                .when()
                .put("/produtos/-1")
                .then()
                .statusCode(422);
    }

    // Teste para o endpoint DELETE /produtos/{id}
    @Test
    void testDeleteProduto() {
        given()
                .when()
                .delete("/produtos/3")
                .then()
                .statusCode(204);
    }

}