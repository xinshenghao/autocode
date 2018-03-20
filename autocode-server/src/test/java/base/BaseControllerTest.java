package base;

import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.testng.annotations.BeforeClass;
import org.unitils.UnitilsTestNG;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;


@SpringApplicationContext({"classpath*:test-spring-mvc.xml", "classpath*:test-spring-mybatis.xml", "classpath*:test-spring-service.xml", "classpath*:test-spring.xml"})
public class BaseControllerTest extends UnitilsTestNG{
	@SpringBeanByType
	private RequestMappingHandlerAdapter handlerAdapter;
	
	
	@BeforeClass
	public void before(){
		
	}
	
}
