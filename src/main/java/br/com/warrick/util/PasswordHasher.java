package br.com.warrick.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Classe utilitária para hashing e verificação de senhas usando BCrypt.
 */
public class PasswordHasher {
    private static final int WORKLOAD = 12; // Custo do hash (maior = mais seguro, porém mais lento)

    /**
     * Gera um hash seguro para a senha fornecida.
     * 
     * @param senha A senha em texto puro
     * @return String com o hash da senha
     */
    public static String hash(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser vazia");
        }
        String salt = BCrypt.gensalt(WORKLOAD);
        return BCrypt.hashpw(senha, salt);
    }

    /**
     * Verifica se uma senha em texto puro corresponde a um hash.
     * 
     * @param senha A senha em texto puro para verificar
     * @param hash O hash armazenado
     * @return true se a senha corresponder ao hash, false caso contrário
     */
    public static boolean verificar(String senha, String hash) {
        if (senha == null || hash == null || !hash.startsWith("$2a$")) {
            return false;
        }
        return BCrypt.checkpw(senha, hash);
    }
}
