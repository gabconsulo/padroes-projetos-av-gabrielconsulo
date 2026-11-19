// Categoria do lote. Cada categoria informa sua regra padrão, então o lote não precisa de if/switch
// e uma nova categoria só exige classes novas (a categoria e, se for o caso, a regra).
public interface Categoria {
    String getNome();

    RegraComissao getRegraPadrao();
}
