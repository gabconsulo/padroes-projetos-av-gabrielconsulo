import java.math.BigDecimal;
import java.math.RoundingMode;

public final class ComissaoVeiculos implements RegraComissao {
    public static final BigDecimal PERCENTUAL = new BigDecimal("0.08");
    public static final BigDecimal LIMITE = new BigDecimal("5000.00");

    @Override
    public BigDecimal calcular(BigDecimal valorVenda) {
        return valorVenda.multiply(PERCENTUAL).min(LIMITE).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescricao() {
        return "Veículos (8% do valor, limitada a R$ 5.000,00)";
    }
}
