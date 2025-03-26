import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Inscription from "@/views/Inscription.vue";
import Patient from "@/views/Patient.vue"



const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
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
      path: '/recherche',
      name: 'recherche',
      component: () => import('@/views/Recherche.vue'),
      meta: { requiresAuth: true },
    },
    {
      path: '/patient',
      name: 'patient',
      component: () => import('../views/Patient.vue'),
      meta: { requiresAuth: true },
    },
    {
      path: '/patient/:patId',
      name: 'PatientPage',
      component: Patient,
      props : true,
    },
    {
      path: '/parametre',
      name: 'parametre',
      component: () => import('../views/Parametre.vue'),
      meta: { requiresAuth: true },
    },
    {
      path: '/nouveauPatient',
      name: 'nouveauPatient',
      component: () => import('../views/NouveauPatient.vue'),
      meta: { requiresAuth: true },
    }
  ],
})

export default router
