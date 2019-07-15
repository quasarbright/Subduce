# Subduce
A programming language based on python and lisp
* TOC
{:toc}
# Syntax
## Example:
```
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
## Variable Naming
Variable names can include any characters except for:    
`\#[]()"=:`  
whitespace (space, tab, newline, return, etc)  
Variables cannot have the same name as a keyword: `print`, `return`  
Built-ins are treated as variables, so they cannot be overridden unless as arguments to a function. Ex:  
```
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
## list
ex: `[1 2 true "lisp"]` `(cons 1 (cons 2 (cons true (cons "lisp" (cons empty)))))` `empty`  
  
`[...]` is syntactic sugar for `cons`. The first two examples are equivalent
# Built-in Functions
## if
`(if condition:boolean expr1:any expr2:any)`
`expr1` is returned if condition evaluates to `true`. `expr2` is returned if condition evaluates to `false`. If `condition` is not a boolean, an error is thrown  
Example:  
```
# absolute value
def (abs num):
  return (if (< num 0) (* -1 num) num)
```
# Scope
Although arguments can share names with global variables, you cannot create local variables with the same name as a global variable (or a variable outside of current scope). Ex:    
```
name = "Mike"
def (greet name):
  print name
  return (+ "Hello, " name)
print (greet "Joey") # prints "Joey" then "Hello, Joey"
print name # prints "Mike"
```
```
sum = 3
def (add a b):
  sum = (+ a b) # will throw an error
  return sum
```  
Variables in independent scopes can share the same name. Ex:  
```
def (square num):
  ans = (* num num)
  return ans

def (double num):
  ans = (+ num num)
  return ans
```
