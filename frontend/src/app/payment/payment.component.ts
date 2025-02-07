import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PaymentService } from '../services/payment.service';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent {

  paymentDetails = {
    name: '',
    email: '',
    phone: '',
    cardNumber: '',
    expiry: '',
    cvv: '',
    address: '',
    city: '',
    governorate: '',
    shipping: 0,
    total: 0 // Example total value
  };

  shippingCosts: { [key: string]: number } = {
    Cairo: 65,
    Giza: 65,
    'Al-Menya': 90,
    Other: 90,
    Asiout: 100,
    Fayom: 100,
    'Luxor & Aswan': 120,
  };

  subtotal: number = 0;

  constructor(private route: ActivatedRoute,
    private paymentService: PaymentService
  ) {}
  ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      this.paymentDetails.total = +params['total'] || 0;;
      this.subtotal = +params['total'] || 0;
    });
  }
  updateShipping() {
    const selectedGovernorate = this.paymentDetails.governorate;

    if (selectedGovernorate in this.shippingCosts) {
      this.paymentDetails.shipping = this.shippingCosts[selectedGovernorate];
    } else {
      this.paymentDetails.shipping = 0; // Default shipping cost for invalid selections
    }


    this.paymentDetails.total = this.subtotal + this.paymentDetails.shipping;
  }

  onSubmit() {
    if (this.paymentDetails.name && this.paymentDetails.email) {

      const userId = localStorage.getItem('user_id') || '';

      console.log('Payment Details:', this.paymentDetails);
      this.paymentService.checkout(userId, this.paymentDetails).subscribe(
       (response :any) =>{
          console.log('Payment successful!');
          console.log(response);
          window.location.reload();
        }
      )

    } else {
      alert('Please fill out all required fields.');
    }
  }

}



/*

import { Component } from '@angular/core';
import { PaymentService } from '../services/payment.service';
import { loadStripe, Stripe, StripeCardElement } from '@stripe/stripe-js';
import { Router } from '@angular/router';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent {
  stripe: Stripe | null = null;
  card: StripeCardElement | null = null;

  constructor(private paymentService: PaymentService, private router: Router) {}

  async ngOnInit() {
    this.stripe = await loadStripe('pk_test_51NiZKsLtTFoBOb8wFWpjprx5p0Wa26RUc0qGXK5HKZNSUrdPzIDPQSQadSoQkdQ8CgED8arZIAuygiiRmTiWeJ0q00PF5pum1G');

    if (this.stripe) {
      const elements = this.stripe.elements();
      this.card = elements.create('card');
      this.card.mount('#card-element');
    }
  }

  async onSubmit() {
    if (this.stripe && this.card) {
      const { paymentMethod, error } = await this.stripe.createPaymentMethod({
        type: 'card',
        card: this.card,
      });

      if (error) {
        console.error(error);
      } else if (paymentMethod) {
        const userId = localStorage.getItem('user_id');
        const access_token = localStorage.getItem('access_token');

        if (userId !== null && access_token !== null) {
          this.paymentService.checkout(userId, access_token, paymentMethod.id).subscribe(
            (response) => {
              alert("Payment successful!");
              this.router.navigate(['/order']);
              console.log(response);
            },
            (error) => {
              console.error(error);
            }
          );
        }
      }
    }
  }
}*/
