package view.view2;

import control.ShopHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Constants;
import model.Shop.Shop;
import model.User.Buyer;
import model.User.User;
import view.view1.CartAndPayment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Homepage {


    @FXML
    ImageView   cartImageView;

    @FXML
    ImageView   shopImageView1,
                shopImageView2,
                shopImageView3,
                shopImageView4;

    @FXML
    Text    shopText1,
            shopText2,
            shopText3,
            shopText4;


    User user = null;

    ArrayList<Shop> shopArrayList;

    public void passParams(User user) throws FileNotFoundException {
        this.user = user;

        ArrayList<Text> textArrayList = new ArrayList<>(){
            {
                add(shopText1);
                add(shopText2);
                add(shopText3);
                add(shopText4);
            }
        };
        ArrayList<ImageView> imageViewArrayList = new ArrayList<>() {
            {
                add(shopImageView1);
                add(shopImageView2);
                add(shopImageView3);
                add(shopImageView4);
            }
        };

        shopArrayList = ShopHandler.findShopBy(((Buyer) user).getBillingAddress(), Constants.NEARBY, false);
        if (shopArrayList != null && shopArrayList.size() != 0){
            for (int i = 0; i < 4; i++) {
                imageViewArrayList.get(i).setImage(new Image(new FileInputStream(shopArrayList.get(i).getLogoImagepath())));
                textArrayList.get(i).setText(shopArrayList.get(i).getShopName());
            }
        }
    }

    public void openCartAndPayment() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view2/cartAndPayment.fxml"));
        Parent root = loader.load();
        CartAndPayment cartAndPayment = loader.getController();
        cartAndPayment.passParam(null, user);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) cartImageView.getScene().getWindow();
        stage.close();
    }
}