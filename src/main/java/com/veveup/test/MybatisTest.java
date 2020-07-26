package com.veveup.test;

import com.veveup.dao.AccountDao;
import com.veveup.domain.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {

    @Test
    public void test() throws IOException {
        // 加载配置文件
        InputStream in = Resources.getResourceAsStream("mybatis.xml");
        // 陈建factory对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        // 创建 Sqlsession 对象
        SqlSession session = factory.openSession();
        //获取代理对象
        AccountDao dao = session.getMapper(AccountDao.class);
        //查询所有数据
        List<Account> all = dao.findAll();
        for(Account i:all){
            System.out.println(i);
        }
        //关闭资源
        session.close();
        in.close();
    }
}
