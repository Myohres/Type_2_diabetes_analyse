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
          '/pat-information': {
            target: 'http://localhost:8083',
            changeOrigin: true,
            secure: false
          },
          '/pat-history': {
            target: 'http://localhost:8083',
            changeOrigin: true,
            secure: false
          },
          '/pat-assessment': {
            target: 'http://localhost:8083',
            changeOrigin: true,
            secure: false
          },
          '/user': {
            target: 'http://localhost:8083',
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
