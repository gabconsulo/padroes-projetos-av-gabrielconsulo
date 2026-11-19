// Observer: qualquer parte que acompanha um lote. Novos tipos de interessado só implementam esta interface.
public interface Interessado {
    void atualizar(Lance lance);
}
