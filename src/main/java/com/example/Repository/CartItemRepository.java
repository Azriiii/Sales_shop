package com.example.Repository;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.model.cartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<cartItem, Long> {


    @Query("select c from cartItem c where c.cart=:cart and c.product=:product and c.userId=:userId and c.size=:size")
    public cartItem isCartItemExist(@Param("cart") Cart cart,
                                    @Param("product") Product product,
                                    @Param("size") String size,
                                    @Param("userId") Long userId);

    @Query("select c from Cart c where c.user.id =:userId")
    public Cart findcartItemById(@Param("userId") Long userId);

}
