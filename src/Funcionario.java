import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

public class Funcionario {
    private String nome;
    private Cargo cargo;
    private YearMonth contratacao;

    public Funcionario(){}
    public Funcionario(String nome, Cargo cargo, YearMonth contratacao) {
        this.nome = nome;
        this.cargo = cargo;
        this.contratacao = contratacao;
    }

    public BigDecimal calcularSalarioComAjusteDeTempo(YearMonth mesAno) {
        long anosTrabalhado = ChronoUnit.YEARS.between(this.contratacao, mesAno);
        anosTrabalhado = anosTrabalhado > 0 ? anosTrabalhado : 0;
        return this.cargo.salarioComAjuste(anosTrabalhado);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public YearMonth getContratacao() {
        return contratacao;
    }

    public void setContratacao(YearMonth contratacao) {
        this.contratacao = contratacao;
    }
}
