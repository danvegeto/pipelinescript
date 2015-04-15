// Fibonacci Series
num prev = 0
num aux = 0
loop(num fib = 1, fib < 500,) {
    aux = fib
    fib = fib + prev
    prev = aux
    print fib
}
