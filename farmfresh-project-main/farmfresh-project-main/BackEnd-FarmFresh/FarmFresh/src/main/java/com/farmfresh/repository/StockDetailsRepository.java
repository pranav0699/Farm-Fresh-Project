package com.farmfresh.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.farmfresh.entities.StockDetails;

@Repository
public interface StockDetailsRepository extends JpaRepository<StockDetails, String> {

	public StockDetails findByStockItem(String name);

	public Optional<StockDetails> findByFarmer1AndId(Integer farmerid, Integer productid);

	public StockDetails findById(Integer productid);

	@Query("SELECT NEW com.farmfresh.entities.StockDetails(s.id, s.stockItem, s.quantity, s.pricePerUnit, s.category, s.imagePath) FROM StockDetails s")
	public List<StockDetails> getAllProductDetails();

	public boolean existsById(Integer productid);

	public void deleteById(Integer productid);

}
