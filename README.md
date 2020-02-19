# ECE-612 Assignment 1 - Dining Philosophers
## Requirements

This project is using Clojure a JVM programming language. It needs JVM to run and build tool Lein.

- Install Java [OpenJDK](https://openjdk.java.net/)
- Install Lein [Leiningen](https://leiningen.org/)

## Project Structure

Source files:

    src/ece612_assignment1/dining_philosophers.clj

Test files:

    test/ece612_assignment1/dining_philosophers.clj

## Generated Documetation (Using Codox):

  [Codox](https://github.com/weavejester/codox) is a tool for generating code documentation similar to Javadoc

  Generated document can be found in:

    target/doc/index.html

## Run

To run the code you can use the following command:

    lein run

This might not produce any output since the threads are running in the background.

To be able to see the resutls you can run the test:

    lein test

## Testing

Two test cases are being covered to make sure the code is functioning as expected:
- Testing that we have no deadlocks by running the code for 30 seconds and continuously monitor the number of the BLOCKED threads. The threads BLOCKED should be less than the number of threads, if all of them are blocked then we have a deadlock.
- Testing that the number of forks being used at any point of time not more than four. That makes sure that we always have two philosophers eating at any point of time.

Tests results:

    Ran 1 tests containing 2043722 assertions.
    0 failures, 0 errors.

When you run the test you might get these results mixed up with the output at the end.

Tests outputs:

    fork1 use: 1, fork2 use: 0, fork3 use: 0, fork4 use: 0, fork5 use: 1
    Philosopher-3 is Eating.
    fork1 use: 1, fork2 use: 0, fork3 use: 1, fork4 use: 1, fork5 use: 1

## License

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
