package com.mincheung.Controller;

import com.mincheung.Responses.Page;
import com.mincheung.Responses.ResponseObject;
import com.mincheung.domain.Class;
import com.mincheung.domain.Room;
import com.mincheung.domain.Roomfacility;
import com.mincheung.domain.Student;
import com.mincheung.domain.User;
import com.mincheung.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


/**
 * @author Min
 * */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    //用户登录
    @RequestMapping("/Login.do")
    public @ResponseBody Object Login(User user){
        //验证登录对象
        User u1=userService.findLogin(user);
        //创建返回对象
        ResponseObject re=new ResponseObject();
        if(u1==null){
            re.setState("登录失败");
            re.setUserName(null);
            re.setUserGrade(null);
            //保证传回json数据不是 乱码 将返回对象包装
            //return new MappingJacksonValue("登录失败");
            return re;
        }else {
            //设置登录状态
            re.setState("登录成功");
            //设置用户id
            re.setUserId(u1.getUserId());
            //设置真实姓名
            re.setUserName(u1.getF_realName());
            //设置用户等级
            re.setUserGrade(u1.getF_grade());
            //return new MappingJacksonValue("登录成功");
            return  re;
        }
    }

    /**密码修改*/
    @RequestMapping(value = "/ModifyPsw.do",method = {RequestMethod.POST})
    public @ResponseBody Object ModifyPsw(User user){
        //进行密码修改 如果 返回false表示 旧密码不正确
        boolean Modify=userService.ModifyPsw(user);
        if(!Modify){
            return new MappingJacksonValue("旧密码不匹配请重新输入");
        }
        return new MappingJacksonValue("密码修改成功");
    }

/**
 * 文件上传
 * */
    @RequestMapping(value = "/uploadphoto.do" ,method = {RequestMethod.POST})
    public @ResponseBody Object Upload(HttpServletRequest request, /**接受碎片式文件*/MultipartFile multipartFile) throws IOException {
        //使用fileupload组件上传
        //获得上传的位置
        String path=request.getSession().getServletContext().getRealPath("/uploads/");
        /**判断该路径是否存在*/
        File file=new File(path);
        //如果不存在就创建这个文件夹
        if (!file.exists()){
            file.mkdirs();
        }
        //获取上传文件的名字
        String fileName=multipartFile.getOriginalFilename();
        //设置文件名字唯一
        String uuid= UUID.randomUUID().toString().replace("-","");
        String FileName=uuid+"-"+fileName;
        //结合路径与上传文件的名字  上传文件
        multipartFile.transferTo(new File(path+FileName));
        String nowPath=path+FileName;
        //返回现在的文件路径
        return new MappingJacksonValue(nowPath);
    }
    /**
     * @author Min
     * 学生入住
     * */
    @RequestMapping(value = "/CheckIn.do",method = {RequestMethod.POST})
    public @ResponseBody Object CheckIn(Student student){

        //判断输入的姓名是否为空格
        if(student.getF_name().trim().isEmpty()){
            return new MappingJacksonValue("姓名不能为空格");
        }
        //判断学号和房间号，姓名是否为空格
        if(student.getStudentId()==null||student.getF_roomId()==null){
            return new MappingJacksonValue("学号或房间号不能为空格");
        }
        //调用插入方法如果返回值是true则表示添加成功
       String s= userService.studentCheckIn(student);
        if(s.equals("插入成功")){
            return new MappingJacksonValue("插入成功");
        }else if(s.equals("房间编号不存在")){
            return new MappingJacksonValue("房间编号不存在");
        }else{
            return new MappingJacksonValue("插入失败");
        }
    }

    /**添加房间*/
    @RequestMapping(value = "/AddRoom.do",method = {RequestMethod.POST})
    public @ResponseBody Object AddRoom(Room room){
        System.out.println(room);
        //调用插入方法如果返回值是true则表示添加成功
            if(userService.AddRoom(room)){
                return  new MappingJacksonValue("添加成功");
            }else{
                return  new MappingJacksonValue("添加失败");
            }
    }

    /**添加房间设施损坏记录*/
    @RequestMapping(value = "/AddFacility.do",method = {RequestMethod.POST})
    public @ResponseBody Object AddFacility(Roomfacility roomfacility){
        //判断房间号和设备号是否为空格
        if(roomfacility.getFacilityId()==null||roomfacility.getF_roomId()==null){
         return    new MappingJacksonValue("设备号或房间号不能为空格");
        }
        String ISok=userService.AddFacility(roomfacility);
        if(ISok.equals("插入成功")){
            return    new MappingJacksonValue("添加设施记录成功");
        }else if(ISok.equals("房间号不存在")){
            return    new MappingJacksonValue("房间编号不存在");
        }else{
            return    new MappingJacksonValue("添加设施记录失败");
        }
    }

    /**添加班级*/
    @RequestMapping(value = "/addClass.do",method = {RequestMethod.POST})
    public @ResponseBody Object AddClass(Class c){
        //判断 班级编号和班级名称是否为空格
        if(c.getClassId()==null){
            return    new MappingJacksonValue("班级编号不能为空格");
        }else if(c.getClassName().trim().isEmpty()){
            return    new MappingJacksonValue("班级名称不能为空格");
        }else{
            //如果上两项不是空格就执行插入方法
          if(userService.AddClass(c)){
              return    new MappingJacksonValue("添加成功");
          }else{
              return    new MappingJacksonValue("添加失败");
          }
        }
    }

    /**添加用户信息*/
    @RequestMapping(value = "/AddUser.do",method = {RequestMethod.POST})
    public @ResponseBody Object AddUser(User user){
        //判断对象内容是否为空格
        if(user.getUserId()==null||user.getF_password()==null){
            return    new MappingJacksonValue("密码或用户编号不能为空格");
        }else if(user.getF_userName().trim().isEmpty() || user.getF_realName().trim().isEmpty()){
            return    new MappingJacksonValue("用户名或真实姓名不能为空格");
        }else {
            //使用业务层对象调用插入方法
            if(userService.AddUser(user)){
                return    new MappingJacksonValue("添加成功");
            }else{
                return    new MappingJacksonValue("添加失败");
            }
        }
    }
    /**查询学生信息总条数*/
    @RequestMapping(value = "/totalPage.do",method = {RequestMethod.POST})
    public @ResponseBody Object StudentTotal(){
            //获取学生总数
            Long total=userService.studentTotal();
         return  total;
    }
    /**按页数查询学生*/
    @RequestMapping(value = "/StudentM.do",method = {RequestMethod.POST})
    public @ResponseBody Object StudentMessage(Page page)  {
        //调用业务层方法获取学生列表
        List<Student> students=userService.findStudent(page);
        return  students;
    }
    /**条件查询*/
    @RequestMapping(value = "/FindStudent.do",method = {RequestMethod.POST})
    public @ResponseBody Object FindStudent(Student student){
        System.out.println(student);
        //避免输入空格
        if(student.getF_name().trim().toString().isEmpty() || student.getF_roomId()==null){
            return new MappingJacksonValue("学生姓名或房间号不能为空");
        }
        //调用查询方法
        List<Student> student1=userService.findStudentQ(student);
        return student1;
    }
    /**换房间**/
    @RequestMapping(value = "/ModifyRoom.do",method = {RequestMethod.POST})
    public  @ResponseBody Object ModifyRoom(Student student){
        //判断是否输入空格
        if(student.getF_newRoom()==null){
            return new MappingJacksonValue("新房间不能为空格");
        }
        //调用修改方法
        String s=userService.ModifyRoom(student);
        if(s.equals("要修改的房间不存在") || s.equals("修改失败")){
            return new MappingJacksonValue("需要调换的房间不存在或修改失败");
        }else{
            return new MappingJacksonValue("转房成功");
        }
    }
    /**退房间**/
    @RequestMapping(value = "/Quit.do",method = {RequestMethod.POST})
    public  @ResponseBody Object StudentQuit(Student student){
        //调用业务层方法
            boolean isOk=userService.StudentQuit(student);
            if(isOk){
                return  new MappingJacksonValue("退房成功");
            }else {
                return  new MappingJacksonValue("退房失败");
            }
    }

    /**计算房间总条数*/
    @RequestMapping(value = "/totalPageofRoom.do",method = {RequestMethod.POST})
    public  @ResponseBody Object totalPageofRoom(){
        return userService.RoomCount();
    }
    /**分页查找房间*/
    @RequestMapping(value = "/RoomM.do",method = {RequestMethod.POST})
    public @ResponseBody Object  RoomM(Page page){
        //调用service方法
        List<Room> rooms=userService.findRoom(page);
            return  rooms;
    }
    /**删除房间*/
    @RequestMapping(value = "/DeletRoom.do",method = {RequestMethod.POST})
    public @ResponseBody Object DeletRoom (Room room){
            //调用 业务层删除方法
            boolean isOk=userService.DeletRoom(room);
            if(isOk){
                return new MappingJacksonValue("删除房间成功");
            }else{
                return new MappingJacksonValue("删除房间失败");
            }

    }
    /**查找房间*/
        @RequestMapping(value = "/SelectRoomQ.do",method = {RequestMethod.POST})
    public @ResponseBody Object SelectRoomQ(Room room){
            //判断房间地址是否为空格
            if(room.getF_roomAddress().trim().isEmpty()){
                return new MappingJacksonValue("房间地址不能为空格");
            }else{
                //判断房间状态是否为空如果为空则置空
                if(room.getF_status().trim().isEmpty()){
                    room.setF_status(null);
                }
                //执行业务层方法  判断返回值是否为空  如果为空 告知用户
                List<Room> rooms=userService.findRoomQ(room);
                return rooms;
            }
        }
        /**计算房间记录总条数*/
        @RequestMapping(value = "/totalPageofRoomF.do",method = {RequestMethod.POST})
    public @ResponseBody Object totalPageofRoomF(){
            return userService.totalPageofRoomF();
        }
        /**分页查找房间设备*/
        @RequestMapping(value = "/RoomF.do",method = {RequestMethod.POST})
    public @ResponseBody Object  findRoomF(Page  page){
            List<Roomfacility> roomFs=userService.findRoomF(page);
            return roomFs;
        }
        /**修改房间设备解决状态*/
        @RequestMapping(value = "/Change.do",method = {RequestMethod.POST})
    public @ResponseBody Object Changestatus(Roomfacility roomfacility){
            //看现在当前状态 将现在状态改为相反状态
            if(roomfacility.getF_resolveStatus().equals("已解决")){
                roomfacility.setF_resolveStatus("未解决");
            }else{
                roomfacility.setF_resolveStatus("已解决");
            }
            //调用业务层方法修改状态
            if(userService.ModifyFS(roomfacility)){
                return  new MappingJacksonValue("修改状态成功");
            }else {
                return  new MappingJacksonValue("修改状态失败");
            }
        }
        /**查询房间的 设备保修记录*/
        @RequestMapping(value = "/SelectRF.do",method = {RequestMethod.POST})
            public @ResponseBody Object SelectRF(Roomfacility roomfacility){
            if(roomfacility.getF_roomId()==null){
                return  new MappingJacksonValue("房间号不能为空格");
            }
            //调用查询方法
            List<Roomfacility> roomfacilities=userService.findRF(roomfacility);
            return roomfacilities;
        }
        /**查询班级总记录条数*/
        @RequestMapping(value = "/totalPageofClass.do",method = {RequestMethod.POST})
        public @ResponseBody  Object totalPageofClass(){
            return userService.totalPageofClass();
        }
        /**分页查询班级信息*/
        @RequestMapping(value = "/ClassF.do",method = {RequestMethod.POST})
            public @ResponseBody Object ClassF(Page page){
            return userService.findClass(page);
        }
        /**删除班级*/
        @RequestMapping(value = "/ClassDe.do",method = {RequestMethod.POST})
         public   @ResponseBody Object  ClassDe(Class class1){
            boolean isOk=userService.ClassDe(class1);
            if (isOk){
                return new MappingJacksonValue("删除成功");
            }else{
                return new MappingJacksonValue("删除失败，可能班里还有学生");
            }
        }
        /**计算班级表记录总条数*/
        @RequestMapping(value = "/totalPageofU.do",method = {RequestMethod.POST})
     public  @ResponseBody  Object totalPageofU(){
            return userService.Usercount();
        }
        /**分页查找用户数据*/
        @RequestMapping(value = "/UserD.do",method = {RequestMethod.POST})
    public @ResponseBody Object UserD(Page page){
            //查询数据
           List<User> users= userService.findUser(page);
            return users;
        }

        /**修改用户*/
        @RequestMapping(value = "/ChangUserG.do",method = {RequestMethod.POST})
        public @ResponseBody Object ChangUserG(User user){
            //调用查询方法
            boolean isOk=userService.ChangUserG(user);

                if(isOk){
                    return  new MappingJacksonValue("修改成功");
                }else{
                    return  new MappingJacksonValue("修改失败");
                }
        }
        /**删除用户*/
        @RequestMapping(value = "/UserDele.do",method = {RequestMethod.POST})
        public @ResponseBody Object UserDele(User user){
            //调用删除方法
            boolean isOk=userService.UserDele(user);
            if(isOk){
                return  new MappingJacksonValue("删除成功");
            }else{
                return new MappingJacksonValue("删除失败");
            }
        }
}
