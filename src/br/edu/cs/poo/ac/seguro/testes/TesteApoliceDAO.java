package br.edu.cs.poo.ac.seguro.testes;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.entidades.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TesteApoliceDAO extends TesteDAO {
    private ApoliceDAO dao = new ApoliceDAO();

    protected Class getClasse() {
        return Apolice.class;
    }

    // Método auxiliar para criar um veículo de teste
    private Veiculo criarVeiculoExemplo(String placa) {
        SeguradoEmpresa empresa = new SeguradoEmpresa(
                "Empresa X",
                new Endereco("Rua Teste dos Testes", "52021030", "234", "apto 103", "Brasil", "PE", "Recife"),
                LocalDate.of(2000, 1, 1),
                BigDecimal.ZERO,
                "12345678000100",
                1000000.0,
                false
        );
        return new Veiculo(placa, 2020, empresa, null, CategoriaVeiculo.INTERMEDIARIO);
    }

    @Test
    public void teste01_incluirEBuscar() {
        String numero = "A001";
        Veiculo veiculo = criarVeiculoExemplo("ABC1234");
        Apolice apolice = new Apolice(numero, veiculo, BigDecimal.TEN, new BigDecimal("500.00"),
                new BigDecimal("10000.00"), LocalDate.now());

        cadastro.incluir(apolice, numero);
        Apolice buscada = dao.buscar(numero);
        Assertions.assertNotNull(buscada);
    }

    @Test
    public void teste02_buscaInexistente() {
        Apolice buscada = dao.buscar("A002");
        Assertions.assertNull(buscada);
    }

    @Test
    public void teste03_excluirExistente() {
        String numero = "A003";
        Veiculo veiculo = criarVeiculoExemplo("DEF5678");
        Apolice apolice = new Apolice(numero, veiculo, BigDecimal.TEN, new BigDecimal("600.00"),
                new BigDecimal("15000.00"), LocalDate.now());

        cadastro.incluir(apolice, numero);
        boolean ret = dao.excluir(numero);
        Assertions.assertTrue(ret);
    }

    @Test
    public void teste04_excluirInexistente() {
        boolean ret = dao.excluir("A999");
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste05_incluirDireto() {
        String numero = "A005";
        Veiculo veiculo = criarVeiculoExemplo("GHI9012");
        Apolice apolice = new Apolice(numero, veiculo, BigDecimal.TEN, new BigDecimal("700.00"),
                new BigDecimal("20000.00"), LocalDate.now());

        boolean ret = dao.incluir(apolice);
        Assertions.assertTrue(ret);
        Assertions.assertNotNull(dao.buscar(numero));
    }

    @Test
    public void teste06_incluirDuplicado() {
        String numero = "A006";
        Veiculo veiculo = criarVeiculoExemplo("JKL3456");
        Apolice apolice = new Apolice(numero, veiculo, BigDecimal.TEN, new BigDecimal("800.00"),
                new BigDecimal("25000.00"), LocalDate.now());

        cadastro.incluir(apolice, numero);
        boolean ret = dao.incluir(apolice);
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste07_alterarInexistente() {
        String numero = "A007";
        Veiculo veiculo = criarVeiculoExemplo("MNO7890");
        Apolice nova = new Apolice(numero, veiculo, BigDecimal.TEN, new BigDecimal("900.00"),
                new BigDecimal("30000.00"), LocalDate.now());

        boolean ret = dao.alterar(nova);
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste08_alterarExistente() {
        String numero = "A008";
        Veiculo veiculo = criarVeiculoExemplo("PQR1234");
        Apolice original = new Apolice(numero, veiculo, BigDecimal.TEN, new BigDecimal("950.00"),
                new BigDecimal("35000.00"), LocalDate.now());

        cadastro.incluir(original, numero);

        Apolice alterada = new Apolice(numero, veiculo, BigDecimal.TEN, new BigDecimal("1000.00"),
                new BigDecimal("36000.00"), LocalDate.now());
        boolean ret = dao.alterar(alterada);
        Assertions.assertTrue(ret);
    }
}
