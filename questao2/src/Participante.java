import java.math.BigDecimal;
import java.util.Objects;

public final class Participante implements Licitante, Interessado {
    private final String nome;

    public Participante(String nome) {
        this.nome = Objects.requireNonNull(nome, "O nome do participante é obrigatório.");
    }

    public ResultadoLance darLance(Lote lote, BigDecimal valor) {
        return lote.darLance(this, valor);
    }

    @Override
    public void atualizar(Lance lance) {
        System.out.println("  [" + nome + "] Lote '" + lance.nomeLote()
                + "': novo lance de " + Moeda.formatar(lance.valor()));
    }

    @Override
    public String getNome() {
        return nome;
    }
}
