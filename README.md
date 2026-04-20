# Overview

This was developed as part of the **Object-Oriented Programming (PO)** course at IST-UL.

The objective was to implement a Java-based Object-Oriented System that simulates the management of a veterinary hotel. It models animals, species, habitats, employees, trees and vaccines, along with their interactions and satisfaction metrics.

The system follows a layered architecture with strict separation between core logic and user interface, and emphasizes extensible object-oriented design principles.

# HVA application

* Base: `po-uilib` contains the base classes
* Core: `hva-core` contains the domain classes
* Interaction: `hva-app` contains the user interaction classes
* UML diagrams: `uml` will contain the diagrams from the first delivery

# Compilation and Usage

1. Enter `po-uilib/`
2. Use `make` to compile base classes
3. Return to root
4. Use `make` to compile `hva-app` and `hva-core`
5. Start app using `java -cp "hva-app/src:hva-core/src:po-uilib/src" hva.app.App`

# Technologies

- [java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
