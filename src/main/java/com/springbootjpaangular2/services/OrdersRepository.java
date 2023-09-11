package com.springbootjpaangular2.services;

import com.springbootjpaangular2.domain.*;

/*import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;*/

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;


/** 
 * @author lyi
 * 08/2023
 * 
 * Spring Repository is actual DAO layer
 * We may add business logic layer service
 */
@Repository
public interface OrdersRepository  extends JpaRepository<Orders, Integer> { // CustomOrdersRepository { 
	
	    // Better not declare Transaction at Interface
	    @Transactional
	    public default Orders updateWholeOrder(Orders order) throws Exception {
	    	Integer orderNo = order.getOrder_no();
	    	String owner =  order.getOwner();
	    	Integer offerNo =  order.getOffer_no();
	    	
	    	Optional<Orders> ordsOpt = findById(orderNo); 
	    	Orders ords = ordsOpt.get();
			Orders.setAllFieldValue(order, ords); 
			
			order.setOrder_no(orderNo);
			order.setOffer_no(offerNo);
			order.setOwner(owner);
      
	        return ords;   
	    }
}
