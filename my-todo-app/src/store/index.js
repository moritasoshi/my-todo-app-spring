import Vue from "vue";
import Vuex from "vuex";
import firebase from "firebase";
import axios from "axios";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    login_user: null,
    boards: [],
  },
  mutations: {
    setLoginUser(state, user) {
      state.login_user = user;
    },
    deleteLoginUser(state) {
      state.login_user = null;
    },
    addBoard(state, board) {
      state.boards.push(board);
    },
    addTile(state, tile) {
      const index = state.boards.findIndex(
        (elem) => elem.board_id === tile.board_id
      );
      state.boards[index].tiles.push(tile);
    },
    addCard(state, { board_id, card }) {
      state.boards
        .find((board) => {
          board.board_id === board_id;
        })
        .tiles.find((tile) => {
          tile.tile_id === card.tile_id;
        })
        .push(card);
    },
    updateBoard(state, board) {
      const index = state.boards.findIndex(
        (elem) => elem.board_id === board.board_id
      );
      state.boards[index] = board;
    },
    deleteBoard(state, board) {
      const index = state.boards.findIndex(
        (elem) => elem.board_id === board.board_id
      );
      state.boards.splice(index, 1);
    },
  },
  actions: {
    // Firebase Authentication
    login() {
      const google_auth_provider = new firebase.auth.GoogleAuthProvider();
      firebase.auth().signInWithRedirect(google_auth_provider);
    },
    logout() {
      firebase.auth().signOut();
    },
    setLoginUser({ commit }, user) {
      commit("setLoginUser", user);
    },
    deleteLoginUser({ commit }) {
      commit("deleteLoginUser");
    },
    // Boards
    fetchBoards({ commit }) {
      axios
        .get("http://localhost:8080/api/read/boards")
        .then((responce) => {
          responce.data.forEach(function(doc) {
            commit("addBoard", doc);
          });
        })
        .catch(function(error) {
          console.log("Error getting data: ", error);
        });
    },
    addBoard({ commit }, board) {
      var url = "http://localhost:8080/api/create/board";
      axios
        .post(url, board)
        .then((responce) => {
          commit("addBoard", responce.data);
        })
        .catch(function(error) {
          console.log("Error getting result: ", error);
        });
    },
    addTile({ commit }, tile) {
      console.log(tile.name);
      var url = "http://localhost:8080/api/create/tile";
      axios
        .post(url, tile)
        .then((responce) => {
          commit("addTile", responce.data);
        })
        .catch(function(error) {
          console.log("Error getting result: ", error);
        });
    },
    addCard({ commit }, { board_id, card }) {
      var url = "http://localhost:8080/api/create/card";
      axios
        .post(url, card)
        .then((responce) => {
          commit("addCard", { board_id: board_id, card: responce.data });
        })
        .catch(function(error) {
          console.log("Error getting result: ", error);
        });
    },
    updateBoard({ getters, commit }, board) {
      firebase
        .firestore()
        .collection("users")
        .doc(getters.uid)
        .collection("boards")
        .doc(board.board_id)
        .set(board)
        .then(function() {
          commit("updateBoard", board);
        })
        .catch(function(error) {
          console.log("Error getting documents: ", error);
        });
    },
    deleteBoard({ commit, getters }, board) {
      firebase
        .firestore()
        .collection("users")
        .doc(getters.uid)
        .collection("boards")
        .doc(board.board_id)
        .delete()
        .then(function() {
          console.log("Document successfully deleted!");
          commit("deleteBoard", board);
        })
        .catch(function(error) {
          console.error("Error removing document: ", error);
        });
    },
  },
  getters: {
    uid: (state) => (state.login_user ? state.login_user.uid : null),
    userName: (state) => (state.login_user ? state.login_user.displayName : ""),
    photoURL: (state) => (state.login_user ? state.login_user.photoURL : ""),
    boards: (state) => state.boards,
    getBoardByName: (state) => (name) =>
      state.boards.find((board) => board.name === name),
  },
});
