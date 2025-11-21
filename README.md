# 📝 TaskManager – Projeto SOA & WebServices  
Sistema desenvolvido para a disciplina de Arquitetura SOA e WebServices, utilizando Java + Spring Boot + JWT, seguindo todos os critérios exigidos na GS.

---

## 👥 Integrantes do Grupo
**Aluno:** João Pedro de Souza Vieira  
**RM:** 99805  

**Aluno:** Lucas Pisaneschi Speranzini  
**RM:** 98297  

---

## 🎯 Objetivo do Projeto
O **TaskManager** é um sistema de gerenciamento de tarefas baseado em arquitetura SOA.  
O sistema permite:

- Cadastro e autenticação de usuários  
- Emissão de token JWT após login  
- Controle de autorização via roles (USER e ADMIN)  
- Criação, listagem e conclusão de tarefas  
- Acesso seguro utilizando política **STATELESS**  
- Estrutura modular, reutilizável e desacoplada  

O projeto cumpre **todos os requisitos da GS**, incluindo DTOs, Entities, Services, Controllers, JWT, Stateless, tratamento global de exceções e ResponseEntity padronizado.

---

## 🏗️ Arquitetura SOA Implementada
O sistema segue o modelo de **serviços independentes**, onde cada responsabilidade é encapsulada em módulos reutilizáveis.

### Camadas principais:
- **Controller:** recebe e devolve requisições HTTP (ex: TaskController, AuthController).  
- **Service:** contém as regras de negócio (ex: TaskService, AuthService).  
- **Repository:** comunicação com o banco via JPA (UserRepository, TaskRepository).  
- **Security:** JWT, filtros, validação e configuração Spring Security.  
- **DTOs:** objetos de transferência de dados.  
- **Model:** entidades do banco (User, Task, Role).  
- **Exception:** tratamento global de erros.

Cada módulo é autônomo e segue o conceito de responsabilidade única, como exigido pelo padrão SOA.

---

## 📁 Estrutura de Pastas do Projeto

taskmanager/
│

├── pom.xml

├── README.md

└── src

└── main

├── java

│ └── com.example.taskmanager

│ ├── TaskManagerApplication.java

│ │

│ ├── controller

│ │ ├── AuthController.java

│ │ ├── TaskController.java

│ │ └── UserController.java

│ │

│ ├── service

│ │ ├── AuthService.java

│ │ ├── TaskService.java

│ │ └── UserService.java

│ │

│ ├── repository

│ │ ├── UserRepository.java

│ │ └── TaskRepository.java

│ │

│ ├── model

│ │ ├── User.java

│ │ ├── Task.java

│ │ └── Role.java

│ │

│ ├── dto

│ │ ├── ApiResponse.java

│ │ ├── AuthRequest.java

│ │ ├── AuthResponse.java

│ │ ├── TaskDTO.java

│ │ └── UserDTO.java

│ │

│ ├── security

│ │ ├── JwtUtil.java

│ │ ├── JwtFilter.java

│ │ ├── SecurityConfig.java

│ │ └── CustomUserDetailsService.java

│ │

│ └── exception

│ └── GlobalExceptionHandler.java

│

└── resources

├── application.properties

└── static / templates (se necessário)

---

## 🔐 Segurança — JWT + Stateless
O sistema utiliza autenticação baseada em token JWT e política **STATELESS**, ou seja:

- Nenhuma sessão é armazenada no servidor  
- Cada requisição deve enviar o token JWT no header Authorization  
- O token contém o usuário e suas roles  
- O filtro `JwtFilter` valida o token antes de acessar qualquer recurso protegido  

Isso garante segurança, escalabilidade e uma implementação real de SOA.

---

## 🛠️ Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3**
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (jjwt)
- MySQL (ou outro banco relacional)
- Maven

---

## ▶️ Como Executar o Projeto

### 1. Configurar o banco no application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager?createDatabaseIfNotExist=true

spring.datasource.username=SEU_USUARIO

spring.datasource.password=SUA_SENHA

### 2. Rodar o projeto

mvn spring-boot:run

---

## 🔗 Endpoints da API

### 🔐 Autenticação
| Método | Endpoint | Descrição |
|--------|----------|------------|
| POST | `/api/auth/register` | Cria novo usuário |
| POST | `/api/auth/login` | Retorna token JWT |

---

### 👤 Usuários
| Método | Endpoint | Permissão | Descrição |
|--------|----------|-----------|-----------|
| GET | `/api/users` | ADMIN | Lista todos os usuários |

---

### 📌 Tarefas
| Método | Endpoint | Descrição |
|--------|----------|------------|
| POST | `/api/tasks` | Cria tarefa para o usuário logado |
| GET | `/api/tasks` | Lista tarefas do usuário logado |
| GET | `/api/tasks/all` | Lista todas as tarefas (ADMIN recomendado) |
| PUT | `/api/tasks/{id}/complete` | Marca tarefa como concluída |
