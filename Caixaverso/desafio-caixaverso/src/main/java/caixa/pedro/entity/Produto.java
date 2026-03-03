package caixa.pedro.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "produtos")
@Getter
@Setter
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @SequenceGenerator(name = "produtos", sequenceName = "produtos_id_seq", allocationSize = 1)
    private Integer id;

    private String nome;
    private String tipoProduto;
    private Float rentabilidadeAnual;
    private String risco;
    private Integer prazoMinMeses;
    private Integer prazoMaxMeses;
    private Float valorMin;
    private Float valorMax;


    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipoProduto='" + tipoProduto + '\'' +
                ", rentabilidadeAnual=" + rentabilidadeAnual +
                ", risco='" + risco +
                ", prazoMinMeses=" + prazoMinMeses +
                ", prazoMaxMeses=" + prazoMaxMeses +
                ", valorMin=" + valorMin +
                ", valorMax=" + valorMax +
                '}';
    }
}

