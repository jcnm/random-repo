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


Nota:
Check build.sbt for more details
For runways type per country, some value come "twiste" should we consider **GRAVEL, GRASS / SOD** as different of **GRASS / SOD, GRAVEL** or not ? Same goes for **GRASS/PAD** , **PAD/GRASS** and probably some more.
