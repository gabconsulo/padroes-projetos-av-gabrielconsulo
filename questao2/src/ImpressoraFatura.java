// Trata toda fatura do mesmo jeito, com ou sem serviços: só percorre os itens e imprime o total.
public final class ImpressoraFatura {
    private ImpressoraFatura() {
    }

    public static void imprimir(Fatura fatura) {
        System.out.println("=== Fatura do comprador ===");
        for (ItemFatura item : fatura.getItens()) {
            System.out.printf("  %-50s %18s%n", item.descricao(), Moeda.formatar(item.valor()));
        }
        System.out.printf("  %-50s %18s%n", "TOTAL A PAGAR", Moeda.formatar(fatura.getTotal()));
    }
}
