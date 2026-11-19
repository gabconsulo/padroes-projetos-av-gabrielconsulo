import java.util.Locale;
import java.util.Map;

// Único componente que conhece as fábricas concretas. Um novo provedor (ex.: Gcp) só exige
// criar as classes novas e registrar a fábrica aqui. As instâncias são únicas por provedor,
// então a numeração sequencial dos recursos é compartilhada entre todos os ambientes.
public final class SeletorProvedor {
    private static final Map<String, FabricaNuvem> FABRICAS = Map.of(
            "AWS", new FabricaAws(),
            "AZURE", new FabricaAzure()
    );

    private SeletorProvedor() {
    }

    public static FabricaNuvem escolher(String provedor) {
        if (provedor == null) {
            throw new IllegalArgumentException("Provedor não pode ser nulo");
        }
        FabricaNuvem fabrica = FABRICAS.get(provedor.trim().toUpperCase(Locale.ROOT));
        if (fabrica == null) {
            throw new IllegalArgumentException("Provedor desconhecido: " + provedor);
        }
        return fabrica;
    }
}
