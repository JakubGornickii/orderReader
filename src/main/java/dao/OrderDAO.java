package dao;

import model.Order;

import java.util.List;
/**
 * This interface it contains all methods for
 * operate on in memory database on Order object
 */
public interface OrderDAO {
    public void addOrder(Order order);
    public int findByNumberOfOrders();
    public int findByNumerOfOrdersByClientId(String clientId);
    public double findTottalPrice();
    public double findTottalPriceByClientId(String clientId);
    public List<Order> findAll();
    public List<Order> findAllByClientId(String clientId);
    public double findByAveragePrice();
    public double findByAveragePriceByClientId(String clientId);

}
