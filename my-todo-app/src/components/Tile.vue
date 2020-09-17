<template>
  <div id="app">
    <Card ></Card>
  </div>
</template>

<script>
import Card from "@/components/Card.vue";

export default {
  components: { Card },
  data() {
    return {
      tile: {
        id: "tileId",
        name: "tile1",
        cards: {},
      },
    };
  },
};
</script>
