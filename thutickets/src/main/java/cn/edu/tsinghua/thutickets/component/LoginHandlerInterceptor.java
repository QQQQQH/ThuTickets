package cn.edu.tsinghua.thutickets.component;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        Object username = request.getSession().getAttribute("username");
        if (username != null && username.toString().equals("admin")) return true;
        else {
            request.setAttribute("msgWarning", "请先登录");
            request.getRequestDispatcher("/admin/login").forward(request, response);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {}
}
