import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vendedor extends Funcionario{
    private List<Venda> vendas;

    public Vendedor(String nome, Cargo cargo, YearMonth contratacao) {
        super(nome, cargo, contratacao);
        this.vendas = new ArrayList<>();
    }
    public void addVenda(Venda venda) {
        this.vendas.add(venda);
    }
    public List<Venda> getVendas() {
        return Collections.unmodifiableList(vendas);
    }
}
