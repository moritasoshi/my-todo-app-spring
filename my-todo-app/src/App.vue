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
    firebase.auth().onAuthStateChanged(async (user) => {
      if (user) {
        this.$router.push({ name: "home" }, () => {});
        this.setLoginUser(user);
        await this.$store.dispatch("loginApi", user);
        this.$store.dispatch("fetchBoards");
      } else {
        this.deleteLoginUser();
        this.deleteBoards();
        this.$router.push({ name: "login" }, () => {});
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
      "loginApi",
      "logout",
      "deleteLoginUser",
      "deleteBoards",
    ]),
  },
};
</script>

<style lang="scss">
* :not(.v-icon) {
  font-family: Arial;
}
</style>
