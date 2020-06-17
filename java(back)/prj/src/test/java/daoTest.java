import bishe.menu.dao.DramaInfoDao;
import bishe.menu.dao.UserDao;
import bishe.menu.dao.impl.DramaInfoDaoImpl;
import bishe.menu.dao.impl.UserDaoImpl;
import bishe.menu.service.IsRegisterService;
import bishe.menu.service.UserService;
import bishe.menu.service.impl.IsRegisterServiceImpl;
import bishe.menu.service.impl.UserServiceImpl;
import org.junit.Test;

public class daoTest {
    @Test
    public void test1(){
        UserDao userDao = new UserDaoImpl();
        System.out.println(userDao.findUserByUsername("6"));
    }
    @Test
    public void test2(){
        UserService userService = new UserServiceImpl();
//        userService
        IsRegisterService isRegisterService = new IsRegisterServiceImpl();
//        isRegisterService.addusername("4","5");
//        User user = userService.login("4","5");
//        System.out.println(user.getId());
//        User user = userService.findUserByUsername("5");
//        System.out.println(user.getId());
        System.out.println(userService.userFile(1));
//        User user = userService.userDetail(1);
//        user.setTel("110010001");
//        userService.updateUser(user);
//        System.out.println(userService.userDetail(1).getTel());
    }
    @Test
    public void test3(){
        DramaInfoDao dramainfoDao = new DramaInfoDaoImpl();
        System.out.println(dramainfoDao.FindAllDrama());

    }
}
