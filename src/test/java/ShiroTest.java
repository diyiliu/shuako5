import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

/**
 * Description: ShiroTest
 * Author: DIYILIU
 * Update: 2016-02-26 17:14
 */
public class ShiroTest {

    @Test
    public void test(){

        String salt = "0072273a5d87322163795118fdd7c45e";
        String newPassword = new SimpleHash(
                "md5",
                "123",
                ByteSource.Util.bytes("liu" + salt),
                2).toHex();

        System.out.println(newPassword);
    }

}
