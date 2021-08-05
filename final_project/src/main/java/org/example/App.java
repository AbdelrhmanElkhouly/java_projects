package org.example;

import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.*;
//import pyspark.sql.functions as f ;


import java.util.*;

import static scala.reflect.internal.util.NoPosition.show;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        System.out.println("Hello World!");
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
        JavaRDD<String> eachSkill = skillsRdd.flatMap(line -> Arrays.asList(line.split(",")).iterator());
        for (String line : eachSkill.collect()) {
            System.out.println("* " + line);
        }

    }
}

