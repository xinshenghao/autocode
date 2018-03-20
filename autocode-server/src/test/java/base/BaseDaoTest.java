package base;

import org.testng.annotations.BeforeClass;
import org.unitils.UnitilsTestNG;
import org.unitils.spring.annotation.SpringApplicationContext;

@SpringApplicationContext({"classpath*:spring-mybatis.xml"})
public class BaseDaoTest extends UnitilsTestNG{  
	
    @BeforeClass
    public void before(){
    	
    }
    
    /*@Test
    @DataSet("StudentService.xls")
    public void test(){
    	User user=new User();
    	user.setUserId("e");
    	user.setUserName("admin");
    	user.setPassword("123546");
    	userDao.insertSelective(user);
    }*/
	
}  