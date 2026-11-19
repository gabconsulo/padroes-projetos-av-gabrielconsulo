import java.math.BigDecimal;

public final class CertificadoAutenticidade extends ServicoAdicional {
    public static final BigDecimal CUSTO = new BigDecimal("300.00");

    public CertificadoAutenticidade(Fatura fatura) {
        super(fatura);
    }

    @Override
    protected String getDescricaoServico() {
        return "Certificado de autenticidade";
    }

    @Override
    protected BigDecimal calcularCusto(BigDecimal valorVenda) {
        return CUSTO;
    }
}
