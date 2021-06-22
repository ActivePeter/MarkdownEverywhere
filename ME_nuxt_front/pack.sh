# cd ./pack
rm -rf ./pack
mkdir ./pack
cp nuxt.config.js ./pack/nuxt.config.js
cp package.json ./pack/package.json
cp -r ./.nuxt ./pack/.nuxt
cp -r ./static ./pack/static