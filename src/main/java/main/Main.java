package main;

import dao.OrderDAO;
import dao.OrderDAOimpl;
import model.Order;
import paser.XMLParser;
import paser.CSVParser;

import java.util.List;
import java.util.Scanner;

class Main {
    private GenerateRaport generateRaport = new GenerateRaport();
    private Scanner scanner = new Scanner(System.in);
    private OrderDAO orderDAO = new OrderDAOimpl();
    private Boolean check;

    private void init() {
        boolean next = true;
        CSVParser CSVParser = new CSVParser();
        XMLParser XMLParser = new XMLParser();
        while (next) {
            Console.clear();
            System.out.println("Żeby wczytać dane z zamówieniami umieść plik w folderze OrderReader");
            System.out.println("i wpisz nazwę pliku z rozszerzeniem np. orders.xml orders.csv ");
            System.out.println("Jeśeli plik znajduje się w innej lokalizacji proszę wpisać pełną ścieżke \n");
            System.out.println("Wpisz nazwę pliku \n");

            String url = geturl();
            String add = "../";
            if (url.contains("csv")) {
                if (url.contains("\\")) {
                    if (CSVParser.parse(url)) {
                        next = nextFile();
                    }
                } else {
                    if (CSVParser.parse(add + url)) {
                        next = nextFile();
                    }

                }

            } else if (url.contains("xml")) {
                if (url.contains("\\")) {
                    if (XMLParser.parse(url)) {
                        next = nextFile();
                    }
                } else {
                    if (XMLParser.parse(add + url)) {
                        next = nextFile();
                    }
                }
            } else {
                System.out.println("błędne rozszerzenie");
                Console.pressEnter();
            }
        }
    }

    private boolean nextFile() {
        while (true) {
            System.out.println("Wczytać kolejny plik t/n");
            String key = scanner.next();
            if (key.equals("t"))
                return true;
            if (key.equals("n"))
                return false;
            if (!key.equals("t") || !key.equals("n"))
                System.out.println("Błąd");
        }
    }

    private String geturl() {
        return scanner.next();
    }

    void run() {


        init();
        while (true) {
            Console.clear();
            System.out.println("Obsługa zamówień menu");
            System.out.println();
            System.out.println("1: Łączna lość zamówień");
            System.out.println("2: Łączna lość zamówień danego klienta");
            System.out.println("3: Łączna kwota zamówień");
            System.out.println("4: Łączna kwota zamówień danego klienta");
            System.out.println("5: Lista wszystkich zamówień");
            System.out.println("6: Lista wszystkich zamówień danego klienta");
            System.out.println("7: Średnia kwota zamówień");
            System.out.println("8: Średnia kwota zamówień danego klienta");
            System.out.println("9: Zakończenie programu");
            try {
                switch (scanner.nextInt()) {
                    case 1:
                        Integer case1 = orderDAO.findByNumberOfOrders();
                        if (isEmpty(case1))
                            break;
                        String data1 = "łączna ilość zamówień: " + case1;
                        raports(data1);
                        break;
                    case 2:
                        String id2 = read();
                        Integer case2 = orderDAO.findByNumerOfOrdersByClientId(id2);
                        if (isEmpty(case2))
                            break;
                        String data2 = "łączna ilość zamówień dla klienta " + id2 + " wynosi: " + case2;
                        raports(data2);
                        break;
                    case 3:
                        Double case3 = orderDAO.findTottalPrice();
                        isEmpty(case3 <= 0 ? 0 : 1);
                        String data3 = "Łączna kwota zamówień: " + case3;
                        raports(data3);
                        break;
                    case 4:
                        String id4 = read();
                        Double case4 = orderDAO.findTottalPriceByClientId(id4);
                        if (isEmpty(case4 <= 0 ? 0 : 1))
                            break;
                        String data4 = "Łączna kwota zamówień dla klienta " + id4 + " wynosi: " + case4;
                        raports(data4);
                        break;
                    case 5:
                        List<Order> case5 = orderDAO.findAll();
                        if (isEmpty(case5.size()))
                            break;
                        String data5 = "Lista wszystkich zamównień";
                        raports(data5, case5);
                        break;
                    case 6:
                        String id6 = read();
                        List<Order> case6 = orderDAO.findAllByClientId(id6);
                        if (isEmpty(case6.size()))
                            break;
                        String data6 = "Lista wszystkich zamównień klienta " + id6;
                        raports(data6, case6);
                        break;
                    case 7:
                        Double case7 = orderDAO.findByAveragePrice();
                        isEmpty(case7 <= 0 ? 0 : 1);
                        String data7 = "Średnia kwota zamówień: " + case7;
                        raports(data7);
                        break;
                    case 8:
                        String id8 = read();
                        Double case8 = orderDAO.findByAveragePriceByClientId(id8);
                        if (isEmpty(case8 <= 0 ? 0 : 1))
                            break;
                        String data8 = "Średnia kwota zamówień dla klienta " + id8 + " wynosi: " + case8;
                        raports(data8);
                        break;
                    case 9:
                        Console.clear();
                        System.out.println("Zakończono program");
                        System.exit(1);
                        break;
                    default:
                        System.out.println("Proszę wybrać poprawną opcje od 1-9");
                        Console.pressEnter();
                }
            } catch (Exception e) {
                System.out.println("Proszę wybrać poprawną opcje od 1-9");
                Console.pressEnter();
            }
        }
    }

    private void raports(String data) {
        Console.clear();
        check = formatAsk();
        if (check == null) {
            generateRaport.saveCsv(data);
        } else if (check) {
            generateRaport.printOnScreen(data);
        } else generateRaport.saveTxt(data);
    }

    private void raports(String data, List<Order> orders) {
        Console.clear();
        check = formatAsk();
        if (check == null) {
            generateRaport.saveCsv(data,orders);
        } else if (check) {
            generateRaport.printOnScreen(data, orders);
        } else generateRaport.saveTxt(data, orders);
    }

    private boolean isEmpty(int order) {
        if (order == 0) {
            System.out.println("Brak danych do wyświetlena na dane zapytanie");
            Console.pressEnter();
            return true;
        } else
            return false;
    }

    private Boolean formatAsk() {
        while (true) {
            System.out.println();
            System.out.println("Co zrobić ?");
            System.out.println("1: Wyświetlić na ekranie");
            System.out.println("2: Zapisać do pliku TXT");
            System.out.println("3: Zapisać do pliku CSV");
            try {
                switch (scanner.nextInt()) {
                    case 1:
                        return true;
                    case 2:
                        return false;
                    case 3:
                        Console.clear();
                        return null;
                    default:
                        System.out.println("Proszę wybrać poprawną opcje od 1-3");
                        Console.pressEnter();
                }
            } catch (Exception e) {
                System.out.println("Proszę wybrać poprawną opcje od 1-3");
                Console.pressEnter();
                scanner.nextLine();

            }
        }
    }

    private String read() {
        Boolean loop = true;
        String id = "";
        while (loop) {
            Console.clear();
            System.out.println("\nProszę wpisać identyfikator klienta\n");
            scanner.nextLine();
            id = scanner.nextLine();
            if (id.length() > 6 || id.contains(" ")) {
                System.out.println("Błędny identyfikator");
                Console.pressEnter();
            } else {
                loop = false;
            }

        }
        return id;
    }
}
