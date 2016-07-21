# random-repo

Airports data from http://ourairports.com/data/ (released under public domain)

### Usage

#### Execution

```bash
sbt run
```

#### Test

```bash
sbt test -feature
```
Note: Tests are using real data instead of using mockers and real fixtures

#### API

* CSVReader
* Scala Test Spec


#### Nota:

Check build.sbt for more details

For runways type per country, some value come "twiste" should we consider **GRAVEL, GRASS / SOD** as different of **GRASS / SOD, GRAVEL** or not ?

Same goes for **GRASS/PAD** , **PAD/GRASS** and probably some more.


#### TODO
PB: Requesting Bel thinking to Belgium return one entry, using it iso code BE returns the good entries
Sol:Use 4 letters instead of 3 to guess the country => Belg returns the good entries, so forbiden 3 letter request or return a warning.
