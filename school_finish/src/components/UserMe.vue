<template>
<div>
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/Chief' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
        <el-breadcrumb-item>操作1</el-breadcrumb-item>
        <el-breadcrumb-item>用户信息查询</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="UserForm">
        <el-table
                :data="tableData"
                stripe
                style="width: 100%">
            <el-table-column
                    prop="userId"
                    label="用户ID"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="f_userName"
                    label="用户名"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="f_realName"
                    label="真实姓名"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="f_password"
                    label="密码"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="f_grade"
                    label="用户身份"
                    width="180">
            </el-table-column>
            <el-table-column
                    label="操作">
                <template slot-scope="scope">
                    <el-button type="warning"  @click="changR(scope.row)">用户等级修改</el-button>
                    <el-button type="danger" id="change" @click="DeleU(scope.row)">删除用户</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>

    <div class="dialog">
        <el-dialog title="修改角色" :visible.sync="dialogFormVisible">
            <el-form :model="form">
                <el-form-item label="用户编号" :label-width="formLabelWidth">
                    {{form.userId}}
                </el-form-item>
                <el-form-item label="用户姓名" :label-width="formLabelWidth">
                    {{form.userRName}}
                </el-form-item>
                <el-form-item label="修改角色" :label-width="formLabelWidth">
                    <el-select v-model="form.role" placeholder="请选择用户角色">
                        <el-option label="班主任" value="班主任"></el-option>
                        <el-option label="房间管理员" value="房间管理员"></el-option>
                        <el-option label="系统管理员" value="系统管理员"></el-option>
                        <el-option label="超级管理员" value="超级管理员"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="ChangRole">确 定</el-button>
            </div>
        </el-dialog>
    </div>

    <div class="dialog2">
        <el-dialog
                title="提示"
                :visible.sync="dialogVisible1"
                width="30%"
                :before-close="handleClose">
            <span>确定删除用户？</span>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible1 = false">取 消</el-button>
    <el-button type="primary" @click="NowDele">确 定</el-button>
             </span>
        </el-dialog>
    </div>

    <div class="page">
        <el-pagination
                background
                layout="prev, pager, next"
                :total="page.total"
                :page-size="page.size"
                :current-page="page.currentpage"
                @current-change="CH"
        >
        </el-pagination>
    </div>

</div>
</template>

<script>
export default{
    name:'UserMe',
    data:function(){
        return{
            page:{
                currentpage:1,
                size:6,
                total:0,
            },
            tableData: [],
            dialogFormVisible: false,
            dialogVisible1:false,
            formLabelWidth: '120px',
            form:{
                userId:'',
                userRName:'',
                role:'',
                nowRole:''
            },
         }
    },methods:{
        CH:function (data) {
            this.page.currentpage=data;
            this.requestUser()

        },
        totalPage:function () { //查看总数，返回总数 框架自己会计算总页数出来
            this.$axios.post('/api/totalPageofU.do').then((res)=>{
                this.page.total=res.data;
            }).catch((err)=>{
                this.$notify({
                    title: '提示',
                    message: '服务器出错',
                    type: 'warning'
                });
            })
        },requestUser:function () { //申请 房间数据
            var params=new URLSearchParams()
            params.append('currentPage',this.page.currentpage)
            params.append('PageSize',this.page.size)
            this.$axios.post('/api/UserD.do',params).then((res)=>{
                if(res.data=='错误或没有数据'){
                    this.$notify({
                        title: '提示',
                        message: res.data,
                        type: 'warning'
                    });
                }
                this.tableData=res.data
            }).catch((err)=>{
                this.$notify({
                    title: '提示',
                    message: '服务器出错',
                    type: 'warning'
                });
            })
        },
        //修改用户角色
        changR: function (row) {
           this.dialogFormVisible=true
            this.form.userId=row.userId
            this.form.userRName=row.f_realName
            this.form.nowRole=row.f_grade
        },
        DeleU:function (row) {
            this.dialogVisible1=true
            this.form.userId=row.userId
            this.form.userRName=row.f_realName
        },//修改用户角色
        ChangRole:function () {
            if(this.form.role==this.form.nowRole){
                this.$notify({
                    title: '提示',
                    message: '你的角色正是--'+this.form.role,
                    type: 'warning'
                });
            }else{  var params=new URLSearchParams()
                params.append('userId',this.form.userId)
                params.append('f_realName',this.form.userRName)
                params.append('f_grade',this.form.role)
                this.$axios.post('/api/ChangUserG.do',params).then((res)=>{
                    if(res.data=='修改失败'){
                        this.$notify({
                            title: '提示',
                            message: res.data,
                            type: 'warning'
                        });
                    }else {
                        this.$notify({
                            title: '提示',
                            message: res.data,
                            type: 'warning'
                        });
                        this.requestUser()
                        this.dialogFormVisible=false
                    }

                }).catch((err)=>{
                    this.$notify({
                        title: '提示',
                        message: '服务器出错',
                        type: 'warning'
                    });
                })}
        },//删除用户
        NowDele:function () {
            var params=new URLSearchParams()
            params.append('userId',this.form.userId)
            params.append('f_realName',this.form.userRName)
            this.$axios.post('/api/UserDele.do',params).then((res)=>{
                if(res.data=='删除失败'){
                    this.$notify({
                        title: '提示',
                        message: res.data,
                        type: 'warning'
                    });
                }else{
                    this.$notify({
                        title: '提示',
                        message: res.data,
                        type: 'warning'
                    });
                    this.totalPage()
                    this.requestUser()
                    this.dialogVisible1=false
                }

            }).catch((err)=>{
                this.$notify({
                    title: '提示',
                    message: '服务器出错',
                    type: 'warning'
                });
            })
        }
    },created:function () {
            this.totalPage()
        this.requestUser()
    }
}
</script>

<style scoped>
    .UserForm{
        margin:0 auto;
        margin-top: 100px;
    }
</style>