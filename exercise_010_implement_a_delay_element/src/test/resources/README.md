implement_a_delay_element

## This phase implements a core streams element - the DelayLine

A `DelayLine` is a `Flow` element that has two input `in`0 and `in`1 and two outputs `out`0 and `out`1

Output `out`0 is input stream `in`0 delayed by a specified number of samples (`delay`)
Output `out`1 is the sum of `in`1 and `out`0 multiplied by a specified value (`scaleFactor`)

An FIR filter can be constructed trivially by chaining `DelayLine`s