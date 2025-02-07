import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Product {
  id: number;
  name: string;
  description: string;
  material: string;
  style: string;
  unitPrice: number;
  images: any[];
  sizes: any[];
  colors: any[];
  category: Category
}
export class SizeStock {
    name: string; // Assuming 'char' in Java corresponds to a single character string in TypeScript
    stock: number;
    inventoryId: number;

    constructor(size: string, inventoryId: number, stock: number) {
        this.name = size;
        this.stock = stock;
        this.inventoryId = inventoryId;
      }
}

export class Color {
    id: number;
    name: string;

    constructor(id: number,colorName: string,) {
        this.id = id;
        this.name = colorName;
    }
}

export class Size {
  id: number;
  name: string;

  constructor(id: number,colorName: string) {
      this.id = id;
      this.name = colorName;
  }
}

export class Image {
    imageURL: string;

    constructor(imageURL: string) {
        this.imageURL = imageURL;
    }
}

export class Category {
  id: number;
  categoryName: string;

  constructor(id: number,categoryName: string) {
      this.id = id;
      this.categoryName = categoryName;
  }
}

export class ProductResponse {
    id: number;
    name: string;
    description: string;
    unitPrice: number; // Using 'number' for simplicity as TypeScript doesn't have a BigDecimal equivalent
    images: Image[] = [];
    colors: Color[] = [];
    sizes: Size[] = [];
    category: Category 

    constructor(
        id: number,
        name: string,
        description: string,
        price: number,
        images: Image[],
        colors: Color[],
        sizes: Size[],
        category: Category
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unitPrice = price;
        this.images = images;
        this.colors = colors;
        this.sizes = sizes;
        this.category = category;
    }
}


@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/api/product';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/list`);
  }

  addProduct(formData: FormData) {
    return this.http.post(`${this.apiUrl}/add`, formData);
  }


  getProductById(productId: number): Observable<ProductResponse> {

    return this.http.get<ProductResponse>(`${this.apiUrl}/getpro`, {
      params: {
        productId: productId.toString()
      }
    });
  }

  addToCart(productId: number, quantity: number): Observable<any> {
    const userId = localStorage.getItem('user_id');

    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + localStorage.getItem('access_token')
    });

    return this.http.post<number>(`http://localhost:8080/api/v1/cart/${userId}/${productId}/${quantity}`, {}, { headers });
  }

  getAllProducts(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`);
  }

  getAllSizes(): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8080/api/sizes`);
  }

  getAllColors(): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8080/api/colors`);
  }

  getAllCategories(): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8080/api/category`);
  }

  delete(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/del`);
  }

}
