package com.week11.zomato.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.week11.zomato.data.model.FoodItem;
import com.week11.zomato.data.model.FooditemDetails;
import com.week11.zomato.data.model.OrderInfo;
import com.week11.zomato.data.model.RestaurantInfo;
import com.week11.zomato.data.model.SearchFoodItem;
import com.week11.zomato.service.UserService;
import com.week11.zomato.service.ValidUser;

@RestController
@RequestMapping(value = "/zomato/user")
public class UserControler {

    @Autowired
    private ValidUser validUser;
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    ResponseEntity<String> signup(@RequestBody Map<String, String> signupDetails) {
        if (!validUser.isPhoneNumberUnique(signupDetails.get("phonenumber"))) {
            return new ResponseEntity<>("phone", HttpStatus.OK);
        }

        return userService.signUp(signupDetails);

    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> login) {
        if (validUser.isPhoneNumberUnique(login.get("phonenumber"))) {
            return new ResponseEntity<>("phone", HttpStatus.OK);
        }
        if (!validUser.isPasswordValid(login.get("phonenumber"), login.get("password"))) {
            return new ResponseEntity<>("password", HttpStatus.OK);

        }

        return userService.login(login);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(@RequestBody Map entity) {
        return userService.logout(entity);
    }

    @PostMapping(value = "/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> entity) {
        if (validUser.isPhoneNumberUnique(entity.get("phonenumber"))) {
            return new ResponseEntity<>("phone", HttpStatus.OK);
        }
        return userService.forgotPassword(entity);

    }

    @PostMapping(value = "/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> entity) {

        return userService.resetPassword(entity);

    }

    @PostMapping(value = "/search-by-name")
    public ResponseEntity<List<RestaurantInfo>> searchByName(@RequestBody Map<String, String> entity) {

        return userService.searchByName(entity);
    }

    @PostMapping(value = "/search-by-fooditem")
    public ResponseEntity<List<SearchFoodItem>> searchByFoodItem(@RequestBody Map<String, String> entity) {

        return userService.searchByFoodItem(entity);
    }

    @PostMapping(value = "/place-order")
    public ResponseEntity<String> placeOrder(@RequestBody Map entity) {

        return userService.placeOrder(entity);
    }

    @PostMapping(value = "/rate-order")
    public ResponseEntity<String> rateOrder(@RequestBody Map entity) {
        return userService.rateOrder(entity);

    }

    @GetMapping(value = "/get-all-food-items")
    public ResponseEntity<List<FooditemDetails>> getFoodAllItems() {

        return userService.getAllFoodItems();

    }
    @PostMapping(value = "/get-all-order-details")
    public ResponseEntity<List<OrderInfo>> getAllOrderDetails(@RequestBody Map entity) {

        return userService.getAllOrderDetails(entity);
    }

}
