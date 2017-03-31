package org.spring.swagger.mvc.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.swagger.mvc.po.UserInfo;
import org.spring.swagger.mvc.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    
    private static List<UserInfo> allUsers = new ArrayList<UserInfo>();
    
    static
    {
        UserInfo user = null;
        for (int i = 0; i < 10; i++)
        {
            user = new UserInfo((i+1), "name", i);
            allUsers.add(user);
        }
    }

    /**
     * @see com.spg.apidoc.service.UserService#addUser(com.spg.apidoc.po.UserInfo)
     */
    public int addUser(UserInfo user)
    {
        LOGGER.debug(String.format("enter function"));
        allUsers.add(user);
        LOGGER.debug(String.format("exit function"));
        return 1;
    }

    /**
     * @see com.spg.apidoc.service.UserService#deleteUser(int)
     */
    public int deleteUser(int id)
    {
        LOGGER.debug(String.format("enter function"));
        LOGGER.debug(String.format("exit function"));
        return 0;
    }

    /**
     * @see com.spg.apidoc.service.UserService#updateUser(int,
     *      com.spg.apidoc.po.UserInfo)
     */
    public int updateUser(int id, UserInfo user)
    {
        LOGGER.debug(String.format("enter function"));
        LOGGER.debug(String.format("exit function"));
        return 0;
    }

    public UserInfo queryUserById(int id)
    {
        LOGGER.debug(String.format("enter function"));
        UserInfo user = allUsers.get(id);
        LOGGER.debug(String.format("exit function, %s", user));
        return user;
    }

	@Override
	public UserInfo queryUserByName(String name) {
		LOGGER.debug(String.format("enter function"));
        UserInfo user = new UserInfo();
        user.setId(1000);
        user.setName(name);
        user.setAge(18);
        user.setPasswd("123456");
        LOGGER.debug(String.format("exit function, %s", user));
        return user;
	}
}
