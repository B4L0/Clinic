package me.sobolewski.clinic.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import me.sobolewski.clinic.Clinic;
import me.sobolewski.clinic.manager.FXMLManager;
import me.sobolewski.clinic.model.Examination;
import me.sobolewski.clinic.model.util.EntityUtils;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class StatsController implements Initializable {
    
    public Button backButton;
    public LineChart<String, Integer> visits7DaysChart;
    public LineChart<String, Integer> visits30DaysChart;
    public BarChart<String, Integer> examinationsChart;
    public BarChart<String, Integer> drugsChart;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initDaysChart(visits7DaysChart, 7);
        initDaysChart(visits30DaysChart, 30);
        
        initExaminationsChart();
    }
    
    private void initDaysChart(LineChart<String, Integer> chart, int days) {
        
        chart.getXAxis().setLabel("Dzień");
        chart.getYAxis().setLabel("Ilość wizyt");
        chart.setLegendVisible(false);
        
        ObservableList<String> categories = FXCollections.observableArrayList();
        
        for (int i = days - 1; i >= 0; i--) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);
            categories.add(String.valueOf(cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
        }
        
        ((CategoryAxis) chart.getXAxis()).setCategories(categories);
        
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        
        for (int i = 0; i < days; i++) {
            series.getData().add(new XYChart.Data<>(
                    categories.get(i),
                    EntityUtils.getVisitsByDay(
                            LocalDate.parse(categories.get(i)),
                            Clinic.getLoginSession().getLoggedDoctor())));
        }
        
        chart.getData().add(series);
        
    }
    
    private void initExaminationsChart(){
        ResultSet rs = EntityUtils.getTopExaminations(Clinic.getLoginSession().getLoggedDoctor());
        Map<String, Integer> rsMap = new HashMap<>();
        try{
            while(rs.next()){
                rsMap.put(
                        EntityUtils.getByID(Examination.class, Integer.parseInt(rs.getString(1))).getName(),
                        rs.getInt(2)
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    
        ((CategoryAxis) examinationsChart.getXAxis()).setCategories(FXCollections.observableArrayList(rsMap.keySet()));
    
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        
        examinationsChart.getXAxis().setLabel("Badanie");
        examinationsChart.getYAxis().setLabel("Wykonano razy");
        
        for(int i = 0; i < 5; i++){
            series.getData().add(new XYChart.Data<>(
                    rsMap.keySet().stream().toList().get(i),
                    rsMap.get(rsMap.keySet().stream().toList().get(i))
            ));
        }
        
        examinationsChart.getData().add(series);
        
    }
    
    public void back() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("start"));
    }
}
