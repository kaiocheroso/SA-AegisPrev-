<template>
  <div class="min-h-screen bg-gradient-to-br from-cyan-100 via-blue-50 to-emerald-100">
    <div class="max-w-7xl mx-auto p-8">

      <div class="mb-6">
        <RouterLink to="/home">
          <button class="flex items-center gap-2 bg-white text-cyan-700 border border-cyan-200 px-4 py-2 rounded-lg shadow hover:bg-cyan-50 transition">
           ← Voltar
          </button>
        </RouterLink>
      </div>
      <div class="bg-white rounded-2xl shadow-lg p-8">

        <h1 class="text-3xl font-bold text-cyan-800 mb-8">
          Área Administrativa
        </h1>

        <div class="space-y-8">

          <!-- BUSCA -->
          <div>
            <label class="block mb-2 font-semibold">
              E-mail do usuário
            </label>

            <input
              v-model="email"
              type="email"
              class="w-full border rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-cyan-500"
              placeholder="Digite o e-mail do médico"
            />

            <button
              @click="buscarMedico"
              class="mt-3 bg-cyan-600 hover:bg-cyan-700 text-white px-4 py-2 rounded-lg"
            >
              Buscar
            </button>
          </div>

          <!-- RESULTADO -->
          <div v-if="medicoSelecionado" class="p-4 bg-gray-50 rounded-lg">
            <p><strong>Nome:</strong> {{ medicoSelecionado.nome }}</p>
            <p><strong>Email:</strong> {{ medicoSelecionado.email }}</p>
          </div>

          <!-- AÇÕES -->
          <div class="grid grid-cols-2 gap-4">

            <button @click="adminSemana"
              class="bg-blue-600 hover:bg-blue-700 text-white rounded-lg p-3">
              Admin por 1 Semana
            </button>

            <button @click="adminMes"
              class="bg-green-600 hover:bg-green-700 text-white rounded-lg p-3">
              Admin por 1 Mês
            </button>

            <button @click="adminPermanente"
              class="bg-cyan-600 hover:bg-cyan-700 text-white rounded-lg p-3">
              Admin Permanente
            </button>

            <button @click="remover"
              class="bg-red-600 hover:bg-red-700 text-white rounded-lg p-3">
              Remover Admin
            </button>

          </div>

          <!-- MENSAGENS -->
          <div v-if="mensagem"
            class="mt-6 p-4 rounded-lg bg-green-100 text-green-700">
            {{ mensagem }}
          </div>

          <div v-if="erro"
            class="mt-6 p-4 rounded-lg bg-red-100 text-red-700">
            {{ erro }}
          </div>

        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref } from "vue";
import {
  removerAdmin,
  tornarAdminSemana,
  tornarAdminMes,
  tornarAdminPermanente,
  getMedicoByEmail
} from "../services/api";

const email = ref("");
const medicoSelecionado = ref<any | null>(null);

const mensagem = ref("");
const erro = ref("");

async function buscarMedico() {
  erro.value = "";
  mensagem.value = "";

  try {
    const res = await getMedicoByEmail(email.value);
    medicoSelecionado.value = res;
  } catch {
    medicoSelecionado.value = null;
    erro.value = "Médico não encontrado";
  }
}

function obterIdUsuario() {
  if (!medicoSelecionado.value) {
    throw new Error("Selecione um médico primeiro");
  }
  return medicoSelecionado.value.idUsuario;
}

function limparMensagens() {
  mensagem.value = "";
  erro.value = "";
}

async function adminSemana() {
  limparMensagens();

  try {
    const id = obterIdUsuario();
    await tornarAdminSemana(id);
    mensagem.value = "Usuário promovido para admin por 1 semana.";
  } catch (e: any) {
    erro.value = e.message;
  }
}

async function adminMes() {
  limparMensagens();

  try {
    const id = obterIdUsuario();
    await tornarAdminMes(id);
    mensagem.value = "Usuário promovido para admin por 1 mês.";
  } catch (e: any) {
    erro.value = e.message;
  }
}

async function adminPermanente() {
  limparMensagens();

  try {
    const id = obterIdUsuario();
    await tornarAdminPermanente(id);
    mensagem.value = "Usuário promovido para admin permanente.";
  } catch (e: any) {
    erro.value = e.message;
  }
}

async function remover() {
  limparMensagens();

  try {
    const id = obterIdUsuario();
    await removerAdmin(id);
    mensagem.value = "Permissão de admin removida.";
  } catch (e: any) {
    erro.value = e.message;
  }
}
</script>