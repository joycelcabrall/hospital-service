# Tech Challenge 3 – Hospital Service API

Projeto desenvolvido em Java com Spring Boot para gerenciamento hospitalar.

## Objetivo

Sistema para controle de:

- usuários do hospital
- consultas médicas
- autenticação
- notificações assíncronas

Com diferentes perfis:

- MÉDICO
- ENFERMEIRO
- PACIENTE

---

## Tecnologias utilizadas

- Java 17
- Spring Boot 3
- Spring Security
- JWT
- Spring Data JPA
- H2 Database
- GraphQL
- Apache Kafka
- Docker Compose
- Maven
- Postman
- Swagger / OpenAPI

---

## Como executar

### 1. Clonar repositório

```bash
git clone https://github.com/joycelcabral/hospital-service.git
```

### 2. Abrir projeto

Abrir normalmente no IntelliJ IDEA.

### 3. Subir Kafka

```bash
docker compose up -d
```

### 4. Executar aplicação

Rodar:

```bash
HospitalServiceApplication
```

Aplicação:

```bash
http://localhost:8080
```

---

# Testes visuais da aplicação

## Swagger UI (principal)

Interface visual para testar toda a API:

```bash
http://localhost:8080/swagger-ui/index.html
```

No Swagger é possível testar:

- criar usuários
- listar usuários
- buscar usuário por ID
- editar usuários
- excluir usuários
- agendar consultas
- editar consultas
- excluir consultas
- testar autenticação

Sem necessidade do Postman.

---

## GraphQL

Interface GraphQL:

```bash
http://localhost:8080/graphiql
```

Exemplo:

```graphql
query {
  consultas {
    id
    especialidade
    status
  }
}
```

---

## Banco H2

Console visual:

```bash
http://localhost:8080/h2-console
```

Configuração:

```txt
JDBC URL: jdbc:h2:mem:hospitaldb
User Name: sa
Password:
```

Visualização:

- usuarios
- consultas

---

## Usuários de teste

### Médico

```json
{
  "email": "drjoaocarlos@hospital.com",
  "senha": "123456"
}
```

### Enfermeiro

```json
{
  "email": "enfmariafernanda@hospital.com",
  "senha": "123456"
}
```

### Paciente

```json
{
  "email": "marcusvinicius@hospital.com",
  "senha": "123456"
}
```

---

## Endpoints REST

### Auth

```bash
POST /auth/login
```

### Usuários

```bash
GET /usuarios
GET /usuarios/{id}
POST /usuarios
PUT /usuarios/{id}
DELETE /usuarios/{id}
```

### Consultas

```bash
GET /consultas
GET /consultas/{id}
POST /consultas
PUT /consultas/{id}
DELETE /consultas/{id}
```

---

## Kafka

Tópico utilizado:

```bash
consultas
```

Ao criar ou editar consulta:

- envia mensagem ao Kafka
- consumer recebe mensagem automaticamente
- notificação simulada no console

Exemplo:

```text
Mensagem enviada para Kafka
Mensagem recebida do Kafka
```

---

## Postman

Collection exportada:

```bash
tech-challenge-fase3-postman.json
```

---

## Status

Projeto finalizado com:

- CRUD completo
- autenticação JWT
- autorização por perfil
- GraphQL protegido
- Kafka Producer / Consumer
- Swagger UI
- H2 Console
- testes via Postman
- documentação via GitHub
