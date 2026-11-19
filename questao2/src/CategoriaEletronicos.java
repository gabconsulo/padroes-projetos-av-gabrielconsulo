public final class CategoriaEletronicos implements Categoria {
    @Override
    public String getNome() {
        return "Eletrônicos";
    }

    @Override
    public RegraComissao getRegraPadrao() {
        return new ComissaoEletronicos();
    }
}
