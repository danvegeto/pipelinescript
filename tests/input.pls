//Read and recursion

num combination(num a num b) {
    num factorial(num a) { if (a == 1) { return 1 } return a * factorial(a - 1) }
    return factorial(a) / factorial(b) / factorial(a-b)
}

print "Enter a and b, one at a time"
print combination((num) read (num) read)
