package id.my.hendisantika.shoppingcartpusher.controller;

import com.pusher.rest.Pusher;
import id.my.hendisantika.shoppingcartpusher.constants.GeneralConstants;
import id.my.hendisantika.shoppingcartpusher.constants.PusherConstants;
import id.my.hendisantika.shoppingcartpusher.controller.vo.ItemRequest;
import id.my.hendisantika.shoppingcartpusher.model.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

    /**
     * Method that returns the products available for shopping
     *
     * @return List of product objects
     */
    @GetMapping(value = "/products",
            produces = "application/json")
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Method that returns the list of products in the current shopping cart
     *
     * @param shoppingCart List of products injected by Spring MVC from the session
     * @return List of products
     */
    @GetMapping(value = "/cart/items",
            produces = "application/json")
    public List<Product> getCartItems(@SessionAttribute(GeneralConstants.ID_SESSION_SHOPPING_CART) List<Product> shoppingCart) {
        return shoppingCart;
    }

    /**
     * Method to add a product to the shopping cart
     *
     * @param request      Request object
     * @param shoppingCart List of products injected by Spring MVC from the session
     * @return Status string
     */
    @PostMapping(value = "/cart/item", consumes = "application/json")
    public String addItem(@RequestBody ItemRequest request, @SessionAttribute(GeneralConstants.ID_SESSION_SHOPPING_CART) List<Product> shoppingCart) {
        Product newProduct = new Product();
        Optional<Product> optional = getProductById(products.stream(), request.getId());

        if (optional.isPresent()) {
            Product product = optional.get();

            newProduct.setId(product.getId());
            newProduct.setName(product.getName());
            newProduct.setPrice(product.getPrice());
            newProduct.setQuantity(request.getQuantity());

            Optional<Product> productInCart = getProductById(shoppingCart.stream(), product.getId());
            String event;

            if (productInCart.isPresent()) {
                productInCart.get().setQuantity(request.getQuantity());
                event = "itemUpdated";
            } else {
                shoppingCart.add(newProduct);
                event = "itemAdded";
            }

            pusher.trigger(PusherConstants.CHANNEL_NAME, event, newProduct);
        }

        return "OK";
    }

    /**
     * Method that deletes an item from the cart
     *
     * @param request      Request object
     * @param shoppingCart List of products injected by Spring MVC from the session
     * @return Status string
     */
    @DeleteMapping(value = "/cart/item", consumes = "application/json")
    public String deleteItem(@RequestBody ItemRequest request, @SessionAttribute(GeneralConstants.ID_SESSION_SHOPPING_CART) List<Product> shoppingCart) {
        Optional<Product> optional = getProductById(products.stream(), request.getId());

        if (optional.isPresent()) {
            Product product = optional.get();

            Optional<Product> productInCart = getProductById(shoppingCart.stream(), product.getId());

            if (productInCart.isPresent()) {
                shoppingCart.remove(productInCart.get());
                pusher.trigger(PusherConstants.CHANNEL_NAME, "itemRemoved", product);
            }
        }

        return "OK";
    }

    /**
     * Method that empties the shopping cart
     *
     * @param model Object from Spring MVC
     * @return Status string
     */
    @DeleteMapping(value = "/cart")
    public String emptyCart(Model model) {
        model.addAttribute(GeneralConstants.ID_SESSION_SHOPPING_CART, new ArrayList<Product>());
        pusher.trigger(PusherConstants.CHANNEL_NAME, "cartEmptied", "");

        return "OK";
    }

    /**
     * Gets a product by its id from a stream
     *
     * @param stream That contains the product to get
     * @param id     Of the product to get
     * @return The product wrapped in an Optional object
     */
    private Optional<Product> getProductById(Stream<Product> stream, Long id) {
        return stream
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }
}
