<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        
        <el-button type="primary" size="small" @click="deleteAll">
            批量删除
        </el-button>

        <el-button type="primary" size="small" @click="centerDialogVisible = true">
            添加歌手
        </el-button>
        <el-input v-model="select_word" placeholder="请输入歌手名..." class="handle-input" size="small"></el-input>
      </div>
    </div>
    <!-- 查询歌手-表格 -->
       <el-table size="mini" border style="width:100%;" height="510px" 
       :data="data" @selection-change="handleSelectionChange">
         <el-table-column label="多选" type="selection" width="50" center></el-table-column>
         <el-table-column label="歌手图片" width="110" align="center">
           <template slot-scope="scope">
             <div class="singer-img">
               <img :src="getUrl(scope.row.pic)" width="100%"/>
             </div>
             <!-- 更新歌手图片 -->
             <el-upload :action="uploadUrl(scope.row.id)" :on-success="handleAvatorSuccess">
               <el-button size="mini">更新图片</el-button>
             </el-upload>
           </template>
           
         </el-table-column>
         <el-table-column label="歌手" prop="name" width="120" align="center"></el-table-column>
         
         <el-table-column label="性别" width="50" align="center">
            <template slot-scope="scope">
             {{getSex(scope.row.sex)}}
           </template> 
         </el-table-column>
         <el-table-column label="生日" width="120" align="center">
           <template slot-scope="scope">
             {{attachBirth(scope.row.birth)}}
           </template>
           
         </el-table-column>
         <el-table-column label="地区" prop="location" width="120" align="center"></el-table-column>
         <el-table-column label="简介" align="center">
           <template slot-scope="scope">
              <p style="height:100px; overflow:hidden">{{scope.row.introduction}}</p>
           </template>
         </el-table-column>
         <el-table-column label="歌曲管理" width="110" align="center">
           <template slot-scope="scope">
             <el-button size="mini" @click="songEdit(scope.row.id,scope.row.name)">歌曲管理</el-button>
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

    <!-- 添加歌手-弹窗 -->
    <el-dialog
      title="添加歌手"
      :visible.sync="centerDialogVisible"
      width="450px"
      center>
      <el-form :model="registerForm" ref="registerForm" label-width="80px" :rules="rules">
        <el-form-item prop="name" label="歌手姓名" size="mini">
            <el-input v-model="registerForm.name" placeholder="歌手名"></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="歌手性别" size="mini">
            <el-radio-group v-model="registerForm.sex">
              <el-radio label="0">女</el-radio>
              <el-radio label="1">男</el-radio>
              <el-radio label="2">组合</el-radio>
              <el-radio label="3">未知</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker v-model="registerForm.birth" placeholder="选择日期" type="date" style="width:100%"></el-date-picker>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-input v-model="registerForm.location" placeholder="所在地区"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input v-model="registerForm.introduction" placeholder="简介" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      
      <span slot="footer">
        <el-button  @click="addSinger" size="mini">确定</el-button>
        <el-button  @click="centerDialogVisible = false" size="mini">取消</el-button>
      </span>
    </el-dialog>

    <!-- 修改歌手-弹窗 -->
    <el-dialog
      title="修改歌手"
      :visible.sync="editVisible"
      width="450px"
      center>
      <el-form :model="editForm" ref="editForm" label-width="80px" :rules="rules">
        <el-form-item prop="name" label="歌手姓名" size="mini">
            <el-input v-model="editForm.name" placeholder="歌手名"></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="歌手性别" size="mini">
            <el-radio-group v-model="editForm.sex">
              <el-radio :label="0">女</el-radio>
              <el-radio :label="1">男</el-radio>
              <el-radio :label="2">组合</el-radio>
              <el-radio :label="3">未知</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker v-model="editForm.birth" placeholder="选择日期" type="date" style="width:100%"></el-date-picker>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-input v-model="editForm.location" placeholder="所在地区"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input v-model="editForm.introduction" placeholder="简介" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      
      <span slot="footer">
        <el-button  @click="editSave" size="mini">确定</el-button>
        <el-button  @click="editVisible = false" size="mini">取消</el-button>
      </span>
    </el-dialog>
    <!-- 删除歌手-弹窗 -->
    <el-dialog title="删除歌手" :visible.sync="deleteVisible" width="300px" center>
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
    import {deleteSinger, setSinger} from '../api/index';
    import {getAllSinger} from '../api/index';
    import {updateSinger} from '../api/index'
    
    export default{
      mixins: [mixin],
        data(){
            return{
                // 默认为false 当单击事件后变为true 就会显示弹窗
                centerDialogVisible: false, //添加弹窗
                editVisible:false,  //修改弹窗
                deleteVisible:false, //删除弹窗

                registerForm:{   //添加框
                    name:'',
                    sex:'1',
                    birth:'',
                    location:'',
                    introduction:''
                   
                },
                editForm:{   //修改框
                    id:'',
                    name:'',
                    sex:'',
                    birth:'',
                    location:'',
                    introduction:''
                   
                },
                tableData:[],  //用于存储查询到的歌手信息，一开始默认为空
                tempData: [],
                select_word: '',
                index: -1,   //选择当前项
                multipleSelection:[],   //确定多选项数量

                pageSize: 4, //一张页面展示多少数据
                currentPage: 1, //当前页
                rules:{
                    name:[
                        {required: true, message:'请输入用户名', trigger: 'blur'}
                    ],
                    birth:[
                        {required: true,message:'请输入手机号码',trigger:'blur'}
                    ],
                    location:[
                        {required: true,message: '请输入所在地区',trigger: 'blur'}
                    ],
                    introduction:[
                        {required: true,message:'请输入签名',trigger:'blur'}
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
                if(item.name.includes(this.select_word)){
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
          //查询歌手
          getData(){
            this.tempData = [];
            this.tableData = []; //一开始清空tableData 防止之前有残留数据
            getAllSinger().then((res) =>{
              this.tempData = res;
              this.tableData = res;
              this.currentPage = 1;
            })
          },
          //添加歌手
          addSinger(){
            this.$refs['registerForm'].validate(valid =>{
            if(valid){
                let d = this.registerForm.birth;
                let datetime = d.getFullYear() + '-' + (d.getMonth()+1) + '-' + d.getDate();
                //接收保存往后台传递的参数,
                let params = new URLSearchParams();
                params.append('name',this.registerForm.name);
                params.append('sex',this.registerForm.sex);
                params.append('pic','img/singerPic/default.png');
                params.append('birth',datetime);
                params.append('location',this.registerForm.location);
                params.append('introduction',this.registerForm.introduction);

              
                setSinger(params)
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

          // 更新歌手头像图片
          uploadUrl(id){
            return `${this.$store.state.HOST}/singer/updateSingerPic?id=${id}`
          },
          // 弹出编辑歌手信息页面
          handleEdit(row){
            this.editVisible = true;
            this.editForm ={
                id: row.id,
                name: row.name,
                sex: row.sex,
                birth: row.birth,
                location: row.location,
                introduction: row.introduction
            }
          },
          // 编辑保存
          editSave(){
            this.$refs['editForm'].validate(valid =>{
            if(valid){
                let d = new Date(this.editForm.birth);
                let datetime = d.getFullYear() + '-' + (d.getMonth()+1) + '-' + d.getDate();
                //接收保存往后台传递的参数,
                let params = new URLSearchParams();
                params.append('id',this.editForm.id);
                params.append('name',this.editForm.name);
                params.append('sex',this.editForm.sex);
                params.append('birth',datetime);
                params.append('location',this.editForm.location);
                params.append('introduction',this.editForm.introduction);
              
                updateSinger(params)
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
              deleteSinger(this.index)
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
          songEdit(id,name){
            this.$router.push({path:`/SongPage`,query:{id,name}});
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
    .singer-img{
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
