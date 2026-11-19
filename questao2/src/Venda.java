import java.math.BigDecimal;

public record Venda(String nomeLote, String categoria, String comprador, BigDecimal valorVenda, BigDecimal comissao) {
    public BigDecimal valorLiquido() {
        return valorVenda.subtract(comissao);
    }
}
