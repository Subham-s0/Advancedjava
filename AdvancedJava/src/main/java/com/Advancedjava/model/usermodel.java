package com.Advancedjava.model;

public class usermodel {

	private String userId;
	 private String userFirstName;
	    private String userLastName;
	    private String userEmail;
	    private String userName;
	    private String userDob;
	    private String userPhnNo;
	    private gender gender;
	    private String userPassword;
		private String userRole;
	    
		private String userProfilePicture;
	    private userStatus userStatus;
	    
	   

		

		

	

		public usermodel(String userFirstName, String userLastName, String userEmail, String userName, String userDob,
				String userPhnNo, com.Advancedjava.model.usermodel.gender gender, String userPassword, String userRole,
				String userProfilePicture, com.Advancedjava.model.usermodel.userStatus userStatus) {
			this.userFirstName = userFirstName;
			this.userLastName = userLastName;
			this.userEmail = userEmail;
			this.userName = userName;
			this.userDob = userDob;
			this.userPhnNo = userPhnNo;
			this.gender = gender;
			this.userPassword = userPassword;
			this.userRole = userRole;
			this.userProfilePicture = userProfilePicture;
			this.userStatus = userStatus;
		}

		public usermodel(String userId, String userFirstName, String userLastName, String userEmail, String userName,
				String userDob, String userPhnNo, com.Advancedjava.model.usermodel.gender gender, String userPassword, String userRole,
				String userProfilePicture, com.Advancedjava.model.usermodel.userStatus userStatus) {
			
			this.userId = userId;
			this.userFirstName = userFirstName;
			this.userLastName = userLastName;
			this.userEmail = userEmail;
			this.userName = userName;
			this.userDob = userDob;
			this.userPhnNo = userPhnNo;
			this.gender = gender;
			this.userPassword = userPassword;
			this.userRole = userRole;
			this.userProfilePicture = userProfilePicture;
			this.userStatus = userStatus;
		}

		public usermodel(String userFirstName, String userLastName, String userEmail, 
                String userName, String userDob, String userPhnNo,String userPassword ) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userDob = userDob;
        this.userPhnNo = userPhnNo;
        this.userPassword = userPassword;
    }
	    
	    public usermodel(String userFirstName, String userLastName, String userEmail, 
                String userName, String userDob, String userPhnNo,String userPassword,String userRole ) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userDob = userDob;
        this.userPhnNo = userPhnNo;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }
		public String getUserId() {
			return this.userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
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
		public String getuserRole() {
			return this.userRole;
		}
		public void setuserRole(String userRole) {
			this.userRole = userRole;
		}
		public String getUserProfilePicture() {
			return this.userProfilePicture;
		}
		public userStatus getUserStatus() {
			return this.userStatus;
		}

		public void setUserStatus(userStatus userStatus) {
			this.userStatus = userStatus;
		}

		public void setUserProfilePicture(String userProfilePicture) {
			this.userProfilePicture = userProfilePicture;
		}
		public enum userStatus {
	        active,
	        blocked
	    }
		public enum gender{
			male,female
		}
		public void display() {
			
		    System.out.println("User Details:");
		    System.out.println("Id is            : " + userId);
		    System.out.println("First Name       : " + userFirstName);
		    System.out.println("Last Name        : " + userLastName);
		    System.out.println("Email            : " + userEmail);
		    System.out.println("Gender           : " + gender);
		    System.out.println("Username         : " + userName);
		    System.out.println("Date of Birth    : " + userDob);
		    System.out.println("Phone Number     : " + userPhnNo);
		    System.out.println("Password         : " + userPassword);
		    System.out.println("Role             : " + userRole);
		    System.out.println("Profile Picture  : " + userProfilePicture);
		    System.out.println("Status           : " + userStatus);
		}
		public gender getGender() {
			return gender;
		}

		public void setGender(gender gender) {
			this.gender = gender;
		}
}
