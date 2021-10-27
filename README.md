NAVIXPATH
========

A Library for Extracting Data From XML
----------------

# Overview

This library was the "navicore data" package as the "data" utility of the
"navimatrix" project (thanks Henry) back in 2004.

After many years I'm working with XML again and remember this lib was my
favorite way to work with XML.

# Usage

```java
InputStream res = this.getClass().getResourceAsStream("/test/test2.xml");
Assert.assertNotNull(res);
DataObject data = XmlCoder.decode(new InputStreamReader(res));

Assert.assertNotNull("xpath returned null", data.get("/message/subject"));

Assert.assertEquals("wrong root node name",
                    "stream",
                    data.getDataObject("/").getType().getName());

Assert.assertEquals("wrong . node",
                    data,
                    data.getDataObject("."));

Assert.assertEquals("wrong . node",
                    data,
                    data.getDataObject("/message/.."));

Assert.assertEquals("wrong xpath return data", "Hi", data.getString("/message/subject"));

Assert.assertEquals("position() broken",
                    "filethree.txt",
                    data.getString("/message/attatchments/filename[position() = 3]"));

Assert.assertEquals("reletive path broken",
                    "filethree.txt",
                    data.getDataObject("./message/attatchments").getString("./filename[position() = 3]"));

Assert.assertEquals("attribute path broken",
                    1001,
                    data.getLong("/message/attatchments/filename[position() = 2]/@age"));

Assert.assertEquals("attribute path broken",
                    "ed@edsweeney.net",
                    data.getString("/message/@from"));

DataObject leaf = data.getDataObject("./message/attatchments/filename[position() = 1]");
Assert.assertEquals("attribute path broken",
                    "ed@edsweeney.net",
                    leaf.getString("/message/@from"));

leaf = data.getDataObject("message/attatchments/filename[position() = 1]");
Assert.assertEquals("attribute path broken",
                    "ed@edsweeney.net",
                    leaf.getString("/message/@from"));

Assert.assertEquals("attribute path broken",
                    "filethree.txt",
                    data.getString("/message/attatchments/filename[@age = 2]"));

Assert.assertEquals("attribute path broken",
                    "filethree.txt",
                    data.getString("/message[@to = 'ed@blazebot.com']/attatchments/filename[@age = 2]"));

Assert.assertEquals("attribute path broken",
                    "filetwo.txt",
                    data.getString("//filename[@age = 1001]"));

Assert.assertNull("attribute path broken",
                  data.getString("//filename[@age = 9999]"));

List list = data.getList("//filename");
Assert.assertEquals("wrong count of filename(s)", 3, list.size());

Assert.assertEquals("count() broken", 3,
                    (long)data.getDouble("count(//filename)"));

Assert.assertEquals("count() broken",
                    1,
                    (long)data.getDouble("count(//subject)"));
```

# Repositories

## Maven Central

https://mvnrepository.com/artifact/tech.navicore/navixpath

### Maven

```
<dependency>
    <groupId>tech.navicore</groupId>
    <artifactId>navipxath</artifactId>
    <version>1.0.0</version>
</dependency>
```

### SBT
```
libraryDependencies += "tech.navicore" %% "navixpath" % "1.0.0"
```

# Build

`mvn clean package`

# License

Several BSD-licensed libs were embedded in the src tree - orig BSD licenses are
still in each embedded file. 

Unless there is a notice in the file stating otherwise, all other code is MIT
licensed.
