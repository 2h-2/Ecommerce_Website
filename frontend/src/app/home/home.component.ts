import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService, Product } from 'src/app/services/product.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  products: Product[] = [];
  constructor(private productService: ProductService,
              private router: Router) { }

  ngOnInit() {
    this.productService.getAll()
      .subscribe((products: Product[]) => {
        console.log(products);
        this.products = products;
      });
  }

  viewDetails(productId: number) {
    this.router.navigate(['/product', productId]);
  }
}

/**
<app-pagination  [currentPage]="page" ></app-pagination>

 <div class="container">
  <div class="container text-center">
    <h1 class="display-5">Products</h1>
  </div>
  <div class="row">
    <div class="col-md-3 mb-4" *ngFor="let product of products">
      <div class="card p-2" [routerLink]="['/product', product.id]" style="cursor: pointer;">
        <img [src]="product.imgUrl" id="productImg" alt="{{ product.name }}">
        <div class="card-body p-2">
          <h6 class="card-title">{{ product.name }}</h6>
          <p class="card-text">${{ product.price }}</p>
        </div>
      </div>
    </div>
  </div>
</div>
 */
