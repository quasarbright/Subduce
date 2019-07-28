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
<iframe src="https://www.draw.io/?lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&title=Subduce%20Pipeline#R7VrLcpswFP0aL51BwmCzbByn7Uw702kWSVYdGWRQCxIVIsb9%2BkpYBLATl7rFGIcV6Op9z%2BHqSGhkzqPsPUdx8Jl5OBxBw8tG5s0IQmA6pnwoy0ZbgD3ZWnxOPG0rDXfkF9ZGQ1tT4uGkVlAwFgoS140uoxS7omZDnLN1vdiKhfVeY%2BTjPcOdi8J96z3xRLC12oZR2j9g4ge652mRsUTuD5%2BzlOruKKN4mxOhohVdNAmQx9YVk7kYmXPOmNi%2BRdkch8qthcO29W5fyX0eMcdUNKngjL%2FORTD%2FaCy%2B3Afs5htcPizHQMOUiE3hCuxJz%2Bgk4yJgPqMoXJTW63y%2BWDVryFRZ5hNjsTQCafyOhdhomFEqmDQFIgp1Ls6IeFDVryydeqzk3GS65TyxKRJU8E2lkko%2BVvPKanmqqLdiVOiBAFumt%2FNVk3zVj9qUsJS7%2BIDztO8E4j4WB8o5z2jLDwizCMvxyXoch0iQp%2Fo4kCay%2F1yuhFS%2BaFT%2FBmFrQLh1hGedImwPCLeOMDC6hFiP8gmFqe5pBO1Qjvc6rgFv%2F0zVcnIdEorHgV6w3skiQC1YVllAvvn6GW6ft%2FGuLeb4xcYVGuMkhyNv2o6z%2FYZXKXUFYbTagWrvP3aREJ8ikeatHurj8Fx3vpz6d1Hh%2By4H1wER%2BC5GObvWUhapEckkob6urDHDXODsME%2F3aaUrwEJGaV0Fi299XYoUUAiMoCJQbKMlJs56x0T5ZaeR8rfyU2tkpCh6Qzw0J6BjHjq94%2BEJIuJlkxAY50bCYvfaIxaW0XBg4VEsnMBzY6Fp7Lls0P9N9T%2BYNtwAwE73eCYYMG4f40mn2%2Fhp7xaTUtK0Ka09vCKUNJBOPV5VdgX2rOt9HhwOhv8h4DhNF5VOT5VA%2FzZRHIuUtxxvEoEEbqCRexxuptCpi1g4u7K6jjjDj4rjI06B1J8jzivEOE3EgcOvilNgDDvFeDpgfAKMzU4x7t%2FB12n2Ki5SznsrsmHS%2BdlXMYdeEnE4gT2Khdakvle2LLN78Qp7x8MnxAlahhdMlN1wdRZEMXtHFJpGS8wvlyYOOMN44gwi%2BngRPWsookGnNzNh%2F%2B7SnEC7LJm3udxYs7sktXmhSybL2%2BV5XuX2vrn4DQ%3D%3D"></iframe>
