# FlowerRec
Android App for Flower Recognition

A native android app for recognising flowers.
Training is done offline using the dataset of Nilsback and Zisserman.
http://www.robots.ox.ac.uk/~vgg/data/flowers/17/index.html

It uses the bag of visual words implementation of Shackenberg
https://github.com/shackenberg/Minimal-Bag-of-Visual-Words-Image-Classifier
to train a support vector machine

The files derived during training (support vectors and scaling file) are then put onto the device.

When testing, it uses the nonfree Sift (OpenCV for Android) implementation to derive
and cluster the features. After projecting into the SVM, each class is associated with a probability.



