:navigation_depth: -1

****************
Subduce Overview
****************

Syntax
======

Example
-------

.. code-block:: scheme

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

Indentation and multiline expressions
-------------------------------------

Tab-based indentation only. Spaces are completely ignored in indentation
Function bodies must be indented 1 level from their header (header is def (fun arg1 arg2):). Headers must be on one line
Expressions can be multiline, and indentation within expressions is ignored.

Examples of valid indentation:

.. code-block:: scheme

    def (put-in-list x):
        ans = [
            x
        ]
        return ans
    
    def (put-in-list x):
        ans = [
        x
        ]
        return ans
    
    def (double x):
    ans = (+
            x
            x)
    return ans
    
    seven = (+
                1
                (*
                    2
                    3))
    
    def (put-in-list x):
        ans = [
    x
        ]
        return ans
    
    def (put-in-list x):
        ans = [
            x
    ]
        return ans
    
    seven = (+
                1
                (*
            2
                3))

Examples of invalid indentation:

.. code-block:: scheme

    def (put-in-list x):
        ans = [x]
            return ans # this shouldn't be indented
    
    def (put-in-list x):
    ans = [x] # this should be indented
        return ans

variable naming
---------------
Variable names can include any characters except for:

``#[]()"=:``

whitespace (space, tab, newline, return, etc)

Variables cannot have the same name as a keyword:``def, return, print, true, false``

Variables cannot start with the characters ``-.0123456789``

Built-ins are treated as variables, so they cannot be overridden unless as arguments to a function. Ex:

.. code-block:: scheme

    def (use-custom-add + a b):
        return (+ a b)
    def (double-add a b):
        return (+ (+ a b) (+ a b))
    print (use-custom-add double-add 2 4) # prints 12, not 6

Types
=====
boolean
-------
``true`` or ``false``

number
------
Integers and floating point numbers. Ex: ``234, -1.2, -.3, 4.``

These will end up as python floats or integers, so they have the same behavior and properties

string
------
No multiline strings. Strings support escaping characters.

Ex:

.. code-block:: scheme

    "hello"
    "backslash\\"
    "\"quotes\""
    "newline\n"

function
--------
Use ``lam`` or ``def`` syntax:

.. code-block:: scheme

    add1 = (lam (x) (+ x 1))

    def (add1 x):
        return (+ x 1)

The two functions above are equivalent

list
----
Constructed using ``[]`` or ``cons``.

Ex:

.. code-block:: scheme

    [1 2 true "lisp"]
    (cons 1 (cons 2 (cons true (cons "lisp" empty))))
    empty

The first two lists are equivalent. ``[]`` syntax is syntactic sugar for ``cons``
