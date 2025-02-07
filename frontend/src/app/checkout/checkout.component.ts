import { CartResp } from './../model/CartResp';
import { Component, OnInit } from '@angular/core';
import {  CartService } from 'src/app/services/cart.service';
import { CartItem } from '../model/CartItem';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  cartItems: any[] = [];
  totalPrice: number = 0;

  constructor(private cartService: CartService) {}

  ngOnInit() {
    console.log('Checkout component initialized.');

    const userId = localStorage.getItem('user_id');
    const jwt = localStorage.getItem('access_token');

    if (userId && jwt) {
      const userIdNumber = +userId;
      console.log('User ID number:', userIdNumber);

      this.cartService.getCartItems(userIdNumber).subscribe(
        (carts: any) => {
          console.log('Received cart data:', carts);

          this.cartItems = carts.map((item: any) => ({
            productId: item.cartId,
            productName: item.productName,
            color: item.color,
            size: item.size,
            price: item.price,
            quantity: item.quantity,
            subTotal: item.price * item.quantity
          }));

          carts.forEach((elem: any) => {
            if (elem.price && elem.quantity) {
              this.totalPrice += elem.price * elem.quantity;
            }
          });

        },
        (error: any) => {
          console.error('Error fetching cart items:', error);
        }
      );
    } else {
      console.error('User ID or JWT token not found in local storage.');
    }
  }

  removeCartItem(productId: number) {
    const userId = localStorage.getItem('user_id');

    if (userId) {
      this.cartService.removeCartItem(productId).subscribe(
        (response: any) => {
          console.log(response);
          window.location.reload();
        },
        (error: any) => {
          console.error('Error removing cart item:', error);
        }
      );
    }
  }
}
