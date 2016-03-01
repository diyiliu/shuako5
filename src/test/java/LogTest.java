import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: LogTest
 * Author: DIYILIU
 * Update: 2016-02-22 17:14
 */
public class LogTest {


    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Test
    public void testLog(){

        logger.info("hello logback!");
    }
}
