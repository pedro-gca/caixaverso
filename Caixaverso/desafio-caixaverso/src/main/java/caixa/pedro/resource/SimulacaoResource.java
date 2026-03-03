package caixa.pedro.resource;

import caixa.pedro.dto.SimulacaoRequest;
import caixa.pedro.entity.Produto;
import caixa.pedro.entity.Simulacao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Path("/simulacoes")
@Transactional
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulacaoResource {

    @PersistenceContext
    EntityManager em;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response simularProduto(SimulacaoRequest request) {
        try {
            List<Produto> produtos = em.createQuery("SELECT p FROM Produto p WHERE p.tipoProduto = :tipo", Produto.class)
                    .setParameter("tipo", request.getTipoProduto())
                    .getResultList();

            if (produtos.isEmpty() || request.getValor() < 0 || request.getPrazoMeses() < 0) {
                return Response.status(422).entity("Produto não encontrado ou parâmetros inválidos").build();
            }

            List<Produto> produtosValidados = new ArrayList<>();

            for (Produto p : produtos) {
                System.out.println(p);
                if (p == null) {
                    continue;
                }
                if (request.getValor() > p.getValorMax() || request.getValor() < p.getValorMin() ||
                        request.getPrazoMeses() > p.getPrazoMaxMeses() || request.getPrazoMeses() < p.getPrazoMinMeses()) {
                    continue;
                }

                Simulacao s = new Simulacao();

                Float valorFinal = calcularValorFinal(request.getValor(), p.getRentabilidadeAnual(), request.getPrazoMeses());

                s.setClienteId(request.getClienteId());
                s.setRentabilidadeAplicada(p.getRentabilidadeAnual());
                s.setTipoProduto(request.getTipoProduto());
                s.setProdutoNome(p.getNome());
                s.setValorInvestido(request.getValor());
                s.setPrazoMeses(request.getPrazoMeses());
                s.setValorFinal(valorFinal);
                s.setDataSimulacao(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                em.persist(s);
                produtosValidados.add(p);
            }

            return Response.ok(produtosValidados).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GET
    @Path("/all")
    public Response getSimulacoes() {
        return Response.ok(em.createQuery("SELECT s FROM Simulacao s", Simulacao.class).getResultList()).build();
    }

    @GET
    public Response getSimulacaoByClienteId(@QueryParam("clienteId") String clienteId) {
        List<Simulacao> simulacoes = em.createQuery(
                "SELECT s FROM Simulacao s WHERE s.clienteId = :clienteId", Simulacao.class
        ).setParameter("clienteId", clienteId).getResultList();

        return Response.ok(em.find(Simulacao.class, clienteId).toString()).build();
    }

    public static Float calcularValorFinal(Float valor, Float rentabilidadeAnual, Integer prazoMeses) {
        return (float) (valor * Math.pow(1 + rentabilidadeAnual / 12, prazoMeses));
    }
}
