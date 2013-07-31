package by.xgear.whois.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Person {

	private String name;
	private String url;

	@SerializedName(value = "image")
	private List<Image> images;
	
	public Person() {
		super();
	}

	public Person(String name, String url, List<Image> images) {
		super();
		this.name = name;
		this.url = url;
		this.images = images;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
	
	
}
