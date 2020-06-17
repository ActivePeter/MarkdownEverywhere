package bishe.menu.service;

public interface IsRegisterService {
    /**
     * 检查该用户名是否已经存在
     * @param username
     * @return
     */
    Boolean isRegister(String username);

    /**
     * mysql增加用户&&redis增加用户名
     * @param username
     */
    void addusername(String username, String password);
}
