package com.springflute

import org.apache.spark.sql.SparkSession

object Test_SQL {

  case class Student(sid:String, sname:String, sbirth:String, ssex:String)
  case class Course(cid:String, cname:String, tid:String)
  case class Teacher(tid:String, tname:String)
  case class Score(sid:String, cid:String, sscore:Int)

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder()
      .appName("test_sql")
      .master("local[1]" )
      .getOrCreate()

    val student = sparkSession.read.csv("d:/data/student.csv")
      .toDF("sid","sname","sbirth","ssex")
      .createOrReplaceTempView("student")
    val course = sparkSession.read.csv("d:/data/course.csv")
      .toDF("cid","cname","tid")
      .createOrReplaceTempView("course")
    val teacher = sparkSession.read.csv("d:/data/teacher.csv")
      .toDF("tid","tname")
      .createOrReplaceTempView("teacher")
    val score = sparkSession.read.csv("d:/data/score.csv")
      .toDF("sid","cid","sscore")
      .createOrReplaceTempView("score")


    //val sql = "select t1.*, t2.sscore score01 ,t3.sscore score02 from student t1 join score t2 on t1.sid = t2.sid and t2.cid = '01' left join score t3 on t1.sid = t3.sid and t3.cid = '02' where t2.sscore > t3.sscore"
    //val sql = "select t1.sid sid,t2.sname sname,t1.avg avg from (select sid,avg(sscore) avg from score group by sid having avg > 60 ) t1 left join student t2 on t1.sid = t2.sid"
    //val sql =
      """
        |select
        |t1.sid,t1.sname,count(t2.cid),sum(t2.sscore)
        |from student t1 left join score t2 on t1.sid = t2.sid
        |group by t1.sid,t1.sname
      """.stripMargin
    //val sql =
      """
        |select
        |count(1)
        |from teacher
        |where tname like "李%"
      """.stripMargin
    //val sql =
      """
        |select
        |t1.sid
        |from score t1 left join course t2 on t1.cid = t2.cid left join teacher t3 on t3.tid = t2.tid
        |where t3.tname = "张三"
      """.stripMargin
    //val sql =
      """
        |select
        |t1.*
        |from student t1 left join
        |(select sid from score
        |join  course on course.cid=score.cid
        |join  teacher on course.tid=teacher.tid and tname='张三') t2 on t1.sid = t2.sid
        |where t2.sid is null
      """.stripMargin
    //val sql =
      """
        |select
        |t1.sid
        |from
        |(select sid from score where cid = '01') t1 join
        |(select sid from score where cid = '02') t2 on t1.sid = t2.sid
      """.stripMargin
    //val sql =
      """
        |select
        |t1.sid
        |from
        |(select sid from score where cid = '01') t1 left join
        |(select sid from score where cid = '02') t2 on t1.sid = t2.sid
        |where t2.sid is null
      """.stripMargin
    val sql =
      """
        |select
        |a.*,b.count
        |from student a
        |left join (select sid,count(1) count from score group by sid having count = 3) b on a.sid = b.sid
        |where b.sid is null
      """.stripMargin

    sparkSession.sql(sql).show()

    sparkSession.close()
  }
}
