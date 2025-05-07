package br.edu.cs.poo.ac.seguro.testes;

import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.entidades.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TesteSinistroDAO extends TesteDAO {
    private SinistroDAO dao = new SinistroDAO();

    @Override
    protected Class getClasse() {
        return Sinistro.class;
    }

    private Endereco criarEnderecoExemplo() {
        return new Endereco("Rua Teste dos Testes", "52021030", "234", "apto 103", "Brasil", "PE", "Recife");
    }

    private SeguradoEmpresa criarSeguradoEmpresaExemplo() {
        return new SeguradoEmpresa("Empresa Teste", criarEnderecoExemplo(), LocalDate.of(2000, 1, 1), BigDecimal.ZERO,
                "12345678000100", 100000.0, false);
    }

    private Veiculo criarVeiculoExemplo() {
        return new Veiculo("ABC1234", 2020, criarSeguradoEmpresaExemplo(), null, CategoriaVeiculo.BASICO);
    }

    @Test
    public void teste01_incluirBuscar() {
        String numero = "S001";
        Sinistro sinistro = new Sinistro(
                numero,
                criarVeiculoExemplo(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "usuario01",
                new BigDecimal("5000.00"),
                TipoSinistro.COLISAO
        );
        boolean ret = dao.incluir(sinistro);
        Assertions.assertTrue(ret);

        Sinistro buscado = dao.buscar(numero);
        Assertions.assertNotNull(buscado);
    }

    @Test
    public void teste02_buscarInexistente() {
        Sinistro sinistro = dao.buscar("S999");
        Assertions.assertNull(sinistro);
    }

    @Test
    public void teste03_excluirExistente() {
        String numero = "S002";
        Sinistro sinistro = new Sinistro(
                numero,
                criarVeiculoExemplo(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "usuario02",
                new BigDecimal("7500.00"),
                TipoSinistro.COLISAO
        );
        cadastro.incluir(sinistro, numero);
        boolean ret = dao.excluir(numero);
        Assertions.assertTrue(ret);
    }

    @Test
    public void teste04_excluirInexistente() {
        boolean ret = dao.excluir("S999");
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste05_alterarExistente() {
        String numero = "S003";
        Sinistro sinistro = new Sinistro(
                numero,
                criarVeiculoExemplo(),
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now(),
                "usuario03",
                new BigDecimal("12000.00"),
                TipoSinistro.INCENDIO
        );
        cadastro.incluir(sinistro, numero);

        // Criar nova versão do sinistro
        Sinistro atualizado = new Sinistro(
                numero,
                criarVeiculoExemplo(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "usuario03",
                new BigDecimal("13000.00"),
                TipoSinistro.INCENDIO
        );

        boolean ret = dao.alterar(atualizado);
        Assertions.assertTrue(ret);
    }

    @Test
    public void teste06_alterarInexistente() {
        String numero = "S004";
        Sinistro sinistro = new Sinistro(
                numero,
                criarVeiculoExemplo(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "usuario04",
                new BigDecimal("3000.00"),
                TipoSinistro.INCENDIO
        );
        boolean ret = dao.alterar(sinistro);
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste07_incluirDuplicado() {
        String numero = "S005";
        Sinistro sinistro = new Sinistro(
                numero,
                criarVeiculoExemplo(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "usuario05",
                new BigDecimal("2000.00"),
                TipoSinistro.COLISAO
        );
        cadastro.incluir(sinistro, numero);
        boolean ret = dao.incluir(sinistro);
        Assertions.assertFalse(ret);
    }
}
