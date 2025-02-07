import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private apiUrl = 'http://localhost:8080/api/cart';

  constructor(private http: HttpClient) {}

  checkout(userId: string, paymentDetails: any) {

    console.log('Checkout Payload:', paymentDetails);
    return this.http.post(`${this.apiUrl}/checkout/${userId}`, paymentDetails);
  }
}
