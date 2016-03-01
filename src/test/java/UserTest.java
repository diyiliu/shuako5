import com.diyiliu.support.other.PaginationHelper;
import com.diyiliu.web.dao.UserDao;
import com.diyiliu.web.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: UserTest
 * Author: DIYILIU
 * Update: 2016-02-19 14:13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserTest {

    @Resource
    private UserDao userDao;

    @Test
    public void insert(){

        User user = new User();

        userDao.insertEntity(user);
    }



    @Test
    public void testPage() {

        PaginationHelper.page(1, 3);
        List<User> list = userDao.findUserByPage("小明");

        System.out.println("总页数：" + PaginationHelper.getMaxPage());
    }

}
