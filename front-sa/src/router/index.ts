import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/home",
      name: "home",
      component: () => import("../views/HomeView.vue"),
      meta: { requiresAuth: true },
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
      meta: { requiresAuth: true },
    },
    {
      path: "/erros",
      name: "erros",
      component: () => import("../views/ErrosView.vue"),
      meta: { requiresAuth: true },
    },
    {
      path: "/historico",
      name: "historico",
      component: () => import("../views/HistoricoView.vue"),
      meta: { requiresAuth: true },
    },

    {
      path: "/Consulta",
      name: "Consulta",
      component: () => import("../views/ConsultaView.vue"),
      meta: { requiresAuth: true },
    },
    {
      path: "/Entrar",
      name: "Entrar",
      component: () => import("../views/EntrarView.vue"),
    },
    {
      path: "/editar/:id",
      name: "EditarPaciente",
      component: () => import("@/views/EditarPacienteView.vue"),
      meta: { requiresAuth: true },
    },
  ],
});

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem("token");

  // se precisa login e não tem token → manda pro login
  if (to.meta.requiresAuth && !token) {
    next("/Entrar");
  }

  // se já está logado e tenta ir pro login → manda pra home
  else if (to.path === "/Entrar" && token) {
    next("/home");
  }

  else {
    next();
  }
});

//  :)  (:  ("");

export default router;
