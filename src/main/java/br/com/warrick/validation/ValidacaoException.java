package br.com.warrick.validation;

/**
 * Exceção lançada quando ocorrem erros de validação.
 */
public class ValidacaoException extends Exception {
    
    /**
     * Cria uma nova exceção com a mensagem especificada.
     * @param message A mensagem de erro
     */
    public ValidacaoException(String message) {
        super(message);
    }
    
    /**
     * Cria uma nova exceção com a mensagem e a causa especificadas.
     * @param message A mensagem de erro
     * @param cause A causa da exceção
     */
    public ValidacaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
