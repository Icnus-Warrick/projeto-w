# WTextField

## Visão Geral

O `WTextField` é um componente de texto avançado que estende `JTextField` do Java Swing, oferecendo recursos aprimorados de validação, feedback visual e personalização.

## Características Principais

- **Rótulo Flutuante**: Texto de dica que se move suavemente quando o campo recebe foco
- **Validação Integrada**: Suporte a validação de campos obrigatórios e mensagens personalizadas
- **Feedback Visual**: Animações suaves para transições de estado e feedback de validação
- **Temas Personalizáveis**: Suporte a temas FlatLaf com cores personalizáveis
- **Validação em Tempo Real**: Verificação instantânea dos dados inseridos
- **Acessibilidade**: Design acessível com feedback tátil e visual
- **Máscaras de Entrada**: Suporte a formatação de dados com padrões personalizáveis

## Uso Básico

### Instanciação e Configuração
```java
// Criar instância com rótulo
WTextField campo = new WTextField("E-mail");

// Configurações básicas
campo.setObrigatorio(true);
campo.setToolTipText("Digite seu e-mail");

// Adicionar a um container
JPanel painel = new JPanel();
painel.add(campo);
```

### Validação
```java
// Validação básica
if (campo.validar()) {
    // Campo válido, continuar com o processamento
    String valor = campo.getText();
    System.out.println("Valor válido: " + valor);
}

// Validação com mensagem personalizada
if (campo.getText().trim().isEmpty()) {
    campo.mostrarErro("Este campo é obrigatório");
} else if (!validarFormatoEmail(campo.getText())) {
    campo.mostrarErro("Formato de e-mail inválido");
} else {
    campo.mostrarSucesso("E-mail válido");
}
```

### Tratamento de Eventos
```java
// Ouvinte para tecla pressionada
campo.addKeyListener(new KeyAdapter() {
    @Override
    public void keyReleased(KeyEvent e) {
        // Validação em tempo real
        if (campo.getText().trim().isEmpty()) {
            campo.mostrarErro("Campo obrigatório");
        } else {
            campo.limparMensagem();
        }
    }
});

// Ouvinte para foco perdido
campo.addFocusListener(new FocusAdapter() {
    @Override
    public void focusLost(FocusEvent e) {
        if (campo.getText().trim().isEmpty() && campo.isObrigatorio()) {
            campo.mostrarErro("Campo obrigatório");
        }
    }
});
```

### Métodos Úteis

| Método | Descrição |
|--------|-----------|
| `setObrigatorio(boolean)` | Define se o campo é obrigatório |
| `validar()` | Valida o campo (retorna true se válido) |
| `mostrarErro(String)` | Exibe mensagem de erro |
| `mostrarSucesso(String)` | Exibe mensagem de sucesso |
| `limparMensagem()` | Remove mensagens de erro/sucesso |
| `hasError()` | Verifica se há erro de validação |
| `getText()` | Obtém o texto digitado |
| `setText(String)` | Define o texto do campo |

## Propriedades Personalizáveis

### Cores

Personalize as cores do componente para combinar com o tema do seu aplicativo.

| Propriedade | Descrição | Valor Padrão | Exemplo |
|-------------|-----------|--------------|---------|
| `lineColor` | Cor da linha quando o campo está em foco | `new Color(3, 155, 216)` | `setLineColor(Color.BLUE)` |
| `hoverColor` | Cor ao passar o mouse sobre o campo | `new Color(100, 180, 220)` | `setHoverColor(new Color(100, 180, 220))` |
| `textColor` | Cor do texto digitado | `new Color(50, 50, 50)` | `setForeground(Color.DARK_GRAY)` |
| `hintColor` | Cor do texto de dica (placeholder) | `new Color(150, 150, 150)` | - |
| `bgColor` | Cor de fundo do campo | `Color.WHITE` | `setBackground(Color.WHITE)` |
| `lineBgColor` | Cor da linha quando sem foco | `new Color(200, 200, 200)` | - |
| `errorColor` | Cor das mensagens de erro | `new Color(220, 53, 69)` | `mostrarErro("Erro")` |
| `successColor` | Cor das mensagens de sucesso | `new Color(40, 167, 69)` | `mostrarSucesso("OK")` |

**Exemplo de Uso:**
```java
// Personalizando cores
WTextField campo = new WTextField("Usuário");
campo.setLineColor(new Color(0, 123, 255)); // Azul Bootstrap
campo.setHoverColor(new Color(0, 86, 179));
campo.setErrorColor(new Color(220, 53, 69));
```

### Tamanhos e Espaçamentos

Ajuste as dimensões do componente conforme necessário.

| Constante | Descrição | Valor Padrão | Uso |
|-----------|-----------|--------------|-----|
| `PADDING_TOP` | Espaçamento interno superior | `20` | Controla a altura do campo |
| `PADDING_LEFT` | Espaçamento interno esquerdo | `10` | Espaço antes do texto |
| `PADDING_BOTTOM` | Espaçamento interno inferior | `15` | Espaço abaixo do texto |
| `PADDING_RIGHT` | Espaçamento interno direito | `10` | Espaço após o texto |
| `LINE_HEIGHT` | Altura da linha inferior | `1` | Espessura da linha |
| `LINE_Y_OFFSET` | Deslocamento vertical da linha | `14` | Posição vertical da linha |

**Exemplo de Uso:**
```java
// Ajustando espaçamentos
campo.setBorder(new EmptyBorder(15, 10, 10, 10)); // top, left, bottom, right

// Aumentando a altura da linha de foco
campo.putClientProperty("JComponent.arcWidth", 10);
campo.putClientProperty("JComponent.arcHeight", 10);
```

### Configurações de Exibição

| Propriedade | Descrição | Valor Padrão | Exemplo |
|-------------|-----------|--------------|---------|
| `obrigatorio` | Se o campo é obrigatório | `false` | `setObrigatorio(true)` |
| `labelText` | Texto do rótulo flutuante | `""` | `setLabelText("Usuário")` |

**Dicas de Uso:**
- Use `setObrigatorio(true)` para campos obrigatórios
- Atualize o `labelText` dinamicamente conforme necessário
- Para campos desabilitados, use `setEnabled(false)`

**Exemplo Completo:**
```java
// Criando campo com configurações personalizadas
WTextField email = new WTextField("E-mail");
email.setObrigatorio(true);
email.setLineColor(new Color(94, 53, 177)); // Roxo
email.setHoverColor(new Color(69, 39, 160));
email.setToolTipText("Digite seu e-mail");
email.setBorder(new EmptyBorder(20, 10, 10, 10));
```

## Métodos Principais

### Validação

| Método | Descrição | Retorno | Lança |
|--------|-----------|----------|-------|
| `setObrigatorio(boolean)` | Define se o campo é obrigatório | `void` | - |
| `validar()` | Valida o campo (verifica obrigatoriedade) | `boolean` | - |
| `validarComMensagem(String)` | Valida com mensagem personalizada | `boolean` | - |
| `mostrarErro(String)` | Exibe uma mensagem de erro abaixo do campo | `void` | - |
| `mostrarSucesso(String)` | Exibe uma mensagem de sucesso abaixo do campo | `void` | - |
| `limparMensagem()` | Remove mensagens de erro/sucesso do campo | `void` | - |

### Aparência

| Método | Descrição | Parâmetros | Retorno |
|--------|-----------|------------|---------|
| `setLabelText(String)` | Define o texto do rótulo flutuante | `text`: Texto a ser exibido | `void` |
| `setLineColor(Color)` | Define a cor da linha de foco | `color`: Cor desejada | `void` |
| `setHoverColor(Color)` | Define a cor de destaque ao passar o mouse | `color`: Cor desejada | `void` |
| `setShowAndHide(boolean)` | Habilita/desabilita o botão de mostrar/esconder | `show`: `true` para mostrar o botão | `void` |

### Estado

| Método | Descrição | Retorno |
|--------|-----------|----------|
| `hasError()` | Verifica se há erro de validação | `boolean` |
| `getErrorMessage()` | Retorna a mensagem de erro atual | `String` |

### Manipulação de Dados

| Método | Descrição | Retorno | Lança |
|--------|-----------|----------|-------|
| `getText()` | Retorna o texto atual | `String` | - |
| `setText(String)` | Define o texto do campo | `void` | - |
| `clear()` | Limpa o conteúdo do campo | `void` | - |

**Notas de Uso:**
- Use `setObrigatorio(true)` para campos obrigatórios
- Para validações personalizadas, use `validarComMensagem()` ou faça a validação manualmente
- Sempre forneça feedback claro ao usuário com `mostrarErro()` ou `mostrarSucesso()`

## Exemplo Completo
```java
// Criação
WTextField email = new WTextField("E-MAIL");
email.setObrigatorio(true);

// Validação
if (email.getText().trim().isEmpty()) {
    email.mostrarErro("E-mail é obrigatório");
} else if (!ValidacaoUtil.validarEmail(email.getText())) {
    email.mostrarErro("Formato de e-mail inválido");
} else {
    email.mostrarSucesso("E-mail válido");
}
```

## Personalização

### Temas FlatLaf
```properties
# Cores do WTextField no tema
WTextField.lineColor=#039bd8       # Linha (foco)
WTextField.hoverColor=#64b4dc      # Mouse sobre
WTextField.textColor=@foreground   # Cor do texto
WTextField.hintColor=@hint         # Texto de dica
WTextField.bgColor=@background     # Fundo
WTextField.lineBgColor=#C8C8C8     # Linha normal
WTextField.errorColor=#dc3545      # Erro
WTextField.successColor=#28a745    # Sucesso
```
## Recomendações de Uso

### Validação de Formulários
```java
// Exemplo de validação em cascata
if (!campoNome.validar()) return;
if (!campoEmail.validar()) return;
if (!campoSenha.validar()) return;

// Se todas as validações passarem
salvarDados();
```

### Máscaras de Entrada
```java
// Aplicar máscara para CPF
campoCPF.setDocument(new FixedLengthDocument(11)); // Apenas números
campoCPF.addKeyListener(new KeyAdapter() {
    public void keyReleased(KeyEvent e) {
        String text = campoCPF.getText().replaceAll("[^0-9]", "");
        if (text.length() == 11) {
            campoCPF.setText(
                text.substring(0, 3) + "." +
                text.substring(3, 6) + "." +
                text.substring(6, 9) + "-" +
                text.substring(9)
            );
        }
    }
});
```

## Boas Práticas

1. **Validação em Tempo Real**
   ```java
   // Validar e-mail enquanto digita
   campoEmail.getDocument().addDocumentListener(new DocumentListener() {
       public void changedUpdate(DocumentEvent e) { validarEmail(); }
       public void removeUpdate(DocumentEvent e) { validarEmail(); }
       public void insertUpdate(DocumentEvent e) { validarEmail(); }
       
       private void validarEmail() {
           String email = campoEmail.getText();
           if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
               campoEmail.mostrarErro("Formato de e-mail inválido");
           } else {
               campoEmail.limparMensagem();
           }
       }
   });
   ```

2. **Foco Automático**
   ```java
   // Focar automaticamente no primeiro campo
   SwingUtilities.invokeLater(() -> campoNome.requestFocusInWindow());
   ```

3. **Navegação por Teclado**
   ```java
   // Permitir navegação por Tab/Shift+Tab
   campo1.setNextFocusableComponent(campo2);
   campo2.setNextFocusableComponent(campo3);
   // ...
   ```

4. **Acessibilidade**
   ```java
   // Melhorando acessibilidade
   campoNome.getAccessibleContext().setAccessibleName("Nome completo");
   campoNome.getAccessibleContext().setAccessibleDescription("Digite seu nome completo");
   campoNome.setMnemonic(KeyEvent.VK_N); // Atalho Alt+N
   ```

## Requisitos

### Versão Mínima do Java
- Java 8 ou superior

### Dependências
| Biblioteca | Versão | Finalidade |
|------------|--------|------------|
| `org.pushingpixels:trident` | 1.6.0+ | Animações suaves e transições |
| `com.formdev:flatlaf` | 2.3+ | Suporte a temas modernos e personalização |

### Dependências Opcionais
- `com.formdev:flatlaf-extras` - Para componentes adicionais do FlatLaf
- `com.formdev:svgSalamander` - Para suporte a ícones SVG

## Notas de Versão

### v3.2.2 (25/11/2025)

#### Melhorias
- Aprimoramento na animação do rótulo flutuante
- Suporte a temas personalizados aprimorado

#### Performance
- Otimização no carregamento de estilos
- Redução no consumo de memória

### v3.0.0 (23/11/2025)

#### Novos Recursos
- Implementação da validação integrada
- Sistema de animações de transição

#### Melhorias
- Melhor suporte a acessibilidade
- Documentação completa da API

## Créditos

### Desenvolvimento Original
- **Autor**: Ra Ven
- **Repositório**: [DJ-Raven/raven-project](https://github.com/DJ-Raven/raven-project)
- **Licença**: [MIT License](LICENSE)

### Melhorias e Manutenção
- **Animações**: Implementação de transições suaves
- **Temas**: Suporte a temas claros e escuros
- **Migração para Trident**: Atualização do sistema de animações
- **Sistema de Mensagens**: Feedback visual para validações
- **Validação Integrada**: Suporte a campos obrigatórios e validações personalizadas
- **Documentação**: Criação e manutenção da documentação

### Contribuições
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e enviar pull requests.
