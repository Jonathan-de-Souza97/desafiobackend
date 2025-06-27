# 📌 Desafio Backend - CRUD VENDEDORES

API REST para criação, edição, deleção e buscas de vendedores, utilizando Clean Code, mensageria com Kafka, DDD e testes unitários com JUnit.

---

## 📁 Estrutura do Projeto

```none
src
├── main
│   ├── java
│   │   └── br.com.grupocasasbahia.desafiobackend.vendedor
│   │       ├── application         # Casos de uso, interfaces, inputs/outputs, responses
│   │       ├── configuration       # Configurações da aplicação (Kafka, Beans, etc)
│   │       ├── controller          # Controllers da API
│   │       ├── core
│   │       │   ├── entities        # Entidades de domínio
│   │       │   └── enums           # Enums utilizados
│   │       ├── infra
│   │       │   ├── drivers         # Integrações externas (Kafka, APIs, etc)
│   │       │   ├── querys          # Consultas de leitura
│   │       │   └── repository      # Implementações de repositórios
│   │       └── DesafioBackEndApplication # Classe principal (Spring Boot)
│   └── resources                   # Configurações 
└── test
    └── java
        └── br.com.grupocasasbahia.desafiobackend.vendedor
            ├── configuration      # Configuração de testes
            ├── integration        # Testes de integração (Kafka, DB, API)
            └── unit               # Testes unitários de domínio e casos de uso

```

## 🚀 Tecnologias e Conceitos

- **Linguagem:** Java 17  
- **Framework:** Spring Boot  
- **Banco de Dados:** PostgreSQL  
- **Mensageria:** Apache Kafka  
- **Arquitetura:** Clean Architecture + DDD  
- **Testes:** JUnit 5, Awaitility, Embedded Kafka  
- **Build Tool:** Maven  

---

## 🔧 1. Pré-requisitos

- Java 17+
- Docker e Docker Compose (opcional)
- PostgreSQL rodando na máquina local (ou via Docker)
- Kafka rodando localmente
- Maven

---

## 🛠️ 2. Criar Banco de Dados

Certifique-se de que o PostgreSQL esteja rodando com um banco chamado `app`. Em seguida, execute o script de criação:

```bash
psql -U postgres -h localhost -d app -f ./scriptSql/create.pgsql
```


## 🔍 Principais Endpoints
```bash
| Método | Rota                                      | Descrição                                        |
|--------|-------------------------------------------|--------------------------------------------------|
| POST   | `/vendedor`                               | Cadastra um novo vendedor                        |
| PUT    | `/vendedor`                               | Edita os dados de um vendedor existente          |
| DELETE | `/vendedor/{matricula}`                   | Remove um vendedor com base na matrícula         |
| GET    | `/vendedor`                               | Retorna a lista de todos os vendedores           |
| GET    | `/vendedor/documento/{documento}`         | Busca vendedores pelo documento (CPF/CNPJ)       |
| GET    | `/statusdoprocesso/{requisicaoId}`        | Verifica o status da requisição por ID           |

```


```bash
Covarage: 
```
![image](https://github.com/user-attachments/assets/a24877fc-9f5d-4d45-a862-1f471d4ed41b)



<p align="center">
  Desenvolvido por <strong>Jonathan de Souza</strong> 🚀  
  <br>
  <a href="https://github.com/jonathan-de-souza97" target="_blank">GitHub</a> • 
  <a href="https://linkedin.com/in/jonathan-de-souza-06a61112b" target="_blank">LinkedIn</a>
</p>
