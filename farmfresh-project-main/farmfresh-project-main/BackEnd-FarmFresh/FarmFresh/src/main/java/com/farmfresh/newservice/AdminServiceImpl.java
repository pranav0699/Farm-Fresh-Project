package com.farmfresh.newservice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.farmfresh.custom_exceptions.ResourceNotFoundException;
import com.farmfresh.entities.Category;
import com.farmfresh.entities.Farmer;
import com.farmfresh.entities.OrderDetails;
import com.farmfresh.entities.StockDetails;
import com.farmfresh.entities.User;
import com.farmfresh.repository.CategoryDetailsRepository;
import com.farmfresh.repository.FarmerDetailsRepository;
import com.farmfresh.repository.StockDetailsRepository;
import com.farmfresh.repository.UserDetailsRepository;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
	private FarmerDetailsRepository fRepo;

	@Autowired
	private StockDetailsRepository sRepo;

	@Autowired
	private CategoryDetailsRepository cRepo;

	@Autowired
	private UserDetailsRepository uRepo;

	@PersistenceContext
	private EntityManager mgr;

	@Override
	public Farmer GetFarmerDetails(Integer farmerid) {
		// TODO Auto-generated method stub
		return fRepo.getFarmerDetailsById(farmerid);
	}

	@Override
	public Farmer AddFarmer(Farmer farmer) {
		// TODO Auto-generated method stub
		return fRepo.save(farmer);
	}

	@Override
	public boolean RemoveFarmer(Integer farmerid) {
		// TODO Auto-generated method stub
		boolean status = false;
		if (fRepo.existsById(farmerid)) {
			fRepo.deleteById(farmerid);
			return status = true;
		}
		return status;
	}

	@Override
	public boolean UpdateFarmer(Farmer farmer) {
		mgr.unwrap(Session.class).update(farmer);
		return true;
	}

	@Override
	public StockDetails GetProductDetails(Integer productid) {
		// TODO Auto-generated method stub
		return sRepo.findById(productid);
	}

	@Override
	public boolean AddProduct(Integer farmerid, StockDetails product) {
		// TODO Auto-generated method stub
		Farmer farmer = fRepo.getFarmerDetailsById(farmerid);
		product.setFarmer1(farmer);
		sRepo.save(product);
		return true;
	}

	@Override
	public boolean RemoveProduct(Integer productid) {
		// TODO Auto-generated method stub
		boolean status = false;
		if (sRepo.existsById(productid)) {
			sRepo.deleteById(productid);
			return status = true;
		}
		return status;
	}

	@Override
	public StockDetails UpdateProduct(StockDetails product) {
		// TODO Auto-generated method stub
		return sRepo.save(product);
	}

	@Override
	public Category GetCategory(Integer catid) {
		// TODO Auto-generated method stub
		return cRepo.findByCategoryId(catid);
	}

	@Override
	public Category SetCategory(String category) {
		// TODO Auto-generated method stub
		Category newCategory = new Category();
		newCategory.setCategoryName(category);
		return cRepo.save(newCategory);
	}

	@Override
	public boolean RemoveCategory(Integer catid) {
		// TODO Auto-generated method stub
		boolean status = false;
		if (cRepo.existsById(catid)) {
			cRepo.deleteById(catid);
			return status = true;
		}
		return status;
	}

	@Override
	public String saveImage(Integer productId, MultipartFile imgFile) throws IOException {
		// TODO Auto-generated method stub
		StockDetails s = mgr.find(StockDetails.class, productId);
		String path = imgFile.getOriginalFilename();
		System.out.println("path {}" + path);
		s.setImagePath(path);
		Files.copy(imgFile.getInputStream(), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);

		return "file copied";
	}

	@Override
	public byte[] restoreImage(Integer pId) throws IOException {
		// TODO Auto-generated method stub
		StockDetails s = mgr.find(StockDetails.class, pId);
		String path = s.getImagePath();
		if (path != null)
			return Files.readAllBytes(Paths.get(path));
		throw new ResourceNotFoundException("Image not  yet assigned , for " + s.getStockItem());
	}

	@Override
	public List<Category> getAllCategory() {
		// TODO Auto-generated method stub
		return cRepo.findAll();
	}

	@Override
	public List<OrderDetails> getAllOrders() {
		String jpql = "SELECT NEW com.farmfresh.pojos.OrderDetails(o.id, o.orderItem, o.quantity, o.amount, o.orders) FROM OrderDetails o";
		return mgr.createQuery(jpql, OrderDetails.class).getResultList();
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return uRepo.findAll();
	}

	@Override
	public User UpdateUser(User user) {
		// TODO Auto-generated method stub
		return uRepo.save(user);
	}

	@Override
	public byte[] restoreImageAgain(String productName) throws IOException {
		// TODO Auto-generated method stub
		StockDetails s = sRepo.findByStockItem(productName);
		String path = s.getImagePath();
		if (path != null)
			return Files.readAllBytes(Paths.get(path));
		throw new ResourceNotFoundException("Image not  yet assigned , for " + s.getStockItem());
	}

}
