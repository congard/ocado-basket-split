# Ocado Basket Split

Ocado coding task for intern position.

**Java Version**: 17
<br>**Tested with**:
```
openjdk 17.0.9 2023-10-17
OpenJDK Runtime Environment (Red_Hat-17.0.9.0.9-4) (build 17.0.9+9)
OpenJDK 64-Bit Server VM (Red_Hat-17.0.9.0.9-4) (build 17.0.9+9, mixed mode, sharing)
```

## Description

The task is to split user's basket in such a way that:

1. Most items can be delivered using a single delivery method;
2. The number of delivery methods (groups of items that can be delivered using different shipping methods)
   should be minimal.

## Solution

In order to solve this problem we should notice, that one item can belong to multiple groups. That is,
is the item `Qux` simultaneously belongs to the groups `Foo` and `Bar`, it can be shipped using only
one delivery method (either `Foo` or `Bar`).

So, to satisfy both requirements we can construct the following algorithm:

1. Build the map of candidate shipping methods: `DeliveryMethodId` is a key, the list of items that can
   be shipped using this method is a value;
2. Generate an `i`-element combination of delivery method candidates ids (order is not important,
   repetition is not allowed). There are `2^i` such combinations that can be generated using bitwise operations,
   but to improve readability and simplify code, the library was used;
3. Check, if the corresponding groups contain all the items
   1. If so, check if it is the current best solution, and if it is, remember the results (solution is considered
      the best if its largest group contains more items than the previous one)
   2. Otherwise, continue
4. If the solution has been already found, both requirements were satisfied. Create the resulting map by
   subtracting `i`th set items from the `i+1`th set items. Note: the largest group is located at the index `0`. 
5. Otherwise, continue

Approx. complexity: `O(2^(f(n)))`, where `n` is the total number of available delivery methods,
`f` - some polynomial function.

## Dependencies

The project has the following dependencies:

1. [`org.json`](https://mvnrepository.com/artifact/org.json/json) - JSON parser
2. [`combinatoricslib3`](https://mvnrepository.com/artifact/com.github.dpaukov/combinatoricslib3) -
   combinations generator
3. [`org.jetbrains.annotations`](https://mvnrepository.com/artifact/org.jetbrains/annotations) -
   to improve code readability (and improve null safety)

## Building

In order to build `jar` file (with all included dependencies, so-called `fat-jar` or `uber-jar`)
execute the following command:

```bash
./gradlew jar
```

The resulting `jar` file will appear in `build/libs` directory.

## Running

Additionally, the app can also be launched from the console:

```bash
./gradlew run --args="<path to the config file> <path to the basket file>"
```

or (in case of already built `jar`):

```bash
java -jar build/libs/ocado-basket-1.0-SNAPSHOT.jar
```

The result will be print as JSON.

## Tests

Some tests were written in order to test the main functionality. JUnit 5 framework was used.
