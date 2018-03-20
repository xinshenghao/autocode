package base;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.unitils.UnitilsTestNG;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

@SpringApplicationContext({"classpath*:test-spring-service.xml","classpath*:test-spring-mybatis.xml"})
public class BaseServiceTest extends UnitilsTestNG{
	
	@BeforeClass
	public void init(){
		
	}
	

}
