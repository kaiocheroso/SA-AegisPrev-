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
              class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-cyan-500 focus:outline-none"
            />
          </div>

          <div>
            <label class="block text-sm font-semibold text-gray-700 mb-1">
              Data de Nascimento
            </label>

            <input
              v-model="form.pacienteDataNascimento"
              type="date"
              class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-cyan-500 focus:outline-none"
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
              class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-cyan-500 focus:outline-none"
            />
          </div>

          <div>
            <label class="block text-sm font-semibold text-gray-700 mb-1">
              Horário
            </label>

            <input
              v-model="form.horario"
              type="time"
              class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-cyan-500 focus:outline-none"
            />
          </div>
        </div>
        <div>
          <label class="block text-sm font-semibold text-gray-700 mb-1">
            Sintomas
          </label>

          <select
            v-model="form.sintomas"
            multiple
            class="w-full px-4 py-3 border rounded-lg focus:ring-2 focus:ring-cyan-500 focus:outline-none h-40"
          >
            <option
              v-for="sintoma in sintomas"
              :key="sintoma.id"
              :value="sintoma.id"
            >
              {{ sintoma.nome }}
            </option>
          </select>

          <p class="text-sm text-gray-500 mt-1">
            Segure Ctrl (Windows) ou Command (Mac) para selecionar vários
            sintomas.
          </p>
        </div>

        <div>
          <label class="block text-sm font-semibold text-gray-700 mb-1">
            Observações
          </label>

          <textarea
            v-model="form.descriçao"
            rows="4"
            placeholder="Digite informações adicionais..."
            class="w-full px-4 py-2 border rounded-lg resize-none focus:ring-2 focus:ring-cyan-500 focus:outline-none"
          ></textarea>
        </div>

        <div class="flex justify-between pt-4">
          <RouterLink to="/home">
            <button
              type="button"
              @click="router.push('/home')"
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
import type { Consulta } from "@/interfaces/Consulta";

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
    descriçao: form.value.descriçao,
    sintomas: form.value.sintomas,
  });

  router.push("/home");
}
interface Sintoma {
  id: number;
  nome: string;
}

const sintomas = ref<Sintoma[]>([
  { id: 1, nome: "Febre" },
  { id: 2, nome: "Tosse" },
  { id: 3, nome: "Dor de cabeça" },
  { id: 4, nome: "Dor de garganta" },
  { id: 5, nome: "Falta de ar" },
  { id: 6, nome: "Náusea" },
  { id: 7, nome: "Vômito" },
]);

function formatarCPF(event: Event): void {
  const input = event.target as HTMLInputElement;

  let cpf = input.value.replace(/\D/g, "").slice(0, 11);

  cpf = cpf.replace(/(\d{3})(\d)/, "$1.$2");
  cpf = cpf.replace(/(\d{3})(\d)/, "$1.$2");
  cpf = cpf.replace(/(\d{3})(\d{1,2})$/, "$1-$2");

  form.value.pacienteCpf = cpf;
}
</script>
