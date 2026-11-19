public final class CategoriaVeiculos implements Categoria {
    @Override
    public String getNome() {
        return "Veículos";
    }

    @Override
    public RegraComissao getRegraPadrao() {
        return new ComissaoVeiculos();
    }
}
