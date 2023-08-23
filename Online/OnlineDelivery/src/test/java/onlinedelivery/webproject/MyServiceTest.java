package onlinedelivery.webproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.tomcat.jni.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import onlinedelivery.webproject.controller.CustomerController;
import onlinedelivery.webproject.entity.ConsignmentNumber;
import onlinedelivery.webproject.entity.Customer;
import onlinedelivery.webproject.repository.CustomerRepository;


 @SpringBootTest   //testing Spring Boot applications
public class MyServiceTest {

     @InjectMocks //inject mock of customercontroller(i.e creates its object)
     private CustomerController customerController;
     @Mock //all those things that are dependent in customer controller are written in mock
    private CustomerRepository customerRepository;

    @Mock
    private ConsignmentNumber consignmentNumber;
// ----------------------------------------------------------------------------------------------------------------
// POSTMAPPING SAVE CUSTOMER TESTING
    @Test //FOR TRY STATEMENT
    public void testSaveCustomer_Success()
    {
        // Arrange: Preperation Phase -->you set up the initial conditions and define the expected behavior for the test
        Customer customer = new Customer(); //instance of customer entity
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setPhone("85934302520");
        customer.setBilladdress("IXR");
        customer.setShipaddress("BLR");
        customer.setDate("10-09-23");
        customer.setStatus("TRANSIT");
        String generatedConsignmentNumber = "CONSIGNMENT NUMBER: 1234"; //define the expected value
        when(consignmentNumber.generateConsignmentNumber()).thenReturn(generatedConsignmentNumber);
        when(customerRepository.save(customer)).thenReturn(customer); //instruct mockito to return customer object itself which is same as save customer in repo

        // Act: Execution Phase -->you call the actual method or code that you want to test
        ResponseEntity<Customer> responseEntity = customerController.saveCustomer(customer); //invoke the saveCustomer method by passing the customer object.

        // Assert: Verification Phase -->verify that code you're testing behaves as expected and meets the defined expectations
        verify(consignmentNumber, times(1)).generateConsignmentNumber();
        verify(customerRepository, times(1)).save(customer);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()); //http status is correct and we get status code
        assertEquals(generatedConsignmentNumber, responseEntity.getBody().getConsignmentNumber());
    }

     @Test  //FOR CATCH STATEMENT
     public void testSaveCustomer_Exception() {
         // Arrange
         Customer customer = new Customer();
         when(consignmentNumber.generateConsignmentNumber()).thenReturn("CONSIGNMENT123");
         when(customerRepository.save(customer)).thenThrow(new RuntimeException("Something went wrong."));

         // Act
         ResponseEntity<Customer> responseEntity = customerController.saveCustomer(customer);

         // Assert
         verify(consignmentNumber, times(1)).generateConsignmentNumber();
         verify(customerRepository, times(1)).save(customer);
         assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
         assertNull(responseEntity.getBody());
     }

// --------------------------------------------------------------------------------------------------------------
     // GETMAPPING GET ALL CUSTOMERS TESTING
     @Test
     public void testGetAllCustomers() {
         // Arrange
         List<Customer> expectedCustomers = new ArrayList<>();
         expectedCustomers.add(new Customer("sofia", "sofia@example.com","9387593242","IXR","BLR","2023-08-17","TRANSIT"));
         expectedCustomers.add(new Customer("Alice", "alice@example.com","9387593242","IXR","BLR","2023-08-17","TRANSIT"));

         when(customerRepository.findAll()).thenReturn(expectedCustomers);

         // Act
         List<Customer> actualCustomers = customerController.getAllCustomers();

         // Assert
         verify(customerRepository, times(1)).findAll();
         assertEquals(expectedCustomers.size(), actualCustomers.size());

         // Check individual customer details
         for (int i = 0; i < expectedCustomers.size(); i++) {
             Customer expectedCustomer = expectedCustomers.get(i);
             Customer actualCustomer = actualCustomers.get(i);

             assertEquals(expectedCustomer.getName(), actualCustomer.getName());
             assertEquals(expectedCustomer.getEmail(), actualCustomer.getEmail());
         }
     }
// ----------------------------------------------------------------------------------------------
     // GETMAPPING GET STATUS & CONSIGNMENT NUMBER TESTING
     @Test //FOR TRY STATEMENT
     public void testGetStatus_CustomerFound()
     {
         // Arrange
         String consignmentNumber = "CONSIGNMENT NUMBER: 1234";
         Customer customer = new Customer();
         customer.setConsignmentNumber(consignmentNumber);
         customer.setStatus("Delivered");

         when(customerRepository.findByConsignmentNumber(consignmentNumber)).thenReturn(customer);

         // Act
         ResponseEntity<String> responseEntity = customerController.getStatus(consignmentNumber);

         // Assert
         verify(customerRepository, times(1)).findByConsignmentNumber(consignmentNumber);
         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
         assertEquals("Consignment Number: " + consignmentNumber + ", Status: Delivered", responseEntity.getBody());
     }

     @Test //FOR CATCH STATEMENT
     public void testGetStatus_CustomerNotFound() {
         // Arrange
         String consignmentNumber = "CONSIGNMENT123";
         when(customerRepository.findByConsignmentNumber(consignmentNumber)).thenReturn(null);

         // Act
         ResponseEntity<String> responseEntity = customerController.getStatus(consignmentNumber);

         // Assert
         verify(customerRepository, times(1)).findByConsignmentNumber(consignmentNumber);
         assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
         assertEquals("Customer not found for the provided consignment number.", responseEntity.getBody());
     }
// ---------------------------------------------------------------------------------------------------------------
     //GETMAPPING GET ALL CONSIGNMENT NUMBER TESTING
     @Test //FOR TRY STATEMENT
public void testGetAllConsignmentNumbers_Exist() {
    // Arrange
    List<String> consignmentNumbers = new ArrayList<>();
    consignmentNumbers.add("CONSIGNMENT NUMBER: 123");
    consignmentNumbers.add("CONSIGNMENT NUMBER: 456");

    when(customerRepository.getAllConsignmentNumbers()).thenReturn(consignmentNumbers);

    // Act
    ResponseEntity<List<String>> responseEntity = customerController.getAllConsignmentNumbers();

    // Assert
    verify(customerRepository, times(1)).getAllConsignmentNumbers();
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(consignmentNumbers, responseEntity.getBody());
}

     @Test //FOR CATCH STATEMENT
     public void testGetAllConsignmentNumbers_NotFound() {
         // Arrange
         when(customerRepository.getAllConsignmentNumbers()).thenReturn(new ArrayList<>());

         // Act
         ResponseEntity<List<String>> responseEntity = customerController.getAllConsignmentNumbers();

         // Assert
         verify(customerRepository, times(1)).getAllConsignmentNumbers();
         assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
         assertNull(responseEntity.getBody());
     }
// ----------------------------------------------------------------------------------------------------------
     // DELETEMAPPING DELETE CONSIGNMENT NUMBER TESTING
     @Test //FOR TRY STATEMENT
public void testDeleteConsignment_CustomerFound() {
    // Arrange
    String consignmentNumber = "CONSIGNMENT NUMBER: 123";
    Customer customer = new Customer();
    customer.setConsignmentNumber(consignmentNumber);

    when(customerRepository.findByConsignmentNumber(consignmentNumber)).thenReturn(customer);

    // Act
    ResponseEntity<String> responseEntity = customerController.deleteConsignment(consignmentNumber);

    // Assert
    verify(customerRepository, times(1)).findByConsignmentNumber(consignmentNumber);
    verify(customerRepository, times(1)).delete(customer);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("Consignment number deleted successfully.", responseEntity.getBody());
}
     @Test //FOR CATCH STATEMENT
     public void testDeleteConsignment_CustomerNotFound() {
         // Arrange
         String consignmentNumber = "CONSIGNMENT NUMBER: 123";
         when(customerRepository.findByConsignmentNumber(consignmentNumber)).thenReturn(null);

         // Act
         ResponseEntity<String> responseEntity = customerController.deleteConsignment(consignmentNumber);

         // Assert
         verify(customerRepository, times(1)).findByConsignmentNumber(consignmentNumber);
         verify(customerRepository, never()).delete(any(Customer.class)); // Ensure delete is not called
         assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
         assertEquals("Consignment number not found.", responseEntity.getBody());
     }
// ------------------------------------------------------------------------------------------------------------------
// PUTMAPPING UPDATE CONSIGNMENT NUMBER STATUS
@Test //FOR TRY STATEMENT
public void testUpdateStatus_CustomerFound() {
    // Arrange
    String consignmentNumber = "CONSIGNMENT NUMBER: 123";
    String newStatus = "In Transit";
    Customer customer = new Customer();
    customer.setConsignmentNumber(consignmentNumber);

    when(customerRepository.findByConsignmentNumber(consignmentNumber)).thenReturn(customer);

    // Act
    ResponseEntity<String> responseEntity = customerController.updateStatus(consignmentNumber, newStatus);

    // Assert
    verify(customerRepository, times(1)).findByConsignmentNumber(consignmentNumber);
    verify(customerRepository, times(1)).save(customer);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("Status updated successfully. New Status: " + newStatus, responseEntity.getBody());
    assertEquals(newStatus, customer.getStatus()); // Check that the status was updated in the customer object
}

     @Test //FOR CATCH STATEMENT
     public void testUpdateStatus_CustomerNotFound() {
         // Arrange
         String consignmentNumber = "CONSIGNMENT123";
         String newStatus = "In Transit";
         when(customerRepository.findByConsignmentNumber(consignmentNumber)).thenReturn(null);

         // Act
         ResponseEntity<String> responseEntity = customerController.updateStatus(consignmentNumber, newStatus);

         // Assert
         verify(customerRepository, times(1)).findByConsignmentNumber(consignmentNumber);
         verify(customerRepository, never()).save(any(Customer.class)); // Ensure save is not called
         assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
         assertEquals("Consignment number not found.", responseEntity.getBody());
     }

 }



 	
