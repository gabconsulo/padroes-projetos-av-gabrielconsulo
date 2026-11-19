import java.math.BigDecimal;

public final class MaquinaVirtualAws implements MaquinaVirtual {
    public static final String PREFIXO = "i-";
    public static final BigDecimal CUSTO_POR_HORA = new BigDecimal("0.10");

    private final String id;

    public MaquinaVirtualAws(int numero) {
        this.id = PREFIXO + numero;
    }

    @Override
    public String getId() { return id; }

    @Override
    public BigDecimal getCustoPorHora() { return CUSTO_POR_HORA; }
}
