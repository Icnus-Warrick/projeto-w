# WPasswordField

## Visão Geral

O `WPasswordField` é um componente de entrada de senha avançado que estende `JPasswordField` do Java Swing, oferecendo recursos aprimorados de segurança, validação e feedback visual.

## Características Principais

- **Rótulo Flutuante**: Texto de dica que se move suavemente quando o campo recebe foco
- **Mostrar/Esconder Senha**: Botão integrado para alternar a visibilidade da senha
- **Validação Integrada**: Suporte a validação de campos obrigatórios e mensagens personalizadas
- **Feedback Visual**: Animações suaves para transições de estado e feedback de validação
- **Temas Personalizáveis**: Suporte a temas FlatLaf com cores personalizáveis
- **Validação em Tempo Real**: Verificação instantânea dos dados inseridos
- **Acessibilidade**: Design acessível com feedback tátil e visual
- **Segurança Aprimorada**: Controle de força da senha e dicas visuais

## Uso Básico

### Instanciação e Configuração
```java
// Criar instância com rótulo
WPasswordField senhaField = new WPasswordField("Senha");

// Configurações básicas
senhaField.setObrigatorio(true);
senhaField.setToolTipText("Digite sua senha (mínimo 6 caracteres)");
senhaField.setShowAndHide(true); // Mostrar botão de visualização

// Adicionar a um container
JPanel painel = new JPanel();
painel.setLayout(new BorderLayout());
painel.add(senhaField, BorderLayout.CENTER);
```

### Validação
```java
// Validação básica
if (senhaField.validar()) {
    // Senha válida, continuar com o processamento
    char[] senha = senhaField.getPassword();
    System.out.println("Senha válida!");
}

// Validação personalizada
char[] senha = senhaField.getPassword();
if (senha.length == 0) {
    senhaField.mostrarErro("Senha é obrigatória");
} else if (senha.length < 6) {
    senhaField.mostrarErro("A senha deve ter pelo menos 6 caracteres");
} else {
    senhaField.mostrarSucesso("Senha válida!");
}
```

### Tratamento de Eventos
```java
// Ouvinte para tecla pressionada
senhaField.addKeyListener(new KeyAdapter() {
    @Override
    public void keyReleased(KeyEvent e) {
        // Validação em tempo real
        if (senhaField.getPassword().length == 0) {
            senhaField.mostrarErro("Senha é obrigatória");
        } else if (senhaField.getPassword().length < 6) {
            senhaField.mostrarErro("Mínimo 6 caracteres");
        } else {
            senhaField.limparMensagem();
        }
    }
});

// Ouvinte para mudança de visibilidade da senha
senhaField.addActionListener(e -> {
    if (senhaField.isPasswordVisible()) {
        System.out.println("Senha visível: " + new String(senhaField.getPassword()));
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
| `getPassword()` | Obtém a senha como array de caracteres |
| `isPasswordVisible()` | Verifica se a senha está visível |
| `setShowAndHide(boolean)` | Habilita/desabilita o botão de mostrar/esconder |

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
WPasswordField senha = new WPasswordField("Senha");
senha.setLineColor(new Color(0, 123, 255)); // Azul Bootstrap
senha.setHoverColor(new Color(0, 86, 179));
senha.setErrorColor(new Color(220, 53, 69));
```

### Tamanhos e Espaçamentos

Ajuste as dimensões do componente conforme necessário.

| Constante | Descrição | Valor Padrão | Uso |
|-----------|-----------|--------------|-----|
| `PADDING_TOP` | Espaçamento interno superior | `20`         | Controla a altura do campo |
| `PADDING_LEFT` | Espaçamento interno esquerdo | `10`         | Espaço antes do texto |
| `PADDING_BOTTOM` | Espaçamento interno inferior | `15`         | Espaço abaixo do texto |
| `PADDING_RIGHT` | Espaçamento interno direito | `30`         | Espaço após o texto |
| `LINE_HEIGHT` | Altura da linha inferior | `1`          | Espessura da linha |
| `LINE_Y_OFFSET` | Deslocamento vertical da linha | `14`         | Posição vertical da linha |
| `EYE_RIGHT_PADDING` | Largura do botão de mostrar/esconder | `35`         | - |
| `EYE_ICON_SIZE` | Tamanho do ícone de olho | `20`         | - |
| `EYE_ICON_MARGIN` | Margem ao redor do ícone | `5`          | - |

**Exemplo de Uso:**
```java
// Ajustando espaçamentos
senha.setBorder(new EmptyBorder(15, 10, 10, 40)); // top, left, bottom, right (direita maior para o botão)

// Ajustando o botão de mostrar/esconder
senha.putClientProperty("JComponent.arcWidth", 10);
senha.putClientProperty("JComponent.arcHeight", 10);
```

### Configurações de Exibição

| Propriedade | Descrição | Valor Padrão | Exemplo |
|-------------|-----------|--------------|---------|
| `obrigatorio` | Se o campo é obrigatório | `false` | `setObrigatorio(true)` |
| `labelText` | Texto do rótulo flutuante | `""` | `setLabelText("Senha")` |
| `showAndHide` | Mostrar botão de visualização | `false` | `setShowAndHide(true)` |

**Dicas de Uso:**
- Use `setObrigatorio(true)` para campos obrigatórios
- Atualize o `labelText` dinamicamente conforme necessário
- Use `setShowAndHide(true)` para habilitar o botão de mostrar/esconder senha

**Exemplo Completo:**
```java
// Criando campo de senha com configurações personalizadas
WPasswordField senha = new WPasswordField("Senha");
senha.setObrigatorio(true);
senha.setShowAndHide(true);
senha.setLineColor(new Color(94, 53, 177)); // Roxo
senha.setHoverColor(new Color(69, 39, 160));
senha.setToolTipText("Mínimo 6 caracteres");
senha.setBorder(new EmptyBorder(20, 10, 10, 40)); // Espaço extra à direita para o botão
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
| `setShowAndHide(boolean)` | Habilita/desabilita o botão de mostrar/esconder senha | `show`: `true` para mostrar o botão | `void` |

### Estado

| Método | Descrição | Retorno |
|--------|-----------|----------|
| `hasError()` | Verifica se há erro de validação | `boolean` |
| `getErrorMessage()` | Retorna a mensagem de erro atual | `String` |
| `isPasswordVisible()` | Verifica se a senha está visível | `boolean` |
| `getPassword()` | Retorna a senha como array de caracteres | `char[]` |

### Manipulação de Dados

| Método | Descrição | Retorno | Lança |
|--------|-----------|----------|-------|
| `getText()` | Retorna o texto atual (não recomendado para senhas) | `String` | - |
| `setText(String)` | Define o texto do campo (não recomendado para senhas) | `void` | - |
| `getPassword()` | Retorna a senha como array de caracteres | `char[]` | - |
| `setPassword(char[])` | Define a senha a partir de um array de caracteres | `void` | - |
| `clear()` | Limpa o conteúdo do campo | `void` | - |

**Notas de Uso:**
- Prefira usar `getPassword()` e `setPassword(char[])` em vez de `getText()`/`setText()` para maior segurança
- Sempre limpe os arrays de caracteres após o uso usando `Arrays.fill(senha, '\0')`
- O método `validar()` verifica apenas se o campo é obrigatório e está preenchido
- Para validações personalizadas, use `validarComMensagem()` ou faça a validação manualmente

**Exemplo de Uso Seguro:**
```java
WPasswordField senhaField = new WPasswordField("Senha");
// ... configurações ...

// Obter a senha de forma segura
char[] senha = senhaField.getPassword();
try {
    // Processar a senha...
    if (senha.length < 6) {
        senhaField.mostrarErro("A senha deve ter pelo menos 6 caracteres");
    } else {
        // Senha válida
    }
} finally {
    // Limpar o array de senha da memória
    Arrays.fill(senha, '\0');
}
```


### Características do Exemplo:

1. **Segurança Aprimorada**:
   - Uso de `char[]` em vez de `String` para armazenamento temporário de senhas
   - Limpeza adequada dos arrays de senha da memória após o uso
   - Validação de força da senha

2. **Boas Práticas**:
   - Interface limpa e intuitiva
   - Feedback visual imediato
   - Tratamento adequado de erros
   - Suporte a temas FlatLaf

3. **Funcionalidades**:
   - Campo de senha com botão de mostrar/esconder
   - Validação em tempo real
   - Confirmação de senha
   - Dicas visuais para o usuário

4. **Personalização**:
   - Cores personalizáveis
   - Mensagens de erro e sucesso
   - Dicas de ferramentas informativas

Para usar este exemplo, certifique-se de ter as dependências necessárias no seu projeto:
```gradle
dependencies {
    implementation 'org.pushingpixels:trident:1.6.0'
    implementation 'com.formdev:flatlaf:2.3'
}
```

## Personalização com FlatLaf

O `WPasswordField` é totalmente compatível com o [FlatLaf](https://www.formdev.com/flatlaf/), permitindo personalização completa através de propriedades de tema.

### Propriedades do Tema

| Propriedade | Descrição | Valor Padrão |
|-------------|-----------|--------------|
| `WPasswordField.background` | Cor de fundo | `#FFFFFF` |
| `WPasswordField.foreground` | Cor do texto | `#212529` |
| `WPasswordField.borderColor` | Cor da borda | `#CED4DA` |
| `WPasswordField.focusedBorderColor` | Borda quando em foco | `#80BDFF` |
| `WPasswordField.hoverBorderColor` | Borda ao passar o mouse | `#B3D7FF` |
| `WPasswordField.errorBorderColor` | Borda em caso de erro | `#DC3545` |
| `WPasswordField.successBorderColor` | Borda quando válido | `#198754` |
| `WPasswordField.hintText` | Cor do texto de dica | `#6C757D` |
| `WPasswordField.showButton.iconColor` | Cor do ícone | `#6C757D` |
| `WPasswordField.showButton.hoverIconColor` | Ícone ao passar o mouse | `#495057` |

### Exemplo Básico

```java
// Aplicar tema personalizado
try {
    UIManager.put("WPasswordField.background", Color.WHITE);
    UIManager.put("WPasswordField.foreground", new Color(33, 37, 41));
    UIManager.put("WPasswordField.focusedBorderColor", new Color(13, 110, 253));
    UIManager.setLookAndFeel(new FlatLightLaf());
} catch (Exception ex) {
    ex.printStackTrace();
}
```

### Arquivo de Tema (.properties)

```properties
# Cores básicas
WPasswordField.background=#FFFFFF
WPasswordField.foreground=#212529
WPasswordField.borderColor=#CED4DA
WPasswordField.focusedBorderColor=#0D6EFD

# Estados
WPasswordField.errorBorderColor=#DC3545
WPasswordField.successBorderColor=#198754

# Botão de mostrar/esconder
WPasswordField.showButton.iconColor=#6C757D
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

#### Correções
- Ajuste no alinhamento do botão de mostrar/esconder

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
- **Migração para Trident**: Atualização do sistema de animações
- **Sistema de Mensagens**: Feedback visual para validações
- **Validação Integrada**: Suporte a campos obrigatórios e validações personalizadas
- **Documentação**: Criação e manutenção da documentação

### Contribuições
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e enviar pull requests.