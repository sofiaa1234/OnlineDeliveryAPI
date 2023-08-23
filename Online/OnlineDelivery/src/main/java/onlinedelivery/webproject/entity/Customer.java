package onlinedelivery.webproject.entity;
import javax.persistence.Entity;
import javax.persistence.Id;
//import javax.persistence.OneToOne;
import javax.persistence.Table;

//import lombok.Getter;
//import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="Customer")
@ToString
public class Customer {
	@Id
    private String name;
    private String email;
    private String phone;
    private String billaddress;
    private String shipaddress;
    private String consignmentNumber;
    private String date;
    private String status;
    

		public Customer() {
		// TODO Auto-generated constructor stub
	}

		public Customer(String string, String string2, String string3, String string4, String string5, String string6,
				String string7) {
			// TODO Auto-generated constructor stub
		}



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

		public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

		public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	

	public String getBilladdress() {
		return billaddress;
	}

	public void setBilladdress(String billaddress) {
		this.billaddress = billaddress;
	}

	public String getShipaddress() {
		return shipaddress;
	}

	public void setShipaddress(String shipaddress) {
		this.shipaddress = shipaddress;
	}

	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

		


		
}
