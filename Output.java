package com.pipelinescript;

public class Pipeline
{

public static void main(String[] args) throws Exception
{
double prev = 0;
 double aux = 0;
 double fib = 1; for(; (((fib < 500) ? 1 : 0)) != 0;) { aux = fib;
 fib = fib + prev;
 prev = aux;
 System.out.println(fib);}
}

}
