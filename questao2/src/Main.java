import java.math.BigDecimal;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Lote tela = new Lote("Tela Paisagem Serrana", new CategoriaArte(), valor("10000"), valor("12000"));
        Lote notebook = new Lote("Notebook Gamer", new CategoriaEletronicos(), valor("3000"), valor("4500"));

        Participante ana = new Participante("Ana");
        Participante bruno = new Participante("Bruno");
        Participante carla = new Participante("Carla");
        PainelLeiloeiro painel = new PainelLeiloeiro();
        Auditor auditor = new Auditor();

        for (Lote lote : new Lote[]{tela, notebook}) {
            lote.acompanhar(painel);
            lote.acompanhar(auditor);
        }
        tela.acompanhar(ana);
        tela.acompanhar(bruno);
        tela.acompanhar(carla);
        notebook.acompanhar(ana);
        notebook.acompanhar(carla);

        System.out.println("=== Lances no lote '" + tela.getNome() + "' (" + tela.getCategoria().getNome() + ") ===");
        lance(ana, tela, "10600");
        lance(bruno, tela, "10700");
        lance(bruno, tela, "11130");

        System.out.println("\n>>> Carla deixa de acompanhar o lote '" + tela.getNome() + "'");
        tela.deixarDeAcompanhar(carla);
        lance(ana, tela, "12000");
        lance(bruno, tela, "12600");

        System.out.println("\n=== Lances no lote '" + notebook.getNome() + "' ("
                + notebook.getCategoria().getNome() + ") ===");
        lance(carla, notebook, "3200");
        lance(ana, notebook, "3300");

        System.out.println("\n=== Campanha: troca da regra de comissão do lote '" + tela.getNome() + "' ===");
        System.out.println("Regra atual: " + tela.getRegraComissao().getDescricao());
        tela.trocarRegraComissao(new ComissaoEletronicos());
        System.out.println("Regra vigente: " + tela.getRegraComissao().getDescricao());

        System.out.println("\n=== Encerramento ===");
        Optional<Venda> vendaTela = tela.encerrar();
        Optional<Venda> vendaNotebook = notebook.encerrar();
        imprimirResultado(tela, vendaTela);
        imprimirResultado(notebook, vendaNotebook);
        lance(ana, tela, "15000");

        if (vendaTela.isPresent()) {
            Venda venda = vendaTela.get();

            System.out.println();
            AcertoVendedor.imprimir(venda);

            System.out.println();
            Fatura semServicos = new FaturaVenda(venda);
            ImpressoraFatura.imprimir(semServicos);

            System.out.println();
            Fatura comServicos = new EntregaExpressa(
                    new EmbalagemEspecial(
                            new CertificadoAutenticidade(
                                    new EmbalagemEspecial(
                                            new SeguroTransporte(new FaturaVenda(venda))))));
            ImpressoraFatura.imprimir(comServicos);
        }

        System.out.println("\n=== Resumo do acompanhamento ===");
        System.out.println("Total de lances aceitos no painel: lote '" + tela.getNome() + "' = "
                + painel.getTotalLances(tela.getNome()) + ", lote '" + notebook.getNome() + "' = "
                + painel.getTotalLances(notebook.getNome()));
        System.out.println("Registros do auditor: " + auditor.getRegistros().size());
    }

    private static void lance(Participante participante, Lote lote, String valor) {
        System.out.println("\n" + participante.getNome() + " dá lance de " + Moeda.formatar(valor(valor))
                + " no lote '" + lote.getNome() + "'");
        ResultadoLance resultado = participante.darLance(lote, valor(valor));
        System.out.println("  -> " + (resultado.aceito() ? "ACEITO: " : "RECUSADO: ") + resultado.mensagem());
    }

    private static void imprimirResultado(Lote lote, Optional<Venda> venda) {
        if (venda.isPresent()) {
            System.out.println("Lote '" + lote.getNome() + "' VENDIDO a " + venda.get().comprador()
                    + " por " + Moeda.formatar(venda.get().valorVenda())
                    + " (reserva " + Moeda.formatar(lote.getPrecoReserva()) + ")");
        } else {
            System.out.println("Lote '" + lote.getNome() + "' NÃO VENDIDO: maior lance "
                    + Moeda.formatar(lote.getLanceAtual()) + " abaixo da reserva de "
                    + Moeda.formatar(lote.getPrecoReserva()));
        }
    }

    private static BigDecimal valor(String texto) {
        return new BigDecimal(texto);
    }
}
