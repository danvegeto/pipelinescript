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

###var_bool

PipelineScript
```
bool x = true
print x
```

Java
```java
boolean x = true;
System.out.println(x);
```

Output
```
true
```

###var_array
(not yet implemented)

PipelineScript
```
num[] x = [1, 2, 3]
print x
```

Java
```java
double[] x = new double[]{1, 2, 3};
System.out.println(x);
```

Output
```
1.0	2.0	3.0
```

###var_table
(not yet implemented)

PipelineScript
```
table x = [[1, 2], [3, 4]]
print x
```

Java
```java
Table x = new Table(new double[][]{{1, 2}, {3, 4}});
System.out.println(x);
```

Output
```
1.0	2.0
3.0	4.0
```

###var_graph
(not yet implemented)

PipelineScript
```
graph x = 3[[1, 2], [1, 3], [2, 3]]
print x
```

Java
```java
Graph x = new Graph(3, new int[][]{{1, 2}, {3, 4}});
System.out.println(x);
```

Output
```
3
1	2
3	4
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


###shell_args

PipelineScript
```
text x = $0
text y = $1
print x
print y
```

Java
```java
public static void main(String[] args)
{
  String x = args[0];
  String y = args[1];
  System.out.println(x);
  System.out.println(y);
}
```
Note: This is not a separate function, it is the main method that contains the whole pipeline. `args` has to match.

Output
```
> ./pls.sh tests/shell_args.pls foobar
tests/shell_args.pls
foobar
```
Note: The first argument will be the name of the pipeline file, like in Shell scripts.

