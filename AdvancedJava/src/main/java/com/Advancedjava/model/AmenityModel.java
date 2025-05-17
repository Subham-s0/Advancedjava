package com.Advancedjava.model;

public class AmenityModel {
	   private int amenityId;
	    private String amenityName;
	    private String amenityDescription;
	    private String amenityIcon;
	    
	    
	    
		public AmenityModel(int amenityId, String amenityName, String amenityDescription, String amenityIcon) {
			this.amenityId = amenityId;
			this.amenityName = amenityName;
			this.amenityDescription = amenityDescription;
			this.amenityIcon = amenityIcon;
		}
		public AmenityModel(String amenityName, String amenityDescription, String amenityIcon) {
			
			this.amenityName = amenityName;
			this.amenityDescription = amenityDescription;
			this.amenityIcon = amenityIcon;
		}
		public int getAmenityId() {
			return this.amenityId;
		}
		public void setAmenityId(int amenityId) {
			this.amenityId = amenityId;
		}
		public String getAmenityName() {
			return this.amenityName;
		}
		public void setAmenityName(String amenityName) {
			this.amenityName = amenityName;
		}
		public String getAmenityDescription() {
			return this.amenityDescription;
		}
		public void setAmenityDescription(String amenityDescription) {
			this.amenityDescription = amenityDescription;
		}
		public String getAmenityIcon() {
			return this.amenityIcon;
		}
		public void setAmenityIcon(String amenityIcon) {
			this.amenityIcon = amenityIcon;
		}

	    
}
