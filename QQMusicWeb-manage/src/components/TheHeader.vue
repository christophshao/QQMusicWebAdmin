<template>
    <div class="header">
        <div class="collapse-btn">
            <!-- 折叠按钮图标-->
            <i class="el-icon-menu" @click="collapseChange"></i>
        </div>
        <div class="logo">
            QQMusic-后台管理
        </div>

        <!-- 右侧图标 -->
        <div class="header-right">
            <!-- 头像 -->
            <div class="user-avator">
                <img src="../assets/img/user.png"/>
            </div>
            <el-dropdown class="user-name" trigger="click" @command="hadleCommand">
                <span class="el-dropdown-link">
                    欢迎您，{{userName}}
                    <i class="el-icon-caret-bottom"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="logout">
                        退出登录
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>

             <!--全屏按钮图标-->
            <div class="btn-fullscreen" @click="handleFullScreen">
                <el-tooltip v-bind:content="fullscreen?`退出全屏`:`进入全屏`" placement="bottom">
                    <i class="el-icon-rank"></i>
                </el-tooltip>
            </div>
        </div>
        

    </div>
</template>
<script>
import bus from "../assets/js/bus"
export default{
    data(){
        return{
            collapse: false,
            fullscreen: false
        }
    },
     // 接收返回来的保存的username
    computed:{
      userName(){
        return localStorage.getItem('userName');
      }
    },
    // 左边栏的收缩
    methods:{
        collapseChange(){
            // 让collapse的属性值 true 和 false转换
            this.collapse = !this.collapse;
            // 提交collapse的属性值
            bus.$emit('collapse',this.collapse);
        },
        // 全屏事件
        handleFullScreen(){
        if(this.fullscreen){
                document.exitFullscreen();
        }else{
            let element = document.documentElement;
                element.requestFullscreen();
            
        }
        this.fullscreen  = !this.fullscreen;
        },
        hadleCommand(command){
            if(command == "logout"){
                localStorage.removeItem('userName');
                this.$router.push("/");
            }
        }
    }
}
</script>

<style scoped>
    .header{
        position: relative;
        background: #333;
        color: #fff;
        width: 100%;
        height: 70px;

    }
    .collapse-btn{
        float: left;
        padding: 0 21px;
        cursor: pointer;
        line-height: 70px;
    }
    .header .logo{
        float: left;
        font-size: 18px;
        line-height: 70px;
    }
    .header-right{
        float: right;
        padding-right: 70px;
        height: 70px;
        align-items: center;
        display: flex;

    }
    .btn-fullscreen{
        transform: rotate(45deg);
        font-size: 24px;
        margin-left: 30px;
    }

    .user-avator{
        margin-left: 20px;
        border: 2px solid #fff;
        border-radius: 50%;
        
    }
    .user-avator img{
        display: block;
        width: 40px;
        height: 40px;
        border-radius: 50%;
        
    }
    .user-name{
        margin-left: 10px;
    }
    .el-dropdown-link{
        color: #ffffff;
        cursor: pointer;
    }


</style>