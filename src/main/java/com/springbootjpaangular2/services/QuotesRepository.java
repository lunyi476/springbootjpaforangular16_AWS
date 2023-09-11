package com.springbootjpaangular2.services;

import com.springbootjpaangular2.domain.*;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import io.reactivex.Observable;*/

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
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
 * 
 */
@Repository
public interface QuotesRepository extends JpaRepository<Quotes, QuotesKeys> { //, CustomQuotesRepository  { 
	// this just one example demo, not real to use
	@Query(nativeQuery = true, value = "SELECT * FROM quotes t WHERE t.owner='BAMBOOROSE'  and t.request_no > 0")
	public List<Quotes> findByOwnerAndNumber(int q);
	
	@Transactional
    public default Quotes updateWholeQuote(Quotes quote) throws Exception {
    	Integer reqNo = quote.getRequest_no();
    	String owner =  quote.getOwner();
    	      	
    	Optional<Quotes> qtsOpt = findById(new QuotesKeys(reqNo, owner));   
        /**  
         *  Simply remove all offers of target, add/copy all field value from source.
         *  Otherwise either passing specific updated fields and offers(deleted/added)
         *  from client side or check changed fields and offers one by one
         *  
         **/ 
    	Quotes qt = qtsOpt.get();
    	qt.removeAllOffers(); 
		Quotes.setAllFieldValue(quote, qt);

        return qt;  
    }
	
//	@Query(nativeQuery = true, value = "SELECT * FROM quotes t WHERE t.owner='BAMBOOROSE'  and t.request_no > 0")
//	public List<Quotes> findAll();

}
