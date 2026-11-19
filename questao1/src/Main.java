import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Força a leitura do config.properties uma única vez, antes das threads.
        Configuracao.getInstance();

        List<SolicitacaoAmbiente> solicitacoes = List.of(
                new SolicitacaoAmbiente("portal-clientes", "Aws", 2, 100),
                new SolicitacaoAmbiente("analytics", "Aws", 5, 500),
                new SolicitacaoAmbiente("backoffice", "Azure", 3, 200),
                new SolicitacaoAmbiente("data-lake", "Aws", 15, 1000)
        );

        System.out.println("=== Quatro provisionamentos disparados simultaneamente ===");
        CountDownLatch largada = new CountDownLatch(1);
        List<Thread> threads = new ArrayList<>();
        for (SolicitacaoAmbiente solicitacao : solicitacoes) {
            Thread thread = new Thread(() -> {
                try {
                    largada.await();
                    FabricaNuvem fabrica = SeletorProvedor.escolher(solicitacao.provedor());
                    new Provisionador(fabrica).provisionar(solicitacao);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "provisionamento-" + solicitacao.nome());
            threads.add(thread);
            thread.start();
        }
        largada.countDown();
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("=== Histórico de auditoria ===");
        List<RegistroAuditoria> registros = HistoricoAuditoria.getInstance().getRegistros();
        for (int i = 0; i < registros.size(); i++) {
            System.out.println((i + 1) + ". " + registros.get(i));
        }
        System.out.println("Total de registros: " + registros.size());
    }
}
