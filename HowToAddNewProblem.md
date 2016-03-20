# Introduction #

In order to add new problem to caparf one needs to define its input and output and implements verifier which should check whether the given output is valid for the given input.

# Details #

## Choose the problem abbreviation ##

The problem abbreviation should consist of 3 parts:
  1. Problem name, i.e. "`o`" for orthogonal, "`s`" for strip, "`k`" for knapsack and etc.
  1. Problem type, i.e. "`pp`" for packing problem or "`cp`" for cutting problem
  1. Problem dimension, i.e. "`1d`", "`2d`", "`3d`" or "`nd`"

## Add items which are packed/cut ##

Define items which are packed/cut in the problem and the corresponding placement. Note, that items you need can be already implemented (although the problem is not yet added). It can happen because items and its placements are shared between all problems. Code location for items and its placements is [src/com/googlecode/caparf/framework/items](http://code.google.com/p/caparf/source/browse/#svn/trunk/src/com/googlecode/caparf/framework/items).

For the reference implementation look at [Rectangle](http://code.google.com/p/caparf/source/browse/trunk/src/com/googlecode/caparf/framework/items/Rectangle.java) and [RectanglePlacement](http://code.google.com/p/caparf/source/browse/trunk/src/com/googlecode/caparf/framework/items/RectanglePlacement.java) classes.

## Implement `Input`, `Output` and `OutputVerifier` for the problem ##

Finally, `Input`, `Output` and `OutputVerifier` for the problem can be implemented. These new classes should be located under `src/com/googlecode/caparf/framework/<problem-abbreviation>`.

Extend [BaseInput](http://code.google.com/p/caparf/source/browse/trunk/src/com/googlecode/caparf/framework/base/BaseInput.java) in order to implement `Input`. Note, that at least 2 constructors should be defined: the first should take `List<Item>` and the second should take `Item[]`. For the reference look at [spp2d Input](http://code.google.com/p/caparf/source/browse/trunk/src/com/googlecode/caparf/framework/spp2d/Input.java).

Extend [BaseOutput](http://code.google.com/p/caparf/source/browse/trunk/src/com/googlecode/caparf/framework/base/BaseOutput.java) in order to implement `Output`. Similar to `Input`, there should be at least 2 defined constructors: the first should take `List<ItemPlacement>` and the second should take `ItemPlacement[]`. Also, `calculateObjectiveFunction` should be implemented. For decision problems (like orthogonal packing) it returns either 0 or 1. For the reference look at [spp2d Output](http://code.google.com/p/caparf/source/browse/trunk/src/com/googlecode/caparf/framework/spp2d/Output.java).

_Note:_ Both `Input` and `Output` should care about transformation if their behavior is different to just storing list of items and item placements. For example, transformation should be applied for oop2d output if there is no solution. In spp2d output transform should call transform method of stored input in order to keep it consistent with output.

Extend [BaseOutputVerifier](http://code.google.com/p/caparf/source/browse/trunk/src/com/googlecode/caparf/framework/base/BaseOutputVerifier.java) in order to implement `OutputVerifier`. There is no tricks here except that in `verify` method one should have no assumptions about output validness, i.e. output should be checked carefully even for ridiculous mistakes.

For more information please refer to Java Docs of [BaseInput](http://code.google.com/p/caparf/source/browse/trunk/src/com/googlecode/caparf/framework/base/BaseInput.java), [BaseOutput](http://code.google.com/p/caparf/source/browse/trunk/src/com/googlecode/caparf/framework/base/BaseOutput.java) and [BaseOutputVerifier](http://code.google.com/p/caparf/source/browse/trunk/src/com/googlecode/caparf/framework/base/BaseOutputVerifier.java). In case of any questions send it to caparf-discuss@googlegroups.com.