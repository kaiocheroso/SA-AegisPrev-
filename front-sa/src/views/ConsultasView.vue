<template>
  <div class="min-h-screen bg-gray-100">
    <header class="bg-cyan-600 text-white shadow">
      <div class="max-w-7xl mx-auto px-6 py-5 grid grid-cols-3 items-center">

        <!-- Esquerda -->
        <div>
          <RouterLink to="/home">
            <button
              class="inline-flex items-center gap-2 bg-white text-cyan-700 px-4 py-2 rounded-lg font-medium hover:bg-cyan-50 transition shadow"
            >
              ← Voltar
            </button>
          </RouterLink>
        </div>

        <!-- Centro -->
        <div class="text-center">
          <h1 class="text-3xl font-bold">Consultas</h1>
        </div>

        <!-- Direita -->
        <div class="flex justify-end">
          <RouterLink to="/cadastrar-consulta">
            <button
              class="bg-white text-cyan-700 px-5 py-2 rounded-lg font-semibold hover:bg-cyan-100 transition shadow"
            >
              + Nova Consulta
            </button>
          </RouterLink>
        </div>

      </div>
    </header>

    <main class="max-w-7xl mx-auto p-6">

      <!-- Pesquisa -->
      <div class="bg-white rounded-xl shadow p-5 mb-6">
        <input
          type="text"
          placeholder="Pesquisar paciente..."
          class="w-full border rounded-lg px-4 py-3 focus:ring-2 focus:ring-cyan-500 outline-none"
        />
      </div>

      <!-- Tabela -->
      <div class="bg-white rounded-xl shadow overflow-hidden">
        <table class="w-full">

          <thead class="bg-cyan-600 text-white">
            <tr>
              <th class="text-left px-6 py-4">Paciente</th>
              <th class="text-left px-6 py-4">Médico</th>
              <th class="text-left px-6 py-4">Data</th>
              <th class="text-center px-6 py-4">Ações</th>
            </tr>
          </thead>

          <tbody>

            <tr
              v-for="consulta in consultas"
              :key="consulta.idConsulta"
              class="border-b hover:bg-gray-50 transition"
            >
              <td class="px-6 py-4">
                {{ consulta.paciente.nomePaciente }}
              </td>

              <td class="px-6 py-4">
                {{ consulta.medico.nome }}
              </td>

              <td class="px-6 py-4">
                {{ formatarData(consulta.dataConsulta) }}
              </td>

              

              <td class="px-6 py-4">

                <div class="flex justify-center gap-3">

                    <button
                        @click="abrirConsulta(consulta.idConsulta)" class="bg-blue-500 hover:bg-blue-600 text-white px-3 py-2 rounded">
                        Ver
                    </button>

                    <button v-if="isAdmin" class="bg-red-500 text-white px-3 py-2 rounded">
                        Excluir
                    </button>
                </div>

              </td>

            </tr>

          </tbody>

        </table>
      </div>

        <div
        v-if="modalAberto"
        class="fixed inset-0 bg-black/50 flex justify-center items-center z-50"
        >
        <div class="bg-white rounded-2xl shadow-xl w-full max-w-3xl p-8">

            <div class="flex justify-between items-center mb-6">

                <h2 class="text-3xl font-bold text-cyan-700">
                    Consulta
                </h2>

                <button
                    @click="modalAberto = false"
                    class="text-gray-500 hover:text-black text-2xl"
                >
                    ✕
                </button>

            </div>

            <div class="grid grid-cols-2 gap-6">

                <div>
                    <h3 class="font-semibold text-gray-500">Paciente</h3>
                    <p>{{ consultaSelecionada?.paciente.nomePaciente }}</p>
                </div>

                <div>
                    <h3 class="font-semibold text-gray-500">Médico</h3>
                    <p>{{ consultaSelecionada?.medico.nome }}</p>
                </div>

                <div>
                    <h3 class="font-semibold text-gray-500">Data</h3>
                    <p>{{ consultaSelecionada ? formatarData(consultaSelecionada.dataConsulta) : "" }}</p>
                </div>

            </div>

            <div class="mt-6">

                <h3 class="font-semibold text-gray-500 mb-2">
                    Descrição
                </h3>

                <div class="border rounded-lg p-4 bg-gray-50">
                    {{ consultaSelecionada?.descricao }}
                </div>

            </div>

            <div class="mt-6">

                <h3 class="font-semibold text-gray-500 mb-2">
                    Sintomas
                </h3>

                <div class="flex flex-wrap gap-2">

                    <span
                    v-for="sintoma in consultaSelecionada?.sintomas"
                    :key="sintoma.idSintoma"
                    class="bg-cyan-100 text-cyan-700 px-3 py-1 rounded-full"
                    >
                    {{ sintoma.nomeSintoma }}
                    </span>

                </div>

            </div>

            <div class="mt-6">

                <h3 class="font-semibold text-gray-500 mb-2">
                    Doenças Confirmadas
                </h3>

                <div class="flex flex-wrap gap-2">

                    <span
                    v-for="doenca in consultaSelecionada?.doencas"
                    :key="doenca.idDoenca"
                    class="bg-red-100 text-red-700 px-3 py-1 rounded-full"
                    >
                    {{ doenca.nomeDoenca }}
                    </span>

                </div>

            </div>

            <div class="mt-6">
                <h3 class="font-semibold text-gray-500 mb-2">
                    Previsões da IA
                </h3>

                <table class="w-full border rounded-lg overflow-hidden">
                    <thead class="bg-cyan-600 text-white">
                    <tr>
                        <th class="px-4 py-2 text-left">Doença</th>
                        <th class="px-4 py-2 text-center">Compatibilidade</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr
                        v-for="previsao in consultaSelecionada?.previsoes"
                        :key="previsao.id"
                        class="border-b"
                    >
                        <td class="px-4 py-2">
                        {{ previsao.nome }}
                        </td>

                        <td class="px-4 py-2 text-center">
                        {{ previsao.compatibilidade }}%
                        </td>
                    </tr>

                    <tr
                        v-if="consultaSelecionada?.previsoes.length === 0"
                    >
                        <td colspan="2" class="text-center py-4 text-gray-500">
                        Nenhuma previsão encontrada.
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

        </div>
    </div>

    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { getConsultasAdmin, getConsultasMedico , getConsultaById } from "@/services/api";
import type { Consulta } from "@/interfaces/Consulta";
import type { JwtPayload } from "@/interfaces/JwtPayload";

const consultas = ref<Consulta[]>([]);
const consultaSelecionada = ref<Consulta | null>(null);

const modalAberto = ref(false);

async function abrirConsulta(id: number) {
  consultaSelecionada.value = await getConsultaById(id);
  modalAberto.value = true;
}

const isAdmin = ref(false);

async function carregarConsultas() {
  if (isAdmin.value) {
    consultas.value = await getConsultasAdmin();
  } else {
    consultas.value = await getConsultasMedico();
  }
}

function formatarData(data: string) {
  return new Date(data).toLocaleString("pt-BR");
}

function getPayload(): JwtPayload | null {
  const token = localStorage.getItem("token");
  if (!token) return null;

  const partes = token.split(".");
  const payload = partes.at(1);

  if (!payload) return null;

  return JSON.parse(atob(payload)) as JwtPayload;
}

onMounted(() => {
  const payload = getPayload();

  if (payload) {
    isAdmin.value = payload.role === "ROLE_ADMIN";
  }

  carregarConsultas();
});

</script>