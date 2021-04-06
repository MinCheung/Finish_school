package com.mincheung.dao;

import com.mincheung.Responses.Page;
import com.mincheung.domain.Class;
import com.mincheung.domain.Room;
import com.mincheung.domain.Roomfacility;
import com.mincheung.domain.Student;
import com.mincheung.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**用户dao*/

@Repository
public interface UserDao {
    /**login*/
    public User findLogin(User user);
    /**修改用户密码*/
    public Integer ModifyPsw(User user);
    /**通过真实姓名和密码旧密码*/
    public  String findUserOldPsw(User user);
    /**学生入住*/
    public Integer studentCheckIn(Student student);
    /**添加房间*/
    public  Integer AddRoom(Room room);
    /**通过学生查找房间编号*/
    public Room findRoomforS(Student student);
    /**通过设施查找房间编号*/
    public Room findRoomforF(Roomfacility roomfacility);
    /**添加设施损坏记录*/
    public Integer AddFacility(Roomfacility roomfacility);
    /**添加班级*/
    public Integer AddClass(Class c);
    /**添加用户*/
    public Integer AddUser(User user);
    /**计算学生表总数*/
    public Long studentTotal();
    /**分页查找学生信息*/
    public List<Student> findStudent(Page page);
    /**条件查询学生*/
    public List<Student> findStudentQ(Student student);
    /**学生房间，修改房间编号*/
    public Integer ModifyRoom(Student student);
    /**查询新房间是否存在*/
    public Room findRoomforSNew(Student student);
    /**学生退房*/
    public Integer StudentQuit(Student student);
    /**计算房间总条数*/
    public Long RoomCount();
    /**分页查找房间*/
    public  List<Room> findRoom(Page page);
    /**删除房间*/
    public Integer DeletRoom(Room room);
    /**通过房间号查询是否还有学生关联*/
    public List<Student> findSFormR(Room room);
    /**通过房间号查询是否还有设备在房间内部*/
    public List<Roomfacility> findRFfromR(Room room);
    /**条件查询房间*/
    public  List<Room> findRoomQ(Room room);
    /**查找房间总记录条数*/
    public Long totalPageofRoomF();
    /**分页查找房间设备*/
    public  List<Roomfacility> findRoomF(Page page);
    /**修改报修房间设备状态*/
    public Integer ModifyFS(Roomfacility roomfacility);
    /**通过房间编号，查找报修记录*/
    public  List<Roomfacility> findRF(Roomfacility roomfacility);
    /**查询班级记录总条数*/
    public  Long totalPageofClass();
    /**分页查找班级*/
    public  List<Class> findClass(Page page);
    /**删除班级*/
    public  Integer ClassDe(Class class1);
    /**通过班级ID查找学生*/
    public  List<Student> findSFC(Class class1);
    /**计算用户记录总条数*/
    public Long  Usercount();
    /**分页查找用户数据*/
    public List<User> findUser(Page page);
    /**修改用户角色**/
    public Integer ChangUserG(User user);
    /**删除用户*/
    public  Integer UserDele(User user);
 }
