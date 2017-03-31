package org.spring.swagger.mvc.service;

import org.spring.swagger.mvc.po.UserInfo;

public interface UserService {

    int addUser(UserInfo user);

    int deleteUser(int id);

    int updateUser(int id, UserInfo user);
    
    UserInfo queryUserById(int id);
    
    UserInfo queryUserByName(String name);
}
