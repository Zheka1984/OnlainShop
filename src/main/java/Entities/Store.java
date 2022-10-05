package Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Store implements Comparable<Store>{
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	 @NotEmpty(message = "Product's name cannot be empty")
	 @Size(min = 2, max = 100)
	 @Column(unique = true)
	private String product; //�������� ������
	// @NotBlank(message = "quantity cannot be empty")
	 @Min(1)
	private int quantity; //���������� ������ � ��������
	// @NotBlank(message = "price cannot be empty")
	 @Min(1)
	private int price; //��������� ����� ������� ������
	 
	public void buying(int quantity) { //������������ ������������ ���������� ������
			this.quantity = this.quantity - quantity;
		}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public int compareTo(Store o) {
		return this.price - o.price;
	}
}
