package com.ecomerce.backend.service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomerce.backend.error.ResourceNotFoundException;
import com.ecomerce.backend.model.dto.CartDto;
import com.ecomerce.backend.model.dto.CartResponse;
import com.ecomerce.backend.model.dto.CheckoutDto;
import com.ecomerce.backend.model.entity.Cart;
import com.ecomerce.backend.model.entity.Color;
import com.ecomerce.backend.model.entity.Order;
import com.ecomerce.backend.model.entity.OrderItem;
import com.ecomerce.backend.model.entity.Product;
import com.ecomerce.backend.model.entity.Size;
import com.ecomerce.backend.model.entity.User;
import com.ecomerce.backend.repository.CartRepo;
import com.ecomerce.backend.repository.ColorRepo;
import com.ecomerce.backend.repository.OrderItemRepo;
import com.ecomerce.backend.repository.OrderRepo;
import com.ecomerce.backend.repository.ProductRepo;
import com.ecomerce.backend.repository.SizeRepo;
import com.ecomerce.backend.repository.UserRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;
    
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ColorRepo colorRepo;

    @Autowired
    private SizeRepo sizeRepo;


    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    public void save(CartDto cartDto){
        Cart cart = Cart.toCart(cartDto);
        Cart cartObj = cartRepo.findByProductIdAndColorIdAndSizeIdAndUserId(cart.getProductId(),cart.getColorId(),cart.getSizeId(),cart.getUserId());
        
        System.out.println(cartObj);

        if(cartObj != null){
            String jpql = "UPDATE Cart c SET c.quantity = :quantity WHERE c.userId = :userId AND c.color = :color AND c.size = :size AND c.productId = :productId";
            entityManager.createQuery(jpql)
            .setParameter("quantity", cart.getQuantity() + cartObj.getQuantity())
            .setParameter("userId", cart.getUserId())
            .setParameter("color", cart.getColorId())
            .setParameter("size", cart.getSizeId())
            .setParameter("productId", cart.getProductId())
            .executeUpdate();
        }else{
            cartRepo.save(cart);
        }
        
    }

    public long getCartItemCountForUser(Long userId) {
        return cartRepo.countByUserId(userId);
    }

    public List<CartResponse> getCartByUser(Long id){
        List<Cart> carts =  cartRepo.findByUserId(id);

        if(!carts.isEmpty()){
            List<CartResponse> cartResponse = new ArrayList<CartResponse>();
            carts.stream().forEach(cart -> {
                
                Optional<Product> pro = productRepo.findById( cart.getProductId());
                if(!pro.isPresent()){
                    throw new ResourceNotFoundException("No product item found for this Id !" );
                }
                
                Optional<Color> color = colorRepo.findById( cart.getColorId());
                if(!color.isPresent()){
                    throw new ResourceNotFoundException("No color found for this Id !" );
                }

                Optional<Size> size = sizeRepo.findById( cart.getSizeId());
                if(!color.isPresent()){
                    throw new ResourceNotFoundException("No size found for this Id !" );
                }
                
                CartResponse resCart = new CartResponse();
                resCart.setCartId(cart.getCartId());
                resCart.setProductName(pro.get().getName());
                resCart.setQuantity(cart.getQuantity());
                resCart.setPrice(pro.get().getUnitPrice());
                resCart.setColor(color.get().getName());
                resCart.setSize(size.get().getName());
                resCart.setCreatedAt(cart.getCreatedAt());
                resCart.setUpdatedAt(cart.getUpdatedAt());

                cartResponse.add(resCart);
            });
            return cartResponse;
        }
        return null;
    }
    

    public boolean isCartExist(Long id) {
        return cartRepo.findById(id).isPresent();
    }


    public void delete(Long id){
        cartRepo.deleteById(id);
    }


    @Transactional
    public void checkout(Long id, CheckoutDto dto){
        List<Cart> carts =  cartRepo.findByUserId(id);
        Integer userId = id.intValue();  
        System.out.println("////////savedOrder"+userId);
        Optional<User> user = userRepo.findById(userId);
        
        Order order = new Order(dto);
        order.setUser(user.get()); 
        
        Order savedOrder = orderRepo.save(order);
        
         
        if(!carts.isEmpty()){
            carts.stream().forEach(cart -> {
                
                Optional<Color> color = colorRepo.findById( cart.getColorId());
                if(!color.isPresent()){
                    throw new ResourceNotFoundException("No color found for this Id !" );
                }

                Optional<Size> size = sizeRepo.findById( cart.getSizeId());
                if(!color.isPresent()){
                    throw new ResourceNotFoundException("No size found for this Id !" );
                }
                
                Optional<Product> pro = productRepo.findById( cart.getProductId());
                if(!pro.isPresent()){
                    throw new ResourceNotFoundException("No product item found for this Id !" );
                }

                Product product = pro.get();
                
                BigDecimal quantity = BigDecimal.valueOf(cart.getQuantity()) ;
                BigDecimal total = BigDecimal.ZERO; 
                BigDecimal unitPrice = pro.get().getUnitPrice();
                total = total.add(quantity.multiply(unitPrice)); //  total


                OrderItem item = new OrderItem();
                item.setName(product.getName());
                item.setDescription(product.getDescription());
                item.setColor(color.get().getName());
                item.setSize(size.get().getName());
                item.setQuantity(cart.getQuantity());
                item.setPrice(product.getUnitPrice());
                item.setSubtotal(total);
                item.setOrder(savedOrder);
                
                orderItemRepo.save(item);
                cartRepo.delete(cart); 
            });
        }
    
    }
}
