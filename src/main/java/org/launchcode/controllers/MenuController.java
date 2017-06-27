package org.launchcode.controllers;

//import org.launchcode.models.Cheese;
import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Christy on 6/21/2017.
 */
@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping(value="")
    public String index (Model model) {
        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "My Menus");

        return "menu/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET )
    public String add(Model model){
        model.addAttribute( "menu", new Menu());
        //model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "Add Menu");

        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@ModelAttribute @Valid Menu newMenu, Errors errors, Model model) {

        if(errors.hasErrors() ) {
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }
        //Menu some_new_menu = menuDao.findOne(menuId);
        //newMenu.setMenu(some_new_menu);
        menuDao.save(newMenu);

        return "redirect:view/" + newMenu.getId();
    }

    @RequestMapping(value="view/{menuId}", method = RequestMethod.GET)
    public String viewMenu(@PathVariable int menuId, Model model) {
        //System.out.println("BEGINNING CHRISTY: viewMenu!!!!!!!!!");
        Menu oneMenu = menuDao.findOne(menuId);
        //model.addAttribute(new Menu());
        model.addAttribute("title", oneMenu.getName());
        model.addAttribute("menu", oneMenu);

        //return "redirect:view/" + menuId;
        return "menu/view"; // goes to menu/view.html
    }

    @RequestMapping(value="add-item/{menuId}", method = RequestMethod.GET)
    public String addItem(@PathVariable int menuId, Model model) {
        // Uses the AddMenuItemForm to add items to menus ???

        Menu oneMenu = menuDao.findOne(menuId);
        Iterable<Cheese> allCheesesList = cheeseDao.findAll();

        model.addAttribute("form",  new AddMenuItemForm(oneMenu, allCheesesList));

        model.addAttribute("title", "Add item to menu: " + oneMenu.getName());

        //model.addAttribute(new Menu());
        //model.addAttribute("menu", oneMenu);
        return  "menu/add-item"; // NO menuId on this path. goes to html file
    }

    @RequestMapping(value="add-item/{menuId}", method = RequestMethod.POST)
    public String addItem(@ModelAttribute @PathVariable int menuId, Model model, @ModelAttribute @Valid AddMenuItemForm form, Errors errors) {

        if(errors.hasErrors() ) {
            model.addAttribute("form", form);
            return "menu/add-item";
        }
        Cheese aCheese = cheeseDao.findOne(form.getCheeseId());
        Menu aMenu = menuDao.findOne(form.getMenuId());

        aMenu.addItem(aCheese);
        menuDao.save(aMenu);
        return "redirect:../view/" + aMenu.getId(); // menu/view.html
    }
}
