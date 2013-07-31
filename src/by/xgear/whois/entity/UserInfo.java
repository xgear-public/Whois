package by.xgear.whois.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class UserInfo {

	private String name;
	private String realname;

	@SerializedName(value = "image")
	private List<Image> images;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public UserInfo(String name, String realname, List<Image> images) {
		super();
		this.name = name;
		this.realname = realname;
		this.images = images;
	}

	public UserInfo() {
		super();
	}

}
