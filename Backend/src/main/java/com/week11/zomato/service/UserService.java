package com.week11.zomato.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.swing.SortOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.week11.zomato.data.model.FoodItem;
import com.week11.zomato.data.model.FoodItemRating;
import com.week11.zomato.data.model.FooditemDetails;
import com.week11.zomato.data.model.OrderFoodItems;
import com.week11.zomato.data.model.OrderInfo;
import com.week11.zomato.data.model.RestaurantInfo;
import com.week11.zomato.data.model.RestaurantRating;
import com.week11.zomato.data.model.SearchFoodItem;
import com.week11.zomato.data.model.UserInfo;
import com.week11.zomato.data.repository.FoodItemRatingRepo;
import com.week11.zomato.data.repository.FoodItemRepo;
import com.week11.zomato.data.repository.OrderFoodItemsRepo;
import com.week11.zomato.data.repository.OrderInfoRepo;
import com.week11.zomato.data.repository.RestaurantInfoRepo;
import com.week11.zomato.data.repository.RestaurantRatingRepo;
import com.week11.zomato.data.repository.UserInfoRepo;

@Service
public class UserService {
    @Autowired
    private UserInfoRepo userInfoRepo;

    @Autowired
    private RestaurantInfoRepo restaurantInfoRepo;

    @Autowired
    private FoodItemRepo foodItemRepo;

    @Autowired
    private OrderFoodItemsRepo orderFoodItemsRepo;

    @Autowired
    private OrderInfoRepo orderInfoRepo;

    @Autowired
    private RestaurantRatingRepo restaurantRatingRepo;
    @Autowired
    private FoodItemRatingRepo foodItemRatingRepo;

    public ResponseEntity<String> signUp(Map<String, String> signup) {

        UserInfo userInfo = new UserInfo();
        userInfo.setName(signup.get("name"));
        userInfo.setPhonenumber(signup.get("phonenumber"));
        userInfo.setSecretquestion(signup.get("secretquestion"));
        userInfo.setAddress(signup.get("address"));
        userInfo.setAnswer(signup.get("answer"));
        userInfo.setPassword(signup.get("password"));
        userInfo.setLoginstatus(Boolean.FALSE);

        userInfo = userInfoRepo.save(userInfo);

        return new ResponseEntity<>("success", HttpStatus.OK);

    }

    public ResponseEntity<String> login(Map<String, String> login) {

        Optional<UserInfo> userInfo = userInfoRepo.findByPhonenumber(login.get("phonenumber"));
        UserInfo userInfo1 = userInfo.get();
        if (userInfo1.getRole() == 0) {
            userInfo1.setLoginstatus(Boolean.TRUE);
            userInfo1 = userInfoRepo.save(userInfo1);

            return new ResponseEntity<>("success_admin", HttpStatus.OK);

        } else {
            userInfo1.setLoginstatus(Boolean.TRUE);

            userInfo1 = userInfoRepo.save(userInfo1);

            return new ResponseEntity<>("success_user", HttpStatus.OK);
        }

    }

    public ResponseEntity<String> logout(Map entity) {
        Optional<UserInfo> userInfo = userInfoRepo.findByPhonenumber((String) entity.get("phonenumber"));
        UserInfo userInfo1 = userInfo.get();
        userInfo1.setLoginstatus(Boolean.FALSE);
        userInfo1 = userInfoRepo.save(userInfo1);
        return ResponseEntity.ok().body("success");
    }

    // to get the secret ques from phonenumber
    public ResponseEntity<String> forgotPassword(Map<String, String> forgotPassword) {

        Optional<UserInfo> userInfo = userInfoRepo.findByPhonenumber(forgotPassword.get("phonenumber"));
        UserInfo userInfo1 = userInfo.get();
        return new ResponseEntity<>(userInfo1.getSecretquestion(), HttpStatus.OK);

    }

    public ResponseEntity<String> resetPassword(Map<String, String> forgotPassword) {

        Optional<UserInfo> userInfo = userInfoRepo.findByPhonenumber(forgotPassword.get("phonenumber"));
        UserInfo userInfo1 = userInfo.get();
        if (userInfo1.getSecretquestion().equals(forgotPassword.get("secretquestion"))) {
            if (userInfo1.getAnswer().equals(forgotPassword.get("answer"))) {
                userInfo1.setPassword(forgotPassword.get("newpassword"));
                userInfo1 = userInfoRepo.save(userInfo1);

            } else {
                return new ResponseEntity<>("answer", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("success", HttpStatus.OK);

    }

    public ResponseEntity<List<RestaurantInfo>> searchByName(Map<String, String> entity) {

        String search = entity.get("search");
        String[] words = search.split(" ");

        ArrayList<RestaurantInfo> common = new ArrayList<RestaurantInfo>();

        for (int i = 0; i < words.length; i++) {

            if (words[i] == "") {
                continue;
            }
            common.addAll(restaurantInfoRepo.findByRestaurantnameContaining(words[i],
                    Sort.by(Sort.Direction.DESC, "restaurantrating")));
        }

        Set<RestaurantInfo> set = new LinkedHashSet<RestaurantInfo>(common);
        List<RestaurantInfo> restaurant = new ArrayList<RestaurantInfo>(set);

        return ResponseEntity.ok().body(restaurant);
    }

    // public ResponseEntity<List<RestaurantInfo>> searchByName(Map<String, String>
    // entity) {

    // List<RestaurantInfo> restaurants =
    // restaurantInfoRepo.searchRestaurantName(entity.get("search"));
    // return ResponseEntity.ok().body(restaurants);

    // }

    public ResponseEntity<List<SearchFoodItem>> searchByFoodItem(Map<String, String> entity) {

        String search = entity.get("search");
        String[] words = search.split(" ");

        List<FoodItem> common = new ArrayList<FoodItem>();

        for (int i = 0; i < words.length; i++) {

            if (words[i] == "") {
                continue;
            }

            common.addAll(foodItemRepo.findByFoodnameContaining(words[i],
                    Sort.by(Sort.Direction.DESC, "fooditemrating")));
        }
        List<FoodItem> foodItems = common;
        ListIterator<FoodItem> itr = foodItems.listIterator();
        List<SearchFoodItem> sfoodItem = new ArrayList<SearchFoodItem>();
        while (itr.hasNext()) {
            FoodItem food = itr.next();
            RestaurantInfo rest = food.getRestaurantInfo();
            SearchFoodItem searchFood = new SearchFoodItem(rest.getRestaurantid(), rest.getRestaurantname(),
                    rest.getRestaurantaddress(), rest.getRestaurantrating(), food);
            sfoodItem.add(searchFood);
        }

        Set<SearchFoodItem> set = new LinkedHashSet<SearchFoodItem>(sfoodItem);
        List<SearchFoodItem> food = new ArrayList<SearchFoodItem>(set);

        return ResponseEntity.ok().body(food);
    }

    public ResponseEntity<String> placeOrder(Map entity) {

        Optional<RestaurantInfo> restaurantInfo = restaurantInfoRepo.findById((Integer) entity.get("restaurantid"));
        RestaurantInfo rest = restaurantInfo.get();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setRestaurantid((Integer) entity.get("restaurantid"));
        orderInfo.setRestaurantname((String) entity.get("restaurantname"));
        Optional<UserInfo> userInfo = userInfoRepo.findByPhonenumber((String) entity.get("phonenumber"));
        UserInfo user = userInfo.get();
        orderInfo.setUserid(user.getUserid());
        orderInfo.setDeliveryaddress((String) entity.get("deliveryaddress"));
        orderInfo.setTotalamount((Integer) entity.get("totalamount"));
        orderInfoRepo.save(orderInfo);

        ArrayList<String> fooditemid = (ArrayList) entity.get("fooditemid");
        ListIterator<String> ll = fooditemid.listIterator();

        ArrayList<String> foodname = (ArrayList) entity.get("foodname");
        ListIterator<String> name = foodname.listIterator();

        ArrayList<String> amount = (ArrayList) entity.get("amount");
        ListIterator<String> fAmount = amount.listIterator();

        ArrayList<String> quantity = (ArrayList) entity.get("quantity");
        ListIterator<String> qua = quantity.listIterator();

        while (ll.hasNext()) {

            OrderFoodItems orderFoodItems = new OrderFoodItems();

            String s = ll.next();
            orderFoodItems.setFooditemid(Integer.parseInt(s));

            s = name.next();

            orderFoodItems.setFoodname(s);

            s = fAmount.next();
            orderFoodItems.setAmount(Integer.parseInt(s));

            s = qua.next();
            orderFoodItems.setQuantity(Integer.parseInt(s));

            orderFoodItems.setOrderinfo(orderInfo);
            orderInfo.getOrderFoodItems().add(orderFoodItems);
            orderInfoRepo.save(orderInfo);

        }

        // ger keys = (Integer) entity.get("keycount");

        // (Integer i = 1; i <= keys; i++) {
        // String s = i.toString();
        // // Map<OrderFoodItems> map = (Map<OrderFoodItems>)entity.get(s);
        // List<Map> list = (ArrayList<Map>) entity.get(s);
        // ator<Map> itr = list.iterator();

        // e (itr.hasNext()) {
        // map = itr.next();
        // Set<Entry> entrySet = map.entrySet();
        // for(Entry entry : entrySet){
        // entry.get("fooditemid")
        // }

        // }

        // }
        System.out.println("*******************************" + orderInfo);

        return ResponseEntity.ok().body("success");
    }

    public ResponseEntity<String> rateOrder(Map entity) {
        Optional<RestaurantInfo> restaurantInfo = restaurantInfoRepo.findById((Integer) entity.get("restaurantid"));
        Optional<UserInfo> userInfo = userInfoRepo.findByPhonenumber((String) entity.get("phonenumber"));
        Optional<OrderInfo> order = orderInfoRepo.findByUseridAndOrderid(userInfo.get().getUserid(),
                (Integer) entity.get("orderid"));
        OrderInfo orderInfo = order.get();
        orderInfo.setOrderflag(1);
        orderInfoRepo.save(orderInfo);
        RestaurantInfo rest = restaurantInfo.get();
        int id = (Integer) (entity.get("restaurantrating"));
        Float f = new Float(id);
        float f1 = f.floatValue();
        Float rating = 0f;
        if (rest.getRestaurantrating() == 0.0) {
            // rating = (Float)(entity.get("restaurantrating"));
            rating = f1;
            rest.setRestaurantrating(rating);
            rest.setNumofrating(rest.getNumofrating() + 1);
            restaurantInfoRepo.save(rest);

        } else {

            // rating = (float) (((rest.getRestaurantrating() * rest.getNumofrating())
            // + (Double) entity.get("restaurantrating")) / (rest.getNumofrating() + 1));
            ///////////////////
            rating = (float) (((rest.getRestaurantrating() * rest.getNumofrating())
                    + f1) / (rest.getNumofrating() + 1));
            rest.setRestaurantrating(rating);
            rest.setNumofrating(rest.getNumofrating() + 1);
            restaurantInfoRepo.save(rest);
        }
        RestaurantRating restaurantRating = new RestaurantRating();

        restaurantRating.setName(userInfo.get().getName());
        restaurantRating.setRestaurantid((Integer) entity.get("restaurantid"));
        restaurantRating.setRestaurantname(rest.getRestaurantname());
        restaurantRating.setRestaurantrating(rest.getRestaurantrating());
        restaurantRating.setRestaurantreview((String) entity.get("restaurantreview"));
        restaurantRatingRepo.save(restaurantRating);

        ArrayList<String> fooditemid = (ArrayList) entity.get("fooditemid");

        if (fooditemid.isEmpty()) {
            return ResponseEntity.ok().body("success");
        } else {
            ListIterator<String> ll = fooditemid.listIterator();

            ArrayList<String> fooditemrating = (ArrayList) entity.get("fooditemrating");
            ListIterator<String> ratingitr = fooditemrating.listIterator();

            ArrayList<String> fooditemreview = (ArrayList) entity.get("fooditemreview");
            ListIterator<String> review = fooditemreview.listIterator();

            while (ll.hasNext()) {

                FoodItemRating foodrating = new FoodItemRating();
                foodrating.setName(userInfo.get().getName());
                foodrating.setRestaurantid((Integer) entity.get("restaurantid"));
                foodrating.setRestaurantname(rest.getRestaurantname());

                String s = ll.next();
                Optional<FoodItem> food = foodItemRepo.findById(Integer.parseInt(s));
                FoodItem foodItem = food.get();
                foodrating.setFooditemid(Integer.parseInt(s));
                foodrating.setFoodname(foodItem.getFoodname());

                String rate = ratingitr.next();
                System.out.println("########################" + rate);
                System.out.println("***************************" + Double.parseDouble(rate));

                foodrating.setFooditemrating(Double.parseDouble(rate));
                String fReview = review.next();
                foodrating.setFooditemreview(fReview);
                foodItemRatingRepo.save(foodrating);
                Double foodRating = 0.0;

                if (foodItem.getFooditemrating() == 0.0) {

                    foodItem.setFooditemrating(Double.parseDouble(rate));
                    foodItem.setNumofrating(foodItem.getNumofrating() + 1);
                    foodItemRepo.save(foodItem);

                } else {

                    foodRating = ((foodItem.getFooditemrating() * foodItem.getNumofrating())
                            + Double.parseDouble(rate)) / (foodItem.getNumofrating() + 1);

                    foodItem.setFooditemrating(foodRating);
                    foodItem.setNumofrating(foodItem.getNumofrating() + 1);
                    foodItemRepo.save(foodItem);
                }

            }

        }

        return ResponseEntity.ok().body("success");
    }

    public ResponseEntity<List<FooditemDetails>> getAllFoodItems() {
        List<FoodItem> foodItem = foodItemRepo.findAll();
        ListIterator<FoodItem> itr = foodItem.listIterator();

        List<FooditemDetails> fid = new ArrayList<FooditemDetails>();

        while (itr.hasNext()) {
            FoodItem fooditem = itr.next();

            RestaurantInfo ri = fooditem.getRestaurantInfo();
            FooditemDetails fs = new FooditemDetails(ri.getRestaurantid(), fooditem, ri.getRestaurantname());
            fid.add(fs);

        }
        return ResponseEntity.ok().body(fid);
    }

    public ResponseEntity<List<OrderInfo>> getAllOrderDetails(Map entity) {
        Optional<UserInfo> userInfo = userInfoRepo.findByPhonenumber((String) entity.get("phonenumber"));
        UserInfo user = userInfo.get();
        int id = user.getUserid();
        List<OrderInfo> oi = orderInfoRepo.findAllByUserid(id);
        if (oi.isEmpty()) {
            return ResponseEntity.ok().body(oi);
        }
        return ResponseEntity.ok().body(oi);
    }

}
