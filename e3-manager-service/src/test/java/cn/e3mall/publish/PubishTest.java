package cn.e3mall.publish;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PubishTest {

	@Test
	public void publish() throws Exception {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		System.out.println("服务已经启动。。。。");
		System.in.read();
		System.out.println("服务已经关闭");
	}
	
}
