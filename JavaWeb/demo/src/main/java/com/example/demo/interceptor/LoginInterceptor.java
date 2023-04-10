package com.example.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandele(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求的url
        String url = request.getRequestURI();
        // 判断url是否是公开地址（实际使用时将公开地址配置到配置文件中）
        // 这里公开地址是登录提交的地址
        if(url.indexOf("login.action") >= 0) {
            // 如果进行登录提交，放行
            return true;
        }
        // 判断session
        Object user = request.getSession().getAttribute("user");
        if(user != null) {
            // session不为空，放行
            return true;
        }
        // 执行到这里表示用户身份需要验证，跳转到登录页面
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        return false;
    }
}
