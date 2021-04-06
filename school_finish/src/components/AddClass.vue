<template>
<div>
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/Chief' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>班级管理</el-breadcrumb-item>
        <el-breadcrumb-item>操作1</el-breadcrumb-item>
        <el-breadcrumb-item>添加班级</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="left">
        <el-col >
            <el-card shadow="hover">
                <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign">
                    <el-form-item label="班级编号">
                        <el-input v-model="formLabelAlign.ClassNumber"></el-input>
                    </el-form-item>
                    <el-form-item label="班级名称">
                        <el-input v-model="formLabelAlign.ClassName"></el-input>
                    </el-form-item>
                    <el-form-item label="带班老师">
                        <el-input v-model="formLabelAlign.teacher"></el-input>
                    </el-form-item>
                    <el-form-item label="开班时间">
                        <el-date-picker
                                v-model="formLabelAlign.Date"
                                type="date"
                                placeholder="选择日期">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="AddC">确认添加</el-button>
                    </el-form-item>
                </el-form>
            </el-card>
        </el-col>

    </div>
</div>
</template>

<script>
export default{
    name:'AddClass',
    data:function(){
        return{
            value1:'',
            labelPosition: 'left',
            formLabelAlign:{
                ClassNumber:'',
                ClassName:'',
                teacher:'',
                Date:''
            }
         }
    },methods:{
        AddC:function () {
            if(this.formLabelAlign.ClassNumber.length==0){
                this.$notify({
                    title: '提示',
                    message: '请输入班级编号',
                    type: 'warning'
                });
            }else  if(this.formLabelAlign.ClassName.length==0){
                this.$notify({
                    title: '提示',
                    message: '请输入班级名称',
                    type: 'warning'
                });
            }else if(this.formLabelAlign.teacher.length==0){
                this.$notify({
                    title: '提示',
                    message: '请输入带班教师',
                    type: 'warning'
                });
            }else if(this.formLabelAlign.Date.length==0){
                this.$notify({
                    title: '提示',
                    message: '请选择日期',
                    type: 'warning'
                });
            }else{
                var params=new URLSearchParams()
                params.append('classId',this.formLabelAlign.ClassNumber)
                params.append('className',this.formLabelAlign.ClassName)
                params.append('createDate',this.formLabelAlign.Date)
                params.append('teacher',this.formLabelAlign.teacher)
                this.$axios.post('/api/addClass.do',params).then((data)=> {
                    this.$notify({
                        title: '提示',
                        message: data.data,
                        type: 'warning'
                    });
                    if(data.data=='添加成功'){
                       this.formLabelAlign.ClassNumber=''
                        this.formLabelAlign.ClassName=''
                        this.formLabelAlign.Date=''
                        this.formLabelAlign.teacher=''
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