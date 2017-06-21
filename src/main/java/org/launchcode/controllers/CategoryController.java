package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by Christy on 6/19/2017.
 */
@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    // Request path: /category
    @RequestMapping(value="")
    public String index(Model model){
        //retrieve the Iterable (list) of all categories (all category objects managed by categoryDao
        Iterable allCategoriesIterableList = categoryDao.findAll();
        model.addAttribute("categories", allCategoriesIterableList);
        model.addAttribute("title", "Categories");

        return "category/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model){

        //create new Category object & pass into the form model
        model.addAttribute(new Category());
        model.addAttribute("title", "Add Category");

        return "category/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Category category, Errors errors ) {

        if(errors.hasErrors() ){
            return "category/add";
        }

        categoryDao.save(category);
        return "redirect:";  // redirects to the index handler
    }

    //public getCategory(Model model) {
    //
    //}
}
