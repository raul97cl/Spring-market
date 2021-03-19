package com.market.repositositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.market.model.Purchase;
import com.market.model.User;


public interface PurchaseRepository extends JpaRepository<Purchase, Long>{

	List<Purchase> findByOwner(User owner);
}
