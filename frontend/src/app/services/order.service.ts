import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OrderResp } from '../model/OrderResp';
import { Observable } from 'rxjs';

export class Order{
  id: number = 0;
  buyerName: string = '';
  buyerEmail: string = '';
  buyerPhone: string = '';
  buyerAddress: string = '';
  createTime: Date | undefined ;
  orderAmount: number = 0;
  orderStatus: number = 0;
}

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiUrl = 'http://localhost:8080/api/order';

  constructor(private http: HttpClient) {}

  getOrdersByUserId(userId: string): Observable<Order[]>{

    return this.http.get<Order[]>(`${this.apiUrl}/${userId}`);
  }

  getAll(): Observable<Order[]>{

    return this.http.get<Order[]>(`${this.apiUrl}/get`);
  }

  show(id: any): Observable<Order> {
    return this.http.get<Order>(`${this.apiUrl}/get/${id}`).pipe(
    );
}

  cancel(id: number): Observable<any> {
    return this.http.patch<any>(`${this.apiUrl}/cancel/${id}`, null).pipe(

    );
}

  finish(id: number): Observable<any> {
    return this.http.patch<any>(`${this.apiUrl}/finish/${id}`, null).pipe(
    );
}
}
