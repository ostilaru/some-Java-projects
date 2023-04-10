package servelets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddServelet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int i = Integer.parseInt(request.getParameter("num1"));
        int j = Integer.parseInt(request.getParameter("num2"));
        int k = i + j;
        PrintWriter out = response.getWriter();
        out.println("Result is " + k);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // POST 方式下设置编码，防止中文乱码
        // 设置编码这条语句必须在获取参数之前
        req.setCharacterEncoding("UTF-8");
        String name =  req.getParameter("fname");
        String price =  req.getParameter("price");
        String quantity =  req.getParameter("quantity");
        String description =  req.getParameter("description");

        System.out.println("fname is " + name);
        System.out.println("price is " + price);
        System.out.println("quantity is " + quantity);
        System.out.println("description is " + description);

    }
}

