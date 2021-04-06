<template>
<div>
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/Chief' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>房间及设施管理管理</el-breadcrumb-item>
        <el-breadcrumb-item>设施管理</el-breadcrumb-item>
        <el-breadcrumb-item>添加设施记录</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="left">
        <el-col >
            <el-card shadow="hover">
                <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign">
                    <el-form-item label="房间编号">
                        <el-input v-model="formLabelAlign.RoomId"></el-input>
                    </el-form-item>
                    <el-form-item label="设备编号">
                        <el-input v-model="formLabelAlign.FacbId"></el-input>
                    </el-form-item>
                    <el-form-item label="描述">
                        <el-input
                                type="textarea"
                                :rows="4"
                                placeholder="请输入内容"
                                v-model="formLabelAlign.miaoshu">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="损坏时间">
                        <el-date-picker
                                v-model="formLabelAlign.Date"
                                type="date"
                                placeholder="选择日期">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="解决状态">
                        <el-select v-model="formLabelAlign.status" placeholder="状态">
                            <el-option label="已解决" value="已解决"></el-option>
                            <el-option label="未解决" value="未解决"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="AddR">确认报修</el-button>
                    </el-form-item>
                </el-form>
            </el-card>
        </el-col>
    </div>

</div>
</template>

<script>
export default{
    name:'AddFac',
    data:function(){
        return{
            labelPosition: 'left',
            formLabelAlign:{
                RoomId:'',
                miaoshu:'',
                Date:'',
                status:'',
                FacbId:''
            }
         }
    },methods:{
        AddR:function () {
        if(this.formLabelAlign.RoomId.length==0){
            this.$notify({
                title: '提示',
                message: '请输入房间编号',
                type: 'warning'
            });
        }else if(this.formLabelAlign.FacbId.length==0){
            this.$notify({
                title: '提示',
                message: '请输入设备编号',
                type: 'warning'
            });
        }else if(this.formLabelAlign.Date.length==0){
            this.$notify({
                title: '提示',
                message: '请选择日期',
                type: 'warning'
            });
        }else if(this.formLabelAlign.miaoshu.length==0){
            this.$notify({
                title: '提示',
                message: '请输入描述',
                type: 'warning'
            });
            }else if(this.formLabelAlign.status.length==0){
            this.$notify({
                title: '提示',
                message: '请选择房间状态',
                type: 'warning'
            });
        }else{
            var params=new URLSearchParams()
            params.append('facilityId',this.formLabelAlign.FacbId)
            params.append('f_damageInfo',this.formLabelAlign.miaoshu)
            params.append('f_damageDate',this.formLabelAlign.Date)
            params.append('f_resolveStatus',this.formLabelAlign.status)
            params.append('f_roomId',this.formLabelAlign.RoomId)
            this.$axios.post('/api/AddFacility.do',params).then((data)=>{
                this.$notify({
                    title: '提示',
                    message: data.data,
                    type: 'warning'
                });
                if(data.data=='添加设施记录成功'){
                    this.formLabelAlign.FacbId=''
                    this.formLabelAlign.miaoshu=''
                    this.formLabelAlign.Date=''
                    this.formLabelAlign.status=''
                    this.formLabelAlign.RoomId=''
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
        width:410px;
        margin: 0 auto;
        margin-top: 60px;
    }
</style>