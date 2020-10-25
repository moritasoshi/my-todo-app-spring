<template>
  <v-app-bar app color="blue darken-3" dark dense>
    <v-toolbar-items>
      <v-btn icon @click="toHome">
        <v-icon>mdi-home</v-icon>
      </v-btn>
    </v-toolbar-items>
    <v-spacer></v-spacer>

    <v-toolbar-title dark>My Board App</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-toolbar-items v-if="$store.state.login_user">
      <v-btn text @click="logout" style="text-transform: none;">Logout</v-btn>
    </v-toolbar-items>
    <v-toolbar-items v-else>
      <v-btn text class="signin-btn" @click="toLogin" style="text-transform: none;">Sign in</v-btn>
    </v-toolbar-items>

    <v-list-item-avatar>
      <img v-if="photoURL" :src="photoURL" />
    </v-list-item-avatar>
  </v-app-bar>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
export default {
  methods: {
    toHome() {
      if (this.$router.currentRoute.name !== "home") {
        this.$router.push({ name: "home" });
      }
    },
    toLogin() {
      if (this.$router.currentRoute.name !== "login") {
        this.$router.push({ name: "login" });
      }
    },
    ...mapActions(["logout"]),
  },
  computed: {
    ...mapGetters(["photoURL"]),
  },
};
</script>

<style scoped>
</style>
