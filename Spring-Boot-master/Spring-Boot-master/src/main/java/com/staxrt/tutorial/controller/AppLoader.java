
// package org.example;
package com.staxrt.tutorial.controller;

import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.*;
//import pyspark.sql.functions as f ;
import com.staxrt.tutorial.controller.JobsDAO;
import com.staxrt.tutorial.controller.Chart;

import java.util.*;

import static scala.reflect.internal.util.NoPosition.show;

/**
 * Hello world!
 *
 */
public class AppLoader
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Logger.getLogger("org").setLevel(Level.ERROR);
        JobsDAO jobs = new JobsDAO();
        Dataset<Row> jobsDs = jobs.readCsv();
        jobsDs.show();
        jobsDs.printSchema();
        jobsDs.describe(jobsDs.columns()).show();
        System.out.println(jobsDs.count());
        jobsDs.distinct();
        jobsDs.na().drop("any");
        System.out.println(jobsDs.count());
        Dataset<Row> company_count = jobsDs.groupBy("Company").count().sort(functions.col("count").desc());
        company_count.show();
        Chart.graphCompanies(company_count);
        Dataset<Row> job_count = jobsDs.groupBy("Title").count().sort(functions.col("count").desc());
        job_count.show();
        Chart.companiesJobChart(job_count);
        Dataset<Row> most_pop_area = jobsDs.groupBy("Location").count().sort(functions.col("count").desc());
        most_pop_area.show();
        Chart.areasNames(most_pop_area);

        Dataset<String> skills_col = jobsDs.select("Skills").as(Encoders.STRING());
        JavaRDD<String> skillsRdd = skills_col.toJavaRDD();
        JavaRDD<String> eachSkill = skillsRdd.flatMap(line-> Arrays.asList(line.split(",")).iterator());
        for(String line:eachSkill.collect()){
            System.out.println("* "+line);
        }
    }

    public String listData(){
        JobsDAO jobs = new JobsDAO();
        Dataset<Row> jobsDs = jobs.readCsv();
        String result =  jobsDs.showString(10 ,25, false);
        return "<pre>" + result + "</pre>";
    }

    public String listCompanyCount(){
        JobsDAO jobs = new JobsDAO();
        Dataset<Row> jobsDs = jobs.readCsv();
        Dataset<Row> company_count = jobsDs.groupBy("Company").count().sort(functions.col("count").desc());
        String result =  company_count.showString(10 ,25, false);
        return "<pre>" + result + "</pre>";

    }
    public String listJobCount()
    {
        JobsDAO jobs = new JobsDAO();
        Dataset<Row> jobsDs = jobs.readCsv();
        Dataset<Row> job_count = jobsDs.groupBy("Title").count().sort(functions.col("count").desc());
        String result =  job_count.showString(10 ,25, false);
        return "<pre>" + result + "</pre>";
    }

    public String mostPopularArea()
    {
        JobsDAO jobs = new JobsDAO();
        Dataset<Row> jobsDs = jobs.readCsv();
        Dataset<Row> most_pop_area = jobsDs.groupBy("Location").count().sort(functions.col("count").desc());
        String result =  most_pop_area.showString(10 ,25, false);
        return "<pre>" + result + "</pre>";
    }
}
