package com.springflute

import org.apache.spark.sql.SparkSession
import util._

object Test_data_cleaning {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("data_cleaning")
      //.master("local")
      .getOrCreate()

//    val id = spark.read.textFile("d:/data/id.txt")
//      .rdd
//      .map(_.split(" "))
//      .map(s => (s(0),s(1)))
//      .map(s => {
//        val id1 = ID_check_util.checkIDNo(s._2)
//        (s._1,id1)
//      })
//      .filter(s => s._2!= null)
//      .saveAsTextFile("d:/data/newID")

    spark.read.textFile("d:/data/VIN.txt")
      .rdd
      .map(s => VIN_check_util.checkVIN(s))
      .filter(s => s != null)
      .saveAsTextFile("d:/data/newVIN")

    spark.close()
  }
}
