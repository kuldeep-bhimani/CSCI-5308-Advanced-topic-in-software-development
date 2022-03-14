package com.dalhousie.MealStop.orders.service;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dalhousie.MealStop.orders.Constants.Constants;

import java.util.List;

public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void addOrder(Orders newOrder){
        //this method adds new order that has been placed
        orderRepository.save(newOrder);
    }

    @Override
    public List<Orders> getAllOrders(){
        //this method returns all the orders that has been placed so far
        return orderRepository.findAll();
    }

    @Override
    public List<Orders> getAllCanceledOrders(){

        //this method returns all the orders that are in the cancelled status
        return orderRepository.findByStatus(Constants.CANCELLED);
    }

    @Override
    public List<Orders> getOrdersByCustomerID(long userId){
        //this method returns all the orders placed by a customer using his customer id
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public Orders getOrderByOrderID(long orderId){
        //this method returns order detail using order id
        return orderRepository.findById(orderId);
    }


    @Override
    public List<Orders> getOrdersByRestaurantID(long restaurantId){
        //this method return restaurant details using restaurant id
        return orderRepository.findByRestaurantId(restaurantId);
    }

    //method to return most ordered meal by restaurant id and customer id.
    // It returns list of meal ids
    public List<Long> getMostOrderedMeal(long customerId, long restaurantId){
        return orderRepository.findByCustomerIdAndRestaurantId(customerId,restaurantId);
    }

    //method to return most ordered meal by restaurant
    // It returns list of meal ids
    public List<Long> getMostOrderedMealOfRestaurant(long restaurantId){
        return orderRepository.findAllByRestaurantId(restaurantId);
    }

    //method to return most ordered meal by customer id
    // It returns list of meal ids
    public List<Long> getMostOrderedMealOfCustomer(long customerId){
        return orderRepository.findAllByCustomerId(customerId);
    }
}
