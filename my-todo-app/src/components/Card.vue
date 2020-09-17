<template>
  <v-container>
    <v-card width="220" hover class="d-flex my-card">
      <v-card-text color="black" v-text="card.name"></v-card-text>
      <v-card-actions>
        <template>
          <v-btn
            color="green lighten-2"
            class="ma-2"
            icon
            @click="
              showCard({
                cardId: card.id,
                cardName: card.name,
                tileName: tile.name,
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
              tileId: tile.id,
              cardId: card.id,
            })
          "
        >
          <v-icon>mdi-delete</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
export default {
  // props: ["card", "tile"],
  data() {
    return {
      card: {
        id: "cardId",
        name: "cardName",
      },
      tile: {
        id: "tileId",
        name: "tileName",
      },
    };
  },
  methods: {
    showCard({ cardId, cardName, tileName }) {
      this.cardShow = !this.cardShow;
      this.targetCard.id = cardId;
      this.targetCard.name = cardName;
      this.targetCard.tileName = tileName;
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
  },
};
</script>
