<template>

<div>
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{path: '/Chief' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>学生管理</el-breadcrumb-item>
        <el-breadcrumb-item>操作1</el-breadcrumb-item>
        <el-breadcrumb-item>学生入住</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="left">
        <el-col >
            <el-card shadow="hover">
                <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign">
                    <el-form-item label="照片">
                        <el-upload
                                class="avatar-uploader"
                                action="/api/uploadphoto.do"
                                :show-file-list="false"
                                :on-success="handleAvatarSuccess"
                                :before-upload="beforeAvatarUpload"
                                enctype="multipart/form-data"
                                name="multipartFile">
                            <img v-if="imageUrl" :src="imageUrl" class="avatar" >
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="姓名">
                        <el-input v-model="formLabelAlign.name"></el-input>
                    </el-form-item>
                    <el-form-item label="学号">
                        <el-input v-model="formLabelAlign.Number"></el-input>
                    </el-form-item>
                    <el-form-item label="房间">
                        <el-input v-model="formLabelAlign.Room"></el-input>
                    </el-form-item>
                    <el-form-item label="联系电话">
                        <el-input v-model="formLabelAlign.Phone"></el-input>
                    </el-form-item>
                    <el-form-item label="班级">
                        <el-select v-model="formLabelAlign.class" placeholder="班级">
                            <el-option label="GT11" value="1711"></el-option>
                            <el-option label="GT22" value="1722"></el-option>
                            <el-option label="GT23" value="1823"></el-option>
                            <el-option label="GT24" value="1824"></el-option>
                            <el-option label="GT25" value="1925"></el-option>
                            <el-option label="GT26" value="1926"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="入住时间">
                        <el-date-picker
                                v-model="formLabelAlign.Date"
                                type="date"
                                placeholder="选择日期">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="性别">
                        <el-radio v-model="radio" label="1" value="男">男</el-radio>
                        <el-radio v-model="radio" label="2" value="女">女</el-radio>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="Checkin">确认入住</el-button>
                    </el-form-item>
                </el-form>
            </el-card>
        </el-col>

    </div>
</div>
</template>

<script>
export default{
    name:'CheckIn',
    data:function(){
        return{
            imageUrl: '',
            labelPosition: 'left',
            radio:'1',
            formLabelAlign:{
                name:'',
                Room:'',
                class:'',
                Phone:'',
                FilePath:'',
                Number:'',
                Date:''
            }
         }
    },methods:{
        handleAvatarSuccess(res, file) {
            this.imageUrl = URL.createObjectURL(file.raw);
            this.formLabelAlign.FilePath=res
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },
        Checkin:function () {
            // 先检查图片是否上传成功
            if(this.formLabelAlign.FilePath.length!=0){
                //判断所有输入框是否为空
                if(this.formLabelAlign.name.length==0){
                    this.$notify({
                        title: '提示',
                        message: '请输入学生姓名',
                        type: 'warning'
                    });
                }else if(this.formLabelAlign.Room.length==0){
                    this.$notify({
                        title: '提示',
                        message: '请输入房间号',
                        type: 'warning'
                    });

                }else if(this.formLabelAlign.Number.length==0){
                    this.$notify({
                        title: '提示',
                        message: '请输入学号',
                        type: 'warning'
                    });
                }else if(isNaN(this.formLabelAlign.Room)){
                    this.$notify({
                        title: '提示',
                        message: '房间号请输入数字类型',
                        type: 'warning'
                    });
                }else if(isNaN(this.formLabelAlign.Number)){
                    this.$notify({
                        title: '提示',
                        message: '学号请输入数字类型',
                        type: 'warning'
                    });
                }
                else if(this.formLabelAlign.class.length==0){
                    this.$notify({
                        title: '提示',
                        message: '请输入班级',
                        type: 'warning'
                    });
                }else if(this.formLabelAlign.Phone.length==0){
                    this.$notify({
                        title: '提示',
                        message: '请输入手机号',
                        type: 'warning'
                    });
                }else{
                    var sex=this.radio==1?'男':'女'
                    var  params=new URLSearchParams();
                    params.append('studentId',this.formLabelAlign.Number)
                    params.append('f_name',this.formLabelAlign.name)
                    params.append('f_sex',sex)
                    params.append('f_classId',this.formLabelAlign.class)
                    params.append('f_roomId',this.formLabelAlign.Room)
                    params.append('f_phone',this.formLabelAlign.Phone)
                    params.append('f_face',this.formLabelAlign.FilePath)
                    params.append('f_joinDate',this.formLabelAlign.Date)
                    this.$axios.post('/api/CheckIn.do',params).then((data)=>{
                        this.$notify({
                            title: '提示',
                            message: data.data,
                            type: 'warning'
                        });
                        //如果插入成功清空表单数据
                        if(data.data=='插入成功'){
                            this.imageUrl=''
                           this.formLabelAlign.Number=''
                            this.formLabelAlign.name=''
                            this.formLabelAlign.class=''
                            this.formLabelAlign.Room=''
                            this.formLabelAlign.Phone=''
                            this.formLabelAlign.FilePath=''
                            this.formLabelAlign.Date=''
                            this.radio='1'
                        }
                    }).catch((err)=>{
                        console.log(err)
                        this.$notify({
                            title: '提示',
                            message: '服务器错误',
                            type: 'warning'
                        });
                    })
                }
            }else{
                this.$notify({
                    title: '提示',
                    message: '请上传照片',
                    type: 'warning'
                });
            }
        }
    }
}
</script>

<style scoped>
    .left{
        width: 360px;
     margin: 0 auto;
        margin-top: 10px;
    }
    .avatar-uploader  {
        border: 1px dashed;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        border-color: aliceblue;
    }
    el-upload{
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        overflow: hidden;

    }
    .avatar-uploader:hover  {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 100px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 150px;
        height: 200px;
        display: block;
    }
</style>