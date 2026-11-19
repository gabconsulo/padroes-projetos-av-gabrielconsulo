public final class AcertoVendedor {
    private AcertoVendedor() {
    }

    public static void imprimir(Venda venda) {
        System.out.println("=== Acerto com o vendedor - Lote '" + venda.nomeLote() + "' ===");
        System.out.printf("%-26s %18s%n", "Valor de venda:", Moeda.formatar(venda.valorVenda()));
        System.out.printf("%-26s %18s%n", "Comissão cobrada:", Moeda.formatar(venda.comissao()));
        System.out.printf("%-26s %18s%n", "Líquido a repassar:", Moeda.formatar(venda.valorLiquido()));
    }
}
