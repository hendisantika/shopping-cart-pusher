package id.my.hendisantika.shoppingcartpusher.controller;

import com.pusher.rest.Pusher;
import id.my.hendisantika.shoppingcartpusher.constants.GeneralConstants;
import id.my.hendisantika.shoppingcartpusher.model.Product;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : shopping-cart-pusher
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 1/31/24
 * Time: 09:18
 * To change this template use File | Settings | File Templates.
 */
@RestController
@SessionAttributes(GeneralConstants.ID_SESSION_SHOPPING_CART)
public class CartController {

    private final List<Product> products = new ArrayList<Product>();

    private Pusher pusher;

}
