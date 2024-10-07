package com.laptopsale.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.laptopsale.Repository.TokenRepository;
import com.laptopsale.Repository.UserRepository;
import com.laptopsale.dto.UserDTO;
import com.laptopsale.entity.PasswordResetToken;
import com.laptopsale.entity.User;




@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	
	@Autowired
	UserRepository userRepository;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	TokenRepository tokenRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				new HashSet<GrantedAuthority>());
	}


	public User save(UserDTO userDTO) {		//Save data into Database
		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setName(userDTO.getName());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		return userRepository.save(user);
	}


	public String sendEmail(User user) {
		try {
			String resetLink = generateResetToken(user);
	
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom("sithuaungjava@gmail.com");// input the senders email ID
			msg.setTo(user.getEmail());

			msg.setSubject("Forget Password Reset Page");
			msg.setText("Your Password Recovery Link is below \n\n" + "Please click on this link to Reset your Password :" + resetLink + ". \n\n"
				+ "Regards \n" + "HMS Laptop Sale Admin");

			javaMailSender.send(msg);

			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	 @Scheduled(fixedRate = 60000) // Check every minute
	    public void deleteExpiredTokens() {
	        LocalDateTime now = LocalDateTime.now();
	        List<PasswordResetToken> expiredTokens = tokenRepository.findByExpiryDateTimeBefore(now);
	        
	        if (!expiredTokens.isEmpty()) {
	            tokenRepository.deleteAll(expiredTokens);
//            logger.info("Deleted {} expired tokens", expiredTokens.size());
            
	        }
	    }



	public String generateResetToken(User user) {
		UUID uuid = UUID.randomUUID();
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalDateTime expiryDateTime = currentDateTime.plusMinutes(1);
		PasswordResetToken resetToken = new PasswordResetToken();
		resetToken.setUser(user);
		resetToken.setToken(uuid.toString());
		resetToken.setExpiryDateTime(expiryDateTime);
		resetToken.setUser(user);
		PasswordResetToken token = tokenRepository.save(resetToken);
		if (token != null) {
			String endpointUrl = "http://localhost:8888/resetPassword";
			return endpointUrl + "/" + resetToken.getToken();
		}
		
		return "";
	}


	public boolean hasExipred(LocalDateTime expiryDateTime) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		return expiryDateTime.isAfter(currentDateTime);
	}
}