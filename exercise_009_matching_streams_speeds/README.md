matching_stream_speeds

## Matching Stream Speeds

In this step, we will start from a base stream that outputs numbers
between 0.02d and 0.5d at a specified delay between samples.

This delay will be in the order of 300ms.

What we want to end-up with is a stream that will repeat the
last value of this base stream as long as there's demand and
switching to a new value when the base stream emits a new value.
