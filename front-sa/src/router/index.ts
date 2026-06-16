import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/home",
      name: "home",
      component: () => import("../views/HomeView.vue"),
    },

    {
      path: "/",
      name: "cadastro",
      component: () => import("../views/CadastroView.vue"),
    },
    {
      path: "/descricao/:id",
      name: "descricao",
      component: () => import("../views/DescricaoView.vue"),
    },
    {
      path: "/erros",
      name: "erros",
      component: () => import("../views/ErrosView.vue"),
    },
    {
      path: "/historico",
      name: "historico",
      component: () => import("../views/HistoricoView.vue"),
    },
    {
      path: "/Pacientes",
      name: "Pacientes",
      component: () => import("../views/PacientesView.vue"),
    },
    {
      path: "/Consulta",
      name: "Consulta",
      component: () => import("../views/ConsultaView.vue"),
    },
    {
      path: "/Entrar",
      name: "Entrar",
      component: () => import("../views/EntrarView.vue"),
    },
  ],
});
("");

export default router;
