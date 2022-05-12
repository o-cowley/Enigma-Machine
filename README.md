#The Enigma Machine

## Project Description:
This project is a working model of the German Enigma machine from WWII. It accurately reflects all of the main  
ways in which the machine worked. The machine relies on several Rotors that determine the encryption of each  
letter. Passing a letter through multiple rotors has the effect of a shifting Vignere cypher. I have modelled  
the Rotors according to their actual wiring from the original Enigma I, used from around 1930 to 1940.  
The rotor wiring details came from Wikepedia, from information written and organized by *Graham Ellsbury*,  
much of it can be found here: [Enigma Information](http://www.ellsbury.com/enigmabombe.htm)

This program will appeal to anyone who has an interest in encryption and wants to feel what it was like to use one  
of the more significant cryptographic tools from the past century. The Enigma has unique operation in that it  
is bi-directional, with encryption and decryption working by passing the plaintext and ciphertext through  
the machine in the same manner, simply using the same rotors and start settings does both jobs. To me this is  
incredibly interesting as it represented a major step in cryptography in the 1900s. As well, it instigated some  
groundbreaking work on computing by Alan Turing, a man I find infinitely fascinating and who inspired my passion  
for computing and cryptography. In the past, I have written some small programs that use simplified version of  
some of the encryption algorithms that protect our data today. When the project was announced, I felt like  
modelling the Enigma machine would be a very compelling way of learning the ins and outs of Java

## Pictures of the Application in Use:
  
This is the application:

![Enigma-Machine-InUse](https://user-images.githubusercontent.com/93353002/168152195-8f9769b7-2b9f-4c7c-8fb9-474151860f60.png)

Upon loading, the user will be prompted to load the previously saved set of rotor settings. When the user chooses to close the application, they will be first prompted if they wish to save their settings from the most recent encryption

The following is an explanation of the buttons and what they do:
- Upper Text Box: This is where a user enters text to encrypt or decrypt
- Lower Text Box: This is where the encrypted or decrypted text will appear when the user presses the Encrypt button
- Clean up text: This button will take the input text and remove all non-encryptable characters, such as numbers or punctuation, to allow the string to be passed through the device
- Encrypt Text: Passes the input string through the device
- Add Rotor: Triggers a popup that allows the user to select the type of rotor (1 - 8) to add to the rotor box
- Edit Setting: Triggers a popup that allows the user to change the setting (0-25, corresponding to A-Z) of the rotor that is highlighted in the list on the right
- Delete Selected: This removes the selected rotor from the list
- Rotor List: This list displays the rotors in the order that they are to be used in the device. Tool Tip Text will show the rotor's type and setting

## The User Stories:
#### These are the goals for the functionality of the application, they are the principles upon which it was  
designed and consequently built

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

## Bonus Content:

There are several easter eggs within the project, images that pop up when you try to encrypt specific lines  
of text. They are meme-based and entertained me immensely while developing the application, so I left  
them in for fun.

