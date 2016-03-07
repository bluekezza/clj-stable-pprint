# Change Log
All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).
This project adheres to [Semantic Versioning](http://semver.org/).

## [Unreleased]

## [0.0.3] - 2016-03-07
### Changed
- Sets that have heterogenous values are stabilized using the custom compare function.

## [0.0.2] - 2016-02-26
### Changed
- Maps that have heterogenous keys can not be compared using clojure.core/compare. So implements a compare that coerces the values to strings if their types mismatch.

## [0.0.1] - 2016-02-25
### Added
- stablilize function
- pprint function

[Unreleased]: https://github.com/bluekezza/clj-stable-pprint/compare/v0.0.3...HEAD
[0.0.3]: https://github.com/bluekezza/clj-stable-pprint/compare/v0.0.2...v0.0.3
[0.0.2]: https://github.com/bluekezza/clj-stable-pprint/compare/v0.0.1...v0.0.2
