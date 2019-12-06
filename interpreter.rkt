;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname interpreter) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(define-struct func [names body env])
(define-struct builtin-func [f])
; a Function is one of
; - (make-func [List-of Symbol] Expression Environment]
; - (make-builtin-func [[List-of Value] -> Value])
; builtin-func is a function written in java/racket/python/whatever
; argument names, function body expression, and scope environment
; might want to add a signature field to these or something

; a Value is one of
; - Number
; - String
; - Boolean
; - Function

(define-struct assignment [name exp])
; a Variable-Assignment is a (make-assignment Symbol Expression)
; non-recursive assignment exp can't reference name

(define-struct func-definition [name argnames body])
; a Function-Definition is a (make-func-definition Symbol [List-of Symbol] Expression)
; recursive assignment (f can reference name)

(define-struct func-call [f args])
; a Function-Call is a (make-func-call Expression [List-of Expression])

; A Sequence is a [List-of Expression]
; everything before the last element should be some kind of assignment or a print.
; no Values or anything like that until the last element.
; check this at parse-time or just make bogus functions for them

; An Expression is one of
; - Value
; - Symbol
; - Variable-Assignment
; - Function-Definition
; - Function-Call
; - Sequence

(define-struct pair [left right])
; An Environment is a [List-of (make-pair Symbol Value)]

(define (y-combinator func)
  ((λ (x) (f (x x))) (λ (x) (f (x x)))))

(define ((eval env) exp)
  (cond
    [(or (number? exp)
         (string? exp)
         (boolean? exp))
     exp]; atomics
    [(symbol? exp) (lookup env exp)]
    [(assignment? exp)
     (local [(define name (assignment-name exp))
             (define val-exp ((eval env) (assignment-exp exp)))]
       (λ (body-exp) (apply (make-func (list name) body-exp env) val-exp)))]
    ; x = 2; x+1 -> ((λ (x) x+1) 2) left left lambda
    [(func-definition? exp)
     (make-func (func-definition-argnames exp) (func-definition-body exp) env-with-this-func-in-it)]
    ; get rid of env-with-this-func-in-it
    ; this is something you need to get recursion, but you can't have mutually recursive data
    ; try doing something with the y combinator
    [(func-call? exp)
     (local [(define f (func-call-f exp))
             (define args (func-call-args exp))]
       (apply f args))]
    ; literally just call apply
    [(sequence? exp)
     (foldr (λ (statement rest-body) (if (false? rest-body)
                                         statement; we're on the last exp in seq
                                         ; don't call this on rest
                                         (statement rest-body)
                                         ; we are an assignment/print
                                         ; therefore we are a function
                                         ; therefore we should call on the rest
                                         ))
            #false
            exp)]
    ; sequence is a list of expressions where the last one is to be evaluated to a value
    ; and the other ones are just variable assignments or prints
    ; therefore all but the last should be functions
    ; therefore compose it like this:
    ; (seq[0] (seq[1] (seq[2] (...(seq[-3] (seq[-2] seq[-1]))...))))
    ))

; lookup: env -> [Symbol -> Value]
; finds varname in env and returns its value
; checks in order such that the most recent definition is used
(define ((lookup env) varname)
  (local [(define ans (foldl (λ (association rest-ans) (if (and (false? rest-ans) (symbol=? (pair-left association) varname))
                                                           (pair-right association)
                                                           (rest-ans)))
                             #false
                             env))]
    (if (false? ans)
        (error "variable referenced before assignment")
        ans)))
                                                        

(define (apply f args)
  (cond [(func? f)
         (local [(define argnames (func-names f))
                 (define body-exp (func-body f))
                 (define old-env (func-env f))
                 (define new-env (add-to-env old-env argnames args))]
           ((eval new-env) body))]
        [(builtin-func? f) (f args)]))

; add-to-env: Environment [List-of Symbol] [List-of Value]
; add the given name-value associations to the environment
; expects names and values to be same length
(define (add-to-env env names values)
  (foldr (λ (name value rest-env) (cons (make-pair name value) rest-env))
         env
         names
         values))
