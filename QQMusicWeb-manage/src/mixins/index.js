export const mixin ={
    methods:{
        // 提示信息
        // notify(title,type){
        //     this.$notify({
        //         title:title,
        //         type: type
        //     })
            
        // }
        message(title,type){
            this.$message({
                message:title,
                type: type
            })
            
        },
        // 根据相对地址获取绝对地址
        getUrl(url){
            return `${this.$store.state.HOST}/${url}`
        },
        // 转变成对应的性别的中文意思
        getSex(value){
            if(value == 0){
                return "女";
            }
            if(value == 1){
                return "男";
            }
            if(value == 2){
                return "组合";
            }
            if(value == 3){
                return "未知";
            }

        },
        //获取生日 转变格式
        attachBirth(val){
            return String(val).substr(0,10);
        },
        // 上传图片成功之后要做的工作
        handleAvatorSuccess(res){
            let _this = this;
            if(res.code == 1){
               _this.getData();
                _this.$message({
                    duration:2000,
                    message:'上传成功！',
                    type: 'success'
                });
            }else{
                _this.$message({
                    duration:2000,
                    message:'上传失败！',
                    type:'error'
                });

            }
        },
        // 传递删除的选项id 显示删除弹框
        handleDelete(id){
            this.index = id;
            this.deleteVisible = true;
        },
        // 把已经选择的选项 放到multipleSelection中
        handleSelectionChange(val){
            this.multipleSelection = val;
        },

        // 批量删除已经选择的项目
        deleteAll(){

            for(let item of this.multipleSelection){
                this.handleDelete(item.id);
                this.deleteRow();
            }
            this.multipleSelection = [];
        }
    }
}