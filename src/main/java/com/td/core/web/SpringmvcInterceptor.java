package com.td.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.td.common.web.session.SessionProvider;
import com.td.core.bean.user.Buyer;



/**
 * 处理上下文
 * 处理Session
 * 全局
 * @author lx
 *
 */
public class SpringmvcInterceptor implements HandlerInterceptor{

	@Autowired
	private SessionProvider sessionProvider;
	
	//常量
	private static final String INTERCEPTOR_URL = "/buyer/";//只拦截  此请求路径下的
	//方法前   /buyer/
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
			Buyer buyer = (Buyer) sessionProvider.getAttribute(request, "buyer_session");
			/*boolean flag = false;
			if(null != buyer){
				flag = true;
			}
			request.setAttribute("isLogin", flag);*/
			//是否拦截   http://localhost:8080/buyer/index.shtml
			//  /buyer/index.shtml
			/*System.out.println(request.getRealPath("/test"));//F:\apache-tomcat-7.0.52\webapps\teseproject\test
			System.out.println(request.getRequestURI());//   /teseproject/testRequest
*/			String requestURI = request.getRequestURI();//获得相对请求路径
			if(requestURI.startsWith(INTERCEPTOR_URL)){//如果请求包含/buyer/，，则 拦截
				//必须登陆
				if(null == buyer){
					response.sendRedirect("/shopping/login.shtml?returnUrl=" + request.getParameter("returnUrl"));
					return false;//lan拦截
				}
			}
		//}
		return true;//放行
	}

	//方法后
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	//页面渲染后
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	

}
