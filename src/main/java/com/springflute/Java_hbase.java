package com.springflute;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.io.encoding.DataBlockEncoding;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Java_hbase {

    private static Configuration conf;
    private Connection conn;
    private TableName tableName;

    private static void init(){
        conf = HBaseConfiguration.create();
        String userdir = System.getProperty("user.dir") + File.separator;
        conf.addResource(new Path(userdir + "hbase-site.xml"));
        conf.addResource(new Path(userdir + "hdfs-site.xml"));
        conf.addResource(new Path(userdir + "core-site.xml"));
    }

    public void getConnection(Configuration conf) throws IOException {
        conn = ConnectionFactory.createConnection(conf);
        tableName = TableName.valueOf("hbase_sample_table");
    }

    public void func() throws IOException {
        HTableDescriptor htd = new HTableDescriptor(tableName);
        HColumnDescriptor hcd = new HColumnDescriptor("info");
        hcd.setCompressionType(Compression.Algorithm.SNAPPY);
        hcd.setDataBlockEncoding(DataBlockEncoding.FAST_DIFF);
        htd.addFamily(hcd);

        //获取admin对象，创建表
        Admin admin = conn.getAdmin();
        if(!admin.tableExists(tableName)){
            admin.createTable(htd);
        }

        //修改表结构，添加列族
        byte[] familyName = Bytes.toBytes("info");
        if(!htd.hasFamily(familyName)){
            hcd = new HColumnDescriptor(familyName);
            htd.addFamily(hcd);
            admin.disableTable(tableName);
            admin.modifyTable(tableName,htd);
            admin.enableTable(tableName);
        }

        //获取table对象、put对象，向表中插入数据
        byte[][] qualifier = {Bytes.toBytes("name"),Bytes.toBytes("age"),Bytes.toBytes("sex")};
        Table table = conn.getTable(tableName);
        List<Put> puts = new ArrayList<Put>();
        Put put = new Put(Bytes.toBytes("001"));
        put.addColumn(familyName,qualifier[0],Bytes.toBytes("zs"));
        put.addColumn(familyName,qualifier[1],Bytes.toBytes("19"));
        put.addColumn(familyName,qualifier[2],Bytes.toBytes("M"));
        puts.add(put);
        table.put(puts);

        //获取table对象、delete对象，删除表中数据
        Delete delete = new Delete(Bytes.toBytes("zs"));
        table.delete(delete);

        //关掉不用的对象连接
        table.close();
        admin.close();

    }

    public static void main(String[] args) {

        init();
    }
}
