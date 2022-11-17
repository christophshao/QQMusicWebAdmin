<template>
  <div class="table">
    <div class="crumbs">
      <i class="el-icon-notebook-2"></i>歌曲管理
    </div>

    <div class="container">
      <div class="handle-box">
        
        <el-button type="primary" size="small" @click="deleteAll">
            批量删除
        </el-button>

        <el-button type="primary" size="small" @click="centerDialogVisible = true">
            添加歌曲
        </el-button>
        <el-input v-model="select_word" placeholder="请输入歌曲名..." class="handle-input" size="small"></el-input>
      </div>
    </div>
    <!-- 查询歌曲-表格 -->
       <el-table size="mini" border style="width:100%" height="510px" ref="multipleTable"
       :data="data" @selection-change="handleSelectionChange">
            <el-table-column label="多选" type="selection" width="50" center></el-table-column>

            <el-table-column label="歌曲图片" width="110" align="center">
              <template slot-scope="scope">
                  <div class="song-img">
                    <img :src="getUrl(scope.row.pic)" width="80%;" height="80%"/>
                  </div>
                  <div class="play" @click="setSongUrl(scope.row.url,scope.row.name)">
                    <div v-if="toggle == scope.row.name">
                      <svg class="icon">
                        <use xlink:href="#icon-zanting"></use>
                      </svg>
                    </div>
                    <div v-if="toggle != scope.row.name">
                      <svg class="icon">
                        <use xlink:href="#icon-bofanganniu"></use>
                      </svg>
                    </div>
                  </div>
              </template>
            </el-table-column>

            <el-table-column label="歌手-歌名" prop="name" width="120" align="center"></el-table-column>
            
            <el-table-column label="专辑" prop="introduction" width="120" align="center"></el-table-column>
            
            <el-table-column label="歌词" align="center">
              <template slot-scope="scope">
                  <ul style="height:140px; overflow:scroll">
                    <li v-for="(item,index) in parseLyric(scope.row.lyric)" :key="index">
                      {{item}}
                    </li>
                  </ul>
              </template>
            </el-table-column>

            <el-table-column label="更新资源" align="center" width="150px">
                <!-- 更新资源 -->
                <template slot-scope="scope">
                  <el-upload :action="uploadUrl(scope.row.id)" :on-success="handleAvatorSuccess">
                    <el-button size="mini">更新封面</el-button>
                  </el-upload>
                  <br>
                  <el-upload :action="uploadSongUrl(scope.row.id)" :on-success="handleSongSuccess">
                    <el-button size="mini">更新歌曲</el-button>
                  </el-upload>
                </template>
            </el-table-column>
            
            <el-table-column label="操作" width="180" align="center">
                <template slot-scope="scope">
                    <el-button type="button" @click="handleEdit(scope.row)" size="small">编辑</el-button>
                    <el-button type="danger" @click="handleDelete(scope.row.id)" size="small">删除</el-button>
                </template>
            </el-table-column>
       </el-table>
       <!-- 分页 -->
       <div class="pageination">
         <el-pagination background layout="total,prev,pager,next" 
         :total="tableData.length" :current-page="currentPage" :page-size="pageSize" 
         @current-change="handleCurrentChange">
         </el-pagination>
       </div>

    <!-- 添加歌曲-弹窗 -->
    <el-dialog title="添加歌曲" :visible.sync="centerDialogVisible" width="450px" center>
      <el-form :model="registerForm" ref="registerForm" label-width="80px" action="" id="tf">
            <div>
                <label>歌名</label>
                <el-input type="text" name="name" v-model="registerForm.name"></el-input>
            </div>
            <div>
                <label>专辑</label>
                <el-input type="text" name="introduction" v-model="registerForm.introduction"></el-input>
            </div>
            <div>
                <label>歌词</label>
                <el-input type="text" name="lyric" v-model="registerForm.lyric"></el-input>
            </div>
            <div>
                <label>歌曲上传</label>
                <el-input type="file" name="file" v-model="registerForm.file"></el-input>
            </div>
      </el-form>
      
      <span slot="footer">
        <el-button  @click="addSong" size="mini">确定</el-button>
        <el-button  @click="centerDialogVisible = false" size="mini">取消</el-button>
      </span>
    </el-dialog>

    <!-- 修改歌手-弹窗 -->
    <el-dialog
      title="修改歌曲"
      :visible.sync="editVisible"
      width="450px"
      center>
      <el-form :model="editForm" ref="editForm" label-width="80px">
        <el-form-item prop="name" label="歌手-歌名" size="mini">
            <el-input v-model="editForm.name" placeholder="歌手-歌名"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="专辑" size="mini">
          <el-input v-model="editForm.introduction" placeholder="专辑"></el-input>
        </el-form-item>
        <el-form-item prop="lyric" label="歌词" size="mini">
          <el-input v-model="editForm.lyric" placeholder="歌词" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      
      <span slot="footer">
        <el-button  @click="editSave" size="mini">确定</el-button>
        <el-button  @click="editVisible = false" size="mini">取消</el-button>
      </span>
    </el-dialog>

    <!-- 删除歌手-弹窗 -->
    <el-dialog title="删除歌曲" :visible.sync="deleteVisible" width="300px" center>
      <div style="text-align:center">删除不可恢复，是否确认删除</div>
      <span slot="footer">
        <el-button  @click="deleteRow" size="mini">确定</el-button>
        <el-button  @click="deleteVisible = false" size="mini">取消</el-button>
      </span>
    </el-dialog>
   
  </div>
</template>

<script>
    import {mixin} from '../mixins/index';
    import {mapGetters} from 'vuex';
    import '@/assets/js/iconfont.js';
    import { deleteSong, selectSongBySingerId, updateSong } from '../api/index';
    

    export default{
      mixins: [mixin],
        data(){
            return{
                // 接收歌手管理页面传来的id
                singerId:'',  //歌手id
                singerName:'', //歌手名

                // 默认为false 当单击事件后变为true 就会显示弹窗
                centerDialogVisible: false, //添加弹窗
                editVisible:false,  //修改弹窗
                deleteVisible:false, //删除弹窗

                // 
                registerForm:{   //添加框
                    name:'',
                    singerName:'',
                    introduction:'',
                    lyric:''
                   
                },
                editForm:{   //修改框
                    id:'',
                    name:'',
                    introduction:'',
                    lyric:''
                   
                },
                tableData:[],  //用于存储查询到的歌手信息，一开始默认为空
                tempData: [],
                select_word: '',
                index: -1,   //选择当前项
                multipleSelection:[],   //确定多选项数量

                pageSize: 3, //一张页面展示多少数据
                currentPage: 1, //当前页
                toggle: false //播放器的显示状态
            }
            
        },
        // 分页--计算当前搜索结果表中的数据
        computed:{
           ...mapGetters([
                'isPlay'

            ]),
          // 计算当前搜索表里的数据
            data(){
              return this.tableData.slice((this.currentPage - 1)*this.pageSize,this.currentPage * this.pageSize);
            }
        },
        // 创建页面的时候执行,用来获取当前页的数据
        
        // 搜索框发生变化的时候，table中的内容同步变化
        watch:{
          select_word: function(){
            if(this.select_word == ''){
              this.tableData = this.tempData;
            }else{
              this.tableData = [];
              for(let item of this.tempData){
                if(item.name.includes(this.select_word)){
                  this.tableData.push(item);
                }
              }
            }
          }
        },
        created(){
            this.singerId = this.$route.query.id;
            this.singerName = this.$route.query.name;
            this.getData();
        },
        destroyed(){
          this.$store.commit('setIsPlay',false);
        },
        methods:{
          //查询歌曲
          getData(){
            this.tempData = [];
            this.tableData = []; //一开始清空tableData 防止之前有残留数据
            selectSongBySingerId(this.singerId).then((res) =>{
              this.tempData = res;
              this.tableData = res;
              this.currentPage = 1;
            })
          },
          //添加歌手
          addSong(){
            let _this = this;
            var form = new FormData(document.getElementById('tf'));
            form.append('singerId',this.singerId);
            form.set('name',this.singerName + '-' + form.get('name'));
            if(!form.get('lyric')){
                form.set('lyric','[00:00:00]暂无歌词');
            }
            var req = new XMLHttpRequest();
            req.onreadystatechange = function(){
              // req.readyState == 4 获取到返回的完整数据
              // req.status == 200 和后台正常交互完成
              if(req.readyState == 4 && req.status == 200){
                  let res = JSON.parse(req.response)
                  if(res.code){
                      _this.getData();
                      _this.registerForm = {};
                      _this.message(res.msg,'success');
                  }else{
                      _this.message('上传失败','error');
                  }
              }

            }
            req.open('post',`${_this.$store.state.HOST}/song/addSong`,false);
            req.send(form);
            _this.centerDialogVisible = false;
          },
          // 弹出编辑歌手信息页面
          handleEdit(row){
            this.editVisible = true;
            this.editForm ={
                id: row.id,
                name: row.name,
                introduction: row.introduction,
                lyric: row.lyric
            }
          },
          // 编辑保存
          editSave(){
            //接收保存往后台传递的参数,
            let params = new URLSearchParams();
            params.append('id',this.editForm.id);
            params.append('name',this.editForm.name);
            params.append('introduction',this.editForm.introduction);
            params.append('lyric',this.editForm.lyric);
          
            updateSong(params)
            .then((res) =>{
              if(res.code == 1){
                this.getData();
                this.message("修改成功！","success");
              }else{
                this.message("修改失败!","error");
              }
            })
            .catch(err =>{
              console.log(err);
            });
            this.editVisible = false;
          },
          // 删除信息
          deleteRow(){
            deleteSong(this.index)
            .then((res) =>{
              if(res){
                this.getData();
                this.message("删除成功！","success");
              }else{
                this.message("删除失败!","error");
              }
            })
            this.deleteVisible = false;
          },
           // 获取当前页
          handleCurrentChange(val){
            this.currentPage = val;
          },
           // 更新歌曲logo图片
          uploadUrl(id){
            return `${this.$store.state.HOST}/song/updateSongPic?id=${id}`
          },
          // 解析歌词
          parseLyric(text){
              let lines = text.split("\n");
              let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g;
              let result = [];
              for(let item of lines){
                let value = item.replace(pattern,'');
                result.push(value);
              }
              return result;
          },
          // 修改上传歌曲成功之后要做的工作
          handleSongSuccess(res){
              let _this = this;
              if(res.code == 1){
                _this.getData();
                  _this.$message({
                      message:'上传成功！',
                      type: 'success'
                  });
              }else{
                  _this.$message({
                      message:'上传失败！',
                      type:'error'
                  });

              }
          },
          // 跟新歌曲url
          uploadSongUrl(id){
            return `${this.$store.state.HOST}/song/updateSongUrl?id=${id}`
          },
          // 切换播放的歌曲
           setSongUrl(url,name){
            //如果此时toggle不等于传入的歌名，则播放此歌曲，否则暂停此歌曲
            if (this.toggle != name){
              this.toggle = name;
              this.$store.commit('setUrl',this.$store.state.HOST +url);
              this.$store.commit('setIsPlay',true);
              // console.log('播放')
            } else{//暂停
              this.toggle = false;
              this.$store.commit('setIsPlay',false);
              // console.log('暂停')
            }
          }
        }
    }
</script>

<style scoped>
    .table{
        width: 100%;
    }
    .handle-box{
        margin-bottom: 20px;
    }
    .song-img{
      border-radius: 5px;
      width: 100%;
      height: 80px;
      margin-bottom: 5px;
      overflow:hidden;
    }
    .handle-input{
      float: right;
      width: 300px;
      display: inline-block;
    }
    .pageination{
        margin-top: 10px;
        display: flex;
        justify-content: center;
    }
    .play{
      position: absolute;
      display: flex;
      z-index: 100;
      width: 80px;
      height: 80px;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      top: 25px;
      left: 15px;
    }
    .icon{
      width: 2em;
      height: 2em;
      color: #999;
      fill: currentColor;
      overflow: hidden;
    }


</style>
