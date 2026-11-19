import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

// Monta o ambiente a partir de uma única fábrica recebida de fora: não conhece nenhuma classe
// concreta de provedor e não decide o provedor (sem if/switch). Como os três recursos saem
// da mesma fábrica, é impossível combinar provedores diferentes.
public final class Ambiente {
    private final String nome;
    private final String provedor;
    private final String regiao;
    private final int quantidadeMaquinas;
    private final int gbArmazenamento;
    private final MaquinaVirtual maquinas;
    private final Armazenamento armazenamento;
    private final Fila fila;

    public Ambiente(String nome, FabricaNuvem fabrica, int quantidadeMaquinas, int gbArmazenamento) {
        Objects.requireNonNull(nome, "O nome do ambiente é obrigatório.");
        Objects.requireNonNull(fabrica, "A fábrica é obrigatória.");
        this.nome = nome;
        this.provedor = fabrica.getNomeProvedor();
        this.regiao = Configuracao.getInstance().getRegiaoPadrao();
        this.quantidadeMaquinas = quantidadeMaquinas;
        this.gbArmazenamento = gbArmazenamento;
        this.maquinas = fabrica.criarMaquinaVirtual();
        this.armazenamento = fabrica.criarArmazenamento();
        this.fila = fabrica.criarFila();
    }

    public BigDecimal getCustoMensal() {
        return Precos.dos(maquinas, armazenamento, fila)
                .custoMensal(quantidadeMaquinas, gbArmazenamento)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public String getNome() {
        return nome;
    }

    public String getProvedor() {
        return provedor;
    }

    // Texto único para que a impressão de threads diferentes não se misture.
    public String resumo() {
        String quebra = System.lineSeparator();
        return "=== Ambiente '" + nome + "' provisionado ===" + quebra
                + "Provedor: " + provedor + " | Região: " + regiao + quebra
                + "Máquinas virtuais (" + quantidadeMaquinas + "): " + maquinas.getId() + quebra
                + "Armazenamento (" + gbArmazenamento + " GB): " + armazenamento.getId() + quebra
                + "Fila de mensagens: " + fila.getId() + quebra
                + "Custo mensal estimado: " + Moeda.formatar(getCustoMensal()) + quebra;
    }
}
