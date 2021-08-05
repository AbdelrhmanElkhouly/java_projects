package org.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.knowm.xchart.*;

import java.util.ArrayList;
import java.util.List;

public class Chart {
    public static void graphCompanies(Dataset<Row> companyCount)
    {

        PieChart chart = new PieChartBuilder().width(800).height(600)
                .title("jobs per company").build();



//       companyCount.collectAsList().forEach(r->chart.addSeries(r.<String>getAs(0),r.<Number>getAs(1)));
        companyCount.collectAsList().stream().limit(10).forEach(r->chart.addSeries(r.<String>getAs(0),r.<Number>getAs(1)));

        // Show it
        new SwingWrapper(chart).displayChart();
    }

    public static void companiesJobChart(Dataset<Row> jobCount)
    {

        List<String> companiesNames = new ArrayList();
        List<Number> count = new ArrayList();
        jobCount.collectAsList().forEach(row ->{
            companiesNames.add(row.getAs(0));
            count.add(row.getAs(1));
        });


        CategoryChart chart = new CategoryChartBuilder().width(1024)
                .height(768).title("count per companies")
                .xAxisTitle("companies").yAxisTitle("cont").build();
        //let customized chart to default values
        chart.addSeries("count per company ",companiesNames,count);
        //to show it
        new SwingWrapper(chart).displayChart();
    }

    public static void areasNames(Dataset<Row> most_popular_area)
    {

        List<String> areasNames = new ArrayList<>();
        List<Number> count = new ArrayList<>();
        most_popular_area.collectAsList().forEach(r->{
            areasNames.add(r.getAs(0));
            count.add(r.getAs(1));
        });
        CategoryChart chart = new CategoryChartBuilder().width(1024)
                .height(768).title("most popular area").xAxisTitle("areas").yAxisTitle("count")
                .build();
        chart.addSeries("most popular area",areasNames,count);
        new SwingWrapper(chart).displayChart();



    }
}
