<template>
<div>
  <el-container>
    <el-header>
      <div class="header-name">
        <h3>xxxx宿舍管理系统</h3>
      </div>
      <h5 class="user">
        <el-row class="card1" >
          <el-col>
            <el-card shadow="always">
              <p>欢迎你:{{username}}</p>
              <p>用户身份:{{userGrade}}</p>
              <el-button type="text"  @click="loginOut">退出</el-button>
            </el-card>
          </el-col>
        </el-row></h5>
    </el-header>
    <el-container>

      <el-aside width="201px" >
         <div class="el-aside-div" >
           <J_a/>
         </div>
      </el-aside>
      <el-container>

        <el-main >
          <router-view></router-view>
        </el-main>

      </el-container>
    </el-container>

    <el-footer>2021@copyright版权归xxx所有</el-footer>
  </el-container>
</div>
</template>

<script>
  import Aside from "../components/j_aside";
export default {
  name: 'Chief',
  components: {
    J_a:Aside
  },
  data: function () {
    return {
      username:sessionStorage.getItem("userName"),
      userGrade:sessionStorage.getItem("userGrade")
    }
  }, methods: {
    loginOut:function() {
        sessionStorage.clear()
        this.$router.push("/")

    }
   }

}
</script>

<style scoped>
  .card1{
  }
  .header-name{

  }
  .user{
    position: absolute;
    text-align: center;
    margin-top: -50px;
    left: 1700px;
    width: 200px;


  }
  .el-aside-div {
    overflow-x:hidden;
    overflow-y:scroll;
    max-height: 832px;
  }
  .el-header{
    background-color: midnightblue;
    color: aliceblue;
    height: 60px;
    text-align: left;
  }
  .el-footer{
    background-color: midnightblue;
    color: #333;
    height: 60px;
    text-align: center;
  }

    .el-aside {
    color: #333;
    text-align:left;

  }

  .el-main {
    min-height: 832px;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
</style>