# WTextArea

## Visão Geral

O `WTextArea` é um componente de área de texto avançado que estende `JTextArea` do Java Swing, oferecendo recursos aprimorados de validação, feedback visual e personalização. É ideal para campos de texto de múltiplas linhas, como observações, descrições e outros conteúdos extensos.

## Características Principais

- **Rótulo Flutuante**: Texto de dica que se move suavemente quando o campo recebe foco
- **Validação Integrada**: Suporte a campos obrigatórios e mensagens personalizadas
- **Feedback Visual**: Animações suaves para transições de estado e feedback de validação
- **Temas Personalizáveis**: Suporte a temas FlatLaf com cores personalizáveis
- **Validação em Tempo Real**: Verificação instantânea dos dados inseridos
- **Acessibilidade**: Design acessível com feedback tátil e visual
- **Rolagem Automática**: Suporte integrado a barras de rolagem
- **Quebra de Linha**: Suporte a quebra de linha automática

## Uso Básico

### Instanciação e Configuração
```java
// Criar instância com rótulo
WTextArea observacoes = new WTextArea("Observações");

// Configurações básicas
observacoes.setObrigatorio(true);
observacoes.setToolTipText("Digite suas observações aqui");
observacoes.setLineWrap(true);  // Ativa quebra de linha
observacoes.setWrapStyleWord(true);  // Quebra por palavras inteiras

// Definir número de linhas visíveis
observacoes.setRows(4);

// Adicionar a um container com JScrollPane
JScrollPane scrollPane = new JScrollPane(observacoes);
painel.add(scrollPane);
```

### Validação
```java
// Validação básica
if (observacoes.validar()) {
    // Campo válido, continuar com o processamento
    String texto = observacoes.getText();
    System.out.println("Texto válido: " + texto);
}

// Validação com mensagem personalizada
if (observacoes.getText().trim().length() < 10) {
    observacoes.mostrarErro("As observações devem ter pelo menos 10 caracteres");
} else {
    observacoes.mostrarSucesso("Observações válidas");
}
```

### Tratamento de Eventos
```java
// Ouvinte para alterações no texto
observacoes.getDocument().addDocumentListener(new DocumentListener() {
    @Override
    public void insertUpdate(DocumentEvent e) { validarConteudo(); }
    @Override
    public void removeUpdate(DocumentEvent e) { validarConteudo(); }
    @Override
    public void changedUpdate(DocumentEvent e) { validarConteudo(); }
    
    private void validarConteudo() {
        if (observacoes.getText().trim().length() < 10) {
            observacoes.mostrarErro("Mínimo 10 caracteres");
        } else {
            observacoes.limparMensagem();
        }
    }
});

// Ouvinte para foco perdido
observacoes.addFocusListener(new FocusAdapter() {
    @Override
    public void focusLost(FocusEvent e) {
        if (observacoes.getText().trim().isEmpty() && observacoes.isObrigatorio()) {
            observacoes.mostrarErro("Campo obrigatório");
        }
    }
});
```

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
WTextArea area = new WTextArea("Comentários");
area.setLineColor(new Color(94, 53, 177)); // Roxo
area.setHoverColor(new Color(69, 39, 160));
area.setErrorColor(new Color(220, 53, 69));
```

### Tamanhos e Espaçamentos

Ajuste as dimensões do componente conforme necessário.

| Constante | Descrição | Valor Padrão | Uso |
|-----------|-----------|--------------|-----|
| `PADDING_TOP` | Espaçamento interno superior | `25` | Controla a altura do campo |
| `PADDING_LEFT` | Espaçamento interno esquerdo | `12` | Espaço antes do texto |
| `PADDING_BOTTOM` | Espaçamento interno inferior | `15` | Espaço abaixo do texto |
| `PADDING_RIGHT` | Espaçamento interno direito | `12` | Espaço após o texto |
| `LINE_HEIGHT` | Altura da linha inferior | `1` | Espessura da linha |
| `LINE_Y_OFFSET` | Deslocamento vertical da linha | `14` | Posição vertical da linha |

**Exemplo de Uso:**
```java
// Ajustando espaçamentos
area.setBorder(new EmptyBorder(25, 12, 15, 12)); // top, left, bottom, right
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

### Estado

| Método | Descrição | Retorno |
|--------|-----------|----------|
| `hasError()` | Verifica se há erro de validação | `boolean` |
| `getErrorMessage()` | Retorna a mensagem de erro atual | `String` |
| `isObrigatorio()` | Verifica se o campo é obrigatório | `boolean` |

### Manipulação de Dados

| Método | Descrição | Retorno |
|--------|-----------|----------|
| `getText()` | Retorna o texto atual | `String` |
| `setText(String)` | Define o texto do campo | `void` |
| `append(String)` | Adiciona texto ao final do conteúdo atual | `void` |
| `clear()` | Limpa o conteúdo do campo | `void` |

## Exemplo Completo
```java
// Criação
WTextArea comentarios = new WTextArea("Comentários");
comentarios.setObrigatorio(true);
comentarios.setRows(4);
comentarios.setLineWrap(true);
comentarios.setWrapStyleWord(true);

// Adicionando a um painel com rolagem
JScrollPane scrollPane = new JScrollPane(comentarios);
scrollPane.setBorder(BorderFactory.createEmptyBorder());
panel.add(scrollPane);

// Validação
if (comentarios.getText().trim().isEmpty()) {
    comentarios.mostrarErro("Por favor, insira seus comentários");
} else if (comentarios.getText().length() < 20) {
    comentarios.mostrarErro("Os comentários devem ter pelo menos 20 caracteres");
} else {
    comentarios.mostrarSucesso("Comentário válido");
}
```

## Personalização com FlatLaf

O `WTextArea` é totalmente compatível com o [FlatLaf](https://www.formdev.com/flatlaf/), permitindo personalização completa através de propriedades de tema.

### Propriedades do Tema

| Propriedade | Descrição | Valor Padrão |
|-------------|-----------|--------------|
| `WTextArea.background` | Cor de fundo | `#FFFFFF` |
| `WTextArea.foreground` | Cor do texto | `#212529` |
| `WTextArea.borderColor` | Cor da borda | `#CED4DA` |
| `WTextArea.focusedBorderColor` | Borda quando em foco | `#80BDFF` |
| `WTextArea.hoverBorderColor` | Borda ao passar o mouse | `#B3D7FF` |
| `WTextArea.errorBorderColor` | Borda em caso de erro | `#DC3545` |
| `WTextArea.successBorderColor` | Borda quando válido | `#198754` |
| `WTextArea.hintText` | Cor do texto de dica | `#6C757D` |

### Exemplo de Uso com FlatLaf

```java
// Aplicar tema personalizado
try {
    UIManager.put("WTextArea.background", Color.WHITE);
    UIManager.put("WTextArea.foreground", new Color(33, 37, 41));
    UIManager.put("WTextArea.focusedBorderColor", new Color(13, 110, 253));
    UIManager.setLookAndFeel(new FlatLightLaf());
} catch (Exception ex) {
    ex.printStackTrace();
}
```

## Boas Práticas

1. **Uso de JScrollPane**
   ```java
   // ❌ Sem rolagem (não recomendado para textos longos)
   panel.add(new WTextArea("Comentários"));
   
   // ✅ Com rolagem (recomendado)
   WTextArea area = new WTextArea("Comentários");
   area.setRows(4);
   JScrollPane scrollPane = new JScrollPane(area);
   scrollPane.setBorder(BorderFactory.createEmptyBorder());
   panel.add(scrollPane);
   ```

2. **Validação em Tempo Real**
   ```java
   comentarios.getDocument().addDocumentListener(new DocumentListener() {
       public void changedUpdate(DocumentEvent e) { validarComentario(); }
       public void removeUpdate(DocumentEvent e) { validarComentario(); }
       public void insertUpdate(DocumentEvent e) { validarComentario(); }
       
       private void validarComentario() {
           int length = comentarios.getText().trim().length();
           if (length > 0 && length < 20) {
               comentarios.mostrarErro("Mínimo 20 caracteres");
           } else if (length >= 20) {
               comentarios.limparMensagem();
           }
       }
   });
   ```

3. **Acessibilidade**
   ```java
   // Melhorando acessibilidade
   comentarios.getAccessibleContext().setAccessibleName("Campo de comentários");
   comentarios.getAccessibleContext().setAccessibleDescription("Digite seus comentários aqui");
   comentarios.setMnemonic(KeyEvent.VK_C); // Atalho Alt+C
   ```

4. **Dicas de Uso**
   - Use `setLineWrap(true)` para ativar a quebra de linha automática
   - Use `setWrapStyleWord(true)` para quebrar por palavras inteiras
   - Defina um número razoável de linhas visíveis com `setRows(int)`
   - Considere limitar o tamanho máximo do texto para evitar problemas de desempenho

## Requisitos

### Versão Mínima do Java
- Java 8 ou superior

### Dependências
| Biblioteca | Versão | Finalidade |
|------------|--------|------------|
| `org.pushingpixels:trident` | 1.6.0+ | Animações suaves e transições |
| `com.formdev:flatlaf` | 2.3+ | Suporte a temas modernos e personalização |

## Notas de Versão

### v1.0.0 (27/11/2025)

#### Novos Recursos
- Implementação inicial do componente WTextArea
- Suporte a temas FlatLaf
- Sistema de validação integrado
- Rótulo flutuante com animações
- Feedback visual para erros e sucessos

## Créditos

### Desenvolvimento Original
- **Autor**: Ra Ven
- **Repositório**: [DJ-Raven/raven-project](https://github.com/DJ-Raven/raven-project)
- **Licença**: [MIT License](LICENSE)

### Atualizado por
- **Autor**: Warrick
- **Repositório**: [Icnus-Warrick/projeto-w](https://github.com/Icnus-Warrick/projeto-w)
- **Licença**: [MIT License](LICENSE)

### Melhorias e Manutenção
- **Animações**: Implementação de transições suaves
- **Temas**: Suporte a temas claros e escuros
- **Validação**: Sistema integrado de validação
- **Documentação**: Guias detalhados e exemplos

## Links Úteis

- [Documentação Java - JTextArea](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JTextArea.html)
- [FlatLaf - Text Components](https://www.formdev.com/flatlaf/components/#text-components)
- [Java Tutorial - Text Area](https://docs.oracle.com/javase/tutorial/uiswing/components/textarea.html)

### Contribuições
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e enviar pull requests.
