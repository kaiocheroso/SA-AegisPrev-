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
          <RouterLink to="/Consulta">
            <button
              class="bg-cyan-600 text-white px-4 py-2 rounded-lg hover:bg-cyan-700 transition"
            >
              Consulta
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

      <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
        <div class="bg-white p-6 rounded-2xl shadow-md">
          <h3 class="text-gray-500"></h3>
          <p class="text-4xl font-bold text-cyan-700 mt-2"></p>
        </div>

        <div class="bg-white p-6 rounded-2xl shadow-md">
          <h3 class="text-gray-500"></h3>
          <p class="text-4xl font-bold text-emerald-600 mt-2"></p>
        </div>

        <div class="bg-white p-6 rounded-2xl shadow-md">
          <h3 class="text-gray-500"></h3>
          <p class="text-4xl font-bold text-blue-600 mt-2"></p>
        </div>
      </div>

      <h3 class="text-2xl font-bold text-cyan-800 mb-4"></h3>

      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <button
          class="bg-white p-6 rounded-2xl shadow-md hover:shadow-xl hover:-translate-y-1 transition text-left"
        >
          <h4 class="font-semibold text-cyan-700 text-lg">Pacientes</h4>
          <p class="text-gray-500 text-sm mt-2">Lista de pacientes</p>
        </button>

        <button
          class="bg-white p-6 rounded-2xl shadow-md hover:shadow-xl hover:-translate-y-1 transition text-left"
        >
          <h4 class="font-semibold text-cyan-700 text-lg">Histórico</h4>
          <p class="text-gray-500 text-sm mt-2">Historico dos pacientes</p>
        </button>

        <button
          class="bg-white p-6 rounded-2xl shadow-md hover:shadow-xl hover:-translate-y-1 transition text-left"
        >
          <h4 class="font-semibold text-cyan-700 text-lg">Descrição</h4>
          <p class="text-gray-500 text-sm mt-2">Descrição dos pacientes</p>
        </button>
      </div>
    </main>
  </div>
</template>
