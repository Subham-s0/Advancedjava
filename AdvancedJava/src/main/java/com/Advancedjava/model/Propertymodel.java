package com.Advancedjava.model;

import java.util.ArrayList;
import java.util.List;

public class Propertymodel {
	private int propertyId;
    private String propertyName;
    private String propertyAddress;
    private String propertyCity;
    private String propertyCountry;
    private String propertyDescription;
    private PropertyStatus propertyStatus;
    private double pricePerNight;
    private int maximumGuests;
    private int totalBedrooms;
    private int totalBath;
    private int totalRooms;
    private int categoryId;
    private String hostName;
    private List<PropertyImagemodel> images = new ArrayList<>();
    
    
    
    
    public Propertymodel(int propertyId, String propertyName, String propertyAddress, String propertyCity,
			String propertyCountry, String propertyDescription, PropertyStatus propertyStatus, double pricePerNight,
			int maximumGuests, int totalBedrooms, int totalBath, int totalRooms, int categoryId, String hostName,
			List<PropertyImagemodel> images) {
		this.propertyId = propertyId;
		this.propertyName = propertyName;
		this.propertyAddress = propertyAddress;
		this.propertyCity = propertyCity;
		this.propertyCountry = propertyCountry;
		this.propertyDescription = propertyDescription;
		this.propertyStatus = propertyStatus;
		this.pricePerNight = pricePerNight;
		this.maximumGuests = maximumGuests;
		this.totalBedrooms = totalBedrooms;
		this.totalBath = totalBath;
		this.totalRooms = totalRooms;
		this.categoryId = categoryId;
		this.hostName = hostName;
		this.images = images;
	}
    


	public Propertymodel(int propertyId, String propertyName, String propertyAddress, String propertyCity,
			String propertyCountry, PropertyStatus propertyStatus, double pricePerNight, int categoryId,
			List<PropertyImagemodel> images) {
		super();
		this.propertyId = propertyId;
		this.propertyName = propertyName;
		this.propertyAddress = propertyAddress;
		this.propertyCity = propertyCity;
		this.propertyCountry = propertyCountry;
		this.propertyStatus = propertyStatus;
		this.pricePerNight = pricePerNight;
		this.categoryId = categoryId;
		this.images = images;
	}



	public int getPropertyId() {
		return propertyId;
	}


	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}


	public String getPropertyName() {
		return propertyName;
	}


	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}


	public String getPropertyAddress() {
		return propertyAddress;
	}


	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}


	public String getPropertyCity() {
		return propertyCity;
	}


	public void setPropertyCity(String propertyCity) {
		this.propertyCity = propertyCity;
	}


	public String getPropertyCountry() {
		return propertyCountry;
	}


	public void setPropertyCountry(String propertyCountry) {
		this.propertyCountry = propertyCountry;
	}


	public String getPropertyDescription() {
		return propertyDescription;
	}


	public void setPropertyDescription(String propertyDescription) {
		this.propertyDescription = propertyDescription;
	}


	public PropertyStatus getPropertyStatus() {
		return propertyStatus;
	}


	public void setPropertyStatus(PropertyStatus propertyStatus) {
		this.propertyStatus = propertyStatus;
	}


	public double getPricePerNight() {
		return pricePerNight;
	}


	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}


	public int getMaximumGuests() {
		return maximumGuests;
	}


	public void setMaximumGuests(int maximumGuests) {
		this.maximumGuests = maximumGuests;
	}


	public int getTotalBedrooms() {
		return totalBedrooms;
	}


	public void setTotalBedrooms(int totalBedrooms) {
		this.totalBedrooms = totalBedrooms;
	}


	public int getTotalBath() {
		return totalBath;
	}


	public void setTotalBath(int totalBath) {
		this.totalBath = totalBath;
	}


	public int getTotalRooms() {
		return totalRooms;
	}


	public void setTotalRooms(int totalRooms) {
		this.totalRooms = totalRooms;
	}


	public int getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}


	public String getHostName() {
		return hostName;
	}


	public void setHostName(String hostName) {
		this.hostName = hostName;
	}


	public List<PropertyImagemodel> getImages() {
		return images;
	}


	public void setImages(List<PropertyImagemodel> images) {
		this.images = images;
	}
	
	 public void addImage(PropertyImagemodel image) {
	        this.images.add(image);
	    }

	    public void removeImage(PropertyImagemodel image) {
	        this.images.remove(image);
	    }


	public enum PropertyStatus {
        AVAILABLE,
        NOT_AVAILABLE
    }
	@Override
	public String toString() {
	    return "Property ID: " + propertyId +
	           "\nName: " + propertyName +
	           "\nAddress: " + propertyAddress +
	           "\nCity: " + propertyCity +
	           "\nCountry: " + propertyCountry +
	           "\nDescription: " + propertyDescription +
	           "\nStatus: " + propertyStatus +
	           "\nPrice per Night: $" + pricePerNight +
	           "\nMax Guests: " + maximumGuests +
	           "\nBedrooms: " + totalBedrooms +
	           "\nBathrooms: " + totalBath +
	           "\nTotal Rooms: " + totalRooms +
	           "\nCategory ID: " + categoryId +
	           "\nHost Name: " + hostName +
	           "\nImages Count: " + (images != null ? images.size() : 0);
	}

}
