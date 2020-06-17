package bishe.menu.util;

import java.util.UUID;

public class UUIDUtils {


    public String Get_UUID(){   //获取一个UUID
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    public static byte[] getByte(String uuid){
        byte[] a=new byte[uuid.length()];
        for(int i=0;i<uuid.length();i++){
            a[i]=(byte)(uuid.charAt(i));
        }
        return a;
    }

}
