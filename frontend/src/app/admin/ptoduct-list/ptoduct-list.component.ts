import { Product, ProductService } from './../../services/product.service';


import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';

@Component({
  selector: 'app-add-product',
  templateUrl: './ptoduct-list.component.html',
  styleUrls: ['./ptoduct-list.component.css'],
})
export class ProductListComponent implements OnInit {
  searchQuery: string = '';
  selectedCategory: string = '';
  // Sample Product Data
  products: Product[] = [];
  filteredProducts: any[] = [];
  categories : any[] = [];

  // Filtered Products

  constructor(private productService: ProductService) {}

  ngOnInit(): void {


    this.productService.getAll().subscribe(
      (response: Product[] )=>{

        this.products = response;
        this.filteredProducts = [...this.products];
        console.log(this.filteredProducts);
      },
      (error: any) => {
        console.error('Error fetching :', error);
      }
    );

    this.productService.getAllCategories().subscribe(
      (response: any[] )=>{
        this.categories = response;
      },
      (error: any) => {
        console.error('Error fetching :', error);
      }
    );
    this.filterProductsFun();

  }

  // Filter products based on the search query
  filterProductsFun(): void {
    this.filteredProducts = this.products.filter((product) =>
      product?.name.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
  }

  filterProducts(): void {
    console.log(this.products[0].category);
    this.filteredProducts = this.products.filter((product) =>
      product?.category.categoryName.toLowerCase().includes(this.selectedCategory.toLowerCase())
    );
  }

  // Update the filtered list whenever the search query changes
  ngOnChanges(): void {
    this.filterProductsFun();
  }

  // Edit product (to be implemented)
  editProduct(product: any): void {
    alert(`Edit product: ${product.title}`);
  }

  // Delete product (to be implemented)
  deleteProduct(product: any): void {
    if (confirm(`Are you sure you want to delete ${product.title}?`)) {
      this.productService.delete().subscribe(
        (res: any)=>{
          console.log(res);
        },
        (error: any) => {
          console.error('Error fetching :', error);
        }
      )
      this.products = this.products.filter((p) => p !== product);
      this.filterProductsFun();
    }
  }
}


/*
import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service'; // Example service for API calls

@Component({
  selector: 'app-admin-product-list',
  templateUrl: './ptoduct-list.component.html',
  styleUrls: ['./ptoduct-list.component.css']
})
export class AdminProductListComponent implements OnInit {
  products: any[] = []; // All products
  filteredProducts: any[] = []; // Products to display after filtering
  categories: string[] = ['Men', 'Women', 'Kids', 'Accessories']; // Example categories
  searchTerm: string = '';
  selectedCategory: string = '';

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  // Load products from API
  loadProducts(): void {
    this.productService.getAllProducts().subscribe((data) => {
      this.products = data;
      this.filteredProducts = data;
    });
  }

  // Filter products by search term and category
  filterProducts(): void {
    this.filteredProducts = this.products.filter((product) => {
      return (
        product.name.toLowerCase().includes(this.searchTerm.toLowerCase()) &&
        (this.selectedCategory === '' || product.category === this.selectedCategory)
      );
    });
  }

  // Edit product
  editProduct(productId: number): void {
    // Redirect to the edit product page
    console.log('Editing product with ID:', productId);
  }

  // Delete product
  deleteProduct(productId: number): void {
    if (confirm('Are you sure you want to delete this product?')) {
      /*this.productService.deleteProduct(productId).subscribe(() => {
        this.loadProducts(); // Refresh the product list after deletion
      });
    }
  }
}*/
