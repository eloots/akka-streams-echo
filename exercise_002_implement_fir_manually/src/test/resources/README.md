implement_fir_manually

## Construction of a simple FIR based echo generator using DelayLine

*Manual* chaining of `DelayLine`s

Note the introduction of two additional elements:

- `FirInitial`: takes a stream of `Double` elements and outputs a stream of (`Double`, `Double`).
  Each element in the input is duplicated in the output tuples elements

- `FirSelectOut`: takes a stream of (`Double`, `Double`) elements and outputs the
  second element (`Double`) of that tuple
