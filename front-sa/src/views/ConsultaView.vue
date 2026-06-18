<template>
  <div
    class="min-h-screen flex items-center justify-center bg-gradient-to-br from-cyan-300 via-blue-100 to-emerald-300 p-4"
  >
    <div class="bg-white rounded-2xl shadow-2xl p-8 w-full max-w-2xl">
      <h1 class="text-3xl font-bold text-center text-gray-800 mb-2">
        Cadastro de Consulta
      </h1>

      <p class="text-center text-gray-500 mb-8">
        Agende uma nova consulta médica
      </p>

      <form @submit.prevent="cadastrarConsulta" class="space-y-5">
        <div>
          <label class="block text-sm font-semibold text-gray-700 mb-1">
            Nome do Médico
          </label>

          <input
            v-model="form.medicoNome"
            type="text"
            placeholder="Digite o nome do médico"
            class="w-full px-4 py-3 border rounded-lg focus:ring-2 focus:ring-cyan-500 focus:outline-none"
          />
        </div>

        <div>
          <label class="block text-sm font-semibold text-gray-700 mb-1">
            Nome do Paciente
          </label>

          <input
            v-model="form.pacienteNome"
            type="text"
            placeholder="Digite o nome do paciente"
            class="w-full px-4 py-3 border rounded-lg focus:ring-2 focus:ring-cyan-500 focus:outline-none"
          />
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label class="block text-sm font-semibold text-gray-700 mb-1">
              CPF
            </label>

            <input
              v-model="form.pacienteCpf"
              @input="formatarCPF"
              type="text"
              placeholder="000.000.000-00"
              class="w-full px-4 py-3 border rounded-lg focus:ring-2 focus:ring-cyan-500 focus:outline-none"
            />
          </div>

          <div>
            <label class="block text-sm font-semibold text-gray-700 mb-1">
              Data de Nascimento
            </label>

            <input
              v-model="form.pacienteDataNascimento"
              type="date"
              class="w-full px-4 py-3 border rounded-lg focus:ring-2 focus:ring-cyan-500 focus:outline-none"
            />
          </div>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label class="block text-sm font-semibold text-gray-700 mb-1">
              Data da Consulta
            </label>

            <input
              v-model="form.dataConsulta"
              type="date"
              class="w-full px-4 py-3 border rounded-lg focus:ring-2 focus:ring-cyan-500 focus:outline-none"
            />
          </div>

          <div>
            <label class="block text-sm font-semibold text-gray-700 mb-1">
              Horário
            </label>

            <input
              v-model="form.horario"
              type="time"
              class="w-full px-4 py-3 border rounded-lg focus:ring-2 focus:ring-cyan-500 focus:outline-none"
            />
          </div>
        </div>

        <div>
          <label class="block text-sm font-semibold text-gray-700 mb-1">
            Observações
          </label>

          <textarea
            v-model="form.observacao"
            rows="4"
            placeholder="Digite informações adicionais..."
            class="w-full px-4 py-3 border rounded-lg resize-none focus:ring-2 focus:ring-cyan-500 focus:outline-none"
          ></textarea>
        </div>

        <div class="flex justify-between pt-4">
          <RouterLink to="/home">
            <button
              type="button"
              class="px-6 py-3 bg-gray-500 text-white rounded-lg hover:bg-gray-600 transition"
            >
              Voltar
            </button>
          </RouterLink>

          <button
            type="submit"
            class="px-8 py-3 bg-cyan-600 text-white font-semibold rounded-lg hover:bg-cyan-700 transition shadow-lg"
          >
            Cadastrar Consulta
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { postConsulta } from "../services/api";
import type { Consulta } from "@/interfaces/Consultas";

const router = useRouter();

const form = ref({} as Consulta);

async function cadastrarConsulta() {
  await postConsulta({
    medicoNome: form.value.medicoNome,
    pacienteNome: form.value.pacienteNome,
    pacienteCpf: form.value.pacienteCpf,
    pacienteDataNascimento: form.value.pacienteDataNascimento,
    dataConsulta: form.value.dataConsulta,
    horario: form.value.horario,
    observacao: form.value.observacao,
  });

  router.push("/home");
}

function formatarCPF(event: Event): void {
  const input = event.target as HTMLInputElement;

  let cpf = input.value.replace(/\D/g, "").slice(0, 11);

  cpf = cpf.replace(/(\d{3})(\d)/, "$1.$2");
  cpf = cpf.replace(/(\d{3})(\d)/, "$1.$2");
  cpf = cpf.replace(/(\d{3})(\d{1,2})$/, "$1-$2");

  form.value.pacienteCpf = cpf;
}
</script>
