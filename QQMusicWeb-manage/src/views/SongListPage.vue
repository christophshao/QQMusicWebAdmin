<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        
        <el-button type="primary" size="small" @click="deleteAll">
            批量删除
        </el-button>

        <el-button type="primary" size="small" @click="centerDialogVisible = true">
            添加歌单
        </el-button>
        <el-input v-model="select_word" placeholder="请输入关键字..." class="handle-input" size="small"></el-input>
      </div>
    </div>
    <!-- 查询歌单-表格 -->
       <el-table size="mini" border style="width:100%;" height="510px" 
       :data="data" @selection-change="handleSelectionChange">
         <el-table-column label="多选" type="selection" width="50" center></el-table-column>
         <el-table-column label="歌单图片" width="110" align="center">
           <template slot-scope="scope">
             <div class="songList-img">
               <img :src="getUrl(scope.row.pic)" width="100%"/>
             </div>
             <!-- 更新歌单图片 -->
             <el-upload :action="uploadUrl(scope.row.id)" :on-success="handleAvatorSuccess">
               <el-button size="mini">更新图片</el-button>
             </el-upload>
           </template>
           
         </el-table-column>
         <el-table-column label="标题" prop="title" width="120" align="center"></el-table-column>
         
         <el-table-column label="简介" align="center">
           <template slot-scope="scope">
              <p style="height:100px; overflow:hidden">{{scope.row.introduction}}</p>
           </template>
         </el-table-column>
         <el-table-column label="风格" prop="style" width="120" align="center"></el-table-column>
         <el-table-column label="歌曲管理" width="110" align="center">
           <template slot-scope="scope">
             <el-button size="mini" @click="songEdit(scope.row.id)">歌曲管理</el-button>
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

    <!-- 添加歌单-弹窗 -->
    <el-dialog
      title="添加歌单"
      :visible.sync="centerDialogVisible"
      width="450px"
      center>
      <el-form :model="registerForm" ref="registerForm" label-width="80px" :rules="rules">
        <el-form-item prop="title" label="歌单名" size="mini">
            <el-input v-model="registerForm.title" placeholder="歌单名"></el-input>
        </el-form-item>
        
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input v-model="registerForm.introduction" placeholder="简介" type="textarea"></el-input>
        </el-form-item>

        <el-form-item prop="style" label="风格" size="mini">
            <el-input v-model="registerForm.style" placeholder="风格"></el-input>
        </el-form-item>

      </el-form>
      
      <span slot="footer">
        <el-button  @click="addSongList" size="mini">确定</el-button>
        <el-button  @click="centerDialogVisible = false" size="mini">取消</el-button>
      </span>
    </el-dialog>

    <!-- 修改歌单-弹窗 -->
    <el-dialog
      title="修改歌单"
      :visible.sync="editVisible"
      width="450px"
      center>
      <el-form :model="editForm" ref="editForm" label-width="80px" :rules="rules">
        <el-form-item prop="title" label="歌单名" size="mini">
            <el-input v-model="editForm.title" placeholder="歌单名"></el-input>
        </el-form-item>
        
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input v-model="editForm.introduction" placeholder="简介" type="textarea"></el-input>
        </el-form-item>

        <el-form-item prop="style" label="风格" size="mini">
            <el-input v-model="editForm.style" placeholder="风格"></el-input>
        </el-form-item>
      </el-form>
      
      <span slot="footer">
        <el-button  @click="editSave" size="mini">确定</el-button>
        <el-button  @click="editVisible = false" size="mini">取消</el-button>
      </span>
    </el-dialog>
    <!-- 删除歌单-弹窗 -->
    <el-dialog title="删除歌单" :visible.sync="deleteVisible" width="300px" center>
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
    import {getAllSongList,setSongList,updateSongList,deleteSongList} from '../api/index';
    
    export default{
      mixins: [mixin],
        data(){
            return{
                // 默认为false 当单击事件后变为true 就会显示弹窗
                centerDialogVisible: false, //添加弹窗
                editVisible:false,  //修改弹窗
                deleteVisible:false, //删除弹窗

                registerForm:{   //添加框
                    title:'',
                    introduction:'',
                    style:''
                   
                },
                editForm:{   //修改框
                    id:'',
                    title:'',
                    introduction:'',
                    style:''
                   
                },
                tableData:[],  //用于存储查询到的歌单信息，一开始默认为空
                tempData: [],
                select_word: '',
                index: -1,   //选择当前项
                multipleSelection:[],   //确定多选项数量
                pageSize: 4, //一张页面展示多少数据
                currentPage: 1, //当前页

                rules:{
                    title:[
                        {required: true, message:'请输入歌单名', trigger: 'blur'}
                    ],
                    introduction:[
                        {required: true,message:'请输入歌单简介',trigger:'blur'}
                    ],
                    style:[
                        {required: true,message:'请输入歌单风格',trigger:'blur'}
                    ],
                    
                }
            }
            
        },

        // 计算当前搜索结果表中的数据
        computed:{
              data(){
                return this.tableData.slice((this.currentPage - 1)*this.pageSize,this.currentPage * this.pageSize);
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
                if(item.title.includes(this.select_word)){
                  this.tableData.push(item);
                }
              }
            }
          }
        },
        // 创建页面的时候执行
        created(){
          this.getData();
        },
        methods:{
          //查询歌单
          getData(){
            this.tempData = [];
            this.tableData = []; //一开始清空tableData 防止之前有残留数据
            getAllSongList().then((res) =>{
              this.tempData = res;
              this.tableData = res;
              this.currentPage = 1;
            })
          },
          //添加歌单
          addSongList(){
            this.$refs['registerForm'].validate(valid =>{
            if(valid){
                //接收保存往后台传递的参数,
                let params = new URLSearchParams();
                params.append('title',this.registerForm.title);
                params.append('pic','img/songListPic/default.png');
                params.append('introduction',this.registerForm.introduction);
                params.append('style',this.registerForm.style);

              
              setSongList(params)
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
                this.centerDialogVisible = false;
              }
            })
          },

          // 更新歌单头像图片
          uploadUrl(id){
            return `${this.$store.state.HOST}/songList/updateSongListPic?id=${id}`
          },
          // 弹出编辑歌单信息页面
          handleEdit(row){
            this.editVisible = true;
            this.editForm ={
                id: row.id,
                title: row.title,
                introduction: row.introduction,
                style: row.style
            }
          },
          // 编辑保存
          editSave(){
            this.$refs['editForm'].validate(valid =>{
            if(valid){
                //接收保存往后台传递的参数,
                let params = new URLSearchParams();
                params.append('id',this.editForm.id);
                params.append('title',this.editForm.title);
                params.append('introduction',this.editForm.introduction);
                params.append('style',this.editForm.style);
              
                updateSongList(params)
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
              }
            })
          },
          // 删除信息
          deleteRow(){
              deleteSongList(this.index)
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
           // 带参数转向歌曲管理页面
          songEdit(id){
            this.$router.push({path:`/ListSong`,query:{id}});
          },
          // 获取当前页
          handleCurrentChange(val){
            this.currentPage = val;
          }
          
        }
    }
</script>

<style scoped>
    .handle-box{
        margin-bottom: 20px;
    }
    .songList-img{
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
</style>
