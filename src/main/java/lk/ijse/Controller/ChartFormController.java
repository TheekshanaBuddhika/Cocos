package lk.ijse.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import lk.ijse.Model.Production;


import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChartFormController implements Initializable {

    @FXML
    private LineChart<?, ?> linechrt;

    private void chart() throws SQLException {

        ArrayList <String> month = new ArrayList<String>();
        month.add("Jan");
        month.add("Feb");
        month.add("Mar");
        month.add("April");
        month.add("May");
        month.add("June");
        month.add("July");
        month.add("Aug");
        month.add("Sept");
        month.add("Oct");
        month.add("Nov");
        month.add("Dec");

        XYChart.Series series = new XYChart.Series();
        series.setName("Progress");
        for(int i = 1 ; i < 13 ;i++){
            Double p = Production.getProduct2(i);
            series.getData().add(new XYChart.Data(month.get(i-1),p));
        }

        linechrt.getData().add(series);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            chart();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
