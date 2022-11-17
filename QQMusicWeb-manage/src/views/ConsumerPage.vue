<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">

        <el-button type="primary" size="small" @click="deleteAll">
            批量删除
        </el-button>

        <el-button type="primary" size="small" @click="centerDialogVisible = true">
            添加用户
        </el-button>
        <el-input v-model="select_word" placeholder="请输入用户名..." class="handle-input" size="small"></el-input>
      </div>
    </div>
    <!-- 查询用户-表格 -->
       <el-table size="mini" border style="width:100%;" height="510px" 
       :data="data" @selection-change="handleSelectionChange">
         <el-table-column label="多选" type="selection" width="50" center></el-table-column>
         <el-table-column label="用户图片" width="110" align="center">
           <template slot-scope="scope">
             <div class="consumer-img">
               <img :src="getUrl(scope.row.avator)" width="100%"/>
             </div>
             <!-- 更新用户图片 -->
             <el-upload :action="uploadUrl(scope.row.id)" :on-success="handleAvatorSuccess">
               <el-button size="mini">更新图片</el-button>
             </el-upload>
           </template>
         </el-table-column>
         <el-table-column label="用户名" prop="username" width="120" align="center"></el-table-column>
         <el-table-column label="性别" width="50" align="center">
            <template slot-scope="scope">
             {{getSex(scope.row.sex)}}
           </template> 
         </el-table-column>
         <el-table-column label="电话号码" prop="phoneNum" width="120" align="center"></el-table-column>
         <el-table-column label="邮箱" prop="email" width="200" align="center"></el-table-column>
         <el-table-column label="生日" width="120" align="center">
           <template slot-scope="scope">
             {{attachBirth(scope.row.birth)}}
           </template>
         </el-table-column>
        <el-table-column label="签名" prop="introduction"  align="center"></el-table-column>
         <el-table-column label="地区" prop="location" width="120" align="center"></el-table-column>
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

    <!-- 添加用户-弹窗 -->
    <el-dialog
      title="添加用户"
      :visible.sync="centerDialogVisible"
      width="450px"
      center>
      <el-form :model="registerForm" ref="registerForm" label-width="80px" :rules="rules">
        <el-form-item prop="username" label="用户名" size="mini">
            <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码" size="mini">
            <el-input v-model="registerForm.password" placeholder="密码" type="password"></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="用户性别" size="mini">
              <input type="radio" name="sex" value="1" v-model="registerForm.sex"/>&nbsp;男&nbsp;&nbsp;
              <input type="radio" name="sex" value="0" v-model="registerForm.sex"/>&nbsp;女
        </el-form-item>
        <el-form-item prop="phoneNum" label="电话号码" size="mini">
            <el-input v-model="registerForm.phoneNum" placeholder="电话号码"></el-input>
        </el-form-item>
        <el-form-item prop="email" label="邮箱" size="mini">
            <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker v-model="registerForm.birth" placeholder="选择日期" type="date" style="width:100%"></el-date-picker>
        </el-form-item>
        <el-form-item prop="introduction" label="签名" size="mini">
          <el-input v-model="registerForm.introduction" placeholder="签名"></el-input>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-input v-model="registerForm.location" placeholder="所在地区"></el-input>
        </el-form-item>
        
      </el-form>
      
      <span slot="footer">
        <el-button  @click="addConsumer" size="mini">确定</el-button>
        <el-button  @click="centerDialogVisible = false" size="mini">取消</el-button>
      </span>
    </el-dialog>

    <!-- 修改用户-弹窗 -->
    <el-dialog
      title="修改用户"
      :visible.sync="editVisible"
      width="450px"
      center>
      <el-form :model="editForm" ref="editForm" label-width="80px" :rules="rules">
        <el-form-item prop="username" label="用户名" size="mini">
            <el-input v-model="editForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码" size="mini">
            <el-input v-model="editForm.password" placeholder="密码" type="password"></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="用户性别" size="mini">
              <input type="radio" name="sex" value="1" v-model="editForm.sex"/>&nbsp;男&nbsp;&nbsp;
              <input type="radio" name="sex" value="0" v-model="editForm.sex"/>&nbsp;女
        </el-form-item>
        <el-form-item prop="phoneNum" label="电话号码" size="mini">
            <el-input v-model="editForm.phoneNum" placeholder="电话号码"></el-input>
        </el-form-item>
        <el-form-item prop="email" label="邮箱" size="mini">
            <el-input v-model="editForm.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker v-model="editForm.birth" placeholder="选择日期" type="date" style="width:100%"></el-date-picker>
        </el-form-item>
        <el-form-item prop="introduction" label="签名" size="mini">
          <el-input v-model="editForm.introduction" placeholder="签名"></el-input>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-input v-model="editForm.location" placeholder="所在地区"></el-input>
        </el-form-item>
      </el-form>
      
      <span slot="footer">
        <el-button  @click="editSave" size="mini">确定</el-button>
        <el-button  @click="editVisible = false" size="mini">取消</el-button>
      </span>
    </el-dialog>
    <!-- 删除用户-弹窗 -->
    <el-dialog title="删除用户" :visible.sync="deleteVisible" width="400px" center>
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
    import {getAllConsumer,setConsumer,updateConsumer,deleteConsumer} from '../api/index';
    
    export default{
      mixins: [mixin],
        data(){
            return{
                // 默认为false 当单击事件后变为true 就会显示弹窗
                centerDialogVisible: false, //添加弹窗
                editVisible:false,  //修改弹窗
                deleteVisible:false, //删除弹窗

                registerForm:{   //添加框
                    username:'',
                    password:'',
                    sex:'1',
                    phoneNum:'',
                    email:'',
                    birth:'',
                    introduction:'',
                    location:''
                },
                editForm:{   //修改框
                    id:'',
                    username:'',
                    password:'',
                    sex:'',
                    phoneNum:'',
                    email:'',
                    birth:'',
                    introduction:'',
                    location:''
                   
                },
                tableData:[],  //用于存储查询到的用户信息，一开始默认为空
                tempData: [],
                select_word: '',
                index: -1,   //选择当前项
                multipleSelection:[],   //确定多选项数量

                pageSize: 4, //一张页面展示多少数据
                currentPage: 1, //当前页

                rules:{
                    username:[
                        {required: true, message:'请输入用户名', trigger: 'blur'}
                    ],
                    password:[
                        {required: true,message:'请输入密码',trigger:'blur'}
                    ],
                    phoneNum:[
                        {required: true,message:'请输入手机号码',trigger:'blur'}
                    ],
                    email:[
                        {required: true,message:'请输入电子邮箱',trigger:'blur'},
                        {type:'email',message:'请输入正确的电子邮箱地址',trigger:['blur','change']}
                    ],
                    introduction:[
                        {required: true,message:'请输入签名',trigger:'blur'}
                    ],
                    location:[
                        {required: true,message: '请输入所在地区',trigger: 'blur'}
                    ]
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
                if(item.username.includes(this.select_word)){
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
          //查询用户
          getData(){
            this.tempData = [];
            this.tableData = []; //一开始清空tableData 防止之前有残留数据
            getAllConsumer().then((res) =>{
              this.tempData = res;
              this.tableData = res;
              this.currentPage = 1;
            })
          },
          //添加用户
          addConsumer(){
            
            this.$refs['registerForm'].validate(valid =>{
                if(valid){
                    let d = this.registerForm.birth;
                    let datetime = d.getFullYear() + '-' + (d.getMonth()+1) + '-' + d.getDate();
                    //接收保存往后台传递的参数,
                    let params = new URLSearchParams();
                    params.append('username',this.registerForm.username);
                    params.append('password',this.registerForm.password);
                    params.append('sex',this.registerForm.sex);
                    params.append('phoneNum',this.registerForm.phoneNum);
                    params.append('email',this.registerForm.email);
                    params.append('pic','img/consumerPic/default.png');
                    params.append('birth',datetime);
                    params.append('introduction',this.registerForm.introduction);
                    params.append('location',this.registerForm.location);
                    params.append('avator','avatorImages/default.png');

                
                    setConsumer(params)
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

          // 更新用户头像图片
          uploadUrl(id){
            return `${this.$store.state.HOST}/consumer/updateConsumerPic?id=${id}`
          },
          // 弹出编辑用户信息页面
          handleEdit(row){
            this.editVisible = true;
            this.editForm ={
                id: row.id,
                username: row.username,
                password: row.password,
                sex: row.sex,
                phoneNum: row.phoneNum,
                email: row.email,
                birth: row.birth,
                introduction: row.introduction,
                location: row.location,
                
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
                params.append('username',this.editForm.username);
                params.append('password',this.editForm.password);
                params.append('sex',this.editForm.sex);
                params.append('phoneNum',this.editForm.phoneNum);
                params.append('email',this.editForm.email);
                params.append('birth',datetime);
                params.append('introduction',this.editForm.introduction);
                params.append('location',this.editForm.location);


                updateConsumer(params)
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
              deleteConsumer(this.index)
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
          }
          
        }
    }
</script>

<style scoped>
    .handle-box{
        margin-bottom: 20px;
    }
    .consumer-img{
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
