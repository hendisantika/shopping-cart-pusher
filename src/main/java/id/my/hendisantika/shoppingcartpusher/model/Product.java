package id.my.hendisantika.shoppingcartpusher.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * Project : shopping-cart-pusher
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 1/31/24
 * Time: 09:15
 * To change this template use File | Settings | File Templates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

    /**
     * UID for serialization
     */
    private static final long serialVersionUID = 6705527563808382509L;

    /**
     * Id of the product
     */
    private Long id;

    /**
     * Name of the product
     */
    private String name;

    /**
     * Price of the product
     */
    private BigDecimal price;

    /**
     * Quantity of the product that will be added to the shopping cart
     */
    private Integer quantity;
}
