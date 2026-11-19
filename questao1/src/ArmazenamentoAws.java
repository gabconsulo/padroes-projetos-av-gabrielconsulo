import java.math.BigDecimal;

public final class ArmazenamentoAws implements Armazenamento {
    public static final String PREFIXO = "s3://";
    public static final BigDecimal CUSTO_POR_GB = new BigDecimal("0.023");

    private final String id;

    public ArmazenamentoAws(int numero) {
        this.id = PREFIXO + numero;
    }

    @Override
    public String getId() { return id; }

    @Override
    public BigDecimal getCustoPorGb() { return CUSTO_POR_GB; }
}
