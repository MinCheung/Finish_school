<template>
<div>
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/Chief' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>房间及设施管理管理</el-breadcrumb-item>
        <el-breadcrumb-item>房间管理</el-breadcrumb-item>
        <el-breadcrumb-item>房间信息查询</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="RoomSelect">
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item label="房间地址">
                <el-input v-model="formInline.RoomName" placeholder="房间地址"></el-input>
            </el-form-item>
            <el-form-item label="房间状态">
                <el-select v-model="formInline.RoomState" placeholder="房间状态">
                    <el-option label="不限" value=""></el-option>
                    <el-option label="正常" value="正常"></el-option>
                    <el-option label="设施损坏" value="设施损坏"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="房间类型">
                <el-select v-model="formInline.RoomKind" placeholder="房间类型">
                    <el-option label="男生宿舍" value="男生宿舍"></el-option>
                    <el-option label="女生宿舍" value="女生宿舍"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="SelectR">查询</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="Re">刷新</el-button>
            </el-form-item>
        </el-form>
    </div>
    <div class="RoomForm">
        <el-table
                :data="tableData"
                stripe
                style="width: 100%">
            <el-table-column
                    prop="roomId"
                    label="房间编号"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="f_roomAddress"
                    label="房间地址"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="f_recipientNumber"
                    label="可容纳人数"
                    width="260">
            </el-table-column>
            <el-table-column
                    prop="f_status"
                    label="房间状态"
                    width="330">
            </el-table-column>
            <el-table-column
                    prop="f_roomType"
                    label="房间类型"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop=""
                    label="操作">
                <template slot-scope="scope">
                <el-button type="primary" id="change"  @click="DeletR(scope.row)">删除房间</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>

    <div class="dialog">
        <el-dialog title="删除房间" :visible.sync="dialogFormVisible">
            <el-form  :model="form">
                <el-form-item label="房间编号" :label-width="formLabelWidth">
                    {{form.roomId}}
                </el-form-item>
                <el-form-item label="房间地址" :label-width="formLabelWidth">
                    {{form.f_roomAddress}}
                </el-form-item>
                <span>确定删除房间？</span>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="DeletRoom">确 定</el-button>
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
    name:'RoomInformation',
    data:function(){
        return{
            dialogFormVisible:false,
            page:{
                currentpage:1,
                size:6,
                total:0,
            },
            formInline: {
                RoomName:'',
                RoomState:'',
                RoomKind:''
            },
            tableData: [],
            formLabelWidth: '120px',
            form:{
                roomId:'',
                f_roomAddress:''
            }
         }
    },methods:{
        Re:function(){
          this.totalPage()
          this.requestRoom()
        },
        CH:function (data) {
            this.page.currentpage=data;
            console.log(this.page.currentpage);
        },
        SelectR:function () {
            var  params=new URLSearchParams()
            params.append('f_roomAddress',this.formInline.RoomName)
            params.append('f_status',this.formInline.RoomState)
            params.append('f_roomType',this.formInline.RoomKind)
            this.$axios.post('/api/SelectRoomQ.do',params).then((data)=>{

                if(data.data=='房间地址不能为空格'){
                    this.$notify({
                        title: '提示',
                        message: data.data,
                        type: 'warning'
                    });
                }else{
                    this.formInline.RoomName=''
                    this.formInline.RoomState='不限'
                    this.formInline.RoomKind=''
                    //查询到的数据展现
                    this.tableData=data.data
                    //设置总页数为1
                    this.page.total=0;
                }
            }).catch((err)=>{
                this.$notify({
                    title: '提示',
                    message: '服务器出错',
                    type: 'warning'
                });
            })

        },
        totalPage:function () { //查看总数，返回总数 框架自己会计算总页数出来
            this.$axios.post('/api/totalPageofRoom.do').then((res)=>{
                this.page.total=res.data;
            }).catch((err)=>{
                this.$notify({
                    title: '提示',
                    message: '服务器出错',
                    type: 'warning'
                });
            })
        },CH:function (data) {
            this.page.currentpage=data
            this.requestRoom()

        },requestRoom:function () { //申请 房间数据
            var params=new URLSearchParams()
            params.append('currentPage',this.page.currentpage)
            params.append('PageSize',this.page.size)
            this.$axios.post('/api/RoomM.do',params).then((res)=>{
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
        },DeletR:function (row) {
            this.dialogFormVisible=true
            this.form.roomId=row.roomId
            this.form.f_roomAddress=row.f_roomAddress
        },DeletRoom:function () {
                var params=new URLSearchParams()
                params.append('roomId', this.form.roomId)
                params.append('f_roomAddress',this.form.f_roomAddress)
                this.$axios.post('/api/DeletRoom.do',params).then((data)=>{
                        if(data.data=='删除房间失败'){
                            this.$notify({
                                title: '提示',
                                message: '房间还有其他关联暂时还不能删除',
                                type: 'warning'
                            });
                        }else{
                            this.$notify({
                                title: '提示',
                                message: data.data,
                                type: 'warning'
                            });
                            this.totalPage()
                            this.requestRoom()
                            this.dialogFormVisible=false;
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
        this.requestRoom()
    }
}
</script>

<style scoped>
    .RoomForm{
        margin:0 auto;
        margin-top: 100px;
    }
    .RoomSelect{
        height: 30px;
        margin-top:20px;
    }
</style>