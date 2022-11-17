<template>
  <div id="app">
    <SongAudio></SongAudio>
    <router-view></router-view>
  </div>
</template>

<script>
  import SongAudio from '@/components/SongAudio'
  export default {
    name: "App",
    components:{
      SongAudio
    }
  };
</script>

<style>
    *{
      margin: 0;
      padding: 0;
    }
</style>
