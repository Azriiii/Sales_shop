package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "cart_items")
    private Set<cartItem> cartItems = new HashSet<>();

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "total_item")
    private int totalItem;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int discount;

    private int totalDiscountedPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart cart)) return false;
        return Double.compare(getTotalPrice(), cart.getTotalPrice()) == 0 && getTotalItem() == cart.getTotalItem() && getDiscount() == cart.getDiscount() && getTotalDiscountedPrice() == cart.getTotalDiscountedPrice() && Objects.equals(getId(), cart.getId()) && Objects.equals(getCartItems(), cart.getCartItems()) && Objects.equals(getUser(), cart.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCartItems(), getTotalPrice(), getTotalItem(), getUser(), getDiscount(), getTotalDiscountedPrice());
    }
}
