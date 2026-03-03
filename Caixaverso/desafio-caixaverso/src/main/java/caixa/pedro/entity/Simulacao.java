package caixa.pedro.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "simulacoes")
@Getter
@Setter
public class Simulacao {

    @Id
    @Column(columnDefinition = "auto_increment")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String clienteId;
    private String produtoNome;
    private String tipoProduto;
    private Float valorInvestido;
    private Integer prazoMeses;
    private Float rentabilidadeAplicada;
    private Float valorFinal;
    private String dataSimulacao;

    @Override
    public String toString() {
        return "Simulacao{ " +
                "id=" + id +
                ", clienteId='" + clienteId + '\'' +
                ", produtoNome='" + produtoNome + '\'' +
                ", tipoProduto='" + tipoProduto + '\'' +
                ", valorInvestido=" + valorInvestido +
                ", prazoMeses=" + prazoMeses +
                ", rentabilidadeAplicada=" + rentabilidadeAplicada +
                ", valorFinal=" + valorFinal +
                ", dataSimulacao=" + dataSimulacao +
                '}';
    }
}