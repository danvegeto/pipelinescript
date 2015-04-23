#PipelineScript Examples

###print

PipelineScript
```
print "hello world"
```

Java
```java
System.out.println("hello world");
```

Output
```
hello world
```

###var_text

PipelineScript
```
text x = "foobar"
print x
```

Java
```java
String x = "foobar";
System.out.println(x);
```

Output
```
foobar
```

###var_num

PipelineScript
```
num x = 42
print x
```

Java
```java
double x = 42;
System.out.println(x);
```

Output
```
42.0
```

###arith_add

PipelineScript
```
num x = 4
num y = 2
print x + y
```

Java
```java
double x = 4;
double y = 2;
System.out.println(x + y);
```

Output
```
6.0
```

###arith_sub

PipelineScript
```
num x = 4
num y = 2
print x + y
```

Java
```java
double x = 4;
double y = 2;
System.out.println(x - y);
```

Output
```
2.0
```

###arith_mult

PipelineScript
```
num x = 4
num y = 2
print x * y
```

Java
```java
double x = 4;
double y = 2;
System.out.println(x * y);
```

Output
```
8.0
```

###arith_div

PipelineScript
```
num x = 4
num y = 2
print x / y
```

Java
```java
double x = 4;
double y = 2;
System.out.println(x / y);
```

Output
```
2.0
```

###text_concat

PipelineScript
```
text x = "foo"
text y = "bar"
print x + " " + y
```

Java
```java
String x = "foo";
String y = "bar";
System.out.println(x + " " + y);
```

Output
```
foo bar
```

