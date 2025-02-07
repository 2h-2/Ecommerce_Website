import { CartItem } from './../model/CartItem';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService , Product, ProductResponse, Size, Color } from 'src/app/services/product.service';
import { CartService } from '../services/cart.service';
import { catchError } from 'rxjs/operators';
import { EMPTY } from 'rxjs';


@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})

export class ProductComponent implements OnInit {
  product : ProductResponse | null = null;
  selectedQuantity: number = 1;
  title: string = 'Product Detail';
  imgUrl: string = '';
  count: number = 5;
  convertedProduct = {};
  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private cartService: CartService,
    private cdRef: ChangeDetectorRef,
    private router: Router
  ) {}

  selectedColor: Color | null = null;
  sizes: Size[] = [];
  selectedSize: Size | null = null;
  selectedStockPercentage: number = 0;
  quantity: number = 1;
  cart: CartItem[] = [];


  ngOnInit() {
    const productId = this.route.snapshot.params['id'];
    this.productService.getProductById(productId)
      .pipe(
        catchError(err => {
          console.error('Error fetching product details:', err);
          return EMPTY;
        })
      )
      .subscribe(
        (product: ProductResponse) => {
          console.log(product);
          this.product = product;
          this.selectedColor = this.product?.colors[0] || '' ;
         // this.getSizesByColor(this.selectedColor);


          console.log(this.getSizesByColor("red"));

        }
      );
  }


  // Function to handle color selection and update available sizes
  getSizesByColor(colorName: string): void {
    const color = this.product?.colors.find(c => c.name.toLowerCase() === colorName.toLowerCase());
    if (color) {
      this.selectedColor = color;
      //this.selectedSize = null;  // Reset selected size when color changes
    }

  }


  selectSize(size: Size): void {
    this.selectedSize = size;
    console.log(this.selectedSize);
    console.log(this.selectedColor);
  }

  decreaseQuantity() {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  increaseQuantity() {
   /* const maxStock = this.selectedSize ? this.selectedSize.stock : this.selectedStock;
    if (this.quantity < maxStock) {

    }*/
    this.quantity++;
  }



addToCart(): void {
  if (!this.selectedColor || !this.selectedSize) {

    alert("Please select a color and size.");
    return;
  }

  const newItem: CartItem = {
    product_id: this.product!.id,
    quantity: this.quantity,
    price: this.product!.unitPrice,
    color_id: this.selectedColor.id,
    size_id: this.selectedSize.id,
    user_id: Number(localStorage.getItem('user_id')) || 0,
  };


  // Check if the item already exists in the cart
  /*const existingItemIndex = this.cart.findIndex(
    (item) => item.inventory_id === newItem.inventory_id
  );

  if (existingItemIndex >= 0) {
    // Update quantity if item exists
    this.cart[existingItemIndex].quantity += newItem.quantity;
  } else {
    // Add new item
    this.cart.push(newItem);

  }*/

  const userId = localStorage.getItem('user_id');

  if (userId) {

    this.cartService.addToCart(newItem).subscribe(
      (response: any) => {
        console.log(response)
      },
      (error: any) => {
        console.error('Error fetching cart count:', error);
      }
    );

    this.cartService.getCartItemsCount(+userId).subscribe(
      (count: number) => {
        alert("Product added to cart.");
        this.cartService.updateCartItemCount(count);

        this.cdRef.detectChanges();
        window.location.reload();
      },
      (error: any) => {
        console.error('Error fetching cart count:', error);
      }
    );
  }else{
    this.router.navigate(['/login']);
  }

  console.log(this.cart);
  //alert(`${newItem.quantity} of ${this.selectedColor} ${this.selectedSize.name} added to the cart.`);
}

  /*addToCart(productId: number, quantity: number) {
    this.productService.addToCart(productId, quantity).subscribe(
      response => {
        console.log('Product added to cart:', response);

        const userId = localStorage.getItem('user_id');

        if (userId) {
          this.cartService.getCartItemsCount(+userId).subscribe(
            (count: number) => {
              alert("Product added to cart.");
              this.cartService.updateCartItemCount(count);
              this.cdRef.detectChanges();
              window.location.reload();
            },
            (error: any) => {
              console.error('Error fetching cart count:', error);
            }
          );
        }
      },
      (error: any) => {
        console.error('Error adding product to cart:', error);
      }
    );
  }*/
}
