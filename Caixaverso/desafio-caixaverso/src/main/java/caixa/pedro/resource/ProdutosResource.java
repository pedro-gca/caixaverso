package caixa.pedro.resource;

import caixa.pedro.entity.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutosResource {

    @PersistenceContext
    EntityManager em;

    @GET
    public List<Produto> getProdutos() {
        return em.createQuery("from Produto order by id", Produto.class).getResultList();
    }

    @GET
    @Path("/{id}")
    public Produto getProdutoById(@PathParam("id") Long id) {
        Produto produto = em.find(Produto.class, id);
        if (produto == null) {
            throw new NotFoundException("Produto with id " + id + " not found");
        }
        return produto;
    }

    @GET
    @Path("/tipo/{tipoProduto}")
    public List<Produto> getProdutosByTipo(@PathParam("tipoProduto") String tipoProduto) {
        return em.createQuery("from Produto where tipoProduto = :tipo order by id", Produto.class)
                .setParameter("tipo", tipoProduto)
                .getResultList();
    }

    @POST
    @Transactional
    public Response createProduto(Produto produto) {
        if (produto.getNome() == null || produto.getTipoProduto() == null || produto.getRentabilidadeAnual() == null ||
                produto.getPrazoMinMeses() == null || produto.getPrazoMaxMeses() == null ||
                produto.getValorMin() == null || produto.getValorMax() == null) {
            return Response.status(422).entity("Todos os campos são obrigatórios").build();
        }
        em.persist(produto);
        return Response.ok(produto.toString()).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateProduto(@PathParam("id") Long id, Produto updatedProduto) {
        Produto produto = em.find(Produto.class, id);
        if (produto == null) {
            return Response.status(422).entity("Produto with id " + id + " not found").build();
        }

        if (updatedProduto.getNome() != null) produto.setNome(updatedProduto.getNome());
        if (updatedProduto.getTipoProduto() != null) produto.setTipoProduto(updatedProduto.getTipoProduto());
        if (updatedProduto.getRentabilidadeAnual() != null) produto.setRentabilidadeAnual(updatedProduto.getRentabilidadeAnual());
        if (updatedProduto.getPrazoMinMeses() != null) produto.setPrazoMinMeses(updatedProduto.getPrazoMinMeses());
        if (updatedProduto.getPrazoMaxMeses() != null) produto.setPrazoMaxMeses(updatedProduto.getPrazoMaxMeses());
        if (updatedProduto.getValorMin() != null) produto.setValorMin(updatedProduto.getValorMin());
        if (updatedProduto.getValorMax() != null) produto.setValorMax(updatedProduto.getValorMax());

        em.merge(produto);
        return Response.ok(produto.toString()).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteProduto(@PathParam("id") Long id) {
        Produto produto = em.find(Produto.class, id);
        if (produto == null) {
            throw new NotFoundException("Produto with id " + id + " not found");
        }
        em.remove(produto);
    }
}
