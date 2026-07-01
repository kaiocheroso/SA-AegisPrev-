<template>
  <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-cyan-300 via-blue-100 to-emerald-300 p-4">
    <div class="bg-white rounded-2xl shadow-2xl p-8 w-full max-w-2xl">

      <h1 class="text-3xl font-bold text-center text-gray-800 mb-6">
        Cadastro de Consulta
      </h1>

      <form @submit.prevent="cadastrarConsulta" class="space-y-5">

        <!-- CPF PACIENTE -->
        <div>
          <label class="block text-sm font-semibold text-gray-700 mb-1">
            CPF do Paciente
          </label>

          <input
            v-model="form.cpfPaciente"
            @blur="buscarPacientePorCpf"
            type="text"
            placeholder="Digite o CPF"
            class="w-full px-4 py-3 border rounded-lg focus:ring-2 focus:ring-cyan-500"
          />
        </div>

        <!-- NOME PACIENTE -->
        <div>
          <label class="block text-sm font-semibold text-gray-700 mb-1">
            Nome do Paciente
          </label>

          <input
            v-model="form.nomePaciente"
            disabled
            class="w-full px-4 py-3 border rounded-lg bg-gray-100 text-gray-700"
          />
        </div>

        <!-- DESCRIÇÃO -->
        <div>
          <label class="block text-sm font-semibold text-gray-700 mb-1">
            Descrição
          </label>

          <textarea
            v-model="form.descricao"
            rows="4"
            class="w-full px-4 py-3 border rounded-lg focus:ring-2 focus:ring-cyan-500"
            placeholder="Descrição da consulta"
          />
        </div>

        <!-- SINTOMAS -->
        <div>
          <label class="block text-sm font-semibold text-gray-700 mb-1">
            Sintomas
          </label>

          <select
            v-model="form.sintomasIds"
            multiple
            class="w-full px-4 py-3 border rounded-lg h-40 focus:ring-2 focus:ring-cyan-500"
          >
            <option
              v-for="s in sintomas"
              :key="s.idSintoma"
              :value="s.idSintoma"
            >
              {{ s.nomeSintoma }}
            </option>
          </select>
        </div>

        <!-- BOTÕES -->
        <div class="flex justify-between pt-4">
          <RouterLink to="/home">
            <button type="button" class="px-6 py-3 bg-gray-500 text-white rounded-lg hover:bg-gray-600">
              Voltar
            </button>
          </RouterLink>

          <button
            type="submit"
            class="px-8 py-3 bg-cyan-600 text-white font-semibold rounded-lg hover:bg-cyan-700"
          >
            Cadastrar Consulta
          </button>
        </div>

      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getSintomas, getPacientes, postConsulta } from "../services/api";

const router = useRouter();

const form = ref({
  cpfPaciente: "",
  nomePaciente: "",
  idPaciente: null as number | null,
  descricao: "",
  sintomasIds: [] as number[],
});

const sintomas = ref<any[]>([]);

onMounted(async () => {
  sintomas.value = await getSintomas();
});

async function buscarPacientePorCpf() {
  try {
    if (!form.value.cpfPaciente) return;

    const pacientes = await getPacientes({
      cpfPaciente: form.value.cpfPaciente,
    });

    const paciente = pacientes[0];

    if (!paciente) {
      form.value.nomePaciente = "";
      form.value.idPaciente = null;
      return;
    }

    form.value.nomePaciente = paciente.nomePaciente;
    form.value.idPaciente = paciente.idPaciente;

  } catch (err) {
    console.error(err);
  }
}

async function cadastrarConsulta() {
  try {
    if (!form.value.idPaciente) {
      alert("Paciente não encontrado");
      return;
    }

    await postConsulta({
      idPaciente: form.value.idPaciente,
      descricao: form.value.descricao,
      sintomasIds: form.value.sintomasIds,
    });

    router.push("/home");

  } catch (err) {
    console.error("Erro ao cadastrar consulta:", err);
  }
}
</script>