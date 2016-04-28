package webshop;

/**
 * @author Niels
 */
public class Product {
    
    private final int id;
    private final String name, category, size, color, gender, description, imagePath;
    private final double price;

    public Product(int id, String name, String category, String size, String color, String gender, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.size = size;
        this.color = color;
        this.gender = gender;
        this.price = price;
        this.description = "";
        this.imagePath = "";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getGender() {
        return gender;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", category=" + category + ", size=" + size + ", color=" + color + ", gender=" + gender + ", price=" + price + '}';
    }
    
}
