import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

// ConcreteComponent: a fatura básica, apenas com o valor de venda do lote.
public final class FaturaVenda implements Fatura {
    private final Venda venda;

    public FaturaVenda(Venda venda) {
        this.venda = Objects.requireNonNull(venda, "A venda é obrigatória.");
    }

    @Override
    public BigDecimal getValorVenda() {
        return venda.valorVenda();
    }

    @Override
    public List<ItemFatura> getItens() {
        return List.of(new ItemFatura("Lote '" + venda.nomeLote() + "' - valor de venda", venda.valorVenda()));
    }

    @Override
    public BigDecimal getTotal() {
        return venda.valorVenda();
    }
}
