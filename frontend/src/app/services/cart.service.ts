import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { CartItem } from '../model/CartItem';
import { CartResp } from '../model/CartResp';



@Injectable({
  providedIn: 'root'
})
export class CartService {
  private baseApiUrl = 'http://localhost:8080/api/cart';

  constructor(private http: HttpClient) {}

  addToCart(cartItem: CartItem){
        return this.http.post<any>(`${this.baseApiUrl}/add`, cartItem).pipe(
          catchError((error) => {
            console.error('Error adding item to cart:', error);
            return throwError(() => new Error('Failed to add item to cart'));
          })
        );
  }

  updateCartItemCount(count: number) {
    console.log('Updated cart item count:', count);
  }

  getCartItemsCount(userId: number): Observable<number> {

    const apiUrl = `${this.baseApiUrl}/count/${userId}`;
    return this.http.get<any>(apiUrl);
  }

  getCartItems(userId: number): Observable<CartResp[]> {

    const apiUrl = `${this.baseApiUrl}/get/${userId}`;
    return this.http.get<CartResp[]>(apiUrl).pipe(
          catchError((error) => {
            console.error('Error fetching cart details:', error);
            return throwError(() => error);
          })
        );
  }

  removeCartItem(productId: number): Observable<CartItem[]> {
    const apiUrl = `${this.baseApiUrl}/del/${productId}`;
    return this.http.delete<any>(apiUrl);
  }
}
