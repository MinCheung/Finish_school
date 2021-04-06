<template>
<div>
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/Chief' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
        <el-breadcrumb-item>操作1</el-breadcrumb-item>
        <el-breadcrumb-item>添加用户</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="left">
        <el-col >
            <el-card shadow="hover">
                <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign">
                    <el-form-item label="用户编号">
                        <el-input v-model="formLabelAlign.Unumber"></el-input>
                    </el-form-item>
                    <el-form-item label="用户名">
                        <el-input v-model="formLabelAlign.Uname"></el-input>
                    </el-form-item>
                    <el-form-item label="真实姓名">
                        <el-input v-model="formLabelAlign.Rname"></el-input>
                    </el-form-item>
                    <el-form-item label="用户密码">
                        <el-input v-model="formLabelAlign.password" show-password></el-input>
                    </el-form-item>
                    <el-form-item label="用户身份">
                        <el-select v-model="formLabelAlign.Ulevel" placeholder="用户等级">
                            <el-option label="班主任" value="班主任"></el-option>
                            <el-option label="房间管理员" value="房间管理员"></el-option>
                            <el-option label="系统管理员" value="系统管理员"></el-option>
                            <el-option label="超级管理员" value="超级管理员"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="AddU">确认添加</el-button>
                    </el-form-item>
                </el-form>
            </el-card>
        </el-col>

    </div>
</div>
</template>

<script>
export default{
    name:'AddUser',
    data:function(){
        return{
            labelPosition: 'left',
            formLabelAlign:{
                Unumber:'',
                Uname:'',
                password:'',
                Rname:'',
                Ulevel:''
            }
         }
    },methods:{
        AddU:function () {
        if(this.formLabelAlign.Unumber.length==0){
            this.$notify({
                title: '提示',
                message: '请输入用户编号',
                type: 'warning'
            });
        }else if(this.formLabelAlign.Uname.length==0){
            this.$notify({
                title: '提示',
                message: '请输入用户名',
                type: 'warning'
            });
        }else if(this.formLabelAlign.password.length==0){
            this.$notify({
                title: '提示',
                message: '请输入秘密吗',
                type: 'warning'
            });
        }else if(this.formLabelAlign.Rname.length==0){
            this.$notify({
                title: '提示',
                message: '请输入真实姓名',
                type: 'warning'
            });
        }else if(this.formLabelAlign.Ulevel.length==0){
            this.$notify({
                title: '提示',
                message: '请选择用户身份',
                type: 'warning'
            });
        }else {
            var params=new URLSearchParams()
            params.append('userId',this.formLabelAlign.Unumber)
            params.append('f_userName',this.formLabelAlign.Uname)
            params.append('f_password',this.formLabelAlign.password)
            params.append('f_realName',this.formLabelAlign.Rname)
            params.append('f_grade',this.formLabelAlign.Ulevel)
            this.$axios.post('/api/AddUser.do',params).then((data)=>{
                this.$notify({
                    title: '提示',
                    message: data.data,
                    type: 'warning'
                });
                if(data.data=='添加成功'){
                    this.formLabelAlign.Unumber=''
                    this.formLabelAlign.Uname=''
                    this.formLabelAlign.password=''
                    this.formLabelAlign.Rname=''
                    this.formLabelAlign.Ulevel=''
                }
            }).catch((err)=>{
                this.$notify({
                    title: '提示',
                    message: '服务器错误',
                    type: 'warning'
                });
            })
        }

        }
    }
}
</script>

<style scoped>
    .left{
        width: 410px;
        margin: 0 auto;
        margin-top: 60px;
    }
</style>