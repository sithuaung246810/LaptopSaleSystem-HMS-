package com.laptopsale.controller;
 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.laptopsale.Repository.BrandRepository;
import com.laptopsale.Repository.TokenRepository;
import com.laptopsale.Repository.UserRepository;
import com.laptopsale.Service.AdminService;
import com.laptopsale.Service.LaptopService;
import com.laptopsale.Service.PurchaseDetailService;
import com.laptopsale.Service.PurchaseService;
import com.laptopsale.Service.UserDetailsServiceImpl;
import com.laptopsale.Service.UserService;
import com.laptopsale.dto.HistoryDTO;
import com.laptopsale.dto.PurchaseDTO;
import com.laptopsale.dto.UserDTO;
import com.laptopsale.dto.UserLoginDTO;
import com.laptopsale.entity.Admin;
import com.laptopsale.entity.Brand;
import com.laptopsale.entity.Laptop;
import com.laptopsale.entity.PasswordResetToken;
import com.laptopsale.entity.Purchase;
import com.laptopsale.entity.PurchaseDetail;
import com.laptopsale.entity.User;
 
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
 
@Controller
public class HomeController {
	
	public static String uploadDir = System.getProperty("user.dir")+"/public/laptopImages";
	
	@Autowired
	private PurchaseDetailService purchaseDetailService;
    @Autowired
    private LaptopService laptopService;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private PurchaseService purchaseService;
    
    
    @Autowired
    BrandRepository brandRepository;
    
    @Autowired
	UserDetailsServiceImpl userDetailsService;
    
    @Autowired
    private TokenRepository tokenRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/nav")
    public String nav() {
    	return "navbar";
    }
    
    @GetMapping("/home")
    public String home(Model model) {
        List<Laptop> laptops = laptopService.getFeaturedLaptops();
        List<Laptop> LatestLaptops = laptopService.getLatestLaptops();
        model.addAttribute("latestLaptops", LatestLaptops);
        model.addAttribute("laptops", laptops);
        return "homepage";
    }
    @GetMapping("/payment")
    public String showPayment() {
     
        return "payment";
    }
//    @GetMapping("/login")
//    public String testlogin(UserDTO userDto,Model model) {
//    	model.addAttribute("userDto", userDto);
//    	return "login";
//    }
//    @PostMapping("/login")
//    public String testloginSave(UserDTO userDto,Model model) {
//    	User user = userService.findByEmail(userDto.getEmail());
//    	userService.saveUser(user);
//    	return "laptop";
//    }
    
    @GetMapping("/profile")
    public String profile(OAuth2AuthenticationToken token, Model model, HttpSession session) {
        String email = token.getPrincipal().getAttribute("email");
        String name = token.getPrincipal().getAttribute("name");
        String address = token.getPrincipal().getAttribute("address") !=null? token.getPrincipal().getAttribute("address"):"none";
        String picture = token.getPrincipal().getAttribute("avatar_url");
        
//        String profileName = oidcUser.getPicture();
        System.out.println("Token: " + token);
        // Check if the user exists in the database
        User dbUser = userService.findByEmail(email);
        if (dbUser == null) {
            // Create a new user if not exists
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setAddress(address);
            newUser.setPhone("none");
            newUser.setPassword("123"); // or generate a random password or hash
//            newUser.setProfileName(picture);
            newUser.setProfileName(null);
            newUser.setRole("ROLE_USER");
            userService.saveUser(newUser); // Save user to the database
            session.setAttribute("profile", newUser.getProfileName());
            session.setAttribute("email", email);
            session.setAttribute("name", newUser.getName());
        }
        
        session.setAttribute("email", email);
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("photo", picture);
 
        return "homepage"; // or redirect to another page
    }
    
//    @GetMapping("/profile")
//    public String profile(OAuth2AuthenticationToken token, Model model, HttpSession session) {
//        String email = token.getPrincipal().getAttribute("email");
//        String name = token.getPrincipal().getAttribute("name");
//        String address = token.getPrincipal().getAttribute("address") !=null? token.getPrincipal().getAttribute("address"):"none";
//        String picture = token.getPrincipal().getAttribute("avatar_url");
//        System.out.println("Token: " + token);
//        // Check if the user exists in the database
//        User dbUser = userService.findByEmail(email);
//        if (dbUser == null) {
//            // Create a new user if not exists
//            User newUser = new User();
//            newUser.setEmail(email);
//            newUser.setName(name);
//            newUser.setAddress(address);
//            newUser.setPhone("none");
//            newUser.setPassword("123"); // or generate a random password or hash
//            newUser.setProfileName(picture);
//            newUser.setRole("ROLE_USER");
//            userService.saveUser(newUser); // Save user to the database
//        }
// 
//        session.setAttribute("email", email);
//        model.addAttribute("name", name);
//        model.addAttribute("email", email);
//        model.addAttribute("photo", picture);
// 
//        return "homepage"; // or redirect to another page
//    }
 
//    @GetMapping("/profile")
//    public String profile(OAuth2AuthenticationToken token, Model model,HttpSession session) {
//    	
//    	String name = token.getPrincipal().getAttribute("name");
//    	String email = token.getPrincipal().getAttribute("email");
//    	
////    	 String phoneNumber = token.getPrincipal().getAttribute("phone_number");
////    	 String address = token.getPrincipal().getAttribute("address");
//    	    
//    	model.addAttribute("name", token.getPrincipal().getAttribute("name"));
//    	model.addAttribute("email", token.getPrincipal().getAttribute("email"));
//    	model.addAttribute("photo", token.getPrincipal().getAttribute("picture"));
//    	
//    	
//    	
//    	
//    	User user = new User();
//    	
//    	user.setEmail(email);
//    	user.setName(name);
//    	user.setAddress("none");
//    	user.setPassword("123");
//    	user.setPhone("none");
//    	
//	
//    	
//    
////    	return "user-profile";
//    	
//    	User dbEmail = userService.findByEmail(email);
//        session.setAttribute("email", email);
//    	if(dbEmail == null){
//    		
//        	
//    		userService.saveUser(user);
////        	return "user-profile";
//    		return "redirect:/home";
//    	}
    	//Oauth2 login
    	
//    	 Admin admin = adminService.findByEmail(email);
//    	
//         if (admin != null ) {
//             session.setAttribute("email", email);
//            
//             return "redirect:/Admin";
//         }
    	
    	
//    	return"redirect:/home";
//    	return "user-profile";
//    }
 
    @GetMapping("/laptop/{id}")
    public String getLaptopDetails(@PathVariable("id") int id, Model model, HttpSession session) {
        Laptop laptop = laptopService.getLaptopById(id);
        session.setAttribute("intendedDestination", "/laptop/" + id);
        if (laptop != null) {
 
            model.addAttribute("laptop", laptop);
  
            return "display";
        } else {
            return "redirect:/home";
        }
    }
 
    @GetMapping("/laptop")
    public String getAllLaptops(@RequestParam(defaultValue = "ALL") String brand, Model model,HttpSession session) {
        
        
        List<Laptop> laptops = laptopService.getLaptopsByBrand(brand);
        List<Brand> brands = brandRepository.findAll();
        session.setAttribute("intendedDestination", "/laptop");
        model.addAttribute("laptops", laptops);
        model.addAttribute("selectedBrand", brand);
        model.addAttribute("brands", brands);
        return "details";
    }
 
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("showRegisterLink", true);
        model.addAttribute("user", new User());
        
        return "login";
    }
  
 
    @PostMapping("/login")
    public String login(
    		
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(value = "laptopId", required = false) Long laptopId,
            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
            Model model,
//             @ModelAttribute UserLoginDTO userLoginDTO,OAuth2AuthenticationToken token,
            HttpSession session) {
    	

    	User user = userService.findByEmail(email);
        Admin admin = adminService.findByEmail(email);
        if (user != null && user.getRole().equals("ROLE_ADMIN") || user.getRole().equals("ROLE_OWNER")) {
            session.setAttribute("email", email);
            session.setAttribute("name", user.getName());
            return "redirect:/admin";
        }
 
        
         
        if (user != null &&  user.getPassword().equals(password) && user.getRole().equals("ROLE_USER")) {
            session.setAttribute("email", email);
            session.setAttribute("userType", "user");
            
//            return "redirect:/home";
            session.setAttribute("name", user.getName());
            session.setAttribute("profile", user.getProfileName());
            String intendedDestination = (String) session.getAttribute("intendedDestination");
            if (intendedDestination != null) {
                session.removeAttribute("intendedDestination");
                return "redirect:" + intendedDestination;
            }
 
            if (laptopId != null) {
                return "redirect:/purchase/" + laptopId + "?quantity=" + quantity;
            } else {
                return "redirect:/home";
            }
        }
        
        model.addAttribute("error", "Invalid email or password");
        return "redirect:/login";  
        
    }
 
    
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
 
    @PostMapping("/register")
    public String registerUser(@RequestParam(value = "laptopId", required = false) Long laptopId,
            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
            
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,

            
//            @ModelAttribute("user") User user,
            @RequestParam("profileImage") MultipartFile file,
            Model model,
            //@ModelAttribute UserDTO userDTO,OAuth2AuthenticationToken token,//repaired
            HttpSession session) throws IOException {
 
//     	String name = token.getPrincipal().getAttribute("name"); //repaired
//    	String email = token.getPrincipal().getAttribute("email");
//    	 user = new User();
//    	user.setEmail(email);
//    	user.setPassword(name);
    	
   
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Please fix the errors below and try again.");
            return "register";
        }

    	
//    	userService.saveUser(user); //end
     	
            if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "An account with this email already exists.");
            model.addAttribute("laptopId", laptopId);
            model.addAttribute("quantity", quantity);
            return "register";
            }
            
	            String imageUUID = null; 
	  		  if(!file.isEmpty()) 
	  		  { 
	  		  imageUUID = file.getOriginalFilename();
	  		  Path fileNameAndPath = Paths.get(uploadDir, imageUUID); 
	  		  Files.write(fileNameAndPath, file.getBytes()); 
	  		  }
	  		  user.setProfileName(imageUUID);
            
            user.setRole("ROLE_USER");
            userService.saveUser(user);
            session.setAttribute("email", user.getEmail());
            session.setAttribute("userType", "user");
            session.setAttribute("name", user.getName());
            session.setAttribute("profile", user.getProfileName());
            
            String intendedDestination = (String) session.getAttribute("intendedDestination");
            if (intendedDestination != null) {
            session.removeAttribute("intendedDestination");
            return "redirect:" + intendedDestination;
            }
            
            
            return "redirect:/home";
            }
     
    
    
    
    @GetMapping("/purchase")
    public String showPurchasePage() {
        return "purchase";
    }
    
    
   
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }
   
 
 
  
    
    @GetMapping("/purchase/{id}")
    public String buyNowPage(@PathVariable("id") int id, @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity, Model model, HttpSession session) {
        String userEmail = (String) session.getAttribute("email");
        if (userEmail == null) {
            session.setAttribute("intendedDestination", "/purchase/" + id + "?quantity=" + quantity);
            return "redirect:/login";
        }
 
        User user = userService.findByEmail(userEmail);
        if (user == null) {
            return "redirect:/login";
        }
 
        Laptop laptop = laptopService.getLaptopById(id);
        if (laptop != null) {
        	PurchaseDTO purchaseDTO = new PurchaseDTO();
        	purchaseDTO.setLaptopId(laptop.getId());  
        	purchaseDTO.setModelNo(laptop.getModelNo());
        	purchaseDTO.setBrandName(laptop.getBrand().getBrandName());
        	purchaseDTO.setPrice(laptop.getPrice());
        	purchaseDTO.setQuantity(quantity);
 
 
            List<PurchaseDTO> purchaseItems = (List<PurchaseDTO>) session.getAttribute("purchaseItems");
       
            purchaseItems = new ArrayList<>();
      
            purchaseItems.add(purchaseDTO);
            session.setAttribute("purchaseItems", purchaseItems);
 
            model.addAttribute("purchaseItems", purchaseItems);
            model.addAttribute("user", user);
 
            return "purchase";
        } else {
            return "redirect:/home";
        }
    }
 
 
 
  
    
    //Forget..
    
	@GetMapping("/forgotPassword")
	public String forgotPassword() {
		return "forgotPassword";
	}
 
	@PostMapping("/forgotPassword")
	@ResponseBody
	public ResponseEntity<String> forgotPassordProcess(@ModelAttribute UserDTO userDTO, HttpSession session) {
	    User user = userRepository.findByEmail(userDTO.getEmail());
	    
	    // Check if user is found
	    if (user != null) {
	        // User exists, send email
	        String output = userDetailsService.sendEmail(user);
	        
	        if ("success".equals(output)) {
	            System.out.println("success"); // Print success
	            return ResponseEntity.ok("success"); // Return success response
	        } else {
	            System.out.println("error during email sending"); // Print error for email sending
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to send email. Please try again later."); // Return detailed error message
	        }
	    }
	    
	    // User not found
	    System.out.println("error: user not found"); // Print error
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found."); // Return error response
	}



	
	@GetMapping("/resetPassword/{token}")
	public String resetPasswordForm(@PathVariable String token, Model model) {
		PasswordResetToken reset = tokenRepository.findByToken(token);
		if (reset != null && userDetailsService.hasExipred(reset.getExpiryDateTime())) {
			model.addAttribute("email", reset.getUser().getEmail());
			
			return "resetPassword";
		}
		return "redirect:/forgotPassword?error";
	}
	
	@PostMapping("/resetPassword")
	public String passwordResetProcess(@ModelAttribute UserDTO userDTO) {
		User user = userRepository.findByEmail(userDTO.getEmail());
		if(user != null) {
//			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			user.setPassword(userDTO.getPassword());
			
			userRepository.save(user);
		}
		return "redirect:/login";
	}
	
	@PostMapping("/confirmPurchase")
    public String confirmPurchase(HttpSession session) {
        String userEmail = (String) session.getAttribute("email");
        if (userEmail == null) {
            return "redirect:/login";
        }
 
        User user = userService.findByEmail(userEmail);
        if (user == null) {
            return "redirect:/login";
        }
 
        List<PurchaseDTO> purchaseItems = (List<PurchaseDTO>) session.getAttribute("purchaseItems");
        if (purchaseItems == null || purchaseItems.isEmpty()) {
            return "redirect:/home";
        }
 
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setPurchaseDate(Date.valueOf(LocalDate.now()));
        purchaseService.savePurchase(purchase);
        
        
        List<String> brandNames = new ArrayList<>();
        List<String> models = new ArrayList<>();
        List<Double> prices = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        double totalPrice = 0.0;
        
        for (PurchaseDTO item : purchaseItems) {
            Laptop laptop = laptopService.getLaptopById(item.getLaptopId());
            
 
            PurchaseDetail purchaseDetail = new PurchaseDetail();
            purchaseDetail.setPurchase(purchase);
            purchaseDetail.setLaptop(laptop);
            purchaseDetail.setQuantity(item.getQuantity());
            purchaseDetailService.savePurchaseDetail(purchaseDetail);
 
            int quantityPurchased = item.getQuantity();
            laptopService.updateStock(laptop.getId(), quantityPurchased);
            
            brandNames.add(item.getBrandName());
            models.add(item.getModelNo());
            prices.add(item.getPrice());         
            quantities.add(item.getQuantity());
            totalPrice += item.getPrice() * item.getQuantity();
            
        }
        
 
        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setPurchaseId(purchase.getPurchaseId());
        historyDTO.setName(purchase.getUser().getName());
        historyDTO.setDate(purchase.getPurchaseDate());
        historyDTO.setEmail(userEmail);
        historyDTO.setPhone(purchase.getUser().getPhone());
        
        historyDTO.setBrand(brandNames);
        historyDTO.setModel(models);
        historyDTO.setPrice(prices);
        historyDTO.setQuantity(quantities);
        historyDTO.setTotal(totalPrice);
        
        purchaseDetailService.purchaseHistory(historyDTO);
        
        
 
        session.removeAttribute("purchaseItems");
        
 
        return "confirmpurchase";
    }
	
	@GetMapping("/profileUpdate")
	public String profileUpdate(Model model,@RequestParam("email") String email) {
		User user=userService.findByEmail(email);
		model.addAttribute("user", user);
		return "profileUpdate";	
	}
	
	@PostMapping("/profileUpdate")
    public String profileUpdate(
//            @ModelAttribute("user") User user,
            @RequestParam("profileImage") MultipartFile file,
            @RequestParam("deleteImage") boolean deleteImage,
            
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            Model model,
            HttpSession session) throws IOException {
		
		  // Check for validation errors
		    if (bindingResult.hasErrors()) {
		        model.addAttribute("error", "Please fix the errors below and try again.");
		        user.setProfileName(null);  
		        return "profileUpdate";
		    }


	            String imageUUID = null; 
	  		  if(!file.isEmpty()) 
	  		  { 
	  			
	  		  imageUUID = file.getOriginalFilename();
	  		  Path fileNameAndPath = Paths.get(uploadDir, imageUUID); 
	  		  Files.write(fileNameAndPath, file.getBytes()); 
	  		  user.setProfileName(imageUUID);
	  		  }
	  		  else if (deleteImage) {
	  			user.setProfileName(null);  
	  		  }else {
	  			user.setProfileName(null);  
	  		  }
	  		  user.setEmail(user.getEmail());
	  		  user.setPhone(user.getPhone());
            
            userService.saveUser(user);
            session.setAttribute("email", user.getEmail());
            session.setAttribute("userType", "user");
            session.setAttribute("name", user.getName());
            session.setAttribute("profile", user.getProfileName());
            
            String intendedDestination = (String) session.getAttribute("intendedDestination");
            if (intendedDestination != null) {
            session.removeAttribute("intendedDestination");
            return "redirect:" + intendedDestination;
            }
            
            
            return "redirect:/home";
            }
    
    
 
 
}
 