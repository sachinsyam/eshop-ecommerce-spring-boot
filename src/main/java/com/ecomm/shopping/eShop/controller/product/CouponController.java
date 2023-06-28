package com.ecomm.shopping.eShop.controller.product;

import com.ecomm.shopping.eShop.entity.order.OrderHistory;
import com.ecomm.shopping.eShop.entity.product.Category;
import com.ecomm.shopping.eShop.entity.product.Coupon;
import com.ecomm.shopping.eShop.entity.product.Product;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.service.OrderHistoryService;
import com.ecomm.shopping.eShop.service.UserInfoService;
import com.ecomm.shopping.eShop.service.impl.CategoryServiceImpl;
import com.ecomm.shopping.eShop.service.impl.CouponServiceImpl;
import com.ecomm.shopping.eShop.service.impl.ProductServiceImpl;
import com.ecomm.shopping.eShop.worker.UsernameProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/coupon")
public class CouponController {
    @Autowired
    CouponServiceImpl couponService;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    OrderHistoryService orderHistoryService;
    @Autowired
    UsernameProvider usernameProvider;


    @GetMapping("/all")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String all(@RequestParam(required = false,defaultValue = "") String filter,
                      @RequestParam(required = false, defaultValue = "") String keyword,
                      @RequestParam(required = false, defaultValue = "ASC") String sort,
                      @RequestParam(required = false, defaultValue = "createdAt") String field,
                      @RequestParam(required = false, defaultValue = "0") int page,
                      @RequestParam(required = false, defaultValue = "10") int size,
                      Model model){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), field));
        Page<Coupon> coupons = Page.empty();
        if(!filter.equals("")){
         //   coupons = variantService.findByProductPaged(filter, pageable);
        }else if(keyword != null || !keyword.equals("")){
            coupons = couponService.findByNameLike(keyword, pageable);
        }else{
            coupons = couponService.findAllPaged(pageable);
        }


        //Pagination Values
        model.addAttribute("filter", filter);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coupons.getTotalPages());
        model.addAttribute("field", field);
        model.addAttribute("sort", sort);
        model.addAttribute("pageSize", size);
        int startPage = Math.max(0, page - 1);
        int endPage = Math.min(page + 1, coupons.getTotalPages() - 1);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("empty", coupons.getTotalElements() == 0);

        model.addAttribute("coupons" , coupons);
        return "dashboard/html/coupon/CouponList";
    }
    @GetMapping("create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(@RequestParam(name="duplicateError", required = false, defaultValue = "") String error,
                         Model model){
        List<Product> products = productService.findAll();
        List<Category> categories = categoryService.findAll();
        List<UserInfo> users = userInfoService.loadAllUsers();

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("users", users);
        if(error.equals("")){
            model.addAttribute("error", false);
        }else{
            model.addAttribute("error", true);
        }
        model.addAttribute("couponCode", error);

        return "dashboard/html/coupon/CreateCoupon";
    }

    @GetMapping("{uuid}")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String get(@RequestParam(required = false,defaultValue = "") String filter,
                      @RequestParam(required = false, defaultValue = "") String keyword,
                      @RequestParam(required = false, defaultValue = "DESC") String sort,
                      @RequestParam(required = false, defaultValue = "createdAt") String field,
                      @RequestParam(required = false, defaultValue = "0") int page,
                      @RequestParam(required = false, defaultValue = "5") int size,
                      @PathVariable("uuid") UUID uuid,
                      Model model){
        Coupon coupon = couponService.get(uuid);
        List<Product> products = productService.findAll();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("coupon", coupon);
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);

        if(coupon.getCouponType() == 1){    //1-Product
            Product product = productService.getProduct(coupon.getApplicableFor());
            model.addAttribute("applicableFor",product.getName());
        } else if (coupon.getCouponType() == 2) {   //2-Category
            Category category = categoryService.getCategory(coupon.getApplicableFor());
            model.addAttribute("applicableFor", category.getName());
        } else if (coupon.getCouponType() == 3) {   //2-Brand
            //brand not yet implemented
        }else{  //4-All
            model.addAttribute("applicableFor", "All Products");
        }

        //Coupon Redemption Details Paged
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), field));
        Page<OrderHistory> orders = Page.empty();
        orders = orderHistoryService.findByCoupon(coupon, pageable);

        //Pagination Values
        model.addAttribute("filter", filter);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orders.getTotalPages());
        model.addAttribute("field", field);
        model.addAttribute("sort", sort);
        model.addAttribute("pageSize", size);
        int startPage = Math.max(0, page - 1);
        int endPage = Math.min(page + 1, orders.getTotalPages() - 1);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("empty", orders.getTotalElements() == 0);

        model.addAttribute("orders" , orders);

        return "dashboard/html/coupon/CouponDetailView";
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String save(@ModelAttribute Coupon coupon){
        Coupon existingCoupon = couponService.findByCode(coupon.getCode());
        if(existingCoupon != null){
            System.out.println("Coupon code already exist "+coupon.getCode());

            return "redirect:/coupon/create?duplicateError="+coupon.getCode();

        }else{

            if(coupon.getCouponType()==4){
                coupon.setApplicableFor(null);
            }
            coupon = couponService.save(coupon);
            return "redirect:/coupon/"+ coupon.getUuid();
        }
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@ModelAttribute Coupon coupon){

        Coupon existingCoupon = couponService.findById(coupon.getUuid());
        coupon.setCouponType(existingCoupon.getCouponType());
        coupon.setApplicableFor(existingCoupon.getApplicableFor());


        if(coupon.getCouponType()==4){
            coupon.setApplicableFor(null);
        }
        coupon = couponService.save(coupon);
        return "redirect:/coupon/"+ coupon.getUuid();

    }

    @GetMapping("/delete/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteById(@PathVariable UUID uuid){
        Coupon coupon = couponService.get(uuid);
        coupon.setDeleted(true);
        coupon.setDeletedBy(usernameProvider.get());
        coupon.setDeletedAt(new Date());
        coupon.setEnabled(false);
        couponService.save(coupon);
        System.out.println("Coupon:"+coupon.getCode()+" soft deleted");
        //couponService.delete(uuid);
        return "redirect:/coupon/all";
    }
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteByModal(@ModelAttribute Coupon coupon){
        Coupon existingCoupon = couponService.get(coupon.getUuid());
        existingCoupon.setDeleted(true);
        coupon.setDeletedBy(usernameProvider.get());
        coupon.setDeletedAt(new Date());
        coupon.setEnabled(false);
        couponService.save(existingCoupon);
        System.out.println("Coupon:"+coupon.getCode()+" soft deleted");
       // couponService.delete(coupon.getUuid());
        return "redirect:/coupon/all";
    }
}
