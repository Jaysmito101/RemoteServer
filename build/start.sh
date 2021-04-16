#!/bin/bash
echo "Enter your password to start servers"
sudo -v
echo "Starting backend and frontend servers ..."
cd backend
sudo java RemoteServer &
cd ..
cd frontend
sudo node server.js &
sleep 1
echo "Servers started!"
read -r -p "Press any key to exit and shutdown servers ..."