import bishe.menu.domain.User;
import bishe.menu.util.TimeTransformer;
import bishe.menu.util.UUIDUtils;
import org.junit.Test;

import javax.servlet.http.HttpSession;

public class UUIDtest {
    @Test
    public void test1(){
        UUIDUtils uuidUtils = new UUIDUtils();
//        System.out.println(uuidUtils.Get_UUID());
        TimeTransformer timeTransformer = new TimeTransformer();
        System.out.println(timeTransformer.getNowTimeStamp());
    }


}