# ⚕️ AegisPrev

<p align="center">
  <img src="https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow?style=for-the-badge" alt="Status"/>
  <img src="https://img.shields.io/badge/Metodologia-Spring e Vue-blue?style=for-the-badge" alt="Metodologia"/>
</p>

---

## 🎯 Tema
O **AegisPrev** é uma solução tecnológica voltada para a **área da saúde e prevenção**. O objetivo do sistema é entregar uma plataforma segura, eficiente e robusta para auxiliar nos processos de prevenção e cuidados médicos.

---

## 👥 Integrantes e Papéis

A equipe está dividida nas seguintes frentes de atuação para garantir a organização e o fluxo de desenvolvimento:

| Integrante | Função Principal |
| :--- | :--- |
| **Kaio Galvin** | Product Owner (PO) & Documentação |
| **Felipe da Rosa** | Desenvolvedor Full-Stack |
| **Samuel Eduardo** | Desenvolvedor Full-Stack |
| **Eduardo Patti** | Análise de Requisitos, Modelagem & Banco de Dados |
| **Igor Rabenschlag** | Quality Assurance (QA) & Documentação |
| **Felipe Cardoso** | Desenvolvedor Back-End & Análise de Requisitos |

---

## 🛠️ Tecnologias Previstas

O ecossistema do projeto foi planejado com as seguintes tecnologias para o desenvolvimento:

* ![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) - Linguagem base para o ecossistema backend.
* ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=Spring+Boot&logoColor=white) - Framework principal para a construção da API REST.
* ![Vue.js](https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vue.js&logoColor=4FC08D) - Framework progressivo para o desenvolvimento da interface do usuário (frontend).
* ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white) - Banco de dados relacional para persistência dos dados.

---

## ⚙️ Instrução Inicial de Organização

Para a organização das tarefas e o primeiro contato com o código, siga as diretrizes abaixo:

### 1. Gestão de Tarefas e Repositório
* **Quadro de Tarefas (Jira):** O progresso das atividades, divisões de sprints e backlog do produto são acompanhados pelo [Gerenciamento do Projeto no Jira](https://estudante-team-saude52.atlassian.net/jira/software/projects/SCRUM/boards/1?cloudId=e86de3a3-eb40-48a3-8685-f62a7eb91335&jql=assignee+IN+%28empty%2C+712020%3Ab616eb23-9c0f-41f8-80fb-d569eb6324cb%2C+712020%3A0283a512-7c3d-44e8-a148-9703f716b9f4%29&atlOrigin=eyJpIjoiYzJlNjhkNmVlMjcxNGQxNDgwZjBhMmQ5YjQ4ODU5ODkiLCJwIjoiaiJ9).
* **Código-Fonte:** O código principal e as ramificações de desenvolvimento ficam centralizados no [Repositório Principal do GitHub](https://github.com/kaiocheroso/SA-AegisPrev-).

### 2. Configuração do Ambiente Local
Certifique-se de possuir instalado em sua máquina:
* [Git](https://git-scm.com)
* [JDK 17 ou superior](https://www.oracle.com/java/technologies/downloads/)
* [Node.js](https://nodejs.org/en/)

### 3. Executando o Projeto Inicialmente
Para clonar, instalar as dependências e executar o projeto em ambiente local de desenvolvimento, utilize os comandos abaixo no terminal:

```bash
# Clone este repositório
$ git clone https://github.com/kaiocheroso/SA-AegisPrev-.git

# Acesse a pasta do projeto
$ cd SA-AegisPrev-

# Instale as dependências necessárias
$ npm install

# Execute a API Backend (em um terminal dedicado)
$ npm run api

# Execute a aplicação Frontend em modo de desenvolvimento (em outro terminal)
$ npm run dev
