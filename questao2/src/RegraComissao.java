import java.math.BigDecimal;

// Strategy: cada regra encapsula seu cálculo. O lote delega e pode trocar de regra em tempo de execução.
public interface RegraComissao {
    BigDecimal calcular(BigDecimal valorVenda);

    String getDescricao();
}
