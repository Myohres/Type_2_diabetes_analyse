import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AboutView from "@/views/AboutView.vue";
import Inscription from "@/views/Inscription.vue";


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',

    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: AboutView,
    },
    {
      path: '/inscription',
      name: 'inscription',
      component: Inscription,
    },
    {
      path: '/connexion',
      name: 'connexion',
      component: () => import('../views/Connexion.vue'),
    },
    {
      path: '/patient',
      name: 'patient',
      component: () => import('../views/Patient.vue'),
    },
    {
      path: '/bilan',
      name: 'bilan',
      component: () => import('../views/Bilan.vue'),
    },
    {
      path: '/parametre',
      name: 'parametre',
      component: () => import('../views/Parametre.vue'),
    },
  ],
})

export default router
