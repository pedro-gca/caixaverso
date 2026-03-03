package caixa.pedro.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimulacaoRequest {
    private String clienteId;
    private Float valor;
    private Integer prazoMeses;
    private String tipoProduto;
}
