InternalResourceViewResolver

1.处理器返回逻辑视图时（也就是return “string”），要经过视图解析器链，前面的解析器能处理的，就不会继续往下传播。
如果不能处理就要沿着解析器链继续寻找，直到找到合适的视图解析器。

2.order表示视图解析的优先级，数目越小优先级越大（即：0为优先级最高，所以优先进行处理视图）

3.对于解析器InternalResourceViewResolver来说，不管能不能解析它都不会返回null，也就是说它拦截了所有的逻辑视图，
让后续的解析器得不到执行，所以InternalResourceViewResolver必须放在最后。


 所有的We MVC框架都有一套它自己的解析视图的机制，Spring MVC也不例外，它使用ViewResolver进行视图解析，让用户在浏览器中渲染模型。
 ViewResolver是一种开箱即用的技术，能够解析JSP、Velocity模板和XSLT等多种视图。
 Spring处理视图最重要的两个接口是ViewResolver和View。ViewResolver接口在视图名称和真正的视图之间提供映射； 而View接口则处理请求将真正的视图呈现给用户。



    1.几种常见的ViewResolver视图解析器

在Spring MVC 4控制器中，所有的处理方法必须返回一个逻辑视图名称，无论是显式的（返回String，View或ModelAndView）还是隐式的。Spring中的视图由视图解析器处理这个逻辑视图名称，Spring有以下几种视图解析器：

AbstractCachingViewResolver：用来缓存视图的抽象视图解析器。通常情况下，视图在使用前就准备好了。继承改解析器就能够使用视图缓存。
XmlViewResolver ：XML视图解析器。它实现了ViewResolver接口，接受相同DTD定义的XML配置文件作为Spring的XML bean工厂。

ResourceBundleViewResolver：它使用了ResourceBundle定义下的bean，实现了ViewResolver接口，指定了绑定包的名称。通常情况下，配置文件会定义在classpath下的properties文件中，默认的文件名字是views.properties。
UrlBasedViewResolver：它简单实现了ViewResolver接口，它不用显式定义，直接影响逻辑视图到URL的映射。它让你不用任何映射就能通过逻辑视图名称访问资源。
InternalResourceViewResolver：国际化视图解析器。
VelocityViewResolver /FreeMarkerViewResolver：Velocity或FreeMarker视图解析器。
ContentNegotiatingViewResolver：内容谈判视图解析器

  在JSP视图技术中，Spring MVC经常会使用 UrlBasedViewResolver视图解析器，该解析器会将视图名称翻译成URL并通过RequestDispatcher处理请求后渲染视图。

<bean id="viewResolver"        class="org.springframework.web.servlet.view.UrlBasedViewResolver">    <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>    <property name="prefix" value="/WEB-INF/views/"/>    <property name="suffix" value=".jsp"/></bean>
    假如我们配置了如上所示的URL视图解析器，我们返回了一个叫“favmvc”的视图名称，视图解析器就会将请求转发到RequestDispatcher，然后跳转到/WEB-INF/views/favmvc.jsp页面。

    假如我们想要在应用中使用不同的视图技术，我们就应该使用 ResourceBundleViewResolver。
<bean id="viewResolver"        class="org.springframework.web.servlet.view.ResourceBundleViewResolver">    <property name="basename" value="views"/>    <property name="defaultParentView" value="parentView"/></bean>

    ResourceBundleViewResolver对于每个要处理的视图，都会检查 ResourceBundle中basename的唯一性，它使用 [viewname].(class)作为视图类，[viewname].url作为视图的url。


   2. 链式视图解析器（Chaining ViewResolvers）
       Spring支持同时配置多个视图解析器，也就是链式视图解析器。这样，在某些情况下，就能够重写某些视图。如果我们配置了多个视图解析器，并想要给视图解析器排序的话，设定 order 属性就可以指定解析器执行的顺序。order的值越高，解析器执行的顺序越晚。

    <bean id="FMViewResolver" class="org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.freemarker.FreeMarkerView"/>
        <property name="viewNames" value="*html" />
        <property name="contentType" value="text/html; charset=utf-8"/>
        <property name="cache" value="true" />
        <property name="prefix" value="/" />
        <property name="suffix" value="" />
        <property name="order" value="0"></property>
    </bean>

    <!-- jsp jstl -->
    <bean id="JSPViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
        <property name="viewNames" value="*jsp" />
        <property name="contentType" value="text/html; charset=utf-8"/>
        <property name="prefix" value="/" />
        <property name="suffix" value="" />
        <property name="order" value="1"></property>
    </bean>

    	<bean id="viewResolverCommon" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    		<property name="prefix" value="/WEB-INF/page/"/>
    		<property name="suffix" value=".jsp"/><!--可为空,方便实现自已的依据扩展名来选择视图解释类的逻辑  -->
    		<property name="viewClass">
    			<value>org.springframework.web.servlet.view.InternalResourceView</value>
    		</property>
    		<property name="order" value="1"/>
    	</bean>
    	

    系统首先按解析器的order值进行查找 首先使用FreeMarkerViewResolver解析器调用canHandle方法，判断当前解析器对视图是否能够解析。如不能解析在依次调用。
    使用其viewNames属性，来指定一个是否可以处理的规则
    <property name= "viewNames" value= "html*" />
    <property name= "viewNames" value= "report*" />
    如上两个的意思是对html或report开头的视图名进行处理，比如返回的视图名为html/a 那么会交给viewNames为html*的那个解析器处理；支持的规则可以查看javadoc。


    3.  重定向视图
    在controller控制器中强制重定向的方法就是创建并返回Spring的RedirectView实例。在这种情况下，DispatcherServlet不再使用正常的视图机制，因为它已经返回了重定向视图，DispatcherServlet只是告诉视图去显示。
    RedirectView会调用 HttpServletResponse.sendRedirect()方法，然后它就作为HTTP重定向返回给客户端浏览器。默认情况下，所有的模板属性变量都认为是重定向URL，其余的属性自动附加为查询参数。