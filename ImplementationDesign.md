# order
1. source (file or code string)
2. lexer
3. parser
4. interpreter
# example
## text
```
name = "Yoshikage Kira"
age = 33
stands = ["Killer Queen" "Sheer Heart Attack" "Bites the Dust"]

def (greeting name age):
  ans = (string+ "My name is " name ". I am " age " years old.")
  return ans

print (greeting name age)
```
## lexer
```json
[
    {
        "type": "identifier",
        "name": "name",
        "line number": 1,
        "start position": 1,
        "end position": 5
    },
    {
        "type": "assign",
        "line number": 1,
        "start position": 6,
        "end position": 7
    },
    {
        "type": "string",
        "value": "Yoshikage Kira",
        "line number": 1,
        "start position": 8,
        "end position": 24
    },
    {
        "type": "identifier",
        "name": "age",
        "line number": 2,
        "start position": 1,
        "end position": 4
    },
    ...
]
```
