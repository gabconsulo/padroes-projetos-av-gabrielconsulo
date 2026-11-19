import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Lance(String nomeLote, Licitante autor, BigDecimal valor, LocalDateTime dataHora) {
}
