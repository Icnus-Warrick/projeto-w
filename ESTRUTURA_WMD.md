# W[NomeDoComponente]

## Sumário
- [Visão Geral](#visão-geral)
- [Características Principais](#características-principais)
- [Uso Básico](#uso-básico)
- [Personalização](#personalização)
- [Solução de Problemas](#solução-de-problemas)
- [Diretrizes de Documentação](#diretrizes-de-documentação)
- [Requisitos](#requisitos)
- [Histórico de Versões](#histórico-de-versões)
- [Créditos](#créditos)
- [Links Úteis](#links-úteis)

## Visão Geral

[Descrição detalhada do componente, seu propósito e benefícios principais.]

## Características Principais

- **Recurso 1**: Descrição detalhada
- **Recurso 2**: Descrição detalhada
- **Recurso 3**: Descrição detalhada
- **Recurso 4**: Descrição detalhada

## Uso Básico

### Instanciação e Configuração
```java
// Exemplo básico
WComponente componente = new WComponente("Rótulo");

// Com configurações adicionais
componente.setObrigatorio(true);
componente.setToolTipText("Descrição do campo");
```

### Exemplos de Código
```java
// Exemplo completo de uso
WComponente campo = new WComponente("Usuário");
campo.setObrigatorio(true);
campo.setPlaceholder("Digite seu usuário");

// Adicionando a um container
JPanel painel = new JPanel();
painel.add(campo);
```

## Propriedades Personalizáveis

### Cores
| Propriedade | Descrição | Padrão | Exemplo |
|-------------|-----------|--------|---------|
| `corFundo` | Cor de fundo | `Color.WHITE` | `setBackground(Color.WHITE)` |
| `corTexto` | Cor do texto | `Color.BLACK` | `setForeground(Color.BLACK)` |
| `corDestaque` | Cor de foco | `new Color(3, 155, 216)` | `setHighlightColor(Color.BLUE)` |

### Tamanhos e Espaçamentos
| Propriedade | Descrição | Padrão |
|-------------|-----------|--------|
| `padding` | Espaçamento interno | `10px` |
| `altura` | Altura do componente | `38px` |

### Estados
| Estado | Classe CSS | Descrição |
|--------|------------|-----------|
| `:hover` | `.wcomponente-hover` | Mouse sobre o componente |
| `:focus` | `.wcomponente-focus` | Componente com foco |
| `:error` | `.wcomponente-error` | Estado de erro |

## Métodos Principais

### Validação
| Método | Descrição | Retorno |
|--------|-----------|---------|
| `validar()` | Executa a validação | `boolean` |
| `adicionarValidador()` | Adiciona validação personalizada | `void` |

### Aparência
| Método | Descrição |
|--------|-----------|
| `aplicarTema()` | Aplica tema personalizado |
| `resetarEstilos()` | Volta ao padrão |

## Exemplos Avançados

### Validação Personalizada
```java
componente.adicionarValidador(texto -> {
    if (texto.length() < 5) {
        return "Mínimo de 5 caracteres";
    }
    return null; // Válido
});
```

### Tema Dinâmico

## Personalização

### Temas FlatLaf
```properties
# Cores do WComponent no tema
WComponent.lineColor=#039bd8       # Linha (foco)
WComponent.hoverColor=#64b4dc      # Mouse sobre
WComponent.textColor=@foreground   # Cor do texto
WComponent.hintColor=@hint         # Texto de dica
WComponent.bgColor=@background     # Fundo
WComponent.lineBgColor=#C8C8C8     # Linha normal
# Quando Houver componentes com mensagem 
WComponent.errorColor=#dc3545      # Erro
WComponent.successColor=#28a745    # Sucesso
```
## Solução de Problemas

### 1. Texto não aparece

**Sintomas:**
- O componente é renderizado, mas o texto não é exibido
- O placeholder é mostrado, mas o texto digitado não aparece

**Soluções:**

1. **Cor do texto igual à cor de fundo**
   ```java
   // ❌ Problema
   componente.setForeground(Color.WHITE);
   componente.setBackground(Color.WHITE);
   
   // ✅ Solução
   componente.setForeground(Color.BLACK);
   componente.setBackground(Color.WHITE);
   ```

2. **Componente desabilitado**
   ```java
   // ❌ Problema
   componente.setEnabled(false);
   
   // ✅ Solução
   componente.setEnabled(true);
   // Ou, se precisar parecer desabilitado mas mostrar o texto
   componente.setEditable(false);
   componente.setBackground(new Color(240, 240, 240));
   ```

### 2. Validação não funciona

**Sintomas:**
- O método `validar()` não retorna o esperado
- Mensagens de erro não são exibidas

**Soluções:**

1. **Validador não adicionado corretamente**
   ```java
   // ❌ Problema - esquecer de adicionar o validador
   
   // ✅ Solução - adicionar validador corretamente
   componente.adicionarValidador(texto -> {
       if (texto == null || texto.trim().isEmpty()) {
           return "Campo obrigatório";
       }
       return null; // Válido
   });
   ```

2. **Evento de validação não configurado**
   ```java
   // ❌ Problema - falta configurar o listener
   
   // ✅ Solução - configurar o listener de mudança
   componente.getDocument().addDocumentListener(new DocumentListener() {
       public void changedUpdate(DocumentEvent e) { validarCampo(); }
       public void removeUpdate(DocumentEvent e) { validarCampo(); }
       public void insertUpdate(DocumentEvent e) { validarCampo(); }
       
       private void validarCampo() {
           boolean valido = componente.validar();
           // Atualizar UI conforme necessário
       }
   });
   ```

### 3. Estilos não estão sendo aplicados

**Sintomas:**
- Cores personalizadas não aparecem
- Tamanhos e margens não são respeitados

**Soluções:**

1. **Verificar ordem de inicialização**
   ```java
   // ❌ Problema - estilos sendo sobrescritos
   componente.setBackground(Color.RED);
   aplicarTemaPadrao(); // Pode sobrescrever o background
   
   // ✅ Solução - aplicar estilos após a inicialização
   componente.inicializar();
   componente.setBackground(Color.RED);
   ```

2. **Usar UIManager para temas**
   ```java
   // ✅ Aplicar estilos globais
   UIManager.put("WComponent.background", Color.WHITE);
   UIManager.put("WComponent.foreground", Color.BLACK);
   
   // Recarregar o componente
   SwingUtilities.updateComponentTreeUI(componente);
   ```

### 4. Problemas de Performance

**Sintomas:**
- Interface travando durante digitação
- Lentidão ao validar formulários grandes

**Soluções:**

1. **Otimizar validações**
   ```java
   // ❌ Problema - validação pesada no evento de digitação
   
   // ✅ Solução - usar timer para validar após parar de digitar
   private Timer validacaoTimer;
   
   private void configurarValidacao() {
       componente.getDocument().addDocumentListener(new DocumentListener() {
           public void changedUpdate(DocumentEvent e) { agendarValidacao(); }
           public void removeUpdate(DocumentEvent e) { agendarValidacao(); }
           public void insertUpdate(DocumentEvent e) { agendarValidacao(); }
           
           private void agendarValidacao() {
               if (validacaoTimer != null) {
                   validacaoTimer.stop();
               }
               validacaoTimer = new Timer(500, e -> {
                   componente.validar();
                   validacaoTimer = null;
               });
               validacaoTimer.setRepeats(false);
               validacaoTimer.start();
           }
       });
   }
   ```

### 5. Problemas de Acessibilidade

**Sintomas:**
- Leitores de tela não anunciam o componente corretamente
- Navegação por teclado não funciona

**Soluções:**

1. **Configurar descrições acessíveis**
   ```java
   // ✅ Adicionar descrição para leitores de tela
   componente.getAccessibleContext().setAccessibleName("Campo de texto");
   componente.getAccessibleContext().setAccessibleDescription("Digite seu nome completo");
   
   // ✅ Habilitar navegação por teclado
   componente.setFocusTraversalKeysEnabled(true);
   componente.getInputMap().put(KeyStroke.getKeyStroke("TAB"), "transferFocus");
   ```

### Dicas de Depuração

1. **Verificar estados do componente**
   ```java
   System.out.println("Visível: " + componente.isVisible());
   System.out.println("Habilitado: " + componente.isEnabled());
   System.out.println("Editável: " + componente.isEditable());
   System.out.println("Texto: " + componente.getText());
   ```

2. **Verificar estilos aplicados**
   ```java
   System.out.println("Cor de fundo: " + componente.getBackground());
   System.out.println("Cor do texto: " + componente.getForeground());
   System.out.println("Fonte: " + componente.getFont());
   ```

## Diretrizes de Documentação

###  Regras Gerais
- Usar português brasileiro
- Manter consistência na formatação
- Incluir exemplos práticos
- Documentar todas as propriedades públicas

### Seções Obrigatórias
1. Visão Geral
2. Uso Básico
3. Referência de API
4. Exemplos

## Requisitos

### Dependências
- Java 8 ou superior
- [Bibliotecas necessárias]

## Histórico de Versões

### Versão 1.0.0 (23/11/2025) Versão inicial e Data de Criação
- Lançamento inicial
- Suporte a temas claro/escuro
- Validações básicas

### Próximas Versões
- [ ] Suporte a máscaras
- [ ] Validações personalizadas

## Créditos
- **Desenvolvido por**: [Seu Nome]
- **Inspirado em**: [Fonte de inspiração]
- **Licença**: [Tipo de licença]

## Links Úteis
- [Documentação Oficial](#)
- [Repositório no GitHub](#)
- [Exemplos Online](#)

## Checklist de Revisão
- [ ] Todas as propriedades documentadas
- [ ] Exemplos de uso incluídos
- [ ] Links para componentes relacionados
- [ ] Screenshots quando aplicável
- [ ] Seção de troubleshooting
