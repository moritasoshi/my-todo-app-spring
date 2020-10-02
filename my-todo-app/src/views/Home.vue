<template>
  <v-container>
    <h2>Your boards</h2>
    <v-container class="d-flex">
      <v-card
        v-for="(board, index) in boards"
        :key="index"
        :to="{
          name: 'board',
          params: { slug: board.name },
        }"
        class="ma-2"
        width="200"
        height="125"
        hover
        dark
        link
        color="cyan lighten-2"
      >
        <v-card-title v-text="board.name"></v-card-title>
      </v-card>
    </v-container>
    <h2>Create a new board</h2>
    <v-container>
      <v-flex xs4 mt-5>
        <v-card>
          <v-card-text>
            <v-form>
              <v-text-field outlined v-model="newBoard.name" label="ボードタイトル" color="grey"></v-text-field>
              <v-btn color="green lighten-2" dark class="ml-2" @click="createNewBoard">新しいボードを作成</v-btn>
            </v-form>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-container>
  </v-container>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  data() {
    return {
      newBoard: {
        name: "",
        user_uid: "",
      },
    };
  },
  computed: {
    boards() {
      return this.boards;
    },
    ...mapGetters(["boards"]),
  },
  methods: {
    createNewBoard() {
      const valid = /^\w/.test(this.newBoard.name);
      const notNull = /^\S/.test(this.newBoard.name);

      if (!valid || !notNull) {
        return;
      }
      const uid = this.$store.getters.uid;
      if (uid) {
        this.newBoard.user_uid = uid;
        this.addBoard(this.newBoard);
        this.newBoard = { name: "", user_uid: "" };
      }
    },
    ...mapActions(["addBoard"]),
  },
};
</script>
