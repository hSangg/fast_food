package utils;

import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UtilityFunctions {
    public boolean isPhoneNumber(String input) {
        return input.matches("^0[0-9]{9}$|^\\+84[0-9]{9}$|^\\(\\+84\\)\\s?[0-9]{9}$");
    }

    public boolean isEmptyString(String input) {
        return input == null || input.isEmpty();
    }

    public boolean isNumericString(String input) {
        if (input == null || input.isEmpty()) {
            return false; // Input is null or empty
        }
        return input.matches("^[0-9]+$");
    }

    public void addColumnHeaderIcon(TableColumn<?, ?> column, String imagePath, String columnHeader) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        icon.setFitWidth(20);
        icon.setFitHeight(20);
        column.setGraphic(icon);
        column.setText(columnHeader);
    }

}
