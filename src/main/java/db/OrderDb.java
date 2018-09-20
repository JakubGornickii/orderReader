package db;

import model.Order;

import java.util.LinkedList;
import java.util.List;
/**
 * This abstract class is in memory database
 */
public abstract class OrderDb {
    public static List<Order> orders = new LinkedList<>();
}
