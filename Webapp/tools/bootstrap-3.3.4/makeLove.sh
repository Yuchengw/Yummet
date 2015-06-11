#!/bin/bash

compileBanner="===============Starting compiling============"
copyBanner="================Start Copying==============="

distPath="/home/yucheng-slave/Yummet/Webapp/app/v1/core/appserver/src/main/webapp/assets/css/bootstrap"

echo $compileBanner
`grunt dist`

echo $copyBanner
`touch ${distPath}/less_build_version.txt`
`cp -f dist/css/bootstrap.css ${distPath}/`
`cp -f dist/css/bootstrap.min.css ${distPath}/`

