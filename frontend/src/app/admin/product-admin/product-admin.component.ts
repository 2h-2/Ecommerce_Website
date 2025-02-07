import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-product-admin',
  templateUrl: './product-admin.component.html',
  styleUrls: ['./product-admin.component.css']
})
export class ProductAdminComponent implements OnInit{
  productForm!: FormGroup;
  categories = ['Men', 'Women', 'Kids', 'Accessories'];
  uploadedImage: File | null = null;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.productForm = this.fb.group({
      title: ['', Validators.required],
      category: ['', Validators.required],
      featured: ['No', Validators.required],
      price: ['', [Validators.required, Validators.min(1)]],
      discount: ['', [Validators.required, Validators.min(0), Validators.max(100)]],
      sizes: ['', Validators.required],
      condition: ['new', Validators.required],
      brand: ['', Validators.required],
      stock: ['', [Validators.required, Validators.min(0)]],
    });
  }

  onImageUpload(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.uploadedImage = file;
    }
  }

  onSubmit() {
    if (this.productForm.invalid) {
      alert('Please fill all the required fields!');
      return;
    }

    const formData = new FormData();
    formData.append('data', JSON.stringify(this.productForm.value));
    if (this.uploadedImage) {
      formData.append('image', this.uploadedImage);
    }

    console.log('Submitted Product:', this.productForm.value);
    // Implement the API call to save the product
  }
}
