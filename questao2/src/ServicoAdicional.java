import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Decorator: embrulha uma Fatura e acrescenta a descrição e o custo do serviço.
public abstract class ServicoAdicional implements Fatura {
    private final Fatura fatura;

    protected ServicoAdicional(Fatura fatura) {
        this.fatura = Objects.requireNonNull(fatura, "A fatura é obrigatória.");
    }

    protected abstract String getDescricaoServico();

    protected abstract BigDecimal calcularCusto(BigDecimal valorVenda);

    @Override
    public BigDecimal getValorVenda() {
        return fatura.getValorVenda();
    }

    @Override
    public List<ItemFatura> getItens() {
        List<ItemFatura> itens = new ArrayList<>(fatura.getItens());
        itens.add(new ItemFatura(getDescricaoServico(), calcularCusto(fatura.getValorVenda())));
        return List.copyOf(itens);
    }

    @Override
    public BigDecimal getTotal() {
        return fatura.getTotal().add(calcularCusto(fatura.getValorVenda()));
    }
}
