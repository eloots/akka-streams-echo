implement_a_delay_element

## This phase implements a core streams element - the DelayLine

A `DelayLine` is a `Flow` element that has two input `in0` and `in1` and two outputs `out0` and `out1`

Output `out0` is input stream `in0` delayed by a specified number of samples (`delay`)
Output `out1` is the sum of `in1` and `out0` multiplied by a specified value (`scalaFactor`)

An FIR filter can be constructed trivially by chaining `DelayLine`s