import org.junit.Test;

/**
 * Created by T on 2017/6/10.
 */
public class StringTest {

	@Test
	public void trimTest(){
		String str = "　/ kamome sano";
		System.out.println(str.substring(2).trim());
	}

}
