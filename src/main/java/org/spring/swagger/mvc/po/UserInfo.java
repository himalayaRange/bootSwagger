package org.spring.swagger.mvc.po;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private static final long serialVersionUID = 5402516802630861439L;

    private int id;

    private String name;
    
    private String passwd;

    private int age;
    
    public UserInfo() {
    	
    }

	public UserInfo(int id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", name=" + name + ", passwd=" + passwd
				+ ", age=" + age + "]";
	}
    

}
