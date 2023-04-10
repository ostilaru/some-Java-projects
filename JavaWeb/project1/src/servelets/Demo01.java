package servelets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Demo01 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取session, 如果获取不到，就创建一个新的
        HttpSession session = req.getSession();
        System.out.println("Last Accessed Time is " + session.getLastAccessedTime()); // 获取最后一次访问时间
        System.out.println("session id is " + session.getId());
    }
}
