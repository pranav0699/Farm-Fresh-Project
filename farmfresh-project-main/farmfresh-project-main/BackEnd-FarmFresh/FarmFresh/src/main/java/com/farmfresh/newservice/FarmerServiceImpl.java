package com.farmfresh.newservice;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farmfresh.custom_exceptions.ResourceNotFoundException;
import com.farmfresh.entities.Farmer;
import com.farmfresh.entities.StockDetails;
import com.farmfresh.repository.FarmerDetailsRepository;
import com.farmfresh.repository.StockDetailsRepository;

@Service
@Transactional
public class FarmerServiceImpl implements FarmerService {

	@Autowired
	private FarmerDetailsRepository fRepo;

	@Autowired
	private StockDetailsRepository sRepo;

	@Override
	public List<Farmer> getFarmersList() {
		// TODO Auto-generated method stub
		return fRepo.getAllFarmerDetails();
	}

	@Override
	public Farmer getFarmerDetails(Integer id) {
		// TODO Auto-generated method stub
		return fRepo.getFarmerDetailsById(id);
	}

	@Override
	public List<StockDetails> getFarmerStock(Integer farmerid) {
	    // Check if the farmer with the given farmerid exists
	    if (fRepo.existsById(farmerid)) {
	        return sRepo.findAll();
	    } else {
	        return null;
	    }
	}

	@Override
	public StockDetails getProductDetails(Integer farmerid, Integer productid) {
	    // Fetch product details from the repository using farmerid and productid
	    return sRepo.findByFarmer1AndId(farmerid, productid)
	               .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
	}


	@Override
	public List<StockDetails> getAllProduct() {
		// TODO Auto-generated method stub
		return sRepo.getAllProductDetails();
	}

}
