import { Color, Size } from './../services/product.service';
export interface CartItem {
  product_id: number;
  quantity: number;
  price: number;
  user_id:number;
  color_id: number;
  size_id: number;
}
