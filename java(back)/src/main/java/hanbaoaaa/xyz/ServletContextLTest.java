package hanbaoaaa.xyz;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletContextLTest implements ServletContextListener {

    // 实现其中的销毁函数

    public void contextDestroyed(ServletContextEvent sce) {

        System.out.println("项目已终止");

    }

    // 实现其中的初始化函数，当有事件发生时即触发

    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("项目刚启动");
        new Thread(){
            public void run(){
                NoteManager noteManager=NoteManager.getInstance();
            }
        }.start();


    }



}