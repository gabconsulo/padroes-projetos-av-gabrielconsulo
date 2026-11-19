import java.math.BigDecimal;
import java.util.Objects;

// Tabela de preços de um provedor. Concentra a fórmula do custo mensal (RF04).
public final class Precos {
    public static final int HORAS_POR_MES = 730;

    private final BigDecimal custoPorHoraMaquina;
    private final BigDecimal custoPorGb;
    private final BigDecimal custoFila;

    public Precos(BigDecimal custoPorHoraMaquina, BigDecimal custoPorGb, BigDecimal custoFila) {
        this.custoPorHoraMaquina = Objects.requireNonNull(custoPorHoraMaquina);
        this.custoPorGb = Objects.requireNonNull(custoPorGb);
        this.custoFila = Objects.requireNonNull(custoFila);
    }

    public static Precos dos(MaquinaVirtual maquina, Armazenamento armazenamento, Fila fila) {
        return new Precos(maquina.getCustoPorHora(), armazenamento.getCustoPorGb(), fila.getCustoMensal());
    }

    public BigDecimal custoMensal(int quantidadeMaquinas, int gbArmazenamento) {
        BigDecimal maquinas = custoPorHoraMaquina
                .multiply(BigDecimal.valueOf(quantidadeMaquinas))
                .multiply(BigDecimal.valueOf(HORAS_POR_MES));
        BigDecimal armazenamento = custoPorGb.multiply(BigDecimal.valueOf(gbArmazenamento));
        return maquinas.add(armazenamento).add(custoFila);
    }
}
