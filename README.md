# Remote Desktop Application using Java socket
## HCMUS Computer Network Project - 21CLC01 - Lecturer: Mr. Do Hoang Cuong
### See my demo here: [https://www.youtube.com/watch?v=2kGFhEFEmqA&t=181s](https://www.youtube.com/watch?v=GqjklNI_6X4)
I got this project on my second year at University and I have no idea how to work with Java. But I still managed to handle it and my project got the highest score :D.
So basiclly I was asked to build a java application using server-client model. In which the client can connect to server and do some stuff like:
- Take screenshot on server and send it back to client
- Shutdown the server computer
- Handle the server's task manager (create, kill process)
- Key-stroke

I use the built-in socket.io library to handle the TCP connection between server and client. Every function in the application use built-in Java medthods except the Key-stroke. For the Key-stroke, I use jnativehook library (https://github.com/kwhat/jnativehook).

And that's it. Even though I'm not familiar with Java but I managed to handle it and I put a lot of efforts in it. 
