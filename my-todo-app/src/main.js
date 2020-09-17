import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import firebase from "firebase";


Vue.config.productionTip = false;

// Your web app's Firebase configuration
var firebaseConfig = {
  apiKey: "AIzaSyAidT0jZFywdSOHt9yrDUP_FHn761vpCcs",
  authDomain: "my-todo-app-fbc02.firebaseapp.com",
  databaseURL: "https://my-todo-app-fbc02.firebaseio.com",
  projectId: "my-todo-app-fbc02",
  storageBucket: "my-todo-app-fbc02.appspot.com",
  messagingSenderId: "47428742568",
  appId: "1:47428742568:web:cded40ae63025e5493c19d",
  measurementId: "G-LTEMSYTG5D",
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);
firebase.analytics();



new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");
