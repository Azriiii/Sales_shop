package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class cartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne//(fetch = FetchType.EAGER)
    private Cart cart;
    @ManyToOne//(fetch = FetchType.EAGER)
    private Product product;
    private int quantity;
    private Integer price;
    private Integer discountedPrice;
    private Long userId;
    private String size;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cartItem cartItem)) return false;
        return getQuantity() == cartItem.getQuantity() && Objects.equals(getId(), cartItem.getId()) && Objects.equals(getProduct(), cartItem.getProduct()) && Objects.equals(getPrice(), cartItem.getPrice()) && Objects.equals(getDiscountedPrice(), cartItem.getDiscountedPrice()) && Objects.equals(getUserId(), cartItem.getUserId()) && Objects.equals(getSize(), cartItem.getSize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProduct(), getQuantity(), getPrice(), getDiscountedPrice(), getUserId(), getSize());
    }

    @Override
    public String toString() {
        return "cartItem{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                ", discountedPrice=" + discountedPrice +
                ", userId=" + userId +
                ", size='" + size + '\'' +
                '}';
    }
}
