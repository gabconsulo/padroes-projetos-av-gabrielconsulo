import java.util.concurrent.atomic.AtomicInteger;

public final class FabricaAzure implements FabricaNuvem {
    private final AtomicInteger sequenciaMaquina = new AtomicInteger();
    private final AtomicInteger sequenciaArmazenamento = new AtomicInteger();
    private final AtomicInteger sequenciaFila = new AtomicInteger();

    @Override
    public String getNomeProvedor() { return "Azure"; }

    @Override
    public MaquinaVirtual criarMaquinaVirtual() {
        return new MaquinaVirtualAzure(sequenciaMaquina.incrementAndGet());
    }

    @Override
    public Armazenamento criarArmazenamento() {
        return new ArmazenamentoAzure(sequenciaArmazenamento.incrementAndGet());
    }

    @Override
    public Fila criarFila() {
        return new FilaAzure(sequenciaFila.incrementAndGet());
    }

    @Override
    public Precos getPrecos() {
        return new Precos(MaquinaVirtualAzure.CUSTO_POR_HORA, ArmazenamentoAzure.CUSTO_POR_GB, FilaAzure.CUSTO_MENSAL);
    }
}
