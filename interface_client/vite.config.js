import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
      server: {
        host: '0.0.0.0',
        port: 5173,
        proxy: {
          '/patient': {
            target: 'http://gateway:8080',
            changeOrigin: true,
            secure: false
          },
          '/historique': {
            target: 'http://gateway:8081',
            changeOrigin: true,
            secure: false
          },
          '/assessment': {
            target: 'http://gateway:8082',
            changeOrigin: true,
            secure: false
          },
          '/user': {
            target: 'http://gateway:8083',
            changeOrigin: true,
            secure: false
          }
        }




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
