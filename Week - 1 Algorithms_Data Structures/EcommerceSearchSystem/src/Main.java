import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        Product[] products = {
            new Product("P001", "Laptop", "Electronics"),
            new Product("P002", "Chair", "Furniture"),
            new Product("P003", "Mobile", "Electronics"),
            new Product("P004", "Table", "Furniture"),
            new Product("P005", "Shoes", "Fashion")
        };

        // Linear Search Test
        Product found1 = Search.linearSearch(products, "Mobile");
        System.out.println("Linear Search Result: " + (found1 != null ? found1 : "Not found"));

        // Sort products for binary search
        Arrays.sort(products, Comparator.comparing(Product::getProductName));

        // Binary Search Test
        Product found2 = Search.binarySearch(products, "Mobile");
        System.out.println("Binary Search Result: " + (found2 != null ? found2 : "Not found"));
    }
}
/*Use binary search when:

You frequently perform searches.

The product list doesn’t change often (or use TreeMap / TreeSet for dynamic sorted data).

Use linear search for:

Small datasets.

Unsorted or frequently changing data.

 */