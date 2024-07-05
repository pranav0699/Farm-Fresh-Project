package com.farmfresh.newservice;

import java.util.List;

import com.farmfresh.entities.Cart;
import com.farmfresh.entities.CartItem;
import com.farmfresh.entities.OrderDetails;
import com.farmfresh.entities.User;

public interface UserService {

	public User Authenticate(String email, String password);

	public User Register(User user);

	public CartItem AddToCart(Integer productid, Integer qty);

	public boolean PlaceOrder(Cart cart, User user);

	public User getUserDetails(Integer userId);

	public List<OrderDetails> getOrder(Integer userId);

}
