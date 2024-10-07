package com.laptopsale.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptopsale.Repository.AdminRepository;
import com.laptopsale.entity.Admin;


@Service
public class AdminService {
	@Autowired
    private AdminRepository adminRepository;

    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }
}
