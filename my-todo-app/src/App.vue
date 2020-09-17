<template>
  <v-app>
    <!-- toolbar -->
    <Toolbar />

    <!-- main -->
    <v-content>
      <v-container fluid fillMheight align-start>
        <!-- router-view: the matched component for the given path  -->
        <router-view />
      </v-container>
    </v-content>
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
      user = {
        uid: "j9clEAc35YRvIakDkb2Ail2AZ7l2",
        displayName: "Morita Soshi",
        photoURL:
          "https://lh3.googleusercontent.com/a-/AOh14GivaHRiuQza3R9STBkDrKOw_R_vE8GeFEntJyuTfQ",
        email: "moritasoushi@gmail.com",
        emailVerified: true,
        phoneNumber: null,
        isAnonymous: false,
        tenantId: null,
        providerData: [
          {
            uid: "113992211697520366876",
            displayName: "Morita Soshi",
            photoURL:
              "https://lh3.googleusercontent.com/a-/AOh14GivaHRiuQza3R9STBkDrKOw_R_vE8GeFEntJyuTfQ",
            email: "moritasoushi@gmail.com",
            phoneNumber: null,
            providerId: "google.com",
          },
        ],
        apiKey: "AIzaSyAidT0jZFywdSOHt9yrDUP_FHn761vpCcs",
        appName: "[DEFAULT]",
        authDomain: "my-todo-app-fbc02.firebaseapp.com",
        stsTokenManager: {
          apiKey: "AIzaSyAidT0jZFywdSOHt9yrDUP_FHn761vpCcs",
          refreshToken:
            "AE0u-NeCbe1C5a3oTHFfpXDo-BQ9vj47eMDxq3dFKAdPVVZ7jx9CFYcW-zArvLfmFFUZ9V2iPyVmidYligtJoc3yCX_m7Xb6DwTKgNrwfmzBfc-CIEGyRoQyVdrFRkf9RUMND7UJQEtmgMCxRkjKYSE9U_QlIY0QTuB-lgkx5d7fWggxJqY_-Mchr1k3MSOukpSoFpRXdWteYVEnIEWxe6RfCnd63P5cVPIq_U0gUrLXMBtR3XPRb3NkgryEk5rrSd8btJex8BK7oLiOKXGcFu495nVP59EuYooRWFX9PjjZpwGSLE9SpQ-veMZ6-qUGTIIXM4hnx5EasuKB0qqE05AlwBnUjLrxf6UeE88ByGp37DhVd4EPCAKf7H5GzXJ4Yz60-6emZ7ELFqSdZr0MNgJbW30oVPuutzNLt9AcN5itaEGnPjOqkNA",
          accessToken:
            "eyJhbGciOiJSUzI1NiIsImtpZCI6IjczNzVhZmY3MGRmZTNjMzNlOTBjYTM2OWUzYTBlZjQxMzE3MmZkODIiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiTW9yaXRhIFNvc2hpIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hLS9BT2gxNEdpdmFIUml1UXphM1I5U1RCa0RyS093X1JfdkU4R2VGRW50Snl1VGZRIiwiaXNzIjoiaHR0cHM6Ly9zZWN1cmV0b2tlbi5nb29nbGUuY29tL215LXRvZG8tYXBwLWZiYzAyIiwiYXVkIjoibXktdG9kby1hcHAtZmJjMDIiLCJhdXRoX3RpbWUiOjE2MDAzMDQ1MzAsInVzZXJfaWQiOiJqOWNsRUFjMzVZUnZJYWtEa2IyQWlsMkFaN2wyIiwic3ViIjoiajljbEVBYzM1WVJ2SWFrRGtiMkFpbDJBWjdsMiIsImlhdCI6MTYwMDMwNDUzMCwiZXhwIjoxNjAwMzA4MTMwLCJlbWFpbCI6Im1vcml0YXNvdXNoaUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJnb29nbGUuY29tIjpbIjExMzk5MjIxMTY5NzUyMDM2Njg3NiJdLCJlbWFpbCI6WyJtb3JpdGFzb3VzaGlAZ21haWwuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoiZ29vZ2xlLmNvbSJ9fQ.PhqiASxjJPbHCrlam5gDvn_Zza2YtwOu4CEN28fai8vPdOTbjbmek1vkslUkJZvnKPawgtn3W2hIJ_vUs3I4mhTFS-YM_UJnFukLDE4Vz8V5wR58BTu3Ildu9vgnhkrFZPGVGKcXojHMOVnJr_5Z34fXIDN3ZYx5WLMfuHIAOlhoXlR_vx2iPUQMD-c4r7GXjSnqBPvh8N2UnDFWpvvpbl1GVOh7Jl3ImuFz2ko0oKKF0dUxZsNlXjtcLmhN1PdDcCDynLybGoZA7fjR-B1RwA98oCJ32Rco93I6HFCSM5yNCq1g1wIk3IlXJJvqW4YRvR2yuh98SkgWtNJftfEdrg",
          expirationTime: 1600308130000,
        },
        redirectEventId: null,
        lastLoginAt: "1600304530800",
        createdAt: "1599617854704",
        multiFactor: { enrolledFactors: [] },
      };
      if (user) {
        this.setLoginUser(user);
        this.fetchBoards();
      } else {
        this.deleteLoginUser();
        this.$router.push({ name: "home" }, () => {});
      }
    });
  },
  data: () => ({
    //
  }),
  methods: {
    ...mapActions(["fetchBoards", "setLoginUser", "logout", "deleteLoginUser"]),
  },
};
</script>
