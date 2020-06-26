# Introduction to chocolate factory

# chocolate-factory

It has two operations:

- adding a new wall on the line.
- querying the number of hard chocolate units the factory line has at the moment.

The -main is on src/core.clj and tests are on test/core_test.clj

To calculate the chocolate units the function compute works as follow:

- pre-compute the highest wall on the left (m-left) for each wall
- pre-compute the highest wall on the right (m-right) for each wall
- for each wall apply the function: units = min(max-left, max-right) - wall-height
- then sum all the units together

The adding operation updates the walls ref with the new wall height

## Installation

To run the program you must have Clojure and it's dependencies installed. 
For more information on installing Clojure see the documentation on 
https://clojure.org/index

## Build

    $ lein uberjar

## Usage

    $ java -jar /target/uberjar/chocolate-factory-0.1.0-SNAPSHOT-standalone.jar  # Uses port 3000

    or

    $ java -jar /target/uberjar/chocolate-factory-0.1.0-SNAPSHOT-standalone.jar [port-num]

    or

    $ lein run

    then use the API like:

    GET /query         # get chocolate units
    POST /wall/:height # add wall with height

    $ curl localhost:3000/query 
    $ 0
    $ curl -X POST localhost:3000/wall/2
    $ OK
    $ curl -X POST localhost:3000/wall/0
    $ OK
    $ curl -X POST localhost:3000/wall/2
    $ OK
    $ curl localhost:3000/query 
    $ 2
    
## Complexity

For the GET operation, the "compute" function is called passing the current wall list, having the time complexity of O(n) (n is the number of walls) and space complexity of O(n) because it uses support vectors of size n. However, not all calls to GET has this complexity. This was achieved using cache (clojure.core.memoize), then between POST calls the complexity is O(1) time and O(1) space.

The Post has a time complexity of O(1) and space complexity of O(1)

## License

Copyright © 2020 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
