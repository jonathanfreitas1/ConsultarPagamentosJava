import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

public class Venda {
    private YearMonth dataVenda;
    private BigDecimal valor;

    public Venda(YearMonth dataVenda, BigDecimal valor) {
        this.dataVenda = dataVenda;
        this.valor = valor;
    }

    public YearMonth getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(YearMonth dataVenda) {
        this.dataVenda = dataVenda;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
