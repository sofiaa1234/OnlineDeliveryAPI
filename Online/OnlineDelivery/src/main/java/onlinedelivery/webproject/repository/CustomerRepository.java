//This repository interacts with the database.
package onlinedelivery.webproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;
import onlinedelivery.webproject.entity.Customer;


/*The CustomerRepository is a Spring Data JPA repository interface. 
It extends the JpaRepository interface, which provides various methods 
for interacting with a database using Java Persistence API (JPA) 
and Hibernate.This repository interface is typically used for CRUD 
(Create, Read, Update, Delete) operations on the Customer entity.*/
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {


	//java.util.Optional<Object[]> findConsignmentNumberAndStatusByConsignmentNumber(String consignmentNumber);

	//java.util.Optional<Object[]> findConsignmentNumberAndStatusByConsignmentNumber(String consignmentNumber);

	Customer findByConsignmentNumber(String consignmentNumber);
	
	/*
	 * @Query("SELECT c.consignmentNumber, c.name FROM Customer c") List<Object[]>
	 * getAllConsignmentNumbersWithNames();
	 */
	 @Query("SELECT c.consignmentNumber FROM Customer c")
	    List<String> getAllConsignmentNumbers();
	
}


