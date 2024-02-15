import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

public class Cargo {
    private TipoCargo nomeCargo;
    private BigDecimal salario;
    private BigDecimal valorAjusteAnual;
    private int beneficio;

    public Cargo(TipoCargo nomeCargo, BigDecimal salario, BigDecimal valorAjusteAnual, int beneficio) {
        this.nomeCargo = nomeCargo;
        this.salario = salario;
        this.beneficio = beneficio;
        this.valorAjusteAnual = valorAjusteAnual;
    }

    public BigDecimal salarioComAjuste(long anosTrabalhado) {
        return salario.add(this.valorAjusteAnual.multiply(BigDecimal.valueOf(anosTrabalhado)));
    }
    public BigDecimal getValorAjusteAnual() {
        return valorAjusteAnual;
    }

    public void setValorAjusteAnual(BigDecimal valorAjusteAnual) {
        this.valorAjusteAnual = valorAjusteAnual;
    }
    public TipoCargo getNomeCargo() {
        return nomeCargo;
    }

    public void setNomeCargo(TipoCargo cargo) {
        this.nomeCargo = cargo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public int getBeneficio() {
        return beneficio;
    }

    public void setBeneficio(int beneficio) {
        this.beneficio = beneficio;
    }
}

