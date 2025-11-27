# WComboBox

## Visão Geral

O `WComboBox` é um componente de seleção avançado que estende `JComboBox` do Java Swing, oferecendo recursos aprimorados de validação, feedback visual e personalização.

## Características Principais

- **Rótulo Flutuante**: Texto de dica que se move suavemente quando uma seleção é feita
- **Validação Integrada**: Suporte a seleção obrigatória e mensagens personalizadas
- **Feedback Visual**: Animações suaves para transições de estado e feedback de seleção
- **Temas Personalizáveis**: Suporte a temas FlatLaf com cores personalizáveis
- **Validação em Tempo Real**: Verificação instantânea da seleção
- **Acessibilidade**: Design acessível com feedback tátil e visual
- **Suporte a Ícones**: Exibição de ícones ao lado dos itens da lista

## Uso Básico

### Instanciação e Configuração
```java
// Criar instância com itens
String[] opcoes = {"Selecione...", "Opção 1", "Opção 2", "Opção 3"};
WComboBox<String> combo = new WComboBox<>(opcoes);
combo.setLabelText("Selecione uma opção");

// Configurações básicas
combo.setObrigatorio(true);
combo.setToolTipText("Selecione uma opção da lista");

// Adicionar a um container
JPanel painel = new JPanel();
painel.add(combo);
```

### Validação
```java
// Validação básica
if (combo.validar()) {
    // Seleção válida, continuar com o processamento
    String selecionado = (String) combo.getSelectedItem();
    System.out.println("Opção selecionada: " + selecionado);
}

// Validação com mensagem personalizada
if (combo.getSelectedIndex() <= 0) {
    combo.mostrarErro("Selecione uma opção");
} else {
    combo.limparMensagem();
}
```

### Tratamento de Eventos
```java
// Ouvinte de mudança de seleção
combo.addActionListener(e -> {
    if (combo.getSelectedIndex() > 0) {
        String selecionado = (String) combo.getSelectedItem();
        System.out.println("Item selecionado: " + selecionado);
        combo.limparMensagem();
    }
});

// Validação ao perder o foco
combo.addFocusListener(new FocusAdapter() {
    @Override
    public void focusLost(FocusEvent e) {
        if (combo.getSelectedIndex() <= 0 && combo.isObrigatorio()) {
            combo.mostrarErro("Selecione uma opção");
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
| `textColor` | Cor do texto selecionado | `new Color(50, 50, 50)` | `setForeground(Color.DARK_GRAY)` |
| `hintColor` | Cor do texto de dica (placeholder) | `new Color(150, 150, 150)` | - |
| `bgColor` | Cor de fundo do campo | `Color.WHITE` | `setBackground(Color.WHITE)` |
| `lineBgColor` | Cor da linha quando sem foco | `new Color(200, 200, 200)` | - |
| `errorColor` | Cor das mensagens de erro | `new Color(220, 53, 69)` | `mostrarErro("Erro")` |
| `successColor` | Cor das mensagens de sucesso | `new Color(40, 167, 69)` | `mostrarSucesso("OK")` |

**Exemplo de Uso:**
```java
// Personalizando cores
WComboBox<String> combo = new WComboBox<>("Categoria");
combo.setLineColor(new Color(0, 123, 255)); // Azul Bootstrap
combo.setHoverColor(new Color(0, 86, 179));
combo.setErrorColor(new Color(220, 53, 69));
```

### Tamanhos e Espaçamentos

Ajuste as dimensões do componente conforme necessário.

| Constante | Descrição | Valor Padrão | Uso |
|-----------|-----------|--------------|-----|
| `PADDING_TOP` | Espaçamento interno superior | `20` | Controla a altura do campo |
| `PADDING_LEFT` | Espaçamento interno esquerdo | `10` | Espaço antes do texto |
| `PADDING_BOTTOM` | Espaçamento interno inferior | `15` | Espaço abaixo do texto |
| `PADDING_RIGHT` | Espaçamento interno direito | `30` | Espaço para o ícone da seta |
| `LINE_HEIGHT` | Altura da linha inferior | `1` | Espessura da linha |
| `LINE_Y_OFFSET` | Deslocamento vertical da linha | `14` | Posição vertical da linha |

**Exemplo de Uso:**
```java
// Ajustando espaçamentos
combo.setBorder(new EmptyBorder(15, 10, 10, 30)); // top, left, bottom, right
```

### Configurações de Exibição

| Propriedade | Descrição | Valor Padrão | Exemplo |
|-------------|-----------|--------------|---------|
| `obrigatorio` | Se a seleção é obrigatória | `false` | `setObrigatorio(true)` |
| `labelText` | Texto do rótulo flutuante | `""` | `setLabelText("Categoria")` |
| `editable` | Se o campo pode ser editado | `false` | `setEditable(true)` |

**Dicas de Uso:**
- Use `setObrigatorio(true)` para campos obrigatórios
- Defina `setEditable(true)` para permitir busca nos itens
- Atualize o `labelText` dinamicamente conforme necessário

## Métodos Principais

### Validação

| Método | Descrição | Retorno | Lança |
|--------|-----------|----------|-------|
| `setObrigatorio(boolean)` | Define se a seleção é obrigatória | `void` | - |
| `validar()` | Valida se uma opção foi selecionada | `boolean` | - |
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
| `setEditable(boolean)` | Permite edição do campo de texto | `editable`: `true` para permitir edição | `void` |

### Estado

| Método | Descrição | Retorno |
|--------|-----------|----------|
| `hasError()` | Verifica se há erro de validação | `boolean` |
| `getErrorMessage()` | Retorna a mensagem de erro atual | `String` |
| `isObrigatorio()` | Verifica se o campo é obrigatório | `boolean` |

### Manipulação de Dados

| Método | Descrição | Retorno | Lança |
|--------|-----------|----------|-------|
| `getSelectedItem()` | Retorna o item selecionado | `Object` | - |
| `setSelectedItem(Object)` | Define o item selecionado | `void` | - |
| `getSelectedIndex()` | Retorna o índice do item selecionado | `int` | - |
| `setSelectedIndex(int)` | Seleciona o item no índice especificado | `void` | `IllegalArgumentException` |
| `addItem(Object)` | Adiciona um item à lista | `void` | - |
| `removeItem(Object)` | Remove um item da lista | `void` | - |
| `removeAllItems()` | Remove todos os itens da lista | `void` | - |

**Notas de Uso:**
- Use `setObrigatorio(true)` para campos obrigatórios
- Para validações personalizadas, use `validarComMensagem()` ou faça a validação manualmente
- Sempre forneça feedback claro ao usuário com `mostrarErro()` ou `mostrarSucesso()`

## Exemplo Completo
```java
// Criação
WComboBox<String> comboEstado = new WComboBox<>("Estado");
comboEstado.addItem("Selecione...");
comboEstado.addItem("São Paulo");
comboEstado.addItem("Rio de Janeiro");
comboEstado.addItem("Minas Gerais");
comboEstado.setObrigatorio(true);

// Validação
if (comboEstado.getSelectedIndex() <= 0) {
    comboEstado.mostrarErro("Selecione um estado");
} else {
    comboEstado.mostrarSucesso("Estado selecionado");
}
```

## Personalização

### Temas FlatLaf
```properties
# Cores do WComboBox no tema
WComboBox.lineColor=#039bd8       # Linha (foco)
WComboBox.hoverColor=#64b4dc      # Mouse sobre
WComboBox.textColor=@foreground   # Cor do texto
WComboBox.hintColor=@hint         # Texto de dica
WComboBox.bgColor=@background     # Fundo
WComboBox.lineBgColor=#C8C8C8     # Linha normal
WComboBox.errorColor=#dc3545      # Erro
WComboBox.successColor=#28a745    # Sucesso
```

## Recomendações de Uso

### Validação de Formulários
```java
// Exemplo de validação em cascata
if (!comboCategoria.validar()) return;
if (!comboSubcategoria.validar()) return;
if (!comboProduto.validar()) return;

// Se todas as validações passarem
processarPedido();
```

### Carregamento Assíncrono
```java
// Carregar itens em segundo plano
SwingWorker<List<String>, Void> worker = new SwingWorker<>() {
    @Override
    protected List<String> doInBackground() {
        // Simular carregamento
        try { Thread.sleep(1000); } catch (Exception e) {}
        return Arrays.asList("Item 1", "Item 2", "Item 3");
    }
    
    @Override
    protected void done() {
        try {
            combo.removeAllItems();
            combo.addItem("Selecione..."); // Item vazio
            for (String item : get()) {
                combo.addItem(item);
            }
        } catch (Exception e) {
            combo.mostrarErro("Erro ao carregar itens");
        }
    }
};
worker.execute();
```

## Boas Práticas

1. **Sempre inclua um item vazio ou padrão**
   ```java
   // ❌ Evitar
   combo.addItem("Opção 1");
   combo.addItem("Opção 2");
   
   // ✅ Preferir
   combo.addItem("Selecione...");
   combo.addItem("Opção 1");
   combo.addItem("Opção 2");
   combo.setSelectedIndex(0); // Seleciona o item vazio
   ```

2. **Validação de Seleção**
   ```java
   // Verificar se uma opção válida foi selecionada
   if (combo.getSelectedIndex() <= 0) {
       combo.mostrarErro("Selecione uma opção válida");
       return;
   }
   ```

3. **Acessibilidade**
   ```java
   // Melhorando acessibilidade
   combo.getAccessibleContext().setAccessibleName("Categoria");
   combo.getAccessibleContext().setAccessibleDescription("Selecione uma categoria da lista");
   combo.setMnemonic(KeyEvent.VK_C); // Atalho Alt+C
   ```

4. **Agrupamento Lógico**
   ```java
   // Agrupar combos relacionados visualmente
   JPanel painelEndereco = new JPanel(new GridLayout(0, 1, 0, 5));
   painelEndereco.setBorder(BorderFactory.createTitledBorder("Endereço"));
   painelEndereco.add(new JLabel("Estado:"));
   painelEndereco.add(comboEstado);
   painelEndereco.add(new JLabel("Cidade:"));
   painelEndereco.add(comboCidade);
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

### v3.2.2 (26/11/2025)

#### Melhorias
- Implementação inicial do componente WComboBox
- Suporte a temas FlatLaf
- Animações de transição suaves
- Sistema de validação integrado

#### Correções
- Ajuste no alinhamento do rótulo flutuante
- Melhoria na renderização em diferentes temas

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
