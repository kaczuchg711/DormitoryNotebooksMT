 ps aux | grep spring | grep -v grep | awk '{print $2}' | xargs kill -9
