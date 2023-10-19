package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orderitem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;
    private String size;
    private int quantity;
    private Integer price;
    private Integer discountedPrice;
    private Long userId;
    private LocalDateTime deliveryDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Orderitem orderitem)) return false;
        return getQuantity() == orderitem.getQuantity() && Objects.equals(getId(), orderitem.getId()) && Objects.equals(getProduct(), orderitem.getProduct()) && Objects.equals(getSize(), orderitem.getSize()) && Objects.equals(getPrice(), orderitem.getPrice()) && Objects.equals(getDiscountedPrice(), orderitem.getDiscountedPrice()) && Objects.equals(getUserId(), orderitem.getUserId()) && Objects.equals(getDeliveryDate(), orderitem.getDeliveryDate());
    }

    @Override
    public String toString() {
        return "Orderitem{" +
                "id=" + id +
                ", product=" + product +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", discountedPrice=" + discountedPrice +
                ", userId=" + userId +
                ", deliveryDate=" + deliveryDate +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProduct(), getSize(), getQuantity(), getPrice(), getDiscountedPrice(), getUserId(), getDeliveryDate());
    }
}
