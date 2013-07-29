# GPL Header, Version 3 or Later
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.

echo Building TemplateWorlds for release distribution.

# Just to verify, this should be run from the project root.

# Get version (sed is magic)
# Intent: Match the first occurence of <version>.*</version> and print only that
VERSION=$(sed -nr '1,/<version>(.*)<\/version>/s/\s<version>(.*)<\/version>/\1/p' pom.xml)

# Build normal jars
mvn clean install
cd api
# Create API zip with doc & sources
mvn javadoc:jar source:jar
cd target
zip "templateworlds-api-$VERSION.zip" templateworlds-api-${VERSION}.jar templateworlds-api-${VERSION}-sources.jar templateworlds-api-${VERSION}-javadoc.jar
cd ../..
# Put completed files in main directory
cp api/target/templateworlds-api-$VERSION.zip .
cp plugin/target/TemplateWorlds-$VERSION.jar .
# Say something when done
echo Created TemplateWorlds-$VERSION.jar
echo Created templateworlds-api-$VERSION.zip
