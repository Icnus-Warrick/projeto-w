/**
 * NomeDaClasse.java
 * 
 * @author Seu Nome
 * @version 1.0.0
 * @since 23/11/2025
 * 
 * Descrição breve do componente.
 * 
 * Histórico de Alterações:
 * (dd/mm/aaaa) - Autor - Descrição da alteração
 */

# Estrutura Base para Componentes W (Swing)

## 1. Estrutura de Pacotes
```
br.com.warrick.swing
├── WComponenteBase.java  // Classe base abstrata
├── WTextField.java
├── WPasswordField.java
├── WComboBox.java
├── WButton.java
└── WCheckBox.java
```

## 2. Documentação do Código

### 2.1 Cabeçalho da Classe
```java
/**
 * NomeDaClasse - Descrição breve do componente
 * 
 * @author Nome do Desenvolvedor
 * @version 1.0.0
 * @since 23/11/2025
 * 
 * @see Classes relacionadas
 * @deprecated (se aplicável)
 */
```

### 2.2 Histórico de Alterações
```java
/**
 * Histórico de Alterações:
 * 
 * 23/11/2025 - Nome - Criação da classe
 * 24/11/2025 - Nome - Adicionado suporte a validação
 * 25/11/2025 - Nome - Corrigido bug na renderização
 */
```

### 2.3 Documentação de Métodos
```java
/**
 * Valida o componente de acordo com as regras definidas
 * 
 * @param valor Valor a ser validado
 * @return true se o valor for válido, false caso contrário
 * @throws IllegalArgumentException Se o valor for inválido
 * @since 1.0.0
 */
public boolean validar(String valor) {
    // Implementação
}
```

## 3. Estrutura da Classe Base

### 3.1 Constantes
```java
// Cores
protected static final Color DEFAULT_COLOR = ...
protected static final Color HOVER_COLOR = ...
protected static final Color ERROR_COLOR = ...

// Tamanhos e Espaçamentos
protected static final int PADDING_TOP = ...
protected static final int PADDING_LEFT = ...
```

### 2.2 Atributos Básicos
```java
protected String labelText;
protected boolean obrigatorio;
protected boolean hasError;
protected String errorMessage;
```

### 2.3 Construtores
```java
public WComponenteBase() { ... }
public WComponenteBase(String label) { ... }
```

### 2.4 Métodos Principais
```java
// Validação
public abstract boolean validar();
protected boolean validarObrigatorio();

// Feedback Visual
public void mostrarErro(String mensagem);
public void mostrarSucesso(String mensagem);
public void limparMensagem();

// Getters/Setters
public String getLabelText();
public void setLabelText(String text);
public boolean isObrigatorio();
public void setObrigatorio(boolean obrigatorio);
```

## 3. Estrutura de Herança
```
JComponent
└── WComponenteBase
    ├── WTextField
    ├── WPasswordField
    ├── WComboBox
    ├── WButton
    └── WCheckBox
```

## 4. Boas Práticas
1. Sobrescrever `paintComponent` para desenho personalizado
2. Implementar `validar()` em cada subclasse
3. Usar constantes para valores reutilizáveis
4. Manter consistência visual entre componentes
5. Documentar métodos públicos com JavaDoc

## 5. Convenções
- Prefixo 'W' para classes de componentes
- Nomes em camelCase
- Métodos em português para API pública
- Uso de constantes para strings e valores mágicos
