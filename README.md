# My Personal Project: The Enigma Machine

## Project Description:
This project is a working model of the German Enigma machine from WWII. It accurately reflects all of the main  
ways in which the machine worked. The machine relies on several Rotors that determine the encryption of each  
letter. Passing a letter through multiple rotors has the effect of a shifting Vignere cypher. I have modelled  
the Rotors according to their actual wiring from the original Enigma I, used from around 1930 to 1940.  
The rotor wiring details came from Wikepedia, from information written and organized by *Graham Ellsbury*,  
much of it can be found here: [Enigma Information](http://www.ellsbury.com/enigmabombe.htm)

This program will be used by anyone who has an interest in encryption and wants to feel what it was like to use one  
of the more significant cryptographic tools from the past century. The Enigma has unique operation in that it  
is bi-directional, with encryption and decryption working by passing the plaintext and ciphertext through  
the machine in the same manner, simply using the same rotors and start settings does both jobs. To me this is  
incredibly interesting as it represented a major step in cryptography in the 1900s. As well, it instigated some  
groundbreaking work on computing by Alan Turing, a man I find infinitely fascinating and who inspired my passion  
for computing and cryptography. In the past, I have written some small programs that use simplified version of  
some of the encryption algorithms that protect our data today. When the project was announced, I felt like  
modelling the Enigma machine would be a very compelling way of learning the ins and outs of Java

## The User Stories:

- As a user, I want the ability to add an arbitrary number of (class:) Rotor to (class:) InUseRotors **(Xs to Y)**
  - the rotors contain the data to encrypt a message, as well as keeping track of its setting (rotation)
- As a user, I want to be able to assign the starting point of each rotor individually
- As a user, I want to be able to review my rotor selections, and then adjust/delete/add to the rotors
- As a user, I should be able to encrypt and decrypt a message by passing it through the machine with  
the same initial settings, any changes to the settings or rotors will mean that decryption will not work
- As a user, I want the option to review my selected settings upon completion of encryption 

- As a user, I want the choice to save the rotor settings that I used to file, or not do so
- As a user, I want the choice to load and use my last saved rotor settings from file, or not do so and pick  
them myself
