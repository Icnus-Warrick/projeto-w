package br.com.warrick.util;

import java.util.regex.Pattern;

/**
 * Classe utilitária para validação de dados de formulário.
 * Fornece métodos estáticos para validação de dados comuns como e-mail, telefone, etc.
 */
public class ValidacaoUtil {
    // Constantes para validação
    private static final int NOME_MIN_LENGTH = 3;
    private static final int NOME_MAX_LENGTH = 100;
    private static final int EMAIL_MAX_LENGTH = 100;
    private static final int SENHA_MIN_LENGTH = 8;
    
    // Padrões de validação
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    
    private static final Pattern NOME_PATTERN = Pattern.compile(
        "^[\\p{L} .'-]+"
    );
    
    private static final Pattern TELEFONE_PATTERN = Pattern.compile(
        "^(?:\\(?[1-9]{2}\\)? ?(?:[2-8]|9[1-9])[0-9]{3}[-. ]?[0-9]{4}$|^$)"
    );
    
    private static final Pattern SENHA_PATTERN = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).*$"
    );
    
    /**
     * Valida um endereço de e-mail.
     * 
     * @param email O e-mail a ser validado
     * @return true se o e-mail for válido, false caso contrário
     */
    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        if (email.length() > EMAIL_MAX_LENGTH) {
            return false;
        }
        
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Valida um nome (aceita letras, espaços, apóstrofos e hífens).
     * 
     * @param nome O nome a ser validado
     * @return true se o nome for válido, false caso contrário
     */
    public static boolean validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }
        
        String nomeSemEspacos = nome.trim();
        return nomeSemEspacos.length() >= NOME_MIN_LENGTH && 
               nomeSemEspacos.length() <= NOME_MAX_LENGTH &&
               NOME_PATTERN.matcher(nomeSemEspacos).matches();
    }
    
    /**
     * Valida um número de telefone no formato brasileiro.
     * 
     * @param telefone O telefone a ser validado
     * @return true se o telefone for válido, false caso contrário
     */
    public static boolean validarTelefone(String telefone) {
        if (telefone == null) {
            return false;
        }
        
        // Remove espaços em branco extras
        String telefoneLimpo = telefone.trim();
        
        // Aceita string vazia (campo opcional)
        if (telefoneLimpo.isEmpty()) {
            return true;
        }
        
        return TELEFONE_PATTERN.matcher(telefoneLimpo).matches();
    }
    
    /**
     * Remove formatação de telefone, deixando apenas os dígitos.
     * 
     * @param telefone Telefone formatado
     * @return Apenas os dígitos do telefone
     */
    public static String removerFormatacaoTelefone(String telefone) {
        if (telefone == null) {
            return "";
        }
        return telefone.replaceAll("[^0-9]", "");
    }
    
    /**
     * Formata um número de telefone no padrão brasileiro.
     * 
     * @param telefone Telefone sem formatação
     * @return Telefone formatado
     */
    public static String formatarTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            return "";
        }
        
        String numeros = removerFormatacaoTelefone(telefone);
        
        // Formato: (XX) XXXXX-XXXX para celulares
        if (numeros.length() == 11) {
            return String.format("(%s) %s-%s", 
                numeros.substring(0, 2),
                numeros.substring(2, 7),
                numeros.substring(7));
        }
        // Formato: (XX) XXXX-XXXX para telefones fixos
        else if (numeros.length() == 10) {
            return String.format("(%s) %s-%s", 
                numeros.substring(0, 2),
                numeros.substring(2, 6),
                numeros.substring(6));
        }
        
        // Retorna sem formatação se não atender aos padrões
        return telefone;
    }
    
    /**
     * Valida uma senha de acordo com os requisitos de segurança.
     * 
     * @param senha A senha a ser validada
     * @return Mensagem de erro ou null se a senha for válida
     */
    public static String validarSenha(String senha) {
        if (senha == null || senha.length() < SENHA_MIN_LENGTH) {
            return String.format("A senha deve ter no mínimo %d caracteres", SENHA_MIN_LENGTH);
        }
        
        if (!senha.matches(".*[A-Z].*")) {
            return "A senha deve conter pelo menos uma letra maiúscula";
        }
        
        if (!senha.matches(".*[a-z].*")) {
            return "A senha deve conter pelo menos uma letra minúscula";
        }
        
        if (!senha.matches(".*\\d.*")) {
            return "A senha deve conter pelo menos um número";
        }
        
        if (!senha.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            return "A senha deve conter pelo menos um caractere especial";
        }
        
        return null; // Senha válida
    }
}
