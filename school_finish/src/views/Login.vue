<template>
<div id="form">
  <el-card class="box-card" shadow="hover">
    <h1>宿舍管理系统</h1>
    <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign">
      <el-form-item label="账户编号">
        <el-input v-model="formLabelAlign.Number" placeholder="请输入用户编号" width="100px"></el-input>
      </el-form-item>
      <el-form-item label="用户名">
        <el-input v-model="formLabelAlign.name" placeholder="请输入用户名" width="100px"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="formLabelAlign.psw" show-password placeholder="请输入密码"></el-input>
      </el-form-item >
      <el-button type="primary" @click="submitForm()">登录</el-button>
    </el-form>
  </el-card>
</div>
</template>

<script>
export default{
    name:'Login',
    data:function(){
        return{
          labelPosition: 'left',
          formLabelAlign: {
            name: '',
            psw: '',
            Number:''
          }
         }
    },methods: {
    submitForm:function () {
      if(this.formLabelAlign.name.length==0){
        this.$notify({
          title: '提示',
          message: '请输入用户名',
          type: 'warning'
        });
      }else if(this.formLabelAlign.psw.length==0){
        this.$notify({
          title: '提示',
          message: '请输入密码',
          type: 'warning'
        });
      }else{
        // this.$notify({
        //   title: '提交成功',
        //   message: '发送一个axios请求',
        //   type: 'success'
        // });
        //封装post传参对象
        var  params=new URLSearchParams()
        params.append('f_userName',this.formLabelAlign.name)
        params.append('f_password',this.formLabelAlign.psw)
        params.append('userId',this.formLabelAlign.Number)
        this.$axios.post('/api/Login.do',params).then((data)=>{
           this.$notify({
             title: '提交成功',
             message: data.data.state,
             type: 'success'
           });
            if(data.data.state == '登录成功'){
              //把需要的额用户登录信息保存在session
              sessionStorage.setItem('userName',data.data.userName)
              sessionStorage.setItem('userGrade',data.data.userGrade)
              sessionStorage.setItem("userId",data.data.userId)
               this.$router.push('/Chief')
            }else{
              this.$notify({
                title: '提示',
                message: '编号或用户名或密码错误',
                type: 'warning'
              });
            }
        }).catch((err)=>{
          console.log(err);
        })
      }
    }

  }
}
</script>

<style scoped>
  .box-card {
    width: 480px;
    margin: 0 auto;
    margin-top: 200px;
  }
</style>