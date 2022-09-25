# Build-Your-Own-World-

Game engine that generates explorable 2D tile-based worlds 


skills: Java, StdDraw, Design,



<img width="1427" alt="Screen Shot 2022-09-13 at 8 13 21 PM" src="https://user-images.githubusercontent.com/107953902/190051156-3bcac045-f772-4035-a4bd-5483fc6e6768.png">



# Design Project Overview Document

**Bryan Aguirre**


## Classes and Data Structures

Main: Main Runner

Engine: Handles the inputs for the main method

RandomUtils: Creates psuedo-random scenarios.

Point: X and Y coordinates

Hallway: Creates the Path that connects two rooms.

Room: Creates a room around a center position with a random width and height.

World: Creates a random number of rooms with different dimensions, and connected hallways.

GameFile: Used to Save Our World. Saves the Game using a Hash Map.

StringInput: Prepares our string to generate a seed.

Avatar: Character and Movements.

Keyboard Interactor: Interacts with Keyboard Movements, as well as has HUD display, Spanish Mode Display, and other requirements for the project. 

String Interactor: Allows us to pass in a string and world in our configuriations. 

## Algorithms

Random World Generator that builds rooms around our single point. Allowing us to keep track of the middle point and based on its position we are able to connect two rooms using hallways based on a certain direction.   

## Persistence
Persistence Utils in which we use it to read and write Serialized information. 

