<template>
  <div class="min-h-screen bg-gradient-to-br from-cyan-100 via-blue-50 to-emerald-100">
    <header class="bg-white shadow-md relative">
      <div class="max-w-7xl mx-auto px-6 py-4 flex justify-between items-center">

        <h1 class="text-2xl font-bold text-cyan-800">AegisPrev</h1>

        <div class="flex items-center gap-4">

          <RouterLink to="/cadastrar-consulta">
            <button
              class="bg-cyan-600 text-white px-4 py-2 rounded-lg hover:bg-cyan-700 transition"
            >
              Cadastrar Consulta
            </button>
          </RouterLink>

          <div class="flex items-center gap-3">
            <p class="text-sm text-gray-600">
              Olá, <span class="font-semibold text-cyan-700">
                {{ medico?.nome }}
              </span>
            </p>

          
            <div class="relative avatar-menu">

              <button
                @click="toggleMenu"
                class="w-10 h-10 rounded-full bg-gray-200 flex items-center justify-center hover:bg-gray-300 transition"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="w-6 h-6 text-gray-600"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M5.121 17.804A9 9 0 1118.88 17.8M15 11a3 3 0 11-6 0 3 3 0 016 0z"
                  />
                </svg>
              </button>

              <!-- DROPDOWN (DENTRO DO RELATIVE) -->
              <div
                v-if="menuOpen"
                class="absolute right-0 mt-2 w-44 bg-white border rounded-lg shadow-lg z-50 overflow-hidden"
              >
                <button
                  @click="openProfile"
                  class="w-full text-left px-4 py-2 hover:bg-gray-100"
                >
                  Perfil
                </button>

                <button
                  @click="logout"
                  class="w-full text-left px-4 py-2 hover:bg-red-100 text-red-600"
                >
                  Logout
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div v-if="profileOpen" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
        <div class="bg-white rounded-2xl shadow-xl w-96 p-6 relative">

          <h2 class="text-xl font-bold text-cyan-700 mb-4">
            Meu Perfil
          </h2>

          <div class="space-y-2 text-gray-700">

            <p><strong>Nome:</strong> {{ medico?.nome }}</p>
            <p><strong>Email:</strong> {{ medico?.email }}</p>
            <p><strong>Sexo:</strong> {{ medico?.sexo }}</p>
            <p><strong>Idade:</strong> {{ medico?.idade }}</p>
            <p><strong>Papel:</strong> {{ user.role }}</p>

            <!-- se tiver especialidade -->
            <p v-if="medico?.especialidade">
              <strong>Especialidade:</strong> {{ medico?.especialidade }}
            </p>

          </div>

          <button
            @click="profileOpen = false"
            class="absolute top-2 right-3 text-gray-500 hover:text-black"
          >
            ✕
          </button>
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
<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { getMedicoLogado } from "@/services/api";

const medico = ref<any>(null);
const menuOpen = ref(false);
const profileOpen = ref(false);

function toggleMenu() {
  menuOpen.value = !menuOpen.value;
}

function openProfile() {
  profileOpen.value = true;
  menuOpen.value = false;
}

function logout() {
  localStorage.removeItem("token");
  window.location.href = "/";
}

/* decode JWT */
function decodeToken() {
  const token = localStorage.getItem("token");
  if (!token) return null;

  try {
    const [, payloadBase64] = token.split(".");
    if (!payloadBase64) return null;
    return JSON.parse(atob(payloadBase64));
  } catch (e) {
    return null;
  }
}

/* usuário (perfil) */
const user = computed(() => {
  const payload = decodeToken();

  return {
    email: payload?.sub || payload?.email || "desconhecido",
    role: payload?.role || "N/A",
    nome: payload?.nome || "Usuário",
  };
});

/* admin */
const isAdmin = computed(() => {
  const payload = decodeToken();
  return payload?.role === "ROLE_ADMIN";
});

/* fechar menu ao clicar fora */
onMounted(async () => {
  try {
    medico.value = await getMedicoLogado();
  } catch (err) {
    console.error("Erro ao buscar médico logado:", err);
  }
  window.addEventListener("click", (e) => {
    const el = e.target;

    if (el instanceof HTMLElement) {
      if (!el.closest(".avatar-menu")) {
        menuOpen.value = false;
      }
    }
  });
});
</script>