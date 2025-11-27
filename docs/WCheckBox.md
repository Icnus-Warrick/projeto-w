# WCheckBox

## Visão Geral

O `WCheckBox` é um componente de seleção avançado que estende `JCheckBox` do Java Swing, oferecendo uma experiência de usuário aprimorada com animações suaves e personalização visual.

## Características Principais

- **Design Moderno**: Linha inferior animada ao receber/perder foco
- **Feedback Visual**: Destaque ao passar o mouse e ao selecionar
- **Personalização Avançada**: Cores e tamanhos totalmente personalizáveis
- **Suporte a Temas**: Compatível com FlatLaf e temas do sistema
- **Animações Suaves**: Transições fluidas para melhor experiência do usuário
- **Acessibilidade**: Suporte a leitores de tela e navegação por teclado

## Uso Básico

### Instanciação e Configuração
```java
// Criar instância com texto
WCheckBox checkBox = new WCheckBox("Aceitar termos e condições");

// Configurações básicas
checkBox.setSelected(true); // Marcar como selecionado
checkBox.setToolTipText("Marque para aceitar os termos");

// Adicionar a um container
JPanel painel = new JPanel();
painel.add(checkBox);
```

### Validação
```java
// Verificar se o checkbox está marcado
if (checkBox.isSelected()) {
    // Ação quando marcado
    System.out.println("Termos aceitos");
} else {
    // Ação quando não marcado
    System.out.println("Você precisa aceitar os termos");
}
```

### Tratamento de Eventos
```java
// Ouvinte para mudanças de estado
checkBox.addActionListener(e -> {
    if (checkBox.isSelected()) {
        System.out.println("Checkbox selecionado");
    } else {
        System.out.println("Checkbox desmarcado");
    }
});
```

### Métodos Úteis

| Método | Descrição |
|--------|-----------|
| `setSelected(boolean)` | Marca/desmarca o checkbox |
| `isSelected()` | Verifica se está selecionado |
| `setText(String)` | Define o texto do checkbox |
| `setEnabled(boolean)` | Habilita/desabilita o componente |
| `addActionListener(ActionListener)` | Adiciona um ouvinte de ação |
| `removeActionListener(ActionListener)` | Remove um ouvinte de ação |
| `addItemListener(ItemListener)` | Adiciona um ouvinte de item |
| `removeItemListener(ItemListener)` | Remove um ouvinte de item |

## Propriedades Personalizáveis

### Cores

Personalize as cores do componente para combinar com o tema do seu aplicativo.

| Propriedade | Descrição | Valor Padrão | Exemplo |
|-------------|-----------|--------------|---------|
| `lineColor` | Cor da linha quando em foco | `new Color(3, 155, 216)` | `setLineColor(Color.BLUE)` |
| `hoverColor` | Cor ao passar o mouse | `new Color(100, 180, 220)` | `setHoverColor(new Color(100, 180, 220))` |
| `checkBgColor` | Cor de fundo quando selecionado | `new Color(69, 124, 235)` | `setCheckBgColor(new Color(69, 124, 235))` |
| `lineBgColor` | Cor da linha quando sem foco | `new Color(200, 200, 200)` | - |
| `textColor` | Cor do texto | `new Color(50, 50, 50)` | `setForeground(Color.DARK_GRAY)` |

### Tamanhos e Espaçamentos

Ajuste as dimensões do componente conforme necessário.

| Constante | Descrição | Valor Padrão | Uso |
|-----------|-----------|--------------|-----|
| `PADDING_TOP` | Espaçamento superior | `5` | Controla o espaço acima do texto |
| `PADDING_LEFT` | Espaçamento esquerdo | `10` | Espaço antes do texto |
| `PADDING_BOTTOM` | Espaçamento inferior | `8` | Espaço abaixo do texto |
| `PADDING_RIGHT` | Espaçamento direito | `10` | Espaço após o texto |
| `LINE_HEIGHT` | Altura da linha | `1` | Espessura da linha |
| `LINE_Y_OFFSET` | Deslocamento vertical | `2` | Posição vertical da linha |
| `CHECK_SIZE` | Tamanho do checkbox | `16` | Largura e altura do quadrado |
| `CHECK_BORDER` | Borda arredondada | `4` | Raio dos cantos do checkbox |

### Configurações de Exibição

| Propriedade | Descrição | Valor Padrão | Exemplo |
|-------------|-----------|--------------|---------|
| `showLine` | Mostra/oculta a linha inferior | `true` | `setShowLine(true)` |
| `animationsEnabled` | Habilita/desabilita animações | `true` | `setAnimationsEnabled(true)` |
| `animationDuration` | Duração da animação em ms | `200` | `setAnimationDuration(300)` |

## Métodos Principais

### Validação

| Método | Descrição | Retorno | Lança |
|--------|-----------|---------|-------|
| `isSelected()` | Verifica se está selecionado | `boolean` | - |
| `setSelected(boolean)` | Define o estado de seleção | `void` | - |

### Aparência

| Método | Descrição | Parâmetros | Retorno |
|--------|-----------|------------|---------|
| `setLineColor(Color)` | Define a cor da linha de foco | `color`: Cor desejada | `void` |
| `setHoverColor(Color)` | Define a cor de destaque | `color`: Cor desejada | `void` |
| `setCheckBgColor(Color)` | Define a cor de fundo quando selecionado | `color`: Cor desejada | `void` |
| `setShowLine(boolean)` | Mostra/oculta a linha inferior | `show`: `true` para mostrar | `void` |
| `setAnimationsEnabled(boolean)` | Habilita/desabilita animações | `enabled`: `true` para habilitar | `void` |
| `setAnimationDuration(int)` | Define a duração da animação | `duration`: Duração em ms | `void` |

### Estado

| Método | Descrição | Retorno |
|--------|-----------|---------|
| `isEnabled()` | Verifica se o componente está habilitado | `boolean` |
| `isAnimationsEnabled()` | Verifica se as animações estão habilitadas | `boolean` |
| `isShowLine()` | Verifica se a linha inferior está visível | `boolean` |

### Ações

| Método | Descrição | Parâmetros | Retorno | Lança |
|--------|-----------|------------|---------|-------|
| `addActionListener(ActionListener)` | Adiciona um ouvinte de ação | `listener`: Ouvinte | `void` | - |
| `removeActionListener(ActionListener)` | Remove um ouvinte de ação | `listener`: Ouvinte | `void` | - |
| `addItemListener(ItemListener)` | Adiciona um ouvinte de item | `listener`: Ouvinte | `void` | - |
| `removeItemListener(ItemListener)` | Remove um ouvinte de item | `listener`: Ouvinte | `void` | - |

## Exemplo Completo

```java
// Criando um painel de configurações
JPanel painel = new JPanel(new GridLayout(0, 1, 5, 5));

// Criando checkboxes
WCheckBox notificacoes = new WCheckBox("Receber notificações por e-mail");
notificacoes.setSelected(true);

WCheckBox novidades = new WCheckBox("Receber novidades e ofertas");
WCheckBox termos = new WCheckBox("Li e aceito os termos de uso");

// Adicionando ao painel
painel.add(notificacoes);
painel.add(novidades);
painel.add(termos);

// Configurando o botão de salvar
JButton salvar = new JButton("Salvar");
salvar.addActionListener(e -> {
    if (!termos.isSelected()) {
        JOptionPane.showMessageDialog(painel, "Você deve aceitar os termos de uso!");
    } else {
        // Salvar preferências...
        JOptionPane.showMessageDialog(painel, "Preferências salvas com sucesso!");
    }
});

// Adicionando o botão ao painel
painel.add(salvar);
```

## Personalização

### Temas FlatLaf

O `WCheckBox` pode ser personalizado através do arquivo de tema FlatLaf. Adicione estas propriedades ao seu arquivo `.properties`:

```properties
# Cores básicas
WCheckBox.background=@background
WCheckBox.foreground=@foreground
WCheckBox.lineColor=#007AFF
WCheckBox.hoverColor=#5CACEE
WCheckBox.checkBgColor=#457BEB
WCheckBox.lineBgColor=lighten(@foreground, 30%)

# Tema escuro
@if isDarkLaf
    WCheckBox.background=lighten(@background, 5%)
    WCheckBox.foreground=#FFFFFF
    WCheckBox.hoverColor=lighten($WCheckBox.lineColor, 15%)
    WCheckBox.lineBgColor=#555555
@end

# Estados
WCheckBox.disabledText=#AAAAAA
WCheckBox.disabledBackground=#F5F5F5

# Layout
WCheckBox.iconTextGap=4
WCheckBox.showMnemonics=true
WCheckBox.textShiftOffset=0
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

#### Melhorias
- Implementação inicial do componente WCheckBox
- Suporte a temas FlatLaf
- Animações de hover e seleção
- Personalização de cores e estilos

#### Correções
- Ajuste no alinhamento do texto
- Melhoria na renderização em diferentes temas

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
