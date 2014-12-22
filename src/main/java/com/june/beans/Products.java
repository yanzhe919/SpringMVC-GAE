package com.june.beans;

public class Products {
	
	private String brand;
	
	private String name;
	
	private String description;
	
	private String url;
	
	private String img_src;
	
	private String img_alt;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg_src() {
		return img_src;
	}

	public void setImg_src(String img_src) {
		this.img_src = img_src;
	}

	public String getImg_alt() {
		return img_alt;
	}

	public void setImg_alt(String img_alt) {
		this.img_alt = img_alt;
	}

	@Override
	public String toString() {
		return "Products [brand=" + brand + ", name=" + name + ", description="
				+ description + ", url=" + url + ", img_src=" + img_src
				+ ", img_alt=" + img_alt + "]";
	}
	
}
