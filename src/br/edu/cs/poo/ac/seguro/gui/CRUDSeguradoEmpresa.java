package br.edu.cs.poo.ac.seguro.gui;

import javax.swing.*;
import java.awt.*;

import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat; 
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoEmpresaMediator;

public class CRUDSeguradoEmpresa {

    private JFrame frameCrudSeguradoEmpresa;
    private SeguradoEmpresaMediator mediator = SeguradoEmpresaMediator.getInstancia();

    private JTextField campoCnpj;
    private JTextField campoNome;
    private JFormattedTextField campoFaturamento;
    private JFormattedTextField campoDataAbertura;
    private JCheckBox ehLocadora;
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

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                CRUDSeguradoEmpresa window = new CRUDSeguradoEmpresa();
                window.frameCrudSeguradoEmpresa.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CRUDSeguradoEmpresa() {
        initialize();
        setInitialState();
    }

    private void initialize() {
        frameCrudSeguradoEmpresa = new JFrame();
        frameCrudSeguradoEmpresa.setTitle("CRUD Segurado Empresa");
        frameCrudSeguradoEmpresa.setBounds(100, 100, 650, 600);
        frameCrudSeguradoEmpresa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameCrudSeguradoEmpresa.getContentPane().setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        frameCrudSeguradoEmpresa.getContentPane().add(new JLabel("CNPJ:"), gbc);
        campoCnpj = new JTextField();
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        frameCrudSeguradoEmpresa.getContentPane().add(campoCnpj, gbc);
        gbc.weightx = 0.0;

        JPanel panelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        btNovo = new JButton("Novo");
        panelBusca.add(btNovo);
        btBuscar = new JButton("Buscar");
        panelBusca.add(btBuscar);
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        frameCrudSeguradoEmpresa.getContentPane().add(panelBusca, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        frameCrudSeguradoEmpresa.getContentPane().add(new JLabel("Nome:"), gbc);
        campoNome = new JTextField();
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        frameCrudSeguradoEmpresa.getContentPane().add(campoNome, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 2;
        frameCrudSeguradoEmpresa.getContentPane().add(new JLabel("Data Abertura (dd/MM/yyyy):"), gbc);
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            campoDataAbertura = new JFormattedTextField(dateMask);
        } catch (ParseException e) {
            e.printStackTrace();
            campoDataAbertura = new JFormattedTextField();
        }
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        frameCrudSeguradoEmpresa.getContentPane().add(campoDataAbertura, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 3;
        frameCrudSeguradoEmpresa.getContentPane().add(new JLabel("Faturamento:"), gbc);
        NumberFormat faturamentoFormat = DecimalFormat.getNumberInstance();
        faturamentoFormat.setMinimumFractionDigits(2);
        faturamentoFormat.setMaximumFractionDigits(2);
        NumberFormatter faturamentoFormatter = new NumberFormatter(faturamentoFormat);
        faturamentoFormatter.setValueClass(Double.class);
        faturamentoFormatter.setAllowsInvalid(false);
        faturamentoFormatter.setCommitsOnValidEdit(true);
        campoFaturamento = new JFormattedTextField(faturamentoFormatter);
        campoFaturamento.setValue(0.00);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        frameCrudSeguradoEmpresa.getContentPane().add(campoFaturamento, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 4;
        frameCrudSeguradoEmpresa.getContentPane().add(new JLabel("Locadora de Veículos:"), gbc);
        ehLocadora = new JCheckBox();
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        frameCrudSeguradoEmpresa.getContentPane().add(ehLocadora, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 5;
        frameCrudSeguradoEmpresa.getContentPane().add(new JLabel("Bônus:"), gbc);
        campoBonus = new JTextField();
        campoBonus.setEditable(false);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        frameCrudSeguradoEmpresa.getContentPane().add(campoBonus, gbc);
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
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        frameCrudSeguradoEmpresa.getContentPane().add(panelEndereco, gbc);
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
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        frameCrudSeguradoEmpresa.getContentPane().add(panelBotoesAcao, gbc);

        addListeners();
    }

    private void setInitialState() {
        campoCnpj.setEnabled(true);
        campoNome.setEnabled(false);
        campoFaturamento.setEnabled(false);
        campoDataAbertura.setEnabled(false);
        ehLocadora.setEnabled(false);
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
        campoFaturamento.setEnabled(enabled);
        campoDataAbertura.setEnabled(enabled);
        ehLocadora.setEnabled(enabled);

        campoLogradouro.setEnabled(enabled);
        campoCep.setEnabled(enabled);
        campoNumeroEndereco.setEnabled(enabled);
        campoComplemento.setEnabled(enabled);
        campoPais.setEnabled(enabled);
        campoEstado.setEnabled(enabled);
        campoCidade.setEnabled(enabled);
    }

    private void clearFields() {
        if (campoCnpj.isEnabled()) {
            campoCnpj.setText("");
        }
        campoNome.setText("");
        campoFaturamento.setValue(0.00);
        campoDataAbertura.setValue(null);
        campoDataAbertura.setText("");
        ehLocadora.setSelected(false);
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

    private void populateFields(SeguradoEmpresa segurado) {
        campoNome.setText(segurado.getNome());
        campoDataAbertura.setText(segurado.getDataAbertura() != null ? segurado.getDataAbertura().format(dateFormatter) : "");
        campoFaturamento.setValue(segurado.getFaturamento());
        ehLocadora.setSelected(segurado.isEhLocadoraDeVeiculos());
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

    private SeguradoEmpresa getSeguradoFromFields() {
        String cnpj = campoCnpj.getText().trim();
        String nome = campoNome.getText().trim();

        LocalDate dataAbertura = null;
        String dataAberturaStr = campoDataAbertura.getText().trim().replace("_","");
        if (!dataAberturaStr.isEmpty()) {
            if (dataAberturaStr.length() == 10 && dataAberturaStr.matches("\\d{2}/\\d{2}/\\d{4}")) { 
                try {
                    dataAbertura = LocalDate.parse(dataAberturaStr, dateFormatter);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(frameCrudSeguradoEmpresa, "Formato de Data de Abertura inválido. Use dd/MM/yyyy.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            } else if (dataAberturaStr.length() == 8 && dataAberturaStr.matches("\\d{8}")) {  
                String formattedDate = dataAberturaStr.substring(0,2) + "/" + dataAberturaStr.substring(2,4) + "/" + dataAberturaStr.substring(4,8);
                try {
                    dataAbertura = LocalDate.parse(formattedDate, dateFormatter);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(frameCrudSeguradoEmpresa, "Formato de Data de Abertura inválido. Use dd/MM/yyyy.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            } else {
                JOptionPane.showMessageDialog(frameCrudSeguradoEmpresa, "Data de Abertura incompleta ou inválida.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }


        double faturamento = 0.0;
        Object faturamentoObj = campoFaturamento.getValue();
        if (faturamentoObj instanceof Number) {
            faturamento = ((Number)faturamentoObj).doubleValue();
        } else {
            JOptionPane.showMessageDialog(frameCrudSeguradoEmpresa, "Faturamento deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        boolean ehLocadora2 = ehLocadora.isSelected();
        BigDecimal bonus = BigDecimal.ZERO;
        if(campoBonus.getText() != null && !campoBonus.getText().isEmpty()){
            try {
                bonus = new BigDecimal(campoBonus.getText().trim());
            } catch (NumberFormatException e) { /* Default to zero */ }
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
        return new SeguradoEmpresa(nome, endereco, dataAbertura, bonus, cnpj, faturamento, ehLocadora2);
    }

    private void addListeners() {
        btNovo.addActionListener(e -> {
            String cnpjInput = campoCnpj.getText().trim();
            if (cnpjInput.isEmpty()) {
                JOptionPane.showMessageDialog(frameCrudSeguradoEmpresa, "CNPJ deve ser informado para Novo.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            SeguradoEmpresa existente = mediator.buscarSeguradoEmpresa(cnpjInput);
            if (existente != null) {
                JOptionPane.showMessageDialog(frameCrudSeguradoEmpresa, "Segurado com este CNPJ já existe.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                campoCnpj.setEnabled(false);
                campoNome.setText("");
                campoFaturamento.setValue(0.00);
                campoDataAbertura.setValue(null);
                campoDataAbertura.setText("");
                ehLocadora.setSelected(false);
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
            String cnpj = campoCnpj.getText().trim();
            if (cnpj.isEmpty()) {
                JOptionPane.showMessageDialog(frameCrudSeguradoEmpresa, "CNPJ deve ser informado para busca.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            SeguradoEmpresa segurado = mediator.buscarSeguradoEmpresa(cnpj);
            if (segurado == null) {
                JOptionPane.showMessageDialog(frameCrudSeguradoEmpresa, "Segurado não encontrado.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                String cnpjBuscado = campoCnpj.getText();
                clearFields();
                campoCnpj.setText(cnpjBuscado);
                campoCnpj.setEnabled(true);
            } else {
                populateFields(segurado);
                setFormEnabled(true);
                campoCnpj.setEnabled(false);
                btNovo.setEnabled(false);
                btBuscar.setEnabled(false);
                btIncluirAlterar.setEnabled(true);
                btIncluirAlterar.setText("Alterar");
                btExcluir.setEnabled(true);
                btCancelar.setEnabled(true);
            }
        });

        btIncluirAlterar.addActionListener(e -> {
            SeguradoEmpresa segurado = getSeguradoFromFields();
            if (segurado == null) return;

            String msg;
            if (btIncluirAlterar.getText().equals("Incluir")) {
                msg = mediator.incluirSeguradoEmpresa(segurado);
            } else {
                msg = mediator.alterarSeguradoEmpresa(segurado);
            }

            if (msg == null) {
                JOptionPane.showMessageDialog(frameCrudSeguradoEmpresa, "Operação realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                setInitialState();
            } else {
                JOptionPane.showMessageDialog(frameCrudSeguradoEmpresa, msg, "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
        });

        btExcluir.addActionListener(e -> {
            String cnpj = campoCnpj.getText().trim();
            int confirm = JOptionPane.showConfirmDialog(frameCrudSeguradoEmpresa,
                    "Tem certeza que deseja excluir este segurado?", "Confirmação de Exclusão",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String msg = mediator.excluirSeguradoEmpresa(cnpj);
                if (msg == null) {
                    JOptionPane.showMessageDialog(frameCrudSeguradoEmpresa, "Segurado excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    setInitialState();
                } else {
                    JOptionPane.showMessageDialog(frameCrudSeguradoEmpresa, msg, "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btCancelar.addActionListener(e -> {
            setInitialState();
        });

        btLimparCampos.addActionListener(e -> {
            if(campoCnpj.isEnabled()) {
                clearFields();
            } else {
                campoNome.setText("");
                campoFaturamento.setValue(0.00);
                campoDataAbertura.setValue(null);
                campoDataAbertura.setText("");
                ehLocadora.setSelected(false);
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