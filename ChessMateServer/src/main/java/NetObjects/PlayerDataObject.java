package NetObjects;

public class PlayerDataObject {
    String name;
    boolean color;

    public PlayerDataObject(){}

    public String getName() {
        return name;
    }

    public boolean isColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(boolean color) {
        this.color = color;
    }
}
