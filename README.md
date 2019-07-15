# Subduce
A programming language based on python and lisp
# syntax
example:
```
# returns the product - the sum of two numbers
def {func a1 a2}:
  sum = (+ a1 a2)
  prod = (* a1 a2)
  print (+ "prod " (number->string prod))
  return (- prod sum)
n = 2
(func n 3) # prints 6 and outputs 1
arr = [1 2 3 4]
doubled = (map (lam (e) (* e 2)) arr)
```
