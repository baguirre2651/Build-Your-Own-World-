# Build-Your-Own-World-

Game engine that generates explorable 2D tile-based worlds 


skills: Java, StdDraw, Design,



<img width="1427" alt="Screen Shot 2022-09-13 at 8 13 21 PM" src="https://user-images.githubusercontent.com/107953902/190051156-3bcac045-f772-4035-a4bd-5483fc6e6768.png">



# Design Project Overview Document

**Bryan Aguirre**


## Classes and Data Structures

Main: Main Runner

Engine: Handles the inputs for the Main Method

RandomUtils: Creates Psuedo-random Scenarios.

Point: X and Y Coordinates

Hallway: Creates the Path that connects Two Rooms.

Room: Creates a Room around a Center Position with a Random Width and Height.

World: Creates a Random Number of Rooms with Different Dimensions, and Connected Hallways.

GameFile: Used to Save Our World. Saves the Game using a Hash Map.

StringInput: Prepares our String to Generate a Seed.

Avatar: Character and Movements Directions.

Keyboard Interactor: Interacts with Keyboard Movements, as well as has HUD display, Spanish Mode Display, and other requirements for the project. 

String Interactor: Allows us to pass in a string and world in our configuriations. 

## Algorithms

Random World Generator that builds rooms around our single point. Allowing us to keep track of the middle point and based on its position we are able to connect two rooms using hallways based on a certain direction. By inputing a string of numbers, the algorithm is able to take in a string and convert it into a seed, where each seed is embedded and capable of generating the same or different world based on the input. There is an Avatar character that is able to move around the world using keyboard inputs of 'WASD' as well as capable of toggling the world using keyboard input 'V'from light mode to dark mode. Using a HashMap in our GameFile Class , we are able to save our world and load back from our previous position. And as a result, we create a Game Engine that generates explorable 2D tile-based worlds 

## Persistence
Persistence Utils in which we use it to read and write Serialized information. 

