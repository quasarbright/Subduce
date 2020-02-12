:navigation_depth: -1

****************
Subduce Overview
****************

Features
========
* basic primitives (numbers, strings, booleans)
* lists
* printing
* structs
* mutual recursion
* first-class functions
    * closures and nested functions
* interactive mode
    * run a program in interactive mode to use definitions
* dynamic typing
* short-circuiting
* lazy if evaluation, strict otherwise
* inclusive character set for identifiers

Syntax
======

Example
-------


.. code-block:: scheme

    # returns the product - the sum of two numbers
    def (func a1 a2) {
        # prefix operators
        sum = (+ a1 a2)
        prod = (* a1 a2)
        print (+ "prod " (number->string prod))
        return (- prod sum)
    }
    # variable assignment
    n = 2
    # function application
    (func n 3) # prints 6 and outputs 1
    # lists
    arr = [1 2 3 4]
    doubled = (map (lam (e) (* e 2)) arr)
    # structs
    (define-struct posn (x y))
    p = (make-posn 3 4)


* Whitespace is irrelevant except for separating identifiers.
* ``print`` and ``return`` have special syntax

variable naming
---------------
Variable names can include any characters except for:

``#[]()"=:``

whitespace (space, tab, newline, return, etc)

Variables cannot have the same name as a keyword such as``def, return, print, true, false``

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


.. code-block:: 

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


struct
------
Defined with ``define-struct``.

Ex:


.. code-block:: scheme

    (define-struct posn (x y))

    p1 = (make-posn 1 2)
    p2 = (make-posn 3 4)

    def (add-posn p1 p2) {
        x = (+ (posn-x p1) (posn-x p2))
        y = (+ (posn-y p1) (posn-y p2))
        (make-posn x y)
    }

    p3 = (add-posn p1 p2)
    (posn? p3) # true


Defining a struct creates a constructor, 
field accessors, and a predicate function