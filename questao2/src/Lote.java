import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// Subject do Observer (conhece apenas Interessado e Licitante) e Context do Strategy (RegraComissao).
public final class Lote {
    private static final BigDecimal FATOR_LANCE_MINIMO = new BigDecimal("1.05");

    private final String nome;
    private final Categoria categoria;
    private final BigDecimal precoInicial;
    private final BigDecimal precoReserva;
    private final List<Interessado> interessados = new ArrayList<>();
    private RegraComissao regraComissao;
    private BigDecimal lanceAtual;
    private Lance maiorLance;
    private int totalLances;
    private EstadoLote estado = EstadoLote.ABERTO;

    public Lote(String nome, Categoria categoria, BigDecimal precoInicial, BigDecimal precoReserva) {
        this.nome = Objects.requireNonNull(nome, "O nome do lote é obrigatório.");
        this.categoria = Objects.requireNonNull(categoria, "A categoria é obrigatória.");
        Objects.requireNonNull(precoInicial, "O preço inicial é obrigatório.");
        Objects.requireNonNull(precoReserva, "O preço de reserva é obrigatório.");
        if (precoInicial.signum() <= 0) {
            throw new IllegalArgumentException("O preço inicial deve ser positivo");
        }
        if (precoReserva.signum() < 0) {
            throw new IllegalArgumentException("O preço de reserva não pode ser negativo");
        }
        this.precoInicial = precoInicial;
        this.precoReserva = precoReserva;
        this.lanceAtual = precoInicial;
        this.regraComissao = categoria.getRegraPadrao();
    }

    // ---- Observer ----
    public void acompanhar(Interessado interessado) {
        Objects.requireNonNull(interessado, "O interessado é obrigatório.");
        if (!interessados.contains(interessado)) {
            interessados.add(interessado);
        }
    }

    public void deixarDeAcompanhar(Interessado interessado) {
        interessados.remove(interessado);
    }

    private void notificar(Lance lance) {
        for (Interessado interessado : new ArrayList<>(interessados)) {
            if (interessado != lance.autor()) {
                interessado.atualizar(lance);
            }
        }
    }

    // ---- Lances ----
    public ResultadoLance darLance(Licitante autor, BigDecimal valor) {
        Objects.requireNonNull(autor, "O autor do lance é obrigatório.");
        Objects.requireNonNull(valor, "O valor do lance é obrigatório.");
        if (estado != EstadoLote.ABERTO) {
            return ResultadoLance.recusado("O lote '" + nome + "' está encerrado e não aceita novos lances.");
        }
        BigDecimal minimo = lanceAtual.multiply(FATOR_LANCE_MINIMO).setScale(2, RoundingMode.CEILING);
        if (valor.compareTo(minimo) < 0) {
            return ResultadoLance.recusado("Lance de " + Moeda.formatar(valor) + " abaixo do mínimo de "
                    + Moeda.formatar(minimo) + " (5% acima do lance atual de " + Moeda.formatar(lanceAtual) + ").");
        }
        Lance lance = new Lance(nome, autor, valor, LocalDateTime.now());
        maiorLance = lance;
        lanceAtual = valor;
        totalLances++;
        notificar(lance);
        return ResultadoLance.aceito("Lance de " + Moeda.formatar(valor) + " aceito.");
    }

    // ---- Strategy ----
    public void trocarRegraComissao(RegraComissao novaRegra) {
        Objects.requireNonNull(novaRegra, "A regra de comissão é obrigatória.");
        if (estado != EstadoLote.ABERTO) {
            throw new IllegalStateException("A regra só pode ser trocada antes do encerramento");
        }
        this.regraComissao = novaRegra;
    }

    // ---- Encerramento ----
    // Vendido ao maior lance se ele atingir a reserva. A comissão usa a regra vigente neste momento.
    public Optional<Venda> encerrar() {
        if (estado != EstadoLote.ABERTO) {
            throw new IllegalStateException("O lote '" + nome + "' já foi encerrado");
        }
        if (maiorLance != null && lanceAtual.compareTo(precoReserva) >= 0) {
            estado = EstadoLote.VENDIDO;
            BigDecimal comissao = regraComissao.calcular(lanceAtual);
            return Optional.of(new Venda(nome, categoria.getNome(), maiorLance.autor().getNome(), lanceAtual, comissao));
        }
        estado = EstadoLote.NAO_VENDIDO;
        return Optional.empty();
    }

    public String getNome() { return nome; }
    public Categoria getCategoria() { return categoria; }
    public BigDecimal getPrecoInicial() { return precoInicial; }
    public BigDecimal getPrecoReserva() { return precoReserva; }
    public BigDecimal getLanceAtual() { return lanceAtual; }
    public int getTotalLances() { return totalLances; }
    public EstadoLote getEstado() { return estado; }
    public RegraComissao getRegraComissao() { return regraComissao; }
}
