#!/bin/bash
# Run RVT Test
read -p 'MVE IP Address: ' ipvar
read -p 'Start Test: ' startvar
read -p 'End Test: ' endvar
read -p 'Username: ' uservar
read -sp 'Password: ' passvar
echo
echo Thankyou $ipvar $uservar $passvar $startvar $endvar
while [ "$startvar" -le "$endvar" ]; do
  java rvt_run $ipvar $uservar $passvar $startvar
  startvar=$(($startvar + 1))
done