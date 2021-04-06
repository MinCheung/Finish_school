package com.mincheung.service.Impl;

import com.mincheung.Responses.Page;
import com.mincheung.dao.UserDao;
import com.mincheung.domain.Class;
import com.mincheung.domain.Room;
import com.mincheung.domain.Roomfacility;
import com.mincheung.domain.Student;
import com.mincheung.domain.User;
import com.mincheung.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**用户类业务实现层*/
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**登录对比查询*/
    @Override
    public User findLogin(User user) {
        User user1=userDao.findLogin(user);
        return user1;
    }
/**修改密码*/
    @Override
    public boolean ModifyPsw(User user) {
            //先比对账户旧密码是否吻合
            String PSW=userDao.findUserOldPsw(user);
            if(!PSW.equals(user.getOldPsw())){
                //如果旧密码密码不正确返回false
                return false;
            }else{
                //修改密码
               Integer isOk= userDao.ModifyPsw(user);
               if(isOk!=null){
                   return true;
               }else {
                   return false;
               }
            }
    }
    /**查询旧密码*/
    @Override
    public String findUserOldPsw(User user) {
        String OldPsw=userDao.findUserOldPsw(user);
        return OldPsw;
    }

    /**学生入住根据插入 查询的id来判断是否插入成功*/
    @Override
    public String studentCheckIn(Student student) {
        //查找输入的房间编号是否存在
        Room r=new Room();
        r=userDao.findRoomforS(student);
        if(r==null){
            return "房间编号不存在";
        }
        Student S=new Student();
        //设置对象得到返回的keyid值
        S.setStudentId(userDao.studentCheckIn(student));
        //插入成功
        if(S.getStudentId()!=null){
                return "插入成功";
        }else{
            //插入失败
            return "插入失败";
        }
    }
    /**添加房间*/
    @Override
    public boolean AddRoom(Room room) {
        Room r=new Room();
        //设置对象得到返回的keyID
        r.setRoomId(userDao.AddRoom(room));
        //如果不为空返回正确
        if(r.getRoomId()!=null){
            return  true;
        }
        //如果为空但会失败
        return false;
    }
    /**
     * 添加房间设施记录
     * */
    @Override
    public String AddFacility(Roomfacility roomfacility) {
        //查找房间编号是否存在
        Room r=new Room();
        r=userDao.findRoomforF(roomfacility);
        if(r==null){
            return "房间号不存在";
        }
        //调用dao层添加方法
        Integer Nmber=userDao.AddFacility(roomfacility);
        //判断返回内容是否为空
        if(Nmber!=null){
            return "插入成功";
        }else{
            return "插入失败";
        }

    }
    /**添加班级*/
    @Override
    public boolean AddClass(Class c) {
        //调用dao层方法接受返回值 如果返回值不为空则插入成功
        Integer in=userDao.AddClass(c);
        if(in!=null){
            return  true;
        }else{
            return false;
        }

    }

    @Override
    public boolean AddUser(User user) {
        //使用userdao调用增添方法 查看selectKey传回对象是否为空
        Integer test=userDao.AddUser(user);
        //不为空则插入成功
        if(test!=null){
            return true;
        }else {
            return false;
        }
    }
    /**计算学生表 总数*/
    @Override
    public Long studentTotal() {
        return userDao.studentTotal();
    }

    /**计算房间总数*/
    @Override
    public Long RoomCount() {
        return userDao.RoomCount();
    }

    //根据页数查询 学生信息
    @Override
    public List<Student> findStudent(Page page) {
        page.setBegining((page.getCurrentPage()-1)*page.getPageSize());
        //调用方法查询学生信息
        List<Student> students=userDao.findStudent(page);
        return students;
    }

    @Override
    public List<Student> findStudentQ(Student student) {
        //执行查询方法返回学生信息
       List<Student> student1= userDao.findStudentQ(student);
        return student1;
    }

    @Override
    public String ModifyRoom(Student student) {
        //先查找学生换房的房间是否存在
        Room r=new Room();
        r=userDao.findRoomforSNew(student);
        if(r!=null){
            System.out.println(r);
            Integer isOk=userDao.ModifyRoom(student);
            if(isOk!=null){
                return "修改成功";
            }else{
                return "修改失败";
            }
        }else{
            return "要修改的房间不存在";
        }
    }

    @Override
    public boolean StudentQuit(Student student) {
        //调用 学生删除方法
            Integer isOk=userDao.StudentQuit(student);
            //如果返回受影响行数不为空 就删除成功
            if(isOk!=null){
                return  true;
            }else{
                return false;
            }
    }
    /**分页查找数据*/
    @Override
    public List<Room> findRoom(Page page) {
        //调用 dao层查询方法 判断查询出是否为空
        //设置开始页
        page.setBegining((page.getCurrentPage()-1)*page.getPageSize());
        List<Room> rooms=userDao.findRoom(page);
        return rooms;
    }
    /**删除房间*/
    @Override
    public boolean DeletRoom(Room room) {
        //查找是否还有学生在使用房间
        List<Student>s=new ArrayList<Student>();
        List<Roomfacility> rf=new ArrayList<Roomfacility>();
        if((s=userDao.findSFormR(room))!=null){
            return  false;
        }
        //查找是否房间还有设备关联
        else if((rf=userDao.findRFfromR(room))!=null){
            return  false;
        }else{
        //如果以上两者都没有那么执行删除账号
        Integer isOk=userDao.DeletRoom(room);
        if(isOk!=null){
            return true;
        }else {
            return false;
        }
        }
    }
/**通过房间找学生*/
    @Override
    public List<Student> findSFormR(Room room) {
        return userDao.findSFormR(room);
    }
/**通过房间找房间设备是否有关联*/
    @Override
    public List<Roomfacility> findRFfromR(Room room) {
        return userDao.findRFfromR(room);
    }

    @Override
    public List<Room> findRoomQ(Room room) {
        List<Room> r=userDao.findRoomQ(room);
        return r;
    }

    @Override
    public Long totalPageofRoomF() {
        return userDao.totalPageofRoomF();
    }

    @Override
    public List<Roomfacility> findRoomF(Page page) {
        //设置开始页
        page.setBegining((page.getCurrentPage()-1)*page.getPageSize());
        //执行查找方法
        List<Roomfacility> roomF =userDao.findRoomF(page);
        return roomF;
    }

    @Override
    public boolean ModifyFS(Roomfacility roomfacility) {
        //调用修改方法
        Integer isOk=userDao.ModifyFS(roomfacility);
        //判断返回对象是否为空
        if(isOk!=null){
            return  true;
        }else {
            return false;
        }
    }
    /**通过房间号查询报修记录*/
    @Override
    public List<Roomfacility> findRF(Roomfacility roomfacility) {
        //调用dao层查询方法
        List<Roomfacility> roomfacilities=userDao.findRF(roomfacility);
        return roomfacilities;
    }

    /**查询班级总条数*/
    @Override
    public Long totalPageofClass() {
        return userDao.totalPageofClass();
    }
    /**分页查询班级记录*/
    @Override
    public List<Class> findClass(Page page) {
        //设置开始页
        page.setBegining((page.getCurrentPage()-1)*page.getPageSize());
        //调用dao层查询方法
       List<Class> classes= userDao.findClass(page);
        return classes;
    }
    /**删除班级*/
    @Override
    public boolean ClassDe(Class class1) {
        //判断班级中是否还有学生
       List<Student> students= userDao.findSFC(class1);
       if(students!=null){
           return false;
       }else {
           //调用userdao方法
           Integer isOk = userDao.ClassDe(class1);
           if (isOk != null) {
               return true;
           } else {
               return false;
           }
       }
    }
        /**通过班级ID查询学生*/
    @Override
    public List<Student> findSFC(Class class1) {
        return userDao.findSFC(class1);
    }
    /**计算用户记录总条数*/
    @Override
    public Long Usercount() {
        return userDao.Usercount();
    }

    @Override
    public List<User> findUser(Page page) {
        //设置开始页数
        page.setBegining((page.getCurrentPage()-1)*page.getPageSize());
        //查找数据
      List<User> users=userDao.findUser(page);
        return users;
    }

    /**修改用户等级*/
    @Override
    public boolean ChangUserG(User user) {
        //调用用修改方法  判断返回值
        Integer isOk=userDao.ChangUserG(user);
        if(isOk!=null){
            return  true;
        }else{
            return false;
        }

    }
    /**删除用户*/
    @Override
    public boolean UserDele(User user) {
        //调用删除
        Integer isOk=userDao.UserDele(user);
        if(isOk!=null){
            return true;
        }else{
            return false;
        }
    }

}
