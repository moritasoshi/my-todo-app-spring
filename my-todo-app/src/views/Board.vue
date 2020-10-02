<template>
  <v-app>
    <v-container class="d-flex">
      <h2>{{ board.name }}</h2>
      <template>
        <v-btn color="green lighten-2" class="mx-5" icon @click="showBoard">
          <v-icon>mdi-pencil</v-icon>
        </v-btn>
      </template>
      <template>
        <v-btn icon @click="deleteBoard">
          <v-icon>mdi-delete</v-icon>
        </v-btn>
      </template>
      <v-spacer></v-spacer>
      <v-btn class="white--text" color="green" @click="updateTiles">save</v-btn>
    </v-container>
    <!-- ボード名変更 -->
    <v-form v-show="boardShow">
      <v-text-field label="ボード名を入力" color="grey" v-model="targetBoardName"></v-text-field>
      <v-btn color="green lighten-2" dark class="ml-2" @click="editBoard">ボード名を変更</v-btn>
    </v-form>

    <!-- 既存ボード -->
    <v-container class="d-flex">
      <v-card width="230" class="mx-1" v-for="(tile, index) in board.tiles" :key="index">
        <v-app-bar dark color="grey" dense>
          <v-toolbar-title>{{ tile.name }}</v-toolbar-title>
          <v-spacer></v-spacer>
          <template>
            <v-btn
              color="white"
              class="ma-2"
              icon
              @click="
                showTile({
                  tileId: tile.tile_id,
                  tileName: tile.name,
                })
              "
            >
              <v-icon>mdi-pencil</v-icon>
            </v-btn>
          </template>
          <template>
            <v-btn
              icon
              @click="
                deleteTile({
                  tileId: tile.tile_id,
                })
              "
            >
              <v-icon>mdi-delete</v-icon>
            </v-btn>
          </template>
        </v-app-bar>
        <v-container>
          <draggable group="all-tasks" :list="tile.cards">
            <v-card
              v-for="(card, i) in tile.cards"
              :key="i"
              width="220"
              hover
              class="d-flex my-card"
            >
              <v-card-text color="black" v-text="card.name"></v-card-text>
              <v-card-actions>
                <template>
                  <v-btn
                    color="green lighten-2"
                    class="ma-2"
                    icon
                    @click="
                      showCard({
                        cardId: card.card_id,
                        cardName: card.name,
                        tileId: tile.tile_id,
                        indicator: card.indicator,
                      })
                    "
                  >
                    <v-icon>mdi-pencil</v-icon>
                  </v-btn>
                </template>
                <v-btn
                  icon
                  @click="
                    deleteCard({
                      tileId: tile.tile_id,
                      cardId: card.card_id,
                    })
                  "
                >
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
              </v-card-actions>
            </v-card>
          </draggable>
          <v-btn @click="addCard(tile.tile_id)">Add</v-btn>
        </v-container>
      </v-card>
      <!-- リスト追加 -->
      <v-form>
        <v-text-field v-model="newTile.name" label="+ リストを追加" outlined color="green" class="shrink"></v-text-field>
        <v-btn color="green lighten-2" dark class="ml-2" @click="addTile">リストを追加</v-btn>
      </v-form>
    </v-container>

    <!-- リスト名変更 -->
    <v-form v-show="tileShow">
      <v-text-field label="リスト名を入力" color="grey" v-model="targetTile.name"></v-text-field>
      <v-btn color="green lighten-2" dark class="ml-2" @click="editTile">リスト名を変更</v-btn>
    </v-form>

    <!-- カード名変更 -->
    <v-form v-show="cardShow">
      <v-text-field label="カード名を入力" color="grey" v-model="targetCard.name"></v-text-field>
      <v-btn color="green lighten-2" dark class="ml-2" @click="editCard">カード名を変更</v-btn>
    </v-form>
  </v-app>
</template>

<script>
import Draggable from "vuedraggable";

export default {
  components: {
    Draggable,
  },
  data() {
    return {
      // board: {},
      tileShow: false,
      boardShow: false,
      cardShow: false,
      targetBoardName: "",
      targetTile: {},
      targetCard: {},

      newTile: {
        name: "",
        cards: [],
      },
      newCard: {
        name: "new card",
      },
    };
  },
  props: {
    slug: {
      type: String,
      required: true,
    },
  },
  computed: {
    board() {
      return this.$store.getters.getBoardByName(this.slug);
    },
  },
  created() {
    // this.board = this.$store.getters.getBoardByName(this.slug);
  },
  methods: {
    // Show when edit buttons pushed
    showBoard() {
      this.boardShow = !this.boardShow;
    },
    showTile({ tileId, tileName }) {
      this.tileShow = !this.tileShow;
      this.targetTile.tile_id = tileId;
      this.targetTile.name = tileName;
      this.targetTile.board_id = this.board.board_id;
    },
    showCard({ cardId, cardName, tileId, indicator }) {
      this.cardShow = !this.cardShow;
      this.targetCard.card_id = cardId;
      this.targetCard.name = cardName;
      this.targetCard.tile_id = tileId;
      this.targetCard.indicator = indicator;
    },
    // Edits
    editBoard() {
      const valid = /^\w/.test(this.targetBoardName);
      if (!valid) {
        return;
      }
      this.board.name = this.targetBoardName;
      this.$store.dispatch("updateBoard", this.board);
      this.$router.push({
        name: "board",
        params: { slug: this.targetBoardName },
      });
      this.targetBoardName = "";
      this.boardShow = false;
    },
    editTile() {
      const valid = /^\w/.test(this.targetTile.name);
      if (!valid) {
        return;
      }
      this.$store.dispatch("updateTile", this.targetTile);
      this.targetTile = {};
      this.tileShow = false;
    },
    editCard() {
      const valid = /^\w/.test(this.targetCard.name);
      if (!valid) {
        return;
      }
      this.$store.dispatch("updateCard", {
        board_id: this.board.board_id,
        card: this.targetCard,
      });
      this.targetCard = {};
      this.cardShow = false;
    },
    updateTiles() {
      this.board.tiles.forEach((tile) => {
        tile.cards.forEach((card, index) => {
          card.tile_id = tile.tile_id;
          card.indicator = index;
        });
      });
      this.$store.dispatch("updateTiles", {
        board_id: this.board.board_id,
        tiles: this.board.tiles,
      });
    },
    // Adds
    addTile() {
      const valid = /^\w/.test(this.newTile.name);
      if (!valid) {
        return;
      }
      this.newTile.board_id = this.board.board_id;
      this.$store.dispatch("addTile", this.newTile);
      this.newTile = {
        name: "",
        cards: [],
      };
    },
    addCard(tileId) {
      this.newCard.tile_id = tileId;
      this.$store.dispatch("addCard", {
        board_id: this.board.board_id,
        card: this.newCard,
      });
      // 初期化
      this.newCard = {
        name: "new card",
        tile_id: null,
      };
    },
    deleteBoard() {
      const isDelete = window.confirm(
        this.board.name +
          " : 現在のボードを削除してもよろしいですか？\n※このリスト内の全てのリスト・カードも削除されます"
      );
      if (isDelete) {
        this.$store.dispatch("deleteBoard", this.board);
        this.$router.push({
          name: "home",
        });
      }
    },
    deleteTile({ tileId }) {
      const tileIndex = this.board.tiles.findIndex(
        (elem) => elem.tile_id === tileId
      );
      const isDelete = window.confirm(
        this.board.tiles[tileIndex].name +
          " : このリストを削除してもよろしいですか？\n※このリスト内の全てのカードも削除されます"
      );
      if (isDelete) {
        this.$store.dispatch("deleteTile", this.board.tiles[tileIndex]);
      }
    },
    deleteCard({ tileId, cardId }) {
      const tileIndex = this.board.tiles.findIndex(
        (tile) => tile.tile_id === tileId
      );
      const cardIndex = this.board.tiles[tileIndex].cards.findIndex(
        (card) => card.card_id === cardId
      );
      const boardId = this.board.board_id;
      const card = this.board.tiles[tileIndex].cards[cardIndex];
      this.$store.dispatch("deleteCard", { board_id: boardId, card: card });
    },
    uuid() {
      return "xxxxxxxxxxxxxxxxxx".replace(/[xy]/g, (c) => {
        var r = (Math.random() * 16) | 0,
          v = c == "x" ? r : (r & 0x3) | 0x8;
        return v.toString(16);
      });
    },
  },
};
</script>

<style scoped>
.my-card {
  margin: 10px 0;
}
</style>
