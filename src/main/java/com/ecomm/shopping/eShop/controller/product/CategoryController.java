package com.ecomm.shopping.eShop.controller.product;

import com.ecomm.shopping.eShop.entity.product.Category;
import com.ecomm.shopping.eShop.service.impl.CategoryServiceImpl;
import com.ecomm.shopping.eShop.worker.UsernameProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    UsernameProvider usernameProvider;


    @GetMapping("create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createCategory(){
        return "dashboard/html/category/CreateCategory";
    }
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String add(@ModelAttribute Category category){
        categoryService.addCategory(category);
        return "redirect:/category/all";
    }
    @GetMapping("/all")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String all(Model model){
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories",categories);
        return "dashboard/html/category/CategoryList";
    }
    @GetMapping("/{uuid}")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getCategory(@PathVariable UUID uuid, Model model){
        Category category = categoryService.getCategory(uuid);

        model.addAttribute("uuid",uuid);
        model.addAttribute("name",category.getName());
        model.addAttribute("description",category.getDescription());

        return "dashboard/html/category/CategoryDetailView";
    }
    //delete a category from dashboard
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCategoryById(@PathVariable("id") UUID uuid){
        Category category = categoryService.getCategory(uuid);
        category.setDeleted(true);
        category.setDeletedBy(usernameProvider.get());
        category.setDeletedAt(new Date());
        categoryService.save(category);
        System.out.println("Soft deleted category");
      //  categoryService.delete(uuid);
        return "redirect:/category/all";
    }
    //delete
    @PostMapping("/deleteCategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@ModelAttribute Category categoryToBeDeleted){
        Category category = categoryService.getCategory(categoryToBeDeleted.getUuid());
        category.setDeleted(true);
        category.setDeletedBy(usernameProvider.get());
        category.setDeletedAt(new Date());
        categoryService.save(category);
        System.out.println("Soft deleted category");

//        System.out.println("Deleting"+category.getUuid());
//        categoryService.delete(category.getUuid());
        return "redirect:/category/all";
    }

    //Update category
    @PostMapping("/updateCategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@ModelAttribute Category category){
        categoryService.updateCategory(category);
        return "redirect:/category/"+category.getUuid();

    }

}


