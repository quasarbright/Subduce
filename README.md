# Subduce
A programming language based on python and lisp
* TOC
{:toc}
# Syntax
## Example:
```scheme
# returns the product - the sum of two numbers
def (func a1 a2):
  sum = (+ a1 a2)
  prod = (* a1 a2)
  print (+ "prod " (number->string prod))
  return (- prod sum)
n = 2
(func n 3) # prints 6 and outputs 1
arr = [1 2 3 4]
doubled = (map (lam (e) (* e 2)) arr)
```
## Indentation and multi-line expressions
**Tab-based indentation only.** Spaces are completely ignored in indentation  
Function bodies must be indented 1 level from their header.  
Expressions can be multiline, and indentation within expressions is ignored, as long as they don't go below their starting indentation. For example:  
```scheme
# ok
def (put-in-list x):
  ans = [
    x
  ]
  return ans
```
```scheme
# ok
def (put-in-list x):
  ans = [
  x
  ]
  return ans
```
```scheme
# ok
def (double x):
ans = (+
        x
        x)
return ans
```
```scheme
# ok
seven = (+
          1
          (*
            2
            3))
```
```scheme
# NOT ok
def (put-in-list x):
  ans = [
x
  ]
  return ans
```
```scheme
# NOT ok
def (put-in-list x):
  ans = [
    x
]
  return ans
```
```scheme
# NOT ok
seven = (+
      1
      (*
    2 # the two went below the indentation of the (* 
      3))
```
## Variable Naming
Variable names can include any characters except for:    
`#[]()"=:`  
whitespace (space, tab, newline, return, etc)  
Variables cannot have the same name as a keyword: `def`, `return`, `true`, `false`  
Variables cannot start with the characters `-.0123456789`  
Built-ins are treated as variables, so they cannot be overridden unless as arguments to a function. Ex:  
```scheme
def (use-custom-add + a b):
  return (+ a b)
def (double-add a b):
  return (+ (+ a b) (+ a b))
print (use-custom-add double-add 2 4) # prints 12, not 6
```
# Types
## boolean
`true` or `false`
## number
integers and floating point numbers  
ex: `234` `-1.2` `-.3` `4.`  
  
These will end up as python floats or integers, so they have the same properties
## string
ex: `"hello"`
## function
ex: `(lam (x) (+ x 1))`  
```scheme
# same as the lam
def (add1 x):
  return (+ x 1)
```
## list
ex:  
```scheme
[1 2 true "lisp"]
(cons 1 (cons 2 (cons true (cons "lisp" (cons empty)))))
empty
```
`[...]` is syntactic sugar for `cons`. The first two examples are equivalent.
# Built-in Functions
## if
`(if condition:boolean expr1:any expr2:any)`
`expr1` is returned if condition evaluates to `true`. `expr2` is returned if condition evaluates to `false`. If `condition` is not a boolean, an error is thrown  
Example:  
```scheme
# absolute value
def (abs num):
  return (if (< num 0) (* -1 num) num)
```
# Scope
Any variables declared in a local scope can override the name of, but can not change the contents of a variable in the outer scope.  
```scheme
name = "Mike"
def (greet name):
  print name
  return (+ "Hello, " name)
greeting = (greet "Joey") # prints "Joey" 
print greeting # prints "Hello, Joey"
print name # prints "Mike"
```
```scheme
sum = 3
def (add a b):
  sum = (+ a b)
  return sum
print sum # prints 3
print (add 5 6) # prints 11
print sum # prints 3
```  
A function's arguments and locals are in the same scope, so you can't override the name of an argument
```scheme
def (not-mult a b):
  a = 9 # error: variable is already defined in local scope
  return (* a b)
print (mult 2 3)
```  
However, you can if you go into a scope inside of the function:  
```scheme
def (foo f):
  def (bar b):
    f = 2
    return f
  
```  
Variables do not "bubble up" out of the scope they were defined in  
```scheme
def (add a b):
  sum = (+ a b)
  return sum
print (add 1 2) # prints 3
print sum # error: undefined
```  
Variables in independent scopes can share the same name. Ex:  
```scheme
def (square num):
  ans = (* num num)
  return ans

def (double num):
  ans = (+ num num)
  return ans
```

