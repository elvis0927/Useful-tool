package com.springflute;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Java_hdfs {
    private Configuration conf;
    private FileSystem fs;

    public void initHDFS() throws URISyntaxException, InterruptedException, IOException {
        conf = new Configuration();
        fs = FileSystem.newInstance(new URI("hdfs://hadoop01:9000"), conf, "admin");
        System.out.println(fs.toString());
    }

    public void func() throws IOException {
        fs.copyFromLocalFile(new Path("d:/data/person.txt"),new Path("/usr/data"));
        fs.mkdirs(new Path("/usr/new"));
        fs.rename(new Path(""),new Path(""));
        fs.close();
    }

}
