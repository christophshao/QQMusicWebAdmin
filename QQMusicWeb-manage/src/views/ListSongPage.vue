<template>
  <div class="table">
    <div class="crumbs">
      <i class="el-icon-notebook-2"></i>歌单歌曲信息管理
    </div>

    <div class="container">
      <div class="handle-box">
        
        <el-button type="primary" size="small" @click="deleteAll">
            批量删除
        </el-button>

        <el-button type="primary" size="small" @click="centerDialogVisible = true">
            添加歌曲
        </el-button>
        <el-input v-model="select_word" placeholder="请输入歌曲名或歌手名..." class="handle-input" size="small"></el-input>
      </div>
    </div>
    <!-- 查询歌曲-表格 -->
       <el-table size="mini" border style="width:100%" height="510px" ref="multipleTable"
       :data="tableData" @selection-change="handleSelectionChange">
            <el-table-column label="多选" type="selection" width="50" center></el-table-column>

            <el-table-column label="歌手-歌名" prop="name" align="center"></el-table-column>
            
            <el-table-column label="操作" width="180" align="center">
                <template slot-scope="scope">
                    <el-button type="danger" @click="handleDelete(scope.row.id)" size="small">删除</el-button>
                </template>
            </el-table-column>
       </el-table>

    <!-- 添加歌曲-弹窗 -->
    <el-dialog title="添加歌曲" :visible.sync="centerDialogVisible" width="450px" center>
      <el-form :model="registerForm" ref="registerForm" label-width="80px" action="" id="tf">
          <el-form-item prop="singerName" label="歌手姓名" size="mini">
            <el-input v-model="registerForm.singerName" placeholder="歌手姓名"></el-input>
          </el-form-item>
          <el-form-item prop="songName" label="歌曲名" size="mini">
            <el-input v-model="registerForm.songName" placeholder="歌曲姓名"></el-input>
          </el-form-item>
      </el-form>
      
      <span slot="footer">
        <el-button  @click="getSongId" size="mini">确定</el-button>
        <el-button  @click="centerDialogVisible = false" size="mini">取消</el-button>
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
    import { listSongDetail,songOfSongId,songOfSongName,listSongAdd,deleteListSong} from '../api/index';
    

    export default{
      mixins: [mixin],
        data(){
            return{
                // 接收歌手管理页面传来的id
                // 默认为false 当单击事件后变为true 就会显示弹窗
                centerDialogVisible: false, //添加弹窗
                deleteVisible:false, //删除弹窗
                // 
                registerForm:{   //添加框
                    singerName:'',
                    songName:''
                   
                },
                tableData:[],  //用于存储查询到的歌手信息，一开始默认为空
                tempData: [],
                select_word: '',
                index: -1,   //选择当前项
                multipleSelection:[],   //确定多选项数量
                songListId: '' //歌单id
            }
            
        }, 
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

        // 创建页面的时候执行,用来获取当前页的数据
        created(){
            this.songListId = this.$route.query.id;
            this.getData();
        },
        methods:{
          //查询歌曲
          getData(){
            this.tempData = [];
            this.tableData = []; //一开始清空tableData 防止之前有残留数据
            listSongDetail(this.songListId).then(res => {
              for(let item of res){
                this.getSong(item.songId);
              }
            })
          },

          // 根据歌曲id查询歌曲对象，放到temData 和 tableData里面
          getSong(id){
            songOfSongId(id)
            .then(res =>{
              this.tempData.push(res);
              this.tableData.push(res);
            })
            .catch(err => {
                console.log(err);
            });
          },
          
          //添加歌曲前的准备，获取到歌曲id
          getSongId(){
            let _this = this;
            var songOfName = _this.registerForm.singerName + "-" + _this.registerForm.songName;
            songOfSongName(songOfName).then(
              res => {
                _this.addSong(res[0].id);
              }
            )
          },

          //添加歌曲
          addSong(songId){
              let _this = this;
              //接收保存往后台传递的参数,
              let params = new URLSearchParams();
              params.append('songId',songId);
              params.append('songListId',this.songListId);

              listSongAdd(params)
              .then((res) =>{
                if(res.code == 1){
                  this.getData();
                  this.message("添加成功！","success");
                }else{
                  this.message("添加失败!","error");
                }
              })
              .catch(err =>{
                console.log(err);
              });
              _this.centerDialogVisible = false;
          },
         
          // 删除一条歌曲
        deleteRow(){
            deleteListSong(this.index,this.songListId)
            .then(res => {
                if(res){
                    this.getData();
                    this.message("删除成功","success");
                }else{
                    this.message("删除失败","error");
                }
            })
            .catch(err => {
                console.log(err);
            });
            this.deleteVisible = false;
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
