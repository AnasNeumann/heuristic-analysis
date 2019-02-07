#!/bin/bash

export PATH="/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:/root/bin"
certbot -q renew --nginx > /BACK_MQT-8100/letsencrypt.log 2>&1
chown mqt-8100:mqt-8100 /BACK_MQT-8100/letsencrypt.log || :
