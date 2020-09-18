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
        <v-btn icon @click="deleteThisBoard">
          <v-icon>mdi-delete</v-icon>
        </v-btn>
      </template>
      <v-spacer></v-spacer>
    </v-container>
    <!-- ボード名変更 -->
    <ValidationObserver v-slot="{ invalid }">
      <v-form v-show="boardShow">
        <ValidationProvider
          rules="required"
          v-slot="{ errors, valid }"
          name="ボード名"
        >
          <v-text-field
            label="ボード名を入力"
            color="grey"
            v-model="targetBoardName"
            :error-messages="errors"
            :success="valid"
          ></v-text-field>
          <v-btn
            color="green lighten-2"
            dark
            class="ml-2"
            :disabled="invalid"
            @click="editBoard"
            >ボード名を変更</v-btn
          >
        </ValidationProvider>
      </v-form>
    </ValidationObserver>

    <!-- 既存ボード -->
    <v-container class="d-flex">
      <v-card
        width="230"
        class="mx-1"
        v-for="(tile, index) in board.tiles"
        :key="index"
      >
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
      <ValidationObserver v-slot="{ invalid }">
        <v-form>
          <ValidationProvider
            rules="required"
            v-slot="{ errors, valid }"
            name="リスト名"
          >
            <v-text-field
              v-model="newTile.name"
              label="+ リストを追加"
              outlined
              color="green"
              class="shrink"
              :error-messages="errors"
              :success="valid"
            ></v-text-field>
          </ValidationProvider>
          <v-btn
            color="green lighten-2"
            dark
            class="ml-2"
            :disabled="invalid"
            @click="addTile"
            >リストを追加</v-btn
          >
        </v-form>
      </ValidationObserver>
    </v-container>

    <!-- 
    <-- トランジション関連 --
    <ValidationObserver v-slot="{ invalid }">
      <ValidationProvider
        rules="required"
        v-slot="{ errors, valid }"
        name="リスト名"
      >
      </ValidationProvider>
    </ValidationObserver>
    -->

    <!-- リスト名変更 -->
    <ValidationObserver v-slot="{ invalid }">
      <v-form v-show="tileShow">
        <ValidationProvider
          rules="required"
          v-slot="{ errors, valid }"
          name="リスト名"
        >
          <v-text-field
            label="リスト名を入力"
            color="grey"
            v-model="targetTile.name"
            :error-messages="errors"
            :success="valid"
          ></v-text-field>
          <v-btn
            color="green lighten-2"
            dark
            :disabled="invalid"
            class="ml-2"
            @click="editTile"
            >リスト名を変更</v-btn
          >
        </ValidationProvider>
      </v-form>
    </ValidationObserver>

    <!-- カード名変更 -->
    <ValidationObserver v-slot="{ invalid }">
      <v-form v-show="cardShow">
        <ValidationProvider
          rules="required"
          v-slot="{ errors, valid }"
          name="カード名"
        >
          <v-text-field
            label="カード名を入力"
            color="grey"
            v-model="targetCard.name"
            :error-messages="errors"
            :success="valid"
          ></v-text-field>
          <v-btn
            color="green lighten-2"
            dark
            :disabled="invalid"
            class="ml-2"
            @click="editCard"
            >カード名を変更</v-btn
          >
        </ValidationProvider>
      </v-form>
    </ValidationObserver>
  </v-app>
</template>

<script>
import Draggable from "vuedraggable";
import { mapActions } from "vuex";
// import { required } from "vee-validate/dist/rules";
import ja from "vee-validate/dist/locale/ja.json";
import {
  extend,
  localize,
  ValidationProvider,
  ValidationObserver,
} from "vee-validate";

// バリデーションルール
extend("required", (value) => {
  if (value) {
    return true;
  }
  return false;
});
// Localization
localize("ja", ja);

export default {
  components: {
    Draggable,
    ValidationProvider,
    ValidationObserver,
  },
  data() {
    return {
      tileShow: false,
      boardShow: false,
      cardShow: false,
      targetBoardName: "",
      targetTile: {
        tile_id: null,
        name: null,
        board_id: null,
      },
      targetCard: {
        card_id: null,
        name: null,
        tile_id: null,
      },

      newTile: {
        name: "",
        cards: [],
      },
      newCard: {
        name: "new card",
      },
      board: {},
    };
  },
  props: {
    slug: {
      type: String,
      required: true,
    },
  },

  created() {
    this.board = this.$store.getters.getBoardByName(this.slug);
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
    showCard({ cardId, cardName, tileId }) {
      this.cardShow = !this.cardShow;
      this.targetCard.card_id = cardId;
      this.targetCard.name = cardName;
      this.targetCard.tile_id = tileId;
    },
    // Edits
    editBoard() {
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
      this.$store.dispatch("updateTile", this.targetTile);
      this.targetTile = {};
      this.tileShow = false;
    },
    editCard() {
      this.$store.dispatch("updateCard", {
        board_id: this.board.board_id,
        card: this.targetCard,
      });
      this.targetCard = {};
      this.cardShow = false;
    },
    // Adds
    addTile() {
      this.newTile.board_id = this.board.board_id;
      this.$store.dispatch("addTile", this.newTile);
      this.newTile = {
        name: null,
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

    deleteThisBoard() {
      alert(
        this.board.board_name +
          " : 現在のボードを削除してもよろしいですか？\n※このリスト内の全てのリスト・カードも削除されます"
      );
      this.$store.dispatch("deleteBoard", this.board);
      this.$router.push({
        name: "home",
      });
    },
    deleteTile({ tileId }) {
      const tileIndex = this.board.tiles.findIndex(
        (tile) => tile.id === tileId
      );
      alert(
        this.board.tiles[tileIndex].name +
          " : このリストを削除してもよろしいですか？\n※このリスト内の全てのカードも削除されます"
      );
      this.board.tiles.splice(tileIndex, 1);
    },
    deleteCard({ tileId, cardId }) {
      const tileIndex = this.board.tiles.findIndex(
        (tile) => tile.id === tileId
      );
      const cardIndex = this.board.tiles[tileIndex].cards.findIndex(
        (card) => card.id === cardId
      );
      this.board.tiles[tileIndex].cards.splice(cardIndex, 1);
    },
    uuid() {
      return "xxxxxxxxxxxxxxxxxx".replace(/[xy]/g, (c) => {
        var r = (Math.random() * 16) | 0,
          v = c == "x" ? r : (r & 0x3) | 0x8;
        return v.toString(16);
      });
    },
    ...mapActions(["updateBoard", "deleteBoard"]),
  },
};
</script>

<style scoped>
.my-card {
  margin: 10px 0;
}
</style>
