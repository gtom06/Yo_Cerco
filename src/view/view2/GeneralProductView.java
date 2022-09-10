package view.view2;

import model.Product.SimpleProduct;
import model.User.User;

public class GeneralProductView {
    User user;
    SimpleProduct simpleProduct;

    public void passParams(User user, SimpleProduct simpleProduct) {
        this.user = user;
        this.simpleProduct = simpleProduct;
    }

}
