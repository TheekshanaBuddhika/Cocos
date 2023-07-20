package lk.ijse.Controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import lk.ijse.dbconnection.DBConnection;
import lk.ijse.util.Notification;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReportFormController {

    @FXML
    private DatePicker datepick;

    @FXML
    void empOnAction(ActionEvent event) throws JRException, SQLException {
        Thread thread = new Thread(()->{
            try {
                InputStream rpt = ReportFormController.class.getResourceAsStream("/assests/report/Employee2.jrxml");
                JasperReport compile =  JasperCompileManager.compileReport(rpt);
                JasperPrint report = JasperFillManager.fillReport(compile,null,DBConnection.getInstance().getConnection());
                JasperViewer.viewReport(report,false);

            }catch (Exception e){

            }
        });

        thread.start();


    }

    @FXML
    void logOnAction(ActionEvent event) throws JRException, SQLException {
        if(!(datepick.getValue()==null)){
            Date date = Date.valueOf(datepick.getValue());
            Thread thread = new Thread(()->{
                try {
                    InputStream rpt = ReportFormController.class.getResourceAsStream("/assests/report/loghistory.jrxml");
                    JasperReport compile =  JasperCompileManager.compileReport(rpt);
                    Map<String,Object> data = new HashMap<>();
                    data.put("dt",date);
                    JasperPrint report = JasperFillManager.fillReport(compile,data,DBConnection.getInstance().getConnection());
                    JasperViewer.viewReport(report,false);

                }catch (Exception e){
                    e.printStackTrace();
                }
            });

            thread.start();
        }else{
            Notification.notice(new Image("/assests/image/wrong.png"),"Report Error","Pick a Date");
        }

    }

    @FXML
    void prOnAction(ActionEvent event) throws JRException, SQLException {
        Thread thread = new Thread(()->{
            try {
                InputStream rpt = ReportFormController.class.getResourceAsStream("/assests/report/Production.jrxml");
                JasperReport compile =  JasperCompileManager.compileReport(rpt);
                JasperPrint report = JasperFillManager.fillReport(compile,null,DBConnection.getInstance().getConnection());
                JasperViewer.viewReport(report,false);

            }catch (Exception e){

            }
        });

        thread.start();

    }

    @FXML
    void saleOnAction(ActionEvent event) throws JRException, SQLException {
        if(!(datepick.getValue()==null)){
            Date date = Date.valueOf(datepick.getValue());
            Thread thread = new Thread(()->{
                try {
                    InputStream rpt = ReportFormController.class.getResourceAsStream("/assests/report/Dailysales.jrxml");
                    JasperReport compile =  JasperCompileManager.compileReport(rpt);
                    Map<String,Object> data = new HashMap<>();
                    data.put("dt2",date);
                    JasperPrint report = JasperFillManager.fillReport(compile,data,DBConnection.getInstance().getConnection());
                    JasperViewer.viewReport(report,false);

                }catch (Exception e){

                }
            });

            thread.start();

        }else{
            Notification.notice(new Image("/assests/image/wrong.png"),"Report Error","Pick a Date");
        }
    }


}
