package hanbaoaaa.xyz;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletContextLTest implements ServletContextListener {

    // ʵ�����е����ٺ���

    public void contextDestroyed(ServletContextEvent sce) {

        System.out.println("��Ŀ����ֹ");

    }

    // ʵ�����еĳ�ʼ�������������¼�����ʱ������

    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("��Ŀ������");
        new Thread(){
            public void run(){
                NoteManager noteManager=NoteManager.getInstance();
            }
        }.start();


    }



}