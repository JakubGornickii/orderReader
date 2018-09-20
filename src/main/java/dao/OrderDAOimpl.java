package dao;

import db.OrderDb;
import model.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

public class OrderDAOimpl implements OrderDAO {
    public void addOrder(Order order) {


        OrderDb.orders.add(order);
    }

    public int findByNumberOfOrders() {
        return OrderDb.orders.size();
    }

    public int findByNumerOfOrdersByClientId(String clientId) {
        int counter = 0;
        for (Order order : OrderDb.orders) {
            if (order.getClientId().equals(clientId)) {
                counter++;
            }
        }
        return counter;
    }

    public double findTottalPrice() {
        double tottalPrice = 0;
        for (Order order : OrderDb.orders) {
            tottalPrice += order.getPrice() * order.getQuantity();
        }
        return tottalPrice;
    }

    public double findTottalPriceByClientId(String clientId) {
        Double tottalPrice = 0.00;
        for (Order order : OrderDb.orders) {
            if (order.getClientId().equals(clientId)) {
                tottalPrice += order.getPrice() * order.getQuantity();
            }
        }
        return tottalPrice;
    }

    public List<Order> findAll() {
        return OrderDb.orders;
    }

    public List<Order> findAllByClientId(String clientId) {
        List<Order> orders = new LinkedList<Order>();
        for (Order order : OrderDb.orders) {
            if (order.getClientId().equals(clientId)) {
                orders.add(order);
            }
        }
        return orders;
    }

    public double findByAveragePrice() {
        Double price = 0.0;
        Double averagePrice;
        for (Order order : OrderDb.orders) {
            price += order.getPrice() * order.getQuantity();
        }
        averagePrice = price / OrderDb.orders.size();
        averagePrice = BigDecimal.valueOf(averagePrice).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
        return averagePrice;
    }

    public double findByAveragePriceByClientId(String clientId) {
        double price = 0;
        int counter = 0;
        double averagePrice = 0;
        for (Order order : OrderDb.orders) {
            if (order.getClientId().equals(clientId)) {
                price += order.getPrice() * order.getQuantity();
                counter++;
            }
        }
        averagePrice = price / counter;
        averagePrice = BigDecimal.valueOf(averagePrice).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
        return averagePrice;
    }
}
