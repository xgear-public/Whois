package by.xgear.whois.entity;

public class UserInfoRoot {

	private UserInfo user;

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public UserInfoRoot() {
		super();
	}

	public UserInfoRoot(UserInfo user) {
		super();
		this.user = user;
	}

}
