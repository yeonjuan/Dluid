![main_icon](./document/resources/title.png)
## Introduction
DLUID (Deep Learning User Interface Design)  
Dluid is a learning application for non-specialists in the computer programming but who want to study deep learning.  

- No complex package installation required.
- Do not require programming knowledge.
- Create and practice a simple sequence model.
- Use relatively familiar Excel data, or at least csv data.

## Demonstration
#### Iris problem
![iris_problem](document/resources/iris_problem_50.gif)

## Motivation
Many people compare the process of making models using deep learning to Lego.
But why don't we have tools like Lego for deep learning?

Let me give you an example.
There is a business student 'Raynor' who wants to study deep learning because it's so hot.
He wants to know how deep learning can be used in future management issues.
Full of enthusiasm, he took a video lesson on deep learning. 
Now, it's time to practice.
Unfortunately, he has no experience about programming languages.
Will student he complete the deep learning exercise safely?

As you know, generally when you study deep learning, it has same mean that studying tensorflow.
We know tensorflow is a great library.
But no matter how cool the tensorflow is, even developers have to spend their time to study and learn it .
Do you think non developer like raynor easily learn about such a library?
'from', 'in', 'for', 'if' ... Even simple grammar is obstacle.
The code is messy and he have no idea how to organize it.
He just wanted to study deep learning, but he spend a whole night catching Python grammar errors.
And even he have to install a tensorflow library using a tool called a "pip" that he can't guess what abbreviation is.
And more, if his operating system is Windows, they need to install 'conda' and create a 'virtual environment'!

These people might give up these deep learning before I even start studying it.
For raynor, deep learning feels like a more darker black box.
And for them, it seems like artificial intelligence will take away all the jobs and dominate the world after overwhelming the humans.
We need to at least get out of this fantasy.
So I wanted to make a GUI tool that can make learn deep learning easily.
DLUID is a learning tool that provides a simple hands-on experience for these people.

## Version
- v0.1.0 (2020.02.01)
  - Support layer
    - Input
    - Output
    - FCNN
    - CNN 1D
    - CNN 2D
    - DeCNN 2D
    - RNN
    - LSTM
    - RNN Output
    - Reshape
    - Pooling 1D
    - Pooling 2D
    - Batch norm
  - Create simple sequence model and test it.
    - Excel, csv data load
    - Visualize learning curve
    - Confirm test data set
    - Confirm test result and export it
  
## Comment
- Block double click : connection start  
- You can download simple dataset in this repository. (./document/dataset)

## TODO 
- Save and load model
- Training RNN (bug(?))
- Develop merge and switch layer. Goal : create several model and train them while finding interaction. 
- Extend project to application that can make MNIST VAE or GAN.