package com.laptopsale.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.laptopsale.Repository.UserRepository;
import com.laptopsale.entity.User;

@Service
public class UserService {
	@Autowired
    private UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User saveUser(User user) {
        // TODO: Implement password hashing before saving
        return userRepository.save(user);
    }
	public List<User> findAllUser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}
	

	
	public User findById(Integer id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id);
	}
	
	public long CountUsers() {
		return userRepository.countUser();
	}
	
	public long CountAdmins() {
		return userRepository.countAdmin();
	}
	public long CountBanUser() {
		return userRepository.countBanUser();
	}
	
	
	public List<User> findUserByRole(String role) {
		// TODO Auto-generated method stub
		return userRepository.findByRole(role);
	}
	 public List<String> getDistinctRoles() {
	        return userRepository.findDistinctRoles(); // Implement a custom query if needed
	    }
	 
	 public Page<User> findAllUsersWithPagination(int page, int size, String sortField, String sortDir) {
		    Pageable pageable = PageRequest.of(page - 1, size, 
		                         sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
		    return userRepository.findAll(pageable);
		}

		public Page<User> findUsersByRoleWithPagination(String role, int page, int size, String sortField, String sortDir) {
		    Pageable pageable = PageRequest.of(page - 1, size, 
		                         sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
		    return userRepository.findByRole(role, pageable);
		}

}
