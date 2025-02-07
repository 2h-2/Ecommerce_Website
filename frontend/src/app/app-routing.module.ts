import { ProductListComponent } from './admin/ptoduct-list/ptoduct-list.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { ProductComponent } from './product/product.component';
import { UserComponent } from './user/user.component';
import { PaymentComponent } from './payment/payment.component'
import { OrderComponent } from './order/order.component';
import { AddProductComponent } from './admin/add-product/add-product.component';
import { EditProductComponent } from './admin/edit-product/edit-product.component';
import { OrderDetailsComponent } from './admin/order-details/order-details.component';
import { OrderAdminComponent } from './admin/order-admin/order-admin.component';
import { CategoryComponent } from './admin/category/category.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'checkout', component: CheckoutComponent },
  { path: 'product/:id', component: ProductComponent },
  { path: 'user', component: UserComponent },
  { path: 'payment', component: PaymentComponent },
  { path: 'order', component: OrderComponent },
  { path: 'admin/order', component: OrderAdminComponent },
  { path: 'admin/category', component: CategoryComponent },
  { path: 'orderDetails/:id', component: OrderDetailsComponent },
  { path: 'admin/add-product', component: AddProductComponent },
  { path: 'admin/edit-product', component: EditProductComponent },
  { path: 'admin/product', component: ProductListComponent }
];
//AddProductComponent
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
