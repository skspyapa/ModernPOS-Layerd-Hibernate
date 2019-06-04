package lk.ijse.dep.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.dep.business.custom.CustomerBO;
import lk.ijse.dep.business.custom.impl.CustomerBOimpl;
import lk.ijse.dep.dto.CustomerDTO;
import lk.ijse.dep.utiltm.CustomerTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.persistence.NoResultException;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageCustomerController {
    public JFXTextField txtID;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public Label lbl_new_Customer;
    public JFXButton save_Action;
    public JFXButton Remove_Action;
    public TableView<CustomerTM> tblCustomer;
    public ObservableList<CustomerTM> items;
    public CustomerTM value;
    public Label lblBack;
    public Connection connection;
    public JFXButton btnReport;
    public JFXTextField txtSalary;
    private CustomerBO customerBO = new CustomerBOimpl();

    public void initialize() {

        customerIdGenerator();
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("salary"));
        items = tblCustomer.getItems();
        loadCustoers();

        lbl_new_Customer.requestFocus();
        tblCustomer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                value = (CustomerTM) observable.getValue();
                if (value != null) {
                    txtID.setText(value.getId());
                    txtName.setText(value.getName());
                    txtAddress.setText(value.getAddress());
                    txtSalary.setText(Double.toString(value.getSalary()));
                    save_Action.setText("Update");
//            txtID.setEditable(false);
//            txtName.setEditable(false);
//            txtAddress.setEditable(false);
                }
            }
        });
    }

    public void save_Action_CLicked(ActionEvent actionEvent) {

        if (!txtID.getText().equals("") && !txtName.getText().equals("") && !txtAddress.getText().equals("") && !txtSalary.getText().equals("") && save_Action.getText().equals("Save")) {
            items.add(new CustomerTM(txtID.getText(), txtName.getText(), txtAddress.getText(), Double.parseDouble(txtSalary.getText())));
        }
            if (save_Action.getText().equals("Save")) {
                try {
                    boolean result = customerBO.save(new CustomerDTO(txtID.getText(), txtName.getText(), txtAddress.getText(), Double.parseDouble(txtSalary.getText())));
                    if (result) {
                        new Alert(Alert.AlertType.INFORMATION, "Customer Successfully Added", ButtonType.OK).showAndWait();
                    } else {
                        new Alert(Alert.AlertType.INFORMATION, "Failed To Add Customer", ButtonType.OK).showAndWait();
                    }
                } catch (Exception e) {
                    Logger.getLogger("lk.ijse.dep").log(Level.SEVERE, null, e);
                }
            } else {
                for (CustomerTM customerTM : items) {
                    if (customerTM.getId().equals(value.getId())) {
                        customerTM.setName(txtName.getText());
                        customerTM.setAddress(txtAddress.getText());
                        customerTM.setSalary(Double.parseDouble(txtSalary.getText()));
                        try {
                            boolean isUpdated = customerBO.update(new CustomerDTO(customerTM.getId(), customerTM.getName(), customerTM.getAddress(), customerTM.getSalary()));
                            if (isUpdated) {
                                tblCustomer.getItems().clear();
                                loadCustoers();
                                new Alert(Alert.AlertType.INFORMATION, "Customer Successfully Updated", ButtonType.OK).showAndWait();
                                save_Action.setText("Save");
                            } else {
                                new Alert(Alert.AlertType.INFORMATION, "Failed To Update Customer", ButtonType.OK).showAndWait();
                            }
                        } catch (Exception e) {
                            Logger.getLogger("lk.ijse.dep").log(Level.SEVERE, null, e);
                        }
                        break;
                    }
                }
            }



        txtID.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
        customerIdGenerator();
    }

    public void loadCustoers() {
        try {

            List<CustomerDTO> allCustomers = customerBO.getAll();
            for (CustomerDTO customerDTO : allCustomers) {
                items.add(new CustomerTM(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getSalary()));
            }
        } catch (Exception e) {
            Logger.getLogger("lk.ijse.dep").log(Level.SEVERE, null, e);
        }
    }

    public void Remove_Action_Clicked(ActionEvent actionEvent) {
        try {
            boolean isUpdated = customerBO.remove(txtID.getText());
            if (isUpdated) {
                items.remove(value);
                tblCustomer.refresh();

            } else {
                JOptionPane.showMessageDialog(null, "Not Updated");
            }
        } catch (Exception e) {
            Logger.getLogger("lk.ijse.dep").log(Level.SEVERE, null, e);
        }
        txtID.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
        customerIdGenerator();
    }

    public void lbl_new_Customer_Clicked(MouseEvent mouseEvent) {
        txtID.requestFocus();
        txtID.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
        txtName.setEditable(true);
        txtAddress.setEditable(true);
        txtSalary.setEditable(true);
        tblCustomer.refresh();
        customerIdGenerator();
    }

    public void txtID_Action(ActionEvent actionEvent) {
        txtName.requestFocus();
    }

    public void txtName_Action(ActionEvent actionEvent) {
        txtAddress.requestFocus();
    }

    public void txtAddress_Action(ActionEvent actionEvent) {
        txtSalary.requestFocus();
    }

    public void lblBack_Clicked(MouseEvent mouseEvent) throws IOException {
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/view/DashBoard.fxml"));
            Scene mainScene = new Scene(root);
            Stage dashStage = (Stage) lblBack.getScene().getWindow();
            dashStage.setScene(mainScene);
            dashStage.setTitle("DashBoard");
            dashStage.centerOnScreen();
            dashStage.show();
        } catch (IOException e) {
            Logger.getLogger("lk.ijse.dep").log(Level.SEVERE, null, e);
        }
    }

    public void customerIdGenerator() {

        try {
            String maxIndex = customerBO.getMaxCustId();
            int maxId = Integer.parseInt(maxIndex.substring(1));

            if (++maxId < 10) {
                txtID.setText("C00" + maxId);
            } else if (maxId < 100) {
                txtID.setText("C0" + maxId);
            } else if (maxId < 1000) {
                txtID.setText("C" + maxId);
            }
            txtID.setEditable(false);
        } catch (NoResultException e) {
            txtID.setText("C001");
            Logger.getLogger("lk.ijse.dep").log(Level.SEVERE, null, e);
        } catch (Exception e) {
            Logger.getLogger("lk.ijse.dep").log(Level.SEVERE, null, e);
        }
    }


    public void btnReport_Action(ActionEvent actionEvent) throws JRException {
        try {
            List<CustomerDTO> all = customerBO.getAll();
            JasperReport jasperReport = JasperCompileManager.compileReport("D:\\Java\\MyPosSystemJPA\\PosSystem\\src\\lk\\ijse\\dep\\reports\\CustomerReport.jrxml");
            HashMap<String, Object> objectObjectHashMap = new HashMap<>();

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(all);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, objectObjectHashMap, ds);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            Logger.getLogger("lk.ijse.dep").log(Level.SEVERE, null, e);
        }
    }

    public void txtSalary_Action(ActionEvent actionEvent) {
        save_Action.requestFocus();
    }
}
