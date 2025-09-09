# BookTok - Plataforma Social de Leitura

Bem-vindo ao **BookTok**, uma plataforma social de leitura desenvolvida em **Java 17** com **Spring Boot 3**, que permite aos usuários cadastrar livros, acompanhar seu progresso de leitura e interagir com outros leitores por meio de comentários. A aplicação utiliza **H2 em memória** como banco de dados, **MapStruct** para mapeamento de DTOs e **Swagger/OpenAPI** para documentação.

---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3+
- Maven
- JPA/Hibernate
- H2 Database (em memória)
- MapStruct
- Bean Validation (Jakarta Validation)
- Lombok
- Swagger/OpenAPI

---

## 📂 Estrutura do Projeto

src/
└─ main/
├─ java/com/avaliacao/tecnica/porto/
│   ├─ controller/
│   ├─ service/
│   ├─ repository/
│   ├─ mapper/
│   └─ model/
└─ resources/
├─ application.properties
└─ data.sql

### Principais Entidades

- **Usuario**
    - id, nome, email, verSpoilers (boolean), status
    - Campo `verSpoilers` define se o usuário deseja visualizar comentários marcados como spoiler.

- **Livro**
    - id, titulo, autor, resumo, anoPublicacao, isbn, status

- **ProgressoLeitura**
    - id, usuario_id, livro_id, paginaAtual, dataRegistro

- **Comentario**
    - id, livro_id, usuario_id, mensagem, dataComentario, spoiler (boolean)

---

## ⚡ Configuração Local

### Pré-requisitos

- Java 17+
- Maven
- Git (opcional)

### Passo 1: Clonar o repositório

```bash
git clone https://github.com/DalthonMike/avaliacao-tecnica-porto.git
cd avaliacao-tecnica-porto/backend
```

### Passo 2: Build do projeto
```bash
mvn clean install
```

### Passo 3: Rodar a aplicação
```bash
mvn spring-boot:run
```

A aplicação irá rodar na porta 8081.

### Passo 4: Acessar o H2 Database
O console H2 está habilitado em:
http://localhost:8081/h2

### Passo 5: Importar collection do postman
O arquivo postman está na raiz do projeto:
avaliacao-tecnica-porto/desafio_tecnico.postman_collection.json

### Configuração:
•	JDBC URL: jdbc:h2:mem:desafiodb
•	Usuário: desafio
•	Senha: desafio

📌 Observações Importantes
•	O H2 Database é em memória, então ao reiniciar a aplicação, todos os dados serão recarregados a partir do data.sql.
•	O projeto usa MapStruct para conversão entre entidades e DTOs.
•	Validações são aplicadas nos DTOs para garantir dados consistentes.
•	Para testes unitários, utilize JUnit e Mockito.
•	Swagger/OpenAPI pode ser configurado para facilitar a documentação e testes de endpoints.

⸻

🔗 Referências
•	Spring Boot Documentation
•	H2 Database
•	MapStruct
•	Jakarta Bean Validation

⸻

✅ Resumo

Este projeto atende ao desafio técnico da BookTok com:
•	CRUD completo de Usuários e Livros
•	Registro de progresso de leitura
•	Comentários com filtro de spoilers
•	Uso de boas práticas como DTOs, MapStruct, validações e organização em camadas

---

## 🧪 Cobertura de Testes

Para verificar a cobertura dos testes unitários com **JaCoCo**, siga os passos abaixo:

1. Execute o comando Maven para gerar a cobertura:

```bash
mvn clean verify
```
2. Após a execução, abra a pasta de relatórios:
3. target/site/jacoco/index.html

📌 Observação: O relatório mostrará quais classes, métodos e linhas estão cobertos pelos testes unitários, permitindo identificar áreas que precisam de mais testes.