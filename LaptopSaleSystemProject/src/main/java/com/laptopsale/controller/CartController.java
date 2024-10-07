package com.laptopsale.controller;

import java.sql.Date;
import java.time.LocalDate;

import java.util.ArrayList;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.laptopsale.Service.LaptopService;
import com.laptopsale.Service.PurchaseDetailService;
import com.laptopsale.Service.PurchaseService;
import com.laptopsale.Service.UserService;
import com.laptopsale.dto.CartItemDTO;
import com.laptopsale.dto.HistoryDTO;
import com.laptopsale.dto.PurchaseDTO;
import com.laptopsale.entity.Laptop;
import com.laptopsale.entity.Purchase;
import com.laptopsale.entity.PurchaseDetail;
import com.laptopsale.entity.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private LaptopService laptopService;
    
    @Autowired
    private PurchaseDetailService purchaseDetailService;
    
    @Autowired
    private PurchaseService purchaseService;
    
    @GetMapping("/purchasecart")
    public String showPurchasePage() {
        return "purchaseCart";
    }
    
    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cartItems");

        if (cartItems == null || cartItems.isEmpty()) {
            return "redirect:/home";  
        }
        double totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

   
            model.addAttribute("totalPrice", totalPrice);
        
        session.setAttribute("cartItemCount", cartItems.size());
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("user", userService.findByEmail((String) session.getAttribute("email")));

        return "cart";  
    }


    @GetMapping("/cartitem/")
    public String buyNowPage(@RequestParam("laptopId") int laptopId,
                             @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                             Model model, HttpSession session) {

        String userEmail = (String) session.getAttribute("email");

        User user = userService.findByEmail(userEmail);
        if (user == null) {
            session.setAttribute("intendedDestination", "/cartitem/?laptopId=" + laptopId + "&quantity=" + quantity);
            return "redirect:/login";
        }

        Laptop laptop = laptopService.getLaptopById(laptopId);
        if (laptop != null) {
            List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cartItems");
            if (cartItems == null) {
                cartItems = new ArrayList<>();
            }


            CartItemDTO existingCartItem = null;
            for (CartItemDTO item : cartItems) {
            	if (item.getLaptopId()==(laptopId)) {
                    existingCartItem = item;
                    break;
                }
            }

            	
            if (existingCartItem != null) {

                if (existingCartItem.getQuantity() + quantity <= laptop.getStock()) {
                    existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
                } else {
                   
                    existingCartItem.setQuantity(laptop.getStock());
                }
            } else {
                    CartItemDTO cartItemDTO = new CartItemDTO();
                    cartItemDTO.setLaptopId(laptop.getId());
                    cartItemDTO.setModelNo(laptop.getModelNo());
                    cartItemDTO.setBrandName(laptop.getBrand().getBrandName());
                    cartItemDTO.setPrice(laptop.getPrice());
                    cartItemDTO.setQuantity(quantity);
                    cartItemDTO.setImage(laptop.getImageName());  

                    cartItems.add(cartItemDTO);
                }
            
	           
            

            session.setAttribute("cartItems", cartItems);
            
            double totalPrice = cartItems.stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();
           
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("user", user);

            return  "redirect:/cart";
        } else {
            return "redirect:/home";
        }
    }

    @PostMapping("/removeFromCart")
    public String removeFromCart(@RequestParam("laptopId") Long laptopId, HttpSession session, Model model) {
   

        List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cartItems");

        if (cartItems != null) {
            cartItems.removeIf(item -> item.getLaptopId()==(laptopId));
            session.setAttribute("cartItems", cartItems);
        }
        double totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        model.addAttribute("totalPrice", totalPrice);
        session.setAttribute("cartItemCount", cartItems.size());
        model.addAttribute("cartItems", cartItems);
        return "cart";
    }

    @GetMapping("/purchase/cart")
    public String purchaseCartItems(HttpSession session, Model model) {
        List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cartItems");

        if (cartItems == null || cartItems.isEmpty()) {
            return "redirect:/home";
        }

        List<PurchaseDTO> purchaseItems = cartItems.stream().map(item -> {
            PurchaseDTO purchase = new PurchaseDTO();
            purchase.setLaptopId(item.getLaptopId());
            purchase.setQuantity(item.getQuantity());
            purchase.setModelNo(item.getModelNo());
            purchase.setBrandName(item.getBrandName());
            purchase.setPrice(item.getPrice());
            
            return purchase;
        }).collect(Collectors.toList());

       
        session.setAttribute("purchaseItems", purchaseItems);
        
        double totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
       
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("purchaseItems", purchaseItems);
        model.addAttribute("user", userService.findByEmail((String) session.getAttribute("email")));

        return "purchasecart";
    }

    
    @PostMapping("/confirmPurchaseCart")
    public String confirmPurchase(HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute("email");
//        if (userEmail == null) {
//            return "redirect:/login";
//        }

        User user = userService.findByEmail(userEmail);
//        if (user == null) {
//            return "redirect:/login";
//        }

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

        session.removeAttribute("cartItems");
        session.removeAttribute("purchaseItems");
        session.setAttribute("cartItemCount", null);
        return "confirmpurchase"; 
    }

}
