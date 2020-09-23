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
        .find((elem) => elem.board_id === board_id)
        .tiles.find((elem) => elem.tile_id === card.tile_id)
        .cards.push(card);
    },
    updateBoard(state, board) {
      state.boards.find((elem) => elem.board_id === board.board_id).name =
        board.name;
    },
    updateTile(state, tile) {
      state.boards
        .find((elem) => elem.board_id === tile.board_id)
        .tiles.find((elem) => elem.tile_id === tile.tile_id).name = tile.name;
    },
    updateCard(state, { board_id, card }) {
      state.boards
        .find((elem) => elem.board_id === board_id)
        .tiles.find((elem) => elem.tile_id === card.tile_id)
        .cards.find((elem) => elem.card_id === card.card_id).name = card.name;
    },
    deleteBoard(state, board) {
      const index = state.boards.findIndex(
        (elem) => elem.board_id === board.board_id
      );
      state.boards.splice(index, 1);
    },
    deleteTile(state, tile) {
      const boardIndex = state.boards.findIndex(
        (elem) => (elem.board_id = tile.board_id)
      );
      const tileIndex = state.boards[boardIndex].tiles.findIndex(
        (elem) => elem.tile_id == tile.tile_id
      );

      state.boards[boardIndex].tiles.splice(tileIndex, 1);
    },
    deleteCard(state, { board_id, card }) {
      const boardIndex = state.boards.findIndex(
        (elem) => elem.board_id === board_id
      );
      const tileIndex = state.boards[boardIndex].tiles.findIndex(
        (elem) => elem.tile_id === card.tile_id
      );
      const cardIndex = state.boards[boardIndex].tiles[
        tileIndex
      ].cards.findIndex((elem) => elem.card_id === card.card_id);

      state.boards[boardIndex].tiles[tileIndex].cards.splice(cardIndex, 1);
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
    fetchBoards({ commit, getters }) {
      const url = "http://localhost:8080/api/read/boards";
      axios
        .get(url, {
          params: {
            user_uid: getters.uid,
          },
        })
        .then((responce) => {
          responce.data.forEach(function(doc) {
            commit("addBoard", doc);
          });
        })
        .catch(function(error) {
          console.log("Error getting data: ", error);
        });
    },
    // Adds
    addBoard({ commit }, board) {
      const url = "http://localhost:8080/api/create/board";
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
      const url = "http://localhost:8080/api/create/tile";
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
      const url = "http://localhost:8080/api/create/card";
      axios
        .post(url, card)
        .then((responce) => {
          commit("addCard", { board_id: board_id, card: responce.data });
        })
        .catch(function(error) {
          console.log("Error getting result: ", error);
        });
    },
    // Updates
    updateBoard({ commit }, board) {
      const url = "http://localhost:8080/api/update/board";
      axios
        .post(url, board)
        .then((responce) => {
          commit("updateBoard", responce.data);
        })
        .catch(function(error) {
          console.log("Error getting results: ", error);
        });
    },
    updateTile({ commit }, tile) {
      const url = "http://localhost:8080/api/update/tile";
      axios
        .post(url, tile)
        .then((responce) => {
          commit("updateTile", responce.data);
        })
        .catch(function(error) {
          console.log("Error getting results: ", error);
        });
    },
    updateCard({ commit }, { board_id, card }) {
      const url = "http://localhost:8080/api/update/card";
      axios
        .post(url, card)
        .then((responce) => {
          commit("updateCard", { board_id: board_id, card: responce.data });
        })
        .catch(function(error) {
          console.log("Error getting results: ", error);
        });
    },
    // Deletes
    deleteBoard({ commit }, board) {
      const url = "http://localhost:8080/api/delete/board";
      axios
        .post(url, board)
        .then((responce) => {
          commit("deleteBoard", responce.data);
        })
        .catch(function(error) {
          console.log("Error getting results: ", error);
        });
    },
    deleteTile({ commit }, tile) {
      const url = "http://localhost:8080/api/delete/tile";
      axios
        .post(url, tile)
        .then((responce) => {
          commit("deleteTile", responce.data);
        })
        .catch(function(error) {
          console.log("Error getting results: ", error);
        });
    },
    deleteCard({ commit }, { board_id, card }) {
      const url = "http://localhost:8080/api/delete/card";
      axios
        .post(url, card)
        .then(() => {
          commit("deleteCard", { board_id: board_id, card: card });
        })
        .catch(function(error) {
          console.log("Error getting results: ", error);
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
