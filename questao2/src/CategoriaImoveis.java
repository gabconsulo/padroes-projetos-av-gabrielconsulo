public final class CategoriaImoveis implements Categoria {
    @Override
    public String getNome() {
        return "Imóveis";
    }

    @Override
    public RegraComissao getRegraPadrao() {
        return new ComissaoImoveis();
    }
}
