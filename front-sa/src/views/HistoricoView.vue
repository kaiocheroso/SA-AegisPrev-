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
                <th class="px-6 py-4 text-left">CPF</th>
                <th class="px-6 py-4 text-center">Ações</th>
              </tr>
            </thead>

            <tbody>
              <tr
                v-for="paciente in pacientesFiltrados"
                :key="paciente.id"
                class="border-b hover:bg-cyan-50 transition"
              >
                <td class="px-6 py-4 font-medium">
                  {{ paciente.nome }}
                </td>

                <td class="px-6 py-4">
                  {{ paciente.cpf }}
                </td>

                <td class="px-6 py-4">
                  <div class="flex justify-center gap-2">
                    <RouterLink :to="`/paciente/${paciente.id}`">
                      <button
                        class="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-lg"
                      >
                        Visualizar
                      </button>
                    </RouterLink>

                    <RouterLink :to="`/editar/${paciente.id}`">
                      <button
                        class="bg-yellow-500 hover:bg-yellow-600 text-white px-4 py-2 rounded-lg"
                      >
                        Editar
                      </button>
                    </RouterLink>

                    <button
                      @click="excluirPaciente(paciente.id)"
                      class="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-lg"
                    >
                      Excluir
                    </button>
                  </div>
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

      <div v-if="tabAtiva === 'sintomas'" class="text-gray-600">
        <h2 class="text-xl font-semibold mb-2">Sintomas</h2>
        <p>Aqui você pode listar os sintomas cadastrados.</p>
      </div>

      <div v-if="tabAtiva === 'doencas'" class="text-gray-600">
        <h2 class="text-xl font-semibold mb-2">Doenças</h2>
        <p>Aqui você pode listar as doenças cadastradas.</p>
      </div>

    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getPacientes, deletePaciente } from "../services/api";
import type { Pacientes } from "@/interfaces/Pacientes";
const tabAtiva = ref("pacientes");


const router = useRouter();

const pesquisa = ref("");

const pacientes = ref<Pacientes[]>([]);

const pacientesFiltrados = computed(() => {
  return pacientes.value.filter((paciente) =>
    paciente.nome.toLowerCase().includes(pesquisa.value.toLowerCase()),
  );
});

async function excluirPaciente(id: number) {
  if (!window.confirm("Você tem certeza que deseja excluir este paciente?")) {
    return;
  }

  await deletePaciente(id);
  await buscarPacientes();
}

async function buscarPacientes() {
  const resposta = await getPacientes();
  pacientes.value = resposta;
}

onMounted(buscarPacientes);
</script>
