package com.example.customerprovincemanagement.controller;

import com.example.customerprovincemanagement.model.Customer;
import com.example.customerprovincemanagement.model.Province;
import com.example.customerprovincemanagement.service.ICustomerService;
import com.example.customerprovincemanagement.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class
CustomerController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> listProvince(){
        return provinceService.findAll();
    }
    @GetMapping
    public ModelAndView listCustomer() {
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        Iterable<Customer> customers = customerService.findAll();
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("customer") Customer customer,
                         RedirectAttributes redirectAttributes) {
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("message", "Create new customer successfully");
        return "redirect:/customers";
    }
    @PostMapping("/search")
    public ModelAndView listCustomersSearch(@RequestParam("search") Optional<String> search, Pageable pageable){
        Page<Customer> customers;
        if(search.isPresent()){
            customers = customerService.findAllByFirstNameContaining(pageable,search.get());
        } else {
            customers = customerService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/customer/update");
            modelAndView.addObject("customer", customer.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error_404");
        }
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("customer") Customer customer,
                         RedirectAttributes redirect) {
        customerService.save(customer);
        redirect.addFlashAttribute("message", "Update customer successfully");
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirect) {
        customerService.remove(id);
        redirect.addFlashAttribute("message", "Delete customer successfully");
        return "redirect:/customers";
    }
    @GetMapping("/customers")
    public ModelAndView listCustomers(Pageable pageable){
        Page<Customer> customers = customerService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

}
