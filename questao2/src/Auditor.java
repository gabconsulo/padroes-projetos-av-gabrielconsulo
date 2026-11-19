import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Auditor implements Interessado {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final List<Lance> registros = new ArrayList<>();

    @Override
    public void atualizar(Lance lance) {
        registros.add(lance);
        System.out.println("  [AUDITOR] Registro #" + registros.size() + ": " + FORMATO.format(lance.dataHora())
                + " | Lote '" + lance.nomeLote() + "' | " + lance.autor().getNome()
                + " | " + Moeda.formatar(lance.valor()));
    }

    public List<Lance> getRegistros() {
        return Collections.unmodifiableList(registros);
    }
}
