import { CategoryServiceService, Category } from './../../services/category-service.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from 'src/app/services/product.service';


@Component({
  selector: 'app-category',
  standalone: false,
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})
export class CategoryComponent {
  category: any = {  name: '', description: '' };
  categories: Category[] = [];

  categoryForm!: FormGroup;
  message: string = '';

  constructor(private fb: FormBuilder, private categoryService: CategoryServiceService, private productService:ProductService) {}

  ngOnInit(): void {
    // Initialize the form
    this.categoryForm = this.fb.group({
      name: ['', Validators.required],  // Add validation
      description: ['', Validators.required]
    });


    this.getCategories();
  }


  getCategories(): void {
    this.productService.getAllCategories().subscribe((category: any[]) => {
      console.log(category);
      this.categories = category;
      console.log(this.categories);
  });
  }

  deleteCategory(id:number): void{

  }
  onSubmit(): void {
    if (this.categoryForm.valid) {
      console.log('Category Data:', this.categoryForm.value);
      const formData = new FormData();
      formData.append("category", new Blob([JSON.stringify(this.categoryForm.value)], { type: "application/json" }));

      this.categoryService.addCategory(formData).subscribe({
        next: (response:any) => {
          this.message = 'Category added successfully!';
        },
        error: (err:any) => {
          console.log(err);
          this.message = 'Error adding category: ' + err.message;
        }
      });

      this.categoryForm.reset();
      window.location.reload();
    } else {
      this.message = 'Please fill in all fields!';
    }
  }

}
