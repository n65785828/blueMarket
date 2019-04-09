package top.study02.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.study02.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.*;

@Configuration
public class ProjectConfig {


    /**
     * 拦截未登录，转发至首页
     * @return
     */
    public HandlerInterceptor handlerInterceptor(){
        HandlerInterceptor handlerInterceptor = new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                if(request.getSession().getAttribute("user")!=null){
                    return true;
                }
                request.setAttribute("error","请先登录");
                request.getRequestDispatcher("login").forward(request,response);
                //response.sendRedirect("login.html");
                return false;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

            }
        };
        return handlerInterceptor;
    }

    /**
     * 供应商信息修改拦截器
     * 经理及以上职位具备修改和删除供应商信息
     * @return
     */
    public HandlerInterceptor providerEditHandlerInterceptor(){
        HandlerInterceptor handlerInterceptor = new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                User user = (User) request.getSession().getAttribute("user");
                if(user==null){
                    request.getRequestDispatcher("login").forward(request,response);
                    return false;
                }
                if(user.getUserlv().equals("普通用户")){
                    request.setAttribute("errorFlag","true");
                    request.setAttribute("error","您无权限修改该用户");
                    request.getRequestDispatcher("/providerList.html").forward(request,response);
                    return false;
                }
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

            }
        };
        return handlerInterceptor;
    }

    /**
     * 用户信息修改拦截器
     * 管理员有权限修改删除所有信息
     * 经理能修改删除普通用户的信息，能修改但不能彻底删除自己的信息
     * 普通用户只能修改自己的信息，不能删除信息
     * @return
     */
    public HandlerInterceptor userEdithandlerInterceptor(){
        HandlerInterceptor handlerInterceptor = new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                User user = (User) request.getSession().getAttribute("user");
                if(user==null){
                    request.setAttribute("error","请先登录");
                    request.setAttribute("error","您无权限修改该用户");
                    request.setAttribute("errorFlag","true");
                    request.getRequestDispatcher("login").forward(request,response);
                    return false;
                }
                if(user.getUserlv().equals("普通用户")){
                    String url = request.getRequestURL().toString();
                    String id = user.getId()+"";
                    if(id.equals(request.getParameter("id"))&&url.endsWith("userUpdate.html")){
                        return true;
                    }
                    request.setAttribute("error","您无权限修改该用户");
                    request.setAttribute("errorFlag","true");
                    request.getRequestDispatcher("/userList.html?pageSize=8").forward(request,response);
                    return false;
                }
                if(user.getUserlv().equals("管理员"))
                    return true;
                if(user.getUserlv().equals("经理")){//代表要修改的用户类型为普通用户
                    String param = request.getParameter("userLv");
                    String id = user.getId()+"";
                    final String pid = request.getParameter("id");
                    final String string = request.getRequestURL().toString();
                    System.out.println(string.endsWith("userUpdate.html"));
                    if(request.getParameter("userLv").equals("3")||id.equals(request.getParameter("id"))&&request.getRequestURL().toString().endsWith("userUpdate.html")){
                        return true;
                    }
                }
                request.setAttribute("errorFlag","true");
                request.setAttribute("error","您无权限修改该用户");
                request.getRequestDispatcher("/userList.html?pageSize=8").forward(request,response);
                return false;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

            }
        };
        return handlerInterceptor;
    }


    /**
     * 配置额外的视图解析器
     * 添加映射规则
     * @return
     */
    @Bean
    public WebMvcConfigurer myWebMvcConfigurer(){
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/login.html").setViewName("login");
                registry.addViewController("/login").setViewName("login");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(handlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/","/login","/check_login","/css/**","/img/**","/js/**","/login.html");
                List<String> userEditFunction = new ArrayList<>();
                userEditFunction.add("/userUpdate.html");
                userEditFunction.add("/delete");
                userEditFunction.add("/userAdd.html");
                registry.addInterceptor(userEdithandlerInterceptor()).addPathPatterns(userEditFunction);
                List<String> providerEditFunction = new ArrayList<>();
                providerEditFunction.add("/providerUpdate.html");
                providerEditFunction.add("/deleteProvider");
                providerEditFunction.add("/providerAdd.html");
                registry.addInterceptor(providerEditHandlerInterceptor()).addPathPatterns(providerEditFunction);
            }

        };
        return webMvcConfigurer;
    }


    /**
     * 配置druid数据源
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "druid")
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        return dataSource;
    }


    //配置Druid的监控
    /**
     * 1、配置一个管理后台的Servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),
                "/druid/*");
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "admin");
        initParams.put("loginPassword", "123456");
        initParams.put("allow", "");//默认就是允许所有访问
        //initParams.put("deny","192.168.15.21");//禁止某ip访问
        bean.setInitParameters(initParams);
        return bean;
    }

    /**
     * 2、配置一个web监控的filter
     * @return
     */
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.css,/druid/*,*.png,*.icon,*jpeg,*.jpg");
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }




}
