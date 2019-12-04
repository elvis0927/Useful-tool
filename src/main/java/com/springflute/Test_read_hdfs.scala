package com.springflute

import org.apache.spark.sql.SparkSession

object Test_read_hdfs {
  case class Person(id:Int,name:String,sex:String)
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
                  .appName("Test_read_hdfs")
                    .master("local[*]")
                    .getOrCreate()
    import spark.implicits._
    val person = spark.read.textFile("/test/person")
      .map(_.split(","))
      .map(p => Person(p(0).toInt,p(1),p(2)))
      .toDF()
    person.show()
    person.printSchema()
    person.createOrReplaceTempView("person")

    val sql = "select * from person"
    val res = spark.sql(sql)
      .filter("")
      .collect()
      .foreach(println)
    spark.stop()
  }
}
