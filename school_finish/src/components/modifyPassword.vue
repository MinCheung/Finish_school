<template>

<div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/Chief' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>个人信息管理</el-breadcrumb-item>
        <el-breadcrumb-item>操作1</el-breadcrumb-item>
        <el-breadcrumb-item>修改密码</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="SubForm">
    <el-form :label-position="labelPosition" label-width="110px" :model="formLabelAlign">
        <el-form-item label="请输入旧密码">
            <el-input v-model="formLabelAlign.oldpsw"  show-password></el-input>
        </el-form-item>
        <el-form-item label="请输入新密码">
            <el-input v-model="formLabelAlign.newpsw" show-password></el-input>
        </el-form-item>
        <el-form-item label="请确认新密码">
            <el-input v-model="formLabelAlign.sure" show-password></el-input>
        </el-form-item>
        <el-button type="primary" @click="Modify">确定修改</el-button>
    </el-form>
    </div>
</div>

</template>

<script>
export default{
    name:'modifyPassword',
    data:function(){
        return{
            labelPosition: 'right',
            formLabelAlign: {
                oldpsw: '',
                newpsw: '',
                sure: ''
            }
         }
    },methods:{
        Modify:function () {
            if(this.formLabelAlign.oldpsw.length==0){
                this.$notify({
                    title: '提示',
                    message: '请输入旧密码',
                    type: 'warning'
                });
            }else if(this.formLabelAlign.newpsw.length==0){
                this.$notify({
                    title: '提示',
                    message: '请输入新密码',
                    type: 'warning'
                });
            }else if(this.formLabelAlign.sure.length==0){
                this.$notify({
                    title: '提示',
                    message: '请再次输入确认新密码',
                    type: 'warning'
                });
            }else if(!(this.formLabelAlign.newpsw==this.formLabelAlign.sure)){
                this.$notify({
                    title: '提示',
                    message: '两次输入新密码不一致',
                    type: 'warning'
                });
            }else{
                //封装传参对象
                var params =new URLSearchParams()
                params.append('OldPsw',this.formLabelAlign.oldpsw)
                params.append('f_password',this.formLabelAlign.newpsw)
                //提取session中的内容
                params.append('userId',sessionStorage.getItem("userId"))
                params.append('f_realName',sessionStorage.getItem("userName"))
                //使用axios请求
                this.$axios.post('/api/ModifyPsw.do',params).then((response)=>{
                    if(response.data=='旧密码不匹配请重新输入'){
                        this.$notify({
                            title: '提示',
                            message:response.data,
                            type: 'warning'
                        });
                    }else{
                        this.$notify({
                            title: '提示',
                            message: response.data,
                            type: 'warning'
                        });
                        this.formLabelAlign.oldpsw=''
                        this.formLabelAlign.newpsw=''
                        this.formLabelAlign.sure=''
                    }
                }).catch((err)=>{
                    console.log(err)
                })
            }
        }
    }
}
</script>

<style scoped>
.SubForm{
    width: 420px;
    margin: 0 auto;
    margin-top: 90px;
}

</style>