package com.iamhere.main_platform;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.iamhere.cache.RedisService;
import com.iamhere.entities.UserObject;
import com.iamhere.platform.func.DmlOperationWrapper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Platform test for UserObject
 * 
 * @author jassica
 *
 */
public class RedisServiceTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public RedisServiceTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(RedisServiceTest.class);
	}

	/**
	 * Create a new user and save to db
	 * 
	 * @throws InterruptedException
	 * 
	 * @throws Exception
	 */
	public void testRedisService() throws InterruptedException {
		// ApplicationContext app = new
		// ClassPathXmlApplicationContext("classpath:spring.xml");
		// RedisService redisService = (RedisService)
		// app.getBean("redisService");
		//
		// String ping = redisService.ping();//测试是否连接成功,连接成功输出PONG
		// System.out.println(ping);
		//
		// //首先,我们看下redis服务里是否有数据
		// long dbSizeStart = redisService.dbSize();
		// System.out.println(dbSizeStart);
		//
		// redisService.set("username", "oyhk");//设值(查看了源代码,默认存活时间30分钟)
		// String username = redisService.get("username");//取值
		// System.out.println(username);
		// redisService.set("username1", "oyhk1", 1);//设值,并且设置数据的存活时间(这里以秒为单位)
		// String username1 = redisService.get("username1");
		// System.out.println(username1);
		// Thread.sleep(2000);//我睡眠一会,再去取,这个时间超过了,他的存活时间
		// String liveUsername1 = redisService.get("username1");
		// System.out.println(liveUsername1);//输出null
		//
		// //是否存在
		// boolean exist = redisService.exists("username");
		// System.out.println(exist);
		//
		// //查看keys
		// Set<String> keys = redisService.keys("*");//这里查看所有的keys
		// System.out.println(keys);//只有username username1(已经清空了)
		//
		// //删除
		// redisService.set("username2", "oyhk2");
		// String username2 = redisService.get("username2");
		// System.out.println(username2);
		// redisService.del("username2");
		// String username2_2 = redisService.get("username2");
		// System.out.println(username2_2);//如果为null,那么就是删除数据了
		//
		// //dbsize
		// long dbSizeEnd = redisService.dbSize();
		// System.out.println(dbSizeEnd);
		//
		// //清空reids所有数据
		// //redisService.flushDB();
	}

	public void testRedisTemplate() {
		// ClassPathXmlApplicationContext context = new
		// ClassPathXmlApplicationContext("classpath:spring.xml");
		// RedisTemplate redisTemplate =
		// (RedisTemplate)context.getBean("jedisTemplate");
		// //其中key采取了StringRedisSerializer
		// //其中value采取JdkSerializationRedisSerializer
		// ValueOperations<String, User> valueOper =
		// redisTemplate.opsForValue();
		// User u1 = new User("zhangsan",12);
		// User u2 = new User("lisi",25);
		// valueOper.set("u:u1", u1);
		// valueOper.set("u:u2", u2);
		// System.out.println(valueOper.get("u:u1").getName());
		// System.out.println(valueOper.get("u:u2").getName());
		//
		//
		//
	}

	public void testPlatformUserCache() throws Exception {
//		ApplicationContext app = new ClassPathXmlApplicationContext(
//				"classpath:spring.xml");
//		RedisService redisService = (RedisService) app.getBean("redisService");
//		String ping = redisService.ping();// 测试是否连接成功,连接成功输出PONG
//		System.out.println(ping);
//		// Clean up all the cache
//		redisService.flushDB();

		UserObject user = new UserObject("test@hotmail.com");
		user = user.load();
		if (user != null) {
			user.setActiveScore(80000);
			DmlOperationWrapper dmlOperationState = user.save();
			assertTrue("Save should succeed", dmlOperationState.isBulkSuccess());
			assertEquals("User should be saved successfully",
					"552c8f59e221fd568dbf3d58", user.getId());
		}
	}
	
	public void testPlatformCreateWillSetCache() throws Exception {
//		 UserObject user = new UserObject("George", "admin",
//		 "test2@hotmail.com", "test1234");
//		 user.setActiveScore(10000);
//		 user.setAlias("gadmin");
//		 user.setCreditInfo(10000);
//		 user.setRole("admin");
//		 user.setEmailAuthorized(true);
//		 DmlOperationWrapper dmlOperationState = user.save();
//		 assertTrue("Save should succeed", dmlOperationState.isBulkSuccess());
//		 assertNotNull("User should be saved successfully", user.getId());
//		 System.out.println("Newly created user id is: " + user.getId());
//		 
//		 UserObject user2 = new UserObject("test2@hotmail.com");
//		user2 = user2.load();
	}

	/**
	 * 如果使用jdk序列化方式，bean必须实现Serializable，且提供getter/setter方法
	 * 
	 * @author qing
	 * 
	 */
	class User implements Serializable {
		/** 
         *  
         */
		private static final long serialVersionUID = -3766780183428993793L;
		private String name;
		private Date created;
		private int age;

		public User() {
		}

		public User(String name, int age) {
			this.name = name;
			this.age = age;
			this.created = new Date();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Date getCreated() {
			return created;
		}

		public void setCreated(Date created) {
			this.created = created;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

	}
}
