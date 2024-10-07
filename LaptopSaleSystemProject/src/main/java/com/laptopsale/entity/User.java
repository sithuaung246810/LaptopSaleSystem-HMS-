package com.laptopsale.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 
	    @Column(nullable = false)
	    @NotEmpty(message = "Name is required.")
	    private String name;

	    @Column(unique = true, nullable = false)
	    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Email is required (eg. test@gmail.com)")
//	    @NotEmpty(message = "Email is required.")
	    private String email;

	  
	    @Size(min = 8, message = "Password must be at least 8 characters long.")
	    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+", message = "Password is required (must contain an uppercase letter, a lowercase letter, and a number)")
//	    @NotEmpty(message = "Password is required.")
	    private String password;
	    
	    
	    @Column(nullable = false)
	    @NotEmpty(message = "Address is required.")
	    private String address;
	    
	    @Column(nullable = false)
	    @Pattern(regexp = "^(09|959)-\\d{3}-\\d{6}$", message = "Phone number is required (eg. 959/09-987-873982)")
//	    @NotEmpty(message = "Phone number is required.")
	    private String phone;
	    
	    public String role;
	    
	    public String profileName;
	   

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;	
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String getProfileName() {
			return profileName;
		}

		public void setProfileName(String profileName) {
			this.profileName = profileName;
		}

	
	    
	    
    }