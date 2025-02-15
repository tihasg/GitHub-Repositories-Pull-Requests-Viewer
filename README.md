# GitHub Repositories & Pull Requests Viewer

Este projeto é um aplicativo Android desenvolvido em **Kotlin** que consulta a API do GitHub para trazer os repositórios mais populares (configuráveis, como por exemplo os de **Kotlin** ou **Java**) e exibe, em uma interface inspirada no mockup fornecido, as informações dos repositórios e seus Pull Requests.

## Funcionalidades

- **Tela Home**  
  Exibe uma lista paginada (endless scroll) dos repositórios mais populares, contendo:  
  - Nome do repositório  
  - Descrição  
  - Nome e foto do autor  
  - Número de stars  
  - Número de forks  
  > **Observação:** A interface desta tela segue o mockup "Tela Home.jpeg".

- **Tela de Pull Requests**  
  Ao tocar em um repositório, o aplicativo navega para uma nova tela que lista os Pull Requests do repositório selecionado, mostrando:  
  - Nome e foto do autor do PR  
  - Título do PR  
  - Data do PR  
  - Corpo (body) do PR  
  - Ao selecionar um item, a página do Pull Request é aberta no navegador padrão.  

## Abordagem Arquitetural

### MVI (Model-View-Intent)
- **Intent:** Representa as ações do usuário, como cliques ou atualizações de scroll, que disparam intenções de mudança na UI.
- **Model:** Responsável por manter o estado atual da tela, que é atualizado com os resultados das interações e das chamadas de API.
- **View:** Observa o estado e renderiza a interface de acordo com os dados atuais, garantindo uma UI reativa e consistente

### Clean Architecture
A aplicação está dividida em quatro camadas bem definidas:

- **MODULE APP:**  
  Contém os componentes de UI e a implementação do MVI, além dos ViewModels (ou presenters) que transformam as intents em estados para a view.
  
- **MODULE DOMAIN :**  
  Abriga a lógica de negócio e os casos de uso (use cases) que orquestram as operações de busca dos repositórios e pull requests.
  
- **MODULE DATA:**  
  Responsável por realizar as chamadas à API do GitHub utilizando Retrofit/OkHttp, além de gerenciar o cache de dados e imagens com bibliotecas como Glide.

- **MODULE CORE:**  
  Responsável por prover a base da aplicação, O BaseActivity, e o utils onde contem as UiExtensions.

### Injeção de Dependências
Utilizamos Dagger2 para realizar a injeção de dependências e garantir a modularidade e a testabilidade do código.

## Tecnologias & Bibliotecas
- **Linguagem:** Kotlin
- **Programação Reativa:** RxJava (ReactiveX)
- **Injeção de Dependências:** Dagger2
- **Chamadas à API:** Retrofit e OkHttp
- **Parsing JSON:** Gson ou Moshi
- **Carregamento e Cache de Imagens:** Glide
- **Testes Unitários:** JUnit com Mockk para mocking
- **Testes de UI (Bônus):** Espresso e/ou Robolectric
- **Material Design:** Utilização dos componentes do Material Design para uma experiência de usuário moderna e consistente

## Estrutura do Projeto

O projeto é modularizado para facilitar a escalabilidade e a manutenção:

- **app:** Módulo principal que contém a configuração de injeção de dependências e o entry point da aplicação.
- **data:** Responsável pelo acesso a dados, tanto remotos (API GitHub) quanto locais (cache).
- **domain:** Contém entidades, casos de uso e interfaces que abstraem a lógica de negócio.
- **presentation:** Abriga as atividades/fragments, ViewModels e implementações do padrão MVI.

## Fluxo de Navegação

1. **Tela Home:**  
   - Realiza a chamada à API do GitHub para buscar os repositórios mais populares (ex.: `https://api.github.com/search/repositories?q=language:Kotlin&sort=stars&page=1`).
   - Implementa paginação com scroll infinito, incrementando o parâmetro `page` conforme o usuário rola a lista.
2. **Ao clicar em um repositório:**  
   - O aplicativo navega para a tela de Pull Requests.
   - Realiza a chamada `https://api.github.com/repos/<criador>/<repositório>/pulls` para buscar os pull requests.
   - Cada item da lista exibe os dados do pull request e, ao ser clicado, abre o link correspondente no navegador.

## Testes
- **Testes Unitários:**  
  Cobrem a lógica dos casos de uso e a camada de apresentação (MVI). Utilizamos Mockk para simular as respostas da API e verificar o comportamento do ViewModel.


## Considerações de Design

- **Material Design:**  
  A interface foi desenvolvida seguindo as diretrizes do Material Design para garantir uma experiência intuitiva e consistente.
  
- **Cache de Dados e Imagens:**  
  Utilizamos Glide para cache de imagens e implementamos uma estratégia de caching para as respostas da API, reduzindo chamadas desnecessárias e melhorando a performance do aplicativo.

## Como Executar o Projeto

1. **Clone o repositório:**
   ```bash
   git clone <URL_DO_REPOSITORIO>
