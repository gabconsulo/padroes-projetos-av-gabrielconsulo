import java.math.BigDecimal;

public final class EmbalagemEspecial extends ServicoAdicional {
    public static final BigDecimal CUSTO = new BigDecimal("180.00");

    public EmbalagemEspecial(Fatura fatura) {
        super(fatura);
    }

    @Override
    protected String getDescricaoServico() {
        return "Embalagem especial";
    }

    @Override
    protected BigDecimal calcularCusto(BigDecimal valorVenda) {
        return CUSTO;
    }
}
