package br.edu.cs.poo.ac.seguro.gui;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.NumberFormat; 

import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoPessoaMediator;

public class CRUDSeguradoPessoa {

    private JFrame frameCrudSeguradoPessoa;
    private SeguradoPessoaMediator mediator = SeguradoPessoaMediator.getInstancia();

    private JTextField campoCpf;
    private JTextField campoNome;
    private JFormattedTextField campoRenda;
    private JFormattedTextField campoDataNascimento;
    private JTextField campoBonus;

    private JTextField campoLogradouro;
    private JFormattedTextField campoCep;
    private JTextField campoNumeroEndereco;
    private JTextField campoComplemento;
    private JTextField campoPais;
    private JTextField campoEstado;
    private JTextField campoCidade;

    private JButton btNovo;
    private JButton btBuscar;
    private JButton btIncluirAlterar;
    private JButton btExcluir;
    private JButton btCancelar;
    private JButton btLimparCampos;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                CRUDSeguradoPessoa window = new CRUDSeguradoPessoa();
                window.frameCrudSeguradoPessoa.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CRUDSeguradoPessoa() {
        initialize();
        setInitialState();
    }

    private void initialize() {
        frameCrudSeguradoPessoa = new JFrame();
        frameCrudSeguradoPessoa.setTitle("CRUD Segurado Pessoa");
        frameCrudSeguradoPessoa.setBounds(100, 100, 650, 550);
        frameCrudSeguradoPessoa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameCrudSeguradoPessoa.getContentPane().setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        frameCrudSeguradoPessoa.getContentPane().add(new JLabel("CPF:"), gbc);
        campoCpf = new JTextField();
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        frameCrudSeguradoPessoa.getContentPane().add(campoCpf, gbc);
        gbc.weightx = 0.0;

        JPanel panelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        btNovo = new JButton("Novo");
        panelBusca.add(btNovo);
        btBuscar = new JButton("Buscar");
        panelBusca.add(btBuscar);
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        frameCrudSeguradoPessoa.getContentPane().add(panelBusca, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        frameCrudSeguradoPessoa.getContentPane().add(new JLabel("Nome:"), gbc);
        campoNome = new JTextField();
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        frameCrudSeguradoPessoa.getContentPane().add(campoNome, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 2;
        frameCrudSeguradoPessoa.getContentPane().add(new JLabel("Data Nasc. (dd/MM/yyyy):"), gbc);
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            campoDataNascimento = new JFormattedTextField(dateMask);
        } catch (ParseException e) {
            e.printStackTrace();
            campoDataNascimento = new JFormattedTextField();
        }
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        frameCrudSeguradoPessoa.getContentPane().add(campoDataNascimento, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 3;
        frameCrudSeguradoPessoa.getContentPane().add(new JLabel("Renda:"), gbc);
        NumberFormat rendaFormat = DecimalFormat.getNumberInstance();
        rendaFormat.setMinimumFractionDigits(2);
        rendaFormat.setMaximumFractionDigits(2);
        NumberFormatter rendaFormatter = new NumberFormatter(rendaFormat);
        rendaFormatter.setValueClass(Double.class);
        rendaFormatter.setAllowsInvalid(false);
        rendaFormatter.setCommitsOnValidEdit(true);
        campoRenda = new JFormattedTextField(rendaFormatter);
        campoRenda.setValue(0.00);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        frameCrudSeguradoPessoa.getContentPane().add(campoRenda, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 4;
        frameCrudSeguradoPessoa.getContentPane().add(new JLabel("Bônus:"), gbc);
        campoBonus = new JTextField();
        campoBonus.setEditable(false);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        frameCrudSeguradoPessoa.getContentPane().add(campoBonus, gbc);
        gbc.gridwidth = 1;

        JPanel panelEndereco = new JPanel(new GridBagLayout());
        panelEndereco.setBorder(BorderFactory.createTitledBorder("Endereço"));
        GridBagConstraints gbcEndereco = new GridBagConstraints();
        gbcEndereco.insets = new Insets(2, 2, 2, 2);
        gbcEndereco.fill = GridBagConstraints.HORIZONTAL;

        gbcEndereco.gridx = 0; gbcEndereco.gridy = 0;
        panelEndereco.add(new JLabel("Logradouro:"), gbcEndereco);
        campoLogradouro = new JTextField();
        gbcEndereco.gridx = 1; gbcEndereco.weightx = 1.0;
        panelEndereco.add(campoLogradouro, gbcEndereco);

        gbcEndereco.gridx = 0; gbcEndereco.gridy = 1; gbcEndereco.weightx = 0.0;
        panelEndereco.add(new JLabel("CEP:"), gbcEndereco);
        try {
            MaskFormatter cepMask = new MaskFormatter("########"); 
            cepMask.setPlaceholderCharacter('_');
            campoCep = new JFormattedTextField(cepMask);
        } catch (ParseException e) {
            e.printStackTrace();
            campoCep = new JFormattedTextField();
        }
        gbcEndereco.gridx = 1; gbcEndereco.weightx = 1.0;
        panelEndereco.add(campoCep, gbcEndereco);

        gbcEndereco.gridx = 0; gbcEndereco.gridy = 2; gbcEndereco.weightx = 0.0;
        panelEndereco.add(new JLabel("Número:"), gbcEndereco);
        campoNumeroEndereco = new JTextField();
        gbcEndereco.gridx = 1; gbcEndereco.weightx = 1.0;
        panelEndereco.add(campoNumeroEndereco, gbcEndereco);

        gbcEndereco.gridx = 0; gbcEndereco.gridy = 3; gbcEndereco.weightx = 0.0;
        panelEndereco.add(new JLabel("Complemento:"), gbcEndereco);
        campoComplemento = new JTextField();
        gbcEndereco.gridx = 1; gbcEndereco.weightx = 1.0;
        panelEndereco.add(campoComplemento, gbcEndereco);

        gbcEndereco.gridx = 0; gbcEndereco.gridy = 4; gbcEndereco.weightx = 0.0;
        panelEndereco.add(new JLabel("Cidade:"), gbcEndereco);
        campoCidade = new JTextField();
        gbcEndereco.gridx = 1; gbcEndereco.weightx = 1.0;
        panelEndereco.add(campoCidade, gbcEndereco);

        gbcEndereco.gridx = 0; gbcEndereco.gridy = 5; gbcEndereco.weightx = 0.0;
        panelEndereco.add(new JLabel("Estado (UF):"), gbcEndereco);
        campoEstado = new JTextField();
        gbcEndereco.gridx = 1; gbcEndereco.weightx = 1.0;
        panelEndereco.add(campoEstado, gbcEndereco);

        gbcEndereco.gridx = 0; gbcEndereco.gridy = 6; gbcEndereco.weightx = 0.0;
        panelEndereco.add(new JLabel("País:"), gbcEndereco);
        campoPais = new JTextField();
        gbcEndereco.gridx = 1; gbcEndereco.weightx = 1.0;
        panelEndereco.add(campoPais, gbcEndereco);
        gbcEndereco.weightx = 0.0;

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        frameCrudSeguradoPessoa.getContentPane().add(panelEndereco, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0.0;
        gbc.gridwidth = 1;

        JPanel panelBotoesAcao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btIncluirAlterar = new JButton("Incluir");
        panelBotoesAcao.add(btIncluirAlterar);
        btExcluir = new JButton("Excluir");
        panelBotoesAcao.add(btExcluir);
        btCancelar = new JButton("Cancelar");
        panelBotoesAcao.add(btCancelar);
        btLimparCampos = new JButton("Limpar Campos");
        panelBotoesAcao.add(btLimparCampos);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        frameCrudSeguradoPessoa.getContentPane().add(panelBotoesAcao, gbc);

        addListeners();
    }

    private void setInitialState() {
        campoCpf.setEnabled(true);
        campoNome.setEnabled(false);
        campoRenda.setEnabled(false);
        campoDataNascimento.setEnabled(false);
        campoBonus.setEnabled(false);

        campoLogradouro.setEnabled(false);
        campoCep.setEnabled(false);
        campoNumeroEndereco.setEnabled(false);
        campoComplemento.setEnabled(false);
        campoPais.setEnabled(false);
        campoEstado.setEnabled(false);
        campoCidade.setEnabled(false);

        btNovo.setEnabled(true);
        btBuscar.setEnabled(true);
        btIncluirAlterar.setEnabled(false);
        btIncluirAlterar.setText("Incluir");
        btExcluir.setEnabled(false);
        btCancelar.setEnabled(false);
        btLimparCampos.setEnabled(true);

        clearFields();
    }

    private void setFormEnabled(boolean enabled) {
        campoNome.setEnabled(enabled);
        campoRenda.setEnabled(enabled);
        campoDataNascimento.setEnabled(enabled);

        campoLogradouro.setEnabled(enabled);
        campoCep.setEnabled(enabled);
        campoNumeroEndereco.setEnabled(enabled);
        campoComplemento.setEnabled(enabled);
        campoPais.setEnabled(enabled);
        campoEstado.setEnabled(enabled);
        campoCidade.setEnabled(enabled);
    }

    private void clearFields() {
        if (campoCpf.isEnabled()) {
            campoCpf.setText("");
        }
        campoNome.setText("");
        campoRenda.setValue(0.00);
        campoDataNascimento.setValue(null);
        campoDataNascimento.setText("");
        campoBonus.setText("");

        campoLogradouro.setText("");
        campoCep.setValue(null);
        campoCep.setText("");
        campoNumeroEndereco.setText("");
        campoComplemento.setText("");
        campoPais.setText("");
        campoEstado.setText("");
        campoCidade.setText("");
    }

    private void populateFields(SeguradoPessoa segurado) {
        campoNome.setText(segurado.getNome());
        campoDataNascimento.setText(segurado.getDataNascimento() != null ? segurado.getDataNascimento().format(dateFormatter) : "");
        campoRenda.setValue(segurado.getRenda());
        campoBonus.setText(segurado.getBonus() != null ? segurado.getBonus().setScale(2).toString() : "0.00");

        if (segurado.getEndereco() != null) {
            Endereco end = segurado.getEndereco();
            campoLogradouro.setText(end.getLogradouro());
            campoCep.setText(end.getCep());
            campoNumeroEndereco.setText(end.getNumero());
            campoComplemento.setText(end.getComplemento());
            campoCidade.setText(end.getCidade());
            campoEstado.setText(end.getEstado());
            campoPais.setText(end.getPais());
        }
    }

    private SeguradoPessoa getSeguradoFromFields() {
        String cpf = campoCpf.getText().trim();
        String nome = campoNome.getText().trim();
        LocalDate dataNascimento = null;
        String dataNascStr = campoDataNascimento.getText().trim().replace("_", "");

        if (!dataNascStr.isEmpty()) {
            if (dataNascStr.length() == 10 && dataNascStr.matches("\\d{2}/\\d{2}/\\d{4}")) { // Verifica se já está no formato com barras
                try {
                    dataNascimento = LocalDate.parse(dataNascStr, dateFormatter);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(frameCrudSeguradoPessoa, "Formato de Data de Nascimento inválido. Use dd/MM/yyyy.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            } else if (dataNascStr.length() == 8 && dataNascStr.matches("\\d{8}")) { 
                String formattedDate = dataNascStr.substring(0,2) + "/" + dataNascStr.substring(2,4) + "/" + dataNascStr.substring(4,8);
                try {
                    dataNascimento = LocalDate.parse(formattedDate, dateFormatter);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(frameCrudSeguradoPessoa, "Formato de Data de Nascimento inválido. Use dd/MM/yyyy.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            } else {
                JOptionPane.showMessageDialog(frameCrudSeguradoPessoa, "Data de Nascimento incompleta ou inválida.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }


        double renda = 0.0;
        Object rendaObj = campoRenda.getValue();
        if (rendaObj instanceof Number) {
            renda = ((Number) rendaObj).doubleValue();
        } else {
            JOptionPane.showMessageDialog(frameCrudSeguradoPessoa, "Renda deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        BigDecimal bonus = BigDecimal.ZERO;
        if (campoBonus.getText() != null && !campoBonus.getText().isEmpty()) {
            try {
                bonus = new BigDecimal(campoBonus.getText().trim());
            } catch (NumberFormatException e) { /* Usa zero */ }
        }

        String cepValue = campoCep.getText().replace("_", "").replace("-","");

        Endereco endereco = new Endereco(
                campoLogradouro.getText().trim(),
                cepValue,
                campoNumeroEndereco.getText().trim(),
                campoComplemento.getText().trim(),
                campoPais.getText().trim(),
                campoEstado.getText().trim(),
                campoCidade.getText().trim()
        );
        return new SeguradoPessoa(nome, endereco, dataNascimento, bonus, cpf, renda);
    }

    private void addListeners() {
        btNovo.addActionListener(e -> {
            String cpfInput = campoCpf.getText().trim();
            if (cpfInput.isEmpty()) {
                JOptionPane.showMessageDialog(frameCrudSeguradoPessoa, "CPF deve ser informado para Novo.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            SeguradoPessoa existente = mediator.buscarSeguradoPessoa(cpfInput);
            if (existente != null) {
                JOptionPane.showMessageDialog(frameCrudSeguradoPessoa, "Segurado com este CPF já existe.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                campoCpf.setEnabled(false);
                campoNome.setText("");
                campoRenda.setValue(0.00);
                campoDataNascimento.setValue(null);
                campoDataNascimento.setText("");
                campoBonus.setText("0.00");
                campoLogradouro.setText("");
                campoCep.setValue(null);
                campoCep.setText("");
                campoNumeroEndereco.setText("");
                campoComplemento.setText("");
                campoPais.setText("");
                campoEstado.setText("");
                campoCidade.setText("");
                setFormEnabled(true);
                btNovo.setEnabled(false);
                btBuscar.setEnabled(false);
                btIncluirAlterar.setEnabled(true);
                btIncluirAlterar.setText("Incluir");
                btExcluir.setEnabled(false);
                btCancelar.setEnabled(true);
            }
        });

        btBuscar.addActionListener(e -> {
            String cpf = campoCpf.getText().trim();
            if (cpf.isEmpty()) {
                JOptionPane.showMessageDialog(frameCrudSeguradoPessoa, "CPF deve ser informado para busca.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            SeguradoPessoa segurado = mediator.buscarSeguradoPessoa(cpf);
            if (segurado == null) {
                JOptionPane.showMessageDialog(frameCrudSeguradoPessoa, "Segurado não encontrado.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                String cpfBuscado = campoCpf.getText();
                clearFields();
                campoCpf.setText(cpfBuscado);
                campoCpf.setEnabled(true);
            } else {
                populateFields(segurado);
                setFormEnabled(true);
                campoCpf.setEnabled(false);
                btNovo.setEnabled(false);
                btBuscar.setEnabled(false);
                btIncluirAlterar.setEnabled(true);
                btIncluirAlterar.setText("Alterar");
                btExcluir.setEnabled(true);
                btCancelar.setEnabled(true);
            }
        });

        btIncluirAlterar.addActionListener(e -> {
            SeguradoPessoa segurado = getSeguradoFromFields();
            if (segurado == null) return;

            String msg;
            if (btIncluirAlterar.getText().equals("Incluir")) {
                msg = mediator.incluirSeguradoPessoa(segurado);
            } else {
                msg = mediator.alterarSeguradoPessoa(segurado);
            }

            if (msg == null) {
                JOptionPane.showMessageDialog(frameCrudSeguradoPessoa, "Operação realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                setInitialState();
            } else {
                JOptionPane.showMessageDialog(frameCrudSeguradoPessoa, msg, "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
        });

        btExcluir.addActionListener(e -> {
            String cpf = campoCpf.getText().trim();
            int confirm = JOptionPane.showConfirmDialog(frameCrudSeguradoPessoa,
                    "Tem certeza que deseja excluir este segurado?", "Confirmação de Exclusão",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String msg = mediator.excluirSeguradoPessoa(cpf);
                if (msg == null) {
                    JOptionPane.showMessageDialog(frameCrudSeguradoPessoa, "Segurado excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    setInitialState();
                } else {
                    JOptionPane.showMessageDialog(frameCrudSeguradoPessoa, msg, "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btCancelar.addActionListener(e -> {
            setInitialState();
        });

        btLimparCampos.addActionListener(e -> {
            if(campoCpf.isEnabled()) {
                clearFields();
            } else {
                campoNome.setText("");
                campoRenda.setValue(0.00);
                campoDataNascimento.setValue(null);
                campoDataNascimento.setText("");
                campoLogradouro.setText("");
                campoCep.setValue(null);
                campoCep.setText("");
                campoNumeroEndereco.setText("");
                campoComplemento.setText("");
                campoPais.setText("");
                campoEstado.setText("");
                campoCidade.setText("");
            }
        });
    }
}