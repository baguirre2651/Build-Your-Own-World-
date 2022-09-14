# Build Your Own World Design Document

**Bryan Aguirre:**


## Classes and Data Structures

Main: main runner

Engine: Handles the inputs for the main method

RandomUtils: creates psuedo-random scenarios.

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
We use Persistence Utils in which we use it to read and write Serialized information. 
