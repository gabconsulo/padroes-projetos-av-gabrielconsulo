import java.math.BigDecimal;

public final class ArmazenamentoAzure implements Armazenamento {
    public static final String PREFIXO = "blob://";
    public static final BigDecimal CUSTO_POR_GB = new BigDecimal("0.02");

    private final String id;

    public ArmazenamentoAzure(int numero) {
        this.id = PREFIXO + numero;
    }

    @Override
    public String getId() { return id; }

    @Override
    public BigDecimal getCustoPorGb() { return CUSTO_POR_GB; }
}
