<template>
<div>
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/Chief' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>学生管理</el-breadcrumb-item>
        <el-breadcrumb-item>操作1</el-breadcrumb-item>
        <el-breadcrumb-item>学生信息查询</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="StudentSelect">
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item label="学生姓名">
                <el-input v-model="formInline.StuName" placeholder="学生姓名"></el-input>
            </el-form-item>
            <el-form-item label="班级">
                <el-select v-model="formInline.class" placeholder="班级">
                    <el-option label="GT11" value="1711"></el-option>
                    <el-option label="GT22" value="1722"></el-option>
                    <el-option label="GT23" value="1823"></el-option>
                    <el-option label="GT24" value="1824"></el-option>
                    <el-option label="GT25" value="1925"></el-option>
                    <el-option label="GT26" value="1926"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="所在房间">
                <el-input v-model="formInline.R" placeholder="所在房间编号"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="SelectM">查询</el-button>
            </el-form-item>
            <el-form-item>
            <el-button  class="re" type="primary" @click="RE">刷新</el-button>
            </el-form-item>
        </el-form>
    </div>

    <div class="StudentForm">
        <el-table
                :data="tableData"
                stripe
                style="width: 100%">
            <el-table-column
                    prop="studentId"
                    label="学生编号"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="f_name"
                    label="学生姓名"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="f_sex"
                    label="性别"
                    width="110">
            </el-table-column>
            <el-table-column
                    prop="f_roomId"
                    label="所在房间"
            width="180">
            </el-table-column>
            <el-table-column
                    prop="f_phone"
                    label="联系电话"
            width="180">
            </el-table-column>
            <el-table-column
                    prop="f_classId"
                    label="班级"
            width="180">
            </el-table-column>
                <el-table-column
                        prop="f_joinDate"
                        label="入住时间"
                        width="180"
                        :formatter="DateC">
            </el-table-column>
            <el-table-column
                    label="操作">
                <template slot-scope="scope">
                <el-button type="primary" id="change"  @click="exchange(scope.row)">学生换房</el-button>
                <el-button type="warning" id="Remove" @click="Quit(scope.row)">学生退房</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>

    <div class="dialog">
        <el-dialog title="学生换房" :visible.sync="dialogFormVisible">
            <el-form  :model="form">
                <el-form-item label="姓名" :label-width="formLabelWidth">
                   {{form.StuName}}
                </el-form-item>
                <el-form-item label="性别" :label-width="formLabelWidth">
                    {{form.StuSex}}
                </el-form-item>
                <el-form-item label="班级" :label-width="formLabelWidth">
                    {{form.StuClass}}
                </el-form-item>
                <el-form-item label="原房间" :label-width="formLabelWidth">
                    {{form.StuRoom}}
                </el-form-item>
                <el-form-item label="新房间" :label-width="formLabelWidth">
                    <el-input v-model="form.newRoom" autocomplete="off"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="ModifyRoom">确 定</el-button>
            </div>
        </el-dialog>
    </div>

    <div class="dialog2">
        <el-dialog
                title="提示"
                :visible.sync="dialogVisible1"
                width="30%"
                :before-close="handleClose">
            <span>确定退房？</span>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible1 = false">取 消</el-button>
    <el-button type="primary" @click="NowQuit">确 定</el-button>
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
    name:'StudentMessage',
    data:function(){
        return{
            dialogVisible1:false,
            dialogFormVisible:false,
            page:{
                currentpage:1,
                size:6,
                total:0
            },
            formInline: {
                StuName: '',
                class: '',
                R:''
            },
            tableData:[],
            form: {
                StudentId:'',
                newRoom:'',
                StuName:'',
                StuSex:'',
                StuClass:'',
                StuRoom:'',
                delivery: false,
            },
            formLabelWidth: '120px'
         }
    },methods:{
        Select:function () {

        },
        RE:function () {
            this.totalPage()
            this.requestStudent()
        }
        ,
        CH:function (data) {
            this.page.currentpage=data
            this.requestStudent()

        },//条件查询
        SelectM:function () {
            if(this.formInline.StuName.length==0){
                this.$notify({
                    title: '提示',
                    message: '请输入学生姓名',
                    type: 'warning'
                });
            }else if(this.formInline.class.length==0){
                this.$notify({
                    title: '提示',
                    message: '请选择班级',
                    type: 'warning'
                });
            }else if(this.formInline.R.length==0){
                this.$notify({
                    title: '提示',
                    message: '请输入房间编号',
                    type: 'warning'
                });
            }else{
                var params = new URLSearchParams()
                params.append('f_name', this.formInline.StuName)
                params.append('f_classId', this.formInline.class)
                params.append('f_roomId', this.formInline.R)
                this.$axios.post('/api/FindStudent.do',params).then((data) => {
                    if(data.data.length==0){
                        this.$notify({
                            title: '提示',
                            message: '数据为空',
                            type: 'warning'
                        });
                    }else if(data.data=='学生姓名或房间号不能为空'){
                        this.$notify({
                            title: '提示',
                            message: data.data,
                            type: 'warning'
                        });
                    }else{
                        console.log(data)
                        this.tableData = data.data
                        this.page.total=data.data.length
                        this.formInline.StuName=''
                        this.formInline.class=''
                        this.formInline.R=''
                    }

                }).catch((err) => {
                    this.$notify({
                        title: '提示',
                        message: '服务器出错',
                        type: 'warning'
                    });
                })
            }
        }//退房对话框弹出
        ,Quit:function(row){
            this.form.StudentId=row.studentId
            this.form.StuName=row.f_name;
            this.form.StuSex=row.f_sex;
            this.form.StuClass=row.f_classId;
            this.form.StuRoom=row.f_roomId;
            this.dialogVisible1=true
        },//退房确定按钮
        NowQuit:function () {
            var params=new URLSearchParams();
            params.append('studentId',this.form.StudentId)
            params.append('f_name',this.form.StuName)
            this.$axios.post('/api/Quit.do',params).then((data)=>{
                //如果返回值等于删除失败
                if(data.data=='退房失败'){
                    this.$notify({
                        title: '提示',
                        message: data.data,
                        type: 'warning'
                    });
                }else{
                    this.$notify({
                        title: '提示',
                        message: data.data,
                        type: 'warning'
                    });
                    this.dialogVisible1=false
                    //成功后更新当前列表
                    this.totalPage();
                    this.requestStudent()
                }

            }).catch((err)=>{

            })
        }//学生换房
         ,ModifyRoom:function () {
            if(this.form.newRoom.length==0){
                this.$notify({
                    title: '提示',
                    message: '请输入新房间',
                    type: 'warning'
                });
            }else {
                var params = new URLSearchParams()
                params.append('studentId', this.form.StudentId)
                params.append('f_name', this.form.StuName)
                params.append('f_roomId', this.form.StuRoom)
                params.append('f_newRoom', this.form.newRoom)
                this.$axios.post('/api/ModifyRoom.do', params).then((data) => {
                    if (data.data == '新房间不能为空格') {
                        this.$notify({
                            title: '提示',
                            message: data.data,
                            type: 'warning'
                        });
                    }else if(data.data == '需要调换的房间不存在'){
                        this.$notify({
                            title: '提示',
                            message: data.data,
                            type: 'warning'
                        });
                    }else{
                        this.$notify({
                            title: '提示',
                            message: data.data,
                            type: 'warning'
                        });
                        this.form.StudentId=''
                        this.form.StuName=''
                        this.form.StuRoom=''
                        this.form.newRoom=''
                        this.dialogFormVisible = false
                        //更新一下外列表

                        this.requestStudent()
                    }

                }).catch((err) => {
                    this.$notify({
                        title: '提示',
                        message: '服务器出错',
                        type: 'warning'
                    });
                })

            }
        },
        exchange:function(row){
            this.dialogFormVisible=true;
            this.form.StudentId=row.studentId
            this.form.StuName=row.f_name;
            this.form.StuSex=row.f_sex;
            this.form.StuClass=row.f_classId;
            this.form.StuRoom=row.f_roomId;
        },
        totalPage:function () { //查看总数，返回总数 框架自己会计算总页数出来
            this.$axios.post('/api/totalPage.do').then((res)=>{
                this.page.total=res.data;
            }).catch((err)=>{
                this.$notify({
                    title: '提示',
                    message: '服务器出错',
                    type: 'warning'
                });
            })
        },requestStudent:function () { //申请 学生数据
             var params=new URLSearchParams()
             params.append('currentPage',this.page.currentpage)
             params.append('PageSize',this.page.size)
             this.$axios.post('/api/StudentM.do',params).then((res)=>{
                 this.tableData=res.data
             }).catch((err)=>{
                 this.$notify({
                     title: '提示',
                     message: '服务器出错',
                     type: 'warning'
                 });
             })
        },
        DateC:function(row,column){
            //转换当行日期 的类型
                return new Date(row.f_joinDate).toLocaleDateString()
        },//退房关闭按钮
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => {});
        }
    },created:function () {
        this.totalPage()
        this.requestStudent()

    },mounted: function(){
    }
    ,updated:function() {

    }
}
</script>

<style scoped>
    .StudentForm{
        margin:0 auto;
        margin-top: 100px;
    }
    .StudentSelect{
        height: 30px;
        margin-top:20px;
    }
</style>