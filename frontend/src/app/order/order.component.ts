import { Component, OnInit } from '@angular/core';
import { OrderService } from '../services/order.service';
import { OrderResp } from '../model/OrderResp';





@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})


export class OrderComponent implements OnInit {
  orders: any[] = [];

  constructor(private orderService: OrderService) {}

  ngOnInit() {
    const userId = localStorage.getItem('user_id');

    if (userId !== null) {
      this.orderService.getOrdersByUserId(userId).subscribe(
        (response: any) => {
        console.log(response.data);
          this.orders = response.data;

        },
        (error) => {
          console.error(error);
        }
      );
    }
  }
}
