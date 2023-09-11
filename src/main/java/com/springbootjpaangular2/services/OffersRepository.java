package com.springbootjpaangular2.services;

import com.springbootjpaangular2.domain.*;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import io.reactivex.Observable;
import java.util.ArrayList;*/

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author lyi
 * 08/2023
 * 
 * Spring Repository is actual DAO layer
 * We may add business logic layer service 
 */
@Repository
public interface OffersRepository extends JpaRepository<Offers, Integer> {//, CustomOffersRepository  { 
  
	@Query(nativeQuery = true, value = "SELECT * FROM offers")
    public List<Offers> retrieveOffers();  
}
