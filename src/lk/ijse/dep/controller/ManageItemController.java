package lk.ijse.dep.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.dep.business.custom.ItemBO;
import lk.ijse.dep.business.custom.impl.ItemBOimpl;
import lk.ijse.dep.dto.CustomerDTO;
import lk.ijse.dep.dto.ItemDTO;
import lk.ijse.dep.entity.Item;
import lk.ijse.dep.utiltm.ItemTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import javax.persistence.NoResultException;
import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageItemController {
    public Label lblItem;
    public JFXTextField txtCode;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public Button btn_Save;
    public Label lblBack;
    public TableView<ItemTM> tblItem;
    public ObservableList<ItemTM> itemTMS;
    public ItemTM value;
    public Connection connection;
    public JFXButton btnReport;
    private ItemBO itemBO = new ItemBOimpl();

    public void initialize() {
        itemCodeGenerator();
        txtCode.setEditable(false);
        tblItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        itemTMS = tblItem.getItems();

        try {
            List<ItemDTO> allItems = itemBO.getAll();

            for (ItemDTO itemDTO : allItems) {
                itemTMS.add(new ItemTM(itemDTO.getCode(), itemDTO.getDescription(), itemDTO.getQtyOnHand(), itemDTO.getUnitPrice()));
            }
            tblItem.refresh();
        } catch (Exception e) {
            Logger.getLogger("lk.ijse.dep").log(Level.SEVERE,null,e);
        }
        lblItem.requestFocus();

        TableColumn<ItemTM, ItemTM> unfriendCol = new TableColumn<>("");
        unfriendCol.setMinWidth(40);
        unfriendCol.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        unfriendCol.setCellFactory(param -> new TableCell<ItemTM, ItemTM>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(ItemTM itemTM, boolean empty) {

                super.updateItem(itemTM, empty);

                if (itemTM == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(
                        event -> tblItem.getItems().remove(itemTM)
                );
                deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            boolean isUpdated = itemBO.remove(itemTM.getCode());
                            if (isUpdated) {
                                itemTMS.remove(itemTM);
                                new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully", ButtonType.OK).showAndWait();
                            } else {
                                new Alert(Alert.AlertType.INFORMATION, "Failed Delete", ButtonType.OK).showAndWait();
                            }
                        } catch (Exception e) {
                            Logger.getLogger("lk.ijse.dep").log(Level.SEVERE,null,e);
                        }
                    }
                });
            }
        });
        tblItem.getColumns().add(unfriendCol);
        tblItem.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                value = (ItemTM) observable.getValue();

                try {
                    txtCode.setText(value.getCode());
                    txtDescription.setText(value.getDescription());
                    txtQtyOnHand.setText(Integer.toString(value.getQtyOnHand()));
                    txtUnitPrice.setText(Double.toString(value.getUnitPrice()));
                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(null, "Empty Table Please Refresh");
                }
                btn_Save.setText("Update");

//                txtCode.setEditable(false);
//                txtDescription.setEditable(false);
//                txtQtyOnHand.setEditable(false);
//                txtUnitPrice.setEditable(false);
//                txtCode.clear();
//                txtDescription.clear();
//                txtQtyOnHand.clear();
//                txtUnitPrice.clear();
            }
        });
    }

    public void txtCode_Clicked(ActionEvent actionEvent) {
        txtDescription.requestFocus();
    }

    public void txtDescription_Clicked(ActionEvent actionEvent) {
        txtQtyOnHand.requestFocus();
    }

    public void txtQtyOnHand_Clicked(ActionEvent actionEvent) {
        txtUnitPrice.requestFocus();
    }

    public void txtUnitPrice_Clicked(ActionEvent actionEvent) {
        btn_Save.requestFocus();
    }

    public void lblItem_Clicked(MouseEvent mouseEvent) {
        txtCode.requestFocus();
        txtCode.clear();
        txtDescription.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
        itemCodeGenerator();
    }

    public void btn_Save_Clicked(ActionEvent actionEvent) {
        if (!txtCode.getText().equals("") && !txtDescription.getText().equals("") && !txtQtyOnHand.getText().equals("") && !txtUnitPrice.getText().equals("") && btn_Save.getText().equals("Save")) {
            itemTMS.add(new ItemTM(txtCode.getText(), txtDescription.getText(), Integer.parseInt(txtQtyOnHand.getText()), Double.parseDouble(txtUnitPrice.getText())));
            tblItem.refresh();
        }
        if (btn_Save.getText().equals("Save")) {
            try {
                boolean isUpdated = itemBO.save(new ItemDTO(txtCode.getText(), txtDescription.getText(), Double.parseDouble(txtUnitPrice.getText()), Integer.parseInt(txtQtyOnHand.getText())));
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Item Successfully Saved", ButtonType.OK).showAndWait();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Failed To Save Item", ButtonType.OK).showAndWait();
                }
            } catch (Exception e) {
                Logger.getLogger("lk.ijse.dep").log(Level.SEVERE,null,e);
            }
        }else{
            try {
                boolean isUpdated = itemBO.update(new ItemDTO(txtCode.getText(), txtDescription.getText(), Double.parseDouble(txtUnitPrice.getText()), Integer.parseInt(txtQtyOnHand.getText())));
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Item Successfully Updated", ButtonType.OK).showAndWait();
                    ItemTM selectedItemTM = tblItem.getSelectionModel().getSelectedItem();
                    selectedItemTM.setCode(txtCode.getText());
                    selectedItemTM.setDescription(txtDescription.getText());
                    selectedItemTM.setQtyOnHand(Integer.parseInt(txtQtyOnHand.getText()));
                    selectedItemTM.setUnitPrice(Double.parseDouble(txtUnitPrice.getText()));
                    tblItem.refresh();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Failed Failed To Update Item", ButtonType.OK).showAndWait();
                }
            } catch (Exception e) {
                Logger.getLogger("lk.ijse.dep").log(Level.SEVERE, null, e);
            }
        }
        txtCode.clear();
        txtDescription.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
        itemCodeGenerator();
    }

    public void lblBack_Clicked(MouseEvent mouseEvent) throws IOException {
        try{
            Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/view/DashBoard.fxml"));
            Scene mainScene = new Scene(root);
            Stage mainStage = (Stage) lblBack.getScene().getWindow();
            mainStage.setScene(mainScene);
            mainStage.setTitle("DashBoard");
            mainStage.centerOnScreen();
            mainStage.show();
        } catch (IOException e) {
            Logger.getLogger("lk.ijse.dep").log(Level.SEVERE,null,e);
        }
    }

    public void itemCodeGenerator() {

        try {
            String maxIndex = itemBO.getMaxItemCode();
            int maxId = Integer.parseInt(maxIndex.substring(1));
            if (++maxId < 10) {
                txtCode.setText("P00" + maxId);
            } else if (maxId < 100) {
                txtCode.setText("P0" + maxId);
            } else if (maxId < 1000) {
                txtCode.setText("P" + maxId);
            }
            txtCode.setEditable(false);

        }catch (NoResultException e){
            txtCode.setText("P001");
        }
        catch (Exception e) {
            Logger.getLogger("lk.ijse.dep").log(Level.SEVERE,null,e);
        }
    }

    public void btnReport_Action(ActionEvent actionEvent) throws JRException {
        try {
            List<ItemDTO> all = itemBO.getAll();
            JasperReport jasperReport = JasperCompileManager.compileReport("D:\\Java\\MyPosSystemJPA\\PosSystem\\src\\lk\\ijse\\dep\\reports\\ItemReport1_1.jrxml");
            HashMap<String, Object> objectObjectHashMap = new HashMap<>();
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(all);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, objectObjectHashMap, ds);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            Logger.getLogger("lk.ijse.dep").log(Level.SEVERE, null, e);
        }
    }
}
