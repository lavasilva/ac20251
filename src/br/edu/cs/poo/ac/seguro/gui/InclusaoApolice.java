package br.edu.cs.poo.ac.seguro.gui;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.NumberFormat; 

import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;
import br.edu.cs.poo.ac.seguro.mediators.ApoliceMediator;
import br.edu.cs.poo.ac.seguro.mediators.DadosVeiculo;
import br.edu.cs.poo.ac.seguro.mediators.RetornoInclusaoApolice;

public class InclusaoApolice {

    private JFrame frameInclusaoApolice;
    private ApoliceMediator mediator = ApoliceMediator.getInstancia();

    private JTextField campoCpfOuCnpj;
    private JTextField campoPlaca;
    private JFormattedTextField campoAno;
    private JFormattedTextField campoValorMaximoSegurado;
    private JComboBox<String> cmbCategoriaVeiculo;

    private JButton btIncluir;
    private JButton btLimpar;

    private List<CategoriaVeiculo> categoriasOrdenadas;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                InclusaoApolice window = new InclusaoApolice();
                window.frameInclusaoApolice.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public InclusaoApolice() {
        initialize();
    }

    private void initialize() {
        frameInclusaoApolice = new JFrame();
        frameInclusaoApolice.setTitle("Inclusão de Apólice");
        frameInclusaoApolice.setBounds(100, 100, 450, 300);
        frameInclusaoApolice.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameInclusaoApolice.getContentPane().setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // CPF ou CNPJ
        gbc.gridx = 0;
        gbc.gridy = 0;
        frameInclusaoApolice.getContentPane().add(new JLabel("CPF/CNPJ:"), gbc);
        campoCpfOuCnpj = new JTextField();
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        frameInclusaoApolice.getContentPane().add(campoCpfOuCnpj, gbc);

        // Placa
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        frameInclusaoApolice.getContentPane().add(new JLabel("Placa:"), gbc);
        campoPlaca = new JTextField();
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        frameInclusaoApolice.getContentPane().add(campoPlaca, gbc);

        // Ano
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        frameInclusaoApolice.getContentPane().add(new JLabel("Ano:"), gbc);
        try {
            MaskFormatter anoMask = new MaskFormatter("####");
            anoMask.setPlaceholderCharacter('_');
            campoAno = new JFormattedTextField(anoMask);
        } catch (ParseException e) {
            e.printStackTrace();
            campoAno = new JFormattedTextField();
        }
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        frameInclusaoApolice.getContentPane().add(campoAno, gbc);

        // Valor Máximo Segurado
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        frameInclusaoApolice.getContentPane().add(new JLabel("Valor Máx. Segurado:"), gbc);
        NumberFormat valorFormat = DecimalFormat.getNumberInstance();
        NumberFormatter valorFormatter = new NumberFormatter(valorFormat);
        valorFormatter.setValueClass(BigDecimal.class);
        valorFormatter.setAllowsInvalid(false);
        valorFormatter.setCommitsOnValidEdit(true);
        campoValorMaximoSegurado = new JFormattedTextField(valorFormatter);
        campoValorMaximoSegurado.setValue(BigDecimal.ZERO);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        frameInclusaoApolice.getContentPane().add(campoValorMaximoSegurado, gbc);

        // Categoria Veículo
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        frameInclusaoApolice.getContentPane().add(new JLabel("Categoria:"), gbc);
        cmbCategoriaVeiculo = new JComboBox<>();
        popularCategorias();
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        frameInclusaoApolice.getContentPane().add(cmbCategoriaVeiculo, gbc);


        // Buttons Panel
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btIncluir = new JButton("Incluir");
        panelBotoes.add(btIncluir);
        btLimpar = new JButton("Limpar");
        panelBotoes.add(btLimpar);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        frameInclusaoApolice.getContentPane().add(panelBotoes, gbc);

        addListeners();
    }

    private void popularCategorias() {
        CategoriaVeiculo[] todasCategorias = CategoriaVeiculo.values();
        categoriasOrdenadas = new ArrayList<>(Arrays.asList(todasCategorias));
        categoriasOrdenadas.sort(Comparator.comparing(CategoriaVeiculo::getNome));

        for (CategoriaVeiculo cat : categoriasOrdenadas) {
            cmbCategoriaVeiculo.addItem(cat.getNome());
        }
        if (!categoriasOrdenadas.isEmpty()) {
            cmbCategoriaVeiculo.setSelectedIndex(0);
        }
    }

    private void clearFields() {
        campoCpfOuCnpj.setText("");
        campoPlaca.setText("");
        campoAno.setValue(null);
        campoAno.setText("");
        campoValorMaximoSegurado.setValue(BigDecimal.ZERO);
        if (cmbCategoriaVeiculo.getItemCount() > 0) {
            cmbCategoriaVeiculo.setSelectedIndex(0);
        }
    }

    private void addListeners() {
        btIncluir.addActionListener(e -> {
            String cpfOuCnpj = campoCpfOuCnpj.getText().trim();
            String placa = campoPlaca.getText().trim();
            String anoStr = campoAno.getText().trim().replace("_", "");

            int ano;
            BigDecimal valorMaximoSegurado;
            int codigoCategoria = -1;

            try {
                if (anoStr.isEmpty()) {
                    JOptionPane.showMessageDialog(frameInclusaoApolice, "Ano deve ser informado.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ano = Integer.parseInt(anoStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frameInclusaoApolice, "Ano deve ser um número inteiro válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Object valObj = campoValorMaximoSegurado.getValue();
                if (valObj instanceof BigDecimal) {
                    valorMaximoSegurado = (BigDecimal) valObj;
                } else if (valObj instanceof Number) {
                    valorMaximoSegurado = new BigDecimal(((Number) valObj).toString());
                } else {
                    JOptionPane.showMessageDialog(frameInclusaoApolice, "Valor Máximo Segurado deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frameInclusaoApolice, "Valor Máximo Segurado deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int selectedIndex = cmbCategoriaVeiculo.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < categoriasOrdenadas.size()) {
                codigoCategoria = categoriasOrdenadas.get(selectedIndex).getCodigo();
            } else {
                JOptionPane.showMessageDialog(frameInclusaoApolice, "Selecione uma categoria válida.", "Erro de Seleção", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DadosVeiculo dadosVeiculoObj = new DadosVeiculo(cpfOuCnpj, placa, ano, valorMaximoSegurado, codigoCategoria);
            RetornoInclusaoApolice retorno = mediator.incluirApolice(dadosVeiculoObj);

            if (retorno.getMensagemErro() == null) {
                JOptionPane.showMessageDialog(frameInclusaoApolice,
                        "Apólice incluída com sucesso! Anote o número da apólice: " + retorno.getNumeroApolice(),
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(frameInclusaoApolice,
                        retorno.getMensagemErro(),
                        "Erro na Inclusão", JOptionPane.ERROR_MESSAGE);
            }
        });

        btLimpar.addActionListener(e -> {
            clearFields();
        });
    }
}