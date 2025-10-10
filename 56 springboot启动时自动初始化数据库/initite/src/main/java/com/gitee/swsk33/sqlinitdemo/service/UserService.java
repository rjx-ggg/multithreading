package com.gitee.swsk33.sqlinitdemo.service;

import com.gitee.swsk33.sqlinitdemo.dao.UserDAO;
import com.gitee.swsk33.sqlinitdemo.dataobject.User;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 启动时需要查询数据库的Beans
 */
@Slf4j
@Component
// 使用@DependsOn注解表示当前类依赖于名为databaseInitialize的Bean
// 这样可以使得databaseInitialize这个Bean（我们的数据库检查类）先被初始化，并执行完成数据库初始化后再初始化本类，以顺利访问数据库
@DependsOn("databaseInitialize")
public class UserService {

	@Autowired
	private UserDAO userDAO;

	@PostConstruct
	private void init() {
		log.info("执行数据库测试访问...");
		User user = new User();
		user.setUsername("用户名");
		user.setPassword("密码");
		userDAO.insert(user);
		List<User> users = userDAO.selectAll();
		for (User eachUser : users) {
			System.out.println(eachUser);
		}
	}

}