<template>
    <div class="song-audio">
        <audio id="player"
        :src="url"
        controls = "controls"
        preload = "true"
        @canplay="startPlay"
        @ended="ended"
        ></audio>
    </div>
</template>

<script>
    import {mapGetters} from 'vuex';

    export default{
        name:'song-audio',
        
        computed:{
            ...mapGetters([
                'id',
                'url',
                'isPlay'

            ])
        },
        watch:{
            // 监听播放还是暂停
            isPlay: function(){
                this.togglePlay();
            }
        },
        methods:{
            //获取链接后准备播放
            startPlay(){
                let player = document.querySelector('#player');
                //开始播放
                player.play();
            },
            //播放完成之后触发
            ended(){
                //
                this.isPlay = false;
            },

            // 开始，暂停的方法
            togglePlay(){
                let player = document.querySelector('#player');
                this.$nextTick( ()=>{
                    if(this.isPlay){
                        player.play();
                    }else{
                        player.pause();
                    }
            })
            }
        }
    }
</script>

<style scoped>
    .song-audio{
        display: none;
    }
</style>
