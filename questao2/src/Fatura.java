import java.math.BigDecimal;
import java.util.List;

// Component do Decorator: a venda pura e a venda com qualquer número de serviços são tratadas igual.
public interface Fatura {
    BigDecimal getValorVenda();

    List<ItemFatura> getItens();

    BigDecimal getTotal();
}
