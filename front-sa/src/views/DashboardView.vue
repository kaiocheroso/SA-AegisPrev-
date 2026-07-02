<template>
  <div class="min-h-screen bg-gradient-to-br from-cyan-100 via-blue-50 to-emerald-100">
    <div class="max-w-7xl mx-auto p-8">
        <div class="mb-6">
            <RouterLink to="/home">
                <button
                class="flex items-center gap-2 bg-white text-cyan-700 border border-cyan-200 px-4 py-2 rounded-lg shadow hover:bg-cyan-50 transition"
                >
                ← Voltar
                </button>
            </RouterLink>
        </div>
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-cyan-800">
          Dashboard Administrativo
        </h1>
        <p class="text-gray-600 mt-2">
          Estatísticas gerais do sistema.
        </p>
      </div>

      <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">

        <div class="bg-white rounded-2xl shadow-lg p-6">
          <h2 class="text-xl font-semibold text-cyan-700 mb-5">
            Top 10 Doenças
          </h2>

          <table class="w-full">
            <thead>
              <tr class="border-b">
                <th class="text-left py-2">Doença</th>
                <th class="text-right py-2">Casos</th>
              </tr>
            </thead>

            <tbody>
              <tr
                v-for="doenca in topDoencas"
                :key="doenca.nomeDoenca"
                class="border-b hover:bg-gray-50"
              >
                <td class="py-3">
                  {{ doenca.nomeDoenca }}
                </td>

                <td class="text-right font-semibold text-cyan-700">
                  {{ doenca.quantidade }}
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <div class="bg-white rounded-2xl shadow-lg p-6">
          <h2 class="text-xl font-semibold text-cyan-700 mb-5">
            Ranking de Médicos
          </h2>

          <table class="w-full">
            <thead>
              <tr class="border-b">
                <th class="text-left py-2">Médico</th>
              </tr>
            </thead>

            <tbody>
              <tr
                v-for="medico in rankingMedicos"
                :key="medico.nomeMedico"
                class="border-b hover:bg-gray-50"
              >
                <td class="py-3">
                  {{ medico.nomeMedico }}
                </td>
              </tr>
            </tbody>
          </table>
        </div>

      </div>

      <div
        v-if="loading"
        class="text-center mt-8 text-cyan-700 font-semibold"
      >
        Carregando dados...
      </div>

    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { getTopDoencas, getRankingMedicos } from "@/services/api";

const topDoencas = ref([]);
const rankingMedicos = ref([]);
const loading = ref(false);

async function carregarDashboard() {
  loading.value = true;

  try {
    const [doencas, medicos] = await Promise.all([
      getTopDoencas(),
      getRankingMedicos(),
    ]);

    topDoencas.value = doencas;
    rankingMedicos.value = medicos;
  } catch (error) {
    console.error("Erro ao carregar dashboard:", error);
  } finally {
    loading.value = false;
  }
}

onMounted(carregarDashboard);
</script>