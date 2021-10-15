# My Personal Project: The Enigma Machine

## Project Description:
This project is a working model of the German Enigma machine from WWII. It accurately reflects all of the main  
ways in which the machine worked. The machine relies on several Rotors that determine the encryption of each  
letter. Passing a letter through multiple rotors has the effect of a shifting Vignere cypher. I have modelled  
the Rotors according to their actual wiring from the original Enigma I, used from around 1930 to 1940.  
The rotor wiring details came from Wikepedia, which I believe was from information written and organized  
by *Graham Ellsbury*, much of it can be found here: [Enigma Information](http://www.ellsbury.com/enigmabombe.htm)

This program will be used by anyone who has an interest in encryption. It has a unique operation in that it  
is bi-directional, with encryption and decryption working by simply passing the plaintext and ciphertext  
through the machine in the same manner, simply with the same rotors and start settings. To me this is  
incredibly interesting as it represented a major step in cryptography in the 1900s. As well, it instigated some  
groundbreaking work on computing by Alan Turing, a man I find infinitely fascinating. I have a bit of a passion  
for cryptography. Consequently, building this program was very appealing to me. In the past, I have written  
some small programs that use simplified version of some of the encryption algorithms that protect our data  
today. When the project was announced, I felt like modelling the Enigma machine would be a very compelling  
way of learning the ins and outs of Java.

## The User Stories:

- as a user I want the ability to add an arbitrary number of (class:) Rotors to (class:) InUseRotors **(Xs to Y)**
  - the rotors are used to encrypt a message and even a single change produces drastically different results
- as a user I want to be able to assign the starting point of each rotor individually
- as a user, I want to be able to go back and edit/delete/add to the rotors I have already selected
- as a user I want to be able to use this encryption method to produce different outcomes, for the same string,  
based on the initial settings
- as a user I want to be able to decrypt a message by using the same settings that were used to encrypt the  
message, and then simply passing the message through the program 
- as a user I want the option to review my initial selected settings, upon completion of encryption 
