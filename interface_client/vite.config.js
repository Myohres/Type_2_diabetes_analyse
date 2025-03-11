import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  server: {
    proxy: {
      '/patient': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false
      },
      '/historique' : {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false
      },
      '/assessment' : {
        target: 'http://localhost:8082',
        changeOrigin: true,
        secure: false
      },
      '/user' : {
        target: 'http://localhost:8083',
        changeOrigin: true,
        secure: false,
      }
    },



  },
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
})
