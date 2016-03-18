#!/bin/sh
cd $HOME
git clone -b gh-pages "https://$GITHUB_TOKEN@github.com/konikvranik/jtoggl.git" gh-pages || exit 1
echo Copying report
cp -ru $TRAVIS_BUILD_DIR/build/report* gh-pages || exit
echo CD to gh-pages
cd $HOME/gh-pages || exit 1
echo config git
git config user.email builds@travis-ci.org
git config user.name "Trsvis CI"
echo Adding lint
echo Adding report
git add report* || exit 1
echo Commiting
git commit -m "travis => build reports"
echo Pushing
git push
