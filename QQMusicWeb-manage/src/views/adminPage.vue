<template>
  <div>
    <el-row :gutter="20" class="mgb20">
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{ consumerCount }}</div>
              <div>用户总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{ singerCount }}</div>
              <div>歌手总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{ songCount }}</div>
              <div>歌曲总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{songListCount}}</div>
              <div>歌单数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="mgb20" style="margin-top:20px">
      <el-col :span="12">
        <el-card>
          <h3 style="margin-bottom: 10px; margin-top: 10px">用户性别比例</h3>
          <div style="background-color: #fff">
            <ve-pie :data="consumerSex" :theme="option"></ve-pie>
            <div ref="consumerSex" :data="consumerSex" :theme="option"></div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12" >
        <el-card>
          <h3 style="margin-bottom: 10px; margin-top: 10px">用户地区分布</h3>
          <div style="background-color: #fff">
            <ve-histogram :data="consumerCountry"></ve-histogram>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="mgb20" style="margin-top:20px">
      <el-col :span="12">
        <el-card>
          <h3 style="margin-bottom: 10px; margin-top: 10px">歌手性别比例</h3>
          <div style="background-color: #fff">
            <ve-pie ref="singerSex" :data="singerSex" :theme="option2"></ve-pie>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12" >
        <el-card>
          <h3 style="margin-bottom: 10px; margin-top: 10px">歌手地区分布</h3>
          <div style="background-color: #fff">
            <ve-histogram :data="country" :theme="option1"></ve-histogram>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="mgb20" style="margin-top:20px">
      <el-col :span="24">
        <el-card>
          <h3 style="margin-bottom: 10px; margin-top: 10px">歌单风格统计</h3>
          <div style="background-color: #fff">
            <ve-line :data="songStyle" :theme="option3"></ve-line>
          </div>
        </el-card>
      </el-col>
      
    </el-row>

    
  </div>
</template>
<script>
import { getAllConsumer, selectAllSong, getAllSinger,getAllSongList} from "../api";
export default {
  data() {
    return {
      consumerCount: 0, //用户总数
      singerCount: 0, //歌手总数
      songCount: 0, //歌曲总数
      songListCount: 0, //歌单总数
      // visitCount: 99, //访问总数

      //按性别分用户数量
      consumer: [],
      consumerSex: {
        columns: ["性别", "总数"],
        rows: [
          { 性别: "男", 总数: 0 },
          { 性别: "女", 总数: 0 },
        ],
      },
      // 按性别分歌手数量
      singerSex: {
        columns: ["性别", "总数"],
        rows: [
          { 性别: "男", 总数: 0 },
          { 性别: "女", 总数: 0 },
          { 性别: "组合", 总数: 0 },
          { 性别: "未知", 总数: 0 },
        ],
      },
      // 歌手地区分布
      country: {
        columns: ["地区", "总数"],
        rows: [
          { 地区: "中国", 总数: 0 },
          { 地区: "美国", 总数: 0 },
          { 地区: "俄罗斯", 总数: 0 },
          { 地区: "英国", 总数: 0 },
          { 地区: "日本", 总数: 0 },
          { 地区: "德国", 总数: 0 },
          { 地区: "法国", 总数: 0 },
          { 地区: "韩国", 总数: 0 },
          { 地区: "其他", 总数: 0 },
        ],
      },
      // 用户地区分布
      consumerCountry: {
        columns: ["地区", "总数"],
        rows: [
          { 地区: "中国", 总数: 0 },
          { 地区: "美国", 总数: 0 },
          { 地区: "俄罗斯", 总数: 0 },
          { 地区: "英国", 总数: 0 },
          { 地区: "日本", 总数: 0 },
          { 地区: "德国", 总数: 0 },
          { 地区: "法国", 总数: 0 },
          { 地区: "韩国", 总数: 0 },
          { 地区: "其他", 总数: 0 },
        ],
      },
      // 用户地区分布
      songStyle: {
        columns: ["风格", "总数"],
        rows: [
          { 风格: "华语", 总数: 0 },
          { 风格: "流行", 总数: 0 },
          { 风格: "摇滚", 总数: 0 },
          { 风格: "民谣", 总数: 0 },
          { 风格: "轻快", 总数: 0 },
          { 风格: "励志", 总数: 0 },
          { 风格: "古风", 总数: 0 },
        ],
      },

      option: {
        color: ["#87cefa", "#ffc0cb"],
      },
      option1:{
        color:["#5c7bd9"]
      },
      option2:{
        color:["#9fe080",'#7ed3f4','#fdd55d','#fb8451']
      },
      option3:{
        color:['#64CDFF']
      }
      
      
    };
  },
  created() {},
  mounted() {
    this.getConsumer();
    this.getSong();
    this.getSinger();
    this.getSongList();
  },

  methods: {
      //用户总数
      getConsumer() {
        getAllConsumer().then((res) => {
          this.consumer = res;
          this.consumerCount = res.length;
          this.consumerSex.rows[0]["总数"] = this.setSex(1, this.consumer);
          this.consumerSex.rows[1]["总数"] = this.setSex(0, this.consumer);
          // 获取各个地区用户数量
          for (let item of res) {
            this.getByconsumerCountry(item.location);
          }

        });
      },
      //根据性别获取用户数
      setSex(sex, consumer) {
        let count = 0;
        for (let item of consumer) {
          if (sex == item.sex) {
            count++;
          }
        }
        return count;
      },

      // 获取歌手总数
      getSinger() {
        getAllSinger().then((res) => {
          this.singer = res;
          this.singerCount = res.length;
          this.singerSex.rows[0]["总数"] = this.setSex(1, this.singer);
          this.singerSex.rows[1]["总数"] = this.setSex(0, this.singer);
          this.singerSex.rows[2]["总数"] = this.setSex(2, this.singer);
          this.singerSex.rows[3]["总数"] = this.setSex(3, this.singer);
          for (let item of res) {
            this.getByCountry(item.location);
          }
        });
      },
      //根据歌手性别获取歌手数量
      setSingerSex(sex, singer) {
        let count = 0;
        for (let item of singer) {
          if (sex == item.sex) {
            count++;
          }
        }
        return count;
      },

      // 获取歌曲总数
      getSong() {
        selectAllSong().then((res) => {
          this.songCount = res.length;
        });
      },
      // 获取歌单不同风格总数
      getSongList() {
        getAllSongList().then((res) => {
          this.songListCount = res.length;
          for(let item of res){
              this.getByStyle(item.style);
          }
        });
      },
      getByStyle(style){
        for(let item of this.songStyle.rows){
            if(style.includes(item['风格'])){
              item['总数'] ++;
            }
        }
      },
      // 根据地区获取歌手数量
      getByCountry(location){
          for(let item of this.country.rows){
            if(location.includes(item['地区'])){
              item['总数']++;
            }
          }
      },
    // 根据地区获取用户数量
        getByconsumerCountry(location){
            for(let item of this.consumerCountry.rows){
              if(location.includes(item['地区'])){
                item['总数']++;
              }
            }
        }
  },
};
</script>


<style>
.grid-content {
  display: flex;
  align-items: center;
  height: 50px;
  background: #fff;
}
.grid-cont-center {
  flex: 1;
  text-align: center;
  font-size: 14px;
  color: darkgray;
}
.grid-num {
  font-size: 30px;
  font-weight: bold;
}
</style>

