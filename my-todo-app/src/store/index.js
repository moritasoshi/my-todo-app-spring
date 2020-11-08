import Vue from "vue";
import Vuex from "vuex";
import firebase from "firebase";
import axios from "axios";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    login_user: null,
    boards: [],
    BASE_URL: "http://localhost:8080",
    authorization: null,
  },
  mutations: {
    setLoginUser(state, user) {
      state.login_user = user;
    },
    deleteLoginUser(state) {
      state.login_user = null;
    },
    setAuthorization(state, auth) {
      state.authorization = auth;
    },
    deleteBoards(state) {
      state.boards = [];
    },
    addBoard(state, board) {
      state.boards.push(board);
    },
    addTile(state, tile) {
      var newBoardIndex = state.boards.findIndex(
        (elem) => elem.board_id === tile.board_id
      );
      var newBoard = state.boards[newBoardIndex];
      newBoard.tiles.push(tile);

      state.boards.splice(newBoardIndex, 1, newBoard);
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
    updateTiles(state, { board_id, tiles }) {
      state.boards.find((elem) => elem.board_id === board_id).tiles = tiles;
    },
    deleteBoard(state, board) {
      const index = state.boards.findIndex(
        (elem) => elem.board_id === board.board_id
      );
      state.boards.splice(index, 1);
    },
    deleteTile(state, tile) {
      const boardIndex = state.boards.findIndex(
        (elem) => elem.board_id === tile.board_id
      );
      const tileIndex = state.boards[boardIndex].tiles.findIndex(
        (elem) => elem.tile_id === tile.tile_id
      );

      state.boards
        .find((elem) => elem.board_id === tile.board_id)
        .tiles.splice(tileIndex, 1);
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
      firebase.auth().signInWithPopup(google_auth_provider);
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
    loginApi({ commit }, user) {
      const url = this.state.BASE_URL + "/login";
      var reqUser = {};
      reqUser.uid = user.uid;
      reqUser.password = "password";
      axios
        .post(url, reqUser)
        .then((response) => {
          const auth = response.headers['authorization'];
          console.log(auth)
          commit("setAuthentication", auth);
        })
        .catch(function(error) {
          console.log("Error getting data: ", error);
        });
    },

    // Boards
    deleteBoards({ commit }) {
      commit("deleteBoards");
    },
    fetchBoards({ commit, getters }) {
      const url = this.state.BASE_URL + "/api/boards";
      axios
        .get(url + "/" + getters.uid)
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
      const url = this.state.BASE_URL + "/api/board";
      axios
        .post(url, board)
        .then((responce) => {
          board.board_id = responce.data.board_id;
          board.tiles = [];
          commit("addBoard", board);
        })
        .catch(function(error) {
          console.log("Error getting result: ", error);
        });
    },
    addTile({ commit }, tile) {
      const url = this.state.BASE_URL + "/api/tile";
      axios
        .post(url, tile)
        .then((responce) => {
          tile.tile_id = responce.data.tile_id;
          tile.cards = [];
          commit("addTile", tile);
        })
        .catch(function(error) {
          console.log("Error getting result: ", error);
        });
    },
    addCard({ commit }, { board_id, card }) {
      const url = this.state.BASE_URL + "/api/card";
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
      const url = this.state.BASE_URL + "/api/board";
      axios
        .put(url, board)
        .then(() => {
          commit("updateBoard", board);
        })
        .catch(function(error) {
          console.log("Error getting results: ", error);
        });
    },
    updateTile({ commit }, tile) {
      const url = this.state.BASE_URL + "/api/tile";
      axios
        .put(url, tile)
        .then(() => {
          commit("updateTile", tile);
        })
        .catch(function(error) {
          console.log("Error getting results: ", error);
        });
    },
    updateCard({ commit }, { board_id, card }) {
      const url = this.state.BASE_URL + "/api/card";
      axios
        .put(url, card)
        .then(() => {
          commit("updateCard", { board_id: board_id, card: card });
        })
        .catch(function(error) {
          console.log("Error getting results: ", error);
        });
    },
    updateTiles({ commit }, { board_id, tiles }) {
      const url = this.state.BASE_URL + "/api/tiles";
      axios
        .put(url, tiles)
        .then(() => {
          commit("updateTiles", { board_id: board_id, tiles: tiles });
        })
        .catch(function(error) {
          console.log("Error getting results: ", error);
        });
    },
    // Deletes
    deleteBoard({ commit }, board) {
      const url = this.state.BASE_URL + "/api/board";
      axios
        .delete(url + "/" + board.board_id)
        .then(() => {
          commit("deleteBoard", board);
        })
        .catch(function(error) {
          console.log("Error getting results: ", error);
        });
    },
    deleteTile({ commit }, tile) {
      const url = this.state.BASE_URL + "/api/tile";
      axios
        .delete(url + "/" + tile.tile_id)
        .then(() => {
          commit("deleteTile", tile);
        })
        .catch(function(error) {
          console.log("Error getting results: ", error);
        });
    },
    deleteCard({ commit }, { board_id, card }) {
      const url = this.state.BASE_URL + "/api/card";
      axios
        .delete(url + "/" + card.card_id)
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
