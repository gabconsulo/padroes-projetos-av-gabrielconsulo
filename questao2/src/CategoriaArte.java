public final class CategoriaArte implements Categoria {
    @Override
    public String getNome() {
        return "Arte";
    }

    @Override
    public RegraComissao getRegraPadrao() {
        return new ComissaoArte();
    }
}
