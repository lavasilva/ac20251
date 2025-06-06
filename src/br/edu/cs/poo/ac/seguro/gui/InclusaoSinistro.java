package br.edu.cs.poo.ac.seguro.gui;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.time.format.DateTimeParseException;

import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import br.edu.cs.poo.ac.seguro.excecoes.ExcecaoValidacaoDados;
import br.edu.cs.poo.ac.seguro.mediators.DadosSinistro;
import br.edu.cs.poo.ac.seguro.mediators.SinistroMediator;

public class InclusaoSinistro {

    private JFrame frameInclusaoSinistro;
    private SinistroMediator mediator = SinistroMediator.getInstancia();

    private JTextField campoPlaca;
    private JFormattedTextField campoDataHoraSinistro;
    private JTextField campoUsuarioRegistro;
    private JFormattedTextField campoValorSinistro;
    private JComboBox<String> cmbTipoSinistro;

    private JButton btIncluir;
    private JButton btLimpar;

    private List<TipoSinistro> tiposSinistroOrdenados;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                InclusaoSinistro window = new InclusaoSinistro();
                window.frameInclusaoSinistro.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public InclusaoSinistro() {
        initialize();
    }

    private void initialize() {
        frameInclusaoSinistro = new JFrame();
        frameInclusaoSinistro.setTitle("Inclusão de Sinistro");
        frameInclusaoSinistro.setBounds(100, 100, 480, 320);
        frameInclusaoSinistro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameInclusaoSinistro.getContentPane().setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Placa
        gbc.gridx = 0;
        gbc.gridy = 0;
        frameInclusaoSinistro.getContentPane().add(new JLabel("Placa:"), gbc);
        campoPlaca = new JTextField();
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        frameInclusaoSinistro.getContentPane().add(campoPlaca, gbc);

        // Data e Hora do Sinistro
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        frameInclusaoSinistro.getContentPane().add(new JLabel("Data/Hora Sinistro (dd/MM/yyyy HH:mm):"), gbc);
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/#### ##:##");
            dateMask.setPlaceholderCharacter('_');
            campoDataHoraSinistro = new JFormattedTextField(dateMask);
        } catch (ParseException e) {
            e.printStackTrace();
            campoDataHoraSinistro = new JFormattedTextField(); // Fallback
        }
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        frameInclusaoSinistro.getContentPane().add(campoDataHoraSinistro, gbc);

        // Usuário Registro
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        frameInclusaoSinistro.getContentPane().add(new JLabel("Usuário Registro:"), gbc);
        campoUsuarioRegistro = new JTextField();
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        frameInclusaoSinistro.getContentPane().add(campoUsuarioRegistro, gbc);

        // Valor do Sinistro
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        frameInclusaoSinistro.getContentPane().add(new JLabel("Valor do Sinistro:"), gbc);
        NumberFormat valorFormat = DecimalFormat.getNumberInstance();
        valorFormat.setMinimumFractionDigits(2);
        valorFormat.setMaximumFractionDigits(2);
        NumberFormatter valorFormatter = new NumberFormatter(valorFormat);
        valorFormatter.setValueClass(Double.class);
        valorFormatter.setAllowsInvalid(false);
        valorFormatter.setCommitsOnValidEdit(true);
        campoValorSinistro = new JFormattedTextField(valorFormatter);
        campoValorSinistro.setValue(0.00);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        frameInclusaoSinistro.getContentPane().add(campoValorSinistro, gbc);

        // Tipo Sinistro
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        frameInclusaoSinistro.getContentPane().add(new JLabel("Tipo de Sinistro:"), gbc);
        cmbTipoSinistro = new JComboBox<>();
        popularTiposSinistro();
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        frameInclusaoSinistro.getContentPane().add(cmbTipoSinistro, gbc);


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
        frameInclusaoSinistro.getContentPane().add(panelBotoes, gbc);

        addListeners();
    }

    private void popularTiposSinistro() {
        TipoSinistro[] todosTipos = TipoSinistro.values();
        tiposSinistroOrdenados = new ArrayList<>(Arrays.asList(todosTipos));
        tiposSinistroOrdenados.sort(Comparator.comparing(TipoSinistro::getNome));

        for (TipoSinistro tipo : tiposSinistroOrdenados) {
            cmbTipoSinistro.addItem(tipo.getNome());
        }
        if (!tiposSinistroOrdenados.isEmpty()) {
            cmbTipoSinistro.setSelectedIndex(0);
        }
    }

    private void clearFields() {
        campoPlaca.setText("");
        campoDataHoraSinistro.setValue(null);
        campoDataHoraSinistro.setText("");
        campoUsuarioRegistro.setText("");
        campoValorSinistro.setValue(0.00);
        if (cmbTipoSinistro.getItemCount() > 0) {
            cmbTipoSinistro.setSelectedIndex(0);
        }
    }

    private void addListeners() {
        btIncluir.addActionListener(e -> {
            String placa = campoPlaca.getText().trim();
            String dataHoraStr = campoDataHoraSinistro.getText().trim();
            String usuarioRegistro = campoUsuarioRegistro.getText().trim();

            LocalDateTime dataHoraSinistro;
            double valorSinistro;
            int codigoTipoSinistro = -1;

            try {
                String cleanDataHoraStr = dataHoraStr.replace("_", "").trim();
                if (!cleanDataHoraStr.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}")) {
                    throw new DateTimeParseException("Formato incompleto ou inválido para data/hora.", cleanDataHoraStr, 0);
                }
                dataHoraSinistro = LocalDateTime.parse(cleanDataHoraStr, dateTimeFormatter);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(frameInclusaoSinistro, "Data/Hora do Sinistro inválida. Use dd/MM/yyyy HH:mm.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Object valObj = campoValorSinistro.getValue();
                if (valObj instanceof Number) {
                    valorSinistro = ((Number) valObj).doubleValue();
                } else {
                    JOptionPane.showMessageDialog(frameInclusaoSinistro, "Valor do Sinistro deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frameInclusaoSinistro, "Valor do Sinistro deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int selectedIndex = cmbTipoSinistro.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < tiposSinistroOrdenados.size()) {
                codigoTipoSinistro = tiposSinistroOrdenados.get(selectedIndex).getCodigo();
            } else {
                JOptionPane.showMessageDialog(frameInclusaoSinistro, "Selecione um tipo de sinistro válido.", "Erro de Seleção", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DadosSinistro dadosSinistroObj = new DadosSinistro(placa, dataHoraSinistro, usuarioRegistro, valorSinistro, codigoTipoSinistro);

            try {
                String numeroSinistroGerado = mediator.incluirSinistro(dadosSinistroObj, LocalDateTime.now());
                JOptionPane.showMessageDialog(frameInclusaoSinistro,
                        "Sinistro incluído com sucesso! Anote o número do sinistro: " + numeroSinistroGerado,
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } catch (ExcecaoValidacaoDados ex) {
                JOptionPane.showMessageDialog(frameInclusaoSinistro,
                        ex.getMessage(),
                        "Erro na Inclusão", JOptionPane.ERROR_MESSAGE);
            }
        });

        btLimpar.addActionListener(e -> {
            clearFields();
        });
    }
}