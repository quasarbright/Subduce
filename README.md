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
Variables cannot have the same name as a keyword: `print`, `return`, `true`, `false`, `empty`
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
