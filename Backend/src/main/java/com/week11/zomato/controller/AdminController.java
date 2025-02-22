package com.week11.zomato.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.week11.zomato.service.AdminService;

@RestController
@RequestMapping(value = "/zomato/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(value = "/add-restaurant")
    public ResponseEntity<String> addProduct(@RequestBody Map entity) {

        return adminService.addRestaurant(entity);
    }

    @PostMapping(value = "/edit-restaurant")
    public ResponseEntity<String> editRestaurant(@RequestBody Map entity) {

        return adminService.editRestaurant(entity);

    }

    @PostMapping(value = "/delete-restaurant")
    public ResponseEntity<String> deleteRestaurant(@RequestBody Map entity) {
        return adminService.deleteRestaurant(entity);
    }

    @PostMapping(value = "/add-fooditems")
    public ResponseEntity<String> addFoodItems(@RequestBody Map entity) {
        return adminService.addFoodItems(entity);
    }

    @PostMapping(value = "/edit-fooditems")
    public ResponseEntity<String> editFoodItems(@RequestBody Map entity) {
        return adminService.editFoodItems(entity);
    }

    @PostMapping(value = "/delete-fooditem")
    public ResponseEntity<String> deleteFooditem(@RequestBody Map entity) {
        return adminService.deleteFooditem(entity);
    }
}
