<template>
  <div class="min-h-screen bg-gray-100 p-8">

    <RouterLink to="/home">
      <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
        Voltar
      </button>
    </RouterLink>

    <div class="max-w-3xl mx-auto bg-white rounded-xl shadow-lg p-8">

      <h1 class="text-3xl font-bold text-cyan-700 mb-8">
        Área Administrativa
      </h1>

      <div class="space-y-6">
        <div class="relative">
          <label class="block mb-2 font-semibold">
            E-mail do usuário
          </label>

          <input
            v-model="email"
            type="email"
            class="w-full border rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-cyan-500"
            placeholder="Digite o e-mail do usuário"
          />

          <!-- lista de sugestões -->
          <div
            v-if="sugestoes.length"
            class="absolute z-10 w-full bg-white border rounded-lg mt-1 shadow"
          >
            <div
              v-for="m in sugestoes"
              :key="m.idMedico"
              @click="selecionarMedico(m)"
              class="p-3 hover:bg-gray-100 cursor-pointer"
            >
              {{ m.email }}
            </div>
          </div>
        </div>

        <div class="grid grid-cols-2 gap-4">

          <button
            @click="adminSemana"
            class="bg-blue-600 hover:bg-blue-700 text-white rounded-lg p-3"
          >
            Admin por 1 Semana
          </button>

          <button
            @click="adminMes"
            class="bg-green-600 hover:bg-green-700 text-white rounded-lg p-3"
          >
            Admin por 1 Mês
          </button>

          <button
            @click="adminPermanente"
            class="bg-cyan-600 hover:bg-cyan-700 text-white rounded-lg p-3"
          >
            Admin Permanente
          </button>

          <button
            @click="remover"
            class="bg-red-600 hover:bg-red-700 text-white rounded-lg p-3"
          >
            Remover Admin
          </button>

        </div>

        <div
          v-if="mensagem"
          class="mt-6 p-4 rounded-lg bg-green-100 text-green-700"
        >
          {{ mensagem }}
        </div>

        <div
          v-if="erro"
          class="mt-6 p-4 rounded-lg bg-red-100 text-red-700"
        >
          {{ erro }}
        </div>

      </div>

    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from "vue";
import {
  removerAdmin,
  tornarAdminSemana,
  tornarAdminMes,
  tornarAdminPermanente,
  getMedicoByEmail
} from "../services/api";

const email = ref("");
const sugestoes = ref<any[]>([]);
const medicoSelecionado = ref<any | null>(null);

const mensagem = ref("");
const erro = ref("");

let timeout: any = null;

// busca enquanto digita (debounce simples)
watch(email, () => {
  clearTimeout(timeout);

  timeout = setTimeout(async () => {
    if (!email.value) {
      sugestoes.value = [];
      return;
    }

    try {
      const res = await getMedicoByEmail(email.value);
      sugestoes.value = res || [];
    } catch {
      sugestoes.value = [];
    }
  }, 300);
});

function selecionarMedico(medico: any) {
  medicoSelecionado.value = medico;
  email.value = medico.email;
  sugestoes.value = [];
  erro.value = ""; // limpa erro ao selecionar
}

function obterIdUsuario() {
  if (!medicoSelecionado.value) {
    throw new Error("Selecione um médico da lista");
  }
  return medicoSelecionado.value.idMedico;
}

function limparMensagens() {
  mensagem.value = "";
  erro.value = "";
}

async function adminSemana() {
  limparMensagens();

  try {
    const id = await obterIdUsuario();

    await tornarAdminSemana(id);

    mensagem.value = "Usuário promovido para administrador por uma semana.";
  } catch (e: any) {
    erro.value = e.message;
  }
}

async function adminMes() {
  limparMensagens();

  try {
    const id = await obterIdUsuario();

    await tornarAdminMes(id);

    mensagem.value = "Usuário promovido para administrador por um mês.";
  } catch (e: any) {
    erro.value = e.message;
  }
}

async function adminPermanente() {
  limparMensagens();

  try {
    const id = await obterIdUsuario();

    await tornarAdminPermanente(id);

    mensagem.value = "Usuário promovido para administrador permanente.";
  } catch (e: any) {
    erro.value = e.message;
  }
}

async function remover() {
  limparMensagens();

  try {
    const id = await obterIdUsuario();

    await removerAdmin(id);

    mensagem.value = "Permissão de administrador removida.";
  } catch (e: any) {
    erro.value = e.message;
  }
}
</script>