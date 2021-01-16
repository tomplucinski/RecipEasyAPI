#!/usr/bin/env bash
lsof -ti:8096 | xargs kill
java \
-Dspring.profiles.active='cloud' \
-DALLOWED_ORIGIN='http://localhost:3000' \
-Dserver.port=8096 \
-Dserver.servlet-context:'/v1' \
-DENV='local' \
-jar ./target/RecipEasyAPI-1.0.0.jar