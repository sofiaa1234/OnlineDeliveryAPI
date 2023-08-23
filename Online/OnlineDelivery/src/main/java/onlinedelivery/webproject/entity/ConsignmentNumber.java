package onlinedelivery.webproject.entity;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class ConsignmentNumber {
    public String generateConsignmentNumber() {
        // Generate a random consignment number logic
        return "CONSIGNMENT NUMBER: "+new Random().nextInt(10000);
    }
}
