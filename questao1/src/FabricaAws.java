import java.util.concurrent.atomic.AtomicInteger;

public final class FabricaAws implements FabricaNuvem {
    private final AtomicInteger sequenciaMaquina = new AtomicInteger();
    private final AtomicInteger sequenciaArmazenamento = new AtomicInteger();
    private final AtomicInteger sequenciaFila = new AtomicInteger();

    @Override
    public String getNomeProvedor() { return "Aws"; }

    @Override
    public MaquinaVirtual criarMaquinaVirtual() {
        return new MaquinaVirtualAws(sequenciaMaquina.incrementAndGet());
    }

    @Override
    public Armazenamento criarArmazenamento() {
        return new ArmazenamentoAws(sequenciaArmazenamento.incrementAndGet());
    }

    @Override
    public Fila criarFila() {
        return new FilaAws(sequenciaFila.incrementAndGet());
    }

    @Override
    public Precos getPrecos() {
        return new Precos(MaquinaVirtualAws.CUSTO_POR_HORA, ArmazenamentoAws.CUSTO_POR_GB, FilaAws.CUSTO_MENSAL);
    }
}
