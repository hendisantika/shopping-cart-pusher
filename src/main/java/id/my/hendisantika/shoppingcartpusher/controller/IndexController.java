package id.my.hendisantika.shoppingcartpusher.controller;

import id.my.hendisantika.shoppingcartpusher.constants.GeneralConstants;
import id.my.hendisantika.shoppingcartpusher.constants.PusherConstants;
import id.my.hendisantika.shoppingcartpusher.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * Project : shopping-cart-pusher
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 1/31/24
 * Time: 09:17
 * To change this template use File | Settings | File Templates.
 */
@Controller
@SessionAttributes(GeneralConstants.ID_SESSION_SHOPPING_CART)
public class IndexController {

    /**
     * Method that sets up and serves the initial page of the app
     *
     * @param model Object from Spring MVC
     * @return ModelAndView object
     */
    @GetMapping(value = "/")
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("index");
        modelAndView.addObject("pusher_app_key", PusherConstants.PUSHER_APP_KEY);
        modelAndView.addObject("pusher_channel", PusherConstants.CHANNEL_NAME);

        if (!model.containsAttribute(GeneralConstants.ID_SESSION_SHOPPING_CART)) {
            model.addAttribute(GeneralConstants.ID_SESSION_SHOPPING_CART, new ArrayList<Product>());
        }

        return modelAndView;
    }
}
