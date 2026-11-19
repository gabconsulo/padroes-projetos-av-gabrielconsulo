import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

// Aplica o orçamento (RF05), imprime o resumo (RF06) e registra a auditoria (RF07).
// Também recebe a fábrica de fora e nunca instancia recursos de provedor.
public final class Provisionador {
    private final FabricaNuvem fabrica;

    public Provisionador(FabricaNuvem fabrica) {
        this.fabrica = Objects.requireNonNull(fabrica, "A fábrica é obrigatória.");
    }

    public Optional<Ambiente> provisionar(SolicitacaoAmbiente solicitacao) {
        HistoricoAuditoria historico = HistoricoAuditoria.getInstance();
        BigDecimal orcamento = Configuracao.getInstance().getOrcamentoMensalMaximo();
        BigDecimal estimado = fabrica.getPrecos()
                .custoMensal(solicitacao.quantidadeMaquinas(), solicitacao.gbArmazenamento());

        if (estimado.compareTo(orcamento) > 0) {
            String motivo = "custo estimado " + Moeda.formatar(estimado)
                    + " ultrapassa o orçamento de " + Moeda.formatar(orcamento);
            historico.registrar(solicitacao.nome(), fabrica.getNomeProvedor(),
                    ResultadoProvisionamento.RECUSADO, motivo);
            System.out.println("=== Ambiente '" + solicitacao.nome() + "' RECUSADO ===" + System.lineSeparator()
                    + "Motivo: " + motivo + System.lineSeparator());
            return Optional.empty();
        }

        Ambiente ambiente = new Ambiente(solicitacao.nome(), fabrica,
                solicitacao.quantidadeMaquinas(), solicitacao.gbArmazenamento());
        historico.registrar(ambiente.getNome(), ambiente.getProvedor(), ResultadoProvisionamento.ACEITO,
                "custo estimado " + Moeda.formatar(ambiente.getCustoMensal()) + " dentro do orçamento");
        System.out.println(ambiente.resumo());
        return Optional.of(ambiente);
    }
}
