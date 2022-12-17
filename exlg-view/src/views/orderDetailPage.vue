<template>
   <MP :startPlace="startPlace" :endPlace="endPlace" />
</template>

<script>
import MP from "../components/MP.vue";
import orderInfo from "../components/orderInfo.vue";
export default {
  name: "orderDetailPage",
  components: { MP, orderInfo },
  props: ["startPlace", "endPlace"],
  methods: {},
  created() {
    console.log(this.startPlace, this.endPlace);
  },
};
</script>

<style>
</style>