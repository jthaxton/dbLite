# Running DBLite

## Requirements
1. Maven

## Recommended
1. OSX so you can run my setup scripts

## Setup
* To build, you can use `./build.sh`
* Once built, you can run `./quickstart.sh`
* Or you can build and run at the same time using `./start.sh`

## Basic commands
Once the command line opens up, you can start writing commands:

### Schema and Migrations
* `schema:create` makes a blank `schema.json` file and versions itself.
* `schema:migrate:create <migrationName> <pluralTableName>` creates a blank migration file with the table name. You are then free to add columns to the * `fields` JSONObject in the following format: `"fields": {"fieldName": {"type": "JavaType", "null": "false", "indexed": "false"}}`. `type`, `null`, and `indexed` are the only field attributes I plan to support in the near future.
* `schema:drop` could result in a bad state. Only do this if you know what you are doing.
* `schema:migrate` applies each migration to `schema.json` and builds file structures in `.data`.
* `write <tableName> <jsonFormattedParameters>` validates `jsonFormattedParameters` and inserts the hash into the data structure.
* `find <tableName> <id>` returns one record that matches the `id` param
* `findBy <tableName> <jsonFormattedQueryParams>` returns all records that match the `jsonFormattedQueryParams`
* `exit` exits the CLI.


### Where to go from here
The database currently uses an inverse index approach to writes - it stores tableName -> attribute -> value.json and each value.json file has one attribute `id` that maps to an array of ids for all records sharing the attribute value. Every table has a mandatory id field that is structured similarly, except its contents are everything saved to that record. A relatively naive approach as B+ trees are more common. I plan to migrate to B+ trees and to also cache reads in memory. I am also thinking about generating classes for each table as it is migrated to make easier database interactions (inspired by ActiveRecord). 
