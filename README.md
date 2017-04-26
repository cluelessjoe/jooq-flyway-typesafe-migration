# jooq-flyway-typesafe-migration

Proof of concept of DB migrations in a type safe, history preserving way using [jOOQ](https://www.jooq.org/) and [Flyway](https://flywaydb.org/).

## Presentation

3 main modules are at play:
* the migrator module contains all the migrations scripts and versioned models,
* the app module contains the business logic and a non versioned model,
* the runner, used at application starting, launching first the migrations then the app.

There's no dependency between the migration and app modules.

For each migration which changes the database structure:
* a versioned model is created in the migrator module,
* the model in the app module is recreated.

As such:
* each new migration is type safe
  * written with [jOOQ](https://www.jooq.org/), so SQL is checked at compile time
  * it uses the latest versioned model, so tables, columns and the like are also type safe
* migrations history is preserved: the previous database models are present, handy when something doesn't go as planned,
* the business logic doesn't have the versioned models in its classpath, avoiding wrong usage,
* the business logic relies only and always on the up to date model.
 
## Migrations details

The migrations are defined in Java through jOOQ, in the migrator module main sources, in the package org.jooq.example.migrator.migrations.

To add a new migration script:
* add a new Java class extending JooqMigration with an incremented version number in the migrations folder,
* base your migration on the model of highest version, 
* if the database structure changes, call ```LatestModelGenerator.main()``` once the migration script is done. This will:
  * drop (if present) and create the latest model in the migrator module, prefixed by its migration number,
  * drop (if present) and create the model in the app module, ensure the business code compiles against the latest model.

As seen above, one migration script may have up to one model but not necessarily. 
Indeed, if the database structure changes in a migration, one needs to regenerate the model before being able to use it in a typesafe way, and thus create a migration script only for using the new model.
  
For example, if we are at migration 20 and model version 20, adding a new table and inserting data in it implies:
* a migration script creating the table, with version 21 and based on model version 20,
* generating the new model, with version 21,
* a migration script inserting data in the table, with version 22, based on model version 21.

## Just a Proof Of Concept

The current code isn't really production ready. 

Things to improve:
* add some testing
* add assertions at various locations
* add some prefix to each class generated in the migrator versioned models 
  * to make it clearer which version is using a migration
    * I actually don't know if doable with jOOQ
  * ideally even check that only one model is used (or even that the right model is ^^)
    * it's clearly some day dreaming ^
* have a better way of handling the dialect in JooqMigration
* improve logging
* run LatestModelGenerator automatically [issue #3](https://github.com/cluelessjoe/jooq-flyway-typesafe-migration/issues/3)
* make sure of possibility to cleanup migrations 
  * over time there may be a lot of migrations
  * ideally I would like to be able to compact them or at least delete the oldest

## Thanks !

Thanks [jOOQ](https://www.jooq.org/) and [Flyway](https://flywaydb.org/) for being open source!

## Contributors

[Lukas Eder](https://twitter.com/lukaseder) and [Joseph Pachod](https://twitter.com/joeclueless)