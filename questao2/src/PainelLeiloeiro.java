import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public final class PainelLeiloeiro implements Interessado {
    private final Map<String, BigDecimal> maiorLancePorLote = new LinkedHashMap<>();
    private final Map<String, Integer> totalPorLote = new LinkedHashMap<>();

    @Override
    public void atualizar(Lance lance) {
        BigDecimal maior = maiorLancePorLote.merge(lance.nomeLote(), lance.valor(), BigDecimal::max);
        int total = totalPorLote.merge(lance.nomeLote(), 1, Integer::sum);
        System.out.println("  [PAINEL] Lote '" + lance.nomeLote() + "': maior lance "
                + Moeda.formatar(maior) + " | total de lances: " + total);
    }

    public int getTotalLances(String nomeLote) {
        return totalPorLote.getOrDefault(nomeLote, 0);
    }
}
