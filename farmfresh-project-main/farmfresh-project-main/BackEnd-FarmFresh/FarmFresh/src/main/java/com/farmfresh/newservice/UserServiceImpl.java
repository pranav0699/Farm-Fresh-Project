package com.farmfresh.newservice;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farmfresh.custom_exceptions.ResourceNotFoundException;
import com.farmfresh.entities.Cart;
import com.farmfresh.entities.CartItem;
import com.farmfresh.entities.Farmer;
import com.farmfresh.entities.OrderDetails;
import com.farmfresh.entities.Orders;
import com.farmfresh.entities.StockDetails;
import com.farmfresh.entities.User;
import com.farmfresh.repository.UserDetailsRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDetailsRepository uRepo;

	@PersistenceContext
	private EntityManager mgr;

	@Override
	public User Authenticate(String email, String password) {
		// TODO Auto-generated method stub
		return uRepo.getUserByEmailAndPassword(email, password)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
	}

	@Override
	public User Register(User user) {
		// TODO Auto-generated method stub
		return uRepo.save(user);
	}

	@Override
	public CartItem AddToCart(Integer productid, Integer qty) {

		// Find the product based on the provided productid
		/*
		 * StockDetails product = sRepo.findById(productid); String jpql1 =
		 * "SELECT sd.farmer1 FROM StockDetails sd JOIN sd.farmer1 f WHERE sd.id=:pid";
		 * Farmer fid = mgr.createQuery(jpql1, Farmer.class).setParameter("pid",
		 * productid).getSingleResult();
		 * 
		 * // Create a new CartItem with the product and quantity CartItem cartItem =
		 * new CartItem(); cartItem.setId(product.getId());
		 * cartItem.setItem(product.getStockItem());
		 * cartItem.setPrice(product.getPricePerUnit()); cartItem.setQty(qty);
		 * cartItem.setAmount(qty * product.getPricePerUnit());
		 * cartItem.setFarmer_id(fid.getFarmerId());
		 * 
		 * return cartItem;
		 */
		String jpql = "SELECT NEW com.farmfresh.entities.StockDetails(sd.id, sd.stockItem, sd.pricePerUnit) FROM StockDetails sd WHERE sd.id=:Id";
		String jpql2 = "SELECT sd.farmer1 FROM StockDetails sd JOIN sd.farmer1 f WHERE sd.id=:pid";
		StockDetails product = mgr.createQuery(jpql, StockDetails.class).setParameter("Id", productid)
				.getSingleResult();
		Farmer fid = mgr.createQuery(jpql2, Farmer.class).setParameter("pid", productid).getSingleResult();

		CartItem item = new CartItem();
		item.setId(product.getId());
		item.setItem(product.getStockItem());
		item.setPrice(product.getPricePerUnit());
		item.setQty(qty);
		item.setAmount(qty * product.getPricePerUnit());
		item.setFarmer_id(fid.getFarmerId());

		return item;
	}

	@Override
	public boolean PlaceOrder(Cart cart, User user) {
		Orders order = new Orders();
		List<CartItem> items = cart.getItems();

		for (CartItem item : items) {
			OrderDetails details = new OrderDetails();
			details.setAmount(item.getAmount());
			details.setOrderItem(item.getItem());
			details.setQuantity(item.getQty());
			Farmer f = mgr.find(Farmer.class, item.getFarmer_id());
			details.setFarmer(f);
			order.getOrderDetails().add(details);
		}

		order.setDeliveryStatus(false);
		order.setPaymentStatus(true);
		order.setUser(user);

		long millis = System.currentTimeMillis();
		order.setPlaceOrderDate(new java.sql.Date(millis));

		java.sql.Date todaysDate = new java.sql.Date(new java.util.Date().getTime());
		int futureDay = 3;
		java.sql.Date futureDate = this.addDays(todaysDate, futureDay);
		order.setDeliveryDate(futureDate);

		mgr.persist(order);
		for (OrderDetails det : order.getOrderDetails()) {
			det.setOrders(order);
			mgr.persist(det);
		}
		return true;
	}

	public Date addDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		return new Date(c.getTimeInMillis());
	}

	@Override
	public User getUserDetails(Integer userId) {
		// TODO Auto-generated method stub
		return uRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
	}

	@Override
	public List<OrderDetails> getOrder(Integer userId) {
		String jpql = "SELECT NEW com.farmfresh.entities.OrderDetails(o.id, o.orderItem, o.quantity, o.amount, o.orders) FROM OrderDetails o "
				+ "JOIN o.orders ord JOIN ord.user u WHERE u.userId =:userid";
		return mgr.createQuery(jpql, OrderDetails.class).setParameter("userid", userId).getResultList();
	}

}
