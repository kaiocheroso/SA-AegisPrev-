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
      path: "/dashboard",
      name: "dashboard",
      component: () => import("../views/DashboardView.vue"),
      meta: { requiresAuth: true, role: "ROLE_ADMIN" },
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
      path: "/cadastrar-consulta",
      name: "consulta",
      component: () => import("../views/CadastrarConsultaView.vue"),
      meta: { requiresAuth: true },
    },
    {
      path: "/entrar",
      name: "entrar",
      component: () => import("../views/EntrarView.vue"),
    },
    {
      path: "/permissoes", //modificar
      name: "permissoes",
      component: () => import("@/views/AdminView.vue"),
      meta: { requiresAuth: true, role: "ROLE_ADMIN" },
    },
    {
      path: "/consultas",
      name: "consultas",
      component: () => import("@/views/ConsultasView.vue"),
      meta: { requiresAuth: true },
    }
  ],
});

router.beforeEach(async (to, from) => {
  const token = localStorage.getItem("token");

  // sem login
  if (to.meta.requiresAuth && !token && from.name !== "entrar") {
    return { name: "entrar" };
  }
  
  // pega role do token
  let role = null;

  if (token) {
    try {
      const payload = JSON.parse(atob(token.split(".")[1]));
      role = payload.role;
    } catch (e) {
      console.error("Token inválido");
      return { name: "entrar" };
    }
  }

  // se rota exige role específica
  if (to.meta.role && to.meta.role !== role) {
    return { name: "home" }; // ou página 403
  }

  // já logado tentando login
  if (to.path === "/entrar" && token) {
    return { name: "home" };
  }
  

});

//  :)  (:  ("");

export default router;
