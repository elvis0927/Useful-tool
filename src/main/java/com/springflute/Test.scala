package com.springflute

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession, types}
import org.apache.spark.{SparkConf, SparkContext}

object Test {
  def main(args: Array[String]): Unit = {

    val s = s"""row${"%03d".format(10)}"""
    println(s)

    val conf = new SparkConf()
      .setAppName("")
      .setMaster("local[1]")
    val spark = new SparkContext(conf)

    val txt = spark.textFile("d:/data/person.txt")
      .map(_.split(","))
      .map(p => (p(0).toInt + 1, p(1), p(2)))
      .sortBy(_._1, false)
      .foreach(println)
    println()

    val list = List(3,1,9,5,4)
    list.sortWith((x,y) => x > y).foreach(println)
    val listRDD: RDD[Int] = spark.parallelize(list)
    listRDD.map(_ + 1)
      .collect()
      .foreach(println)
    val ds = spark.range(1, 20)

    val ss = SparkSession.builder()
      .master("local[*]")
      .getOrCreate()
    val csv = ss.read.csv("d:/data/person.csv")
      .toDF("id","name","gender")
      .filter("id < 11")
    csv.show()
    csv.printSchema()

    val lines = spark.parallelize(List("1,zs,19","3,ls,20","6,ww,88"))
    val rowRDD = lines.map(x => {
      val fields = x.split(",")
      val id = fields(0).toInt
      val name = fields(1)
      val age = fields(2).toInt
      Row(id,name,age)
    })
    val schema = StructType(List(
      StructField("id",IntegerType),
      StructField("name",StringType),
      StructField("age",IntegerType)
    ))
    val df = ss.createDataFrame(rowRDD,schema)
    df.createOrReplaceTempView("person")
    val res = ss.sql("select * from person")
    res.show()
    res.write.saveAsTable("person1")

    spark.stop()
  }

}
