#!/bin/sh
sudo apt update
mkdir OSS
cd OSS
sudo apt install -y python2.7
sudo apt install -y python3.6
sudo apt install -y python-pip
sudo apt install -y python3-pip
sudo pip install --upgrade pip
sudo pip3 install pip --upgrade
sudo apt install -y python3-opencv
sudo pip install -y easyocr
