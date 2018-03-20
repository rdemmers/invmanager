package nl.roydemmers.invmanager.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@Column(name="username")
	@NotBlank(message="Username cannot be blank.")
	@Size(min=8, max=20, message="Username must be between 8 and 15 characters long")
	@Pattern(regexp = "^\\w{8,}$", message="Username can only consist of numbers, letters and underscore")
	private String username;
	@Column(name="password")
	@NotBlank
	@Pattern(regexp="^\\S+$")
	private String password;
	@Column(name="enabled")
	private boolean enabled = false;
	
	@Email
	@Column(name="email")
	private String email;
	@Column(name="authority")
	private String authority;
	
	public User() {
		
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
