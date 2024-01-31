package id.my.hendisantika.shoppingcartpusher.controller.vo;

/**
 * Created by IntelliJ IDEA.
 * Project : shopping-cart-pusher
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 1/31/24
 * Time: 09:16
 * To change this template use File | Settings | File Templates.
 */

import lombok.Data;

import java.io.Serializable;

/**
 * Value Object that represents a request to add or delete an item to the shopping cart
 */
@Data
public class ItemRequest implements Serializable {

    /**
     * UID for serialization
     */
    private static final long serialVersionUID = 7720978167137384733L;

    /**
     * Id of the item
     */
    private Long id;

    /**
     * Quantity of the item to be added to the shopping cart
     */
    private Integer quantity;
}
