<template>
  <form @submit.prevent="cadastrar">
    <div
      class="min-h-screen flex items-center justify-center bg-gradient-to-br from-cyan-300 via-blue-100 to-emerald-300"
    >
      <div class="bg-white p-8 rounded-2xl shadow-2xl w-full max-w-md">
        <h1 class="text-3xl font-bold text-center text-gray-800 mb-6">
          Cadastro
        </h1>

        <div class="space-y-4">
          <input
            v-model="form.email"
            type="email"
            placeholder="Digite seu email"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
          />

          <input
            v-model="form.senha"
            type="password"
            placeholder="Digite sua senha"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
          />

          <button
            type="submit"
            class="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition duration-300"
          >
            Cadastrar
          </button>
        </div>

        <p class="text-center text-gray-500 mt-4 text-sm">
          Já possui uma conta?
          <a href="#" class="text-blue-600 hover:underline">Entrar</a>
        </p>
      </div>
    </div>
  </form>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { postUsuario } from "../services/api";
import type { Usuario } from "@/interfaces/Usuario";

const router = useRouter();
const email = ref("");
const senha = ref("");
const form = ref({} as Usuario);

async function cadastrar(): Promise<void> {
  await postUsuario({
    email: form.value.email,
    senha: form.value.senha,
  });
  router.push("/home");
}
</script>
