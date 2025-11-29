# WLabel

## Visão Geral

O `WLabel` é um componente de rótulo avançado que estende `JLabel` do Java Swing, oferecendo recursos aprimorados de formatação, animações e personalização visual. Projetado para fornecer feedback visual claro e consistente em interfaces gráficas.

## Características Principais

- **Linha Inferior Animada**: Linha decorativa que se expande ao passar o mouse
- **Alinhamento Flexível**: Suporte completo a alinhamentos horizontal e vertical
- **Temas Personalizáveis**: Integração com FlatLaf e cores personalizáveis
- **Feedback Visual**: Animações suaves para melhor experiência do usuário
- **Acessibilidade**: Suporte a dicas de ferramentas e leitores de tela
- **Suporte a HTML**: Renderização de conteúdo HTML básico
- **Estilização Avançada**: Personalização de cores, fontes e espaçamentos

## Uso Básico

### Instanciação e Configuração
```java
// Criar instância básica
WLabel rotulo = new WLabel("Texto do Rótulo");

// Com alinhamento personalizado
WLabel rotuloCentralizado = new WLabel("Centralizado", SwingConstants.CENTER);

// Configurações adicionais
rotulo.setLineColor(Color.BLUE);
rotulo.setToolTipText("Descrição do rótulo");

// Adicionar a um container
JPanel painel = new JPanel();
painel.add(rotulo);
```

### Exemplo Completo
```java
// Criar e configurar o rótulo
WLabel rotulo = new WLabel("E-mail:");
rotulo.setHorizontalAlignment(SwingConstants.RIGHT);
rotulo.setVerticalAlignment(SwingConstants.CENTER);
rotulo.setLineColor(new Color(3, 155, 216));

// Adicionar a um layout
JPanel formulario = new JPanel(new GridBagLayout());
GridBagConstraints gbc = new GridBagConstraints();
gbc.insets = new Insets(5, 5, 5, 5);
gbc.gridx = 0;
gbc.gridy = 0;
formulario.add(rotulo, gbc);
```

## Propriedades Personalizáveis

### Cores
- `setLineColor(Color color)`: Define a cor da linha inferior
- `setTextColor(Color color)`: Define a cor do texto
- `setLineBgColor(Color color)`: Define a cor de fundo da linha

### Tamanhos e Espaçamentos
- `setLineHeight(int height)`: Altura da linha em pixels
- `setLineSpacing(int spacing)`: Espaçamento entre o texto e a linha

### Comportamento
- `setAnimated(boolean animated)`: Habilita/desabilita as animações
- `setAnimationDuration(int ms)`: Define a duração das animações em milissegundos

## Exemplos Avançados

### Estilização com CSS (FlatLaf)
```java
// Estilo personalizado
UIManager.put("WLabel.lineColor", new Color(76, 175, 80));
UIManager.put("WLabel.textColor", new Color(33, 33, 33));
UIManager.put("WLabel.lineBgColor", new Color(224, 224, 224));

// Aplicar tema
FlatLightLaf.setup();
SwingUtilities.updateComponentTreeUI(frame);
```

### Rótulo com Ícone
```java
// Criar ícone
ImageIcon icone = new ImageIcon("caminho/para/icone.png");

// Criar rótulo com ícone
WLabel rotuloComIcone = new WLabel("Texto com Ícone");
rotuloComIcone.setIcon(icone);
rotuloComIcone.setIconTextGap(10);
```

## Solução de Problemas Comuns

### 1. Texto não visível
```java
// ❌ Problema - texto não aparece
WLabel rotulo = new WLabel("");

// ✅ Solução - definir texto e tamanho
rotulo.setText("Texto visível");
rotulo.setPreferredSize(new Dimension(100, 30));
```

### 2. Linha não aparece
```java
// ❌ Problema - linha não visível

// ✅ Solução - verificar cor e altura da linha
rotulo.setLineColor(Color.BLUE);
rotulo.setLineHeight(2);
rotulo.repaint();
```

### 3. Alinhamento incorreto
```java
// ❌ Problema - alinhamento inesperado

// ✅ Solução - definir alinhamentos corretamente
rotulo.setHorizontalAlignment(SwingConstants.RIGHT);
rotulo.setVerticalAlignment(SwingConstants.CENTER);
```

## Requisitos

- Java 8 ou superior
- FlatLaf (recomendado para melhor aparência)
- Trident (para animações suaves)

## Histórico de Versões

### v2.0.0 (2025-11-29)
- Adicionado suporte a alinhamento vertical
- Melhorias na animação da linha
- Suporte a temas personalizados

### v1.0.0 (2025-11-27)
- Versão inicial
- Linha inferior animada
- Suporte a alinhamento horizontal

## Créditos

### Desenvolvimento Original
- **Autor**: Warrick
- **Repositório**: [Icnus-Warrick/projeto-w](https://github.com/Icnus-Warrick/projeto-w)
- **Licença**: [MIT License](LICENSE)

## Links Úteis

- [Documentação Java Swing](https://docs.oracle.com/javase/8/docs/api/javax/swing/JLabel.html)
- [FlatLaf - Theming](https://www.formdev.com/flatlaf/)
- [Trident Animation](https://github.com/kirill-grouchnikov/radiance/tree/master/docs/trident)

### Contribuições
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e enviar pull requests.
