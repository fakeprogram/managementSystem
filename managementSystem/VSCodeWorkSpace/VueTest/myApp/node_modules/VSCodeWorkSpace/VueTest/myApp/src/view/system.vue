<script setup>
import topBox from "@/components/topBox.vue"
</script>

<template>
    <topBox/>
    <router-view :key="$route.fullPath"></router-view>
</template>

<style scoped></style>