package com.xxzy.EXLG.interceptor;

import com.xxzy.EXLG.common.constant.SystemConstant;
import com.xxzy.EXLG.common.constant.UserConstant;
import com.xxzy.EXLG.entity.EmployeeEntity;
import com.xxzy.EXLG.entity.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @author gjq0117
 * @email gjq0117@163.com
 * @date 2022/4/23 上午 11:50
 * 员工登录的拦截器:
 */
@Component
public class LoginUserInterceptor implements HandlerInterceptor {

    public static ThreadLocal<EmployeeEntity> employeeInfo = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //进来先判断是否登录,即session中有没有数据,针对员工
        String uri = request.getRequestURI();
        // 用户发起的登录注册请求不拦截
        boolean matchLogin = new AntPathMatcher().match("/employee/empLogin", uri);
        boolean matchReg = new AntPathMatcher().match("/employee/regStageOrSQ", uri);


        if (matchLogin || matchReg){
            return true;
        }
        //  通过cookie判断是否是小程序发来的请求  小程序发来的请求一律不拦截
        Cookie[] cookies = request.getCookies();
        boolean flag = false;
        List<Cookie> cookiesList = Arrays.asList(cookies);
        for (Cookie cookie :
                cookiesList) {
            if ("exlg-wx".equals(cookie.getName())){    // 携带exlg-wx的请求都是小程序发过来的
                flag = true;
            }
        }
        if (flag){
            return true;
        }
        HttpSession session = request.getSession();
        EmployeeEntity employeeEntity = (EmployeeEntity) session.getAttribute(UserConstant.LOGIN_USER);  // 管理员登陆
        if (employeeEntity != null) {  //已经登录   放行
            employeeInfo.set(employeeEntity);
            return true;
        } else {
            response.sendRedirect("http://" + SystemConstant.EXLG_DOMAIN_NAME);
            return false;
        }
    }
}
