# WButton

## Visão Geral

O `WButton` é um componente de botão avançado que estende `JButton` do Java Swing, oferecendo recursos aprimorados de interação, feedback visual e personalização. Desenvolvido para integrar-se perfeitamente com o FlatLaf, proporciona uma experiência de usuário moderna e consistente em aplicações desktop Java.

## Características Principais

- **Design Moderno**: Linha inferior animada e efeito de zoom no texto
- **Temas FlatLaf**: Suporte nativo a temas personalizáveis
- **Animações Suaves**: Transições fluidas para hover e clique
- **Acessibilidade**: Suporte a leitores de tela e navegação por teclado
- **Personalização Avançada**: Cores, tamanhos e comportamentos personalizáveis
- **Temas Dinâmicos**: Adaptação automática a temas claros e escuros
- **Integração com Swing**: Compatível com todos os componentes Swing padrão
- **Desempenho Otimizado**: Animações leves e eficientes

## Uso Básico

### Instanciação e Configuração
```java
// Criar instância com texto
WButton botao = new WButton("Clique Aqui");

// Configurações básicas
botao.setToolTipText("Clique para executar a ação");
botao.setMnemonic('C');  // Atalho Alt+C

// Configurando ação do botão
botao.addActionListener(e -> {
    // Ação a ser executada quando o botão for clicado
    System.out.println("Botão clicado!");
});

// Adicionar a um container
JPanel painel = new JPanel();
painel.add(botao);
```

### Validação
```java
// Verificando estado do botão
if (botao.isEnabled()) {
    // Botão habilitado, pode executar ação
    botao.doClick();
} else {
    // Botão desabilitado
    System.out.println("Botão desabilitado");
}
```

### Tratamento de Eventos
```java
// Ouvinte para clique do mouse
botao.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseEntered(MouseEvent e) {
        // Efeito ao passar o mouse
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        // Efeito ao remover o mouse
        botao.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
});
```

### Métodos Úteis

| Método | Descrição |
|--------|-----------|
| `setLineColor(Color)` | Define a cor da linha inferior |
| `setHoverColor(Color)` | Define a cor ao passar o mouse |
| `setPressedTextColor(Color)` | Define a cor do texto quando pressionado |
| `setAnimationDuration(int)` | Define a duração da animação em ms |
| `setShowLine(boolean)` | Mostra/oculta a linha inferior |
| `setEnabled(boolean)` | Habilita/desabilita o botão |
| `addActionListener(ActionListener)` | Adiciona um ouvinte de ação |
| `removeActionListener(ActionListener)` | Remove um ouvinte de ação |
| `doClick()` | Simula um clique no botão |

## Propriedades Personalizáveis

### Cores

Personalize as cores do botão para combinar com o tema do seu aplicativo.

| Propriedade | Descrição | Valor Padrão | Exemplo |
|-------------|-----------|--------------|---------|
| `lineColor` | Cor da linha inferior | `new Color(0, 122, 255)` | `setLineColor(Color.BLUE)` |
| `hoverColor` | Cor ao passar o mouse | `new Color(92, 172, 238)` | `setHoverColor(new Color(92, 172, 238))` |
| `pressedTextColor` | Cor do texto quando pressionado | `new Color(0, 85, 204)` | `setPressedTextColor(new Color(0, 85, 204))` |
| `lineBgColor` | Cor da linha quando sem foco | `new Color(200, 200, 200)` | - |
| `disabledText` | Cor do texto desabilitado | `new Color(170, 170, 170)` | - |
| `disabledBackground` | Cor de fundo desabilitada | `new Color(245, 245, 245)` | - |

**Exemplo de Uso:**
```java
// Personalizando cores
WButton botao = new WButton("Salvar");
botao.setLineColor(new Color(0, 122, 255));
botao.setHoverColor(new Color(92, 172, 238));
botao.setPressedTextColor(new Color(0, 85, 204));
botao.setBackground(new Color(0, 122, 255));
botao.setForeground(Color.WHITE);
```

### Tamanhos e Espaçamentos

Ajuste as dimensões do botão conforme necessário.

| Constante | Descrição | Valor Padrão | Uso |
|-----------|-----------|--------------|-----|
| `PADDING_TOP` | Espaçamento interno superior | `10` | Controla a altura do botão |
| `PADDING_LEFT` | Espaçamento interno esquerdo | `20` | Espaço antes do texto |
| `PADDING_BOTTOM` | Espaçamento interno inferior | `10` | Espaço abaixo do texto |
| `PADDING_RIGHT` | Espaçamento interno direito | `20` | Espaço após o texto |
| `LINE_HEIGHT` | Altura da linha inferior | `2` | Espessura da linha |
| `LINE_Y_OFFSET` | Deslocamento vertical da linha | `14` | Posição vertical da linha |

**Exemplo de Uso:**
```java
// Ajustando espaçamentos
botao.setBorder(new EmptyBorder(10, 20, 10, 20)); // top, left, bottom, right

// Ajustando a aparência
botao.putClientProperty("JComponent.arcWidth", 10);
botao.putClientProperty("JComponent.arcHeight", 10);
```

### Configurações de Exibição

| Propriedade | Descrição | Valor Padrão | Exemplo |
|-------------|-----------|--------------|---------|
| `showLine` | Mostra/oculta a linha inferior | `true` | `setShowLine(true)` |
| `animationsEnabled` | Habilita/desabilita animações | `true` | `setAnimationsEnabled(true)` |
| `animationDuration` | Duração da animação em ms | `200` | `setAnimationDuration(300)` |

**Dicas de Uso:**
- Use `setShowLine(false)` para botões sem linha inferior
- Ajuste `animationDuration` para controlar a velocidade das animações
- Desative animações em dispositivos lentos com `setAnimationsEnabled(false)`

## Métodos Principais

### Validação

| Método | Descrição | Retorno | Lança |
|--------|-----------|---------|-------|
| `isEnabled()` | Verifica se o botão está habilitado | `boolean` | - |
| `setEnabled(boolean)` | Habilita/desabilita o botão | `void` | - |

### Aparência

| Método | Descrição | Parâmetros | Retorno |
|--------|-----------|------------|---------|
| `setLineColor(Color)` | Define a cor da linha inferior | `color`: Cor desejada | `void` |
| `setHoverColor(Color)` | Define a cor ao passar o mouse | `color`: Cor desejada | `void` |
| `setPressedTextColor(Color)` | Define a cor do texto quando pressionado | `color`: Cor desejada | `void` |
| `setShowLine(boolean)` | Mostra/oculta a linha inferior | `show`: `true` para mostrar | `void` |
| `setAnimationsEnabled(boolean)` | Habilita/desabilita animações | `enabled`: `true` para habilitar | `void` |
| `setAnimationDuration(int)` | Define a duração da animação | `duration`: Duração em ms | `void` |

### Estado

| Método | Descrição | Retorno |
|--------|-----------|---------|
| `isEnabled()` | Verifica se o botão está habilitado | `boolean` |
| `isAnimationsEnabled()` | Verifica se as animações estão habilitadas | `boolean` |
| `isShowLine()` | Verifica se a linha inferior está visível | `boolean` |

### Ações

| Método | Descrição | Parâmetros | Retorno | Lança |
|--------|-----------|------------|---------|-------|
| `addActionListener(ActionListener)` | Adiciona um ouvinte de ação | `listener`: Ouvinte | `void` | - |
| `removeActionListener(ActionListener)` | Remove um ouvinte de ação | `listener`: Ouvinte | `void` | - |
| `doClick()` | Simula um clique no botão | - | `void` | - |

## Exemplo Completo

```java
// Criando um painel de botões
JPanel painel = new JPanel(new GridLayout(0, 1, 10, 10));

// Botão primário
WButton btnPrimario = new WButton("Salvar");
btnPrimario.setBackground(new Color(0, 122, 255));
btnPrimario.setForeground(Color.WHITE);
btnPrimario.setHoverColor(new Color(0, 98, 204));

// Botão com ícone
WButton btnComIcone = new WButton("Abrir", new ImageIcon("icone.png"));
btnComIcone.setIconTextGap(8);

// Botão desabilitado
WButton btnDesabilitado = new WButton("Desabilitado");
btnDesabilitado.setEnabled(false);

// Adicionando ao painel
painel.add(btnPrimario);
painel.add(btnComIcone);
painel.add(btnDesabilitado);

// Adicionando ação ao botão primário
btnPrimario.addActionListener(e -> {
    JOptionPane.showMessageDialog(painel, "Botão salvar clicado!");
});
```

## Personalização

### Temas FlatLaf

O `WButton` pode ser personalizado através do arquivo de tema FlatLaf. Adicione estas propriedades ao seu arquivo `.properties`:

```properties
# Cores básicas
WButton.background=@background
WButton.foreground=@foreground
WButton.lineColor=#007AFF
WButton.hoverColor=#5CACEE
WButton.pressedTextColor=#0055CC
WButton.lineBgColor=lighten(@foreground, 30%)

# Tema escuro
@if isDarkLaf
    WButton.background=lighten(@background, 5%)
    WButton.foreground=#FFFFFF
    WButton.hoverColor=lighten($WButton.lineColor, 15%)
@end

# Arredondamento e bordas
WButton.arc=8
WButton.focusWidth=2
WButton.focusColor=#7AB4FF

# Estados
WButton.disabledText=#AAAAAA
WButton.disabledBackground=#F5F5F5

# Layout
WButton.iconTextGap=4
WButton.showMnemonics=true
WButton.textShiftOffset=0
WButton.minimumWidth=75
WButton.minimumHeight=30
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

### v1.0.0 (26/11/2025)
- Versão inicial do componente
- Suporte a temas FlatLaf
- Animações de hover e clique
- Personalização de cores e estilos

## Créditos

### Desenvolvimento Original
- **Autor**: Warrick
- **Licença**: [MIT License](LICENSE)

### Melhorias e Manutenção
- **Animações**: Implementação de transições suaves
- **Temas**: Suporte a temas claros e escuros
- **Documentação**: Criação e manutenção da documentação

### Contribuições
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e enviar pull requests.
