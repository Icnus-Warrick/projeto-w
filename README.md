<div align="center">
    <h1>📦 Biblioteca de Componentes Swing</h1>
    <p>Componentes Java Swing modernos e altamente personalizáveis para desenvolvimento de interfaces profissionais</p>
    
    [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
    [![Java](https://img.shields.io/badge/Java-8%2B-orange)](https://www.oracle.com/java/)
    [![GitHub stars](https://img.shields.io/github/stars/Icnus-Warrick/projeto-w?style=social)](https://github.com/Icnus-Warrick/projeto-w/stargazers)
</div>

## 🎨 Demonstração dos Componentes

### WButton - Botão Moderno
<div align="center">
    <div style="display: inline-block; margin: 0 10px;">
        <img src="docs/images/WButton1.png" alt="WButton Normal" width="180"/>
        <p><em>Estado Normal</em></p>
    </div>
    <div style="display: inline-block; margin: 0 10px;">
        <img src="docs/images/WButton2.png" alt="WButton Hover" width="180"/>
        <p><em>Estado Hover</em></p>
    </div>
    <div style="display: inline-block; margin: 0 10px;">
        <img src="docs/images/WButton3.png" alt="WButton Pressionado" width="180"/>
        <p><em>Estado Pressionado</em></p>
    </div>
</div>

### WTextField - Campo de Texto Avançado
<div align="center">
    <img src="docs/images/WTextField1.png" alt="WTextField Normal" width="280"/>
    <img src="docs/images/WTextField2.png" alt="WTextField com Foco" width="280"/>
    <p><em>WTextField em diferentes estados</em></p>
</div>

### WLabel - Rótulo Interativo
<div align="center">
    <div style="background: #f5f5f5; padding: 20px; border-radius: 8px; display: inline-block;">
        <p>WLabel em ação</p>
        <p><small>Execute a classe Teste_WComponentes para ver a demonstração</small></p>
    </div>
    <p><em>WLabel com linha de destaque interativa</em></p>
</div>

### WTextArea - Área de Texto Avançada
<div align="center">
    <div style="background: #f5f5f5; padding: 20px; border-radius: 8px; display: inline-block;">
        <p>WTextArea em ação</p>
        <p><small>Execute a classe Teste_WComponentes para ver a demonstração</small></p>
    </div>
    <p><em>WTextArea com rótulo flutuante e validação integrada</em></p>
</div>

## 🚀 Recursos Principais

- **Design Moderno**: Componentes com aparência profissional e animações suaves
- **Fácil Integração**: Compatível com qualquer aplicação Java Swing existente
- **Temas Personalizáveis**: Suporte nativo ao FlatLaf para diferentes esquemas de cores
- **Documentação Completa**: Guias detalhados e exemplos para cada componente
- **Leve e Rápido**: Desempenho otimizado para aplicações desktop
- **Compatibilidade Java 8+**: Funciona perfeitamente em ambientes Java 8 e superiores

## 🔧 Compatibilidade

Esta biblioteca foi desenvolvida com foco na **compatibilidade retroativa**, garantindo que funcione corretamente em:

- **Java 8**: Versão mínima suportada
- **Java 11, 17, 21**: Totalmente compatível
- **Ambientes legados**: Ideal para modernizar aplicações Swing existentes sem necessidade de upgrade do Java

## 📦 Componentes Disponíveis

### 📝 Campos de Entrada
- **WTextField**: Campo de texto com rótulo flutuante e validação integrada
- **WPasswordField**: Campo de senha seguro com opção de visualização
- **WComboBox**: Menu suspenso com suporte a busca e validação
- **WTextArea**: Área de texto com rótulo flutuante, validação e suporte a rolagem

### 🎛️ Controles de Interface
- **WButton**: Botão com efeitos visuais e suporte a ícones
- **WCheckBox**: Caixa de seleção com estados personalizáveis
- **WLabel**: Rótulo interativo com linha de destaque e efeito hover

### 🎨 Recursos Avançados
- **Validação em Tempo Real**: Feedback visual imediato para o usuário
- **Animações Suaves**: Transições fluidas para melhor experiência do usuário
- **Acessibilidade**: Totalmente compatível com leitores de tela
- **Suporte a Temas**: Mude facilmente entre temas claros e escuros
- **Documentação Detalhada**: Exemplos de uso e personalização para cada componente

## 🛠️ Como Usar

1. **Adicione a dependência** ao seu `pom.xml`:

```xml
<dependency>
    <groupId>br.com.warrick</groupId>
    <artifactId>projeto-W</artifactId>
    <version>1.0.0</version>
</dependency>
```

2. **Importe os componentes** no seu código:

```java
import br.com.warrick.swing.WButton;
import br.com.warrick.swing.WTextField;
import br.com.warrick.swing.WLabel;
```

3. **Execute a demonstração**:

```bash
# Compilar e executar
mvn clean compile exec:java -Dexec.mainClass="br.com.warrick.main.Main"

# Ou execute diretamente após build
java -cp target/classes br.com.warrick.main.Main
```

## 🎯 Demonstração Interativa

Para ver todos os componentes em ação, execute a classe `Teste_WComponentes` que demonstra:

- Estados visuais de cada componente
- Animações e transições
- Validação em tempo real
- Temas FlatLaf aplicados

## 📚 Documentação Detalhada

Cada componente possui sua própria documentação detalhada na pasta `docs/WComponentes/`:

- [WButton](docs/WComponentes/WButton.md) - Botão personalizável
- [WCheckBox](docs/WComponentes/WCheckBox.md) - Caixa de seleção
- [WComboBox](docs/WComponentes/WComboBox.md) - Menu suspenso com busca
- [WLabel](docs/WComponentes/WLabel.md) - Rótulo interativo
- [WPasswordField](docs/WComponentes/WPasswordField.md) - Campo de senha seguro
- [WTextArea](docs/WComponentes/WTextArea.md) - Área de texto com rótulo flutuante
- [WTextField](docs/WComponentes/WTextField.md) - Campo de texto avançado

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e enviar pull requests.

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Faça commit das suas alterações (`git commit -m 'Add some AmazingFeature'`)
4. Faça push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

## ✨ Status do Projeto

✅ **Pronto para Produção**
- Build limpo e sem erros
- Todos os componentes funcionais
- Compatível com Java 8+
- JAR otimizado (142KB)
- Sem vulnerabilidades conhecidas

📊 **Métricas**
- **Componentes**: 7 componentes Swing personalizados
- **Linhas de Código**: ~2.500+ linhas
- **Dependências**: 3 bibliotecas principais
- **Compatibilidade**: Java 8 até versões recentes

## 📦 Requisitos

- **Java**: 8 ou superior
- **Dependências Principais**:
  - `com.formdev:flatlaf` (3.6.2+) - Temas modernos
  - `org.pushingpixels:trident` (1.3+) - Animações suaves
  - `org.netbeans.external:AbsoluteLayout` (RELEASE270) - Gerenciador de layout
- **Dependências Opcionais**:
  - `com.formdev:flatlaf-extras` - Componentes adicionais
  - `com.formdev:svgSalamander` - Suporte a ícones SVG

## 🛠️ Instalação

### Maven
```xml
<dependencies>
    <dependency>
        <groupId>br.com.warrick</groupId>
        <artifactId>projeto-W</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

### Gradle
```gradle
implementation 'br.com.warrick:projeto-W:1.0.0'
```

## 📖 Documentação

### Guias de Referência
- [Estrutura dos Componentes](docs/Estrutura/ESTRUTURA_WSWING.md) - Padrões de desenvolvimento
- [Documentação em Markdown](docs/Estrutura/ESTRUTURA_WMD.md) - Modelo para documentação

### Componentes
- [WButton](docs/WComponentes/WButton.md) - Botão personalizado
- [WCheckBox](docs/WComponentes/WCheckBox.md) - Caixa de seleção
- [WComboBox](docs/WComponentes/WComboBox.md) - Menu suspenso
- [WLabel](docs/WComponentes/WLabel.md) - Rótulo interativo com linha de destaque
- [WPasswordField](docs/WComponentes/WPasswordField.md) - Campo de senha
- [WTextArea](docs/WComponentes/WTextArea.md) - Área de texto com rolagem e rótulo flutuante
- [WTextField](docs/WComponentes/WTextField.md) - Campo de texto

## 🚀 Começando

### Exemplo Rápido

```java
// Criando um formulário de login
JPanel painel = new JPanel(new GridBagLayout());
WTextField campoEmail = new WTextField("E-mail");
WPasswordField campoSenha = new WPasswordField("Senha");
WButton botaoEntrar = new WButton("Entrar");

// Adicionando ao painel
painel.add(campoEmail);
painel.add(campoSenha);
painel.add(botaoEntrar);
```

## ✨ Status do Projeto

✅ **Pronto para Produção**
- Build limpo e sem erros
- Todos os componentes funcionais
- Compatível com Java 8+
- JAR otimizado (142KB)
- Sem vulnerabilidades conhecidas

📊 **Métricas**
- **Componentes**: 7 componentes Swing personalizados
- **Linhas de Código**: ~2.500+ linhas
- **Dependências**: 3 bibliotecas principais
- **Compatibilidade**: Java 8 até versões recentes

## 📄 Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

## 🙏 Créditos

Baseado no trabalho original de [Ra Ven](https://github.com/DJ-Raven/raven-project).

### Melhorias e Manutenção
- **Animações**: Transições suaves e feedback visual
- **Temas**: Suporte a temas claros e escuros
- **Validação**: Sistema integrado de validação
- **Documentação**: Guias detalhados e exemplos

---

Desenvolvido e atualizado por Warrick - [GitHub](https://github.com/Icnus-Warrick)
