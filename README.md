## Advanced Akka with Scala

---

### Introduction

This document describes how to setup:

- The development environment
- The case study project

We recommend using the following tools:

- Eclipse or IntelliJ, both with Scala plugin
- sbt build tool

---

## Prerequisites

---

### Required Knowledge and Software

This course is best suited for individuals that have knowledge of Akka and Scala as covered in our [Fast Track to Akka with Scala](http://www.typesafe.com/subscription/training) course. Also, we need access to the internet and a computer with the following software installed:

- Unix compatible shell
- JVM 1.8 or higher
- Scala 2.11 or higher
- Sbt 0.13.8 or higher

---

### Unix Compatible Shell

If you are running OSX, then you are on a **nix** system already. Otherwise install a Unix compatible shell like [Cygwin](https://www.cygwin.com/).

---

### JVM 1.8 or Higher

If you are running OSX and a [Homebrew Cask](https://github.com/caskroom/homebrew-cask) user, from a terminal run:

```bash
$ brew cask install java
```

Otherwise follow the [setup instructions](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) to download and install. Once the installation is complete, very the installation by running the following command in a terminal session:

```bash
$ java -version
java version "1.8.0_45"
Java(TM) SE Runtime Environment (build 1.8.0_45-b14)
Java HotSpot(TM) 64-Bit Server VM (build 25.45-b02, mixed mode)
```

---

### Scala 2.11 or Higher

If you are running OSX and a [Homebrew](http://brew.sh/) user, from a terminal run:

```bash
$ brew install scala
```

Otherwise follow the [setup instruction](http://www.scala-lang.org/documentation/) to download and install. Once the installation is complete, verify the installation by running the following command in a terminal session:

```bash
$ scala -version
Scala code runner version 2.11.6 -- Copyright 2002-2013, LAMP/EPFL
```

---

### Sbt 0.13.8 or Higher

If you are running OSX and a [Homebrew](http://brew.sh/) user, from a terminal run:

```bash
$ brew install sbt
```

Otherwise follow the [setup instruction](http://www.scala-sbt.org/0.13/docs/index.html) to download and install. Once the installation is complete, verify the installation by running the following command in a terminal session:

```bash
$ sbt -version
sbt launcher 0.13.8
```

---

## Simple Build Tool

---

### Make Yourself Familiar with Sbt

- Read the first chapters of the [Getting Started Guide](http://www.scala-sbt.org/release/tutorial/index.html)
- Starting `sbt` takes you to a **interactive session**
- Take a look at `build.sbt` and the other `.sbt` files for Akka Collect
- Change directory to the `advanced-akka-scala` directory and start `sbt` as follows:

```scala
$ sbt
man [e] > akka-collect > initial-state >
```

---

### man

The `man` command, short for manual, displays the setup instructions (what you are reading now) for the courseware. To view the instructions for the current exercise, use the `e` option. If you are using an IDE, you can also open up the setup instructions (`README.md`) file or the current exercises instructions (`src/test/resources/README.md`) file in your workspace.

```scala
// display the setup instructions
man [e] > akka-collect > initial-state > man

// display the instructions for the current exercise
man [e] > akka-collect > initial-state > man e
```

---

### run

As part of each exercise, we use the `run` command to bootstrap one of the main classes.

```scala
man [e] > akka-collect > initial-state > run
```

---

### command aliases (ge, pr, sr, ge2, pr2, sr2 and sj)

This courseware has several command aliases setup for convenience and can view them in the `build.sbt` file. The instruction for a given exercise will explain when to use these command aliases.

```scala
// runs the game engine on port 2551
man [e] > akka-collect > cluster-events > ge

// runs the player registry on port 2552
man [e] > akka-collect > cluster-events > pr

// runs the scores repository on port 2553
man [e] > akka-collect > cluster-events > sr

// runs the game engine on port 2554
man [e] > akka-collect > cluster-events > ge2

// runs the player registry on port 2555
man [e] > akka-collect > cluster-events > pr2

// runs the scores repository on port 2556
man [e] > akka-collect > cluster-events > sr2

// runs the shared journal on port 2550
man [e] > akka-collect > cluster-events > sj
```

---
### koan

Koan is the `sbt` plugin that allows us to navigate the courseware and pull the current exercises tests that to confirm our solution is accurate. It is important to note that the tests make some assumptions about the code, in particular, naming and scope; please adjust your source accordingly. Following are the available `koan` commands:

```scala
// show the current exercise
man [e] > akka-collect > initial-state > koan show
[info] Currently at koan 'Initial state (koan:initial)

// move to the next exercise
man [e] > akka-collect > complete-initial-state > koan next
[info] Moved to next koan 'Implement an actor'

// move to the previous exercise
man [e] > akka-collect > initial-state > koan prev
[info] Moved to previous koan 'Initial state (koan:initial)'
```

---

### clean

To clean your current exercise, use the `clean` command from your `sbt` session. Clean deletes all generated files in the `target` directory.

```scala
man [e] > akka-collect > initial-state > clean
```

---

### compile

To compile your current exercise, use the `compile` command from your `sbt` session. This command compiles the source in the `src/main/scala` directory.

```scala
man [e] > akka-collect > initial-state > compile
```

---

### reload

To reload `sbt`, use the `reload` command from your `sbt` session. This command reloads the build definitions, `build.sbt`, `project/.scala` and `project/.sbt` files. Reloading is a **requirement** if you change the build definition files.

```scala
man [e] > akka-collect > initial-state > reload
```

---

### test

To test your current exercise, use the `test` command from your `sbt` session. Test compiles and runs all tests for the current exercise. Automated tests are your safeguard and validate whether or not you have completed the exercise successfully and are ready to move on.

```scala
man [e] > akka-collect > initial-state > test
```

---

## Eclipse

---

### Install the Scala IDE for Eclipse

Follow these instructions if you want to use Eclipse:

- **Attention**: Make sure you pick the right packages for Scala 2.11!
- You can download the prepackaged and preconfigured [Scala IDE](http://scala-ide.org/download/sdk.html) for your platform
- You can also use your already installed Eclipse:
    - Install the latest Scala IDE plugin
    - Use the following [update site](http://scala-ide.org/download/current.html) for Eclipse 4.3 and 4.4 (Kepler and Luna)
- In Eclipse import the `akka-collect` project with `Import... > Existing Projects into Workspace`

---

### Install the ScalaTest Plugin for Eclipse

Follow these instructions if you want to use Eclipse:

- In Eclipse select `Help > Install New Software`
- Select the `Scala IDE` update site
- Expand the entry `Scala IDE plugins`
- Select entry `ScalaTest for Scala IDE`
- Press `Next` > and follow the installation process
- Restart Eclipse if prompted

---

### Create Eclipse Project from Sbt

Follow these instructions if you want to use Eclipse:

- From your `sbt` session use the `eclipse` command.
- You have to re-run this command and refresh the Eclipse project when you change the build definition.

```scala
man [e] > akka-collect > initial-state > eclipse
[info] Successfully created Eclipse project files ...
```

---

## IntelliJ

---

### Install IntelliJ IDEA with Scala Plugin

Follow these instructions if you want to use IntelliJ IDEA:

- Download and install the latest version of [IntelliJ IDEA 14](https://www.jetbrains.com/idea/download/)
- Start IntelliJ IDEA and install the Scala plugin via the plugin configuration
- Import the Akka Collect project via `Import Project`

---

## Case Study

---

### Akka Collect

Welcome to  Akka Collect, a multi-player game, where players are in competition to collect the most coins. In this courseware, we work through a series of exercises as laid out in the Advanced Akka with Scala slide deck and experience:

- Players are placed on a grid and try to **collect coins**.
- On each move, players on see a **nearby section** of the grid.
- When a **coin is collected**, a new one is **created** somewhere else on the grid.

Our mission is starting with the provided implementation, make Akka Collect **distributed** and **highly available**!

---

### Exercise Outline

0. Exercise 0 > Initial State
1. Exercise 1 > Complete Initial State
2. Exercise 2 > Remoting
3. Exercise 3 > Cluster Events
4. Exercise 4 > Cluster Aware Routers
5. Exercise 5 > Cluster Singleton
6. Exercise 6 > Cluster Sharding
7. Exercise 7 > Persistent Actors
8. Exercise 8 > Data Replication
