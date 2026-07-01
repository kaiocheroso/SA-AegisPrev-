<template>
  <div
    class="min-h-screen bg-gradient-to-br from-cyan-300 via-blue-100 to-emerald-300 p-8"
  >
    <div class="max-w-7xl mx-auto bg-white rounded-2xl shadow-2xl p-8">
      <div
        class="flex flex-col md:flex-row justify-between items-center mb-8 gap-4"
      >
        <div>
          <h1 class="text-4xl font-bold text-cyan-700">
            Histórico
          </h1>

          <p class="text-gray-500 mt-1">
            Visualize todas as informações.
          </p>
        </div>

        <RouterLink to="/home">
          <button
            class="bg-cyan-600 hover:bg-cyan-700 text-white px-6 py-3 rounded-lg transition"
          >
            Voltar
          </button>
        </RouterLink>
      </div>

      <div class="mb-6">
        <input
          v-model="pesquisa"
          type="text"
          placeholder="Pesquisar..."
          class="w-full md:w-96 border rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-cyan-500"
        />
      </div>

      <div class="flex gap-2 border-b mb-6">
        <button
          @click="tabAtiva = 'pacientes'"
          class="px-4 py-2"
          :class="tabAtiva === 'pacientes'
            ? 'border-b-2 border-cyan-600 text-cyan-700 font-semibold'
            : 'text-gray-500'"
        >
          Pacientes
        </button>

        <button
          @click="tabAtiva = 'sintomas'"
          class="px-4 py-2"
          :class="tabAtiva === 'sintomas'
            ? 'border-b-2 border-cyan-600 text-cyan-700 font-semibold'
            : 'text-gray-500'"
        >
          Sintomas
        </button>

        <button
          @click="tabAtiva = 'doencas'"
          class="px-4 py-2"
          :class="tabAtiva === 'doencas'
            ? 'border-b-2 border-cyan-600 text-cyan-700 font-semibold'
            : 'text-gray-500'"
        >
          Doenças
        </button>
      </div>
      
      <div v-if="tabAtiva === 'pacientes'">
        <div class="overflow-x-auto rounded-xl shadow">
          <table class="w-full">
            <thead class="bg-cyan-600 text-white">
              <tr>
                <th class="px-6 py-4 text-left">Nome</th>
                <th class="px-6 py-4 text-center">CPF</th>
                <th class="px-6 py-4 text-right">Data de Nascimento</th>
              </tr>
            </thead>

            <tbody>
              <tr
                v-for="paciente in pacientesFiltrados"
                :key="paciente.idPaciente"
                class="border-b hover:bg-cyan-50 transition"
              >
                <td class="px-6 py-4 text-left font-medium">
                  {{ paciente.nomePaciente }}
                </td>

                <td class="px-6 py-4 text-center">
                  {{ paciente.cpfPaciente }}
                </td>
                <td class="px-6 py-4 text-right">
                  {{ paciente.dataNascimento }}
                </td>
              </tr>
              <tr v-if="pacientesFiltrados.length === 0">
                <td colspan="5" class="text-center py-8 text-gray-500">
                  Nenhum paciente encontrado.
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <div v-if="tabAtiva === 'sintomas'">
        <div class="overflow-x-auto rounded-xl shadow">
          <table class="w-full">
            <thead class="bg-cyan-600 text-white">
              <tr>
                <th class="px-6 py-4 text-left">Nome</th>
                <th class="px-6 py-4 text-right">Descrição</th>
              </tr>
            </thead>

            <tbody>
              <tr
                v-for="sintoma in sintomasFiltrados"
                :key="sintoma.idSintoma"
                class="border-b hover:bg-cyan-50 transition"
              >
                <td class="px-6 py-4 text-left font-medium">
                  {{ sintoma.nomeSintoma }}
                </td>

                <td class="px-6 py-4 text-right">
                  {{ sintoma.descricaoSintoma }}
                </td>
              </tr>

              <tr v-if="sintomasFiltrados.length === 0">
                <td colspan="2" class="text-center py-8 text-gray-500">
                  Nenhum sintoma encontrado.
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <div v-if="tabAtiva === 'doencas'">
        <div class="overflow-x-auto rounded-xl shadow">
          <table class="w-full">
            <thead class="bg-cyan-600 text-white">
              <tr>
                <th class="px-6 py-4 text-left">Nome</th>
                <th class="px-6 py-4 text-center">Hereditária</th>
                <th class="px-6 py-4 text-right">Descrição</th>
                <th class="px-6 py-4 text-right">Ações</th>
              </tr>
            </thead>

            <tbody>
              <tr
                v-for="doenca in doencasFiltradas"
                :key="doenca.idDoenca"
                class="border-b hover:bg-cyan-50 transition"
              >
                <td class="px-6 py-4 text-left font-medium">
                  {{ doenca.nomeDoenca }}
                </td>

                <td class="px-6 py-4 text-center">
                  {{ doenca.hereditaria ? "Sim" : "Não" }}
                </td>

                <td class="px-6 py-4 text-right">
                  {{ doenca.descricaoDoenca }}
                </td>

                <td class="px-6 py-4 text-right">
                  <button
                    @click="abrirDoenca(doenca.idDoenca)"
                    class="text-cyan-600 hover:text-cyan-800"
                    title="Visualizar"
                  >
                    👁️
                  </button>
                </td>
              </tr>

              <tr v-if="doencasFiltradas.length === 0">
                <td colspan="4" class="text-center py-8 text-gray-500">
                  Nenhuma doença encontrada.
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <div v-if="modalAberto" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
          <div class="bg-white rounded-xl p-6 w-full max-w-lg">

            <h2 class="text-2xl font-bold mb-4">
              {{ doencaSelecionada?.nomeDoenca }}
            </h2>

            <p class="mb-3">
              <strong>Descrição:</strong>
              {{ doencaSelecionada?.descricaoDoenca }}
            </p>

            <p class="mb-4">
              <strong>Hereditária:</strong>
              {{ doencaSelecionada?.hereditaria ? "Sim" : "Não" }}
            </p>

            <div>
              <strong>Sintomas</strong>

              <ul class="list-disc ml-6 mt-2">
                <li
                  v-for="sintoma in doencaSelecionada?.sintomas"
                  :key="sintoma.idSintoma"
                >
                  {{ sintoma.nomeSintoma }}
                </li>
              </ul>
            </div>

            <div class="mt-6 flex justify-end">
              <button
                @click="modalAberto = false"
                class="bg-cyan-600 text-white px-4 py-2 rounded-lg hover:bg-cyan-700"
              >
                Fechar
              </button>
            </div>

          </div>
        </div>


      </div>

    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { getPacientes, getSintomas, getDoencas, getDoencaById} from "../services/api";
import type { Pacientes } from "@/interfaces/Pacientes";
import type { Sintoma } from "@/interfaces/Sintoma";
import type { Doenca } from "@/interfaces/Doenca";

const tabAtiva = ref("pacientes");
const pesquisa = ref("");

const pacientes = ref<Pacientes[]>([]);
const sintomas = ref<Sintoma[]>([]);
const doencas = ref<Doenca[]>([]);

const pacientesFiltrados = computed(() => {
  const texto = pesquisa.value.toLowerCase();

  return pacientes.value.filter((paciente) =>
    paciente.nomePaciente.toLowerCase().includes(texto)
  );
});

const sintomasFiltrados = computed(() => {
  const texto = pesquisa.value.toLowerCase();

  return sintomas.value.filter((sintoma) =>
    sintoma.nomeSintoma.toLowerCase().includes(texto)
  );
});

const doencasFiltradas = computed(() => {
  const texto = pesquisa.value.toLowerCase();

  return doencas.value.filter((doenca) =>
    doenca.nomeDoenca.toLowerCase().includes(texto)
  );
});

async function buscarPacientes() {
  try {
    const resposta = await getPacientes();

    // Se getPacientes retorna apenas o array:
    pacientes.value = resposta;

    // Caso retorne AxiosResponse, troque por:
    // pacientes.value = resposta.data;

  } catch (erro) {
    console.error("Erro ao buscar pacientes:", erro);
  }
}

async function buscarSintomas() {
  try {
    sintomas.value = await getSintomas();
  } catch (erro) {
    console.error("Erro ao buscar sintomas:", erro);
  }
}

async function buscarDoencas() {
  try {
    doencas.value = await getDoencas();
  } catch (erro) {
    console.error("Erro ao buscar doenças:", erro);
  }
}

const doencaSelecionada = ref<Doenca | null>(null);
const modalAberto = ref(false);

async function abrirDoenca(id: number) {
  doencaSelecionada.value = await getDoencaById(id);
  modalAberto.value = true;
}

onMounted(async () => {
  await buscarPacientes();
  await buscarSintomas();
  await buscarDoencas();
});
</script>
