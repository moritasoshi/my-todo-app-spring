<template>
  <v-app>
    <!-- toolbar -->
    <Toolbar />

    <!-- main -->
    <v-main>
      <v-container fluid fillMheight align-start>
        <!-- router-view: the matched component for the given path  -->
        <router-view />
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import Toolbar from "./components/Toolbar";
import firebase from "firebase";
import { mapActions } from "vuex";

export default {
  name: "App",
  components: {
    Toolbar,
  },
  created() {
    firebase.auth().onAuthStateChanged((user) => {
      if (user) {
        this.setLoginUser(user);
        this.fetchBoards();
      } else {
        this.deleteLoginUser();
        this.deleteBoards();
        this.$router.push({ name: "home" }, () => {});
      }
    });
  },
  data: () => ({
    //
  }),
  methods: {
    ...mapActions([
      "fetchBoards",
      "setLoginUser",
      "logout",
      "deleteLoginUser",
      "deleteBoards",
    ]),
  },
};
</script>
