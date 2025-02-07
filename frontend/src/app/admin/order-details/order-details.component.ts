import { Component } from '@angular/core';
import { User } from 'src/app/services/user.service';
import {Observable} from "rxjs";
import {OrderService} from "../../services/order.service";
import {ActivatedRoute} from "@angular/router";

export class Item{
  photo: string = '';
  name: string = '';
  description: string = '';
  price: number = 0;
  quantity: number = 0;
  total: number = 0;
}
@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css']
})

export class OrderDetailsComponent {
  constructor(private orderService: OrderService,
    private route: ActivatedRoute) {
}

  total : number = 0;
  order: any ;


ngOnInit() {

this.orderService.show(this.route.snapshot.paramMap.get('id')).subscribe(
      (res: any) => {
      if (res) {
        console.log(res.items);
        this.order = res.items;
      }

      this.order.forEach((elem: any) => {
        if (elem.price && elem.quantity) {
          this.total+= elem.price * elem.quantity;
        }
      });
  });

 
  this.calculateTotal();
}
calculateTotal(): void {
  this.total = 0; 
  
    this.order.forEach((element: any) => {
      console.log(element.quantity)
      this.total += (element.quantity * element.price); // Multiply quantity by price for each item
    });

}
}
