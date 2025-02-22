<template>
  <div class="body">
<h2>Patient Historique</h2>
    <table class="table table-striped">
      <thead>
      <tr>
        <th scope="col">Id</th>
        <th scope="col">PatId</th>
        <th scope="col">Patient</th>
        <th scope="col">Note</th>
      </tr>
      </thead>
      <tbody>

      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: "PatientHistorique",

  patHistoriqueList: [{
    "patId": "2",
    "patient": "TestBorderline",
    "note": "Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement"
  },
    {
      "patId": "2",
      "patient": "TestBorderline",
      "note": "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale"
    }]
}

</script>

<style scoped>
.body{
  background-color: aquamarine;
}
</style>