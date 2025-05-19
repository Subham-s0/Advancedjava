package com.Advancedjava.model;

public class Property_Amenity {
	private int propertyId;
	 private int amenityId;
	 
	public Property_Amenity(int propertyId, int amenityId) {
		
		this.propertyId = propertyId;
		this.amenityId = amenityId;
	}
	public int getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}
	public int getAmenityId() {
		return amenityId;
	}
	public void setAmenityId(int amenityId) {
		this.amenityId = amenityId;
	}

}
