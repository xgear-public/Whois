package by.xgear.whois.entity;

import com.google.gson.annotations.SerializedName;

public class Image {

	@SerializedName(value = "#text")
	private String url;
	
	private String size;
	
	public enum ImageSize{
		LARGE(2,"LARGE"),
		MEDIUM(1,"MEDIUM"),
		SMALL(0,"SMALL"),
		EXTRALARGE(3,"EXTRALARGE");
		
		private int priority;
		private String name;
		private ImageSize(int priority, String name) {
			this.priority = priority;
			this.name = name;
		}
		
		public static int getPriorityBySize(String size) {
			for(ImageSize enSize : ImageSize.values()) {
				if(enSize.getName().equalsIgnoreCase(size))
					return enSize.getPriority();
			}
			return 0;
		}

		public int getPriority() {
			return priority;
		}

		public void setPriority(int priority) {
			this.priority = priority;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}

	public Image() {
		super();
	}

	public Image(String url, String size) {
		super();
		this.url = url;
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	
}
