package by.xgear.whois.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Input {

	@SerializedName(value = "user")
	private List<Person> users;

	public List<Person> getUsers() {
		return users;
	}

	public void setUsers(List<Person> users) {
		this.users = users;
	}
	
}
