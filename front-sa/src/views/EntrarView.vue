<template>
  <form @submit.prevent="Entrar">
    <div
      class="min-h-screen flex items-center justify-center bg-gradient-to-br from-cyan-300 via-blue-100 to-emerald-300"
    >
      <div class="bg-white p-8 rounded-2xl shadow-2xl w-full max-w-md">
        <h1 class="text-4xl font-bold text-center text-gray-800 mb-1">
          Entrar
        </h1>

        <p class="text-center text-gray-500 mb-4">Conecte-se à sua conta</p>
        <div class="space-y-4">
          <label class="form-label">Email</label>
          <input
            v-model="form.email"
            type="email"
            placeholder="Digite seu email"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
          />

          <label class="form-label">Senha</label>
          <input
            v-model="form.password"
            type="password"
            placeholder="Digite sua senha"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
          />

          <p class="text-center text-gray-500 mt-4 text-sm"></p>
          <button
            type="submit"
            class="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition duration-300"
          >
            Logar
          </button>
        </div>

        <p class="text-center text-gray-500 mt-4 text-sm">
          Não possui uma conta?
          <router-link to="/" class="text-blue-600 hover:underline"
            >Cadastrar</router-link
          >
        </p>
      </div>
    </div>
  </form>
</template>
<script lang="ts" setup>
import { loginAuth } from "../services/api";
import type { Medico } from "@/interfaces/Medico";
import { useRouter } from "vue-router";
import { ref } from "vue";

const router = useRouter();
const form = ref({
  email: "",
  password: "",
});

async function Entrar(): Promise<void> {
  const response = await loginAuth({
    email: form.value.email,
    password: form.value.password,
  });

  // se backend retornar token, você pode salvar aqui:
  // localStorage.setItem("token", response.token);
  localStorage.setItem("token", response.token);
  router.push("/home");
}
</script>
