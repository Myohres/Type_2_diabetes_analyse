<script setup>
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
</script>

<template>
<div class="body">
  <header>
    <img alt="Vue logo" class="logo" src="@/assets/logo.svg" width="125" height="125" />

    <div>
      <HelloWorld msg="Medi Labo Solution" />

      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/inscription">Inscription</RouterLink>
        <RouterLink to="/connexion">Connexion</RouterLink>
        <RouterLink to="/recherche">Recherche Patient</RouterLink>
        <RouterLink to="/parametre">Parametre</RouterLink>
      </nav>
    </div>
  </header>
<RouterView></RouterView>

</div>
</template>

<style scoped>
.body{
  text-align: center;


}

header {
  line-height: 1.5;
  max-height: 100vh;

}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  background-color: burlywood;
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {

    place-items: center;

  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {


  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }

  button {
    margin-top: 10px;
    padding: 8px;
    background-color: blue;
    color: white;
    border: none;
    cursor: pointer;
  }
}
</style>
