package com.Advancedjava.model;

public class usermodel {
	 private String userFirstName;
	    private String userLastName;
	    private String userEmail;
	    private String userName;
	    private String userDob;
	    private String userPhnNo;
	    private String userPassword;
	    
	    public usermodel(String userFirstName, String userLastName, String userEmail, 
                String userName, String userDob, String userPhnNo, 
                String userPassword) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userDob = userDob;
        this.userPhnNo = userPhnNo;
        this.userPassword = userPassword;
    }
	    
	    
		public String getUserFirstName() {
			return userFirstName;
		}
		public void setUserFirstName(String userFirstName) {
			this.userFirstName = userFirstName;
		}
		public String getUserLastName() {
			return userLastName;
		}
		public void setUserLastName(String userLastName) {
			this.userLastName = userLastName;
		}
		public String getUserEmail() {
			return userEmail;
		}
		public void setUserEmail(String userEmail) {
			this.userEmail = userEmail;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getUserDob() {
			return userDob;
		}
		public void setUserDob(String userDob) {
			this.userDob = userDob;
		}
		public String getUserPhnNo() {
			return userPhnNo;
		}
		public void setUserPhnNo(String userPhnNo) {
			this.userPhnNo = userPhnNo;
		}
		public String getUserPassword() {
			return userPassword;
		}
		public void setUserPassword(String userPassword) {
			this.userPassword = userPassword;
		}
}
