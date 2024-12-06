

import controller.AdminController;
import view.AdminView;
import model.Admin;

public class Main {
    public static void main(String[] args) {
        AdminView view = new AdminView();
        Admin adminModel = new Admin();
        AdminController controller = new AdminController(view, adminModel);
        controller.start();
    }
}
