package org.example;

import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class JobsDAO {
  private SparkSession sparkSession;

  public JobsDAO() {
    sparkSession = SparkSession.builder().appName("WuzzufJobs").master("local[4]").getOrCreate();

  }

  public Dataset<Row> readCsv() {
    DataFrameReader dataFrameReader = sparkSession.read();
    dataFrameReader.option("header", true);
    Dataset<Row> jobs = dataFrameReader.csv("src/main/resources/Wuzzuf_Jobs.csv");
    return jobs;


  }
}
