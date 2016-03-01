import org.junit.Assume;
import org.junit.Test;

/**
 * Description: MatchTest
 * Author: DIYILIU
 * Update: 2016-02-19 11:41
 */
public class MatchTest {

    @Test
    public void test(){

        String str = "deleteEntity";
        String reg = ".*(add|delete|update)Entity$";
        Boolean flag = str.matches(reg);

        Assume.assumeTrue(flag);
    }
}
