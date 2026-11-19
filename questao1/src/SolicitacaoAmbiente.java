public record SolicitacaoAmbiente(String nome, String provedor, int quantidadeMaquinas, int gbArmazenamento) {
    public SolicitacaoAmbiente {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do ambiente não pode ser vazio");
        }
        if (provedor == null || provedor.isBlank()) {
            throw new IllegalArgumentException("Provedor não pode ser vazio");
        }
        if (quantidadeMaquinas < 1) {
            throw new IllegalArgumentException("É preciso ao menos uma máquina virtual");
        }
        if (gbArmazenamento < 0) {
            throw new IllegalArgumentException("Armazenamento não pode ser negativo");
        }
    }
}
