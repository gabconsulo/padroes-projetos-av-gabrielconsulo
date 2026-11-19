import java.math.BigDecimal;
import java.math.RoundingMode;

public final class ComissaoEletronicos implements RegraComissao {
    public static final BigDecimal PERCENTUAL = new BigDecimal("0.12");
    public static final BigDecimal MINIMO = new BigDecimal("50.00");

    @Override
    public BigDecimal calcular(BigDecimal valorVenda) {
        return valorVenda.multiply(PERCENTUAL).max(MINIMO).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescricao() {
        return "Eletrônicos (12% do valor, mínimo de R$ 50,00)";
    }
}
