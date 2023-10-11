import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;




public class Person {


    private String firstName;
    private String lastName;
    private int height;   //height in cm
    private List<Person> children;

    public Person(String firstName, String lastName) {

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String firstName, String lastName, int height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void addChild(Person child) {
        if(children==null) {
            children = new ArrayList<>();
        }

        children.add(child);
    }

    public List<Person> getChildren() {
        return children;
    }


    public int getHeight() {
        return height;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(firstName + " " + lastName);

        if (children != null) {
            builder.append(" (Kinder: " +
                children.stream().map(Person::toString).collect(Collectors.joining(", "))
            +")");
        }

        return builder.toString();
    }
}
