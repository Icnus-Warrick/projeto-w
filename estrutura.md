# Estrutura de Componentes Swing Personalizados

## Estrutura Básica

### 1. Constantes
- **Cores**: Definição de cores padrão para diferentes estados do componente
  - Cores de foco, hover, texto, fundo, etc.
- **Layout**: Valores de espaçamento, tamanhos e posicionamento
  - Padding, margens, alturas de linha, etc.

### 2. Atributos
- Estados do componente
  - `mouseOver`, `hasError`, `isSuccessMessage`, `obrigatorio`, etc.
- Propriedades visuais
  - Cores, textos, animações em andamento
- Objetos de animação
  - Timelines, listeners, etc.

### 3. Construtores
- Inicialização básica do componente
- Chamada para métodos de configuração
- Inicialização de valores padrão

## Métodos Principais

### Métodos Privados
1. **Configuração Inicial**
   - `setupComponent()` - Configura propriedades básicas
   - `initAnimation()` - Inicializa estados de animação

2. **Configuração de Listeners**
   - `setupListeners()` - Adiciona listeners de mouse, foco e teclado
   - `addCustomListeners()` - Listeners específicos do componente

3. **Métodos de Pintura**
   - `paintComponent(Graphics g)` - Desenha o componente principal
   - `paint(Graphics g)` - Desenho adicional (fora dos limites do componente)
   - `paintLabel(Graphics2D g2)` - Desenha o rótulo flutuante
   - `paintLine(Graphics2D g2)` - Desenha a linha inferior
   - `paintError(Graphics2D g2)` - Desenha mensagens de erro/sucesso

4. **Métodos de Animação**
   - `animateLabel(boolean show)` - Anima o rótulo flutuante
   - `animateLine(boolean show)` - Anima a linha de foco
   - `animateError(boolean show)` - Anima mensagens de erro/sucesso

### Métodos de Validação
- `validar()` - Validação principal do componente
- `validarObrigatorio()` - Verifica campo obrigatório
- `validarFormato()` - Validações específicas de formato
- `limparValidacao()` - Limpa estados de validação

### Métodos Públicos
1. **Configuração**
   - `setLabelText(String text)` - Define o texto do rótulo
   - `setObrigatorio(boolean obrigatorio)` - Define se o campo é obrigatório
   - `setMensagem(String mensagem, boolean sucesso)` - Define mensagem de feedback

2. **Gerenciamento de Estado**
   - `limparMensagem()` - Remove mensagens de erro/sucesso
   - `reset()` - Retorna ao estado inicial

3. **Getters**
   - `getLabelText()`
   - `isObrigatorio()`
   - `hasError()`
   - `getErrorMessage()`

## Boas Práticas
1. Sempre use `@Override` para sobrescrever métodos
2. Documente os métodos públicos com JavaDoc
3. Mantenha a consistência visual entre os componentes
4. Use constantes para valores reutilizáveis
5. Trate adequadamente os recursos (dispose de objetos Graphics)

## Exemplo de Uso
```java
WTextField campo = new WTextField("Nome");
campo.setObrigatorio(true);
if (!campo.validar()) {
    campo.mostrarErro("Campo obrigatório");
}
```

Esta estrutura serve como base para criar componentes Swing personalizados com comportamento consistente e profissional.
