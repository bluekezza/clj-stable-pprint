# clj-stable-pprint

An implementation of pprint which provides stable, deterministic string representation of edn data structures.

Map and Set entries which typically ignore entry order are sorted, thereby allowing easier comparison of similiar data structures using diff tools such as [Meld](http://meldmerge.org/).

Inspired by the JSON implementation:  [json-stable-stringify](https://github.com/substack/json-stable-stringify)

## Installation

```clojure
:dependencies [[clj-stable-pprint "0.0.1-SNAPSHOT"]]
```

## Usage

```clojure
(:require [clj-stable-pprint.core :as spp])
```

### API

#### stabilize

spp/stabilize is the core function which traverses the edn data structure sorting maps and sets.

```
user> (spp/stabilize {:c 8, :b [{:z 6, :y5 ,:x 4}, 7], :a 3})
{:a 3, :b [{:x 4, :y 5, :z 6}, 7], :c 8}
```

#### pprint

spp/pprint is a handy alias for: `(clojure.pprint/pprint (spp/stabilize data))`

```
user> (spp/pprint {:c 8, :b [{:z 6, :y 5 ,:x 4}, 7], :a 3})
{:a 3, :b [{:x 4, :y 5, :z 6} 7], :c 8}
```

### Examples

See clj-stable-pprint.core-test for further examples.
