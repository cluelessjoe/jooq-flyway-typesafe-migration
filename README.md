# jooq-flyway-typesafe-migration
How to do DB migration in a typesafe, history preserving with jOOQ and Flyway

## How is it working

The migration module contains all the migrations scripts and models.

When writing a new migration, the latest model definition is used (so the migration is type safe).

Once the new migration is written, ```LatestModelGenerator.main()``` is then called and:
* drop (if present) and create the latest model in the migrator module, prefixed by its migration number
* drop (if present) and create the model in the app module, ensure the business code compiles against the latest model
** the app module is independent from the migrator module: the versioned models aren't available

When deployed, the runner module runs the migration and then launch the app.
 
## Migrations caveat

In one migration, one can't change the Data Definition, for example a table, and then use it directly. It wouldn't be typesafe. It means though that some migrations may not affect the model. In this case running ```LatestModelGenerator.main()``` is pointless. However this means the model version numbers aren't continuous, which is surprising at first. 

## Work in progress

The runner isn't coded yet.
 

