<template>
  <form @submit.prevent="cadastrar">
    <div
      class="min-h-screen flex items-center justify-center bg-gradient-to-br from-cyan-300 via-blue-100 to-emerald-300"
    >
      <div class="bg-white p-8 rounded-2xl shadow-2xl w-full max-w-md">
        <h1 class="text-3xl font-bold text-center text-gray-800 mb-6">
          Cadastrar Paciente
        </h1>

        <div class="space-y-4">
          <label class="form-label">Nome completo</label>
          <input
            v-model="form.nome"
            type="text"
            placeholder="Digite seu nome completo"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
          />
          <label class="form-label">CPF</label>
          <input
            v-model="form.cpf"
            type="text"
            placeholder="Digite seu CPF"
            @input="formatarCPF"
            maxlength="14"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
          />
          <label class="form-label">Data de Nascimento</label>
          <input
            v-model="form.dataNascimento"
            type="date"
            placeholder="Digite sua data de nascimento"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
          />

          <button
            type="submit"
            class="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition duration-300"
          >
            Cadastrar
          </button>
        </div>
      </div>
    </div>
  </form>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { postPacientes } from "../services/api";
import type { Pacientes } from "@/interfaces/Pacientes";

const router = useRouter();
const email = ref("");
const senha = ref("");
const form = ref({} as Pacientes);

async function cadastrar(): Promise<void> {
  await postPacientes({
    nome: form.value.nome,
    cpf: form.value.cpf,
    dataNascimento: form.value.dataNascimento,
  });
  router.push("/home");
}
function formatarData(data: string): string {
  if (!data) return "";
  const dataPartes = data.split("-");
  return `${dataPartes[2]}/${dataPartes[1]}/${dataPartes[0]}`;
}
function formatarCPF(event: Event): void {
  const input = event.target as HTMLInputElement;

  let cpf = input.value.replace(/\D/g, "").slice(0, 11);

  cpf = cpf.replace(/(\d{3})(\d)/, "$1.$2");
  cpf = cpf.replace(/(\d{3})(\d)/, "$1.$2");
  cpf = cpf.replace(/(\d{3})(\d{1,2})$/, "$1-$2");

  form.value.cpf = cpf;
}
</script>
