package com.mincheung.test;

import com.mincheung.Responses.Page;
import com.mincheung.domain.User;
import com.mincheung.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class springtest {

    @Test
    public void Test1(){
        //创建加载配置文件创建容器
        ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
        //得到对象
        UserService user=(UserService) ac.getBean("UserService");
        //调用方法
        Page p=new Page();
        p.setCurrentPage(new Long(1));
        p.setPageSize(new Long(1));
        user.findStudent(p);
    }
}
