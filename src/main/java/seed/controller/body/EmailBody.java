package seed.controller.body;

public class EmailBody {
    private String name;
    private int  isOpen=1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }
}
