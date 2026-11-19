import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

// Singleton (inicialização sob demanda, thread-safe): o config.properties é lido uma única vez
// e todas as classes enxergam os mesmos valores sem receber a configuração por parâmetro.
public final class Configuracao {
    private static final String CHAVE_REGIAO = "regiao.padrao";
    private static final String CHAVE_ORCAMENTO = "orcamento.mensal.maximo";
    private static final String[] CAMINHOS = {
            "config.properties", "../config.properties", "questao1/config.properties"
    };

    private final String regiaoPadrao;
    private final BigDecimal orcamentoMensalMaximo;

    private Configuracao() {
        Properties propriedades = carregar();
        this.regiaoPadrao = obrigatoria(propriedades, CHAVE_REGIAO);
        this.orcamentoMensalMaximo = new BigDecimal(obrigatoria(propriedades, CHAVE_ORCAMENTO));
        System.out.println("[CONFIG] config.properties lido uma única vez (região "
                + regiaoPadrao + ", orçamento " + Moeda.formatar(orcamentoMensalMaximo) + ")");
    }

    private static final class Holder {
        private static final Configuracao INSTANCIA = new Configuracao();
    }

    public static Configuracao getInstance() {
        return Holder.INSTANCIA;
    }

    public String getRegiaoPadrao() {
        return regiaoPadrao;
    }

    public BigDecimal getOrcamentoMensalMaximo() {
        return orcamentoMensalMaximo;
    }

    private static Properties carregar() {
        for (String caminho : CAMINHOS) {
            Path arquivo = Path.of(caminho);
            if (Files.isRegularFile(arquivo)) {
                try (InputStream entrada = Files.newInputStream(arquivo)) {
                    Properties propriedades = new Properties();
                    propriedades.load(entrada);
                    return propriedades;
                } catch (IOException e) {
                    throw new IllegalStateException("Falha ao ler " + caminho, e);
                }
            }
        }
        throw new IllegalStateException("Arquivo config.properties não encontrado");
    }

    private static String obrigatoria(Properties propriedades, String chave) {
        String valor = propriedades.getProperty(chave);
        if (valor == null || valor.isBlank()) {
            throw new IllegalStateException("Propriedade obrigatória ausente: " + chave);
        }
        return valor.trim();
    }
}
