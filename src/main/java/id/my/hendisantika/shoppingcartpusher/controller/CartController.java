package id.my.hendisantika.shoppingcartpusher.controller;

import com.pusher.rest.Pusher;
import id.my.hendisantika.shoppingcartpusher.constants.GeneralConstants;
import id.my.hendisantika.shoppingcartpusher.constants.PusherConstants;
import id.my.hendisantika.shoppingcartpusher.model.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.math.BigDecimal;
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

    /**
     * Method executed after the object is created
     * that creates an instance of the Pusher object and
     * populates the list of products
     */
    @PostConstruct
    public void configure() {
        pusher = new Pusher(
                PusherConstants.PUSHER_APP_ID,
                PusherConstants.PUSHER_APP_KEY,
                PusherConstants.PUSHER_APP_SECRET
        );
        pusher.setCluster(PusherConstants.PUSHER_CLUSTER_KEY);
        pusher.setEncrypted(true);

        Product product = new Product();
        product.setId(1L);
        product.setName("Office Chair");
        product.setPrice(new BigDecimal("55.99"));
        products.add(product);

        product = new Product();
        product.setId(2L);
        product.setName("Sunglasses");
        product.setPrice(new BigDecimal("99.99"));
        products.add(product);

        product = new Product();
        product.setId(3L);
        product.setName("Wireless Headphones");
        product.setPrice(new BigDecimal("349.01"));
        products.add(product);

        product = new Product();
        product.setId(4L);
        product.setName("External Hard Drive");
        product.setPrice(new BigDecimal("89.99"));
        products.add(product);
    }
}