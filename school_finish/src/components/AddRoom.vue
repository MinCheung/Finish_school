<template>
<div>
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/Chief' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>房间及设施管理管理</el-breadcrumb-item>
        <el-breadcrumb-item>房间管理</el-breadcrumb-item>
        <el-breadcrumb-item>房间添加</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="left">
        <el-col >
            <el-card shadow="hover">
                <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign">
                    <el-form-item label="房间编号">
                        <el-input v-model="formLabelAlign.RoomNumber"></el-input>
                    </el-form-item>
                    <el-form-item label="房间地址">
                        <el-input v-model="formLabelAlign.Roomname"></el-input>
                    </el-form-item>
                    <el-form-item label="容纳人数">
                        <el-input v-model="formLabelAlign.PeopleNumber"></el-input>
                    </el-form-item>
                    <el-form-item label="房间状态">
                        <el-select v-model="formLabelAlign.status" placeholder="状态">
                            <el-option label="正常" value="正常"></el-option>
                            <el-option label="不正常" value="设施损坏"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="房间类型">
                        <el-select v-model="formLabelAlign.RoomKind" placeholder="房间类型">
                            <el-option label="男生宿舍" value="男生宿舍"></el-option>
                            <el-option label="女生宿舍" value="女生宿舍"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="AddR">确认添加</el-button>
                    </el-form-item>
                </el-form>
            </el-card>
        </el-col>

    </div>

</div>
</template>

<script>
export default{
    name:'AddRoom',
    data:function(){
        return{
            labelPosition: 'left',
            formLabelAlign:{
                RoomNumber:'',
                Roomname:'',
                PeopleNumber:'',
                RoomKind:'',
                status:''
            }
         }
    },methods:{
        AddR:function () {
            if(this.formLabelAlign.RoomNumber.length==0){
                this.$notify({
                    title: '提示',
                    message: '请输入房间编号',
                    type: 'warning'
                });
            }
            else if(this.formLabelAlign.Roomname.length==0){
                this.$notify({
                    title: '提示',
                    message: '请输入房间地址',
                    type: 'warning'
                });
            }else if(this.formLabelAlign.PeopleNumber.length==0){
                this.$notify({
                    title: '提示',
                    message: '请输入房间可容纳人数',
                    type: 'warning'
                });
            }else if(this.formLabelAlign.status.length==0){
                this.$notify({
                    title: '提示',
                    message: '请选择房间状态',
                    type: 'warning'
                });
            }else if(this.formLabelAlign.RoomKind.length==0){
                this.$notify({
                    title: '提示',
                    message: '请选择房间类型',
                    type: 'warning'
                });
            }else{
                //添加post传送的数据对象
                var  params=new URLSearchParams()
                params.append('roomId',this.formLabelAlign.RoomNumber)
                params.append('f_roomAddress',this.formLabelAlign.Roomname)
                params.append('f_recipientNumber',this.formLabelAlign.PeopleNumber)
                params.append('f_status',this.formLabelAlign.status)
                params.append('f_roomType',this.formLabelAlign.RoomKind)
                this.$axios.post('/api/AddRoom.do',params).then((data)=>{
                    this.$notify({
                        title: '提示',
                        message: data.data,
                        type: 'warning'
                    });
                    this.formLabelAlign.RoomNumber=''
                    this.formLabelAlign.Roomname=''
                    this.formLabelAlign.PeopleNumber=''
                    this.formLabelAlign.status=''
                    this.formLabelAlign.RoomKind=''
                }).catch((err)=>{
                    this.$notify({
                        title: '提示',
                        message: '服务器出错',
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