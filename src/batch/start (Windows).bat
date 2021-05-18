@echo off
echo Starting backend and frontend servers ...
cd backend
start java RemoteServer
cd ..
cd frontend
start node server.js &
echo Press any key to exit and shutdown servers ...
pause