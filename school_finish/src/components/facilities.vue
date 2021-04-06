<template>
<div>
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/Chief' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>房间及设施管理管理</el-breadcrumb-item>
        <el-breadcrumb-item>设施管理管理</el-breadcrumb-item>
        <el-breadcrumb-item>查看房间信息</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="RoomSelect">
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item label="房间编号">
                <el-input v-model="formInline.RoomName" placeholder="房间编号"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="SelectR">查询</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="RE_N">刷新</el-button>
            </el-form-item>
        </el-form>
    </div>

    <div class="facilities">
        <el-table
                :data="tableData"
                stripe
                style="width: 100%">
            <el-table-column
                    prop="f_roomId"
                    label="房间号"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="facilityId"
                    label="设备编号"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="f_damageInfo"
                    label="损坏描述"
                    width="420">
            </el-table-column>
            <el-table-column
                    prop="f_resolveStatus"
                    label="解决状态"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="f_damageDate"
                    label="损坏日期"
                    width="180"
                    :formatter="DateC">
            </el-table-column>
            <el-table-column
                    label="操作">
                <template slot-scope="scope">
                    <el-button type="primary" id="daily"  @click="ok(scope.row)">已解决</el-button>
                    <el-button type="info" id="change"  @click="No(scope.row)">未解决</el-button>
                </template>
            </el-table-column>
        </el-table>
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
    name:'facilities',
    data:function(){
        return{
            page:{
                currentpage:1,
                size:8,
                total:0,
            },
            formInline: {
                RoomName:''
            },
            tableData: [],
         }
    },methods:{
        CH:function (data) {
            this.page.currentpage=data;
            this.requestRoomF()
        },
        SelectR:function () {
            var params=new URLSearchParams()
            params.append('f_roomId',this. formInline.RoomName)
            this.$axios.post('/api/SelectRF.do',params).then((res)=>{
                if(res.data=='房间号不能为空格'){
                    this.$notify({
                        title: '提示',
                        message: res.data,
                        type: 'warning'
                    });
                }
                else if(res.data.length==0){
                    this.$notify({
                        title: '提示',
                        message: '没有数据',
                        type: 'warning'
                    });
                }else{
                    this.tableData=res.data
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
            this.$axios.post('/api/totalPageofRoomF.do').then((res)=>{
                this.page.total=res.data;
            }).catch((err)=>{
                this.$notify({
                    title: '提示',
                    message: '服务器出错',
                    type: 'warning'
                });
            })
        },
        requestRoomF:function () { //申请 房间设备数据
            var params=new URLSearchParams()
            params.append('currentPage',this.page.currentpage)
            params.append('PageSize',this.page.size)
            this.$axios.post('/api/RoomF.do',params).then((res)=>{
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
        },DateC:function (row) {
            //转换日期格式
            return new Date(row.f_damageDate).toLocaleDateString()
        },ok:function (row) {
            if(row.f_resolveStatus=='已解决'){
                this.$notify({
                    title: '提示',
                    message:'状态正是 已解决',
                    type: 'warning'
                });
            }else{    var params=new URLSearchParams()
                params.append('f_roomId',row.f_roomId)
                params.append('facilityId',row.facilityId)
                params.append('f_resolveStatus',row.f_resolveStatus)
                this.$axios.post('/api/Change.do',params).then((res)=>{
                    if(res.data=='修改状态失败'){
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
                        //更新
                        this.requestRoomF()
                    }

                }).catch((err)=>{
                    this.$notify({
                        title: '提示',
                        message: '服务器出错',
                        type: 'warning'
                    });
                })
            }
        },
        No:function (row) {
            if(row.f_resolveStatus=='未解决'){
                this.$notify({
                    title: '提示',
                    message:'状态正是 未解决',
                    type: 'warning'
                });
            }else{    var params=new URLSearchParams()
                params.append('f_roomId',row.f_roomId)
                params.append('facilityId',row.facilityId)
                params.append('f_resolveStatus',row.f_resolveStatus)
                this.$axios.post('/api/Change.do',params).then((res)=>{
                    if(res.data=='修改状态失败'){
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
                        this.requestRoomF()
                    }
                }).catch((err)=>{
                    this.$notify({
                        title: '提示',
                        message: '服务器出错',
                        type: 'warning'
                    });
                })
            }
        },
        RE_N:function () {
            this.totalPage()
            this.requestRoomF()
        }
    },created:function () {
        this.totalPage()
        this.requestRoomF()
    }
}
</script>

<style scoped>
    .facilities{
        margin:0 auto;
        margin-top: 100px;
    }
    .RoomSelect{
        height: 30px;
        margin-top:20px;
    }
</style>