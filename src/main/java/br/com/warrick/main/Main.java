package br.com.warrick.main;

import br.com.warrick.view.*;
import com.formdev.flatlaf.*;
import com.formdev.flatlaf.themes.*;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Configura o look and feel do sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Habilita o anti-aliasing para melhorar a qualidade gráfica
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");

            // Registra o tema personalizado do diretório resources
            FlatLaf.registerCustomDefaultsSource("br.com.warrick.themes");

            // Configura o tema
            FlatLightLaf.setup();

            // Força a atualização do UI
            FlatLaf.updateUI();
            // Executa a aplicação na EDT (Event Dispatch Thread)
            SwingUtilities.invokeLater(() -> {
                new Teste_WComponentes().setVisible(true);
            });
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Erro ao iniciar a aplicação: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
