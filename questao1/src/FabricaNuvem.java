// AbstractFactory: cria a família completa de recursos de um único provedor.
public interface FabricaNuvem {
    String getNomeProvedor();
    MaquinaVirtual criarMaquinaVirtual();
    Armazenamento criarArmazenamento();
    Fila criarFila();
    Precos getPrecos();
}
