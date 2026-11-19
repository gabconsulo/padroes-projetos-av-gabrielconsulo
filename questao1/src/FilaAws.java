import java.math.BigDecimal;

public final class FilaAws implements Fila {
    public static final String PREFIXO = "sqs-";
    public static final BigDecimal CUSTO_MENSAL = new BigDecimal("0.40");

    private final String id;

    public FilaAws(int numero) {
        this.id = PREFIXO + numero;
    }

    @Override
    public String getId() { return id; }

    @Override
    public BigDecimal getCustoMensal() { return CUSTO_MENSAL; }
}
