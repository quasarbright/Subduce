# pipeline  
1. source (file or code string)
2. lexer
3. parser
4. interpreter
# example  
## source code  
```scheme
def (add1 x):
  return (+ x 1)
```
## lexer  
```
<start file> at 1
<keyword> at 1:1-4. value: def
<start function> at 1:5-6
<identifier> at 1:6-10. value: add1
<identifier> at 1:11-12. value: x
<end function> at 1:12-13
<end signature> at 1:13-14
<newline> at 2
<indent> at 2
<keyword> at 2:2-8. value: return
<start function> at 2:9-10
<identifier> at 2:10-11. value: +
<identifier> at 2:12-13. value: x
<number> at 2:14-15. value: 1
<end function> at 2:15-16
<end file> at 3
```
## parser  
![parse tree example](https://quasarbright.github.io/Subduce/figures/parseTreeExample.PNG)
