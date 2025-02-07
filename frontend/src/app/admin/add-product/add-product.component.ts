import { Component, OnInit, Input } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css'],
})
export class AddProductComponent {

  productForm!: FormGroup;

  availableSizes: any[] = [];
  availableCategories: any[] = [];
  availableColors: any[] = [];
  selectedSizes: any[] = [];
  selectedSizesnames: string[] = [];
  selectedColorsnames: string[] = [];
  selectedColors: any[] = [];
  selectedCatg: string = '';
  selectedFiles: File[] = [];

  constructor(private productService: ProductService,
              private fb: FormBuilder) {}

  ngOnInit(): void {
    this.productService.getAllColors().subscribe((colors: any[]) => {
        console.log(colors);
        this.availableColors = colors;
    });

    this.productService.getAllSizes().subscribe((sizes: any[]) => {
        console.log(sizes);
        this.availableSizes = sizes;
    });

    this.productService.getAllCategories().subscribe((category: any[]) => {
      console.log(category);
      this.availableCategories = category;
  });

    this.productForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required]],
      unitPrice: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      discount: ['', [Validators.pattern('^[0-9]*$')]],
      colors: [[], Validators.required],
      sizes: [[], Validators.required],
      category: ['', Validators.required],
      images: [null],
    });
  }

  onImageSelect(event: any): void {
    const files = event.target.files;
    this.selectedFiles = Array.from(event.target.files);
    if (files.length > 0) {
      console.log(files); // Handle file upload
    }
  }

  onSizeSelect(size: any, event: any): void {
    if (event.target.checked) {
      this.selectedSizes.push(size);
      this.selectedSizesnames.push(size.name);
    } else {
      this.selectedSizes = this.selectedSizes.filter((s) => s !== size);
      this.selectedSizesnames = this.selectedSizesnames.filter((s) => s !== size.name);
    }
    this.productForm.controls['sizes'].setValue(this.selectedSizes);
    console.log(this.productForm.controls['sizes'].value);
  }

  oncatgSelect(cat: any, event: any): void {
    if (event.target.checked) {
      this.productForm.controls['category'].setValue(cat);
      this.selectedCatg = cat.categoryName;

    } else {
      this.productForm.controls['category'].setValue('');
      this.selectedCatg = '';
    }

    console.log(this.productForm.controls['category'].value);
  }

  onColorSelect(color: any, event: any): void {
    if (event.target.checked) {
      this.selectedColors.push(color);
      this.selectedColorsnames.push(color.name);
    } else {
      this.selectedColors = this.selectedColors.filter((c) => c !== color);
      this.selectedColorsnames = this.selectedColorsnames.filter((s) => s !== color.name);
    }
    this.productForm.controls['colors'].setValue(this.selectedColors);
  }

  onSubmit(): void {
    const formData = new FormData();
    formData.append("product", new Blob([JSON.stringify(this.productForm.value)], { type: "application/json" }));

    if (this.selectedFiles.length === 0) {
      alert('Please select at least one image.');
      return;
    }
    for (let file of this.selectedFiles) {
      formData.append('images', file);
    }

    console.log(this.productForm.value);

    this.productService.addProduct(formData).
    subscribe(response => {
      console.log('Product added successfully', response);
      alert('Product added successfully');
      this.productForm.reset();
      window.location.reload();
      this.productForm.reset();
    }, error => {
      console.error('Error adding product', error);
    });
  }
}
