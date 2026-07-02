<template>
  <div
    class="min-h-screen bg-gradient-to-br from-cyan-100 via-blue-50 to-emerald-100"
  >
    <header class="bg-white shadow-md">
      <div
        class="max-w-7xl mx-auto px-6 py-4 flex justify-between items-center"
      >
        <h1 class="text-2xl font-bold text-cyan-800">AegisPrev</h1>
        <div>
          <RouterLink to="/cadastrar-consulta">
            <button
              class="bg-cyan-600 text-white px-4 py-2 rounded-lg hover:bg-cyan-700 transition"
            >
              Cadastrar Consulta
            </button>
          </RouterLink>
        </div>
      </div>
    </header>

    <main class="max-w-7xl mx-auto px-6 py-10">
      <div
        class="bg-white rounded-3xl shadow-lg p-8 mb-8 border border-cyan-100"
      >
        <h2 class="text-3xl font-bold text-cyan-800 mb-2">
          Bem-vindo ao Sistema de Saúde da AegisPrev
        </h2>

        <p class="text-gray-600"></p>
      </div>

      <div :class="[ 'grid gap-6', isAdmin ? 'grid-cols-1 md:grid-cols-2 lg:grid-cols-4' : 'grid-cols-1 md:grid-cols-2' ]">
        <RouterLink to="/historico" class="block w-full">
          <div
            class="bg-white p-6 rounded-2xl shadow-md hover:shadow-xl hover:-translate-y-1 transition text-left h-full"
          >
            <h4 class="font-semibold text-cyan-700 text-lg">Histórico</h4>
            <p class="text-gray-500 text-sm mt-2">Histórico dos pacientes</p>
          </div>
        </RouterLink>
        <RouterLink to="/consultas" class="block w-full">
          <div
            class="bg-white p-6 rounded-2xl shadow-md hover:shadow-xl hover:-translate-y-1 transition text-left h-full"
          >
            <h4 class="font-semibold text-cyan-700 text-lg">Consultas</h4>
            <p class="text-gray-500 text-sm mt-2">
              Lista das consultas cadastradas
            </p>
          </div>
        </RouterLink>

        <!-- Terceira Div -->
          <!-- Área Administrativa -->
          <RouterLink v-if="isAdmin" to="/permissoes" class="block w-full">
            <div
              class="bg-white p-6 rounded-2xl shadow-md hover:shadow-xl hover:-translate-y-1 transition text-left h-full"
            >
              <h4 class="font-semibold text-cyan-700 text-lg">
                Área Administrativa
              </h4>
              <p class="text-gray-500 text-sm mt-2">
                Gerencie permissões e administradores.
              </p>
            </div>
          </RouterLink>

          <!-- Dashboard -->
          <RouterLink
            v-if="isAdmin"
            to="/dashboard"
            class="block w-full"
          >
            <div
              class="bg-white p-6 rounded-2xl shadow-md hover:shadow-xl hover:-translate-y-1 transition text-left h-full border border-cyan-200"
            >
              <h4 class="font-semibold text-cyan-700 text-lg">
                Dashboard
              </h4>
              <p class="text-gray-500 text-sm mt-2">
                Visualize estatísticas do sistema.
              </p>
            </div>
          </RouterLink>

      </div>
    </main>
  </div>
  
</template>
<script setup>
import { computed } from "vue";

const token = localStorage.getItem("token");

const isAdmin = computed(() => {
  if (!token) return false;

  try {
    const payload = JSON.parse(atob(token.split(".")[1]));

    console.log(payload);

    return payload.role === "ROLE_ADMIN";
  } catch (e) {
    console.error(e);
    return false;
  }
});
</script>