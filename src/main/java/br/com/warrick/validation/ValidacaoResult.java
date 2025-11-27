package br.com.warrick.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe que representa o resultado de uma validação.
 * Armazena os erros de validação e fornece métodos para verificar se a validação foi bem-sucedida.
 */
public class ValidacaoResult {
    private final List<String> erros;
    
    public ValidacaoResult() {
        this.erros = new ArrayList<>();
    }
    
    /**
     * Adiciona uma mensagem de erro ao resultado da validação.
     * @param erro A mensagem de erro a ser adicionada
     */
    public void adicionarErro(String erro) {
        if (erro != null && !erro.trim().isEmpty()) {
            erros.add(erro);
        }
    }
    
    /**
     * Verifica se a validação foi bem-sucedida (sem erros).
     * @return true se não houver erros, false caso contrário
     */
    public boolean isValido() {
        return erros.isEmpty();
    }
    
    /**
     * Retorna uma lista imutável contendo todas as mensagens de erro.
     * @return Lista de mensagens de erro
     */
    public List<String> getErros() {
        return Collections.unmodifiableList(erros);
    }
    
    /**
     * Retorna todas as mensagens de erro como uma única string, separadas por quebras de linha.
     * @return String contendo todas as mensagens de erro
     */
    public String getMensagemErro() {
        return String.join("\n", erros);
    }
    
    /**
     * Lança uma exceção com as mensagens de erro se a validação falhou.
     * @throws ValidacaoException Se houver erros de validação
     */
    public void lancarErroSeInvalido() throws ValidacaoException {
        if (!isValido()) {
            throw new ValidacaoException(getMensagemErro());
        }
    }
}
