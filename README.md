# jooq-flyway-typesafe-migration

Proof of concept of DB migrations in a type safe, history preserving way using [jOOQ](https://www.jooq.org/) and [Flyway](https://flywaydb.org/).

## Concepts

On each jOOQ based migration, if the database structure changes, a matching versioned model is created. Then the next jOOQ based migration can use this model to be type safe. 
When all the migrations are over, a non versioned model is created for the business code.

## Architecture

3 main modules are at play:
* the migrator module contains all the migrations scripts and versioned models,
* the app module contains the business logic and a non versioned model,
* the runner, used when the application starts, launches first the migrations then the app.

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
  * you can't change the model and use the new one in the same migration: you need 2 migrations for this use case.
* base your migration on the model of highest version, 

## Just a Proof Of Concept

The current code isn't really production ready. 

Things to improve:
* add some testing
* add assertions at various locations
* ideally even check that only one model is used (or even that the right model is ^^)
* have a better way of handling the dialect in JooqMigration
* improve logging
* make sure of possibility to cleanup migrations 
  * over time there may be a lot of migrations
  * ideally I would like to be able to compact them or at least delete the oldest

## Thanks !

Thanks [jOOQ](https://www.jooq.org/) and [Flyway](https://flywaydb.org/) for existing and being open source!

## Contributors

[Lukas Eder](https://twitter.com/lukaseder) and [Joseph Pachod](https://twitter.com/joeclueless)
