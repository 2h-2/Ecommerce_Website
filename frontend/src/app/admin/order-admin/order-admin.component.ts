import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Order, OrderService} from "../../services/order.service";
import {UserService} from "../../services/user.service";
import {Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";



export enum OrderStatus {
  "New",
  "Finished",
  "Cenceled"
}
@Component({
  selector: 'app-order-admin',
  templateUrl: './order-admin.component.html',
  styleUrls: ['./order-admin.component.css']
})

export class OrderAdminComponent {

  OrderStatus = OrderStatus;
  orders: Order[] = [];

  constructor(private httpClient: HttpClient,
              private orderService: OrderService,
              private userService: UserService,
              private route: ActivatedRoute
  ) {
  }

  ngOnInit() {


    this.orderService.getAll().subscribe(
      (response: any) => {
        console.log(response)
        this.orders = response.data;
      },
      (error: any) => {
        console.error('Error fetching cart count:', error);
      }
    );

}

  update() {

  }


  cancel(order: Order) {
    this.orderService.cancel(order.id).subscribe(
      (res: Order) => {
      if (res) {
          order.orderStatus = res.orderStatus;
      }
  });
  }

  finish(order: Order) {
    this.orderService.finish(order.id).subscribe(
      (res: Order) => {

      if (res) {
          order.orderStatus = res.orderStatus;
          console.log(res)
      }
  },
  (error: any) => {
    console.error('Error fetching cart count:', error);
  });
  }

  ngOnDestroy(): void {
      //this.querySub.unsubscribe();
  }
}
