package org.launchcode.controllers;

//import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
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
        model.addAttribute(new Menu());
        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "My Menus");

        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@ModelAttribute @Valid Menu newMenu, Errors errors, @RequestParam int menuId, Model model) {

        if(errors.hasErrors() ) {
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }

        Menu some_new_menu = menuDao.findOne(menuId);

        //newMenu.setMenu(some_new_menu);

        menuDao.save(some_new_menu);
        return "redirect:view/" + some_new_menu.getId();

    }

    @RequestMapping(value="view/(menuId}", method = RequestMethod.GET)
    public String viewMenu(@PathVariable int menuId, Model model) {

        Menu oneMenu = menuDao.findOne(menuId);
        model.addAttribute(new Menu());
        model.addAttribute("title", oneMenu.getName());
        model.addAttribute("menu", oneMenu);

        return "redirect:view/" + menuId;
    }
}
