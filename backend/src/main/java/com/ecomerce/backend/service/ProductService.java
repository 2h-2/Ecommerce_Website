package com.ecomerce.backend.service;

import java.math.BigDecimal;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecomerce.backend.error.ResourceNotFoundException;
import com.ecomerce.backend.model.dto.ProductDto;
import com.ecomerce.backend.model.entity.Category;
import com.ecomerce.backend.model.entity.Product;
import com.ecomerce.backend.model.entity.ProductImages;
import com.ecomerce.backend.model.entity.Size;
import com.ecomerce.backend.model.entity.Color;
import com.ecomerce.backend.repository.CategoryRepo;
import com.ecomerce.backend.repository.ColorRepo;
import com.ecomerce.backend.repository.ProductRepo;
import com.ecomerce.backend.repository.SizeRepo;

import jakarta.transaction.Transactional;



@Service
public class ProductService {
    @Autowired
    private FileUploadService fileUploadService; 

    @Autowired
    private SizeRepo sizeRepository;

    @Autowired
    private ColorRepo colorRepository;

    @Autowired
    private CategoryRepo categoryRepository;
    

    @Autowired
    private ProductRepo productRepo ;

    public class image{
        public String imageURL;

        public image(String imageURL){
            this.imageURL = imageURL;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            image image = (image) o;
            return Objects.equals(imageURL, image.imageURL);
        }
    }

    public class productResponse{
        public Long id;
        public String name;
        public String description;
        public BigDecimal unitPrice;
        public Category category;
        public List<ProductImages> images = new ArrayList<>();
        public List<Color> colors = new ArrayList<>();
        public List<Size> sizes = new ArrayList<>();
    }

    public List<image> mapimages(List<ProductImages> inventories){
        List<image> images= new ArrayList<>();
                
        for (ProductImages inventory : inventories) {

            images.add(new image(inventory.getImageURL()));  
        }
        List<image> imagesFilter= new ArrayList<>();
        
        for (image img : images) {

            if(!imagesFilter.contains(img)){
                imagesFilter.add(img);
            }
        }
        return imagesFilter;
    }

    @Transactional
    public productResponse getProduct(Long id){
        Optional<Product> product =  productRepo.findById(id);
        
        if(product.isPresent()){
            Product pro = product.get();
            productResponse response  = new productResponse();
            //System.out.println("///////pro"+pro);
            response.id = pro.getId();
            response.name = pro.getName();
            response.description = pro.getDescription();
            response.unitPrice = pro.getUnitPrice();
            response.sizes = pro.getSizes();
            response.images = pro.getImages();
            

            return response;
        }
        return null;
    }

    public productResponse getPro(Long id){
        Optional<Product> product =  productRepo.findById(id);
        
        if(product.isPresent()){
            Product pro = product.get();
            productResponse response  = new productResponse();
            response.id = pro.getId();
            response.name = pro.getName();
            response.description = pro.getDescription();
            response.unitPrice = pro.getUnitPrice();
            response.sizes = pro.getSizes();
            response.colors = pro.getColors();
            response.images = pro.getImages();
            

            return response;
        }
        return null;
    }
   
    public String test(){
        String text = "Small (S)";
        String size = "" ;
        int start = text.indexOf('(') + 1;
        int end = text.indexOf(')');

        if (start > 0 && end > start) {
            size = text.substring(start, end);
        }
        return size;
    }

    public List<productResponse> getAll(){
        List<productResponse> productsResp = new ArrayList<>();
        List<Product> products = productRepo.findAll();

        if(!products.isEmpty()){
            products.stream().forEach(pro -> {
                productResponse response  = new productResponse();
                response.id = pro.getId();
                response.name = pro.getName();
                response.description = pro.getDescription();
                response.unitPrice = pro.getUnitPrice();
                response.images = pro.getImages();
                response.sizes = pro.getSizes();
                response.colors = pro.getColors();
                response.category = pro.getCategory();
                productsResp.add(response);
                
            });
            return productsResp;
        }

        return null;
    }

    @Transactional
    public void addProduct(ProductDto obj, MultipartFile[] images){
        
        Product product = new Product();
        product = Product.toEntity(obj);

        Category category = categoryRepository.findById(obj.getCategory().getId())
        .orElseThrow(() -> new RuntimeException("Invalid category ID"));

        List<Long> colorIds = obj.getColors().stream().map(Color::getId).toList();
        List<Long> sizeIds = obj.getSizes().stream().map(Size::getId).toList();

        List<Color> colors = new ArrayList<>(colorRepository.findAllById(colorIds));
        List<Size> sizes = new ArrayList<>(sizeRepository.findAllById(sizeIds));

        if (colors.size() != colorIds.size()) {
            throw new RuntimeException("Some Color IDs are invalid.");
        }
        if (sizes.size() != sizeIds.size()) {
            throw new RuntimeException("Some Size IDs are invalid.");
        }

        product.setCategory(category);
        product.setColors(colors);
        product.setSizes(sizes);

        product = productRepo.save(product); 
    
        if (images != null) {
            for (MultipartFile image : images) {
                fileUploadService.storeFile(product, image);
            }
        }
    }

    public void delete(Long id){
        Optional<Product> product =  productRepo.findById(id);
        
        if(!product.isPresent()){
            throw new ResourceNotFoundException("No product item found for this Id !" );
        }

        productRepo.deleteById(id);
    }

}
