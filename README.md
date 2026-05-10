# API de Tabela Tarifária de Água

API REST desenvolvida em Java com Spring Boot para gerenciamento de tabelas tarifárias de água e cálculo progressivo por faixa de consumo.  
O sistema é totalmente parametrizável, permitindo alterar faixas e valores no banco de dados sem necessidade de modificar o código.

## Tecnologias utilizadas

- Java 17 (Eclipse Temurin)
- Spring Boot 4.0.6
- Spring Web MVC
- Spring Data JPA
- Spring Validation
- Springdoc OpenAPI / Swagger UI
- PostgreSQL
- Lombok
- Maven
- Docker

## Objetivo do projeto

O projeto foi desenvolvido para atender ao desafio técnico de uma API capaz de:

- Criar tabelas tarifárias completas com categorias e faixas de consumo.
- Listar tabelas cadastradas.
- Listar apenas tabelas ativas.
- Excluir tabelas logicamente, impedindo uso futuro em cálculos.
- Calcular o valor total da tarifa com cobrança progressiva por faixa.

## Regras de negócio

As principais regras implementadas são:

- O sistema suporta as categorias `COMERCIAL`, `INDUSTRIAL`, `PARTICULAR` e `PUBLICO`.
- As faixas de consumo devem começar em 1.
- O início de cada faixa deve ser menor que o fim.
- Não pode haver sobreposição entre faixas.
- Não pode haver buracos entre faixas.
- O cálculo deve ser progressivo, cobrando apenas a parcela do consumo dentro de cada faixa.
- Apenas tabelas ativas podem ser consideradas nos cálculos.
- Quando houver mais de uma tabela ativa, é utilizada a tabela ativa mais recente com base na data de vigência.

## Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

- Java 17 (Eclipse Temurin)
- Maven 3.9+
- PostgreSQL 14+
- IDE de sua preferência, como IntelliJ IDEA ou VS Code
- Docker Desktop (opcional, para execução via container)

## Configuração do banco de dados

Crie um banco PostgreSQL, por exemplo:

```sql
CREATE DATABASE tarifasdb;
```

Depois, configure o arquivo `src/main/resources/application.properties` com os dados da sua máquina:

```properties
spring.application.name=tarifas

spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/tarifasdb}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false

springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/v3/api-docs

spring.profiles.active=dev
```

No arquivo `src/main/resources/application-dev.properties`, defina as credenciais de desenvolvimento:

```properties
DB_USERNAME=postgres
DB_PASSWORD=sua_senha
```

A estrutura do banco é gerada automaticamente pelo JPA, o que atende ao requisito de configuração equivalente ao script SQL.

## Modelagem de dados

A aplicação foi modelada com três entidades principais:

- **TabelaTarifaria**: representa uma tabela tarifária completa, contendo nome, data de vigência e status de ativação.


- **CategoriaConsumo**: representa uma categoria vinculada a uma tabela tarifária, como COMERCIAL, INDUSTRIAL, PARTICULAR ou PUBLICO.


- **FaixaConsumo**: representa uma faixa de consumo com início, fim e valor unitário.

### Relacionamentos

- Uma **TabelaTarifaria** possui várias **CategoriaConsumo**.
- Uma **CategoriaConsumo** pertence a uma única **TabelaTarifaria**.
- Uma **CategoriaConsumo** possui várias **FaixaConsumo**.
- Uma **FaixaConsumo** pertence a uma única **CategoriaConsumo**.

### Estrutura resumida

TabelaTarifaria (1) ---- (N) CategoriaConsumo (1) ---- (N) FaixaConsumo

Essa modelagem permite que as faixas e valores sejam parametrizados no banco de dados, atendendo ao requisito do desafio de alterar regras tarifárias sem modificar o código-fonte.

## Como executar a aplicação

1. Clone o repositório:

```bash
git clone https://github.com/yfgdavid/tarifas
```

2. Acesse a pasta do projeto:

```bash
cd tarifas
```

3. Configure o `application.properties` e o `application-dev.properties` com os dados do PostgreSQL.

4. Execute a aplicação com Maven:

```bash
./mvnw spring-boot:run
```

No Windows, caso necessário:

```bash
mvnw.cmd spring-boot:run
```

5. A aplicação ficará disponível em:

```text
http://localhost:8080
```

## Execução com Docker

Também é possível executar a aplicação com Docker.

### 1. Build da imagem

Execute o comando abaixo na raiz do projeto, onde está o arquivo `Dockerfile`:

```bash
docker build -t tarifas .
```

### 2. Execução do container

No Windows PowerShell, utilize:

```powershell
docker run -p 8080:8080 `
  -e DB_USERNAME=postgres `
  -e DB_PASSWORD=sua_senha `
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/tarifasdb `
  tarifas
```

### Observação importante

Ao executar a aplicação via Docker, o PostgreSQL deve estar acessível a partir do container.  
No Windows, o endereço `host.docker.internal` permite que o container acesse o banco de dados rodando na máquina host.

Após iniciar o container, a aplicação ficará disponível em:

```text
http://localhost:8080
```

## Documentação da API

Com a aplicação em execução, a documentação Swagger pode ser acessada em:

```text
http://localhost:8080/swagger-ui.html
```

ou, dependendo da configuração do Springdoc:

```text
http://localhost:8080/swagger-ui/index.html
```

'''# Endpoints

## 1. Criar tabela tarifária

**POST** `/api/tabelas-tarifarias`

### Exemplo de request

```json
{
  "nome": "Tabela Tarifaria Completa",
  "dataVigencia": "2026-05-09",
  "ativo": true,
  "categorias": [
    {
      "categoria": "COMERCIAL",
      "faixas": [
        { "inicio": 1, "fim": 10, "valorUnitario": 4.00 },
        { "inicio": 11, "fim": 20, "valorUnitario": 5.50 },
        { "inicio": 21, "fim": 30, "valorUnitario": 7.00 },
        { "inicio": 31, "fim": 99999, "valorUnitario": 8.50 }
      ]
    },
    {
      "categoria": "INDUSTRIAL",
      "faixas": [
        { "inicio": 1, "fim": 10, "valorUnitario": 6.00 },
        { "inicio": 11, "fim": 20, "valorUnitario": 8.00 },
        { "inicio": 21, "fim": 30, "valorUnitario": 10.00 },
        { "inicio": 31, "fim": 99999, "valorUnitario": 12.00 }
      ]
    },
    {
      "categoria": "PARTICULAR",
      "faixas": [
        { "inicio": 1, "fim": 10, "valorUnitario": 3.00 },
        { "inicio": 11, "fim": 20, "valorUnitario": 4.50 },
        { "inicio": 21, "fim": 30, "valorUnitario": 6.00 },
        { "inicio": 31, "fim": 99999, "valorUnitario": 7.50 }
      ]
    },
    {
      "categoria": "PUBLICO",
      "faixas": [
        { "inicio": 1, "fim": 10, "valorUnitario": 2.50 },
        { "inicio": 11, "fim": 20, "valorUnitario": 4.00 },
        { "inicio": 21, "fim": 30, "valorUnitario": 5.50 },
        { "inicio": 31, "fim": 99999, "valorUnitario": 7.00 }
      ]
    }
  ]
}
```

### Exemplo de response

```json
{
  "id": 1,
  "nome": "Tabela Tarifaria Completa",
  "dataVigencia": "2026-05-09",
  "ativo": true,
  "categorias": [
    {
      "id": 1,
      "categoria": "COMERCIAL",
      "faixas": [
        { "id": 1, "inicio": 1, "fim": 10, "valorUnitario": 4.00 },
        { "id": 2, "inicio": 11, "fim": 20, "valorUnitario": 5.50 },
        { "id": 3, "inicio": 21, "fim": 30, "valorUnitario": 7.00 },
        { "id": 4, "inicio": 31, "fim": 99999, "valorUnitario": 8.50 }
      ]
    },
    {
      "id": 2,
      "categoria": "INDUSTRIAL",
      "faixas": [
        { "id": 5, "inicio": 1, "fim": 10, "valorUnitario": 6.00 },
        { "id": 6, "inicio": 11, "fim": 20, "valorUnitario": 8.00 },
        { "id": 7, "inicio": 21, "fim": 30, "valorUnitario": 10.00 },
        { "id": 8, "inicio": 31, "fim": 99999, "valorUnitario": 12.00 }
      ]
    },
    {
      "id": 3,
      "categoria": "PARTICULAR",
      "faixas": [
        { "id": 9, "inicio": 1, "fim": 10, "valorUnitario": 3.00 },
        { "id": 10, "inicio": 11, "fim": 20, "valorUnitario": 4.50 },
        { "id": 11, "inicio": 21, "fim": 30, "valorUnitario": 6.00 },
        { "id": 12, "inicio": 31, "fim": 99999, "valorUnitario": 7.50 }
      ]
    },
    {
      "id": 4,
      "categoria": "PUBLICO",
      "faixas": [
        { "id": 13, "inicio": 1, "fim": 10, "valorUnitario": 2.50 },
        { "id": 14, "inicio": 11, "fim": 20, "valorUnitario": 4.00 },
        { "id": 15, "inicio": 21, "fim": 30, "valorUnitario": 5.50 },
        { "id": 16, "inicio": 31, "fim": 99999, "valorUnitario": 7.00 }
      ]
    }
  ]
}
```

> Os IDs podem variar conforme os dados persistidos no banco.

## 2. Listar todas as tabelas tarifárias

**GET** `/api/tabelas-tarifarias`

### Exemplo de response

```json
[
  {
    "id": 1,
    "nome": "Tabela Tarifaria Completa",
    "dataVigencia": "2026-05-09",
    "ativo": true,
    "categorias": [
      {
        "id": 1,
        "categoria": "COMERCIAL",
        "faixas": [
          { "id": 1, "inicio": 1, "fim": 10, "valorUnitario": 4.00 },
          { "id": 2, "inicio": 11, "fim": 20, "valorUnitario": 5.50 },
          { "id": 3, "inicio": 21, "fim": 30, "valorUnitario": 7.00 },
          { "id": 4, "inicio": 31, "fim": 99999, "valorUnitario": 8.50 }
        ]
      },
      {
        "id": 2,
        "categoria": "INDUSTRIAL",
        "faixas": [
          { "id": 5, "inicio": 1, "fim": 10, "valorUnitario": 6.00 },
          { "id": 6, "inicio": 11, "fim": 20, "valorUnitario": 8.00 },
          { "id": 7, "inicio": 21, "fim": 30, "valorUnitario": 10.00 },
          { "id": 8, "inicio": 31, "fim": 99999, "valorUnitario": 12.00 }
        ]
      },
      {
        "id": 3,
        "categoria": "PARTICULAR",
        "faixas": [
          { "id": 9, "inicio": 1, "fim": 10, "valorUnitario": 3.00 },
          { "id": 10, "inicio": 11, "fim": 20, "valorUnitario": 4.50 },
          { "id": 11, "inicio": 21, "fim": 30, "valorUnitario": 6.00 },
          { "id": 12, "inicio": 31, "fim": 99999, "valorUnitario": 7.50 }
        ]
      },
      {
        "id": 4,
        "categoria": "PUBLICO",
        "faixas": [
          { "id": 13, "inicio": 1, "fim": 10, "valorUnitario": 2.50 },
          { "id": 14, "inicio": 11, "fim": 20, "valorUnitario": 4.00 },
          { "id": 15, "inicio": 21, "fim": 30, "valorUnitario": 5.50 },
          { "id": 16, "inicio": 31, "fim": 99999, "valorUnitario": 7.00 }
        ]
      }
    ]
  }
]
```

## 3. Listar apenas tabelas ativas

**GET** `/api/tabelas-tarifarias/ativas`

### Exemplo de response

```json
[
  {
    "id": 1,
    "nome": "Tabela Tarifaria Completa",
    "dataVigencia": "2026-05-09",
    "ativo": true,
    "categorias": [
      {
        "id": 1,
        "categoria": "COMERCIAL",
        "faixas": [
          { "id": 1, "inicio": 1, "fim": 10, "valorUnitario": 4.00 },
          { "id": 2, "inicio": 11, "fim": 20, "valorUnitario": 5.50 },
          { "id": 3, "inicio": 21, "fim": 30, "valorUnitario": 7.00 },
          { "id": 4, "inicio": 31, "fim": 99999, "valorUnitario": 8.50 }
        ]
      },
      {
        "id": 2,
        "categoria": "INDUSTRIAL",
        "faixas": [
          { "id": 5, "inicio": 1, "fim": 10, "valorUnitario": 6.00 },
          { "id": 6, "inicio": 11, "fim": 20, "valorUnitario": 8.00 },
          { "id": 7, "inicio": 21, "fim": 30, "valorUnitario": 10.00 },
          { "id": 8, "inicio": 31, "fim": 99999, "valorUnitario": 12.00 }
        ]
      },
      {
        "id": 3,
        "categoria": "PARTICULAR",
        "faixas": [
          { "id": 9, "inicio": 1, "fim": 10, "valorUnitario": 3.00 },
          { "id": 10, "inicio": 11, "fim": 20, "valorUnitario": 4.50 },
          { "id": 11, "inicio": 21, "fim": 30, "valorUnitario": 6.00 },
          { "id": 12, "inicio": 31, "fim": 99999, "valorUnitario": 7.50 }
        ]
      },
      {
        "id": 4,
        "categoria": "PUBLICO",
        "faixas": [
          { "id": 13, "inicio": 1, "fim": 10, "valorUnitario": 2.50 },
          { "id": 14, "inicio": 11, "fim": 20, "valorUnitario": 4.00 },
          { "id": 15, "inicio": 21, "fim": 30, "valorUnitario": 5.50 },
          { "id": 16, "inicio": 31, "fim": 99999, "valorUnitario": 7.00 }
        ]
      }
    ]
  }
]
```

## 4. Listar tarifas por categoria

**GET** `/api/tabelas-tarifarias/categoria/{categoria}`

### Parâmetro de rota

- `categoria`: `COMERCIAL`, `INDUSTRIAL`, `PARTICULAR` ou `PUBLICO`.

### Exemplo de request

```http
GET /api/tabelas-tarifarias/categoria/COMERCIAL
```

### Exemplo de response

```json
[
  {
    "id": 1,
    "inicio": 1,
    "fim": 10,
    "valorUnitario": 4.00
  },
  {
    "id": 2,
    "inicio": 11,
    "fim": 20,
    "valorUnitario": 5.50
  },
  {
    "id": 3,
    "inicio": 21,
    "fim": 30,
    "valorUnitario": 7.00
  },
  {
    "id": 4,
    "inicio": 31,
    "fim": 99999,
    "valorUnitario": 8.50
  }
]
```

### Observação

Esse endpoint retorna as faixas da primeira tabela ativa encontrada para a categoria informada, conforme a regra atual da aplicação.

## 5. Excluir tabela tarifária logicamente

**DELETE** `/api/tabelas-tarifarias/{id}`

### Exemplo de request

```http
DELETE /api/tabelas-tarifarias/1
```

### Comportamento esperado

A tabela não é removida fisicamente do banco.  
O campo `ativo` é alterado para `false`, impedindo que essa tabela seja utilizada em cálculos futuros.

## 6. Calcular valor da tarifa

**POST** `/api/calculos`

### Exemplo de request

```json
{
  "categoria": "COMERCIAL",
  "consumo": 18
}
```

### Exemplo de response

```json
{
  "categoria": "COMERCIAL",
  "consumoTotal": 18,
  "valorTotal": 84.00,
  "detalhamento": [
    {
      "faixa": {
        "id": 1,
        "inicio": 1,
        "fim": 10,
        "valorUnitario": 4.00
      },
      "m3Cobrados": 10,
      "valorUnitario": 4.00,
      "subtotal": 40.00
    },
    {
      "faixa": {
        "id": 2,
        "inicio": 11,
        "fim": 20,
        "valorUnitario": 5.50
      },
      "m3Cobrados": 8,
      "valorUnitario": 5.50,
      "subtotal": 44.00
    }
  ]
}
```
Nesse exemplo, o cálculo é progressivo:
- 10 m³ são cobrados na primeira faixa;
- 8 m³ são cobrados na segunda faixa;
- total final de R$ 84,00.

## Respostas de erro

A aplicação possui tratamento global de exceções para retornar erros em formato JSON padronizado.

### Exemplo de erro de regra de negócio

```json
{
  "timestamp": "2026-05-08T00:10:00",
  "status": 400,
  "erro": "Regra de negócio",
  "mensagem": "Categoria não encontrada na tabela tarifária ativa",
  "campos": null
}
```

### Exemplo de erro de validação

```json
{
  "timestamp": "2026-05-08T00:11:00",
  "status": 400,
  "erro": "Erro de validação",
  "mensagem": "Existem campos inválidos na requisição",
  "campos": {
    "nome": "O nome é obrigatório."
  }
}
```

## Como testar a aplicação

Uma sequência recomendada de testes:

1. Criar uma tabela tarifária com categorias e faixas.
2. Consultar todas as tabelas.
3. Consultar apenas as tabelas ativas.
4. Executar o cálculo para diferentes categorias e consumos.
5. Excluir logicamente uma tabela.
6. Confirmar que a tabela excluída não aparece entre as ativas e não é usada em novos cálculos.

Também é recomendado testar cenários de erro:
- categoria inexistente;
- tabela ativa inexistente;
- faixas sobrepostas;
- faixas com buracos;
- faixa que não inicia em 1;
- campos obrigatórios ausentes.

## Estrutura do projeto

Exemplo simplificado da organização em camadas:

```text
src/main/java/com/example/tarifas
├── controller
├── dto
├── exception
├── model
├── repository
└── service
```

## Autor
David Victor da Silva Irmão

