import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Singleton (inicialização sob demanda, thread-safe). Os métodos são sincronizados no mesmo
// monitor, então execuções simultâneas não perdem nem corrompem registros, e a data/hora é
// tomada dentro da seção crítica, o que preserva a ordem em que os registros foram feitos.
public final class HistoricoAuditoria {
    private final List<RegistroAuditoria> registros = new ArrayList<>();

    private HistoricoAuditoria() {
    }

    private static final class Holder {
        private static final HistoricoAuditoria INSTANCIA = new HistoricoAuditoria();
    }

    public static HistoricoAuditoria getInstance() {
        return Holder.INSTANCIA;
    }

    public synchronized void registrar(String ambiente, String provedor,
                                       ResultadoProvisionamento resultado, String motivo) {
        registros.add(new RegistroAuditoria(LocalDateTime.now(), ambiente, provedor, resultado, motivo));
    }

    public synchronized List<RegistroAuditoria> getRegistros() {
        return List.copyOf(registros);
    }
}
