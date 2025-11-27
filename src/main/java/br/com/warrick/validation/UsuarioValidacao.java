package br.com.warrick.validation;

import br.com.warrick.model.Usuario;
import java.util.regex.Pattern;

/**
 * Classe responsável por validar os dados de um usuário.
 * Fornece métodos estáticos para validação de diferentes aspectos do usuário.
 */
public class UsuarioValidacao {
    
    // Expressões regulares para validação
    private static final String EMAIL_REGEX = 
        "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String SENHA_REGEX = 
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    
    /**
     * Valida se um email está em um formato válido.
     * @param email O email a ser validado
     * @return true se o email for válido, false caso contrário
     */
    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches(EMAIL_REGEX, email);
    }
    
    /**
     * Valida se uma senha atende aos requisitos de segurança.
     * Requisitos: mínimo 8 caracteres, pelo menos 1 número, 1 letra maiúscula, 
     * 1 letra minúscula e 1 caractere especial.
     * 
     * @param senha A senha a ser validada
     * @return true se a senha for válida, false caso contrário
     */
    public static boolean validarSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches(SENHA_REGEX, senha);
    }
    
    /**
     * Valida se um nome é válido.
     * @param nome O nome a ser validado
     * @return true se o nome for válido, false caso contrário
     */
    public static boolean validarNome(String nome) {
        return nome != null && nome.trim().length() >= 3 && nome.trim().length() <= 100;
    }
    
    /**
     * Valida todos os dados de um usuário.
     * @param usuario O usuário a ser validado
     * @return Um objeto ValidacaoResult contendo o resultado da validação
     */
    public static ValidacaoResult validarUsuario(Usuario usuario) {
        ValidacaoResult resultado = new ValidacaoResult();
        
        if (usuario == null) {
            resultado.adicionarErro("Usuário não pode ser nulo");
            return resultado;
        }
        
        if (!validarNome(usuario.getNome())) {
            resultado.adicionarErro("Nome inválido. Deve conter entre 3 e 100 caracteres.");
        }
        
        if (!validarEmail(usuario.getEmail())) {
            resultado.adicionarErro("Email inválido.");
        }
        
        if (!validarSenha(usuario.getSenha())) {
            resultado.adicionarErro("""
                Senha inválida. A senha deve conter:
                - Mínimo de 8 caracteres
                - Pelo menos 1 número
                - Pelo menos 1 letra maiúscula
                - Pelo menos 1 letra minúscula
                - Pelo menos 1 caractere especial (@#$%^&+=)""");
        }
        return resultado;
    }
}