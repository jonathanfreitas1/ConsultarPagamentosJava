import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Criação dos cargos
        Cargo secretario = new Cargo(TipoCargo.SECRETARIO, BigDecimal.valueOf(7000),BigDecimal.valueOf(1000), 20);
        Cargo vendedor = new Cargo(TipoCargo.VENDEDOR, BigDecimal.valueOf(12000), BigDecimal.valueOf(1800),30);
        Cargo gerente = new Cargo(TipoCargo.GERENTE, BigDecimal.valueOf(20000), BigDecimal.valueOf(3000), 0);

        // Criação dos funcionarios
        Funcionario jorgeCarvalho = new Funcionario("Jorge Carvalho", secretario, YearMonth.of(2018,1));
        Funcionario mariaSouza = new Funcionario("Maria Souza", secretario, YearMonth.of(2015,12));
        Vendedor anaSilva = new Vendedor("Ana Silva", vendedor, YearMonth.of(2021, 12));
        Vendedor  joaoMendes = new Vendedor("João Mendes", vendedor, YearMonth.of(2021, 12));
        Funcionario julianaAlves = new Funcionario("Juliana Alves", gerente, YearMonth.of(2017, 7));
        Funcionario bentoAlbino = new Funcionario("Bento Albine", gerente, YearMonth.of(2014, 3));

        // Criação das vendas
        anaSilva.addVenda(new Venda(YearMonth.of(2021,12), BigDecimal.valueOf(5200)));
        anaSilva.addVenda(new Venda(YearMonth.of(2022,1), BigDecimal.valueOf(4000)));
        anaSilva.addVenda(new Venda(YearMonth.of(2022,2), BigDecimal.valueOf(4200)));
        anaSilva.addVenda(new Venda(YearMonth.of(2022,3), BigDecimal.valueOf(5850)));
        anaSilva.addVenda(new Venda(YearMonth.of(2022,4), BigDecimal.valueOf(70000)));

        joaoMendes.addVenda(new Venda(YearMonth.of(2021,12), BigDecimal.valueOf(3400)));
        joaoMendes.addVenda(new Venda(YearMonth.of(2022,1), BigDecimal.valueOf(7700)));
        joaoMendes.addVenda(new Venda(YearMonth.of(2022,2), BigDecimal.valueOf(5000)));
        joaoMendes.addVenda(new Venda(YearMonth.of(2022,3), BigDecimal.valueOf(5900)));
        joaoMendes.addVenda(new Venda(YearMonth.of(2022,4), BigDecimal.valueOf(6500)));

        //Listas de funcionarios
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(anaSilva);
        funcionarios.add(jorgeCarvalho);

        List<Funcionario> vendedores = new ArrayList<>();
        vendedores.add(anaSilva);
        vendedores.add(joaoMendes);

        //Chamando as funções
        BigDecimal salariosComBeneficio = salariosComBeneficio(funcionarios, YearMonth.of(2022,2));
        System.out.println("Total a pagar com beneficios: " + salariosComBeneficio);

        BigDecimal salarios = salario(funcionarios, YearMonth.of(2022,2));
        System.out.println("Total a pagar sem beneficios: " + salarios);

        BigDecimal beneficios = beneficio(funcionarios, YearMonth.of(2022,2));
        System.out.println("Total a pagar de beneficios:" + beneficios);

        Funcionario maiorSalario = maiorSalario(funcionarios, YearMonth.of(2010,5));
        if (maiorSalario.getNome() == null) {
            System.out.println("Funcionario com maior salario: funcionarios não existem para essa data");
        } else {
            System.out.println("Funcionario com maior salario: " + maiorSalario.getNome());
        }

        Funcionario maiorBeneficio = maiorBeneficio(funcionarios, YearMonth.of(2010,2));
        if (maiorBeneficio.getNome() == null) {
            System.out.println("Funcionario com maior beneficio: funcionarios não existem para essa data");
        } else {
            System.out.println("Funcionario com maior beneficio: " + maiorBeneficio.getNome());
        }

        Funcionario vendedorDoMes = maiorVendedor(vendedores, YearMonth.of(2022,4));
        if (vendedorDoMes.getNome() == null) {
            System.out.println("Vendedor com maior venda: Vendedores não existem para essa data");
        } else {
            System.out.println("Vendedor com maior venda: " + vendedorDoMes.getNome());
        }
    }

    public static BigDecimal salariosComBeneficio(List<Funcionario> funcionarios, YearMonth mesAno) {
         return funcionarios.stream().map(f -> {
            long anosTrabalhado = ChronoUnit.YEARS.between(f.getContratacao(), mesAno);
            if (f.getContratacao().isAfter(mesAno)) {
                return BigDecimal.ZERO;
            }
            BigDecimal salario = f.getCargo().salarioComAjuste(anosTrabalhado);
            if (f.getCargo().getNomeCargo().equals(TipoCargo.SECRETARIO)) {
                BigDecimal beneficio = salario.multiply(BigDecimal.valueOf(f.getCargo().getBeneficio()/100.0));
                salario = salario.add(beneficio);

            }
            if (f.getCargo().getNomeCargo().equals(TipoCargo.VENDEDOR)) {
                Vendedor funcionario = (Vendedor) f;
                BigDecimal totalVendido = funcionario.getVendas().stream().filter(v -> v.getDataVenda().equals(mesAno)).map(v -> v.getValor()).reduce(BigDecimal.ZERO, (b1, b2) -> b1.add(b2));
                salario = salario.add(totalVendido.multiply(BigDecimal.valueOf(f.getCargo().getBeneficio()/100.0)));
            }
            return salario;
        }).reduce(BigDecimal.ZERO, (b1, b2) -> b1.add(b2));
    }

    public static BigDecimal salario(List<Funcionario> funcionarios, YearMonth mesAno) {
        return funcionarios.stream().map(f -> {
            long anosTrabalhado = ChronoUnit.YEARS.between(f.getContratacao(), mesAno);
            if (f.getContratacao().isAfter(mesAno)) {
                return BigDecimal.ZERO;
            }
            BigDecimal salario = f.getCargo().salarioComAjuste(anosTrabalhado);
            return salario;
        }).reduce(BigDecimal.ZERO, (b1, b2) -> b1.add(b2));
    }

    public static BigDecimal beneficio(List<Funcionario> funcionarios, YearMonth mesAno) {
        return funcionarios.stream().map(f -> {
            long anosTrabalhado = ChronoUnit.YEARS.between(f.getContratacao(), mesAno);
            if (f.getContratacao().isAfter(mesAno)) {
                return BigDecimal.ZERO;
            }
            BigDecimal salario = f.getCargo().salarioComAjuste(anosTrabalhado);
            BigDecimal beneficio = new BigDecimal("0");
            if (f.getCargo().getNomeCargo().equals(TipoCargo.SECRETARIO)) {
                beneficio = salario.multiply(BigDecimal.valueOf(0.2));
            }
            if (f.getCargo().getNomeCargo().equals(TipoCargo.VENDEDOR)) {
                Vendedor funcionario = (Vendedor) f;
                BigDecimal totalVendido = funcionario.getVendas().stream().filter(v -> v.getDataVenda().equals(mesAno)).map(v -> v.getValor()).reduce(BigDecimal.ZERO, (b1, b2) -> b1.add(b2));
                beneficio = totalVendido.multiply(BigDecimal.valueOf(0.3));
            }
            return beneficio;
        }).reduce(BigDecimal.ZERO, (b1, b2) -> b1.add(b2));
    }
    public static Funcionario maiorSalario(List<Funcionario> funcionarios, YearMonth mesAno) {
        BigDecimal maiorSalario = new BigDecimal("0");
        Funcionario funcionarioComMaiorSalario = new Funcionario();
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getContratacao().isAfter(mesAno))
                continue;
            BigDecimal salario = funcionario.calcularSalarioComAjusteDeTempo(mesAno);
            if(funcionario.getCargo().getNomeCargo().equals(TipoCargo.SECRETARIO)) {
                BigDecimal beneficio = salario.multiply(BigDecimal.valueOf(funcionario.getCargo().getBeneficio()/100.0));
                salario = salario.add(beneficio);
            }
            if(funcionario.getCargo().getNomeCargo().equals(TipoCargo.VENDEDOR)) {
                Vendedor vendedor = (Vendedor) funcionario;
                BigDecimal totalVendido = vendedor.getVendas().stream().filter(v -> v.getDataVenda().equals(mesAno)).map(v -> v.getValor()).reduce(BigDecimal.ZERO, (b1, b2) -> b1.add(b2));
                salario = salario.add(totalVendido.multiply(BigDecimal.valueOf(funcionario.getCargo().getBeneficio()/100.0)));
            }
            if (maiorSalario.compareTo(salario) == -1) {
                maiorSalario = salario;
                funcionarioComMaiorSalario = funcionario;
            }
        }
        return funcionarioComMaiorSalario;
    }
    public static Funcionario maiorBeneficio(List<Funcionario> funcionarios, YearMonth mesAno) {
        BigDecimal maiorBeneficio = new BigDecimal("0");
        BigDecimal beneficio = new BigDecimal("0");
        Funcionario funcionarioComMaiorBeneficio = new Funcionario();
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getContratacao().isAfter(mesAno))
                continue;
            BigDecimal salario = funcionario.calcularSalarioComAjusteDeTempo(mesAno);
            if(funcionario.getCargo().getNomeCargo().equals(TipoCargo.SECRETARIO)) {
                beneficio = salario.multiply(BigDecimal.valueOf(funcionario.getCargo().getBeneficio()/100.0));
            }
            if(funcionario.getCargo().getNomeCargo().equals(TipoCargo.VENDEDOR)) {
                Vendedor vendedor = (Vendedor) funcionario;
                BigDecimal totalVendido = vendedor.getVendas().stream().filter(v -> v.getDataVenda().equals(mesAno)).map(v -> v.getValor()).reduce(BigDecimal.ZERO, (b1, b2) -> b1.add(b2));
                beneficio = totalVendido.multiply(BigDecimal.valueOf(funcionario.getCargo().getBeneficio()/100.0));

            }
            if (maiorBeneficio.compareTo(beneficio) == -1) {
                maiorBeneficio = beneficio;
                funcionarioComMaiorBeneficio = funcionario;
            }
        }
        return funcionarioComMaiorBeneficio;
    }
    public static Funcionario maiorVendedor(List<Funcionario> funcionarios, YearMonth mesAno) {
        BigDecimal maiorVendedor = new BigDecimal("0");
        Funcionario vendedorDoMes = new Funcionario();
        for (Funcionario funcionario : funcionarios.stream().filter(f -> TipoCargo.VENDEDOR.equals(f.getCargo().getNomeCargo())).collect(Collectors.toList())){
            if (funcionario.getContratacao().isAfter(mesAno))
                continue;
            Vendedor vendedor = (Vendedor) funcionario;
            BigDecimal totalVendido = vendedor.getVendas().stream().filter(v -> v.getDataVenda().equals(mesAno)).map(v -> v.getValor()).reduce(BigDecimal.ZERO, (b1, b2) -> b1.add(b2));
            BigDecimal valorVendas = totalVendido.add(totalVendido.multiply(BigDecimal.valueOf(funcionario.getCargo().getBeneficio()/100.0)));

            if (maiorVendedor.compareTo(valorVendas) == -1) {
                maiorVendedor = valorVendas;
                vendedorDoMes = funcionario;
            }
        }
        return vendedorDoMes;
    }
}