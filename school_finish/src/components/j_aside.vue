<template>
<div >
    <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">
        <el-radio-button :label="false">展开</el-radio-button>
        <el-radio-button :label="true">收起</el-radio-button>
    </el-radio-group>
    <el-menu default-active="1-4-1" class="el-menu-vertical-demo" :collapse="isCollapse">
        <el-submenu index="1">
            <template slot="title">
                <i class="el-icon-setting"></i>
                <span slot="title">个人信息管理</span>
            </template>
            <el-submenu index="1-1">
                <span slot="title">操作1</span>
                <el-menu-item index="1-1-1"><router-link to="/modifyPassword">修改密码</router-link></el-menu-item>
            </el-submenu>
        </el-submenu>

        <el-submenu index="2">
            <template slot="title">
                <i class="el-icon-setting"></i>
                <span slot="title">学生管理</span>
            </template>
            <el-submenu index="2-1">
                <span slot="title">操作1</span>
                <el-menu-item index="2-1-1"><router-link to="/StudentMessage">学生信息查询</router-link></el-menu-item>
                <el-menu-item index="2-1-2"><router-link to="/CheckIn">学生入住</router-link></el-menu-item>
            </el-submenu>
        </el-submenu>

        <el-submenu index="3">
            <template slot="title">
                <i class="el-icon-setting"></i>
                <span slot="title">房间及设施管理</span>
            </template>
            <el-submenu index="3-1">
                <span slot="title">房间管理</span>
                <el-menu-item index="3-1-1"><router-link to="/RoomInformation">房间信息查询</router-link></el-menu-item>
                <el-menu-item index="3-1-2"><router-link to="/AddRoom">房间添加</router-link></el-menu-item>
            </el-submenu>

            <el-submenu index="3-2">
                <span slot="title">设施管理</span>
                <el-menu-item index="3-1-1"><router-link to="/facilities">查看房间信息</router-link></el-menu-item>
                <el-menu-item index="3-1-2"><router-link to="/AddFac">添加设施记录</router-link></el-menu-item>
            </el-submenu>
        </el-submenu>

        <el-submenu index="4">
            <template slot="title">
                <i class="el-icon-setting"></i>
                <span slot="title">班级管理</span>
            </template>
            <el-submenu index="4-1">
                <span slot="title">操作1</span>
                <el-menu-item index="4-1-1"><router-link to="/ClassMe">班级信息查询</router-link></el-menu-item>
                <el-menu-item index="4-1-2"><router-link to="/AddClass">添加班级</router-link></el-menu-item>
            </el-submenu>
        </el-submenu>

        <el-submenu index="5">
            <template slot="title">
                <i class="el-icon-setting"></i>
                <span slot="title">用户管理</span>
            </template>
            <el-submenu index="5-1">
                <span slot="title">操作1</span>
                <el-menu-item index="5-1-1"><router-link to="/UserMe">用户信息查询</router-link></el-menu-item>
                <el-menu-item index="5-1-2"><router-link to="/AddUser">添加用户</router-link></el-menu-item>
            </el-submenu>
        </el-submenu>
    </el-menu>
</div>
</template>

<script>
export default{
    name:'j_aside',
    data:function(){
        return{
            isCollapse: false
         }
    },methods:{

    }
}
</script>

<style scoped>
    .el-menu-vertical-demo:not(.el-menu--collapse) {
        width: 200px;
        min-height: 400px;
    }
    a{
        text-decoration: none;
    }
    .router-link-active {
        text-decoration: none;
    }
</style>