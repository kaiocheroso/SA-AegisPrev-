<template>
  <div
    class="flex flex-col items-center justify-center text-center py-12 min-h-[80vh]"
  >
    <i class="bi bi-vinyl text-7xl text-amber-500 mb-3 animate-spin-slow"></i>

    <h1 class="text-8xl font-extrabold text-gray-900">404</h1>

    <h3 class="text-xl text-gray-500 mb-3">Página Não Encontrada</h3>

    <p class="text-gray-500 mb-4 max-w-md">
      O link que você acessou pode estar quebrado ou a página foi removida.
    </p>
    <div class="flex flex-wrap gap-3 justify-center">
      <RouterLink
        to="/home"
        class="inline-flex items-center justify-center bg-amber-500 hover:bg-amber-600 text-gray-900 text-lg font-bold py-3 px-6 rounded-lg transition-colors duration-200"
      >
        <i class="bi bi-house mr-2"></i>Voltar para Home
      </RouterLink>
    </div>
  </div>
</template>
