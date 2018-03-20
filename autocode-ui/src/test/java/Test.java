import static org.junit.Assert.*;

import java.util.List;

import com.hitoo.general.folderscanner.FolderScanner;
import com.hitoo.general.folderscanner.rule.NoEndWithRule;

public class Test {

	@org.junit.Test
	public void test() {
		FolderScanner scanner = new FolderScanner("/home/xsh/autoCode/demo/src/main/java/com/example/demo/mybatis/domain");
		scanner.addRule(new NoEndWithRule("Example.java"));
		List<String> name = scanner.scan();
		for (String string : name) {
			System.out.println(string);
		}
	}

}
