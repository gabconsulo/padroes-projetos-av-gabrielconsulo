import java.math.BigDecimal;

public final class EntregaExpressa extends ServicoAdicional {
    public static final BigDecimal CUSTO = new BigDecimal("450.00");

    public EntregaExpressa(Fatura fatura) {
        super(fatura);
    }

    @Override
    protected String getDescricaoServico() {
        return "Entrega expressa";
    }

    @Override
    protected BigDecimal calcularCusto(BigDecimal valorVenda) {
        return CUSTO;
    }
}
