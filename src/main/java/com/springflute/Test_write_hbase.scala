package com.springflute

import org.apache.spark.sql.SparkSession

object Test_write_hbase {
  def main(args: Array[String]): Unit = {
    val catalog =
      s"""{
         |"table":{"namespace":"default","name":"test"},
         |"rowkey":"id",
         |"columns":{
         |"col0":{"cf":"rowkey", "col":"id", "type":"String"},
         |"col1":{"cf":"info", "col":"value", "type":"String"},
         |}
         |}""".stripMargin

    val spark = SparkSession.builder()
      .appName("Test_write_hbase")
      .master("local" )
      .getOrCreate()

    import spark.implicits._
    //造数据
    val data = Array(("hbase","1"))
      .map(x => HBaseRecord(x._1,x._2))
    //写数据
    spark.sparkContext.parallelize(data)
      .toDF()
      .write
      .format("org.apache.spark.sql.execution.datasources.hbase")
      .options(Map( " " -> catalog))
      .save()
  }
  case class HBaseRecord(col1:String,col2:String)
}


