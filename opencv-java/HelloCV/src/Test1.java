import java.lang.reflect.Method;


public class Test1 {

	public static void main(String[] args) throws Exception{
		
		Class c = Class.forName("Test2");
		Class noparams[] = {};
		
		Object obj = c.newInstance();
		Method method = c.getDeclaredMethod("foo", noparams);
		method.invoke(obj, null);

	}

}
