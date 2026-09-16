# Guia de Deploy — AegisPrev

## 1. Banco de dados — Neon (PostgreSQL)

1. Crie uma conta em https://neon.tech (sem cartão de crédito).
2. Crie um projeto novo, ex: `aegisprev`.
3. Copie a **connection string** que o Neon fornece. Ela vem parecida com:
   ```
   postgresql://usuario:senha@ep-xxxx.us-east-2.aws.neon.tech/aegisprev?sslmode=require
   ```
4. Você vai precisar transformar isso em formato JDBC para o Spring Boot:
   ```
   jdbc:postgresql://ep-xxxx.us-east-2.aws.neon.tech/aegisprev?sslmode=require
   ```
   (usuário e senha vão em variáveis separadas — veja abaixo)

## 2. Backend — Render (Docker)

1. Suba o projeto pro GitHub (se ainda não subiu).
2. Em https://render.com, crie um **New Web Service** apontando pro repositório.
3. Environment: **Docker**. O Render vai detectar o `Dockerfile` em `backend/AegisPrev/` automaticamente
   (configure o "Root Directory" para `backend/AegisPrev` nas configurações do serviço).
4. Configure as variáveis de ambiente (Environment > Add Environment Variable):

   | Variável | Valor |
   |---|---|
   | `DB_URL` | `jdbc:postgresql://ep-xxxx.us-east-2.aws.neon.tech/aegisprev?sslmode=require` |
   | `DB_USERNAME` | usuário do Neon |
   | `DB_PASSWORD` | senha do Neon |
   | `JWT_SECRET` | gere um novo valor (veja abaixo) — **não reaproveite o que estava no código** |
   | `CORS_ORIGIN` | URL do seu frontend no Vercel, ex: `https://aegisprev.vercel.app` |
   | `DDL_AUTO` | `update` (ou `validate` se preferir mais segurança) |

   Para gerar um novo `JWT_SECRET`, rode localmente:
   ```bash
   openssl rand -base64 32
   ```

5. Depois do deploy, anote a URL pública do backend (algo como `https://aegisprev.onrender.com`).

## 3. Frontend — Vercel

1. Em https://vercel.com, importe o mesmo repositório.
2. Root Directory: `front-sa`.
3. Framework preset: Vite (detecta sozinho).
4. Configure a variável de ambiente:

   | Variável | Valor |
   |---|---|
   | `VITE_API_URL` | URL do backend no Render, ex: `https://aegisprev.onrender.com` |

5. Deploy. A URL final (ex: `https://aegisprev.vercel.app`) é o que você coloca na `CORS_ORIGIN` do passo 2.

## 4. Ordem recomendada

Como o backend precisa saber a URL do frontend (CORS) e o frontend precisa saber a URL do backend
(`VITE_API_URL`), o mais simples é:

1. Suba o backend primeiro no Render com `CORS_ORIGIN` temporário (ex: `http://localhost:5173`).
2. Suba o frontend no Vercel apontando `VITE_API_URL` pra URL do backend.
3. Volte no Render e atualize `CORS_ORIGIN` com a URL final do Vercel.
4. Redeploy do backend (o Render redeploya sozinho ao salvar a env var).

## 5. Antes da apresentação

- Abra a URL do frontend e faça login uns 5-10 minutos antes, pra "acordar" o backend no Render free
  (ele dorme após ~15 min de inatividade e demora 30-50s pra acordar na primeira requisição).
- Confira se o `JWT_SECRET` novo não quebrou nenhum token antigo salvo no `localStorage` do navegador
  (se estiver testando com uma conta antiga, pode ser necessário fazer login de novo).
