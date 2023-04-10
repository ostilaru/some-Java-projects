
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.pojo.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class MyBatisDemo {
    public static void main(String[] args) throws IOException {
        // 1. 加载mybatis核心配置文件， 获取sqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 2. 获取SqlSession对象，用它来执行对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 3. 执行sql语句
        List<Student> students = sqlSession.selectList("test.selectAll");
        System.out.println(students);

        // 4. 释放资源
        sqlSession.close();
    }
}
