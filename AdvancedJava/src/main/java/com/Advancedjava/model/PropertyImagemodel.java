package com.Advancedjava.model;

public class PropertyImagemodel {
	private int imageId;
	private int propertyId;
	public int getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}
	private String fileName;
	private String imageName;
	public int getImageId() {
		return this.imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public String getFileName() {
		return this.fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getImageName() {
		return this.imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public PropertyImagemodel(int imageId, int propertyId, String fileName, String imageName) {
		super();
		this.imageId = imageId;
		this.propertyId = propertyId;
		this.fileName = fileName;
		this.imageName = imageName;
	}
	public PropertyImagemodel(int propertyId, String fileName, String imageName) {
		super();
		this.propertyId = propertyId;
		this.fileName = fileName;
		this.imageName = imageName;
	}

}
