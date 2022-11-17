<template>
  <div class="login-wrap">
    <div class="admin-title">QQMusic-后台管理</div>

    <!-- 登录框 -->
    <div class="ad-login">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm">
        <el-form-item prop="username">
          <el-input v-model="ruleForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="ruleForm.password" placeholder="密码"></el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm">Sign In</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import {mixin} from "../mixins/index"
import {getLoginStatus} from "../api/index"

export default{
    mixins:[mixin],
    data: function(){
      return{
        ruleForm:{
          username: "admin",
          password: "123"
        },
        rules:{
          username:[
            {required:true,message:"请输入用户名",trigger:"blur"}
          ],
          password:[
            {required:true,message:"请输入密码",trigger:"blur"}
          ]
        }
      };
    },
   
    methods: {
        submitForm(){
          let params = new URLSearchParams();
          params.append("name",this.ruleForm.username);
          params.append("password",this.ruleForm.password);
          getLoginStatus(params)
          .then((res) =>{
            if(res.code == 1){
              localStorage.setItem('userName',this.ruleForm.username); //h5的功能能把用户名保存起来
              // this.notify("登录成功！","success");
                this.message("登录成功！","success");  //$message组件弹窗
                this.$router.push("/admin");
            }else{
              // this.notify("登录失败！","error");
               this.message("登录失败！","error");
            }
        });
      }
    }
}
</script>

<style scoped>
  .login-wrap {
    margin: 0;
    padding: 0;
    position: relative;
    width: 100%;
    height: 100vh;
    background: url("../assets/img/background.png");
    background-size: 100% 100%;
  }
  .admin-title {
    position: absolute;
    top: 40%;
    width: 100%;
    margin-top: -180px;
    text-align: center;
    font-size: 30px;
    font-weight: bold;
    color: #ffffff;
  }
  .ad-login {
    position: absolute;
    left: 45%;
    top: 50%;
    width: 450px;
    height: 230px;
    margin-left: -190px;
    margin-top: -150px;
    padding: 40px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.6);
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  }
  .login-btn{
    text-align: center;
  }
  .login-btn button{
    width: 100%;  
  }
</style>