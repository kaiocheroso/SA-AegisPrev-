<template>
  <form @submit.prevent="editar">
    <div
      class="min-h-screen flex items-center justify-center bg-gradient-to-br from-cyan-300 via-blue-100 to-emerald-300"
    >
      <div class="bg-white p-8 rounded-2xl shadow-2xl w-full max-w-md">
        <h1 class="text-3xl font-bold text-center text-gray-800 mb-6">
          Editar Paciente
        </h1>

        <div class="space-y-4">
          <label class="form-label">Nome completo</label>
          <input
            v-model="form.nome"
            type="text"
            placeholder="Digite o nome completo"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />

          <label class="form-label">CPF</label>
          <input
            v-model="form.cpf"
            type="text"
            @input="formatarCPF"
            maxlength="14"
            placeholder="Digite o CPF"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />

          <label class="form-label">Data de Nascimento</label>
          <input
            v-model="form.dataNascimento"
            type="date"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />

          <label class="form-label">Telefone</label>
          <input
            v-model="form.telefone"
            type="text"
            @input="formatarTelefone"
            maxlength="15"
            placeholder="Digite o telefone"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />

          <button
            type="submit"
            class="w-full bg-yellow-500 text-white py-3 rounded-lg hover:bg-yellow-600 transition"
          >
            Salvar Alterações
          </button>
        </div>
      </div>
    </div>
  </form>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { getPacientesById, editarPaciente } from "../services/api";
import type { Pacientes } from "@/interfaces/Pacientes";

const router = useRouter();
const route = useRoute();

const id = Number(route.params.id);

const form = ref({} as Pacientes);

onMounted(async () => {
  form.value = await getPacientesById(id);
});

async function editar() {
  await editarPaciente(id, {
    nome: form.value.nome,
    cpf: form.value.cpf,
    dataNascimento: form.value.dataNascimento,
    telefone: form.value.telefone,
  });

  router.push("/historico");
}

function formatarCPF(event: Event): void {
  const input = event.target as HTMLInputElement;

  let cpf = input.value.replace(/\D/g, "").slice(0, 11);

  cpf = cpf.replace(/(\d{3})(\d)/, "$1.$2");
  cpf = cpf.replace(/(\d{3})(\d)/, "$1.$2");
  cpf = cpf.replace(/(\d{3})(\d{1,2})$/, "$1-$2");

  form.value.cpf = cpf;
}

function formatarTelefone(event: Event): void {
  const input = event.target as HTMLInputElement;

  let telefone = input.value.replace(/\D/g, "").slice(0, 11);

  if (telefone.length <= 10) {
    telefone = telefone.replace(/^(\d{2})(\d)/, "($1) $2");
    telefone = telefone.replace(/(\d{4})(\d)/, "$1-$2");
  } else {
    telefone = telefone.replace(/^(\d{2})(\d)/, "($1) $2");
    telefone = telefone.replace(/(\d{5})(\d)/, "$1-$2");
  }

  form.value.telefone = telefone;
}
</script>
