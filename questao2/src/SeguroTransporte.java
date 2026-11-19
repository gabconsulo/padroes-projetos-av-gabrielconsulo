import java.math.BigDecimal;
import java.math.RoundingMode;

public final class SeguroTransporte extends ServicoAdicional {
    public static final BigDecimal PERCENTUAL = new BigDecimal("0.015");

    public SeguroTransporte(Fatura fatura) {
        super(fatura);
    }

    @Override
    protected String getDescricaoServico() {
        return "Seguro de transporte (1,5% do valor de venda)";
    }

    @Override
    protected BigDecimal calcularCusto(BigDecimal valorVenda) {
        return valorVenda.multiply(PERCENTUAL).setScale(2, RoundingMode.HALF_UP);
    }
}
