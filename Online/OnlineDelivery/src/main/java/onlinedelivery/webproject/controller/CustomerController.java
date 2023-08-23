//This is the REST controller for handling customer-related operations.
package onlinedelivery.webproject.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import net.bytebuddy.dynamic.DynamicType;
//import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;
import onlinedelivery.webproject.entity.ConsignmentNumber;
import onlinedelivery.webproject.entity.Customer;
import onlinedelivery.webproject.repository.CustomerRepository;

@RestController  //handling customer-related operations,handling incoming HTTP requests
@RequestMapping("/api/customers") //Specifies the base URL path for all the endpoints defined within this controller
public class CustomerController {
    @Autowired //used to automatically inject dependencies into the controller.
    private CustomerRepository customerRepository;
    @Autowired
    private ConsignmentNumber consignmentNumber;
    
    @PostMapping("/customer") //handle http post request
	  public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) { //@RequestBody: Indicates that the customer object is expected in the request body
			try { //handle exceptions
				
				String generatedConsignmentNumber = consignmentNumber.generateConsignmentNumber();
				customer.setConsignmentNumber(generatedConsignmentNumber);
				
				return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			} 
		}
	  
	  @GetMapping("/customers")
	 public List<Customer> getAllCustomers(){
		  return customerRepository.findAll();
	  }
	 
	  
	  @GetMapping("/get-status/{consignmentNumber}")
	  public ResponseEntity<String> getStatus(@PathVariable String consignmentNumber) {   //specify the type of the response body.
	      // Remove leading and trailing spaces from the consignment number
	      String trimmedConsignmentNumber = consignmentNumber.trim();

	      // Retrieve the customer by consignment number from the database
	      Customer customer = customerRepository.findByConsignmentNumber(trimmedConsignmentNumber);

	      if (customer != null) {
	          String status = customer.getStatus();
	          String response = "Consignment Number: " + trimmedConsignmentNumber + ", Status: " + status;
	          return new ResponseEntity<>(response, HttpStatus.OK);
	      } else {
	          return new ResponseEntity<>("Customer not found for the provided consignment number.", HttpStatus.NOT_FOUND);
	      }
	  }

		/*
		 * @GetMapping("/all-consignment-numbers") public ResponseEntity<List<Customer>>
		 * getAllConsignmentNumbersWithNames() { // Fetch all customers from the
		 * database List<Customer> customers = customerRepository.findAll(); if
		 * (!customers.isEmpty()) { return new ResponseEntity<>(customers,
		 * HttpStatus.OK); } else { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
		 * }
		 */
	  
	  @GetMapping("/all-consignment-numbers")
	    public ResponseEntity<List<String>> getAllConsignmentNumbers() {
	        // Fetch all consignment numbers from the database
	        List<String> consignmentNumbers = customerRepository.getAllConsignmentNumbers();

	        // Check if consignment numbers exist
	        if (!consignmentNumbers.isEmpty()) {
	            return new ResponseEntity<>(consignmentNumbers, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	  
	  @DeleteMapping("/delete-consignment/{consignmentNumber}")
	    public ResponseEntity<String> deleteConsignment(@PathVariable String consignmentNumber) {
	        // Find the customer by consignment number
	        Customer customer = customerRepository.findByConsignmentNumber(consignmentNumber);

	        if (customer != null) {
	            // Delete the customer from the database
	            customerRepository.delete(customer);
	            return new ResponseEntity<>("Consignment number deleted successfully.", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Consignment number not found.", HttpStatus.NOT_FOUND);
	        }
	    }
	  
	  @PutMapping("/update-status/{consignmentNumber}")
	    public ResponseEntity<String> updateStatus(
	            @PathVariable String consignmentNumber,
	            @RequestBody String newStatus
	    ) {
	        // Find the customer by consignment number
	        Customer customer = customerRepository.findByConsignmentNumber(consignmentNumber);

	        if (customer != null) {
	            // Update the status
	            customer.setStatus(newStatus);
	            customerRepository.save(customer);
	            String responseMessage = "Status updated successfully. New Status: " + newStatus;
	            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	            
	        } else {
	            return new ResponseEntity<>("Consignment number not found.", HttpStatus.NOT_FOUND);
	        }
	    }
	}
	
	






	 

