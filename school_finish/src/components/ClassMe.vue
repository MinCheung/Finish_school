<template>
<div>
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/Chief' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>班级管理</el-breadcrumb-item>
        <el-breadcrumb-item>操作1</el-breadcrumb-item>
        <el-breadcrumb-item>班级信息查询</el-breadcrumb-item>
    </el-breadcrumb>


    <div class="Cl">
        <el-table
                :data="tableData"
                stripe
                style="width: 100%">
            <el-table-column
                    prop="classId"
                    label="班级ID"
                    width="220">
            </el-table-column>
            <el-table-column
                    prop="className"
                    label="班级名字"
                    width="220">
            </el-table-column>
            <el-table-column
                    prop="createDate"
                    label="创建时间"
                    width="220"
                    :formatter="DateC">
            </el-table-column>
            <el-table-column
                    prop="teacher"
                    label="班主任"
                    width="220">
            </el-table-column>
            <el-table-column
                    label="操作">
                <template slot-scope="scope">
                    <el-button type="danger" @click="DeleClass(scope.row)" >删除班级</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
    <div class="dialog">
        <el-dialog title="删除房间" :visible.sync="dialogFormVisible">
            <el-form  :model="form">
                <el-form-item label="班级ID" :label-width="formLabelWidth">
                    {{form.classId}}
                </el-form-item>
                <el-form-item label="班级名字" :label-width="formLabelWidth">
                    {{form.className}}
                </el-form-item>
                <span>确定删除班级？</span>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="DeletClass">确 定</el-button>
            </div>
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
    name:'ClassMe',
    data:function(){
        return{
            page:{
                currentpage:1,
                size:6,
                total:0,
            },
            tableData: [],
            formLabelWidth: '120px',
            dialogFormVisible:false,
            form:{
                classId:'',
                className:''
            }
         }
    },methods:{
        CH:function (data) {
            this.page.currentpage=data;
            this.requestClass()
        },
        totalPage:function () { //查看总数，返回总数 框架自己会计算总页数出来
            this.$axios.post('/api/totalPageofClass.do').then((res)=>{
                this.page.total=res.data;
            }).catch((err)=>{
                this.$notify({
                    title: '提示',
                    message: '服务器出错',
                    type: 'warning'
                });
            })
        }, requestClass:function () { //申请 班级数据
            var params=new URLSearchParams()
            params.append('currentPage',this.page.currentpage)
            params.append('PageSize',this.page.size)
            this.$axios.post('/api/ClassF.do',params).then((res)=>{
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
        DateC:function (row) {
            return new Date(row.createDate).toLocaleDateString()
        },
        DeleClass:function (row) {
            this.dialogFormVisible=true
            this.form.classId=row.classId
            this.form.className=row.className
        },
        DeletClass:function () {
            var params=new URLSearchParams()
            params.append('classId',this.form.classId)
            params.append('className',this.form.className)
            this.$axios.post('/api/ClassDe.do',params).then((res)=>{
                if(res.data=='删除失败，可能班里还有学生'){
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
                    this.requestClass()
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
        this.requestClass()
    }
}
</script>

<style scoped>
    .Cl{
        margin:0 auto;
        margin-top: 100px;
    }
</style>